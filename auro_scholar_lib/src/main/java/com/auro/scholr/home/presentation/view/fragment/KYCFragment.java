package com.auro.scholr.home.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.core.util.uiwidget.others.HideBottomNavigation;
import com.auro.scholr.databinding.KycFragmentLayoutBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCResItemModel;
import com.auro.scholr.home.data.model.KYCResListModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.KYCuploadAdapter;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.cropper.CropImage;
import com.auro.scholr.util.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.KYC_BUTTON_CLICK;
import static com.auro.scholr.core.common.Status.UPLOAD_PROFILE_IMAGE;


public class KYCFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("KYCFragment")
    ViewModelFactory viewModelFactory;


    KycFragmentLayoutBinding binding;
    KYCViewModel kycViewModel;
    KYCuploadAdapter kyCuploadAdapter;

    private Activity mActivity;
    private HideBottomNavigation hideBottomNavigation;

    private int pos;
    private DashboardResModel dashboardResModel;
    ArrayList<KYCDocumentDatamodel> kycDocumentDatamodelArrayList;
    private boolean uploadBtnStatus;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        kycViewModel = ViewModelProviders.of(this, viewModelFactory).get(KYCViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setKycViewModel(kycViewModel);
        HomeActivity.setListingActiveFragment(HomeActivity.KYC_FRAGMENT);
        return binding.getRoot();
    }

    public void setAdapter() {
        this.kycDocumentDatamodelArrayList = kycViewModel.homeUseCase.makeAdapterDocumentList(dashboardResModel);
        binding.documentUploadRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.documentUploadRecyclerview.setHasFixedSize(true);
        kyCuploadAdapter = new KYCuploadAdapter(getActivity(), kycViewModel.homeUseCase.makeAdapterDocumentList(dashboardResModel), this);
        binding.documentUploadRecyclerview.setAdapter(kyCuploadAdapter);
    }


    private void getUserPref() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }
        getUserPref();
    }


    @Override
    protected void setToolbar() {
        /*Do code here*/
    }

    @Override
    protected void setListener() {
        /*Do code here*/
        binding.btUploadAll.setOnClickListener(this);
        if (kycViewModel != null && kycViewModel.serviceLiveData().hasObservers()) {
            kycViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.kyc_fragment_layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setToolbar();
        setListener();
        setAdapter();
        if (kycViewModel.homeUseCase.checkUploadButtonStatus(kycDocumentDatamodelArrayList)) {
            binding.btUploadAll.setVisibility(View.VISIBLE);
        } else {
            binding.btUploadAll.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && prefModel.getUserKYCProfilePhotoPath() != null && !prefModel.getUserKYCProfilePhotoPath().isEmpty()) {
            updateKYCList(prefModel.getUserKYCProfilePhotoPath());
            prefModel.setUserKYCProfilePhotoPath("");
            AppPref.INSTANCE.setPref(prefModel);
        }
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

        switch (commonDataModel.getClickType()) {
            case KYC_BUTTON_CLICK:
                onUploadDocClick(commonDataModel);

                break;
            case KYC_RESULT_PATH:
                /*do code here*/
                break;
        }
    }

    public void openActivity() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);
    }

    public void openCameraFragment() {
        CameraFragment cameraFragment = new CameraFragment();
        FragmentUtil.replaceFragment(AuroApp.getAppContext(), cameraFragment, R.id.home_container, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLogger.e("chhonker", "fragment requestCode=" + requestCode);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Uri resultUri = result.getUri();
                    updateKYCList(resultUri.getPath());
                } catch (Exception e) {

                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if (requestCode == AppConstant.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    String path = data.getStringExtra(AppConstant.PROFILE_IMAGE_PATH);
                    updateKYCList(path);
                    // loadImageFromStorage(path);
                } catch (Exception e) {

                }


            } else {

            }
        }

    }

    private void loadImageFromStorage(String path) {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            binding.resultImage.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void updateKYCList(String path) {
        try {

            File file = new File(path);
            if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.documentType.ID_PROOF_FRONT_SIDE) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_front.jpg");
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.documentType.ID_PROOF_BACK_SIDE) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_back.jpg");
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.documentType.SCHOOL_ID_CARD) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_school.jpg");
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.documentType.UPLOAD_YOUR_PHOTO) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("profile.jpg");
            }
            kycDocumentDatamodelArrayList.get(pos).setDocumentURi(Uri.parse(path));
            kycDocumentDatamodelArrayList.get(pos).setButtonText(getString(R.string.upload_file));
            kyCuploadAdapter.updateList(kycDocumentDatamodelArrayList);
        } catch (Exception e) {
            /*Do code here when error occur*/
        }
    }

    private void observeServiceResponse() {
        kycViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    progressBarHandling(0);
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == UPLOAD_PROFILE_IMAGE) {
                        updateListonResponse((KYCResListModel) responseApi.data);
                        if (uploadBtnStatus && pos <= 3) {
                            binding.btUploadAll.setVisibility(View.INVISIBLE);
                            pos = pos + 1;
                            onUploadDocClick(AppUtil.getCommonClickModel(pos, KYC_BUTTON_CLICK, kycDocumentDatamodelArrayList.get(pos)));
                        } else {
                            uploadBtnStatus = false;
                        }
                    }

                    break;

                case NO_INTERNET:
                case AUTH_FAIL:
                case FAIL_400:
                    showError((String) responseApi.data);
                    break;

                default:
                    showError((String) responseApi.data);
                    break;
            }

        });
    }

    private void progressBarHandling(int status) {
        if (status == 0) {
            kyCuploadAdapter.updateProgressValue(true);
            kycDocumentDatamodelArrayList.get(pos).setProgress(true);
            kycDocumentDatamodelArrayList.get(pos).setButtonText("");
        } else {
            kyCuploadAdapter.updateProgressValue(false);
            kycDocumentDatamodelArrayList.get(pos).setProgress(false);
            kycDocumentDatamodelArrayList.get(pos).setBackgroundStatus(false);
            kycDocumentDatamodelArrayList.get(pos).setButtonText("Sucessfully");
        }
        kyCuploadAdapter.updateList(kycDocumentDatamodelArrayList);
    }

    private void updateListonResponse(KYCResListModel kycResListModel) {
        for (KYCResItemModel resItemModel : kycResListModel.getList()) {
            if (resItemModel.getId_name().equalsIgnoreCase(kycDocumentDatamodelArrayList.get(0).getId_name())) {
                if (!resItemModel.getError() && resItemModel.getUrl() != null && !resItemModel.getUrl().isEmpty()) {
                    setDataOnResponse(resItemModel, 0, true);
                } else {
                    setDataOnResponse(resItemModel, 0, false);
                }

            } else if (resItemModel.getId_name().equalsIgnoreCase(kycDocumentDatamodelArrayList.get(1).getId_name())) {
                if (!resItemModel.getError() && resItemModel.getUrl() != null && !resItemModel.getUrl().isEmpty()) {
                    setDataOnResponse(resItemModel, 1, true);
                } else {
                    setDataOnResponse(resItemModel, 1, false);
                }
            } else if (resItemModel.getId_name().equalsIgnoreCase(kycDocumentDatamodelArrayList.get(2).getId_name())) {
                if (!resItemModel.getError() && resItemModel.getUrl() != null && !resItemModel.getUrl().isEmpty()) {
                    setDataOnResponse(resItemModel, 2, true);
                } else {
                    setDataOnResponse(resItemModel, 2, false);
                }
            } else if (resItemModel.getId_name().equalsIgnoreCase(kycDocumentDatamodelArrayList.get(3).getId_name())) {
                if (!resItemModel.getError() && resItemModel.getUrl() != null && !resItemModel.getUrl().isEmpty()) {
                    setDataOnResponse(resItemModel, 3, true);
                } else {
                    setDataOnResponse(resItemModel, 3, false);
                }
            }
        }

    }

    private void setDataOnResponse(KYCResItemModel resItemModel, int pos, boolean b) {
        if (b) {
            kycDocumentDatamodelArrayList.get(pos).setDocumentstatus(true);
            kycDocumentDatamodelArrayList.get(pos).setDocumentUrl(resItemModel.getUrl());
        } else {
            kycDocumentDatamodelArrayList.get(pos).setDocumentstatus(false);
            kycDocumentDatamodelArrayList.get(pos).setDocumentUrl("");
        }

        progressBarHandling(1);

    }

    private void showError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    public void onClick(View v) {
        if (kycViewModel.homeUseCase.checkUploadButtonDoc(kycDocumentDatamodelArrayList)) {
            uploadBtnStatus = true;
            binding.btUploadAll.setEnabled(false);
            uploadAllDoc();
        } else {
            ViewUtil.showSnackBar(binding.getRoot(), "Please select the all four document first");
        }

    }

    private void uploadAllDoc() {
        pos = 0;
        onUploadDocClick(AppUtil.getCommonClickModel(pos, KYC_BUTTON_CLICK, kycDocumentDatamodelArrayList.get(pos)));
    }

    private void onUploadDocClick(CommonDataModel commonDataModel) {
        pos = commonDataModel.getSource();
        KYCDocumentDatamodel kycDocumentDatamodel = (KYCDocumentDatamodel) commonDataModel.getObject();
        if (kycDocumentDatamodel.getButtonText().equalsIgnoreCase(getString(R.string.upload_file))) {
            try {

                File file = new File(kycDocumentDatamodel.getDocumentURi().getPath());
                //file= ImageUtil.compressFile(file);
                long length = file.length();
                length = length / 1024;
                InputStream is = AuroApp.getAppContext().getApplicationContext().getContentResolver().openInputStream(Uri.fromFile(file));
                kycViewModel.uploadProfileImage(kycViewModel.getBytes(is), kycDocumentDatamodel.getDocumentURi());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            if (kycDocumentDatamodel.getDocumentId() == AppConstant.documentType.UPLOAD_YOUR_PHOTO) {
                openActivity();
            } else {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity());
            }
        }
    }



}
