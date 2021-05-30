package com.auro.scholr.home.presentation.view.activity.newDashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

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
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.ActivityStudentMainDashboardBinding;
import com.auro.scholr.databinding.StudentActivityDashboardBinding;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteApi;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.NavItemModel;
import com.auro.scholr.home.presentation.view.activity.StudentDashboardActivity;
import com.auro.scholr.home.presentation.view.adapter.DrawerListAdapter;
import com.auro.scholr.home.presentation.view.fragment.CertificateFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCViewFragment;
import com.auro.scholr.home.presentation.view.fragment.PrivacyPolicyFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.StudentProfileFragment;
import com.auro.scholr.home.presentation.view.fragment.TransactionsFragment;
import com.auro.scholr.home.presentation.view.fragment.WalletInfoDetailFragment;
import com.auro.scholr.home.presentation.viewmodel.HomeViewModel;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.teacher.presentation.view.fragment.MyClassroomFragment;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.util.ViewUtil.setLightStatusBar;

public class StudentMainDashboardActivity extends BaseActivity implements OnItemClickListener, View.OnClickListener,CommonCallBackListner{




    private String TAG = StudentDashboardActivity.class.getSimpleName().toString();

    @Inject
    HomeRemoteApi remoteApi;

    @Inject
    @Named("StudentMainDashboardActivity")
    ViewModelFactory viewModelFactory;

    ActivityStudentMainDashboardBinding binding;
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
    ActionBarDrawerToggle mDrawerToggle;
    ArrayList<NavItemModel> mNavItems = new ArrayList<NavItemModel>();
    DrawerListAdapter drawerListAdapter;
    QuizViewModel quizViewModel;
    public static int getListingActiveFragment() {
        return LISTING_ACTIVE_FRAGMENT;
    }

    public static void setListingActiveFragment(int listingActiveFragment) {
        LISTING_ACTIVE_FRAGMENT = listingActiveFragment;
    }


    DashboardResModel dashboardResModel;

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

        binding = DataBindingUtil.setContentView(this, getLayout());
        AuroApp.getAppComponent().doInjection(this);
        //view model and handler setup
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);
        setLightStatusBar(this);
       /* if (getIntent() != null && getIntent().getParcelableExtra(AppConstant.AURO_DATA_MODEL) != null) {
            auroScholarDataModel = (AuroScholarDataModel) getIntent().getParcelableExtra(AppConstant.AURO_DATA_MODEL);
        }*/
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
       // openFragment(new QuizHomeFragment());
        setDrawerItemList(0, 0);
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
        return R.layout.activity_student_main_dashboard;
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
    private void setDrawerItemList(int status, int val) {
        if (this == null) {
            return;
        }
        mNavItems.clear();

        mNavItems.add(new NavItemModel(getResources().getString(R.string.student_profile), "", R.drawable.ic_student_profile));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.passport), getResources().getString(R.string.analytics_more), R.drawable.ic_student_pass));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.kyc_verification), "", R.drawable.ic_verification));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.certificates), "", R.drawable.ic_certificate_icon));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.payment_info), "", R.drawable.ic_payment_info));

        // mNavItems.add(new NavItemModel( getActivity().getResources().getString(R.string.change_language), "", R.drawable.ic_language));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.privacy_policy), "", R.drawable.ic_policy));
        // DrawerLayout
        if (status == 0) {
            drawerListAdapter = new DrawerListAdapter(this, mNavItems);
            binding.navList.setAdapter(drawerListAdapter);
            // Drawer Item click listeners
            binding.navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    handleDrawerItemClick(position);
                }
            });
        } else {
            if (drawerListAdapter != null) {
                drawerListAdapter.udpateList(mNavItems);
            }
        }
    }

    private void handleDrawerItemClick(int position) {

        switch (position) {
            case 0:
                /*Profile*/
                openStudentFragment();
                break;

            case 1:
                /*Passport*/
                openTransactionsFragment();
                break;

            case 2:
                /*KYC Verification*/
                if (quizViewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
                    openKYCViewFragment(dashboardResModel);
                } else {
                    openKYCFragment(dashboardResModel);
                }
                break;

            case 3:
                /*Certificates*/
                openCertificateFragment();
                break;

            case 4:
                /*Payment Info*/
                openWalletAmountlistFragment();
                break;

            //  case 5:
            /*Change Grade*/
            // ((HomeActivity) getActivity()).openGradeChangeFragment(AppConstant.Source.DASHBOARD_NAVIGATION);
            // openGradeChangeFragment(AppConstant.Source.DASHBOARD_NAVIGATION);
           /* if (AuroApp.getAuroScholarModel().getSdkcallback() != null) {
                AuroApp.getAuroScholarModel().getSdkcallback().commonCallback(Status.NAV_CHANGE_GRADE_CLICK, "");
            }*/
            //  break;

            /* case 7:
             *//*Change Language*//*
               // openChangeLanguageDialog();
                break;
*/
            case 5:
                /*Privacy Policy*/
                openFragment(new PrivacyPolicyFragment());
                break;


        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);

    }

    public void openTransactionsFragment() {
        Bundle bundle = new Bundle();
        TransactionsFragment transactionsFragment = new TransactionsFragment();
       // bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        transactionsFragment.setArguments(bundle);
        openFragment(transactionsFragment);
    }
    public void openStudentFragment() {
        Bundle bundle = new Bundle();
        StudentProfileFragment studentProfile = new StudentProfileFragment();
       // bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);

        studentProfile.setArguments(bundle);
        openFragment(studentProfile);
    }

    /*For testing purpose*/
    public void openCertificateFragment() {
        Bundle bundle = new Bundle();
        CertificateFragment certificateFragment = new CertificateFragment();
        openFragment(certificateFragment);
    }


    private void openWalletAmountlistFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        WalletInfoDetailFragment fragment = new WalletInfoDetailFragment();
        fragment.setArguments(bundle);
        openFragment(fragment);
    }
    public void openKYCFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);
        openFragment(kycFragment);
    }

    public void openKYCViewFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCViewFragment kycViewFragment = new KYCViewFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycViewFragment.setArguments(bundle);
        openFragment(kycViewFragment);
    }
    public void openProfileFragment() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        Bundle bundle = new Bundle();
        StudentProfileFragment studentProfile = new StudentProfileFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, prefModel.getDashboardResModel());
        bundle.putString(AppConstant.COMING_FROM, AppConstant.SENDING_DATA.STUDENT_PROFILE);
        studentProfile.setArguments(bundle);
        openFragment(studentProfile);
    }

}