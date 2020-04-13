package com.auro.scholr.home.presentation.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.core.util.uiwidget.others.HideBottomNavigation;
import com.auro.scholr.databinding.QuizHomeLayoutBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.QuizItemAdapter;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;


public class QuizHomeFragment extends BaseFragment {

    @Inject
    @Named("QuizHomeFragment")
    ViewModelFactory viewModelFactory;


    QuizHomeLayoutBinding binding;
    QuizViewModel quizViewModel;

    private Activity mActivity;
    private HideBottomNavigation hideBottomNavigation;
    QuizItemAdapter quizItemAdapter;


    private static final String TAG = "CardFragment";


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        ((AuroApp) getActivity().getApplication()).getAppComponent().doInjection(this);
        quizViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setQuizViewModel(quizViewModel);

        HomeActivity.setListingActiveFragment(HomeActivity.CARD_FRAGMENT);
        setAdapter();

        return binding.getRoot();
    }

    private void setAdapter() {
        binding.quizTypeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quizTypeList.setHasFixedSize(true);
        quizItemAdapter = new QuizItemAdapter(this.getContext(), quizViewModel.homeUseCase.makeDummyQuizList());
        binding.quizTypeList.setAdapter(quizItemAdapter);

    }


    @Override
    protected void init() {
        hideBottomNavigation = (HideBottomNavigation) mActivity;

    }


    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }


    @Override
    protected int getLayout() {
        return R.layout.quiz_home_layout;
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


}
