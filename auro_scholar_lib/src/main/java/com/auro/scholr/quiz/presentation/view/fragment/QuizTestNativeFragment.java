package com.auro.scholr.quiz.presentation.view.fragment;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.Image;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.database.AppPref;

import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.QuizTestNativeLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AssignmentResModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.SaveImageReqModel;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;

import com.auro.scholr.home.presentation.view.fragment.DemographicFragment;
import com.auro.scholr.home.presentation.view.fragment.newDesgin.MainQuizHomeFragment;
import com.auro.scholr.quiz.data.model.getQuestionModel.FetchQuizResModel;
import com.auro.scholr.quiz.data.model.getQuestionModel.QuestionResModel;
import com.auro.scholr.quiz.data.model.response.SaveQuestionResModel;
import com.auro.scholr.quiz.data.model.submitQuestionModel.OptionResModel;
import com.auro.scholr.quiz.presentation.view.adapter.SelectQuizOptionAdapter;
import com.auro.scholr.quiz.presentation.viewmodel.QuizTestNativeViewModel;
import com.auro.scholr.teacher.data.model.SelectResponseModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.DeviceUtil;
import com.auro.scholr.util.ImageUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialog;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;
import com.auro.scholr.util.alert_dialog.ExitDialog;
import com.auro.scholr.util.alert_dialog.NativeQuizImageDialog;
import com.auro.scholr.util.broadcastreceiver.NetworkChangeReceiver;
import com.auro.scholr.util.camera.Camera2Source;
import com.auro.scholr.util.camera.CameraSource;
import com.auro.scholr.util.camera.CameraSourcePreview;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;
import com.auro.scholr.util.timer.Hourglass;
import com.auro.scholr.util.utility.Camera3Source;
import com.auro.scholr.util.utility.CloudLandmarkRecognitionProcessor;
import com.auro.scholr.util.utility.GraphicOverlay;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;


public class QuizTestNativeFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener, View.OnTouchListener {

    @Inject
    @Named("QuizTestNativeFragment")
    ViewModelFactory viewModelFactory;
    QuizTestNativeLayoutBinding binding;
    QuizTestNativeViewModel viewModel;
    List<SelectResponseModel> list;
    SelectQuizOptionAdapter mcSelectQuizAdapter;
    Hourglass hourglass;
    int workingornot = 1;
    int START_TIME_IN_MILLIS = 61000;
    int COUNTDOWN_INTERVAL = 1000;
    CustomDialog customInstructionDialog;
    String TAG = "QuizTestNativeFragment";
    FetchQuizResModel fetchQuizResModel;
    List<QuestionResModel> quizQuestionList;
    /*Camera Params*/
    OptionResModel optionResModel;
    boolean clickPictureStatus;
    CustomProgressDialog customFinishProgressDialog;


    // CAMERA VERSION TWO DECLARATIONS
    private Camera2Source mCamera2Source = null;

    // CAMERA VERSION ONE DECLARATIONS
    private CameraSource mCameraSource = null;
    private Camera3Source mCamera3Source = null;
    private CameraSourcePreview preview;


    // COMMON TO BOTH CAMERAS
    private CameraSourcePreview mPreview;
    private FaceDetector previewFaceDetector = null;

    // DEFAULT CAMERA BEING OPENED
    private boolean usingFrontCamera = true;

    // MUST BE CAREFUL USING THIS VARIABLE.
    // ANY ATTEMPT TO START CAMERA2 ON API < 21 WILL CRASH.
    private boolean useCamera2 = false;

    private GraphicOverlay mGraphicOverlay;
    int cameraID = 0;
    public static boolean status;
    AlertDialog dialog;
    Dialog dialogFinish;
    ExitDialog attemptedExitDialog;
    Timer timer;
    Timer clickPictureTimer;
    int countQuiz = 0;
    DashboardResModel dashboardResModel;
    QuizResModel quizResModel;
    AssignmentReqModel assignmentReqModel;

    private BroadcastReceiver mNetworkReceiver;
    public static AlertDialog internetDialog;

    boolean apiCalling = false;
    boolean interDialogOpen = false;
    boolean alreadyAttemptedDialog = false;

