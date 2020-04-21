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
import android.webkit.JavascriptInterface;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.NetworkUtil;
import com.auro.scholr.databinding.CardFragmentLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AssignmentResModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.ObjStudentExamInfo;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;
import com.auro.scholr.home.presentation.viewmodel.QuizTestViewModel;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;

import static com.auro.scholr.core.common.Status.ASSIGNMENT_STUDENT_DATA_API;
import static com.auro.scholr.core.common.Status.DEMOGRAPHIC_API;

/**
 * Created by varun
 */

public class QuizTestFragment extends BaseFragment {
    public static final String TAG = "QuizTestFragment";

    @Inject
    @Named("QuizTestFragment")
    ViewModelFactory viewModelFactory;


    CardFragmentLayoutBinding binding;
    private WebView webView;
    private WebSettings webSettings;
    DashboardResModel dashboardResModel;
    QuizTestViewModel quizTestViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.card_fragment_layout, container, false);
            AuroApp.getAppComponent().doInjection(this);
            quizTestViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizTestViewModel.class);
            binding.setLifecycleOwner(this);
        }

        return binding.getRoot();
    }

    @Override
    protected void init() {

        if (quizTestViewModel != null && quizTestViewModel.serviceLiveData().hasObservers()) {
            quizTestViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
        askPermission();
    }


    private void observeServiceResponse() {

        quizTestViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == ASSIGNMENT_STUDENT_DATA_API) {
                        AssignmentResModel assignmentResModel = (AssignmentResModel) responseApi.data;
                        String webUrl = "https://assessment.eklavvya.com/exam/StartExam?StudentID=" + dashboardResModel.getStudent_id() + "&ExamAssignmentID=" + assignmentResModel.getAssignExamToStudentResult();
                        loadWeb(webUrl);
                    }

                    break;

                case NO_INTERNET:
//On fail
                    break;

                case AUTH_FAIL:
                case FAIL_400:
                default:

                    break;
            }

        });
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayout() {
        return 0;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }

        init();


    }

    private void askPermission() {
        String rationale = "For taking the quiz camera permission is must.";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                if (dashboardResModel != null) {
                    AssignmentReqModel assignmentReqModel = new AssignmentReqModel();
                    ObjStudentExamInfo objStudentExamInfo = new ObjStudentExamInfo();
                    objStudentExamInfo.setExamLanguage("E");
                    objStudentExamInfo.setGrade(dashboardResModel.getStudentclass());
                    objStudentExamInfo.setMonth(dashboardResModel.getMonth());
                    objStudentExamInfo.setStudentID(dashboardResModel.getStudent_id());
                    objStudentExamInfo.setSubjectName(dashboardResModel.getSubjectName());
                    objStudentExamInfo.setExamName("Test11");
                    assignmentReqModel.setObjStudentExamInfo(objStudentExamInfo);
                    quizTestViewModel.getAssignExamData(assignmentReqModel);

                }

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void loadWeb(String webUrl) {
        webView = binding.webView;
        webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        ;

        webSettings.setAllowFileAccess(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");

        webView.setWebViewClient(new PQClient());
        webView.setWebChromeClient(new WebChromeClient());
        //if SDK version is greater of 19 then activate hardware acceleration otherwise activate software acceleration
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        // webView.loadUrl("https://en.imgbb.com/");
        webView.loadUrl(webUrl);

    }

    public class PQClient extends WebViewClient {
        ProgressDialog progressDialog;

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // If url contains mailto link then open Mail Intent
            if (url.contains("mailto:")) {

                // Could be cleverer and use a regex
                //Open links in new browser
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                // Here we can open new activity

                return true;

            } else {

                // Stay within this webview and load url
                view.loadUrl(url);
                return true;
            }
        }

        //Show loader on url load
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (view.getUrl().equalsIgnoreCase("http://auroscholar.com/index.php")) {
                Log.e("chhonker", url);
                getActivity().getSupportFragmentManager().popBackStack();
            }

            // Then show progress  Dialog
            // in standard case YourActivity.this
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Loading...");
                progressDialog.hide();
            }
        }

        // Called when all page resources loaded
        public void onPageFinished(WebView view, String url) {
            webView.loadUrl("javascript:(function(){ " +
                    "document.getElementById('android-app').style.display='none';})()");

            try {
                // Close progressDialog
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
