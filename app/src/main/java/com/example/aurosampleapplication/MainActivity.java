package com.example.aurosampleapplication;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.SdkCallBack;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.AuroScholarInputModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AuroScholar;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.encryption.Cryptor;
import com.example.aurosampleapplication.databinding.ActivityMainBinding;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btSdk.setOnClickListener(this);
        binding.btOpen.setOnClickListener(this);
        // binding.enterNumber.setText("8178307851");
        binding.btSdk.setVisibility(View.VISIBLE);
        // printHashKey(this);
        String jsonString = "{\"userid\":\"9999999999\",\"mode\":\"W\"}";
        String secret = "5d41402abc4b2a76b9719d911017c592";
        String tt = new Cryptor().HMAC_SHA256(secret, jsonString);
        //  AppLogger.e("chhonker",tt);
        //printDeviceInfo();
    }

    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("printHashKey", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("printHashKey", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("printHashKey", "printHashKey()", e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sdk:
               // openGenricSDK();
                 openScholarSpecificSdk();
                hideKeyboard(this);


                break;
            case R.id.bt_open:
                openTeacherSDK();
                break;

        }
    }


    public static void hideKeyboard(Context context) {
        if (context == null) {
            return;
        }
        View view = ((AppCompatActivity) context).getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /*  auroScholarDataModel.setMobileNumber("7503600686");
        auroScholarDataModel.setScholrId("880426");
        auroScholarDataModel.setStudentClass("6");*/

    private void openScholarSpecificSdk() {
        AuroScholarDataModel auroScholarDataModel = new AuroScholarDataModel();
        auroScholarDataModel.setMobileNumber("7503600686");
        auroScholarDataModel.setStudentClass("6");
        auroScholarDataModel.setScholrId("880426");
        auroScholarDataModel.setRegitrationSource(null);
        auroScholarDataModel.setShareType(null);
        auroScholarDataModel.setShareIdentity(null);
        auroScholarDataModel.setActivity(this);

        auroScholarDataModel.setSdkFragmentType(AppConstant.FragmentType.QUIZ_DASHBOARD);
        auroScholarDataModel.setFragmentContainerUiId(R.id.home_container);
        auroScholarDataModel.setEmailVerified(true);
        auroScholarDataModel.setSdkcallback(new SdkCallBack() {
            @Override
            public void callBack(String message) {
                /*Api response here*/
                AppLogger.e("Chhonker", "callback ---" + message);
            }

            @Override
            public void logOut() {
                AppLogger.e("Chhonker", "Logout");
            }

            @Override
            public void commonCallback(Status status,Object o) {
                switch (status) {
                    case BOOK_TUTOR_SESSION_CLICK:
                        /*write your code here*/
                        AppLogger.e("Chhonker", "commonCallback");
                        break;
                }
            }

        });

        openFragment(AuroScholar.openAuroDashboardFragment(auroScholarDataModel));
    }

    private void openTeacherSDK() {
        AuroScholarDataModel auroScholarDataModel = new AuroScholarDataModel();
        auroScholarDataModel.setMobileNumber("7503600686");
        auroScholarDataModel.setStudentClass("6");
        auroScholarDataModel.setScholrId("880426");
        auroScholarDataModel.setRegitrationSource("AuroScholr");
        auroScholarDataModel.setShareType("teacher");
        auroScholarDataModel.setShareIdentity("chandan Sir");
        auroScholarDataModel.setActivity(this);
        auroScholarDataModel.setReferralLink("");
        auroScholarDataModel.setEmailVerified(true);
        auroScholarDataModel.setFragmentContainerUiId(R.id.home_container);
        auroScholarDataModel.setSdkcallback(new SdkCallBack() {
            @Override
            public void callBack(String message) {
                /*Api response here*/
                AppLogger.e("Chhonker", "callback ---" + message);
            }

            @Override
            public void logOut() {
                AppLogger.e("Chhonker", "Logout");
            }

            @Override
            public void commonCallback(Status status,Object o) {
                switch (status) {
                    case BOOK_TUTOR_SESSION_CLICK:
   /*write your code here*/
                        AppLogger.e("Chhonker", "commonCallback");
                        break;
                }
            }
        });


        AuroScholar.startTeacherSDK(auroScholarDataModel);
        //   openFragment(AuroScholar.startAuroSDK(inputModel));
    }

    /*inputModel.setMobileNumber("8700808003");
            inputModel.setStudentClass("11");*/
    private void openGenricSDK() {
        AuroScholarInputModel inputModel = new AuroScholarInputModel();
        inputModel.setMobileNumber("7503600604");
        inputModel.setStudentClass("6");
        inputModel.setRegitrationSource("AuroScholr");
        inputModel.setReferralLink("");
        inputModel.setActivity(this);
        inputModel.setFragmentContainerUiId(R.id.home_container);
        openFragment(AuroScholar.startAuroSDK(inputModel));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //must param to get the acitivity
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this)).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.home_container, fragment, SampleFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }


    private void printDeviceInfo() {
        Log.i("chhonker", "SERIAL: " + Build.SERIAL);
        Log.i("chhonker", "MODEL: " + Build.MODEL);
        Log.i("chhonker", "ID: " + Build.ID);
        Log.i("chhonker", "Manufacture: " + Build.MANUFACTURER);
        Log.i("chhonker", "brand: " + Build.BRAND);
        Log.i("chhonker", "type: " + Build.TYPE);
        Log.i("chhonker", "user: " + Build.USER);
        Log.i("chhonker", "BASE: " + Build.VERSION_CODES.BASE);
        Log.i("chhonker", "INCREMENTAL " + Build.VERSION.INCREMENTAL);
        Log.i("chhonker", "SDK  " + Build.VERSION.SDK);
        Log.i("chhonker", "BOARD: " + Build.BOARD);
        Log.i("chhonker", "BRAND " + Build.BRAND);
        Log.i("chhonker", "HOST " + Build.HOST);
        Log.i("chhonker", "FINGERPRINT: " + Build.FINGERPRINT);
        Log.i("chhonker", "Version Code: " + Build.VERSION.RELEASE);
        Log.i("chhonker", "Version Name: " + getVersionName());
    }

    private String getVersionName() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        String codeName = "UNKNOWN";
        for (Field field : fields) {
            try {
                if (field.getInt(Build.VERSION_CODES.class) == Build.VERSION.SDK_INT) {
                    codeName = field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return codeName;
    }

    @Override
    public void onBackPressed() {
        ViewUtil.showSnackBar(binding.getRoot(), "Press again to close app");
    }
}
