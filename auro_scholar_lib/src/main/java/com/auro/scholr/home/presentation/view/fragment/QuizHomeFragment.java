package com.auro.scholr.home.presentation.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.auro.scholr.databinding.QuizHomeLayoutBinding;
import com.auro.scholr.home.data.model.AssignmentReqModel;

import com.auro.scholr.home.data.model.CustomSnackBarModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.data.model.RandomInviteFriendsDataModel;
import com.auro.scholr.home.data.model.SubjectResModel;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;

import com.auro.scholr.home.presentation.view.adapter.QuizItemAdapter;
import com.auro.scholr.home.presentation.view.adapter.QuizItemNewAdapter;
import com.auro.scholr.home.presentation.view.adapter.QuizWonAdapter;
import com.auro.scholr.home.presentation.viewmodel.DataModel;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialog;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomSnackBar;
import com.auro.scholr.util.firebase.FirebaseEventUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Named;

import static android.app.Activity.RESULT_OK;
import static com.auro.scholr.core.common.Status.AZURE_API;
import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.GRADE_UPGRADE;


public class QuizHomeFragment extends BaseFragment implements View.OnClickListener, CommonCallBackListner, SwipeRefreshLayout.OnRefreshListener, NavigationView.OnNavigationItemSelectedListener {

