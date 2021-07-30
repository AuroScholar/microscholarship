package com.example.aurosampleapplication;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.SdkCallBack;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.AuroScholarInputModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.AuroScholar;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.encryption.Cryptor;
import com.example.aurosampleapplication.databinding.ActivityMainBinding;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;


    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Locale locale = new Locale("hi");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());*/
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btSdk.setOnClickListener(this);
        binding.btOpen.setOnClickListener(this);
        binding.btSdk.setVisibility(View.VISIBLE);
        binding.btOpen.setVisibility(View.GONE);


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
                String mobileNumber = binding.mobileNumber.getText().toString();
                String student_class = binding.userClass.getText().toString();
                if (TextUtil.isEmpty(mobileNumber) && mobileNumber.length() != 10) {
                    Toast.makeText(this, "Please Enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else if (TextUtil.isEmpty(student_class)) {
                    Toast.makeText(this, "Please Enter class", Toast.LENGTH_SHORT).show();
                } else {
                    openGenricSDK(mobileNumber, student_class);
                }
                hideKeyboard(this);
                break;
            case R.id.bt_open:
                if (!Pattern.matches("[a-zA-Z]+", binding.mobileNumber.getText().toString()) &&
                        binding.mobileNumber.getText().length() > 9 && binding.mobileNumber.getText().length() <= 10 && binding.mobileNumber.getText().toString() != null) {
                    openTeacherSDK();
                } else {
                    Toast.makeText(this, "Please Enter valid mobile number", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    private void openTeacherSDK() {
        AuroScholarDataModel auroScholarDataModel = new AuroScholarDataModel();
        auroScholarDataModel.setMobileNumber(binding.mobileNumber.getText().toString());//Mandatory
        auroScholarDataModel.setRegitrationSource("");
        auroScholarDataModel.setShareType("");
        auroScholarDataModel.setShareIdentity("");
        auroScholarDataModel.setActivity(this); // Activity context here
        auroScholarDataModel.setEmailVerified(true);
        auroScholarDataModel.setPartnerSource("");
        auroScholarDataModel.setUTMLink(binding.utmTextHere.getText().toString());
       // auroScholarDataModel.setUTMLink("");

        auroScholarDataModel.setFragmentContainerUiId(R.id.home_container) ;//This is the example please put your container id here.
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
            public void commonCallback(Status status, Object o) {
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

    public static void hideKeyboard(Context context) {
        if (context == null) {
            return;
        }
        View view = ((AppCompatActivity) context).getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    private void openGenricSDK(String mobileNumber, String student_class) {
        String language = binding.language.getText().toString();
        AuroScholarInputModel inputModel = new AuroScholarInputModel();
        inputModel.setMobileNumber(mobileNumber);//mobileNumber
        inputModel.setStudentClass(student_class);//"binding.userClass.getText().toString()"
        inputModel.setRegitrationSource("");
        inputModel.setReferralLink("");
        inputModel.setPartnerSource(""); //this id is provided by auroscholar for valid partner//Demo partner id:AUROJ1i5dA
        inputModel.setActivity(this);
        inputModel.setPartnerLogoUrl(""); //Mandatory
        inputModel.setSchoolName("");//optional Filed
        inputModel.setBoardType("");//optional Filed
        inputModel.setSchoolType(" ");//optional Filed
        inputModel.setGender("");//optional Filed
        inputModel.setPartnerName("");//Mandatory
        inputModel.setEmail("");//optional Filed
        AuroScholar.startAuroSDK(inputModel);
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
        super.onBackPressed();
        //  ViewUtil.showSnackBar(binding.getRoot(), "Press again to close app");
    }

    private void setDummyImagePath() {
        Bitmap compressedImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chandan_image_two);
        compressedImageBitmap = getResizedBitmap(compressedImageBitmap, 500);
        byte[] bytes = AppUtil.encodeToBase64(compressedImageBitmap, 100);
        long mb = AppUtil.bytesIntoHumanReadable(bytes.length);
        Log.e("chhonker", "Image size-" + mb);
        //   binding.image.setImageBitmap(compressedImageBitmap);

    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
