package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.databinding.DemographicFragmentLayoutBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class DemographicFragment extends BaseFragment implements CommonCallBackListner {

    @Inject
    @Named("DemographicFragment")
    ViewModelFactory viewModelFactory;


    DemographicFragmentLayoutBinding binding;
    DemographicViewModel demographicViewModel;
    private int pos;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        demographicViewModel = ViewModelProviders.of(this, viewModelFactory).get(DemographicViewModel.class);
        binding.setLifecycleOwner(this);
        HomeActivity.setListingActiveFragment(HomeActivity.DEMOGRAPHIC_FRAGMENT);
        return binding.getRoot();
    }




    @Override
    protected void init() {
        // Spinner Drop down Gender
        List<String> genderLines = Arrays.asList(getResources().getStringArray(R.array.genderlist));
        spinnermethodcall(genderLines,binding.SpinnerGender);

        // Spinner Drop down schooltype
        List<String> schooltypeLines = Arrays.asList(getResources().getStringArray(R.array.schooltypelist));
        spinnermethodcall(schooltypeLines,binding.SpinnerSchoolType);

        // Spinner Drop down boardlist
        List<String> boardLines = Arrays.asList(getResources().getStringArray(R.array.boardlist));
        spinnermethodcall(boardLines,binding.SpinnerBoard);

        // Spinner Drop down language
        List<String> languageLines = Arrays.asList(getResources().getStringArray(R.array.languagelist));
        spinnermethodcall(languageLines,binding.SpinnerLanguageMedium);



    }
    public void spinnermethodcall(List<String> languageLines, AppCompatSpinner sppiner){
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languageLines);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        sppiner.setAdapter(dataAdapter);
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
