package com.auro.scholr.home.presentation.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.core.network.URLConstant;
import com.auro.scholr.databinding.QuizTestLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AssignmentResModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.presentation.viewmodel.QuizTestViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.alert_dialog.CustomDialog;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.ASSIGNMENT_STUDENT_DATA_API;

/**
 * Created by varun
 */
@SuppressLint("SetJavaScriptEnabled")
public class QuizTestFragment extends BaseFragment {
    public static final String TAG = "QuizTestFragment";

    @Inject
    @Named("QuizTestFragment")
    ViewModelFactory viewModelFactory;

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private WebSettings webSettings;
    private ValueCallback<Uri[]> mUploadMessage;
    private String mCameraPhotoPath = null;
    private long size = 0;
    QuizTestLayoutBinding binding;
    private WebView webView;
    DashboardResModel dashboardResModel;
    QuizTestViewModel quizTestViewModel;
    QuizResModel quizResModel;
    AssignmentResModel assignmentResModel;
    CustomDialog customDialog;
    Dialog customProgressDialog;

    AssignmentReqModel assignmentReqModel;

    boolean submittingTest = false;

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
            AuroApp.getAppComponent().doInjection(this);
            quizTestViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizTestViewModel.class);
            binding.setLifecycleOwner(this);
            setHasOptionsMenu(true);

        }
        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);

    }

    @Override
    public void onDestroy() {

        if (webView != null) {
            webView.destroy();
        }
        if (customDialog != null) {
            customDialog.cancel();
        }
        super.onDestroy();

    }


    @Override
    protected void init() {
        setKeyListner();
        setListener();
        if (dashboardResModel != null && quizResModel != null) {
            assignmentReqModel = quizTestViewModel.homeUseCase.getAssignmentRequestModel(dashboardResModel, quizResModel);
            quizTestViewModel.getAssignExamData(assignmentReqModel);
        }
    }


    private void observeServiceResponse() {

        quizTestViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    handleProgress(0, "");
                    break;
                case SUCCESS:
                    if (responseApi.apiTypeStatus == ASSIGNMENT_STUDENT_DATA_API) {
                        assignmentResModel = (AssignmentResModel) responseApi.data;
                        if (!assignmentResModel.isError()) {
                            String webUrl = URLConstant.TEST_URL + "StudentID=" + assignmentResModel.getStudentID() + "&ExamAssignmentID=" + assignmentResModel.getExamAssignmentID();
                            openDialog();
                            loadWeb(webUrl);
                        } else {
                            handleProgress(2, assignmentResModel.getMessage());
                        }
                    }
                    break;
                case NO_INTERNET:
                    handleProgress(2, getActivity().getString(R.string.internet_check));
                    break;
                case AUTH_FAIL:
                case FAIL_400:
                default:
                    handleProgress(2, getActivity().getString(R.string.default_error));
                    break;
            }
        });
    }

    @Override
    protected void setToolbar() {
        /*Do cod ehere*/
    }

    @Override
    protected void setListener() {
        if (quizTestViewModel != null && quizTestViewModel.serviceLiveData().hasObservers()) {
            quizTestViewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.quiz_test_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
            quizResModel = getArguments().getParcelable(AppConstant.QUIZ_RES_MODEL);
        }
        init();
    }

    public void openQuizHomeFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void openDemographicFragment() {
        Bundle bundle = new Bundle();
        DemographicFragment demographicFragment = new DemographicFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        demographicFragment.setArguments(bundle);
        openFragment(demographicFragment);
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, KYCViewFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setListener();
    }

    @SuppressLint("JavascriptInterface")
    private void loadWeb(String webUrl) {
        webView = binding.webView;
        webSettings = webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings = binding.webView.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setMediaPlaybackRequiresUserGesture(false);
        webSettings.setAllowFileAccess(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");
        webView.setWebViewClient(new PQClient());
        webView.setWebChromeClient(new PQChromeClient());
        //if SDK version is greater of 19 then activate hardware acceleration otherwise activate software acceleration
        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }


        webView.addJavascriptInterface(new MyJavaScriptInterface(getActivity()), "ButtonRecognizer");

        webView.loadUrl(webUrl);

    }


    class MyJavaScriptInterface {

        private Context ctx;

        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        @JavascriptInterface
        public void boundMethod(String html) {
            openProgressDialog();
            AppLogger.e("chhonker bound method", html);
          /*  new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    cancelDialogAfterSubmittingTest();

                }
            }, 8000);
*/
        }
    }

    private void cancelDialogAfterSubmittingTest() {
        if (!submittingTest) {
            submittingTest = true;
            if (customProgressDialog != null) {
                customProgressDialog.cancel();
            }
            if (!quizTestViewModel.homeUseCase.checkDemographicStatus(dashboardResModel)) {
                openDemographicFragment();
            } else {
                openQuizHomeFragment();
            }

        }
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null) {
            prefModel.setAssignmentReqModel(assignmentReqModel);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    public class PQClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // If url contains mailto link then open Mail Intent
            AppLogger.e("chhonker shouldOverrideUrlLoading", url);


            if (url.contains("mailto:")) {
                // Could be cleverer and use a regex
                //Open links in new browser
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else {
                //Stay within this webview and load url
                view.loadUrl(url);
                return true;
            }
        }


        //Show loader on url load
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            AppLogger.e("chhonker onPageStarted", url);
            if (!TextUtil.isEmpty(url)) {
                if (url.equalsIgnoreCase("http://auroscholar.com/index.php") ||
                        url.equalsIgnoreCase("http://auroscholar.com/demographics.php")
                        || url.equalsIgnoreCase("http://auroscholar.com/dashboard.php")) {
                    cancelDialogAfterSubmittingTest();
                }
            }
        }

        // Called when all page resources loaded
        public void onPageFinished(WebView view, String url) {
            //webView.loadUrl("<button type=\"button\" value=\"Continue\" onclick=\"Continue.performClick(this.value);\">Continue</button>\n");
            AppLogger.e("chhonker Finished", url);
            loadEvent(clickListener());
            if (!TextUtil.isEmpty(url) && url.equalsIgnoreCase("https://assessment.eklavvya.com/Exam/CandidateExam")) {
                AppLogger.e("chhonker Finished exam", url);
                closeDialog();
            }
            handleProgress(1, "");
        }

        private void closeDialog() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (customDialog != null) {
                        customDialog.cancel();
                    }
                }
            }, 2000);
        }

        private void loadEvent(String javascript) {
            webView.loadUrl("javascript:" + javascript);
        }

        private String clickListener() {
            return getButtons() + "for(var i = 0; i < buttons.length; i++){\n" +
                    "\tbuttons[i].onclick = function(){ console.log('click worked.'); ButtonRecognizer.boundMethod('button clicked'); };\n" +
                    "}";
        }

        private String getButtons() {
            //  return "var buttons = document.getElementsByClassName('col-sm-12'); console.log(buttons.length + ' buttons');\n";
            return "var buttons = document.getElementsByClassName('btn-primary btn btn-style'); console.log(buttons.length + ' buttons');\n";

        }
    }

    public class PQChromeClient extends WebChromeClient {
        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            String[] requestedResources = request.getResources();
            ArrayList<String> permissions = new ArrayList<>();
            ArrayList<String> grantedPermissions = new ArrayList<String>();
            for (int i = 0; i < requestedResources.length; i++) {
                if (requestedResources[i].equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                    permissions.add(Manifest.permission.RECORD_AUDIO);
                } else if (requestedResources[i].equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                    permissions.add(Manifest.permission.CAMERA);
                }
            }
            for (int i = 0; i < permissions.size(); i++) {
                if (ContextCompat.checkSelfPermission(getActivity(), permissions.get(i)) != PackageManager.PERMISSION_GRANTED) {
                    continue;
                }
                if (permissions.get(i).equals(Manifest.permission.RECORD_AUDIO)) {
                    grantedPermissions.add(PermissionRequest.RESOURCE_AUDIO_CAPTURE);
                } else if (permissions.get(i).equals(Manifest.permission.CAMERA)) {
                    grantedPermissions.add(PermissionRequest.RESOURCE_VIDEO_CAPTURE);
                }
            }

            if (grantedPermissions.isEmpty()) {
                request.deny();
            } else {
                String[] grantedPermissionsArray = new String[grantedPermissions.size()];
                grantedPermissionsArray = grantedPermissions.toArray(grantedPermissionsArray);
                request.grant(grantedPermissionsArray);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // pbPageLoading.setProgress(newProgress);
        }

        // For Android 5.0+
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, FileChooserParams fileChooserParams) {
            // Double check that we don't have any existing callbacks
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
            }
            mUploadMessage = filePath;

            int writePermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int cameraPermission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            if (!(writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED)) {
                try {
                    Intent galleryintent = new Intent(Intent.ACTION_GET_CONTENT, null);
                    galleryintent.setType("image/*");

                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.e("error", "Unable to create Image File", ex);
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                        cameraIntent.putExtra(
                                MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile)
                        );
                    }
                    Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                    chooser.putExtra(Intent.EXTRA_INTENT, galleryintent);
                    chooser.putExtra(Intent.EXTRA_TITLE, "Select from:");

                    Intent[] intentArray = {cameraIntent};
                    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                    // startActivityForResult(chooser, REQUEST_PIC);
                    startActivityForResult(chooser, INPUT_FILE_REQUEST_CODE);
                } catch (Exception e) {
                    // TODO: when open file chooser failed
                }
            }
            return true;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }

    private void handleProgress(int status, String msg) {
        if (status == 0) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.webView.setVisibility(View.GONE);
            binding.errorConstraint.setVisibility(View.GONE);
        } else if (status == 1) {
            binding.progressBar.setVisibility(View.GONE);
            binding.webView.setVisibility(View.VISIBLE);
            binding.errorConstraint.setVisibility(View.GONE);
        } else if (status == 2) {
            binding.progressBar.setVisibility(View.GONE);
            binding.webView.setVisibility(View.GONE);
            binding.errorConstraint.setVisibility(View.VISIBLE);
            binding.errorLayout.textError.setText(msg);
            binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dashboardResModel != null && quizResModel != null) {
                        quizTestViewModel.getAssignExamData(quizTestViewModel.homeUseCase.getAssignmentRequestModel(dashboardResModel, quizResModel));
                    }
                }
            });
        }
    }

    private void openDialog() {
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle("Quiz Instructions");
        customDialogModel.setContent(getActivity().getResources().getString(R.string.bullted_list));
        customDialogModel.setTwoButtonRequired(false);
        customDialog = new CustomDialog(customDialogModel);
        // Window window = customDialog.getWindow();
        // window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDialog.getWindow().setAttributes(lp);
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.setCancelable(false);
        customDialog.show();

    }

    private void openProgressDialog() {
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle("Calculating Your Score");
        customDialogModel.setContent(getActivity().getResources().getString(R.string.bullted_list));
        customDialogModel.setTwoButtonRequired(false);
        customProgressDialog = new CustomProgressDialog(customDialogModel);
        Objects.requireNonNull(customProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();
    }

    private void setKeyListner() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
    }
}
