package com.auro.scholr.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.util.alert_dialog.ErrorSnackbar;
import com.auro.scholr.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class ViewUtil {

    private static View snackView;

    public static String parseJSONData(Context context, String fileName) {
        String JsonString = null;
        try {

            InputStream inputStream = context.getAssets().open(fileName);

            int sizeOfJSONFile = inputStream.available();

            byte[] bytes = new byte[sizeOfJSONFile];

            inputStream.read(bytes);

            inputStream.close();

            JsonString = new String(bytes, "UTF-8");

        } catch (IOException ex) {
            AppLogger.e("ViewUtil", ex.getMessage());
            return null;
        }
        return JsonString;
    }

    public static void showToast(Context activity, String msg) {

        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    public static void deActivateDot(ImageView imageView) {

        imageView.setBackground(AuroApp.getAppContext().getResources().getDrawable(R.drawable.more_icon));

    }

    public static void activateDot(ImageView imageView) {

        imageView.setBackground(AuroApp.getAppContext().getResources().getDrawable(R.drawable.mobile_icon_svg
        ));

    }


    public static void fullScreen(Activity context) {

        context.requestWindowFeature(Window.FEATURE_NO_TITLE);
        context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void keepOnScreen(Activity context) {

        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    public static String format2LenStr(int num) {

        return (num < 10) ? "0" + num : String.valueOf(num);
    }

    public static void showSnackBar(View rootLayout, String snackTitle) {

        if (rootLayout == null) {
            return;
        }
        if (rootLayout.getParent() == null) {
            return ;
        }
        snackView = ErrorSnackbar.showSnackbar(rootLayout, snackTitle);
    }

    public static void showSnackBar(View rootLayout, String snackTitle,int color) {

        if (rootLayout == null) {
            return;
        }
        if (rootLayout.getParent() == null) {
            return ;
        }
        snackView = ErrorSnackbar.showSnackbar(rootLayout, snackTitle,color);
    }

    public static void dismissSnackBar() {
        if (snackView != null) {
            snackView.setVisibility(View.GONE);
        }
    }

    public static String getPackageVersion() {
        String packageVersion = null;
        try {
            PackageInfo pInfo = AuroApp.getAppContext().getPackageManager().getPackageInfo(AuroApp.getAppContext().getPackageName(), 0);
            packageVersion = pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packageVersion;

    }


    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static int dpToPx(float dp, Context context) {
        return dpToPx(dp, context.getResources());
    }

    public static int dpToPx(float dp, Resources resources) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }


    public static void showToast(String message) {
        Toast.makeText(AuroApp.getAppContext(), message,
                Toast.LENGTH_SHORT).show();
    }

    public static void customTextView(TextView view, CommonCallBackListner commonCallBackListner) {
        view.setHighlightColor(AuroApp.getAppContext().getResources().getColor(android.R.color.transparent));

        SpannableStringBuilder spanTxt = new SpannableStringBuilder("terms_of_service");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Do code here
                if (commonCallBackListner != null) {
                    commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(AppConstant.TERMS_CONDITION_TEXT, null, ""));
                }

            }
        }, spanTxt.length() - ("terms_of_service").length(), spanTxt.length(), 0);
        spanTxt.append(" " + "and" + " ");
        spanTxt.setSpan(new ForegroundColorSpan(AuroApp.getAppContext().getResources().getColor(R.color.color_red)), 0, ("terms_of_service").length(), 0);
        spanTxt.append("privacy_policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                //Do code here
                if (commonCallBackListner != null) {
                    commonCallBackListner.commonEventListner(AppUtil.getCommonClickModel(AppConstant.PRIVACY_POLICY_TEXT, null, ""));
                }
            }
        }, spanTxt.length() - ("privacy_policy").length(), spanTxt.length(), 0);
        spanTxt.setSpan(new ForegroundColorSpan(AuroApp.getAppContext().getResources().getColor(R.color.color_red)), (spanTxt.length() - ("privacy_policy").length()), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    public static void hideKeyboard(Context context) {
        if (context == null) {
            return;
        }
        View view = ((AppCompatActivity) context).getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public static void showKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (methodManager != null && view != null) {
            methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    public static void setLightStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // add LIGHT_STATUS_BAR to flag
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(activity.getColor(R.color.white)); // optional
        }
    }

    public static Resources getCustomResource( Activity activity) {
       // Locale locale = new Locale(getLanguage());
        Resources standardResources = activity.getResources();
        AssetManager assets = standardResources.getAssets();
        DisplayMetrics metrics = standardResources.getDisplayMetrics();
        Configuration config = new Configuration(standardResources.getConfiguration());
        config.locale =new Locale(getLanguage());
        Resources res = new Resources(assets, metrics, config);
        return res;
    }


    public static String getLanguage() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && prefModel.getUserLanguage() != null && !prefModel.getUserLanguage().isEmpty()) {
            return prefModel.getUserLanguage();
        }
        return AppConstant.LANGUAGE_EN;
    }

    public static void setLanguage(String language) {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null ) {
            prefModel.setUserLanguage(language);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    public static void setLanguageonUi(Activity activity){
        ViewUtil.setActivityLang(activity);
      /*Resources resources = activity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = new Locale(Locale.getDefault().getLanguage());
        resources.updateConfiguration(config, dm);*/
        //Locale.setDefault(Locale.getDefault());
    }

    public static void setActivityLang(Activity activity){
        Resources resources = activity.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = new Locale(getLanguage());
        resources.updateConfiguration(config, dm);
    }



}
