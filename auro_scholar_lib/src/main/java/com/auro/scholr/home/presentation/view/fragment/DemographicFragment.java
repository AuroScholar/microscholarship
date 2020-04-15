package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.DemographicFragmentLayoutBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.KYCuploadAdapter;
import com.auro.scholr.home.presentation.viewmodel.CardViewModel;

import javax.inject.Inject;
import javax.inject.Named;


public class DemographicFragment extends BaseFragment implements CommonCallBackListner {

    @Inject
    @Named("DemographicFragment")
    ViewModelFactory viewModelFactory;


    DemographicFragmentLayoutBinding binding;
    CardViewModel kycViewModel;
    KYCuploadAdapter kyCuploadAdapter;

    private CommonDataModel commonDataModel;
    private int pos;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        kycViewModel = ViewModelProviders.of(this, viewModelFactory).get(CardViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setCardViewModel(kycViewModel);
        HomeActivity.setListingActiveFragment(HomeActivity.DEMOGRAPHIC_FRAGMENT);
        return binding.getRoot();
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

    }

    @Override
    protected void setListener() {

    }


    @Override
    protected int getLayout() {
        return R.layout.demographic_fragment_layout;
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
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

    }
}
