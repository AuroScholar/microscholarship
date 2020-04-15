package com.auro.scholr.home.presentation.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.core.common.OnItemClickListener;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.ActivityDashboardBinding;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteApi;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;
import com.auro.scholr.home.presentation.viewmodel.HomeViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.cropper.CropImage;

import com.auro.scholr.core.application.base_component.BaseActivity;
import com.auro.scholr.core.application.di.component.ViewModelFactory;

import com.auro.scholr.util.permission.PermissionListener;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.PermissionsCallback;

import javax.inject.Inject;
import javax.inject.Named;

public class HomeActivity extends BaseActivity implements OnItemClickListener, View.OnClickListener, PermissionListener {

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


    AuroScholarDataModel auroScholarDataModel;


    /*Permission Handling*/
    PermissionResults permissionResults;
    private PermissionUtil mPermissionUtil = new PermissionUtil();
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;


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
        AuroApp.intialiseSdk(this);
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


    public interface PermissionResults {
        void onPermissionReceived(boolean result, int permissionType);

    }

    public void setResultListener(PermissionResults listener) {
        this.permissionResults = listener;
    }


    @Override
    public void permissionCallback(int requestCode) {

    }

    public void askPermission(int status) {

        switch (status) {

            case AppConstant.PermissionCode.LOCATION_PERMISSION_CODE:
                mPermissionUtil.checkLocationPermissions(this, new PermissionsCallback() {
                    @Override
                    public void onGranted(boolean newPermissionsGranted) {
                        super.onGranted(newPermissionsGranted);
                        AppLogger.d(TAG, "GPS ON PERMISSIONS");

                    }

                    @Override
                    public void onDenied() {
                        super.onDenied();
                        if (permissionResults != null) {
                            //  permissionResults.onPermissionReceived(false, AppConstant.PermissionCode.LOCATION_PERMISSION_CODE);
                        }
                    }
                }, true, AppConstant.PermissionCode.LOCATION_PERMISSION_CODE);
                break;
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AppLogger.d("PermissionUtils", "Request Code" + requestCode);

        /*  for locations persmission*/
        if (requestCode == AppConstant.PermissionCode.LOCATION_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                AppLogger.d(TAG, " Location PERMISSION_GRANTED");

            } else {
                sendPermissionCllback(false, AppConstant.PermissionCode.LOCATION_PERMISSION_CODE);
                mPermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.PermissionCode.LOCATION_PERMISSION_CODE) {
            AppLogger.d(TAG, "RESULT_CODE_GPS_SETTING");
            askPermission(AppConstant.PermissionCode.LOCATION_PERMISSION_CODE);
        } else if (requestCode == REQUEST_CHECK_SETTINGS_GPS) {
            switch (resultCode) {
                case Activity.RESULT_OK:

                    break;
                case Activity.RESULT_CANCELED:
                    sendPermissionCllback(false, AppConstant.PermissionCode.LOCATION_PERMISSION_CODE);
                    break;
                default:
                    // do nothing here
                    break;

            }

        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Uri resultUri = result.getUri();
                    sendCallBack(Status.KYC_RESULT_PATH, "" + resultUri.getPath());
                    // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    // binding.naviagtionContent.cropImageView.setImageBitmap(ImageUtil.contrast(bitmap, 50f));
                } catch (Exception e) {

                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }


    private void sendPermissionCllback(boolean result, int code) {
        if (permissionResults != null) {
            permissionResults.onPermissionReceived(result, code);
        }
    }

    public void setCommonCallBackListener(CommonCallBackListner listener) {
        this.commonCallBackListner = listener;
    }

    private void sendCallBack(Status status, Object o) {
        if (commonCallBackListner != null) {
            commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(-1, status, o));
        }
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