    //chhonker test
    @Inject
    @Named("QuizHomeFragment")
    ViewModelFactory viewModelFactory;
    QuizHomeLayoutBinding binding;
    QuizViewModel quizViewModel;
    QuizItemAdapter quizItemAdapter;
    private String TAG = "QuizHomeFragment";
    DashboardResModel dashboardResModel;
    QuizResModel quizResModel;
    QuizWonAdapter quizWonAdapter;
    // Resources resources;
    boolean isStateRestore;
    AssignmentReqModel assignmentReqModel;
    CustomDialog customDialog;
    List<RandomInviteFriendsDataModel> list;
    FirebaseEventUtil firebaseEventUtil;
    Map<String, String> logparam;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        // resources = ViewUtil.getCustomResource(getActivity());
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && TextUtil.isEmpty(prefModel.getUserLanguage())) {
            ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
        }

        AppLogger.e("handleback","QuizHomeFragment");

        setRetainInstance(true);
        ViewUtil.setLanguage(Locale.getDefault().getLanguage());
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }

    private void setQuizListAdapter(List<QuizResModel> resModelList) {
        binding.quizTypeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quizTypeList.setHasFixedSize(true);
        quizItemAdapter = new QuizItemAdapter(this.getContext(), resModelList, this);
        binding.quizTypeList.setAdapter(quizItemAdapter);

    }

    private void setQuizWonListAdapter(List<QuizResModel> resModelList) {
        binding.quizwonTypeList.setHasFixedSize(true);
        quizWonAdapter = new QuizWonAdapter(this.getContext(), resModelList, quizViewModel.homeUseCase.getQuizWonCount(resModelList));
        binding.quizwonTypeList.setAdapter(quizWonAdapter);

    }

    @Override
    protected void init() {
        if (getArguments() != null) {
            // mobileNumber = getArguments().getString(AppConstant.MOBILE_NUMBER);
        }
        firebaseEventUtil = new FirebaseEventUtil(getContext());
        logparam = new HashMap<>();

        logparam.put(getActivity().getResources().getString(R.string.log_start_quiz), "true");
        firebaseEventUtil.logEvent(getActivity().getResources().getString(R.string.log_quiz_home_fragment_student), logparam);


        if (quizViewModel != null && quizViewModel.serviceLiveData().hasObservers()) {
            quizViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
        Glide.with(this).load(R.raw.anima).into(binding.customUiSnackbar.gifview);
        openToolTip();

        //navigation drawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(), binding.drawerLayout, R.string.drawer_open, R.string.drawer_close);

        // Where do I put this?
        mDrawerToggle.syncState();
        //PRADEEP
        lockDrawerMenu();
        AppLogger.e("handleback","AuroApp.getAuroScholarModel()");
        quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel());
        binding.swipeRefreshLayout.setOnRefreshListener(this);

        setNavHeaderText();
    }

    @Override
    protected void setToolbar() {
        /*Do code here*/
    }

    @Override
    protected void setListener() {
        binding.walletBalText.setOnClickListener(this);
        binding.privacyPolicy.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        binding.customUiSnackbar.btInvite.setOnClickListener(this);
        binding.navView.setNavigationItemSelectedListener(this);
        binding.navView.setItemIconTintList(null);
        binding.customUiSnackbar.inviteParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(new FriendsLeaderBoardFragment());
            }
        });
        binding.fab.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.quiz_home_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setToolbar();

        //checkJson();
    }

    private void openSnackBar() {
        CustomSnackBarModel customSnackBarModel = new CustomSnackBarModel();
        customSnackBarModel.setView(binding.getRoot());
        customSnackBarModel.setStatus(0);
        customSnackBarModel.setContext(getActivity());
        customSnackBarModel.setCommonCallBackListner(this);
        CustomSnackBar.INSTANCE.showCartSnackbar(customSnackBarModel);

    }

    @Override
    public void onResume() {
        super.onResume();
        // resources = ViewUtil.getCustomResource(getActivity());
        init();
        setListener();
        setDataOnUI();
        AppLogger.e("handleback","ONrESUME");
    }

    private void setDataOnUI() {
        binding.toolbarLayout.backArrow.setVisibility(View.VISIBLE);
        AppLogger.e("chhonker", DateUtil.getMonthName());
        if (!TextUtil.isEmpty(DateUtil.getMonthName())) {
            binding.getScholarshipText.setText(DateUtil.getMonthName() + " " + getActivity().getResources().getString(R.string.scholarship));
        }
        //  binding.getScholarshipText.setText(resources.getText(R.string.get_scholarship));
        binding.headerTopParent.cambridgeHeading.setText(AuroApp.getAppContext().getResources().getString(R.string.question_bank_powered_by_cambridge));
        randomlistforsnackbar();

        String lang = ViewUtil.getLanguage();
        if (lang.equalsIgnoreCase(AppConstant.LANGUAGE_EN) || TextUtil.isEmpty(lang)) {
            setLangOnUi(AppConstant.HINDI);
        } else {
            setLangOnUi(AppConstant.ENGLISH);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        CustomSnackBar.INSTANCE.dismissCartSnackbar();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setLanguage(AppConstant.LANGUAGE_EN);
    }

    private void setLanguage(String language) {

        ViewUtil.setLanguage(language);
        // resources = ViewUtil.getCustomResource(getActivity());
    }

    private void observeServiceResponse() {

        quizViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar
                    if (!isStateRestore) {
                        handleProgress(0, "");
                    }
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        onApiSuccess(responseApi);
                    } else if (responseApi.apiTypeStatus == AZURE_API) {
                        // openQuizTestFragment(dashboardResModel);
                    } else if (responseApi.apiTypeStatus == GRADE_UPGRADE) {
                        DashboardResModel dashboardResModel = (DashboardResModel) responseApi.data;
                        handleProgress(1, "");
                        dashboardResModel = (DashboardResModel) responseApi.data;
                        //setPrefForTesting();
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
//On fail
                    if (customDialog != null) {
                        customDialog.dismiss();
                    }
                    handleProgress(2, (String) responseApi.data);
                    binding.swipeRefreshLayout.setRefreshing(false);
                    break;

                case AUTH_FAIL:
                case FAIL_400:
// When Authrization is fail
                    if (customDialog != null) {
                        customDialog.dismiss();
                    }
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        handleProgress(2, (String) responseApi.data);
                    } else {
                        setImageInPref(assignmentReqModel);
                        // openQuizTestFragment(dashboardResModel);
                    }
                    binding.swipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    if (customDialog != null) {
                        customDialog.dismiss();
                    }
                    binding.swipeRefreshLayout.setRefreshing(false);
                    Log.d(TAG, "observeServiceResponse: default");
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        handleProgress(2, (String) responseApi.data);
                    } else {
                        setImageInPref(assignmentReqModel);
                        //  openQuizTestFragment(dashboardResModel);
                    }
                    break;
            }

        });
    }

    private void handleProgress(int value, String message) {
        if (value == 0) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.mainParentLayout.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.startShimmer();
        } else if (value == 1) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.mainParentLayout.setVisibility(View.VISIBLE);
            checkForFriendsLeaderBoard();
            binding.fab.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.shimmerViewQuiz.stopShimmer();
        } else {
            binding.errorConstraint.setVisibility(View.VISIBLE);
            binding.mainParentLayout.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.shimmerViewQuiz.stopShimmer();
            binding.errorLayout.textError.setText(message);
            binding.customUiSnackbar.inviteParentLayout.setVisibility(View.GONE);
            binding.fab.setVisibility(View.GONE);
            binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel());
                }
            });
        }

    }

    private void checkForFriendsLeaderBoard() {
        if (dashboardResModel.getFeature() == 0) {
            binding.customUiSnackbar.inviteParentLayout.setVisibility(View.VISIBLE);
            binding.navView.getMenu().findItem(R.id.nav_friends_leaderboard).setVisible(true);
        } else {
            binding.customUiSnackbar.inviteParentLayout.setVisibility(View.GONE);
            binding.navView.getMenu().findItem(R.id.nav_friends_leaderboard).setVisible(false);
        }
    }

    private void setDataOnUi(DashboardResModel dashboardResModel) {
        if (isAdded()) {
            //   quizViewModel.walletBalance.setValue(getString(R.string.rs) + " " + dashboardResModel.getWalletbalance());
            quizViewModel.walletBalance.setValue(getActivity().getResources().getString(R.string.rs) + " " + quizViewModel.homeUseCase.getWalletBalance(dashboardResModel));
            //   setQuizListAdapter(dashboardResModel.getQuiz());
            setQuizListNewAdapter();
            //setQuizWonListAdapter(dashboardResModel.getSubjectResModelList());
            getSpannableString();
        }

    }

    public void openQuizTestFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        QuizTestFragment quizTestFragment = new QuizTestFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        bundle.putParcelable(AppConstant.QUIZ_RES_MODEL, quizResModel);
        quizTestFragment.setArguments(bundle);
        Locale.getDefault().getLanguage();
        ViewUtil.setLanguageonUi(getActivity());
        openFragment(quizTestFragment);
    }

    public void openCameraPhotoFragment() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);

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

                    // loadImageFromStorage(path);
                } catch (Exception e) {

                }

            } else {

            }
        }
    }

    private void azureImage(String path) {
        try {
            AppLogger.d(TAG, "Image Path" + path);
            assignmentReqModel = quizViewModel.homeUseCase.getAssignmentRequestModel(dashboardResModel, quizResModel);
            assignmentReqModel.setEklavvya_exam_id("");
            assignmentReqModel.setSubject(quizResModel.getSubjectName());
            Bitmap picBitmap = BitmapFactory.decodeFile(path);
            byte[] bytes = AppUtil.encodeToBase64(picBitmap, 100);
           // long mb = AppUtil.bytesIntoHumanReadable(bytes.length);
            int file_size = Integer.parseInt(String.valueOf(bytes.length / 1024));

            if (file_size >500) {
                assignmentReqModel.setImageBytes(AppUtil.encodeToBase64(picBitmap, 50));
            } else {
                assignmentReqModel.setImageBytes(bytes);
            }
            quizViewModel.getAzureRequestData(assignmentReqModel);
        } catch (Exception e) {
            /*Do code here when error occur*/
        }
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
        openFragment(kycViewFragment);
    }

    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this.getContext())).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, QuizHomeFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.wallet_bal_text) {
            // openFragment(new TeacherProfileFragment());
            closeToolTip();
            logparam.put(getActivity().getResources().getString(R.string.log_click_add_kyc_student), "true");
            firebaseEventUtil.logEvent(getActivity().getResources().getString(R.string.log_start_quiz), logparam);
            if (quizViewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
                openKYCViewFragment(dashboardResModel);
            } else {
                openKYCFragment(dashboardResModel);
            }

        } else if (v.getId() == R.id.privacy_policy) {

            openFragment(new PrivacyPolicyFragment());

            // openQuizHomeNewFragment();
        } else if (v.getId() == R.id.lang_eng) {
            CustomSnackBar.INSTANCE.dismissCartSnackbar();
            String text = binding.toolbarLayout.langEng.getText().toString();
            if (!TextUtil.isEmpty(text) && text.equalsIgnoreCase(AppConstant.HINDI)) {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_HI);
                //  resources = ViewUtil.getCustomResource(getActivity());
            } else {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
                // resources = ViewUtil.getCustomResource(getActivity());
            }
            reloadFragment();
        } else if (v.getId() == R.id.bt_upload_all) {
            openFriendLeaderBoardFragment();
        } else if (v.getId() == R.id.back_arrow) {
            // getActivity().getSupportFragmentManager().popBackStack();
            //pradeep wait
            binding.drawerLayout.openDrawer(Gravity.LEFT);
        } else if (v.getId() == R.id.bt_invite) {
            openFriendLeaderBoardFragment();
        } else if (v.getId() == R.id.fab) {
            openChat();
            // openFragment(new TeacherProfileFragment());
            // TeacherSaveDetailFragment mteacherSaveDetailFragment = new TeacherSaveDetailFragment();
            /*TransactionsFragment mtransactionsFragment = new TransactionsFragment();*/
            //  openFragment(mteacherSaveDetailFragment);
            //  TeacherProfileFragment mfragment = new TeacherProfileFragment();
            // openFragment(mfragment);

        }
    }

    private void openFriendLeaderBoardFragment() {
        FriendsLeaderBoardFragment fragment = new FriendsLeaderBoardFragment();
        openFragment(fragment);
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private void setLangOnUi(String lang) {
        binding.toolbarLayout.langEng.setText(lang);
    }

    private void askPermission() {
        String rationale = getString(R.string.permission_error_msg);
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                // openQuizTestFragment(dashboardResModel);
                openCameraPhotoFragment();

            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                ViewUtil.showSnackBar(binding.getRoot(), rationale);
            }
        });
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case NEXT_QUIZ_CLICK:
                quizResModel = (QuizResModel) commonDataModel.getObject();
                askPermission();
                break;

            case START_QUIZ_BUTON:
                quizResModel = (QuizResModel) commonDataModel.getObject();
                askPermission();
                break;

            case FRIEND_LEADER_BOARD_CLICK:

                break;
        }

    }

    public void getSpannableString() {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        SpannableStringBuilder span1 = new SpannableStringBuilder(AuroApp.getAppContext().getResources().getString(R.string.score_and_get));
        ForegroundColorSpan color1 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.auro_grey_color));
        span1.setSpan(color1, 0, span1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.append(span1);

        SpannableStringBuilder span2 = new SpannableStringBuilder(" " + getString(R.string.rs) + "50" + " ");
        ForegroundColorSpan color2 = new ForegroundColorSpan(AuroApp.getAppContext().getResources().getColor(R.color.color_red));
        span2.setSpan(color2, 0, span2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        span2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, span2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(span2);

        SpannableStringBuilder span3 = new SpannableStringBuilder(AuroApp.getAppContext().getResources().getString(R.string.for_each_quiz));
        ForegroundColorSpan color3 = new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.auro_grey_color));
        span3.setSpan(color3, 0, span3.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        builder.append(span3);

        binding.scoreText.setText(builder, TextView.BufferType.SPANNABLE);
    }

    public void setImageInPref(AssignmentReqModel assignmentReqModel) {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && prefModel.getListAzureImageList() != null) {
            prefModel.getListAzureImageList().add(assignmentReqModel);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    private void openFragmentDialog(Fragment fragment) {
        /* getActivity().getSupportFragmentManager().popBackStack();*/
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(AuroApp.getFragmentContainerUiId(), fragment, CongratulationsDialog.class.getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    private void startAnimationFromBottom() {
        //Animation on button
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.down_to_top_slide);
        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                startAnimatioFromTop();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });
        binding.rltooltipe.startAnimation(anim);

    }

    private void startAnimatioFromTop() {
        //Animation on button
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.top_to_down_slide);

        anim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation arg0) {
                startAnimationFromBottom();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationStart(Animation arg0) {
                // TODO Auto-generated method stub

            }

        });
        binding.rltooltipe.startAnimation(anim);
    }

    public void openToolTip() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && !prefModel.isTooltipStatus()) {
            binding.rltooltipe.setVisibility(View.VISIBLE);
            startAnimationFromBottom();
        } else {
            binding.rltooltipe.setVisibility(View.GONE);
        }
    }

    public void closeToolTip() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && !prefModel.isTooltipStatus()) {
            binding.rltooltipe.setVisibility(View.GONE);
            prefModel.setTooltipStatus(true);
            AppPref.INSTANCE.setPref(prefModel);
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

    private void openQuizHomeNewFragment() {
        QuizHomeNewFragment openhomenewfragment = new QuizHomeNewFragment();
        openFragment(openhomenewfragment);
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
                    if (String.valueOf(quizResModel.getNumber()).equalsIgnoreCase(assignmentReqModel.getExam_name()) && quizResModel.getScorepoints() >= 8) {
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

    private void setPrefForTesting() {
        dashboardResModel.getSubjectResModelList().get(0).getChapter().get(0).setScorepoints(6);
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null) {
            AssignmentReqModel assignmentReqModel = new AssignmentReqModel();
            assignmentReqModel.setRegistration_id(dashboardResModel.getAuroid());
            assignmentReqModel.setExam_name("" + dashboardResModel.getSubjectResModelList().get(0).getChapter().get(0).getNumber());
            assignmentReqModel.setQuiz_attempt("" + dashboardResModel.getSubjectResModelList().get(0).getChapter().get(0).getAttempt());
            assignmentReqModel.setExamlang("E");
            prefModel.setAssignmentReqModel(assignmentReqModel);
            AppPref.INSTANCE.setPref(prefModel);
        }
    }

    private void openChat() {
        Uri uri = Uri.parse("https://wa.me/919667480783");
        logparam.put(getResources().getString(R.string.log_click_on_whatapp_student), "true");
        firebaseEventUtil.logEvent(getResources().getString(R.string.log_quiz_home_fragment_student), logparam);
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(Intent.createChooser(i, ""));
    }

    private void openErrorDialog() {
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
        customDialog.setCancelable(false);
        customDialog.show();

    }

    public void randomlistforsnackbar() {
        RandomInviteFriendsDataModel model = new RandomInviteFriendsDataModel(
                AuroApp.getAppContext().getResources().getString(R.string.text1_random),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._4sdp),
                AuroApp.getAppContext().getResources().getString(R.string.button1_random),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp));
        RandomInviteFriendsDataModel model2 = new RandomInviteFriendsDataModel(
                AuroApp.getAppContext().getResources().getString(R.string.text2_random_chalange_your_friends),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp),
                AuroApp.getAppContext().getResources().getString(R.string.button1_random),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp));
        RandomInviteFriendsDataModel model3 = new RandomInviteFriendsDataModel(
                AuroApp.getAppContext().getResources().getString(R.string.text3_random_double_the),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp),
                AuroApp.getAppContext().getResources().getString(R.string.button2_random),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp));
        RandomInviteFriendsDataModel model4 = new RandomInviteFriendsDataModel(
                AuroApp.getAppContext().getResources().getString(R.string.text4_random_learning),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp),
                AuroApp.getAppContext().getResources().getString(R.string.button2_random),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp));
        RandomInviteFriendsDataModel model5 = new RandomInviteFriendsDataModel(
                AuroApp.getAppContext().getResources().getString(R.string.text5_random_multiply),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp),
                AuroApp.getAppContext().getResources().getString(R.string.button1_random),
                AuroApp.getAppContext().getResources().getDimension(R.dimen._3sdp));

        list = new ArrayList<>();
        list.add(model);
        list.add(model2);
        list.add(model3);
        list.add(model4);
        list.add(model5);

        pickRandom();
    }

    public void pickRandom() {
        Random rand = new Random();
        RandomInviteFriendsDataModel randomElement = list.get(rand.nextInt(list.size()));
        binding.customUiSnackbar.kycMsg.setText(randomElement.getTextTitle());
        binding.customUiSnackbar.kycMsg.setTextSize(randomElement.getTextTitleSize());
        binding.customUiSnackbar.btInvite.setText(randomElement.getButtonTitle());
        binding.customUiSnackbar.btInvite.setTextSize(randomElement.getButtonSize());
    }

    public void openDemographicFragment() {
        Bundle bundle = new Bundle();
        DemographicFragment demographicFragment = new DemographicFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        demographicFragment.setArguments(bundle);
        openFragment(demographicFragment);
    }

    private void setQuizListNewAdapter() {
        binding.quizTypeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quizTypeList.setHasFixedSize(true);
        QuizItemNewAdapter quizItemAdapter = new QuizItemNewAdapter(this.getContext(), dashboardResModel.getSubjectResModelList(), this);
        binding.quizTypeList.setAdapter(quizItemAdapter);
    }

    @Override
    public void onRefresh() {
        quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel());

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {


            default:
                return super.onOptionsItemSelected(item);
        }
        // return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_kyc) {
            if (quizViewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
                openKYCViewFragment(dashboardResModel);
            } else {
                openKYCFragment(dashboardResModel);
            }
        } else if (id == R.id.nav_friends_leaderboard) {
            openFriendLeaderBoardFragment();
        } else if (id == R.id.nav_payment_info) {
            openTransactionFragment();
        } else if (id == R.id.nav_privacy) {
            openFragment(new PrivacyPolicyFragment());
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    private void openTransactionFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        TransactionsFragment fragment = new TransactionsFragment();
        fragment.setArguments(bundle);
        openFragment(fragment);
    }


    public void alertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // Set the alert dialog yes button click listener
        builder.setMessage(message);
        builder.setPositiveButton(Html.fromHtml("<font color='#00A1DB'>OK</font>"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        // Display the alert dialog on interface
        dialog.show();
    }


    public void lockDrawerMenu() {
        binding.toolbarLayout.backArrow.setEnabled(false);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unLockDrawerMenu() {
        binding.toolbarLayout.backArrow.setEnabled(true);
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }


    private void onApiSuccess(ResponseApi responseApi) {
        dashboardResModel = (DashboardResModel) responseApi.data;
        binding.swipeRefreshLayout.setRefreshing(false);
        handleProgress(1, "");
        AppUtil.setDashboardResModelToPref(dashboardResModel);
        //setPrefForTesting();
        if (!dashboardResModel.isError()) {
            unLockDrawerMenu();
            checkStatusforCongratulationDialog();
            if (dashboardResModel != null && dashboardResModel.getStatus().equalsIgnoreCase(AppConstant.FAILED)) {
                handleProgress(2, dashboardResModel.getMessage());
            } else {
                setDataOnUi(dashboardResModel);
            }
        } else {
            handleProgress(2, dashboardResModel.getMessage());
            if (dashboardResModel.getMessage().contains("grade")) {
                openErrorDialog();
            }
        }


    }

    private void callClassUpgradeApi() {
        quizViewModel.gradeUpgrade(AuroApp.getAuroScholarModel());
    }

    private void setLanguagefromsdk() {
        Locale locale = new Locale(AuroApp.getAuroScholarModel().getLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config, getActivity().getBaseContext().getResources().getDisplayMetrics());
    }

    private void setNavHeaderText()
    {
        TextView login_txt = binding.navView.getHeaderView(0).findViewById(R.id.login_id);
        TextView class_txt = binding.navView.getHeaderView(0).findViewById(R.id.txtClass);
        if (AuroApp.getAuroScholarModel() != null && !TextUtil.isEmpty(AuroApp.getAuroScholarModel().getMobileNumber()) &&
                !TextUtil.isEmpty(AuroApp.getAuroScholarModel().getStudentClass())) {
            login_txt.setVisibility(View.VISIBLE);
            class_txt.setVisibility(View.VISIBLE);
            login_txt.setText(getActivity().getString(R.string.mobile_num) + AuroApp.getAuroScholarModel().getMobileNumber());
            class_txt.setText(getActivity().getString(R.string.student_class) + AuroApp.getAuroScholarModel().getStudentClass());
        }else
        {
            login_txt.setVisibility(View.GONE);
            class_txt.setVisibility(View.GONE);
        }


    }
}

