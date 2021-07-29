package com.auro.scholr.home.presentation.view.fragment.newDesgin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
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
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.core.network.URLConstant;
import com.auro.scholr.databinding.FragmentMainQuizHomeBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DynamiclinkResModel;
import com.auro.scholr.home.data.model.NavItemModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.SelectChapterQuizModel;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.home.presentation.view.adapter.DrawerListAdapter;
import com.auro.scholr.home.presentation.view.adapter.newuiadapter.ChapterSelectAdapter;
import com.auro.scholr.home.presentation.view.adapter.newuiadapter.SubjectSelectAdapter;
import com.auro.scholr.home.presentation.view.fragment.CertificateFragment;
import com.auro.scholr.home.presentation.view.fragment.CongratulationsDialog;
import com.auro.scholr.home.presentation.view.fragment.ConsgratuationLessScoreDialog;
import com.auro.scholr.home.presentation.view.fragment.DemographicFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCFragment;
import com.auro.scholr.home.presentation.view.fragment.KYCViewFragment;
import com.auro.scholr.home.presentation.view.fragment.PrivacyPolicyFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizTestFragment;
import com.auro.scholr.home.presentation.view.fragment.StudentProfileFragment;
import com.auro.scholr.home.presentation.view.fragment.TransactionsFragment;
import com.auro.scholr.home.presentation.view.fragment.WalletInfoDetailFragment;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialog;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.disclaimer.QuizDisclaimerDialog;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.GRADE_UPGRADE;


