package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.KycFragmentLayoutBinding;
import com.auro.scholr.home.data.model.CustomSnackBarModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.presentation.view.adapter.KYCViewDocAdapter;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import com.auro.scholr.util.alert_dialog.CustomSnackBar;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;


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
       /* CustomSnackBarModel customSnackBarModel = new CustomSnackBarModel();
        customSnackBarModel.setContext(getActivity());
        customSnackBarModel.setView(binding.getRoot());
        CustomSnackBar.INSTANCE.showCartSnackbar(customSnackBarModel);*/
    }

    private void setDataOnUi() {
        if (dashboardResModel != null) {
            if (!TextUtil.isEmpty(dashboardResModel.getWalletbalance())) {
                binding.walletBalText.setText(getString(R.string.rs) + " " + dashboardResModel.getWalletbalance());
            }
            setDataStepsOfVerifications();
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
        binding.btModifyAll.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);
        if (kycViewModel != null && kycViewModel.serviceLiveData().hasObservers()) {
            kycViewModel.serviceLiveData().removeObservers(this);
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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_modify_all) {
            openKYCFragment();
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

    public void openKYCFragment() {
        dashboardResModel.setModify(true);
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
        int verifyStatus = 1;
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
                binding.stepTwo.tickSign.setVisibility(View.VISIBLE);
                binding.stepTwo.textVerifyMsg.setTextColor(getResources().getColor(R.color.ufo_green));
                if (scholarshipTansfered) {
                    binding.stepThree.textTransferMsg.setText(R.string.successfully_transfered);
                    binding.stepThree.textTransferMsg.setTextColor(getResources().getColor(R.color.white));
                    binding.stepThree.tickSign.setVisibility(View.VISIBLE);
                } else {
                    binding.stepThree.textTransferMsg.setTextColor(getResources().getColor(R.color.ufo_green));
                    binding.stepThree.textTransferMsg.setText(R.string.transfer_money_text);
                    binding.stepThree.tickSign.setVisibility(View.VISIBLE);
                    binding.stepThree.btTransferMoney.setVisibility(View.VISIBLE);
                    binding.stepThree.btTransferMoney.setOnClickListener(this);
                  /*  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen._25sdp),(int) getResources().getDimension(R.dimen._25sdp));
                    lp.setMargins(0, 0,  0, (int) getResources().getDimension(R.dimen._10sdp));
                    lp.gravity = Gravity.CENTER_VERTICAL;
                    binding.stepThree.tickSign.setLayoutParams(lp);*/


                }
            } else if (verifyStatus == 2) {
                binding.stepTwo.textVerifyMsg.setText(R.string.declined);
                binding.stepTwo.textVerifyMsg.setTextColor(getResources().getColor(R.color.color_red));
                binding.stepTwo.textVerifyMsg.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setVisibility(View.VISIBLE);
                binding.stepTwo.tickSign.setBackground(getResources().getDrawable(R.drawable.ic_cancel_icon));


                binding.stepThree.textTransferMsg.setTextColor(getResources().getColor(R.color.white));
                binding.stepThree.textTransferMsg.setText(R.string.you_will_see_transfer);
                binding.stepThree.btTransferMoney.setVisibility(View.GONE);
                binding.stepThree.tickSign.setVisibility(View.GONE);


            }
        }
    }
}
