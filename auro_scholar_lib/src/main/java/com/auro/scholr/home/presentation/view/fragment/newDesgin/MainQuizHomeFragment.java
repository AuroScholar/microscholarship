package com.auro.scholr.home.presentation.view.fragment.newDesgin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FragmentMainQuizHomeBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DynamiclinkResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.SelectChapterQuizModel;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.home.presentation.view.activity.newUi.StudentNewDashBoardActivity;
import com.auro.scholr.home.presentation.view.adapter.newuiadapter.ChapterSelectAdapter;
import com.auro.scholr.home.presentation.view.adapter.newuiadapter.SubjectSelectAdapter;
import com.auro.scholr.home.presentation.view.fragment.CongratulationsDialog;
import com.auro.scholr.home.presentation.view.fragment.ConsgratuationLessScoreDialog;
import com.auro.scholr.home.presentation.view.fragment.QuizTestFragment;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.BACK_CLICK;
import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.NEXT_QUIZ_CLICK;
import static com.auro.scholr.core.common.Status.START_QUIZ_BUTON;
import static com.auro.scholr.core.common.Status.SUBJECT_CLICKED;


public class MainQuizHomeFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("QuizHomeFragment")
    ViewModelFactory viewModelFactory;

    FragmentMainQuizHomeBinding binding;
    QuizViewModel quizViewModel;
    DashboardResModel dashboardResModel;

    String TAG = MainQuizHomeFragment.class.getSimpleName();
    QuizResModel quizResModel;
    AssignmentReqModel assignmentReqModel;

    public MainQuizHomeFragment() {
        // Required empty public constructor
    }

    ChapterSelectAdapter adapter;

    List<SelectChapterQuizModel> laugList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        quizViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setQuizViewModel(quizViewModel);
        setRetainInstance(true);
        init();
        setListener();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((StudentNewDashBoardActivity) getActivity()).setListner(this);
    }

    @Override
    protected void init() {

        AppLogger.e(TAG, DateUtil.getMonthName());
        if (!TextUtil.isEmpty(DateUtil.getMonthName())) {
            binding.RPTextView9.setText(DateUtil.getMonthName() + " " + getActivity().getResources().getString(R.string.scholarship));
        }
        callDasboardApi();
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void setListener() {
        StudentNewDashBoardActivity.setListingActiveFragment(StudentNewDashBoardActivity.QUIZ_DASHBOARD_FRAGMENT);
        binding.languageLayout.setOnClickListener(this);
        binding.walleticon.setOnClickListener(this);
        binding.cardView2.setOnClickListener(this);
        binding.privacyPolicy.setOnClickListener(this);
        binding.quizSelectionSheet.sheetLayoutQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFadeOutSelectionLayout();

            }
        });
        binding.quizSelectionSheet.cardviewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if (quizViewModel != null && quizViewModel.serviceLiveData().hasObservers()) {
            quizViewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
    }

    public void setAdapterChapter(SubjectResModel subjectResModel) {
        binding.quizSelectionSheet.RPScore.setText(getString(R.string.rs) + " " + quizViewModel.homeUseCase.getCurrentMonthWalletBalance(dashboardResModel));
        binding.quizSelectionSheet.RPSubject.setText(subjectResModel.getSubject());
        String[] listArrayLanguage = getResources().getStringArray(R.array.classes);
        laugList = new ArrayList();
        for (String lang : listArrayLanguage) {
            SelectChapterQuizModel selectChapterModel = new SelectChapterQuizModel();
            selectChapterModel.setCheck(false);
            laugList.add(selectChapterModel);
        }
        openFadeInSelectionLayout();

        binding.quizSelectionSheet.rvClass.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quizSelectionSheet.rvClass.setHasFixedSize(true);
        binding.quizSelectionSheet.rvClass.setNestedScrollingEnabled(false);
        adapter = new ChapterSelectAdapter(getActivity(), subjectResModel.getChapter(), this);
        binding.quizSelectionSheet.rvClass.setAdapter(adapter);
    }

    public void setSubjectAdapter(DashboardResModel dashboardResModel) {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        binding.recyclerViewMenu.setLayoutManager(layoutManager);
        binding.recyclerViewMenu.setHasFixedSize(true);
        SubjectSelectAdapter subjectadapter = new SubjectSelectAdapter(dashboardResModel.getSubjectResModelList(), getContext(),this);
        binding.recyclerViewMenu.setAdapter(subjectadapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main_quiz_home;
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        AppLogger.e(TAG, "commonEventListner");
        switch (commonDataModel.getClickType()) {
            case SUBJECT_CLICKED:

                setAdapterChapter((SubjectResModel) commonDataModel.getObject());
                break;

            case BACK_CLICK:
                AppLogger.e(TAG, "commonEventListner BACK_CLICK");
                backPressHandling();
                break;

            case NEXT_QUIZ_CLICK:
                quizResModel = (QuizResModel) commonDataModel.getObject();

                askPermission();
                break;

            case START_QUIZ_BUTON:
                quizResModel = (QuizResModel) commonDataModel.getObject();


                askPermission();
                break;
        }
    }

    private void askPermission() {
        String rationale = getString(R.string.permission_error_msg);
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
               // openCameraPhotoFragment();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                ViewUtil.showSnackBar(binding.getRoot(), rationale);
            }
        });
    }

   /* public void openCameraPhotoFragment() {
        Intent intent = new Intent(getActivity(), CameraxActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);
        //  startActivity(new Intent(getActivity(), CameraxActivity.class));
    }*/

    void backPressHandling() {
        int status = binding.quizSelectionSheet.sheetLayoutQuiz.getVisibility();
        AppLogger.e(TAG, "visibility value--" + status);
        if (status == 0) {
            binding.quizSelectionSheet.sheetLayoutQuiz.setVisibility(View.GONE);
        } else {
            ((StudentNewDashBoardActivity) getActivity()).dismissApplication();
        }
    }


    private void openFadeInSelectionLayout() {
        //Animation on button
        binding.quizSelectionSheet.sheetLayoutQuiz.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_to_up);
        binding.quizSelectionSheet.sheetLayoutQuiz.startAnimation(anim);

    }

    private void openFadeOutSelectionLayout() {
        //Animation on button
        binding.quizSelectionSheet.sheetLayoutQuiz.setVisibility(View.GONE);
      /*  Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
        binding.quizSelectionSheet.sheetLayout.startAnimation(anim);*/

    }

    @Override
    public void onClick(View v) {
        AppLogger.e(TAG, "On click called");
        if (v.getId() == R.id.sheet_layout_quiz) {
            AppLogger.e(TAG, "On click called sheet_layout 1");
        }



    }


    private void observeServiceResponse() {

        quizViewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {
                case SUCCESS:
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {


                    }
                    break;

                case NO_INTERNET:
                case AUTH_FAIL:
                case FAIL_400:
                    handleProgress(1, (String) responseApi.data);
                    break;
                default:
                    handleProgress(1, (String) responseApi.data);
                    break;
            }

        });
    }


    private void handleProgress(int value, String message) {
        AppLogger.e(TAG, "handleProgress calling - " + value + "-message-" + message);
        if (value == 0) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.mainParentLayout.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.startShimmer();
        } else if (value == 1) {
            binding.errorConstraint.setVisibility(View.VISIBLE);
            binding.mainParentLayout.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.shimmerViewQuiz.stopShimmer();
            binding.errorLayout.textError.setText(message);
            binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel());
                }
            });
        } else if (value == 2) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.mainParentLayout.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.shimmerViewQuiz.stopShimmer();

        }

    }


    public void checkStatusforCongratulationDialog() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && prefModel.getAssignmentReqModel() != null) {
            AssignmentReqModel assignmentReqModel = prefModel.getAssignmentReqModel();
            if (!TextUtil.isEmpty(assignmentReqModel.getExam_name()) && !TextUtil.isEmpty(assignmentReqModel.getQuiz_attempt())) {
                if (dashboardResModel != null && !TextUtil.checkListIsEmpty(dashboardResModel.getSubjectResModelList())) {
                    SubjectResModel subjectResModel = dashboardResModel.getSubjectResModelList().get(assignmentReqModel.getSubjectPos());
                    int finishedTestPos = ConversionUtil.INSTANCE.convertStringToInteger(assignmentReqModel.getExam_name());
                    QuizResModel quizResModel = subjectResModel.getChapter().get(finishedTestPos - 1);
                    Gson gson = new Gson();
                    String json = gson.toJson(quizResModel);
                    String jso2 = gson.toJson(assignmentReqModel);
                    AppLogger.e("chhonker quizresponse", json);
                    AppLogger.e("chhonker assignment", jso2);
                    int score = 0;
                    if (quizResModel.getAttempt() > 0) {
                        if (!TextUtil.isEmpty(quizResModel.getScoreallpoints())) {
                            String[] scoreArray = quizResModel.getScoreallpoints().split(",");
                            if (scoreArray.length > 0) {
                                score = ConversionUtil.INSTANCE.convertStringToInteger(scoreArray[scoreArray.length - 1]);
                            }
                        }
                    }
                   /*// funnelCompleteQuiz();
                    assignmentReqModel.setActualScore(score);
                    if (String.valueOf(quizResModel.getNumber()).equalsIgnoreCase(assignmentReqModel.getExam_name()) && score > 7) {
                        openCongratulationsDialog(dashboardResModel, assignmentReqModel);
                    } else {
                        openCongratulationsLessScoreDialog(dashboardResModel, assignmentReqModel);
                    }*/
                }
                prefModel.setAssignmentReqModel(null);
                AppPref.INSTANCE.setPref(prefModel);
            }

        }
    }


   /* private void openCongratulationsDialog(DashboardResModel dashboardResModel, AssignmentReqModel assignmentReqModel) {
        CongratulationsDialog congratulationsDialog = CongratulationsDialog.newInstance(this, dashboardResModel, assignmentReqModel);
        openFragmentDialog(congratulationsDialog);
    }

    private void openCongratulationsLessScoreDialog(DashboardResModel dashboardResModel, AssignmentReqModel assignmentReqModel) {
        ConsgratuationLessScoreDialog congratulationsDialog = ConsgratuationLessScoreDialog.newInstance(this, dashboardResModel, assignmentReqModel);
        openFragmentDialog(congratulationsDialog);
    }*/

    private void openFragmentDialog(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(AuroApp.getFragmentContainerUiId(), fragment, CongratulationsDialog.class.getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }


    void callDasboardApi() {
        handleProgress(0, "");
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        String deviceToken = prefModel.getDeviceToken();
        if (!TextUtil.isEmpty(deviceToken)) {
            AppLogger.v("DeviceToken_1", deviceToken);
        }
        AppLogger.v("DeviceToken_2", deviceToken);
        quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    String path = data.getStringExtra(AppConstant.PROFILE_IMAGE_PATH);
                    azureImage(path);
                    openQuizTestFragment(dashboardResModel);
                    AppLogger.e("chhonker-", "QuizTestFragment  setp 1");
                    //loadImageFromStorage(path);
                } catch (Exception e) {

                }

            } else {

            }
        }
    }

    private void azureImage(String path) {
        if (quizResModel == null || dashboardResModel == null) {
            return;
        }
        try {
            AppLogger.d(TAG, "Image Path" + path);
            assignmentReqModel = quizViewModel.homeUseCase.getAssignmentRequestModel(dashboardResModel, quizResModel);
            assignmentReqModel.setEklavvya_exam_id("");
            assignmentReqModel.setSubject(quizResModel.getSubjectName());
            Bitmap picBitmap = BitmapFactory.decodeFile(path);
            byte[] bytes = AppUtil.encodeToBase64(picBitmap, 100);
            long mb = AppUtil.bytesIntoHumanReadable(bytes.length);
            if (mb > 1.5) {
                assignmentReqModel.setImageBytes(AppUtil.encodeToBase64(picBitmap, 50));
            } else {
                assignmentReqModel.setImageBytes(bytes);
            }
            quizViewModel.getAzureRequestData(assignmentReqModel);
        } catch (Exception e) {
            AppLogger.e("chhonker-", "QuizTestFragment  setp 2" + e.getMessage());

            /*Do code here when error occur*/
        }
    }

    public void openQuizTestFragment(DashboardResModel dashboardResModel) {
        if (quizResModel == null || dashboardResModel == null) {
            return;
        }
        Bundle bundle = new Bundle();
        QuizTestFragment quizTestFragment = new QuizTestFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        bundle.putParcelable(AppConstant.QUIZ_RES_MODEL, quizResModel);
        quizTestFragment.setArguments(bundle);
        openFragment(quizTestFragment);
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



}