public class MainQuizHomeFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("MainQuizHomeFragment")
    ViewModelFactory viewModelFactory;

    FragmentMainQuizHomeBinding binding;
    QuizViewModel quizViewModel;
    DashboardResModel dashboardResModel;

    boolean isStateRestore;

    String TAG = MainQuizHomeFragment.class.getSimpleName();
    QuizResModel quizResModel;
    AssignmentReqModel assignmentReqModel;

    AuroScholarDataModel auroScholarDataModel;
    ActionBarDrawerToggle mDrawerToggle;
    ArrayList<NavItemModel> mNavItems = new ArrayList<NavItemModel>();
    DrawerListAdapter drawerListAdapter;

    public MainQuizHomeFragment() {
        // Required empty public constructor
    }

    ChapterSelectAdapter adapter;

    List<SelectChapterQuizModel> laugList;

    CustomDialog customDialog;


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

        if (binding != null) {
            isStateRestore = true;
            return binding.getRoot();
        }

        if (!AuroApp.getAuroScholarModel().isApplicationLang()) {
            String lang = AuroApp.getAuroScholarModel().getLanguage();
            if (!TextUtil.isEmpty(lang)) {
                if (lang.equalsIgnoreCase(AppConstant.LANGUAGE_HI) || lang.equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
                    setLanguagefromsdk();
                }
            } else {
                AuroApp.getAuroScholarModel().setLanguage(AppConstant.LANGUAGE_EN);
            }
        }
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        quizViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setQuizViewModel(quizViewModel);
        setRetainInstance(true);
        init();
        setListener();
        AppLogger.v("Pradeep", DateUtil.getMonthName());
        ViewUtil.setLanguage(Locale.getDefault().getLanguage());

        ViewUtil.setLanguageonUi(getActivity());
        setDrawerItemList(0, 0);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppLogger.v("Pradeep", DateUtil.getMonthName());
        // ((StudentMainDashboardActivity) getActivity()).setListner(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        callDasboardApi();
        StudentMainDashboardActivity.setListingActiveFragment(StudentMainDashboardActivity.QUIZ_DASHBOARD_FRAGMENT);
    }

    @Override
    protected void init() {

        AppLogger.e(TAG, DateUtil.getMonthName());
        if (!TextUtil.isEmpty(DateUtil.getMonthName())) {
            binding.RPTextView9.setText(DateUtil.getMonthName() + " " + getActivity().getResources().getString(R.string.scholarship));
        }

        //navigation drawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(), binding.drawerLayout, R.string.drawer_open, R.string.drawer_close);

        // Where do I put this?
        mDrawerToggle.syncState();
        lockDrawerMenu();
        AppLogger.e("handleback", "AuroApp.getAuroScholarModel()");

        setPrefData();
        ((StudentMainDashboardActivity) getActivity()).loadPartnerLogo(binding.partnerLogo);
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
        binding.walleticon.setOnClickListener(this);
        binding.cardView2.setOnClickListener(this);
        binding.privacyPolicy.setOnClickListener(this);
        binding.menuBarItem.setOnClickListener(this);
        binding.termsofuse.setOnClickListener(this);
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
        SubjectSelectAdapter subjectadapter = new SubjectSelectAdapter(dashboardResModel.getSubjectResModelList(), getContext(), this);
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
                checkPreQuizDisclaimer();
                break;

            case ACCEPT_PARENT_BUTTON:
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
                openCameraPhotoFragment();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                ViewUtil.showSnackBar(binding.getRoot(), rationale);
            }
        });
    }

    public void openCameraPhotoFragment() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);
    }


    void backPressHandling() {
        int status = binding.quizSelectionSheet.sheetLayoutQuiz.getVisibility();
        AppLogger.e(TAG, "visibility value--" + status);
        if (status == 0) {
            binding.quizSelectionSheet.sheetLayoutQuiz.setVisibility(View.GONE);
        } else {
            // ((StudentMainDashboardActivity) getActivity()).dismissApplication();
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
        int id = v.getId();
        if (id == R.id.sheet_layout_quiz) {
            AppLogger.e(TAG, "On click called sheet_layout");
        } else if (id == R.id.cardView2) {
            ((StudentMainDashboardActivity) getActivity()).openProfileFragment();
        } else if (id == R.id.walleticon) {
            if (quizViewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
                ((StudentMainDashboardActivity) getActivity()).openKYCViewFragment(dashboardResModel);
            } else {
                ((StudentMainDashboardActivity) getActivity()).openKYCFragment(dashboardResModel);
            }
        } else if (id == R.id.privacy_policy) {
            ((StudentMainDashboardActivity) getActivity()).openFragment(new PrivacyPolicyFragment());
        } else if (id == R.id.menuBarItem) {
           // openDemographicFragment();
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        } else if (id == R.id.termsofuse) {
            Bundle bundle = new Bundle();
            PrivacyPolicyFragment policyFragment = new PrivacyPolicyFragment();
            bundle.putString(AppConstant.WEB_LINK, URLConstant.TERM_CONDITION);
            policyFragment.setArguments(bundle);
            openFragment(policyFragment);
        }

    }


    private void observeServiceResponse() {

        quizViewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar
                   /* if (!isStateRestore) {
                        handleProgress(0, "");
                    }*/
                    break;
                case SUCCESS:

                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        if (isVisible()) {
                            dashboardResModel = (DashboardResModel) responseApi.data;
                            onApiSuccess(dashboardResModel);
                            AppLogger.v("PRADEEP_DATA", "SUCCESS");
                        }

                    } else if (responseApi.apiTypeStatus == GRADE_UPGRADE) {
                        handleProgress(0, "");
                        dashboardResModel = (DashboardResModel) responseApi.data;
                        //setPrefForTesting()
                        AppLogger.v("PRADEEP_DATA", dashboardResModel.isError() + "");
                        if (!dashboardResModel.isError()) {
                            if (customDialog != null) {
                                customDialog.dismiss();
                                quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel());
                            }
                        } else {
                            if (customDialog != null) {
                                customDialog.updateUI(0);
                            }
                        }
                    }
                    break;

                case NO_INTERNET:
                    if (customDialog != null) {
                        customDialog.dismiss();
                    }
                    AppLogger.v("PRADEEP_DATA", "NO_INTERNET");
                    handleProgress(1, (String) responseApi.data);
                    ((StudentMainDashboardActivity) getActivity()).setDashboardApiCallingInPref(true);
                    break;
                case AUTH_FAIL:
                case FAIL_400:
                    if (customDialog != null) {
                        customDialog.dismiss();
                        AppLogger.v("PRADEEP_DATA", "FAIL_400 null");
                    }
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        handleProgress(1, (String) responseApi.data);
                        AppLogger.v("PRADEEP_DATA", "DASHBOARD_API---");
                    } else {
                        setImageInPref(assignmentReqModel);
                        // openQuizTestFragment(dashboardResModel);
                    }
                    ((StudentMainDashboardActivity) getActivity()).setDashboardApiCallingInPref(true);
                    //handleProgress(1, (String) responseApi.data);
                    break;
                default:


                    if (customDialog != null) {
                        customDialog.dismiss();
                    }
                    // binding.swipeRefreshLayout.setRefreshing(false);
                    AppLogger.v("PRADEEP_DATA", "default---");
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        handleProgress(1, (String) responseApi.data);
                    } else {
                        setImageInPref(assignmentReqModel);
                        //  openQuizTestFragment(dashboardResModel);
                    }
                    ((StudentMainDashboardActivity) getActivity()).setDashboardApiCallingInPref(true);
                    //  handleProgress(1, (String) responseApi.data);
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


    private void onApiSuccess(DashboardResModel dashboardResModel) {
        updateList(dashboardResModel);
        handleProgress(1, "");
        AppUtil.setDashboardResModelToPref(dashboardResModel);
        if (!dashboardResModel.isError()) {
            ((StudentMainDashboardActivity) getActivity()).setDashboardApiCallingInPref(false);
            handleProgress(2, "");
            checkStatusforCongratulationDialog();
            if (dashboardResModel != null && dashboardResModel.getStatus().equalsIgnoreCase(AppConstant.FAILED)) {
                handleProgress(1, dashboardResModel.getMessage());
            } else {
                setDataOnUi(dashboardResModel);
            }
        } else {
            ((StudentMainDashboardActivity) getActivity()).setDashboardApiCallingInPref(true);
            handleProgress(1, dashboardResModel.getMessage());
            if (dashboardResModel.getMessage().contains("grade")) {
                openErrorDialog();
            }
        }


        /*Update Dynamic  to empty*/
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        DynamiclinkResModel model = prefModel.getDynamiclinkResModel();
        prefModel.setDynamiclinkResModel(new DynamiclinkResModel());
        AppPref.INSTANCE.setPref(prefModel);
        ViewUtil.setProfilePic(binding.imageView6);
    }

    private void updateList(DashboardResModel dashboardResModel) {
        if (dashboardResModel != null && !dashboardResModel.isError() && !TextUtil.checkListIsEmpty(dashboardResModel.getSubjectResModelList())) {
            for (SubjectResModel subjectResModel : dashboardResModel.getSubjectResModelList()) {
                for (QuizResModel quizResModel : subjectResModel.getChapter()) {
                    quizResModel.setCoreSubjectName(subjectResModel.getSubject());
                }
            }
        }
    }

    private void setDataOnUi(DashboardResModel dashboardResModel) {
        binding.walletBalText.setText(getContext().getResources().getString(R.string.rs) + " " + quizViewModel.homeUseCase.getWalletBalance(dashboardResModel));
        setSubjectAdapter(dashboardResModel);
        setNavHeaderText();
    }


    public void checkStatusforCongratulationDialog() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && prefModel.getAssignmentReqModel() != null) {
            AssignmentReqModel assignmentReqModel = prefModel.getAssignmentReqModel();
            if (!TextUtil.isEmpty(assignmentReqModel.getExam_name()) && !TextUtil.isEmpty(assignmentReqModel.getQuiz_attempt())) {
                if (dashboardResModel != null && !TextUtil.checkListIsEmpty(dashboardResModel.getSubjectResModelList())) {
                    SubjectResModel subjectResModel = dashboardResModel.getSubjectResModelList().get(assignmentReqModel.getSubjectPos());
                    int finishedTestPos = ConversionUtil.INSTANCE.convertStringToInteger(assignmentReqModel.getExam_name());
                    QuizResModel quizResModel = prefModel.getQuizResModel();
                    for (SubjectResModel model : dashboardResModel.getSubjectResModelList()) {
                        if (model.getSubject().equalsIgnoreCase(quizResModel.getCoreSubjectName())) {
                            quizResModel = model.getChapter().get(quizResModel.getNumber() - 1);
                            break;
                        }
                    }
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
                    assignmentReqModel.setActualScore(score);

                    AppLogger.e("chhonker score", "" + score);
                    if (String.valueOf(quizResModel.getNumber()).equalsIgnoreCase(assignmentReqModel.getExam_name()) && score > 7) {
                        openCongratulationsDialog(dashboardResModel, assignmentReqModel);
                    } else {
                        openCongratulationsLessScoreDialog(dashboardResModel, assignmentReqModel);
                    }
                }
                prefModel.setAssignmentReqModel(null);
                AppPref.INSTANCE.setPref(prefModel);
            }

        }
    }


    private void openCongratulationsDialog(DashboardResModel dashboardResModel, AssignmentReqModel assignmentReqModel) {
        CongratulationsDialog congratulationsDialog = new CongratulationsDialog(getContext(), dashboardResModel, assignmentReqModel, this);
        openFragmentDialog(congratulationsDialog);
    }

    private void openCongratulationsLessScoreDialog(DashboardResModel dashboardResModel, AssignmentReqModel assignmentReqModel) {
        ConsgratuationLessScoreDialog congratulationsDialog = new ConsgratuationLessScoreDialog(getContext(), this, dashboardResModel, assignmentReqModel);
        openFragmentDialog(congratulationsDialog);
    }


    private void openFragmentDialog(Fragment fragment) {
        if (isVisible()) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(AuroApp.getFragmentContainerUiId(), fragment, CongratulationsDialog.class.getSimpleName())
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }

    }


    void callDasboardApi() {

        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();

        String deviceToken = prefModel.getDeviceToken();
        if (!TextUtil.isEmpty(deviceToken)) {
            AppLogger.v("DeviceToken_1", deviceToken);
        }
        AppLogger.v(TAG, ""+prefModel.isDashbaordApiCall());
        if (prefModel.isDashbaordApiCall()) {
            handleProgress(0, "");
            quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel());
        } else {
            dashboardResModel = prefModel.getDashboardResModel();
            onApiSuccess(prefModel.getDashboardResModel());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    String path = data.getStringExtra(AppConstant.PROFILE_IMAGE_PATH);
                    openFadeOutSelectionLayout();
                    azureImage(path);
                    openQuizTestFragment(dashboardResModel);

                    // loadImageFromStorage(path);
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

        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        prefModel.setDashboardResModel(dashboardResModel);
        prefModel.setQuizResModel(quizResModel);
        AppPref.INSTANCE.setPref(prefModel);
        //  Bundle bundle = new Bundle();
        QuizTestFragment quizTestFragment = new QuizTestFragment();
      /*  bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        bundle.putParcelable(AppConstant.QUIZ_RES_MODEL, quizResModel);
        quizTestFragment.setArguments(bundle);*/
        openFragment(quizTestFragment);
        //bundle.clear();//TODO PRADEEP
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

    private void setDrawerItemList(int status, int val) {
        if (getActivity() == null) {
            return;
        }
        mNavItems.clear();

        mNavItems.add(new NavItemModel(getResources().getString(R.string.student_profile), "", R.drawable.ic_student_profile));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.passport), getResources().getString(R.string.analytics_more), R.drawable.ic_student_pass));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.kyc_verification), "", R.drawable.ic_verification));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.certificates), "", R.drawable.ic_certificate_icon));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.payment_info), "", R.drawable.ic_payment_info));

        // mNavItems.add(new NavItemModel( getActivity().getResources().getString(R.string.change_language), "", R.drawable.ic_language));

        mNavItems.add(new NavItemModel(getResources().getString(R.string.privacy_policy), "", R.drawable.ic_policy));
        // DrawerLayout
        if (status == 0) {
            drawerListAdapter = new DrawerListAdapter(getActivity(), mNavItems);
            binding.navList.setAdapter(drawerListAdapter);
            // Drawer Item click listeners
            binding.navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    handleDrawerItemClick(position);
                }
            });
        } else {
            if (drawerListAdapter != null) {
                drawerListAdapter.udpateList(mNavItems);
            }
        }
    }

    private void handleDrawerItemClick(int position) {

        switch (position) {


            case 0:
                /*Profile Fragment*/
                openStudentFragment();

                break;

            case 1:
                /*Transaction Fragemnt*/

                openTransactionsFragment();

                break;

            case 2:
                /*KYC Verification*/
                if (quizViewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
                    openKYCViewFragment(dashboardResModel);
                } else {
                    openKYCFragment(dashboardResModel);
                }
                break;

            case 3:
                /*Certificates*/
                openCertificateFragment();
                break;

            //  case 5:
            /*Change Grade*/
            // ((HomeActivity) getActivity()).openGradeChangeFragment(AppConstant.Source.DASHBOARD_NAVIGATION);
            // openGradeChangeFragment(AppConstant.Source.DASHBOARD_NAVIGATION);
           /* if (AuroApp.getAuroScholarModel().getSdkcallback() != null) {
                AuroApp.getAuroScholarModel().getSdkcallback().commonCallback(Status.NAV_CHANGE_GRADE_CLICK, "");
            }*/
            //  break;

            /* case 7:
             *//*Change Language*//*
               // openChangeLanguageDialog();
                break;
*/
            case 4:
                /*Payment Info*/
                openWalletAmountlistFragment();
                break;
            case 5:
                /*Privacy Policy*/
                Bundle bundle = new Bundle();
                PrivacyPolicyFragment policyFragment = new PrivacyPolicyFragment();
                bundle.putString(AppConstant.WEB_LINK, URLConstant.PRIVACY_POLICY);
                policyFragment.setArguments(bundle);
                openFragment(policyFragment);
                break;


        }

        binding.drawerLayout.closeDrawer(GravityCompat.START);

    }


    public void openStudentFragment() {
        Bundle bundle = new Bundle();
        StudentProfileFragment studentProfile = new StudentProfileFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);

        studentProfile.setArguments(bundle);
        openFragment(studentProfile);
    }

    /*For testing purpose*/
    public void openCertificateFragment() {
        Bundle bundle = new Bundle();
        CertificateFragment certificateFragment = new CertificateFragment();
        openFragment(certificateFragment);
    }


    private void openWalletAmountlistFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        WalletInfoDetailFragment fragment = new WalletInfoDetailFragment();
        fragment.setArguments(bundle);
        openFragment(fragment);
    }

    public void openKYCFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);
        openFragment(kycFragment);
    }

    public void openKYCViewFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCViewFragment kycViewFragment = new KYCViewFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycViewFragment.setArguments(bundle);
        ViewUtil.setLanguageonUi(getActivity());
        openFragment(kycViewFragment);
    }

    public void openProfileFragment() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        Bundle bundle = new Bundle();
        StudentProfileFragment studentProfile = new StudentProfileFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, prefModel.getDashboardResModel());
        bundle.putString(AppConstant.COMING_FROM, AppConstant.SENDING_DATA.STUDENT_PROFILE);
        studentProfile.setArguments(bundle);
        openFragment(studentProfile);
    }

    public void openTransactionsFragment() {
        Bundle bundle = new Bundle();
        TransactionsFragment transactionsFragment = new TransactionsFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        transactionsFragment.setArguments(bundle);
        openFragment(transactionsFragment);
    }

    public void lockDrawerMenu() {
        // binding.toolbarLayout.backArrow.setEnabled(false);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unLockDrawerMenu() {
        // binding.toolbarLayout.backArrow.setEnabled(true);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    public void setPrefData() {
        if (AuroApp.getAuroScholarModel() != null) {
            PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
            prefModel.setUserMobile(AuroApp.getAuroScholarModel().getMobileNumber());
            prefModel.setStudentClass(ConversionUtil.INSTANCE.convertStringToInteger(AuroApp.getAuroScholarModel().getStudentClass()));
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    private void setLanguagefromsdk() {
        Locale locale = new Locale(AuroApp.getAuroScholarModel().getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
    }

    public void setImageInPref(AssignmentReqModel assignmentReqModel) {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && prefModel.getListAzureImageList() != null) {
            prefModel.getListAzureImageList().add(assignmentReqModel);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    private void openErrorDialog() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }

        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(AuroApp.getAppContext());
        customDialogModel.setTitle(AuroApp.getAppContext().getResources().getString(R.string.information));
        customDialogModel.setContent(AuroApp.getAppContext().getResources().getString(R.string.grade_chnage_message));//
        customDialogModel.setTwoButtonRequired(true);
        customDialog = new CustomDialog(AuroApp.getAppContext(), customDialogModel);
        customDialog.setSecondBtnTxt(AuroApp.getAppContext().getResources().getString(R.string.yes));
        customDialog.setFirstBtnTxt(AuroApp.getAppContext().getResources().getString(R.string.no));
        customDialog.setFirstCallcack(new CustomDialog.FirstCallcack() {
            @Override
            public void clickNoCallback() {

                customDialog.dismiss();
            }
        });

        customDialog.setSecondCallcack(new CustomDialog.SecondCallcack() {
            @Override
            public void clickYesCallback() {
                //   customDialog.dismiss();
                customDialog.updateUI(1);
                callClassUpgradeApi();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(customDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        customDialog.getWindow().setAttributes(lp);
        Objects.requireNonNull(customDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.setCancelable(true);
        customDialog.show();

    }

    private void callClassUpgradeApi() {
        quizViewModel.gradeUpgrade(AuroApp.getAuroScholarModel());
    }

    private void setNavHeaderText() {
        TextView login_txt = binding.navHeader.findViewById(R.id.login_id);
        login_txt.setText(getActivity().getString(R.string.mobile_num) + dashboardResModel.getPhonenumber());

        TextView class_txt = binding.navHeader.findViewById(R.id.txtClass);
        class_txt.setText(getActivity().getString(R.string.student_class) + dashboardResModel.getStudentclass());

    }

    public void openDemographicFragment() {
        Bundle bundle = new Bundle();
        DemographicFragment demographicFragment = new DemographicFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        demographicFragment.setArguments(bundle);
        openFragment(demographicFragment);
    }


    private void checkPreQuizDisclaimer() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (!prefModel.isPreQuizDisclaimer()) {
            QuizDisclaimerDialog askDetailCustomDialog = new QuizDisclaimerDialog(getActivity(), this);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(askDetailCustomDialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            askDetailCustomDialog.getWindow().setAttributes(lp);
            Objects.requireNonNull(askDetailCustomDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            askDetailCustomDialog.setCancelable(false);
            askDetailCustomDialog.show();
        }
    }
}