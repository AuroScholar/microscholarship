package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.FragmentTeacherKycBinding;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCResItemModel;
import com.auro.scholr.home.data.model.KYCResListModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.teacher.data.model.response.MyClassRoomResModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomTeacherResModel;
import com.auro.scholr.teacher.presentation.view.adapter.TeacherKycDocumentAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherKycViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;
import com.auro.scholr.util.cropper.CropImageViews;
import com.auro.scholr.util.cropper.CropImages;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;

public class TeacherKycFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {
    @Inject
    @Named("TeacherKycFragment")
    ViewModelFactory viewModelFactory;
    FragmentTeacherKycBinding binding;
    TeacherKycViewModel viewModel;
    boolean isStateRestore;
    private boolean uploadBtnStatus;
    Resources resources;
    int pos;
    Uri resultUri;
    ArrayList<KYCDocumentDatamodel> kycDocumentDatamodelArrayList;
    TeacherKycDocumentAdapter mteacherKycDocumentAdapter;
    CustomProgressDialog customProgressDialog;

    public TeacherKycFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding != null) {
            isStateRestore = true;
            return binding.getRoot();
        }
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TeacherKycViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setTeacherKycViewModel(viewModel);
        setRetainInstance(true);
        setAdapter();

        return binding.getRoot();
    }

    @Override
    protected void init() {
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        setListener();
        viewModel.getTeacherDashboardData(AuroApp.getAuroScholarModel().getMobileNumber());
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }

        binding.button.setOnClickListener(this);
    }


    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {

                case LOADING:
                    if (responseApi.apiTypeStatus == Status.TEACHER_KYC_API) {
                        // handleProgress(0);
                        openProgressDialog();
                    }

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == Status.TEACHER_KYC_API) {
                        KYCResListModel kycResListModel = (KYCResListModel) responseApi.data;
                        if (!TextUtil.checkListIsEmpty(kycResListModel.getList())) {
                            updateUIKYC(kycResListModel);
                        } else if (responseApi.apiTypeStatus == Status.GET_TEACHER_DASHBOARD_API) {
                            AppUtil.myClassRoomResModel = (MyClassRoomTeacherResModel) responseApi.data;
                        }
                        handleProgress(1);
                        uploadBtnStatus = false;
                        closeDialog();

                    }
                    break;

                case FAIL:
                case NO_INTERNET:
                    if (responseApi.apiTypeStatus == Status.TEACHER_KYC_API) {
                        handleProgress(1);
                        showSnackbarError((String) responseApi.data);
                    }
                    updateDialogUi();
                    break;


                default:
                    handleProgress(1);
                    showSnackbarError(getString(R.string.default_error));
                    updateDialogUi();

                    break;
            }

        });
    }

    private void updateUIKYC(KYCResListModel kycResListModel) {
        int count = 0;
        for (int i = 0; i < kycResListModel.getList().size(); i++) {
            count++;
            KYCResItemModel model = kycResListModel.getList().get(i);
            /*if (!model.getError() && model.getId_name().equalsIgnoreCase(AppConstant.TeacherKycParam.SCHOOL_ID_CARD)) {

                kycDocumentDatamodelArrayList.get(i).setModify(true);
            } else {
                kycDocumentDatamodelArrayList.get(i).setModify(false);
            }*/

            AppLogger.e("chhonker- ",""+model.getError());
            AppLogger.e("chhonker- ",model.getId_name());
            if (!model.getError() && model.getId_name().equalsIgnoreCase(AppConstant.TeacherKycParam.SCHOOL_ID_CARD)) {
                if (AppUtil.myClassRoomResModel != null ) {
                    AppLogger.e("chhonker 1- ",model.getId_name());
                    AppUtil.myClassRoomResModel.setSchool_id_card(model.getUrl());
                }
            } else if (!model.getError() && model.getId_name().equalsIgnoreCase(AppConstant.TeacherKycParam.GOVT_ID_FRONT)) {
                if (AppUtil.myClassRoomResModel != null) {
                    AppLogger.e("chhonker 2- ",model.getId_name());
                    AppUtil.myClassRoomResModel.setGovt_id_front(model.getUrl());
                }
            } else if (!model.getError() && model.getId_name().equalsIgnoreCase(AppConstant.TeacherKycParam.GOVT_ID_BACK)) {
                if (AppUtil.myClassRoomResModel != null) {
                    AppLogger.e("chhonker 3- ",model.getId_name());
                    AppUtil.myClassRoomResModel.setGovt_id_back(model.getUrl());
                }
            }else if (!model.getError() && model.getId_name().equalsIgnoreCase(AppConstant.TeacherKycParam.TEACHER_PHOTO)) {
                if (AppUtil.myClassRoomResModel != null) {
                    AppLogger.e("chhonker 4- ",model.getUrl());
                    AppUtil.myClassRoomResModel.setTeacher_photo(model.getUrl());
                    AppLogger.e("chhonker 5- ", AppUtil.myClassRoomResModel.getTeacher_photo());
                }
            }
        }



        AppLogger.e("chhonker 6- ", AppUtil.myClassRoomResModel.getTeacher_photo());
        kycDocumentDatamodelArrayList = viewModel.teacherUseCase.makeAdapterDocumentList();
        mteacherKycDocumentAdapter.updateList(kycDocumentDatamodelArrayList);
        if (count == 4) {
            binding.buttonLayout.setVisibility(View.INVISIBLE);
        }

    }

    public void handleProgress(int status) {
        switch (status) {
            case 0:
                binding.button.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.button.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.setListingActiveFragment(HomeActivity.TEACHER_KYC_FRAGMENT);
        // resources = ViewUtil.getCustomResource(getActivity());
        init();
        setDataOnUI();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_teacher_kyc;
    }

    public void setAdapter() {
        kycDocumentDatamodelArrayList = viewModel.teacherUseCase.makeAdapterDocumentList();
        binding.rvDoucumentUpload.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvDoucumentUpload.setHasFixedSize(true);
        binding.rvDoucumentUpload.setNestedScrollingEnabled(false);
        mteacherKycDocumentAdapter = new TeacherKycDocumentAdapter(kycDocumentDatamodelArrayList, this);
        binding.rvDoucumentUpload.setAdapter(mteacherKycDocumentAdapter);
        int count = 0;
        for (KYCDocumentDatamodel kycDocumentDatamodel : kycDocumentDatamodelArrayList) {
            if (kycDocumentDatamodel.isModify()) {
                count++;
            }
        }
        if (count == 4) {
            binding.buttonLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void setDataOnUI() {
        if (AppUtil.myClassRoomResModel != null && AppUtil.myClassRoomResModel != null) {
            if (AppUtil.myClassRoomResModel.getScoreTotal() != null && !TextUtil.isEmpty(String.valueOf(AppUtil.myClassRoomResModel.getScoreTotal()))) {
                binding.points.setText("" + AppUtil.myClassRoomResModel.getScoreTotal());
            } else {
                binding.points.setText("0");
            }

            if (!TextUtil.isEmpty(String.valueOf(AppUtil.myClassRoomResModel.getWalletBalance()))) {
                binding.walletBal.setText(" " + AuroApp.getAppContext().getResources().getString(R.string.rs) + AppUtil.myClassRoomResModel.getWalletBalance());
            } else {
                binding.walletBal.setText("0");
            }
        }
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case DOCUMENT_CLICK:
                if (!uploadBtnStatus) {
                    onUploadDocClick(commonDataModel);
                }
                break;
        }
    }

    public void openActivity() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);
    }


    private void showSnackbarError(String message) {
        uploadBtnStatus = false;
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    private void onUploadDocClick(CommonDataModel commonDataModel) {
        pos = commonDataModel.getSource();
        KYCDocumentDatamodel kycDocumentDatamodel = (KYCDocumentDatamodel) commonDataModel.getObject();
        if (kycDocumentDatamodel.getDocumentId() == AppConstant.DocumentType.UPLOAD_YOUR_PHOTO) {
            openActivity();
        } else {
            CropImages.activity()
                    .setGuidelines(CropImageViews.Guidelines.ON)
                    .start(getActivity());
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLogger.e("chhonker", "fragment requestCode=" + requestCode);
        if (requestCode == CropImages.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImages.ActivityResult result = CropImages.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    resultUri = result.getUri();
                    updateKYCList(resultUri.getPath());

                } catch (Exception e) {

                }

            } else if (resultCode == CropImages.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
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
            if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.ID_PROOF_FRONT_SIDE) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_front.jpg");
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.ID_PROOF_BACK_SIDE) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_back.jpg");
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.SCHOOL_ID_CARD) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_school.jpg");
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.UPLOAD_YOUR_PHOTO) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("profile.jpg");
            }
            kycDocumentDatamodelArrayList.get(pos).setDocumentURi(Uri.parse(path));
            File file = new File(kycDocumentDatamodelArrayList.get(pos).getDocumentURi().getPath());
            InputStream is = AuroApp.getAppContext().getApplicationContext().getContentResolver().openInputStream(Uri.fromFile(file));
            kycDocumentDatamodelArrayList.get(pos).setImageBytes(viewModel.teacherUseCase.getBytes(is));
            mteacherKycDocumentAdapter.updateList(kycDocumentDatamodelArrayList);
            uploadAllDocApi();
        } catch (Exception e) {
            /*Do code here when error occur*/
        }
    }


    @Override
    public void onClick(View v) {
        if (viewModel.teacherUseCase.checkUploadButtonDoc(kycDocumentDatamodelArrayList)) {
            uploadBtnStatus = true;
            uploadAllDocApi();
        } else {
            ViewUtil.showSnackBar(binding.getRoot(), getString(R.string.document_all_four_error_msg));
        }
    }

    private void uploadAllDocApi() {
        viewModel.teacherKYCUpload(kycDocumentDatamodelArrayList);
    }


    private void openProgressDialog() {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            return;
        }
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle("Uploading...");
        customDialogModel.setTwoButtonRequired(true);
        customProgressDialog = new CustomProgressDialog(customDialogModel);
        Objects.requireNonNull(customProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.setCallcack(new CustomProgressDialog.ButtonClickCallback() {
            @Override
            public void retryCallback() {
                uploadAllDocApi();
                customProgressDialog.updateUI(1);
            }

            @Override
            public void closeCallback() {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName(getActivity().getResources().getString(R.string.no_file_chosen));
                mteacherKycDocumentAdapter.updateList(kycDocumentDatamodelArrayList);
            }
        });
        customProgressDialog.show();
    }


    public void updateDialogUi() {
        if (customProgressDialog != null) {
            customProgressDialog.updateUI(0);
        }

    }

    public void closeDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
        }
    }
}
