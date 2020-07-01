package com.auro.scholr.home.presentation.view.fragment;

import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FragmentTransactionsBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.presentation.view.adapter.LeaderBoardAdapter;
import com.auro.scholr.home.presentation.view.adapter.MontlyWiseAdapter;
import com.auro.scholr.home.presentation.viewmodel.FriendsLeaderShipViewModel;
import com.auro.scholr.home.presentation.viewmodel.TransactionsViewModel;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import javax.inject.Inject;
import javax.inject.Named;


public class TransactionsFragment extends BaseFragment implements View.OnClickListener {


    @Inject
    @Named("TransactionsFragment")
    ViewModelFactory viewModelFactory;
    FragmentTransactionsBinding binding;
    TransactionsViewModel viewModel;
    MontlyWiseAdapter leaderBoardAdapter;
    Resources resources;
    DashboardResModel dashboardResModel;


    public TransactionsFragment() {
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
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_transactions, container, false);

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionsViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        setToolbar();
        setListener();
    }

    @Override
    protected void init() {

        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }
        setListener();
        setTransationsBoard();

        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel.getUserLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            setLanguageText(AppConstant.HINDI);
        } else {
            setLanguageText(AppConstant.ENGLISH);
        }

        setData1OnUI();
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.headerParent.cambridgeHeading.setVisibility(View.VISIBLE);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_transactions;

    }

    public void setTransationsBoard() {
        binding.monthlyWiseList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.monthlyWiseList.setHasFixedSize(true);
        binding.monthlyWiseList.setNestedScrollingEnabled(false);
        leaderBoardAdapter = new MontlyWiseAdapter(viewModel.homeUseCase.makeListForMonthlyScholarShip());
        binding.monthlyWiseList.setAdapter(leaderBoardAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
        } else if (v.getId() == R.id.lang_eng) {
            changeLanguage();
        }
    }


    private void changeLanguage() {
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
    }

    private void setLanguageText(String text) {
        binding.toolbarLayout.langEng.setText(text);
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private void setData1OnUI() {
        String rs = getActivity().getResources().getString(R.string.rs);
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getApproved_scholarship_money())) {
            binding.amountTrajection.txtAmountApproved.setText(rs + dashboardResModel.getApproved_scholarship_money());
        } else {
            binding.amountTrajection.txtAmountApproved.setText(rs + "0");
        }

        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getDisapproved_scholarship_money())) {
            binding.amountTrajection.txtAmountRejected.setText(rs + dashboardResModel.getDisapproved_scholarship_money());
        } else {
            binding.amountTrajection.txtAmountRejected.setText(rs + "0");
        }

        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getUnapproved_scholarship_money())) {
            binding.amountTrajection.txtAmountVerification.setText(rs + dashboardResModel.getUnapproved_scholarship_money());
        } else {
            binding.amountTrajection.txtAmountVerification.setText(rs + "0");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setKeyListner();
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
