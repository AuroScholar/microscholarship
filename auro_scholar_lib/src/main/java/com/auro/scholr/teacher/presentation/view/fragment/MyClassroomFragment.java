package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.databinding.KycFragmentLayoutBinding;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.fragment.CameraFragment;
import com.auro.scholr.teacher.presentation.viewmodel.MyClassroomViewModel;


import javax.inject.Inject;
import javax.inject.Named;


public class MyClassroomFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("MyClassroomFragment")
    ViewModelFactory viewModelFactory;
    String TAG = "MyClassroomFragment";
    KycFragmentLayoutBinding binding;
    MyClassroomViewModel myClassroomViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        myClassroomViewModel = ViewModelProviders.of(this, viewModelFactory).get(MyClassroomViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        return binding.getRoot();
    }

    public void setAdapter() {

    }


    @Override
    protected void init() {

        setDataOnUi();

    }

    private void setDataOnUi() {

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

        /*if (myClassroomViewModel != null && myClassroomViewModel.serviceLiveData().hasObservers()) {
            myClassroomViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }*/
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
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case KYC_BUTTON_CLICK:

                break;
            case KYC_RESULT_PATH:
                /*do code here*/
                break;
        }
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {

    }
}
