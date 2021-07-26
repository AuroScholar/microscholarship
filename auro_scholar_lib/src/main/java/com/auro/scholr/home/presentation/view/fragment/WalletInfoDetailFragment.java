package com.auro.scholr.home.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.databinding.FragmentWalletInfoDetailBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.WalletResponseAmountResModel;
import com.auro.scholr.home.presentation.view.activity.StudentDashboardActivity;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.home.presentation.view.adapter.WalletAdapter;
import com.auro.scholr.home.presentation.viewmodel.WalletAmountViewModel;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class WalletInfoDetailFragment extends BaseFragment implements View.OnClickListener, CommonCallBackListner {

    @Inject
    @Named("WalletInfoDetailFragment")
    ViewModelFactory viewModelFactory;
    FragmentWalletInfoDetailBinding binding;
    WalletAmountViewModel viewModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DashboardResModel dashboardResModel;

    public WalletInfoDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletInfoDetailFragment.
     */
// TODO: Rename and change types and number of parameters
    public static WalletInfoDetailFragment newInstance(String param1, String param2) {
        WalletInfoDetailFragment fragment = new WalletInfoDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WalletAmountViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        init();
        setToolbar();
        setListener();
        return binding.getRoot();
        // return inflater.inflate(R.layout.fragment_wallet_info_detail, container, false);
    }

    @Override
    protected void init() {


        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }
       // AuroScholarDashBoardActivity.setListingActiveFragment(AuroScholarDashBoardActivity.TRANSACTION_FRAGMENT);




      /*  PrefModel prefModel = AuroAppPref.INSTANCE.getModelInstance();
        if (prefModel.getUserLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            setLanguageText(AppConstant.HINDI);
        } else {
            setLanguageText(AppConstant.ENGLISH);
        }*/

        ViewUtil.setProfilePic(binding.imageView6);
        setAdapter();


    }

    public List<WalletResponseAmountResModel> getListWallet() {
        String rs = getActivity().getResources().getString(R.string.rs);

/*

        int approvedValue = ConversionUtil.INSTANCE.convertStringToInteger(dashboardResModel.getApproved_scholarship_money());
        if (approvedValue > 0) {
            binding.amountTrajection.btTransferMoney.setOnClickListener(this);
            binding.amountTrajection.btTransferMoney.setVisibility(View.GONE);
        } else {
            binding.amountTrajection.btTransferMoney.setVisibility(View.GONE);
        }
*/


        WalletResponseAmountResModel model1 = new WalletResponseAmountResModel();
        model1.setText(getString(R.string.wl_amount_rejected));
        model1.setDrawable(getResources().getDrawable(R.drawable.wallet_card_reject_background));
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getDisapproved_scholarship_money())) {
            model1.setAmount(rs + dashboardResModel.getDisapproved_scholarship_money());
        } else {
            model1.setAmount(rs + "0");
        }

        WalletResponseAmountResModel model2 = new WalletResponseAmountResModel();
        model2.setText(getString(R.string.wl_verification_in_process));
        model2.setDrawable(getResources().getDrawable(R.drawable.wallet_card_process_background));
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getUnapproved_scholarship_money())) {
            model2.setAmount(rs + dashboardResModel.getUnapproved_scholarship_money());
        } else {
            model2.setAmount(rs + "0");
        }


        WalletResponseAmountResModel model3 = new WalletResponseAmountResModel();
        model3.setText(getString(R.string.wl_scholarship_amounta_approved));
        model3.setDrawable(getResources().getDrawable(R.drawable.wallet_card_approve_background));
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getApproved_scholarship_money())) {
            model3.setAmount(rs + dashboardResModel.getApproved_scholarship_money());
        } else {
            model3.setAmount(rs + "0");
        }


        WalletResponseAmountResModel model4 = new WalletResponseAmountResModel();
        model4.setText(getString(R.string.wl_payment_in_process));
        model4.setDrawable(getResources().getDrawable(R.drawable.wallet_card_payment_process_background));

        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getInProcessScholarShipMoney())) {
            model4.setAmount(rs + dashboardResModel.getInProcessScholarShipMoney());
        } else {
            model4.setAmount(rs + "0");
        }


        WalletResponseAmountResModel model5 = new WalletResponseAmountResModel();
        model5.setText(getString(R.string.wl_scholarship_disbursed));
        model5.setDrawable(getResources().getDrawable(R.drawable.wallet_card_disbursed_background));
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getDisburseScholarshipMoney())) {
            model5.setAmount(rs + dashboardResModel.getDisburseScholarshipMoney());
        } else {
            model5.setAmount(rs + "0");
        }


        List<WalletResponseAmountResModel> list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);
        return list;
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        ((StudentMainDashboardActivity)getActivity()).setListingActiveFragment(StudentMainDashboardActivity.PAYMENT_INFO_FRAGMENT);
        ((StudentMainDashboardActivity) getActivity()).loadPartnerLogo(binding.auroScholarLogo);
        // binding.headerParent.cambridgeHeading.setVisibility(View.VISIBLE);
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        binding.backButton.setOnClickListener(this);
        binding.cardView2.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_wallet_info_detail;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            getActivity().onBackPressed();
        }else if (v.getId()==  R.id.cardView2){
            ((StudentMainDashboardActivity)getActivity()).openProfileFragment();
        }
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

    }

    public void setAdapter() {
        // List<CertificateResModel> list = viewModel.homeUseCase.makeCertificateList();
        binding.RvwalletAmount.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.RvwalletAmount.setHasFixedSize(true);
        WalletAdapter kyCuploadAdapter = new WalletAdapter(getActivity(), getListWallet(), this);
        binding.RvwalletAmount.setAdapter(kyCuploadAdapter);
    }

}