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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import com.auro.scholr.home.presentation.view.fragment.newDesgin.MainQuizHomeFragment;
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
    public static final int PASSPORT_FRAGMENT = 3;
    public static final int KYC_FRAGMENT = 4;
    public static final int KYC_VIEW_FRAGMENT = 5;
    public static final int SEND_MONEY_FRAGMENT = 6;
    public static final int CERTIFICATES_FRAGMENT = 7;
    public static final int PAYMENT_INFO_FRAGMENT = 8;
    public static final int PRIVACY_POLICY_FRAGMENT = 9;
    public static final int QUIZ_TEST_FRAGMENT = 10;


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
        openFragment(new MainQuizHomeFragment());

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
            case QUIZ_TEST_FRAGMENT:
                alertDialogForQuitQuiz();
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


    public void openProfileFragment() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        Bundle bundle = new Bundle();
        StudentProfileFragment studentProfile = new StudentProfileFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, prefModel.getDashboardResModel());
        bundle.putString(AppConstant.COMING_FROM, AppConstant.SENDING_DATA.STUDENT_PROFILE);
        studentProfile.setArguments(bundle);
        openFragment(studentProfile);
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


    public void alertDialogForQuitQuiz() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.quiz_exit_txt));

        String yes = "<font color='#00A1DB'>" + this.getResources().getString(R.string.yes) + "</font>";
        String no = "<font color='#00A1DB'>" + this.getResources().getString(R.string.no) + "</font>";
        // Set the alert dialog yes button click listener
        builder.setPositiveButton(Html.fromHtml(yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getSupportFragmentManager().popBackStack();
                dialog.dismiss();
            }
        });
        // Set the alert dialog no button click listener
        builder.setNegativeButton(Html.fromHtml(no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }
}