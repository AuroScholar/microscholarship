package com.auro.scholr.home.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseActivity;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.core.common.OnItemClickListener;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.StudentActivityDashboardBinding;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteApi;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.viewmodel.HomeViewModel;
import com.auro.scholr.teacher.presentation.view.fragment.MyClassroomFragment;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;
import javax.inject.Named;

public class StudentDashboardActivity extends BaseActivity implements OnItemClickListener, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener, CommonCallBackListner {

    private String TAG = StudentDashboardActivity.class.getSimpleName().toString();

    @Inject
    HomeRemoteApi remoteApi;

    @Inject
    @Named("StudentDashboardActivity")
    ViewModelFactory viewModelFactory;

    private StudentActivityDashboardBinding binding;
    private Context mContext;
    private HomeViewModel viewModel;
    public static int LISTING_ACTIVE_FRAGMENT = 0;
    int backPress = 0;
    public static final int QUIZ_DASHBOARD_FRAGMENT = 1;
    public static final int PROFILE_FRAGMENT = 2;
    public static final int PASSPORT_FRAGMENT = 2;
    public static final int KYC_FRAGMENT = 2;
    public static final int KYC_VIEW_FRAGMENT = 2;
    public static final int SEND_MONEY_FRAGMENT = 2;
    public static final int CERTIFICATES_FRAGMENT = 2;
    public static final int PAYMENT_INFO_FRAGMENT = 2;
    public static final int PRIVACY_POLICY_FRAGMENT = 2;


    CommonCallBackListner commonCallBackListner;

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
        AuroApp.getAuroScholarModel().setActivity(this);
        AuroApp.context=this;
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        init();
        setListener();
    }


    @Override
    protected void init() {
        AppUtil.callBackListner = this;
        binding = DataBindingUtil.setContentView(this, getLayout());
        AuroApp.getAppComponent().doInjection(this);
        //view model and handler setup
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setLightStatusBar(this);
        if (getIntent() != null && getIntent().getParcelableExtra(AppConstant.AURO_DATA_MODEL) != null) {
            auroScholarDataModel = (AuroScholarDataModel) getIntent().getParcelableExtra(AppConstant.AURO_DATA_MODEL);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        openFragment(new QuizHomeFragment());
    }

    @Override
    protected void setListener() {

        /*set listner here*/
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.student_activity_dashboard;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    private void setLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // add LIGHT_STATUS_BAR to flag
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(this.getResources().getColor(R.color.blue_color)); // optional

        }
    }

    @Override
    public void onItemClick(int position) {


    }


    private void setText(String text) {
        popBackStack();
    }


    public void openFragment(Fragment fragment) {
        FragmentUtil.replaceFragment(this, fragment, R.id.home_container, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
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
            case QUIZ_DASHBOARD_FRAGMENT:
                finish();
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
        AppLogger.e("chhonker", "Activity requestCode=" + requestCode);
    }

    private void popBackStack() {
        backPress = 0;
        getSupportFragmentManager().popBackStack();
    }


    private void dismissApplication() {
        if (backPress == 0) {
            backPress++;
        } else {
            finish();
            //  finishAffinity();
        }
    }


    public void setHomeFragmentTab() {
        openFragment(new MyClassroomFragment());
    }


    @Override
    public void onClick(View view) {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


        return false;
    }


    public void printHashKey(Activity context) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {

                case LOADING:

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == Status.DYNAMIC_LINK_API) {

                    }
                    break;

                case FAIL:
                case NO_INTERNET:
                    AppLogger.e("Error", (String) responseApi.data);
                    break;


                default:
                    AppLogger.e("Error", (String) responseApi.data);
                    break;
            }

        });
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        AppLogger.e("chhonker-", "-commonEventListner");
        switch (commonDataModel.getClickType()) {
            case GET_TEACHER_DASHBOARD_API:

                break;
        }
    }

}
