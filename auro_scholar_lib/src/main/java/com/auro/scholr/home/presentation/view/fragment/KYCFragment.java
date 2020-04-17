package com.auro.scholr.home.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.core.util.uiwidget.others.HideBottomNavigation;
import com.auro.scholr.databinding.KycFragmentLayoutBinding;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.KYCuploadAdapter;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.cropper.CropImage;
import com.auro.scholr.util.cropper.CropImageView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;


public class KYCFragment extends BaseFragment implements CommonCallBackListner {

    @Inject
    @Named("KYCFragment")
    ViewModelFactory viewModelFactory;


    KycFragmentLayoutBinding binding;
    KYCViewModel kycViewModel;
    KYCuploadAdapter kyCuploadAdapter;

    private Activity mActivity;
    private HideBottomNavigation hideBottomNavigation;
    private CommonDataModel commonDataModel;
    private int pos;
    ArrayList<KYCDocumentDatamodel> kycDocumentDatamodelArrayList;


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
        setAdapter();
        return binding.getRoot();
    }

    public void setAdapter() {
        this.kycDocumentDatamodelArrayList = kycViewModel.homeUseCase.makeDummyDocumentList();
        binding.documentUploadRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.documentUploadRecyclerview.setHasFixedSize(true);
        kyCuploadAdapter = new KYCuploadAdapter(getActivity(), kycViewModel.homeUseCase.makeDummyDocumentList(), this);
        binding.documentUploadRecyclerview.setAdapter(kyCuploadAdapter);
    }


    private void getUserPref() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
    }

    @Override
    protected void init() {

        getUserPref();
    }


    @Override
    protected void setToolbar() {
        /*Do code here*/
    }

    @Override
    protected void setListener() {
        /*Do code here*/
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
                pos = commonDataModel.getSource();
                KYCDocumentDatamodel kycDocumentDatamodel = (KYCDocumentDatamodel) commonDataModel.getObject();
                if (kycDocumentDatamodel.getDocumentId() == AppConstant.documentType.UPLOAD_YOUR_PHOTO) {
                    openActivity();
                } else {
                    CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(getActivity());
                }

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
                } catch (Exception e) {

                }


            } else {

            }
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

            kycDocumentDatamodelArrayList.get(pos).setDocumentURi(new URI(path));
            kycDocumentDatamodelArrayList.get(pos).setButtonText(getString(R.string.upload_file));
            kyCuploadAdapter.updateList(kycDocumentDatamodelArrayList);
        } catch (Exception e) {
            /*Do code here when error occur*/
        }
    }


}