    String buttonText = "";
    QuestionResModel questionResModel;
    FaceDetector.Detections<Face> detectionResultsList;
    boolean finishDialogClick;
    String submitStatus;
    boolean submitQuizFromExitDialog = false;
    boolean isQuizStarted = false;
    AssignmentResModel assignmentResModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizTestNativeViewModel.class);
        binding.setLifecycleOwner(this);

        StudentMainDashboardActivity.setListingActiveFragment(StudentMainDashboardActivity.NATIVE_QUIZ_FRAGMENT);
        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
            quizResModel = getArguments().getParcelable(AppConstant.QUIZ_RES_MODEL);
        }
        setRetainInstance(true);
        handleErrorLayout(1, "");
        openInstructionDialog();
        callFetchQuestionApi();
        /*Register Broad Cast*/

        registerNetworkBroadcastForNougat();
        AppUtil.callBackListner = this;
        return binding.getRoot();
    }

    @Override
    protected void init() {
        setListener();
        if (hasFrontCamera()) {
            cameraID = Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        setTimerProgress(countQuiz + 1);
        useCamera2 = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        if (DeviceUtil.getManufacturer().equalsIgnoreCase(AppConstant.ManufacturerName.SAMSUNG)) {
            useCamera2 = false;
        }
        if (dashboardResModel != null && quizResModel != null) {
            assignmentReqModel = viewModel.quizNativeUseCase.getAssignmentRequestModel(dashboardResModel, quizResModel);
            //    quizTestViewModel.getAssignExamData(assignmentReqModel);
        }
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        assignmentResModel = prefModel.getAssignmentResModel();
        mGraphicOverlay = binding.faceOverlay;
        mPreview = binding.preview;
        stopCameraSource();
        clickPictureStore();


    }

    private void setQuizData(QuestionResModel model) {
        setTextOfButton();
        questionResModel = model;
        startCountdownTimer();
        if (!TextUtil.isEmpty(model.getImageName())) {
            binding.quesImg.setVisibility(View.VISIBLE);
            ImageUtil.loadNormalImage(binding.quesImg, model.getImageName());
        } else {
            binding.quesImg.setVisibility(View.GONE);
        }
        String sourceString = "<b>" + (countQuiz + 1) + ". ) " + "</b> " + " " + model.getQuestion();
        binding.subjectTitle.setText(quizResModel.getSubjectName());
        setQuestionText(sourceString);
        setOptionAdapter(model.getOptionResModelList());
    }

    private void setQuestionText(String quesText) {
        binding.quesText.setText(Html.fromHtml(quesText));
    }

    private void callFetchQuestionApi() {
        if (!apiCalling) {
            apiCalling = true;
            if (dashboardResModel != null && quizResModel != null) {
                assignmentReqModel = viewModel.quizUseCase.getAssignmentRequestModel(dashboardResModel, quizResModel);
            }
            viewModel.fetchQuizData(assignmentReqModel);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCameraSource();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.serviceLiveData().removeObservers(this);
        if (timer != null) {
            timer.cancel();
        }
        if (clickPictureTimer != null) {
            clickPictureTimer.cancel();
        }

        unregisterNetworkChanges();
        stopCameraSource();
        if (previewFaceDetector != null) {
            previewFaceDetector.release();
        }


        if (((StudentMainDashboardActivity) getActivity()).dialogQuit != null && ((StudentMainDashboardActivity) getActivity()).dialogQuit.isShowing()) {
            ((StudentMainDashboardActivity) getActivity()).dialogQuit.dismiss();
        }
        if (timer != null) {
            timer.cancel();
        }
        if (clickPictureTimer != null) {
            clickPictureTimer.cancel();
        }
        if (countDownTimerofme != null) {
            countDownTimerofme.cancel();
        }
        closeCameraDialog();
        stopCameraSource();
        if (mCamera2Source != null) {
            mCamera2Source.release();
        }
        if (mCameraSource != null) {
            mCameraSource.release();
        }

        closeAllDialog();

    }

    void closeCameraDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            closeCameraDialog();
        }
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.progressBar.setOnClickListener(this);
        binding.exitBt.setOnClickListener(this);
        binding.saveNextBt.setOnClickListener(this);
        binding.quesImg.setOnTouchListener(this);
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }

        binding.quesImg.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.quiz_test_native_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setToolbar();
        setListener();
    }

    @Override
    public void onResume() {
        super.onResume();
        AppUtil.callBackListner = this;
        AppUtil.dashboardResModel = null;
        if (checkGooglePlayAvailability()) {
            askPermission();
        }
        try {
            AppLogger.e("chhonker", "try block");
          /*  AudioManager mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_ALARM, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_RING, true);
            mAudioManager.setStreamMute(AudioManager.STREAM_SYSTEM, true);*/
        } catch (Exception e) {
            AppLogger.e("chhonker", "catch block-" + e.getMessage());
        }
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case OPTION_SELECT_API:
                optionResModel = (OptionResModel) commonDataModel.getObject();
                break;

            case NO_INTERNET_BROADCAST:
                boolean val = (boolean) commonDataModel.getObject();
                if (val) {
                    checkInternetBeforeCallApi();
                } else {
                    openNoInterNetDialog(val);
                }

                break;
            case EXIT_DIALOG_CLICK:
                getActivity().getSupportFragmentManager().popBackStack();
                break;

            case FINISH_DIALOG_CLICK:
                finishDialogClick = true;
                submitStatus = AppConstant.QuizFinishStatus.EXIT_BY_STUDENT;
                submitQuizFromExitDialog = true;
                callSaveQuestionApi();
                break;

            case LISTNER_SUCCESS:
                AppLogger.v("SaveQuiz","Step 001"+"Assignment req Model"+quizResModel.getSubjectPos());
                AppUtil.dashboardResModel = (DashboardResModel) commonDataModel.getObject();
                //closeAllDialog();
               // closeProgress();
                customFinishProgressDialog.dismiss();
                getActivity().getSupportFragmentManager().popBackStack();
              /*  getActivity().getSupportFragmentManager().popBackStack();*/
                break;
            case LISTNER_FAIL:
                /*Do code here*/
                closeProgress();
                customFinishProgressDialog.dismiss();
                getActivity().getSupportFragmentManager().popBackStack();
                break;

        }
    }

    void checkInternetBeforeCallApi() {
        openNoInterNetDialog(true);
        viewModel.quizNativeRemoteUseCase.isAvailInternet().subscribe(hasInternet -> {
            if (hasInternet) {
                binding.progressBar.setVisibility(View.VISIBLE);
                callFetchQuestionApi();
            } else {
                // please check your internet
                AppLogger.e(TAG, "checkInternetBeforeCallApi");
                openNoInterNetDialog(false);
            }
        });


    }

    void openNoInterNetDialog(boolean value) {
        if (getActivity() != null) {
            setTextOfButton();
            if (!value) {
                interDialogOpen = true;
                if (internetDialog != null && internetDialog.isShowing()) {
                    internetDialog.dismiss();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                if (!isQuizStarted) {
                    builder.setMessage(getActivity().getResources().getString(R.string.quiz_internet));
                } else {
                    builder.setMessage(getActivity().getResources().getString(R.string.quiz_internet_2));
                }
                // Set the alert internetDialog yes button click listener
                builder.setPositiveButton(Html.fromHtml("<font color='#00A1DB'>" + getActivity().getResources().getString(R.string.retry) + "</font>"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        interDialogOpen = false;
                        dialog.dismiss();
                        checkInternetBeforeCallApi();
                    }
                });
                internetDialog = builder.create();
                internetDialog.setCancelable(false);
                // Display the alert internetDialog on interface

                if (customFinishProgressDialog != null && customFinishProgressDialog.isShowing()) {
                    customFinishProgressDialog.dismiss();
                }
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                closeAllDialog();
                internetDialog.show();
            } else {
                if (internetDialog != null && internetDialog.isShowing()) {
                    interDialogOpen = false;
                    internetDialog.dismiss();
                }
            }
        }
    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.exit_bt) {
            ((StudentMainDashboardActivity) getActivity()).openDialogForQuit();
        } else if (v.getId() == R.id.save_next_bt) {
            AppLogger.i("Countdown", "onClick");
            if (!interDialogOpen) {
                if (binding.saveNextBt.getText().toString().equalsIgnoreCase(getActivity().getResources().getString(R.string.save_submit))) {
                    openDialogFinishQuiz();
                } else {
                    callSaveQuestionApi();
                    /*if (optionResModel != null) {
                        callSaveQuestionApi();
                    } else {
                        goToNextQuiz();
                    }
*/
                }
            }


            // initRecordingTimer();

        } else if (v.getId() == R.id.ques_img) {
            NativeQuizImageDialog yesNoAlert = NativeQuizImageDialog.newInstance(questionResModel.getImageName());
            yesNoAlert.show(getParentFragmentManager(), null);
        }
    }

    private boolean checkGooglePlayAvailability() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode == ConnectionResult.SUCCESS) {
            return true;
        } else {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(), resultCode, 2404).show();
            }
        }
        return false;
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    /*Camera Permisiipon*/
    private void askPermission() {
        String rationale = "Please provide camera permission so that you can ...";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                createCameraSourceFront();

                createCameraSource();
                startCamera3Source();
                startCameraSource();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.

            }
        });
    }

    private void startCamera3Source() {
        if (mCamera3Source != null) {
            try {
                if (preview == null) {
                    AppLogger.d(TAG, "resume: Preview is null");
                }
                if (mGraphicOverlay == null) {
                    AppLogger.d(TAG, "resume: graphOverlay is null");
                    preview.start(mCameraSource, mGraphicOverlay);
                }

            } catch (IOException e) {
                mCamera3Source.release();
                mCamera3Source = null;
            }
        }
    }
    private void createCameraSource() {
        // If there's no existing cameraSource, create one.
        if (mCamera3Source == null) {
            //
            mCamera3Source = new Camera3Source(getActivity(), mGraphicOverlay);
        }
        mCamera3Source.setMachineLearningFrameProcessor(new CloudLandmarkRecognitionProcessor());
    }


    private void stopCameraSource() {
        mPreview.stop();
    }

    private void createCameraSourceFront() {
        previewFaceDetector = new FaceDetector.Builder(getActivity())
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .setProminentFaceOnly(true)
                .setTrackingEnabled(true)
                .build();

        if (previewFaceDetector.isOperational()) {
            previewFaceDetector.setProcessor(new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory()).build());
        } else {
            Toast.makeText(getActivity(), "FACE DETECTION NOT AVAILABLE", Toast.LENGTH_SHORT).show();
        }

        if (useCamera2) {
            mCamera2Source = new Camera2Source.Builder(getActivity(), previewFaceDetector)
                    .setFocusMode(Camera2Source.CAMERA_AF_AUTO)
                    .setFlashMode(Camera2Source.CAMERA_FLASH_AUTO)
                    .setFacing(Camera2Source.CAMERA_FACING_FRONT)
                    .build();

            //IF CAMERA2 HARDWARE LEVEL IS LEGACY, CAMERA2 IS NOT NATIVE.
            //WE WILL USE CAMERA1.
            if (mCamera2Source.isCamera2Native()) {
                startCameraSource();
            } else {
                useCamera2 = false;
                createCameraSourceFront();

            }
        } else {
            mCameraSource = new CameraSource.Builder(getActivity(), previewFaceDetector)
                    .setFacing(CameraSource.CAMERA_FACING_FRONT)
                    .setRequestedFps(30.0f)
                    .build();
            startCameraSource();
        }
    }

    private void startCameraSource() {
        if (useCamera2) {
            if (mCamera2Source != null) {
                AppLogger.e("Camera", "camera  2.");
                try {
                    mPreview.start(mCamera2Source, mGraphicOverlay);
                } catch (IOException e) {
                    AppLogger.e("Camera", "Unable to start camera source 2." + e);
                    mCamera2Source.release();
                    mCamera2Source = null;
                }
            }
        } else {
            if (mCameraSource != null) {
                AppLogger.e("Camera", "camera  1.");
                try {
                    mPreview.start(mCameraSource, mGraphicOverlay);
                } catch (IOException e) {
                    AppLogger.e("Camera", "Unable to start camera source ." + e);
                    mCameraSource.release();
                    mCameraSource = null;
                }
            }
        }
    }


    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            // status = true;
            AppLogger.e("chhonker", "face detetcted");
        }


        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            //  AppLogger.e("chhonker", "--onUpdate face" + detectionResults.getDetectedItems().size());
            if (detectionResults.getDetectedItems().size() == 1) {
                status = true;
                //AppLogger.e("chhonker", "--onUpdate face 1 , size" + detectionResults.getDetectedItems().size());
            } else {
                status = false;
                // AppLogger.e("chhonker", "--onUpdate face 2, size" + detectionResults.getDetectedItems().size());

            }

        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            //detectionResultsList=detectionResults;

            AppLogger.e("chhonker", "--ondetection face" + detectionResults.getDetectedItems().size());
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            AppLogger.e("chhonker", "--on done face");
            status = false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();


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

    private void checkValueEverySecond() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //   Toast.makeText(getActivity(), "status " + status, Toast.LENGTH_SHORT).show();
                            if (!status) {
                                if (dialog == null) {
                                  //  openDialog();
                                } else {
                                    if (!dialog.isShowing() & !apiCalling && isVisible() && !interDialogOpen && !alreadyAttemptedDialog) {
                                        dialog.show();
                                    }
                                }
                            } else {
                                if (dialog != null) {
                                    dialog.dismiss();
                                }
                            }

                        }
                    });
                }

            }

        }, 0, 700);
    }


    final Camera2Source.ShutterCallback camera2SourceShutterCallback = new Camera2Source.ShutterCallback() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onShutter() {
            AppLogger.d(TAG, "Shutter Callback for CAMERA2");
        }
    };

    final Camera2Source.PictureCallback camera2SourcePictureCallback = new Camera2Source.PictureCallback() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onPictureTaken(Image image) {
            clickPictureStatus = false;
            AppLogger.d(TAG, "Taken picture is here!");
            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes);
            Bitmap picBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
            processImage(picBitmap);
            image.close();
        }
    };

    final CameraSource.ShutterCallback cameraSourceShutterCallback = new CameraSource.ShutterCallback() {
        @Override
        public void onShutter() {
            AppLogger.d(TAG, "Shutter Callback!");
        }
    };

    final CameraSource.PictureCallback cameraSourcePictureCallback = new CameraSource.PictureCallback() {
        @Override
        public void onPictureTaken(Bitmap picture) {
            clickPictureStatus = false;
           /* AudioManager mgr = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
            int shutterSound = soundPool.load(this, R.raw.camera_click, 0);
            soundPool.play(shutterSound, 1f, 1f, 0, 0, 1);*/
            AppLogger.d(TAG, "Taken picture is here!");
            processImage(picture);
        }
    };

