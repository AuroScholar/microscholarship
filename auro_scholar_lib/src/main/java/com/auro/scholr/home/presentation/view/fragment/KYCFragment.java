package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.auro.scholr.home.data.model.CustomSnackBarModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCResItemModel;
import com.auro.scholr.home.data.model.KYCResListModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.adapter.KYCuploadAdapter;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.payment.presentation.view.fragment.PaytmFragment;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import com.auro.scholr.util.alert_dialog.CustomSnackBar;

import com.auro.scholr.util.cropper.CropImages;
import com.auro.scholr.util.cropper.CropImageViews;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        kycViewModel = ViewModelProviders.of(this, viewModelFactory).get(KYCViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setKycViewModel(kycViewModel);
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
                binding.walletBalText.setText(getString(R.string.rs) + " " + dashboardResModel.getWalletbalance());
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
        binding.btUploadAll.setOnClickListener(this);
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
                    Uri resultUri = result.getUri();
                    updateKYCList(resultUri.getPath());
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    getTextFromImage(bitmap);
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
            kycDocumentDatamodelArrayList.get(pos).setImageBytes(kycViewModel.getBytes(is));

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
                        uploadBtnStatus = false;
                    }

                    break;

                case NO_INTERNET:
                case AUTH_FAIL:
                case FAIL_400:
                    showError((String) responseApi.data);
                    progressBarHandling(1);
                    break;

                default:
                    showError((String) responseApi.data);
                    progressBarHandling(1);
                    break;
            }

        });
    }

    private void progressBarHandling(int status) {
        if (status == 0) {
            binding.btUploadAll.setText("");
            binding.btUploadAll.setEnabled(false);
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.btUploadAll.setText(getString(R.string.upload));
            binding.btUploadAll.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);
            if (kycViewModel.homeUseCase.checkUploadButtonStatus(kycDocumentDatamodelArrayList)) {
                binding.btUploadAll.setVisibility(View.INVISIBLE);
            } else {
                binding.btUploadAll.setVisibility(View.VISIBLE);
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
                ViewUtil.showSnackBar(binding.getRoot(), getString(R.string.document_all_four_error_msg));
            }
        } else if (v.getId() == R.id.lang_eng) {
            String text = binding.toolbarLayout.langEng.getText().toString();
            if (!TextUtil.isEmpty(text) && text.equalsIgnoreCase(AppConstant.HINDI)) {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_HI);
                resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.ENGLISH);
            } else {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
                resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.HINDI);
            }
            reloadFragment();
        } else if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
        }else if(v.getId()== R.id.bt_transfer_money)
        {
            openFragment(new SendMoneyFragment());
        }

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
        kycViewModel.uploadProfileImage(kycDocumentDatamodelArrayList, dashboardResModel.getPhonenumber());

    }


    public void openKYCFragment() {
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);
        openFragment(kycFragment);
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().popBackStack();
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

    private void getTextFromImage(Bitmap bitmap) {
        TextRecognizer txtRecognizer = new TextRecognizer.Builder(getActivity()).build();
        if (!txtRecognizer.isOperational()) {
            // Shows if your Google Play services is not up to date or OCR is not supported for the device
            AppLogger.e(TAG, "Detector dependencies are not yet available");
        } else {
            // Set the bitmap taken to the frame to perform OCR Operations.
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray items = txtRecognizer.detect(frame);
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < items.size(); i++) {
                TextBlock item = (TextBlock) items.valueAt(i);
                strBuilder.append(item.getValue());
                strBuilder.append("\n");
                // The following Process is used to show how to use lines & elements as well
                findNameFromAadharCard(items, item);
            }
            AppLogger.e(TAG + " Final String ", strBuilder.toString());
            extractPhoneNumber(strBuilder.toString());
            validateAadharNumber(strBuilder.toString());

            for (String t : getDate(strBuilder.toString())) {
                AppLogger.e(TAG, "Date == " + t);
            }
        }
    }

    public void findNameFromAadharCard(SparseArray items, TextBlock item) {
        String name;
        for (int j = 0; j < items.size(); j++) {
            for (int k = 0; k < item.getComponents().size(); k++) {
                //extract scanned text lines here
                Text line = item.getComponents().get(k);
                AppLogger.e(TAG + " lines", line.getValue());

                String lineString = line.getValue().toLowerCase();
                if (lineString.contains("name")) {
                    String[] names = lineString.split(":");
                    name = names[1];
                    AppLogger.e(TAG, "name here:" + name);
                } else {
                    if (lineString.contains("dob")) {
                        Text dobline = item.getComponents().get(k - 1);
                        name = dobline.getValue();
                        AppLogger.e(TAG, "dob name here:" + name);
                    }
                }

            }
        }
    }

    public List<String> extractPhoneNumber(String input) {
        List<String> list = new ArrayList<>();
        Iterator<PhoneNumberMatch> existsPhone = PhoneNumberUtil.getInstance().findNumbers(input, "IN").iterator();
        while (existsPhone.hasNext()) {
            String numberOnly = existsPhone.next().number().toString().replaceAll("[^0-9]", "");
            list.add(numberOnly);
            AppLogger.e(TAG, "Phone == " + numberOnly);
        }
        return list;

    }

    public static void validateAadharNumber(String aadharNumber) {
        String[] items = aadharNumber.split("\n");
        for (String str : items) {
            str.replaceAll("\\s", "");
            String numberOnly = str.replaceAll("[^0-9]", "");
            if (numberOnly.length() == 12) {
                AppLogger.e("KYCFragment", "Aadhar number == " + numberOnly);
            }
        }

    }

    private static ArrayList<String> getDate(String desc) {
        int count = 0;
        ArrayList<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d").matcher(desc);
        while (m.find()) {
            allMatches.add(m.group());
        }

        Matcher match_second = Pattern.compile("\\d{4}-\\d{2}-\\d{2}").matcher(desc);
        while (m.find()) {
            AppLogger.e("KYCFragment", "Date == " + match_second.group(0));
            allMatches.add(match_second.group(0));
        }

        return allMatches;

    }

    private void setDataStepsOfVerifications() {
        int verifyStatus = 0;
        boolean scholarshipTansfered = false;
        if (kycViewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
            binding.stepOne.tickSign.setVisibility(View.VISIBLE);
            binding.stepOne.textUploadDocumentMsg.setText(R.string.document_uploaded);
            binding.stepOne.textUploadDocumentMsg.setTextColor(getResources().getColor(R.color.ufo_green));
            if (verifyStatus == 0) {
                binding.stepTwo.textVerifyMsg.setText(getString(R.string.verification_is_in_process));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
            } else if (verifyStatus == 1) {
                binding.stepTwo.textVerifyMsg.setText(R.string.document_verified);
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
                binding.stepTwo.textVerifyMsg.setTextColor(getResources().getColor(R.color.ufo_green));
                if (scholarshipTansfered) {
                    binding.stepThree.textTransferMsg.setText(R.string.successfully_transfered);
                    binding.stepThree.textTransferMsg.setTextColor(getResources().getColor(R.color.white));
                } else {
                    binding.stepThree.textTransferMsg.setTextColor(getResources().getColor(R.color.ufo_green));
                    binding.stepThree.textTransferMsg.setText(R.string.transfer_money_text);
                    binding.stepThree.tickSign.setVisibility(View.VISIBLE);
                    binding.stepThree.btTransferMoney.setVisibility(View.VISIBLE);
                    binding.stepThree.btTransferMoney.setOnClickListener(this);
                }
            } else if (verifyStatus == 2) {
                binding.stepTwo.textVerifyMsg.setText(R.string.declined);
                binding.stepTwo.textVerifyMsg.setTextColor(getResources().getColor(R.color.color_red));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setBackground(getResources().getDrawable(R.drawable.ic_cancel_icon));
            }
        }
    }

}
