package com.auro.scholr.home.presentation.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.NetworkUtil;
import com.auro.scholr.databinding.CardFragmentLayoutBinding;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.util.ViewUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.reactivex.disposables.Disposable;

/**
 * Created by varun
 */

public class ScholarShipFragment extends Fragment {
    public static final String TAG = "ScholarShipFragment";

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private WebSettings webSettings;
    private ValueCallback<Uri[]> mUploadMessage;
    private String mCameraPhotoPath = null;
    private long size = 0;
    CardFragmentLayoutBinding binding;
    AuroScholarDataModel auroScholarDataModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true);
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.card_fragment_layout, container, false);

        }
        HomeActivity.setListingActiveFragment(HomeActivity.QUIZ_DASHBOARD_WEB_FRAGMENT);
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (getArguments() != null) {
            auroScholarDataModel = getArguments().getParcelable(AppConstant.AURO_DATA_MODEL);
        }

        webSettings = binding.webView.getSettings();
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
        binding.webView.addJavascriptInterface(new WebAppInterface(getActivity()), "Android");

        webSettings.setAllowFileAccess(true);
        webSettings.setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.95 Safari/537.36");

        binding.webView.setWebViewClient(new PQClient());
        binding.webView.setWebChromeClient(new PQChromeClient());
        //if SDK version is greater of 19 then activate hardware acceleration otherwise activate software acceleration
        if (Build.VERSION.SDK_INT >= 19) {
            binding.webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            binding.webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        webviewInit();

    }

    public void webviewInit() {
        checkInternet();
    }

    public void loadWebData() {
        binding. webView.loadUrl("https://assessment.eklavvya.com/exam/StartExam?StudentID=2175083&ExamAssignmentID=3160329");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setUserCommunityProfileData() {
        // TODO: 30/1/19 communicate with ruchi(backend dev) for api to show data in daily attendance card and answer now card.
      /*  userCommunityProfile = PrefManager.getInstance().getCommunityProfile();
        if (userCommunityProfile != null && userCommunityProfile.getProfile() != null) {
            if (userCommunityProfile.getProfile().name == null) {
                userCommunityProfile.getProfile().name = "User";
            }
            PrefManager.getInstance().setTotalCoins(userCommunityProfile.getPointsEarned());

            if (userCommunityProfile.getProfile().name == null || userCommunityProfile.getProfile().name.equals("")) {
                userCommunityProfile.getProfile().name = "User";
            }

        } else {
            getUserCommunityProfileData();
        }*/
    }

    // Storage Permissions variables
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != INPUT_FILE_REQUEST_CODE || mUploadMessage == null) {
            super.onActivityResult(requestCode, resultCode, data);
            mUploadMessage.onReceiveValue(new Uri[]{});
            mUploadMessage = null;
            return;
        }
        try {
            String file_path = mCameraPhotoPath.replace("file:", "");
            File file = new File(file_path);
            size = file.length();

        } catch (Exception e) {
            Log.e("Error!", "Error while opening image file" + e.getLocalizedMessage());
        }

        if (data != null || mCameraPhotoPath != null) {
            Integer count = 0; //fix fby https://github.com/nnian
            ClipData images = null;
            try {
                images = data.getClipData();
            } catch (Exception e) {
                Log.e("Error!", e.getLocalizedMessage());
            }

            if (images == null && data != null && data.getDataString() != null) {
                count = data.getDataString().length();
            } else if (images != null) {
                count = images.getItemCount();
            }
            Uri[] results = new Uri[count];
            // Check that the response is a good one
            if (resultCode == Activity.RESULT_OK) {
                if (size != 0) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else if (data.getClipData() == null) {
                    results = new Uri[]{Uri.parse(data.getDataString())};
                } else {

                    for (int i = 0; i < images.getItemCount(); i++) {
                        results[i] = images.getItemAt(i).getUri();
                    }
                }
                mUploadMessage.onReceiveValue(results);
                mUploadMessage = null;
            } else {
                mUploadMessage.onReceiveValue(new Uri[]{});
                mUploadMessage = null;
            }

        }
    }

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED || cameraPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            mUploadMessage.onReceiveValue(new Uri[]{});
            mUploadMessage = null;
        }
      else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                binding.webView.evaluateJavascript("grantedPermission();", null);
            }
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

    public class PQChromeClient extends WebChromeClient {

        @Override
        public void onPermissionRequest(final PermissionRequest request) {
            String[] requestedResources = new String[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                requestedResources = request.getResources();
            }
            ArrayList<String> permissions = new ArrayList<>();
            ArrayList<String> grantedPermissions = new ArrayList<String>();
            for (int i = 0; i < requestedResources.length; i++) {
                if (requestedResources[i].equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                    permissions.add(Manifest.permission.RECORD_AUDIO);
                } else if (requestedResources[i].equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)) {
                    permissions.add(Manifest.permission.CAMERA);
                }
                // TODO: RESOURCE_MIDI_SYSEX, RESOURCE_PROTECTED_MEDIA_ID.
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.deny();
                }
            } else {
                String[] grantedPermissionsArray = new String[grantedPermissions.size()];
                grantedPermissionsArray = grantedPermissions.toArray(grantedPermissionsArray);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(grantedPermissionsArray);
                }
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

    public class PQClient extends WebViewClient {
        ProgressDialog progressDialog;

        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // If url contains mailto link then open Mail Intent
            if (url.contains("http://auroscholar.com/dashboard.php")) {
                //   tvTitle.setVisibility(View.VISIBLE);
                //    ivHome.setVisibility(View.GONE);
            } else {
                //tvTitle.setVisibility(View.GONE);
                //  ivHome.setVisibility(View.VISIBLE);
            }
            return false;
        }

        //Show loader on url load
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (!view.getUrl().equalsIgnoreCase("https://assessment.eklavvya.com/Exam/CandidateExam")) {
                Log.e("chhonker", url);
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

            view.loadUrl("javascript:window.android.onUrlChange(window.location.href);");

//            webView.loadUrl("javascript:(function(){ " +
//                                    "document.getElementById('android-app').style.display='none';})()");

            try {
                // Close progressDialog
                handleProgress(1);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void askCamera() {
            verifyStoragePermissions(getActivity());
        }
    }


    private void handleProgress(int value) {
        if (value == 0) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.startShimmer();
        } else if (value == 1) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.webView.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.stopShimmer();
        } else {
            binding.errorConstraint.setVisibility(View.VISIBLE);
            binding.webView.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.shimmerViewQuiz.stopShimmer();
            binding.errorLayout.textError.setText(getString(R.string.internet_check));
            binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webviewInit();
                }
            });
        }

    }

    private void checkInternet() {
        NetworkUtil.hasInternetConnection().subscribe(hasInternet -> {
            if (hasInternet) {
                handleProgress(0);
                loadWebData();
            } else {
                handleProgress(2);
            }
        });

    }

}