/*    private void processImage(Bitmap picBitmap) {
        byte[] image_bytes = viewModel.encodeToBase64(picBitmap, 100);
        long mb = viewModel.bytesIntoHumanReadable(image_bytes.length);
        if (mb > 1.5) {
            AppLogger.e("chhonker", "size of the image greater than 1.5 mb -" + mb);
            callSendExamImageApi(viewModel.encodeToBase64(picBitmap, 50));
            AppLogger.e("chhonker", "size of the image greater than 1.5 mb after conversion -" + mb);
        } else {
            AppLogger.e("chhonker", "size of the image less 1.5 mb -" + mb);
            callSendExamImageApi(image_bytes);
        }
    }*/
    void processImage(Bitmap picBitmap) {
        byte[] bytes = AppUtil.encodeToBase64(picBitmap, 100);
        long mb = AppUtil.bytesIntoHumanReadable(bytes.length);
        int file_size = Integer.parseInt(String.valueOf(bytes.length / 1024));
        //  AppLogger.d(TAG, "Image Path Size mb- " + mb + "-bytes-" + file_size);
        if (file_size >= 500) {
            assignmentReqModel.setImageBytes(AppUtil.encodeToBase64(picBitmap, 50));
        } else {
            assignmentReqModel.setImageBytes(bytes);
        }
        int new_file_size = Integer.parseInt(String.valueOf(assignmentReqModel.getImageBytes().length / 1024));
        //AppLogger.d(TAG, "Image Path  new Size kb- " + mb + "-bytes-" + new_file_size);
        //callSendExamImageApi();
    }

    private void clickPicture() {
        if (useCamera2) {
            if (mCamera2Source != null)
                mCamera2Source.takePicture(camera2SourceShutterCallback, camera2SourcePictureCallback);
        } else {
            if (mCameraSource != null)
                mCameraSource.takePicture(cameraSourceShutterCallback, cameraSourcePictureCallback);
        }
    }


    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.not_showing_face);
        // Set the alert dialog yes button click listener
        String ok = getActivity().getResources().getString(R.string.ok);
        builder.setPositiveButton(Html.fromHtml("<font color='#00A1DB'>" + ok + "</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Set the alert dialog no button click listener
       /* builder.setNegativeButton(Html.fromHtml("<font color='#00A1DB'>NO</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/

        dialog = builder.create();
        dialog.setCancelable(false);
        // Display the alert dialog on interface
    }


    private void clickPictureStore() {
        clickPictureTimer = new Timer();
        clickPictureTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            if (status && !clickPictureStatus) {
                                clickPictureStatus = true;
                                clickPicture();
                            }
                        }
                    });
                }

            }

        }, 0, 7000);


    }

    public void setOptionAdapter(List<OptionResModel> listQuizOption) {
        binding.rvselectQuizOption.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvselectQuizOption.setHasFixedSize(true);
        binding.rvselectQuizOption.setNestedScrollingEnabled(false);
        mcSelectQuizAdapter = new SelectQuizOptionAdapter(getActivity(), listQuizOption, this);
        binding.rvselectQuizOption.setAdapter(mcSelectQuizAdapter);

    }

    CountDownTimer countDownTimerofme = new CountDownTimer(START_TIME_IN_MILLIS, COUNTDOWN_INTERVAL) {
        @Override
        public void onTick(long millisUntilFinished) {
            int numberOfSeconds = START_TIME_IN_MILLIS / COUNTDOWN_INTERVAL;
            int secondsRemaining = (int) (millisUntilFinished / COUNTDOWN_INTERVAL);
            int factor = 100 / numberOfSeconds;

            NumberFormat dateformat = new DecimalFormat("00");
            long min = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished));
            binding.timerText.setText(dateformat.format(min) + ":" + dateformat.format(seconds));
            //  binding.timerText.setText("" + String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            //  binding.timerText.setText("" + String.format("%d : %d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished), TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            int progressPercentage = (int) (numberOfSeconds - secondsRemaining) * factor;
        }

        @Override
        public void onFinish() {
            binding.timerText.setText("00:00");
            if (countQuiz == quizQuestionList.size() - 1) {
                submitStatus = AppConstant.QuizFinishStatus.AUTO_SUBMITTED;
            }
            if (!interDialogOpen) {
                callSaveQuestionApi();
              /*  if (optionResModel != null) {
                   //
                } else {*/
               /* if (countQuiz == quizQuestionList.size() - 1) {
                    callSaveQuestionApi();
                    //callFinishQuizApi();
                } else {
                    //   goToNextQuiz();
                    callSaveQuestionApi();
                }*/
                //}
            }

        }
    };

    public void startCountdownTimer() {
        countDownTimerofme.start();
    }

    public void stopmeplease() {
        countDownTimerofme.cancel();
    }

    public void goToNextQuiz() {
        if (countQuiz < quizQuestionList.size()) {
            countQuiz++;
        }
        setTimerProgress(countQuiz + 1);
        if (countQuiz < quizQuestionList.size()) {
            stopmeplease();
            AppLogger.i(TAG, "goToNextQuiz");
            setQuizData(quizQuestionList.get(countQuiz));
        }
        setTextOfButton();

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
     /*   Dialog nagDialog = new Dialog(getActivity(),android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(false);
        nagDialog.setContentView(R.layout.preview_image);
        ImageView ivPreview = (ImageView)nagDialog.findViewById(R.id.iv_preview_image);
        ivPreview.setBackgroundDrawable();
        nagDialog.show();*/

    }

    private void observeServiceResponse() {
        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {
                case SUCCESS:
                    isQuizStarted = true;
                    apiCalling = false;
                    AppLogger.e(TAG, "SUCCESS");
                    checkResponse(responseApi);
                    break;

                case NO_INTERNET:
                    apiCalling = false;
                    closeAllDialog();
                    setTextOfButton();
                    openNoInterNetDialog(false);
                    binding.progressBar.setVisibility(View.GONE);
                    break;

                case AUTH_FAIL:
                case FAIL_400:
                    apiCalling = false;
                    AppLogger.e(TAG, "AUTH_FAIL");
                    setTextOfButton();
                    binding.progressBar.setVisibility(View.GONE);
                    closeAllDialog();
                    break;
                default:
                    apiCalling = false;
                    closeAllDialog();
                    if (getActivity() != null) {
                        setTextOfButton();
                        binding.progressBar.setVisibility(View.GONE);
                        AppLogger.e(TAG, "FAIL_400");
                    }
                    break;
            }
        });
    }

    private void closeAllDialog() {
        AppLogger.e("closeAllDialog-", "i am calling");
        if (customFinishProgressDialog != null && customFinishProgressDialog.isShowing()) {
            customFinishProgressDialog.dismiss();
            AppLogger.e("closeAllDialog-", "" + customFinishProgressDialog.isShowing());
        }
        if (customInstructionDialog != null && customInstructionDialog.isShowing()) {
            customInstructionDialog.dismiss();
        }
        if (dialogFinish != null && dialogFinish.isShowing()) {
            dialogFinish.dismiss();
        }

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (attemptedExitDialog != null && attemptedExitDialog.isShowing()) {
            alreadyAttemptedDialog = false;
            attemptedExitDialog.dismiss();
        }

        closeProgress();

    }

    private void closeProgress() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.progressBar.setVisibility(View.GONE);
            }
        }, 500);
    }

    void closeInstructionDialog() {
        if (customInstructionDialog != null) {
            customInstructionDialog.dismiss();
        }
    }

    private void openInstructionDialog() {
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());//bug report on 06/07/2020
        customDialogModel.setTitle(getActivity().getResources().getString(R.string.quiz_instruction));
        customDialogModel.setContent(getActivity().getResources().getString(R.string.bullted_list));
        customDialogModel.setTwoButtonRequired(false);
        if (getActivity() != null) {
            customInstructionDialog = new CustomDialog(getActivity(), customDialogModel);
            // Window window = customDialog.getWindow();
            // window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(customInstructionDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            customInstructionDialog.getWindow().setAttributes(lp);
            Objects.requireNonNull(customInstructionDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customInstructionDialog.setCancelable(false);
            customInstructionDialog.show();
        }

    }


    private void makeOptionsList() {
        for (QuestionResModel resModel : fetchQuizResModel.getQuestionResModelList()) {
            List<OptionResModel> list = new ArrayList<>();
            String jsonString = resModel.getOptionDetails().replaceAll("\\\\", "");
            AppLogger.e(TAG, "json string-" + jsonString);
            Map<String, String> retMap = new Gson().fromJson(
                    jsonString, new TypeToken<HashMap<String, Object>>() {
                    }.getType()
            );
            Iterator myVeryOwnIterator = retMap.keySet().iterator();
            while (myVeryOwnIterator.hasNext()) {
                String key = (String) myVeryOwnIterator.next();
                String value = (String) retMap.get(key);
                if (!TextUtil.isEmpty(value)) {
                    String option[] = value.split("_");
                    OptionResModel optionResModel = new OptionResModel();
                    optionResModel.setCheck(false);
                    optionResModel.setOption(option[1]);
                    optionResModel.setIndex(key);
                    optionResModel.setOptionId(option[0]);
                    list.add(optionResModel);
                }
            }
            resModel.setOptionResModelList(list);
            AppLogger.e(TAG, "retMap-" + retMap);
        }
    }

    private void handleErrorLayout(int status, String msg) {
        switch (status) {
            case 1:
                AppLogger.e(TAG, "handleErrorLayout 1");
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.mainContentLayout.setVisibility(View.GONE);
                binding.errorLayout.errorIcon.setVisibility(View.GONE);
                binding.errorLayout.textError.setText(msg);
                binding.errorLayout.btRetry.setVisibility(View.GONE);
                break;
            case 2:
                AppLogger.e(TAG, "handleErrorLayout 2");
                binding.errorConstraint.setVisibility(View.GONE);
                binding.mainContentLayout.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void callSaveQuestionApi() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        AppLogger.e(TAG, "callSaveQuestionApi 1");
        if (optionResModel == null) {
            AppLogger.e(TAG, "callSaveQuestionApi 2");
            optionResModel = new OptionResModel();
            optionResModel.setOptionId("0");
            optionResModel.setOption("");
            optionResModel.setCheck(false);
            optionResModel.setIndex("0");
        }
        if (!apiCalling) {
            apiCalling = true;
            buttonText = binding.saveNextBt.getText().toString();
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.saveNextBt.setText(getActivity().getResources().getString(R.string.saving));
            SaveQuestionResModel saveQuestionResModel = new SaveQuestionResModel();
            saveQuestionResModel.setAnswerID(optionResModel.getOptionId());
            saveQuestionResModel.setExamAssignmentID(fetchQuizResModel.getExamAssignmentID());
            saveQuestionResModel.setQuestionID("" + quizQuestionList.get(countQuiz).getQuestionID());
            saveQuestionResModel.setQuestionSerialNo("" + (countQuiz + 1));
            saveQuestionResModel.setExamId(assignmentResModel.getExamId());
            viewModel.saveQuizData(saveQuestionResModel);
        }
    }

    private void callFinishQuizApi() {
        if (!apiCalling) {
            apiCalling = true;
            openFinishProgressDialog();
            SaveQuestionResModel saveQuestionResModel = new SaveQuestionResModel();
            saveQuestionResModel.setExamAssignmentID(fetchQuizResModel.getExamAssignmentID());
            saveQuestionResModel.setComplete_by(submitStatus);
            saveQuestionResModel.setExamId(assignmentResModel.getExamId());
            viewModel.finishQuiz(saveQuestionResModel);
        }
    }


    private void openDialogFinishQuiz() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getActivity().getString(R.string.finish_quiz_txt));
        // Set the alert dialog yes button click listener
        String yes = getActivity().getResources().getString(R.string.yes);
        String no = getActivity().getResources().getString(R.string.no);
        builder.setPositiveButton(Html.fromHtml("<font color='#00A1DB'>" + yes + "</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogQuit, int which) {
                submitStatus = AppConstant.QuizFinishStatus.STUDENT;
                callSaveQuestionApi();
                dialogQuit.dismiss();

            }
        });
        // Set the alert dialog no button click listener
        builder.setNegativeButton(Html.fromHtml("<font color='#00A1DB'>" + no + "</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogQuit, int which) {
                dialogQuit.dismiss();

            }
        });

        dialogFinish = builder.create();
        dialogFinish.setCancelable(false);
        dialogFinish.show();
        // Display the alert dialog on interface
    }

    void checkResponse(ResponseApi responseApi) {
        switch (responseApi.apiTypeStatus) {
            case FETCH_QUIZ_DATA_API:
                AppLogger.e(TAG, "progressbar lock ");
                closeProgress();
                fetchQuizResModel = (FetchQuizResModel) responseApi.data;
                if (fetchQuizResModel.getError()) {
                    closeInstructionDialog();
                    openAlreadyAttemptedQuiz(getActivity().getResources().getString(R.string.quiz_error));
                } else {
                    if (fetchQuizResModel != null && fetchQuizResModel.getQuizStatus().equalsIgnoreCase(AppConstant.QUIZ_ATTEMPTED)) {
                        closeInstructionDialog();
                        openAlreadyAttemptedQuiz(getActivity().getResources().getString(R.string.already_finished));
                    } else {
                        if (customInstructionDialog != null) {
                            quizQuestionList = fetchQuizResModel.getQuestionResModelList();
                            if (fetchQuizResModel.getQuestionResModelList() != null && fetchQuizResModel.getQuestionAttemped() == fetchQuizResModel.getQuestionResModelList().size()) {
                                countQuiz = fetchQuizResModel.getQuestionResModelList().size() - 1;
                                AppLogger.e("chhonke newr-", "coint size--" + countQuiz);
                            } else {
                                if (fetchQuizResModel.getQuestionAttemped() > 0) {
                                    countQuiz = fetchQuizResModel.getQuestionAttemped();
                                    setTimerProgress(countQuiz + 1);
                                    AppLogger.e("chhonker old-", "coint size--" + countQuiz);
                                }
                            }
                            closeInstructionDialog();
                            makeOptionsList();
                            handleErrorLayout(2, "");
                            setQuizData(quizQuestionList.get(countQuiz));
                            checkValueEverySecond();
                        }
                    }
                }
                break;

            case FINISH_QUIZ_API:
                SaveQuestionResModel resModel = (SaveQuestionResModel) responseApi.data;
                AppLogger.v("SaveQuiz","Step 1"+resModel.getSubmitExamAPIResult().getScore());
                AppLogger.v("SaveQuiz","Step 2"+resModel.getSubmitExamAPIResult().getSuccessFlag());
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (resModel.getSubmitExamAPIResult() != null && resModel.getSubmitExamAPIResult().getSuccessFlag()) {
                    AppLogger.v("SaveQuiz","Step 002"+"Assignment req Model"+quizResModel.getSubjectPos());

                    AppUtil.dashboardQuizScore = resModel.getSubmitExamAPIResult().getScore();
                    cancelDialogAfterSubmittingTest();
                }
                break;

            case SAVE_QUIZ_DATA_API:
                closeProgress();
                SaveQuestionResModel data = (SaveQuestionResModel) responseApi.data;
                AppLogger.e(TAG, "SAVE_QUIZ_DATA_API 1");
                if (!data.getError()) {
                    AppLogger.e(TAG, "SAVE_QUIZ_DATA_API 2");
                    if (data.getSaveAnswerResModel().getSave()) {
                        AppLogger.e(TAG, "SAVE_QUIZ_DATA_API 3 --" + binding.saveNextBt.getText().toString());
                        if (submitQuizFromExitDialog) {
                            AppLogger.e(TAG, "SAVE_QUIZ_DATA_API 4");
                            submitQuizFromExitDialog = false;
                            callFinishQuizApi();
                        } else {
                            if (buttonText.equalsIgnoreCase(getActivity().getResources().getString(R.string.save_submit))) {
                                AppLogger.e(TAG, "SAVE_QUIZ_DATA_API 5");
                                callFinishQuizApi();
                            } else {
                                AppLogger.e(TAG, "SAVE_QUIZ_DATA_API 6");
                                setTextOfButton();
                                closeProgress();
                                optionResModel = null;
                                goToNextQuiz();
                            }
                        }

                    }
                } else {
                    AppLogger.e(TAG, "SAVE_QUIZ_DATA_API 5");
                    setTextOfButton();
                    closeProgress();
                    optionResModel = null;
                }
                break;
        }
    }


    private void setTextOfButton() {
        if (quizQuestionList != null && countQuiz == quizQuestionList.size() - 1) {
            binding.saveNextBt.setText(getActivity().getResources().getString(R.string.save_submit));
        } else {
            binding.saveNextBt.setText(getActivity().getResources().getString(R.string.save_next));
        }
    }

    private void openFinishProgressDialog() {
        AppLogger.e("openFinishProgressDialog-", "openFinishProgressDialog");
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle(getActivity().getResources().getString(R.string.finish_your_text));
        customDialogModel.setContent(getActivity().getResources().getString(R.string.bullted_list));
        customDialogModel.setTwoButtonRequired(false);
        customFinishProgressDialog = new CustomProgressDialog(getActivity(), customDialogModel);
        Objects.requireNonNull(customFinishProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customFinishProgressDialog.setCancelable(false);
        customFinishProgressDialog.show();
        customFinishProgressDialog.setProgressDialogText(getActivity().getResources().getString(R.string.finish_your_text));
    }


    private void callSendExamImageApi() {

        if (!TextUtil.isEmpty(assignmentResModel.getExamAssignmentID())) {
            if (assignmentResModel != null && !TextUtil.isEmpty(assignmentResModel.getExamAssignmentID())) {
                SaveImageReqModel saveQuestionResModel = new SaveImageReqModel();
                saveQuestionResModel.setImageBytes(assignmentReqModel.getImageBytes());
                saveQuestionResModel.setExamId(assignmentResModel.getExamAssignmentID());
                saveQuestionResModel.setQuizId(assignmentResModel.getQuizId());
                saveQuestionResModel.setImgNormalPath(assignmentResModel.getImgNormalPath());
                saveQuestionResModel.setImgPath(assignmentResModel.getImgPath());
                PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
                DashboardResModel dashboardResModel = prefModel.getDashboardResModel();
                saveQuestionResModel.setRegistration_id(dashboardResModel.getAuroid());

                viewModel.uploadExamFace(saveQuestionResModel);
            }
        }
    }


    /*BroadCast Code Here*/
    private void registerNetworkBroadcastForNougat() {
        mNetworkReceiver = new NetworkChangeReceiver();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            getActivity().registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getActivity().registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            getActivity().unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    private void openAlreadyAttemptedQuiz(String msg) {
        closeInstructionDialog();
    /*    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg);
        // Set the alert dialog yes button click listener
        builder.setPositiveButton(Html.fromHtml("<font color='#00A1DB'>Exit</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogQuit, int which) {
                dialogQuit.dismiss();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        // Set the alert dialog no button click listener

        Dialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();*/

        if (getActivity() != null) {
            alreadyAttemptedDialog = true;
            attemptedExitDialog = new ExitDialog(getActivity(), msg);
            attemptedExitDialog.setCancelable(false);
            attemptedExitDialog.show();

        }
        // Display the alert dialog on interface
    }

    private void cancelDialogAfterSubmittingTest() {
        if (!viewModel.quizNativeUseCase.checkDemographicStatus(dashboardResModel)) {
            closeAllDialog();
            AppLogger.v("SaveQuiz","Step 003"+"Assignment req Model"+quizResModel.getSubjectPos());
            openDemographicFragment();
        } else {
            //open quiz home fragment
            AppLogger.v("SaveQuiz","Step 004"+"Assignment req Model"+quizResModel.getSubjectPos());

            callDashboardApi();
            //  getActivity().getSupportFragmentManager().popBackStack();
        }
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null) {
            assignmentReqModel.setSubjectPos(quizResModel.getSubjectPos());
            prefModel.setAssignmentReqModel(assignmentReqModel);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    public void openDemographicFragment() {
        Bundle bundle = new Bundle();
        AppLogger.v("SaveQuiz","Step 4"+"DemoGraphic Fragment");
        DemographicFragment demographicFragment = new DemographicFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        demographicFragment.setArguments(bundle);
        openFragment(demographicFragment);
    }


    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this.getContext())).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, MainQuizHomeFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    private void callDashboardApi() {
        ((StudentMainDashboardActivity) getActivity()).setListner(this);
        ((StudentMainDashboardActivity) getActivity()).callDashboardApi();
    }

    private void setTimerProgress(int progress) {
        binding.timerProgressbar.setProgress(progress);

    }


}