package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.KycFragmentLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.CustomSnackBarModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCResListModel;
import com.auro.scholr.home.presentation.view.adapter.KYCViewDocAdapter;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import com.auro.scholr.util.alert_dialog.CustomSnackBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.AZURE_API;
import static com.auro.scholr.core.common.Status.UPLOAD_PROFILE_IMAGE;


public class KYCViewFragment extends BaseFragment implements View.OnClickListener {

    @Inject
    @Named("KYCViewFragment")
    ViewModelFactory viewModelFactory;


    KycFragmentLayoutBinding binding;
    KYCViewModel kycViewModel;
    KYCViewDocAdapter kycViewDocAdapter;

    private DashboardResModel dashboardResModel;
    ArrayList<KYCDocumentDatamodel> kycDocumentDatamodelArrayList;
    Resources resources;

    /*Face Image Params*/
    List<AssignmentReqModel> faceModelList;
    int faceCounter = 0;


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
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }

    public void setAdapter() {
        this.kycDocumentDatamodelArrayList = kycViewModel.homeUseCase.makeAdapterDocumentList(dashboardResModel);
        binding.documentUploadRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.documentUploadRecyclerview.setHasFixedSize(true);
        kycViewDocAdapter = new KYCViewDocAdapter(getActivity(), kycViewModel.homeUseCase.makeAdapterDocumentList(dashboardResModel));
        binding.documentUploadRecyclerview.setAdapter(kycViewDocAdapter);
    }


    @Override
    protected void init() {
        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }

        binding.btUploadAll.setVisibility(View.GONE);
        binding.btModifyAll.setVisibility(View.VISIBLE);

        setDataOnUi();
    }

    private void setDataOnUi() {
        if (dashboardResModel != null) {
            if (!TextUtil.isEmpty(dashboardResModel.getWalletbalance())) {
                //binding.walletBalText.setText(getString(R.string.rs) + " " + dashboardResModel.getWalletbalance());
                binding.walletBalText.setText(getString(R.string.rs) + " " + kycViewModel.homeUseCase.getWalletBalance(dashboardResModel));
            }
            setDataStepsOfVerifications();
        }
        binding.cambridgeHeading.cambridgeHeading.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.white));

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
        binding.btModifyAll.setOnClickListener(this);
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
        setKeyListner();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_modify_all) {
            openKYCFragment();
        } else if (v.getId() == R.id.lang_eng) {
            String text = binding.toolbarLayout.langEng.getText().toString();
            if (!TextUtil.isEmpty(text) && text.equalsIgnoreCase(AppConstant.HINDI)) {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_HI);
               // resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.ENGLISH);
            } else {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
                //resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.HINDI);
            }
            reloadFragment();
        } else if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
            AppLogger.e("handleback","backlisner");

        } else if (v.getId() == R.id.bt_transfer_money) {
            //callNumber();
            openSendMoneyFragment();
        } else if (v.getId() == R.id.wallet_info) {
            openTransactionFragment();
        }
    }

    public void openSendMoneyFragment( ) {
       // getActivity().getSupportFragmentManager().popBackStack();
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
        openFragment(fragment);
    }


    public void callNumber() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:9667480783"));
        startActivity(callIntent);
    }


    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    public void openKYCFragment() {
        dashboardResModel.setModify(true);
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager().popBackStack();
        openFragment(kycFragment);
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, KYCViewFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // CustomSnackBar.INSTANCE.dismissCartSnackbar();
    }

    private void setDataStepsOfVerifications() {
       /* dashboardResModel.setIs_kyc_uploaded("Yes");
        dashboardResModel.setIs_kyc_verified("Rejected");
        dashboardResModel.setIs_payment_lastmonth("Yes");*/
        //  dashboardResModel.setIs_kyc_verified(AppConstant.DocumentType.YES);
        //  dashboardResModel.setApproved_scholarship_money("50");

        if (dashboardResModel.getIs_kyc_uploaded().equalsIgnoreCase(AppConstant.DocumentType.YES)) {
            binding.stepOne.tickSign.setVisibility(View.VISIBLE);
            binding.stepOne.textUploadDocumentMsg.setText(R.string.document_uploaded);
            binding.stepOne.textUploadDocumentMsg.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.ufo_green));
            if (dashboardResModel.getIs_kyc_verified().equalsIgnoreCase(AppConstant.DocumentType.IN_PROCESS)) {
                binding.stepTwo.textVerifyMsg.setText(getString(R.string.verification_is_in_process));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
            } else if (dashboardResModel.getIs_kyc_verified().equalsIgnoreCase(AppConstant.DocumentType.APPROVE)) {
                binding.stepTwo.textVerifyMsg.setText(R.string.document_verified);
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setVisibility(View.VISIBLE);
                binding.stepTwo.textVerifyMsg.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.ufo_green));
                int approvedMoney = ConversionUtil.INSTANCE.convertStringToInteger(dashboardResModel.getApproved_scholarship_money());
                if (approvedMoney < 1) {
                   /* binding.stepThree.tickSign.setVisibility(View.VISIBLE);
                    binding.stepThree.textQuizVerifyMsg.setText(AuroApp.getAppContext().getResources().getString(R.string.scholarship_approved));

                    binding.stepFour.textTransferMsg.setText(R.string.successfully_transfered);
                    binding.stepFour.textTransferMsg.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.ufo_green));
                    binding.stepFour.tickSign.setVisibility(View.GONE);
                    binding.stepFour.textTransferMsg.setVisibility(View.VISIBLE);
                    binding.stepFour.btTransferMoney.setVisibility(View.GONE);*/
                } else {
                    binding.stepThree.tickSign.setVisibility(View.VISIBLE);
                    binding.stepThree.textQuizVerifyMsg.setText(AuroApp.getAppContext().getResources().getString(R.string.scholarship_approved));
                    binding.stepThree.textQuizVerifyMsg.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.ufo_green));

                    binding.stepFour.textTransferMsg.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.ufo_green));
                    binding.stepFour.textTransferMsg.setText(R.string.call_our_customercare);
                    binding.stepFour.textTransferMsg.setVisibility(View.GONE);
                    binding.stepFour.tickSign.setVisibility(View.VISIBLE);
                    binding.stepFour.btTransferMoney.setVisibility(View.VISIBLE);
                    binding.stepFour.btTransferMoney.setOnClickListener(this);
                }

            } else if (dashboardResModel.getIs_kyc_verified().equalsIgnoreCase(AppConstant.DocumentType.REJECTED)) {
                binding.stepTwo.textVerifyMsg.setText(R.string.declined);
                binding.stepTwo.textVerifyMsg.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.color_red));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setBackground(AuroApp.getAppContext().getResources().getDrawable(R.drawable.ic_cancel_icon));
                binding.stepFour.textTransferMsg.setTextColor(ContextCompat.getColor(getActivity(),R.color.auro_dark_blue));
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



    private void observeServiceResponse() {
        kycViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {
                case LOADING:
                    /*Do handling in background*/
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == AZURE_API) {
                        sendFaceImageOnServer();
                    }

                    break;

                case NO_INTERNET:
                case AUTH_FAIL:
                case FAIL_400:
                default:
                    updateFaceListInPref();
                    break;

            }

        });
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
        if (prefModel != null) {
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

}
