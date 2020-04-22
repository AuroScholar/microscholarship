package com.auro.scholr.home.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.core.common.OnItemClickListener;
import com.auro.scholr.databinding.ActivityDashboardBinding;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteApi;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;
import com.auro.scholr.home.presentation.viewmodel.HomeViewModel;

import com.auro.scholr.core.application.base_component.BaseActivity;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.util.AppLogger;

import javax.inject.Inject;
import javax.inject.Named;

public class HomeActivity extends BaseActivity implements OnItemClickListener, View.OnClickListener {

    @Inject
    HomeRemoteApi remoteApi;

    @Inject
    @Named("HomeActivity")
    ViewModelFactory viewModelFactory;

    private ActivityDashboardBinding binding;
    private Context mContext;
    private HomeViewModel viewModel;
    private static int LISTING_ACTIVE_FRAGMENT = 0;
    int backPress = 0;
    public static final int QUIZ_DASHBOARD_FRAGMENT = 1;
    public static final int QUIZ_DASHBOARD_WEB_FRAGMENT = 2;
    public static final int KYC_FRAGMENT = 3;
    public static final int DEMOGRAPHIC_FRAGMENT = 44;
    private String TAG = HomeActivity.class.getSimpleName();
    String memberType;
    CommonCallBackListner commonCallBackListner;
    public static int screenHeight=0;
    public static int screenWidth=0;

    AuroScholarDataModel auroScholarDataModel;


    public static int getListingActiveFragment() {
        return LISTING_ACTIVE_FRAGMENT;
    }

    public static void setListingActiveFragment(int listingActiveFragment) {
        LISTING_ACTIVE_FRAGMENT = listingActiveFragment;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        init();
        setListener();


    }


    @Override
    protected void init() {
        memberType = "Member";
        binding = DataBindingUtil.setContentView(this, getLayout());
        AuroApp.getAppComponent().doInjection(this);
        //view model and handler setup
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        mContext = HomeActivity.this;
        setLightStatusBar(this);
        if (getIntent() != null && getIntent().getParcelableExtra(AppConstant.AURO_DATA_MODEL) != null) {
            auroScholarDataModel = (AuroScholarDataModel) getIntent().getParcelableExtra(AppConstant.AURO_DATA_MODEL);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;


        setHomeFragmentTab();
    }


    @Override
    protected void setListener() {
        /*set listner here*/
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_dashboard;
    }


    private void setLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // add LIGHT_STATUS_BAR to flag
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(getColor(R.color.toolbar_pink)); // optional

        }
    }

    @Override
    public void onItemClick(int position) {


    }


    private void setText(String text) {
        popBackStack();
        binding.naviagtionContent.errorMesssage.setVisibility(View.VISIBLE);
        binding.naviagtionContent.errorMesssage.setText(text);

    }


    public void openFragment(Fragment fragment) {
        FragmentUtil.replaceFragment(mContext, fragment, R.id.home_container, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onBackPressed() {
        backStack();

    }


    private synchronized void backStack() {

        switch (LISTING_ACTIVE_FRAGMENT) {

            case QUIZ_DASHBOARD_WEB_FRAGMENT:
            case QUIZ_DASHBOARD_FRAGMENT:
                dismissApplication();

            case DEMOGRAPHIC_FRAGMENT:
            case KYC_FRAGMENT:
                popBackStack();
                break;

            default:
                popBackStack();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        AppLogger.e("chhonker","Activity requestCode="+requestCode);
    }

    private void popBackStack() {
        backPress = 0;
        getSupportFragmentManager().popBackStack();
    }


    private void dismissApplication() {
        finish();
    }


    public void setHomeFragmentTab() {
        switch (auroScholarDataModel.getScreenType()) {
            case AppConstant.ScreenType.QUIZ_DASHBOARD:
                openQuizFragment(auroScholarDataModel.getMobileNumber());
                break;
            case AppConstant.ScreenType.QUIZ_DASHBOARD_WEB:
                openScholarShipFragment(auroScholarDataModel);
                break;
            default:
//Default code here
                finish();
                break;
        }

    }


    @Override
    public void onClick(View view) {

    }


    public void openQuizFragment(String mobileNumber) {
        QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.MOBILE_NUMBER, mobileNumber);
        quizHomeFragment.setArguments(bundle);
        FragmentUtil.replaceFragment(AuroApp.getAppContext(), quizHomeFragment, R.id.home_container, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }


    public void openScholarShipFragment(AuroScholarDataModel auroScholarDataModel) {
        ScholarShipFragment scholarShipFragment = new ScholarShipFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.AURO_DATA_MODEL, auroScholarDataModel);
        scholarShipFragment.setArguments(bundle);
        FragmentUtil.replaceFragment(AuroApp.getAppContext(), scholarShipFragment, R.id.home_container, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }

}
