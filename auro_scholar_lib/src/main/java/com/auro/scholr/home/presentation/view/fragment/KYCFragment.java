package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.auro.scholr.databinding.KycFragmentLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.data.model.KYCResItemModel;
import com.auro.scholr.home.data.model.KYCResListModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.adapter.KYCuploadAdapter;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;
import com.auro.scholr.util.cropper.CropImages;
import com.auro.scholr.util.cropper.CropImageViews;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.AZURE_API;
import static com.auro.scholr.core.common.Status.UPLOAD_PROFILE_IMAGE;


public class KYCFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("KYCFragment")
    ViewModelFactory viewModelFactory;
    String TAG = "KYCFragment";
    KycFragmentLayoutBinding binding;
    KYCViewModel kycViewModel;
    KYCuploadAdapter kyCuploadAdapter;
    private int pos;
    private DashboardResModel dashboardResModel;
    ArrayList<KYCDocumentDatamodel> kycDocumentDatamodelArrayList;
    private boolean uploadBtnStatus;
    Resources resources;
    KYCInputModel kycInputModel = new KYCInputModel();
    CustomProgressDialog customProgressDialog;


    /*Face Image Params*/
    List<AssignmentReqModel> faceModelList;
    int faceCounter = 0;
    Uri resultUri;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        kycViewModel = ViewModelProviders.of(this, viewModelFactory).get(KYCViewModel.class);
        binding.setLifecycleOwner(this);

        ViewUtil.setLanguageonUi(getActivity());
        setRetainInstance(true);
        return binding.getRoot();
    }

    public void setAdapter() {
        this.kycDocumentDatamodelArrayList = kycViewModel.homeUseCase.makeAdapterDocumentList(dashboardResModel);
        binding.documentUploadRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.documentUploadRecyclerview.setHasFixedSize(true);
        kyCuploadAdapter = new KYCuploadAdapter(getActivity(), kycViewModel.homeUseCase.makeAdapterDocumentList(dashboardResModel), this);
        binding.documentUploadRecyclerview.setAdapter(kyCuploadAdapter);
    }


    @Override
    protected void init() {
        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }

        setDataOnUi();

    }

    private void setDataOnUi() {
        if (dashboardResModel != null) {
            setDataStepsOfVerifications();
            if (!TextUtil.isEmpty(dashboardResModel.getWalletbalance())) {
                // binding.walletBalText.setText(AuroApp.getAppContext().getResources().getString(R.string.rs) + " " + dashboardResModel.getWalletbalance());
                binding.walletBalText.setText(AuroApp.getAppContext().getResources().getString(R.string.rs) + " " + kycViewModel.homeUseCase.getWalletBalance(dashboardResModel));
            }

        }
        binding.cambridgeHeading.cambridgeHeading.setTextColor(getResources().getColor(R.color.white));
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel.getUserLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            setLanguageText(AppConstant.HINDI);
        } else {
            setLanguageText(AppConstant.ENGLISH);
        }
    }


    private void setLanguageText(String text) {
        binding.toolbarLayout.langEng.setText(text);
    }


    @Override
    protected void setToolbar() {
        /*Do code here*/
    }

    @Override
    protected void setListener() {
        /*Do code here*/
        binding.toolbarLayout.backArrow.setVisibility(View.VISIBLE);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        //  binding.btUploadAll.setOnClickListener(this);
        binding.walletInfo.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);
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

        /*Check for face image is Exist Or Not*/
        checkForFaceImage();
    }

    private void checkForFaceImage() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && !TextUtil.checkListIsEmpty(prefModel.getListAzureImageList()) && prefModel.getListAzureImageList().size() > 0) {
            faceModelList = prefModel.getListAzureImageList();
            if (faceModelList.get(0) != null) {
                kycViewModel.sendAzureImageData(faceModelList.get(0));
            }
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

        setKeyListner();
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

        switch (commonDataModel.getClickType()) {
            case KYC_BUTTON_CLICK:
                if (!uploadBtnStatus) {
                    onUploadDocClick(commonDataModel);
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
            if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.ID_PROOF_FRONT_SIDE) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_front.jpg");
                processImageData(true);
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.ID_PROOF_BACK_SIDE) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_back.jpg");
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.SCHOOL_ID_CARD) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("id_school.jpg");
                processImageData(false);
            } else if (kycDocumentDatamodelArrayList.get(pos).getDocumentId() == AppConstant.DocumentType.UPLOAD_YOUR_PHOTO) {
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName("profile.jpg");
            }

            kycDocumentDatamodelArrayList.get(pos).setDocumentURi(Uri.parse(path));
            File file = new File(kycDocumentDatamodelArrayList.get(pos).getDocumentURi().getPath());
            InputStream is = AuroApp.getAppContext().getApplicationContext().getContentResolver().openInputStream(Uri.fromFile(file));


            Bitmap picBitmap = BitmapFactory.decodeFile(path);
            byte[] bytes = kycViewModel.encodeToBase64(picBitmap, 100);
            long mb = kycViewModel.bytesIntoHumanReadable(bytes.length);
            if (mb > 1.5) {
                AppLogger.e("chhonker", "size of the image greater than 1.5 mb -" + mb);
                kycDocumentDatamodelArrayList.get(pos).setImageBytes(kycViewModel.encodeToBase64(picBitmap, 50));
                AppLogger.e("chhonker", "size of the image greater than 1.5 mb after conversion -" + kycViewModel.bytesIntoHumanReadable(kycDocumentDatamodelArrayList.get(pos).getImageBytes().length));
            } else {
                AppLogger.e("chhonker", "size of the image less 1.5 mb -" + mb);
                kycDocumentDatamodelArrayList.get(pos).setImageBytes(bytes);
            }
            kyCuploadAdapter.updateList(kycDocumentDatamodelArrayList);
            uploadAllDocApi();
        } catch (Exception e) {
            /*Do code here when error occur*/
        }
    }


    private void processImageData(boolean status) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
            kycViewModel.homeUseCase.getTextFromImage(getActivity(), bitmap, kycInputModel, status);
        } catch (Exception e) {

        }
    }


    private void observeServiceResponse() {
        kycViewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {
                case LOADING:
                    if (responseApi.apiTypeStatus == UPLOAD_PROFILE_IMAGE) {
                        openProgressDialog();
                    } else {
                        progressBarHandling(0);
                    }
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == UPLOAD_PROFILE_IMAGE) {
                        closeDialog();
                        KYCResListModel kycResListModel = (KYCResListModel) responseApi.data;
                        if (!kycResListModel.isError()) {
                            updateListonResponse(kycResListModel);

                            uploadBtnStatus = false;
                        } else {
                            showError(kycResListModel.getMessage());
                            progressBarHandling(1);
                        }
                    } else if (responseApi.apiTypeStatus == AZURE_API) {
                        sendFaceImageOnServer();
                    }

                    break;

                case NO_INTERNET:
                case AUTH_FAIL:
                case FAIL_400:
                    if (responseApi.apiTypeStatus == UPLOAD_PROFILE_IMAGE) {
                        updateDialogUi();
                    } else {
                        showError((String) responseApi.data);
                        progressBarHandling(1);
                        updateFaceListInPref();
                    }
                    break;

                default:
                    if (responseApi.apiTypeStatus == UPLOAD_PROFILE_IMAGE) {
                        updateDialogUi();
                    } else {
                        showError((String) responseApi.data);
                        progressBarHandling(1);
                        updateFaceListInPref();
                    }
                    break;
            }

        });
    }

    private void progressBarHandling(int status) {
        if (status == 0) {
            binding.btUploadAll.setText("");
            binding.btUploadAll.setEnabled(false);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else if (status == 1) {
            binding.btUploadAll.setText(getActivity().getResources().getString(R.string.upload));
            binding.btUploadAll.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);
            if (kycViewModel.homeUseCase.checkUploadButtonStatus(kycDocumentDatamodelArrayList)) {
                binding.buttonUploadLayout.setVisibility(View.INVISIBLE);
            } else {
                binding.buttonUploadLayout.setVisibility(View.INVISIBLE);
            }
        }

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
            kycDocumentDatamodelArrayList.get(pos).setModify(false);
            kycDocumentDatamodelArrayList.get(pos).setDocumentstatus(true);
            kycDocumentDatamodelArrayList.get(pos).setDocumentUrl(resItemModel.getUrl());
        } else {
            kycDocumentDatamodelArrayList.get(pos).setModify(true);
            kycDocumentDatamodelArrayList.get(pos).setDocumentstatus(false);
            kycDocumentDatamodelArrayList.get(pos).setDocumentUrl("");
        }
        kyCuploadAdapter.updateList(kycDocumentDatamodelArrayList);
        progressBarHandling(1);

    }

    private void showError(String message) {
        uploadBtnStatus = false;
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_upload_all) {
            if (kycViewModel.homeUseCase.checkUploadButtonDoc(kycDocumentDatamodelArrayList)) {
                uploadBtnStatus = true;
                uploadAllDocApi();
            } else {
                ViewUtil.showSnackBar(binding.getRoot(), getActivity().getResources().getString(R.string.document_all_four_error_msg));
            }
        } else if (v.getId() == R.id.lang_eng) {
            String text = binding.toolbarLayout.langEng.getText().toString();
            if (!TextUtil.isEmpty(text) && text.equalsIgnoreCase(AppConstant.HINDI)) {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_HI);
                //   resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.ENGLISH);
            } else {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
                //resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.HINDI);
            }
            reloadFragment();
        }  else if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
            AppLogger.e("handleback","backlisner");

        } else if (v.getId() == R.id.bt_transfer_money) {
            openSendMoneyFragment();
            //callNumber();
        } else if (v.getId() == R.id.wallet_info) {
            openTransactionFragment();
        }

    }


    public void callNumber() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:9667480783"));
        startActivity(callIntent);
    }

    public void openSendMoneyFragment() {
        Bundle bundle = new Bundle();
        SendMoneyFragment sendMoneyFragment = new SendMoneyFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        sendMoneyFragment.setArguments(bundle);
        openFragment(sendMoneyFragment);
    }


    private void openTransactionFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        TransactionsFragment fragment = new TransactionsFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, QuizHomeFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
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


    private void uploadAllDocApi() {
        kycInputModel.setUser_phone(dashboardResModel.getPhonenumber());
        kycViewModel.uploadProfileImage(kycDocumentDatamodelArrayList, kycInputModel);
    }


    public void openKYCFragment() {
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);
        openFragment(kycFragment);
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, QuizHomeFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    private void setDataStepsOfVerifications() {
       /* dashboardResModel.setIs_kyc_uploaded("Yes");
        dashboardResModel.setIs_kyc_verified("Rejected");
        dashboardResModel.setIs_payment_lastmonth("Yes");*/

        AppLogger.e("chhonker step ","kyc Step 1");
        if (dashboardResModel == null) {
            return;
        }
     /*   dashboardResModel.setIs_kyc_verified(AppConstant.DocumentType.YES);
        dashboardResModel.setApproved_scholarship_money("50");
*/
        if (!TextUtil.isEmpty(dashboardResModel.getIs_kyc_uploaded()) && dashboardResModel.getIs_kyc_uploaded().equalsIgnoreCase(AppConstant.DocumentType.YES)) {
            AppLogger.e("chhonker step ","kyc Step 2");
            binding.stepOne.tickSign.setVisibility(View.VISIBLE);
            binding.stepOne.textUploadDocumentMsg.setText(R.string.document_uploaded);
            binding.stepOne.textUploadDocumentMsg.setTextColor(ContextCompat.getColor(getActivity(), R.color.ufo_green));
            if (!TextUtil.isEmpty(dashboardResModel.getIs_kyc_verified()) && dashboardResModel.getIs_kyc_verified().equalsIgnoreCase(AppConstant.DocumentType.IN_PROCESS)) {
                AppLogger.e("chhonker step ","kyc Step 3");
                binding.stepTwo.textVerifyMsg.setText(getString(R.string.verification_is_in_process));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
            } else if (!TextUtil.isEmpty(dashboardResModel.getIs_kyc_verified()) &&
                    dashboardResModel.getIs_kyc_verified().equalsIgnoreCase(AppConstant.DocumentType.APPROVE)) {
                AppLogger.e("chhonker step ","kyc Step 4");
                binding.stepTwo.textVerifyMsg.setText(R.string.document_verified);
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setVisibility(View.VISIBLE);
                binding.stepTwo.textVerifyMsg.setTextColor(ContextCompat.getColor(getActivity(), R.color.ufo_green));
                int approvedMoney = ConversionUtil.INSTANCE.convertStringToInteger(dashboardResModel.getApproved_scholarship_money());
                if (approvedMoney < 1) {
                    AppLogger.e("chhonker step ","kyc Step 5");
                  /*  binding.stepThree.tickSign.setVisibility(View.GONE);
                    binding.stepThree.textQuizVerifyMsg.setText(AuroApp.getAppContext().getResources().getString(R.string.scholarship_approved));
                    binding.stepFour.textTransferMsg.setText(R.string.successfully_transfered);
                    binding.stepFour.textTransferMsg.setTextColor(ContextCompat.getColor(getActivity(), R.color.ufo_green));
                    binding.stepFour.tickSign.setVisibility(View.GONE);
                    binding.stepFour.btTransferMoney.setVisibility(View.GONE);*/
                } else {
                    AppLogger.e("chhonker step ","kyc Step 6");
                    binding.stepThree.tickSign.setVisibility(View.VISIBLE);
                    binding.stepThree.textQuizVerifyMsg.setText(AuroApp.getAppContext().getResources().getString(R.string.scholarship_approved));
                    binding.stepFour.textTransferMsg.setTextColor(ContextCompat.getColor(getActivity(), R.color.ufo_green));
                    binding.stepFour.textTransferMsg.setText(R.string.call_our_customercare);
                    binding.stepFour.tickSign.setVisibility(View.VISIBLE);
                    binding.stepFour.btTransferMoney.setVisibility(View.VISIBLE);
                    binding.stepFour.btTransferMoney.setOnClickListener(this);
                }
            } else if (!TextUtil.isEmpty(dashboardResModel.getIs_kyc_verified()) && dashboardResModel.getIs_kyc_verified().equalsIgnoreCase(AppConstant.DocumentType.REJECTED)) {
                AppLogger.e("chhonker step ","kyc Step 7");
                binding.stepTwo.textVerifyMsg.setText(R.string.declined);
                binding.stepTwo.textVerifyMsg.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_red));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_cancel_icon));
                binding.stepFour.textTransferMsg.setTextColor(ContextCompat.getColor(getActivity(), R.color.auro_dark_blue));
                binding.stepFour.textTransferMsg.setText(R.string.you_will_see_transfer);
                binding.stepFour.btTransferMoney.setVisibility(View.GONE);
                binding.stepFour.tickSign.setVisibility(View.GONE);
            } else if (!TextUtil.isEmpty(dashboardResModel.getIs_kyc_verified()) && dashboardResModel.getIs_kyc_verified().equalsIgnoreCase(AppConstant.DocumentType.PENDING)) {
                AppLogger.e("chhonker step ","kyc Step 7");
                binding.stepTwo.textVerifyMsg.setText(getString(R.string.verification_pending));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
            }
        }
    }


    private void sendFaceImageOnServer() {
        if (!TextUtil.checkListIsEmpty(faceModelList)) {
            faceModelList.get(faceCounter).setUploaded(true);
            faceCounter++;
            if (faceModelList.size() > faceCounter) {
                kycViewModel.sendAzureImageData(faceModelList.get(faceCounter));
            } else {
                updateFaceListInPref();
            }
        }
    }

    private void updateFaceListInPref() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && !TextUtil.checkListIsEmpty(faceModelList)) {
            List<AssignmentReqModel> newList = new ArrayList<>();
            for (AssignmentReqModel model : faceModelList) {
                if (model != null && !model.isUploaded()) {
                    newList.add(model);
                }
            }
            prefModel.setListAzureImageList(newList);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    private void setKeyListner() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
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
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName(AuroApp.getAppContext().getResources().getString(R.string.no_file_chosen));
                kyCuploadAdapter.updateList(kycDocumentDatamodelArrayList);
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
