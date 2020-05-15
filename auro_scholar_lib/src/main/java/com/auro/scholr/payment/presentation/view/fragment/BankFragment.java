package com.auro.scholr.payment.presentation.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.BankFragmentLayoutBinding;
import com.auro.scholr.databinding.SendMoneyFragmentLayoutBinding;
import com.auro.scholr.payment.presentation.viewmodel.SendMoneyViewModel;
import com.auro.scholr.util.ViewUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class BankFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("BankFragment")
    ViewModelFactory viewModelFactory;
    BankFragmentLayoutBinding binding;
    SendMoneyViewModel viewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SendMoneyViewModel.class);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }


    @Override
    protected void init() {
    }



    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }


    @Override
    protected int getLayout() {
        return R.layout.bank_fragment_layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {

        }
        init();
        setToolbar();
        setListener();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

    }



    @Override
    public void onClick(View v) {


    }


    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

}
