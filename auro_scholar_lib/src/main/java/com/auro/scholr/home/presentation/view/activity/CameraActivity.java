package com.auro.scholr.home.presentation.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseActivity;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.OnItemClickListener;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.CameraFragmentLayoutBinding;
import com.auro.scholr.home.presentation.view.fragment.CameraFragment;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;
import com.auro.scholr.util.camera.CameraOverlay;
import com.auro.scholr.util.camera.FaceOverlayGraphics;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.exifinterface.media.ExifInterface;

import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.auro.scholr.R;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CameraActivity extends BaseActivity implements View.OnClickListener {
    String TAG = "AppCompatActivity";
    CameraSource mCameraSource;
    private static final int RC_HANDLE_GMS = 9001;
    private static final int RC_HANDLE_CAMERA_PERM = 2;
    int cameraID = 0;
    Camera.Parameters params;
    Camera camera;
    boolean isFlash = false;
    CameraFragmentLayoutBinding binding;
    public static boolean status;
    private boolean safeToTakePicture = true;
    CustomProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        ViewUtil.setActivityLang(this);
        init();
    }

    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, getLayout());
        binding.setLifecycleOwner(this);
        HomeActivity.setListingActiveFragment(HomeActivity.DEMOGRAPHIC_FRAGMENT);
        if (hasFrontCamera()) {
            cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
            // binding.flashContainer.setVisibility(View.GONE);
        }/*else{
            binding.flashContainer.setVisibility(View.VISIBLE);
        }*/
        setListener();
        ViewUtil.setLanguageonUi(this);

        checkValueEverySecond();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ViewUtil.setLanguageonUi(this);
        askPermission();
    }

    private void checkValueEverySecond() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (status) {
                    binding.captureButtonSecondaryContainer.animate().alpha(1F).start();
                } else {
                    binding.captureButtonSecondaryContainer.animate().alpha(0F).start();
                }
                checkValueEverySecond();
            }
        }, 1000);

    }


    private void askPermission() {
        String rationale = "Please provide location permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(this, PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                createCameraSource(cameraID);
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                finish();
            }
        });
    }


    private void createCameraSource(int cameraID) {

        Context context = this.getApplicationContext();
        FaceDetector detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();


        detector.setProcessor(
                new MultiProcessor.Builder<>(new CameraActivity.GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {

            AppLogger.e(TAG, "Face detector dependencies are not yet available.");
        }


        mCameraSource = new CameraSource.Builder(context, detector)
                .setFacing(cameraID)
                .setAutoFocusEnabled(true)
                .setRequestedFps(30.0f)
                .build();

        startCameraSource();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Cycle", "onStop");
        stopCamera();

    }

    private void stopCamera() {
        if (mCameraSource != null) {
            mCameraSource.stop();
            mCameraSource.release();
            mCameraSource = null;

        }
    }

    private void startCameraSource() {
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                this.getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                binding.preview.start(mCameraSource, binding.faceOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.switch_orientation) {
            changeCamera();
        } else if (v.getId() == R.id.flash_toggle) {
            flashIsAvailable();

        } else if (v.getId() == R.id.stillshot) {
            if (safeToTakePicture) {
                clickPicture();
                safeToTakePicture = false;
            }

        }
    }

    private void changeCamera() {
        if (cameraID == Camera.CameraInfo.CAMERA_FACING_BACK) {
            cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
            binding.flashToggle.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_flash_off_black));
            // binding.flashContainer.setVisibility(View.GONE);
        } else {
            cameraID = Camera.CameraInfo.CAMERA_FACING_BACK;
            // binding.flashContainer.setVisibility(View.VISIBLE);
        }
        binding.faceOverlay.clear();
        mCameraSource.release();
        createCameraSource(cameraID);
    }


    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new CameraActivity.GraphicFaceTracker(binding.faceOverlay);
        }
    }

    private class GraphicFaceTracker extends Tracker<Face> {
        private CameraOverlay mOverlay;
        private FaceOverlayGraphics faceOverlayGraphics;

        GraphicFaceTracker(CameraOverlay overlay) {
            mOverlay = overlay;
            faceOverlayGraphics = new FaceOverlayGraphics(overlay);
        }


        @Override
        public void onNewItem(int faceId, Face item) {
            faceOverlayGraphics.setId(faceId);
            status = true;
            binding.stillshot.setEnabled(true);

        }

        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(faceOverlayGraphics);
            faceOverlayGraphics.updateFace(face);
        }

        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {

            mOverlay.remove(faceOverlayGraphics);

        }

        @Override
        public void onDone() {
            mOverlay.remove(faceOverlayGraphics);
            status = false;
            binding.stillshot.setEnabled(false);

        }
    }

    private void clickPicture() {
        openProgressDialog();
        binding.loadingSpinner.setVisibility(View.GONE);
        mCameraSource.takePicture(null, new CameraSource.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes) {
                try {

                    ExifInterface exifInterface = new ExifInterface(new ByteArrayInputStream(bytes));
                    int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                    int rotationDegrees = 0;
                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotationDegrees = 90;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotationDegrees = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotationDegrees = 270;
                            break;
                    }


                    int finalRotationDegrees = rotationDegrees;
                    Single<String> single = Single.create(new SingleOnSubscribe<String>() {
                        @Override
                        public void subscribe(SingleEmitter<String> emitter) throws Exception {
                            try {
                                // convert byte array into bitmap
                                Bitmap loadedImage = null;
                                Bitmap loadedImageNew = BitmapFactory.decodeByteArray(bytes, 0,
                                        bytes.length);
                                loadedImage = rotateBitmap(loadedImageNew, finalRotationDegrees);
                                //  String path = saveToInternalStorage(loadedImage) + "/profile.jpg";
                                String path = saveToInternalStorage(loadedImage);

                                emitter.onSuccess(path);
                            } catch (Exception e) {
                                emitter.onError(e);
                            }
                        }
                    });


                    new CompositeDisposable().add(single
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<String>() {
                                        @Override
                                        public void accept(String path) throws Exception {
                                            closeDialog();
                                            Intent intent = new Intent();
                                            intent.putExtra(AppConstant.PROFILE_IMAGE_PATH, path);
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    },
                                    new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                        }
                                    }
                            ));


                    //loadImageFromStorage(saveToInternalStorage(loadedImage));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
        if (degree == 0 || bitmap == null) {
            return bitmap;
        }
        final Matrix matrix = new Matrix();
        matrix.setRotate(degree, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private String SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/auroca_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "auro_profile" + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            boolean f = file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return file.getAbsolutePath();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        try {
            Uri uri = Uri.fromFile(File.createTempFile("profile", ".jpg", getCacheDir()));
            File mypath = new File(uri.getPath());
            FileOutputStream fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return mypath.getAbsolutePath();
        } catch (Exception e) {
        }
        return "";
    }


    private void loadImageFromStorage(String path) {
        try {

            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            this.getSupportFragmentManager().popBackStack();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean hasFrontCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }


    private void flashIsAvailable() {
        boolean hasFlash = this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (hasFlash && cameraID == 0) {
            changeFlashStatus();
        } else if (hasFlash && cameraID == 1) {
            changeFlashStatus();
        }

    }


    public void changeFlashStatus() {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    camera = (Camera) field.get(mCameraSource);
                    if (camera != null) {
                        params = camera.getParameters();
                        if (!isFlash) {
                            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                            binding.flashToggle.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_flash_on_black));
                            isFlash = true;
                        } else {
                            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                            binding.flashToggle.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_flash_off_black));
                            isFlash = false;
                        }
                        if (params == null) {
                            camera.setParameters(params);
                        }else if( params != null){
                            camera.setParameters(params);
                        }

                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Cycle", "onDestroy");
        releaseCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Cycle", "onPause");
        if (isFlash) {
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
    }


    @Override
    protected void setListener() {
        binding.stillshot.setOnClickListener(this);
       // binding.switchOrientation.setOnClickListener(this);
       // binding.flashToggle.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.camera_fragment_layout;
    }

    private void releaseCamera() {
        if (binding.faceOverlay != null) {
            binding.faceOverlay.clear();
        }
        stopCamera();
    }


    private void openProgressDialog() {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            return;
        }

        Locale.getDefault().getLanguage();
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(this);
        customDialogModel.setTitle(this.getResources().getString(R.string.processing));
        customDialogModel.setTwoButtonRequired(true);
        customProgressDialog = new CustomProgressDialog(customDialogModel);
        Objects.requireNonNull(customProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();
        customProgressDialog.updateDataUi(0);
    }
    public void closeDialog() {
        if (customProgressDialog != null) {
            customProgressDialog.dismiss();
        }
    }
}
