package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
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
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.TeacherMyClassroomLayoutBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.teacher.data.model.common.MonthDataModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomResModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomStudentResModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomTeacherResModel;
import com.auro.scholr.teacher.presentation.view.adapter.MonthSpinnerAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.MyClassroomAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.MyClassroomViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.ExitDialog;
import com.auro.scholr.util.firebase.FirebaseEvent;
import com.auro.scholr.util.firebase.FirebaseEventUtil;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.FocusShape;
import me.toptas.fancyshowcase.listener.OnViewInflateListener;


public class MyClassroomFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("MyClassroomFragment")
    ViewModelFactory viewModelFactory;
    String TAG = "MyClassroomFragment";
    TeacherMyClassroomLayoutBinding binding;
    MyClassroomViewModel viewModel;
    boolean isStateRestore;
    MyClassRoomTeacherResModel myClassRoomResModel;
    List<MonthDataModel> monthDataModelList;
    MyClassroomAdapter leaderBoardAdapter;
    private CallbackManager callbackManager;
    ShareDialog shareDialog;
    private FirebaseEventUtil mFirebaseAnalytics;
    Map<String, String> logeventparam;
    FancyShowCaseView btnfacebook;
    FancyShowCaseQueue queue;
    BottomNavigationView toolbar;
    FancyShowCaseView btnprofile;
    FancyShowCaseView btnKycapp;
    FancyShowCaseView btnInfotutorial;


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
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyClassroomViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        return binding.getRoot();
    }

    public void setAdapter(List<MyClassRoomStudentResModel> resModelList) {
        binding.studentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.studentList.setHasFixedSize(true);
        leaderBoardAdapter = new MyClassroomAdapter(getActivity(), resModelList, this);
        binding.studentList.setAdapter(leaderBoardAdapter);
    }

    @Override
    protected void init() {
        HomeActivity.setListingActiveFragment(HomeActivity.TEACHER_DASHBOARD_FRAGMENT);
        mFirebaseAnalytics = new FirebaseEventUtil(getActivity());
        logeventparam = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add("odd");
        list.add("even");
        list.add("primary");
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        setDataOnUi();
        // viewModel.getTeacherProfileData("7503600686");

        shareDialog = new ShareDialog(this);
        viewModel.getTeacherProfileData(AuroApp.getAuroScholarModel().getMobileNumber());

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation);

        // Initialize facebook SDK.
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());

        // Create a callbackManager to handle the login responses.
        callbackManager = CallbackManager.Factory.create();
    }

    private void setDataOnUi() {

    }

    private void monthSpinner() {
        String date = "";
        if (myClassRoomResModel != null && !TextUtil.isEmpty(myClassRoomResModel.getRegistrationDate())) {
            date = myClassRoomResModel.getRegistrationDate();
        }
        AppLogger.e("chhonker- reg date-",date);
        monthDataModelList = viewModel.teacherUseCase.monthDataModelList(date);
        if (!TextUtil.checkListIsEmpty(monthDataModelList)) {
            MonthSpinnerAdapter stateSpinnerAdapter = new MonthSpinnerAdapter(monthDataModelList);
            binding.monthSpinner.setAdapter(stateSpinnerAdapter);
            binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (leaderBoardAdapter != null && myClassRoomResModel != null && !TextUtil.checkListIsEmpty(myClassRoomResModel.getStudentResModels())) {
                        leaderBoardAdapter.updateList(viewModel.teacherUseCase.makeStudentList(myClassRoomResModel.getStudentResModels(), monthDataModelList.get(position).getMonthNumber(), monthDataModelList.get(position).getYear()));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            int year = DateUtil.getcurrentYearNumber();
            int month = DateUtil.getcurrentMonthNumber();
            if (month != 0) {
                //month = month - 1;
            }
            for (int i = 0; i < monthDataModelList.size(); i++) {
                MonthDataModel model = monthDataModelList.get(i);
                AppLogger.e(TAG, "--current year--" + year + "--month number--" + month);
                AppLogger.e(TAG, model.getMonth() + "--year--" + model.getYear() + "--month number--" + model.getMonthNumber());
                if (year == model.getYear() && (month + 1) == (model.getMonthNumber())) {
                    binding.monthSpinner.setSelection(i);
                }
            }
        }


    }

    @Override
    protected void setToolbar() {
        /*Do code here*/
    }

    @Override
    protected void setListener() {
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
        binding.facebook.setOnClickListener(this);
        binding.whatsapp.setOnClickListener(this);
        binding.share.setOnClickListener(this);
        binding.inviteLayout.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.teacher_my_classroom_layout;
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

    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case SEND_MESSAGE_CLICK:
                logeventparam.put(getResources().getString(R.string.log_send_message_teacher), "true");
                mFirebaseAnalytics.logEvent(getResources().getString(R.string.log_send_message_log_teacher), logeventparam);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstant.SENDING_DATA.STUDENT_DATA, (MyClassRoomStudentResModel) commonDataModel.getObject());
                SelectYourMessageDialogFragment fragment = new SelectYourMessageDialogFragment(); //where MyFragment is my fragment I want to show
                fragment.setCancelable(true);
                fragment.setArguments(bundle);
                fragment.show(getActivity().getSupportFragmentManager(), MyClassroomFragment.class.getSimpleName());
                break;
            case KYC_RESULT_PATH:
                /*do code here*/
                break;
        }
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        String completeLink = AuroApp.getAppContext().getResources().getString(R.string.teacher_share_msg);
        if (AuroApp.getAuroScholarModel() != null && !TextUtil.isEmpty(AuroApp.getAuroScholarModel().getReferralLink())) {
            completeLink = completeLink + AuroApp.getAuroScholarModel().getReferralLink();
            logeventparam.put(getResources().getString(R.string.log_get_referal_link_byscolor_teacher), "true");
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.log_share_links_teacher), logeventparam);
            mFirebaseAnalytics.setUserProperTy(getResources().getString(R.string.log_get_referal_link_byscolor_teacher), "true");
        } else {
            completeLink = completeLink + " https://rb.gy/np9uh5";
            logeventparam.put(getResources().getString(R.string.log_get_referal_link_byscolor_teacher), "false");
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.log_share_links_teacher), logeventparam);
            mFirebaseAnalytics.setUserProperTy(getResources().getString(R.string.log_get_referal_link_byscolor_teacher), "false");
        }
        if (v.getId() == R.id.whatsapp) {
            sendWhatsapp(completeLink);
        } else if (v.getId() == R.id.facebook) {
            shareAppLinkViaFacebook(completeLink);
        } else {
            shareWithFriends(completeLink);
        }

    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {

                case LOADING:
                    if (!isStateRestore) {
                        handleProgress(0, "");
                    }
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == Status.GET_TEACHER_DASHBOARD_API) {


                        boolean getTutorial = AppPref.INSTANCE.getBooleanTutorial(getResources().getString(R.string.pref_tutorial));
                        if (getTutorial) {
                            AppPref.INSTANCE.setBooleanTutorial(getResources().getString(R.string.pref_tutorial), false);
                            displayTutofacebook();
                        }

                        handleProgress(1, "");
                        myClassRoomResModel = (MyClassRoomTeacherResModel) responseApi.data;
                        if (AppUtil.callBackListner != null && myClassRoomResModel.getError()) {
                            binding.errorTxt.setText(myClassRoomResModel.getMessage());
                            binding.inviteLayout.setVisibility(View.GONE);
                            AppUtil.callBackListner.commonEventListner(AppUtil.getCommonClickModel(0, Status.GET_TEACHER_DASHBOARD_API, myClassRoomResModel));
                        }
                        AppUtil.myClassRoomResModel = myClassRoomResModel;
                        monthSpinner();
                        if (myClassRoomResModel != null
                                && !TextUtil.checkListIsEmpty(myClassRoomResModel.getStudentResModels())) {
                            binding.inviteLayout.setVisibility(View.GONE);
                            setAdapter(viewModel.teacherUseCase.makeStudentList(myClassRoomResModel.getStudentResModels(), DateUtil.getcurrentMonthNumber(), DateUtil.getcurrentYearNumber()));
                        } else {
                            binding.inviteLayout.setVisibility(View.VISIBLE);
                            binding.studentList.setVisibility(View.GONE);
                            binding.errorTxt.setVisibility(View.VISIBLE);
                        }

                    }
                    break;

                case FAIL:
                case NO_INTERNET:
                    handleProgress(2, (String) responseApi.data);
                    break;


                default:
                    handleProgress(2, (String) responseApi.data);
                    break;
            }

        });
    }

    private void handleProgress(int status, String message) {
        switch (status) {
            case 0:
                binding.parentLayout.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.shimmerMyClassroom.setVisibility(View.VISIBLE);
                binding.shimmerMyClassroom.startShimmer();
                break;

            case 1:
                binding.parentLayout.setVisibility(View.VISIBLE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.shimmerMyClassroom.setVisibility(View.GONE);
                binding.shimmerMyClassroom.stopShimmer();
                break;

            case 2:
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.parentLayout.setVisibility(View.GONE);
                binding.shimmerMyClassroom.setVisibility(View.GONE);
                binding.shimmerMyClassroom.stopShimmer();
                binding.errorLayout.textError.setText(message);
                binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewModel.getTeacherProfileData(AuroApp.getAuroScholarModel().getMobileNumber());
                    }
                });
                break;
        }

    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    public void shareWithFriends(String link) {
        logeventparam.put(getResources().getString(R.string.log_link_teacher), link);
        mFirebaseAnalytics.logEvent(getResources().getString(R.string.log_share_links_teacher), logeventparam);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Invite to AuroScholar Scholarship");
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        AuroApp.getAppContext().startActivity(shareIntent);
    }

    private void sendWhatsapp(String message) {
        logeventparam.put(getResources().getString(R.string.log_whatapplink_teacher), message);
        mFirebaseAnalytics.logEvent(getResources().getString(R.string.log_share_links_teacher), logeventparam);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(sendIntent);
        } else {
            ExitDialog mexitDialog = new ExitDialog(getActivity());
            mexitDialog.show();
        }
    }

    private void shareAppLinkViaFacebook(String urlToShare) {
        try {
            logeventparam.put(getResources().getString(R.string.log_facebook_teacher), urlToShare);
            mFirebaseAnalytics.logEvent(getResources().getString(R.string.log_share_links_teacher), logeventparam);
            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, urlToShare);
            PackageManager pm = AuroApp.getAppContext().getPackageManager();
            List<ResolveInfo> activityList = pm.queryIntentActivities(shareIntent, 0);
            for (final ResolveInfo app : activityList) {
                if ((app.activityInfo.name).contains("facebook")) {
                    final ActivityInfo activity = app.activityInfo;
                    final ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
                    shareIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                    shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    shareIntent.setComponent(name);
                    AuroApp.getAppContext().startActivity(shareIntent);
                    break;
                } else {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setQuote(urlToShare.replace("-  https://rb.gy/np9uh5", ""))
                            .setContentUrl(Uri.parse("  https://rb.gy/np9uh5"))
                            .build();

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        shareDialog.show(linkContent);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            shareWithFriends(urlToShare);
        }
    }

    protected void displayTutofacebook() {

        queue = new FancyShowCaseQueue();

        int[] viewLocation = new int[2];
        binding.socialShareLayout.getLocationOnScreen(viewLocation);


        btnfacebook = new FancyShowCaseView.Builder(getActivity())
                .focusOn(binding.socialShareLayout)
                .focusShape(FocusShape.ROUNDED_RECTANGLE)
                .customView(R.layout.tutorial_my_class_facebook_layout, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated(View view) {
                        setAnimatedContent(view, btnfacebook);
                    }
                })
                .build();
        btnprofile = new FancyShowCaseView.Builder(getActivity())
                .focusOn(toolbar.findViewById(R.id.action_profile))
                .focusShape(FocusShape.CIRCLE)
                .customView(R.layout.tutorial_my_class_facebook_layout, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated(View view) {
                        setAnimatedContent(view, btnprofile);
                    }
                })
                .build();
        btnKycapp = new FancyShowCaseView.Builder(getActivity())
                .focusOn(toolbar.findViewById(R.id.action_kyc))
                .focusShape(FocusShape.CIRCLE)
                .customView(R.layout.tutorial_my_class_facebook_layout, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated(View view) {
                        setAnimatedContent(view, btnKycapp);
                    }
                })
                .build();
        btnInfotutorial = new FancyShowCaseView.Builder(getActivity())
                .focusOn(toolbar.findViewById(R.id.action_info))
                .focusShape(FocusShape.CIRCLE)
                .customView(R.layout.tutorial_my_class_facebook_layout, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated(View view) {
                        setAnimatedContent(view, btnInfotutorial);
                    }
                })
                .build();
        queue.add(btnfacebook);
        queue.add(btnprofile);
        queue.add(btnKycapp);
        queue.add(btnInfotutorial);
        queue.show();
    }

    public void setAnimatedContent(View view, FancyShowCaseView fancyShowCaseView) {
        TextView link = view.findViewById(R.id.descFb);
        TextView profile = view.findViewById(R.id.tutorial_profile);
        TextView kyc = view.findViewById(R.id.tutorial_kyc);
        TextView info = view.findViewById(R.id.tutorial_info);
        LinearLayout layoutinvite = view.findViewById(R.id.llayoutinvite);
        LinearLayout layoutprofile = view.findViewById(R.id.layoutProfile);
        LinearLayout layoutkyc = view.findViewById(R.id.layoutkyc);
        LinearLayout layoutinfo = view.findViewById(R.id.layoutinfo);
        if (fancyShowCaseView == btnfacebook) {
            link.setVisibility(View.VISIBLE);
            layoutinvite.setVisibility(View.VISIBLE);
            link.setText(getResources().getString(R.string.tutorial_link));
        } else if (fancyShowCaseView == btnprofile) {
            link.setVisibility(View.GONE);
            profile.setVisibility(View.VISIBLE);
            layoutprofile.setVisibility(View.VISIBLE);
            layoutinvite.setVisibility(View.GONE);
            profile.setText(getResources().getString(R.string.tutorial_profile));
        } else if (fancyShowCaseView == btnKycapp) {
            link.setVisibility(View.GONE);
            profile.setVisibility(View.GONE);
            layoutprofile.setVisibility(View.VISIBLE);
            layoutprofile.setVisibility(View.GONE);
            layoutinvite.setVisibility(View.GONE);
            layoutkyc.setVisibility(View.VISIBLE);
            kyc.setVisibility(View.VISIBLE);
            kyc.setText(getResources().getString(R.string.tutorial_kyc));
        } else if (fancyShowCaseView == btnInfotutorial) {
            link.setVisibility(View.GONE);
            profile.setVisibility(View.GONE);
            layoutprofile.setVisibility(View.GONE);
            layoutprofile.setVisibility(View.GONE);
            layoutinvite.setVisibility(View.GONE);
            layoutkyc.setVisibility(View.GONE);
            info.setVisibility(View.VISIBLE);
            layoutinfo.setVisibility(View.VISIBLE);
            info.setText(getResources().getString(R.string.on_step_info));
        }

    }
}
