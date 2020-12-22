package com.auro.scholr.home.presentation.view.fragment;

import android.Manifest;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.network.URLConstant;
import com.auro.scholr.databinding.QuizTestLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentResModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.presentation.viewmodel.QuizTestViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ViewUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.Disposable;

import static com.auro.scholr.core.common.Status.ASSIGNMENT_STUDENT_DATA_API;

/**
 * Created by varun
 */

public class PrivacyPolicyFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = "PrivacyPolicyFragment";

    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private WebSettings webSettings;
    private ValueCallback<Uri[]> mUploadMessage;
    private String mCameraPhotoPath = null;
    private long size = 0;
    QuizTestLayoutBinding binding;
    private WebView webView;

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
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setHasOptionsMenu(true);
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
            binding.setLifecycleOwner(this);
        }
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {

        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();

    }

    @Override
    protected void init() {

        checkInternet();
    }

    @Override
    protected void setToolbar() {
        /*Do cod ehere*/
    }

    @Override
    protected void setListener() {
        binding.toolbarLayout.backArrow.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.quiz_test_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setListener();
    }

    public void checkInternet() {
        Disposable disposable = NetworkUtil.hasInternetConnection().subscribe(hasInternet -> {
            if (hasInternet) {
                handleProgress(0, "");
                loadWeb(URLConstant.PRIVACY_POLICY);
            } else {
                handleProgress(2, getString(R.string.internet_check));
            }

        });
    }


    @Override
    public void onResume() {
        super.onResume();
        setKeyListner();
    }

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSettings.setMediaPlaybackRequiresUserGesture(false);
        }
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

        webView.loadUrl(webUrl);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.back_arrow){
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public class PQClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // If url contains mailto link then open Mail Intent
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
            AppLogger.e(TAG, url);
            if (view.getUrl().equalsIgnoreCase("http://auroscholar.com/index.php")
                    || view.getUrl().equalsIgnoreCase("http://auroscholar.com/dashboard.php")) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
            handleProgress(0, "");


        }

        // Called when all page resources loaded
        public void onPageFinished(WebView view, String url) {
            handleProgress(1, "");
            webView.loadUrl("javascript:(function(){ " +
                    "document.getElementById('android-app').style.display='none';})()");
        }
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
                    checkInternet();
                }
            });
        }
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
