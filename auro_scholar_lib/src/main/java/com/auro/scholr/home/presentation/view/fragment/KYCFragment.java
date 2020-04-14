package com.auro.scholr.home.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.core.util.uiwidget.others.HideBottomNavigation;
import com.auro.scholr.databinding.KycFragmentLayoutBinding;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.KYCuploadAdapter;
import com.auro.scholr.home.presentation.viewmodel.KYCViewModel;
import com.auro.scholr.util.cropper.CropImage;
import com.auro.scholr.util.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;


public class KYCFragment extends BaseFragment implements CommonCallBackListner {

    @Inject
    @Named("KYCFragment")
    ViewModelFactory viewModelFactory;


    KycFragmentLayoutBinding binding;
    KYCViewModel kycViewModel;
    KYCuploadAdapter kyCuploadAdapter;

    private Activity mActivity;
    private HideBottomNavigation hideBottomNavigation;
    private CommonDataModel commonDataModel;
    private int pos;
    ArrayList<KYCDocumentDatamodel> kycDocumentDatamodelArrayList;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        ((AuroApp) getActivity().getApplication()).getAppComponent().doInjection(this);
        kycViewModel = ViewModelProviders.of(this, viewModelFactory).get(KYCViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setKycViewModel(kycViewModel);
        ((HomeActivity) getActivity()).setCommonCallBackListener(this);
        HomeActivity.setListingActiveFragment(HomeActivity.CARD_FRAGMENT);
        setAdapter();
        return binding.getRoot();
    }

    public void setAdapter() {
        this.kycDocumentDatamodelArrayList = kycViewModel.homeUseCase.makeDummyDocumentList();
        binding.documentUploadRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.documentUploadRecyclerview.setHasFixedSize(true);
        kyCuploadAdapter = new KYCuploadAdapter(getActivity(), kycViewModel.homeUseCase.makeDummyDocumentList(), this);
        binding.documentUploadRecyclerview.setAdapter(kyCuploadAdapter);
    }


    private void getUserPref() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
    }

    @Override
    protected void init() {
        hideBottomNavigation = (HideBottomNavigation) mActivity;
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
        return R.layout.kyc_fragment_layout;
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
        hideBottomNavigation.onOpen();
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

        switch (commonDataModel.getClickType()) {
            case KYC_BUTTON_CLICK:
                pos = commonDataModel.getSource();
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity());
                break;
            case KYC_RESULT_PATH:
                String path = (String) commonDataModel.getObject();
                File file = new File(path);
                kycDocumentDatamodelArrayList.get(pos).setDocumentFileName(file.getName());
                kycDocumentDatamodelArrayList.get(pos).setButtonText(getString(R.string.upload_file));
                kyCuploadAdapter.updateList(kycDocumentDatamodelArrayList);
                break;
        }
    }
}
