package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.auro.scholr.databinding.TeacherMyClassroomLayoutBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.teacher.data.model.common.MonthDataModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomResModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomStudentResModel;
import com.auro.scholr.teacher.presentation.view.adapter.MonthSpinnerAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.MyClassroomAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.MyClassroomViewModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.AuroScholar;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class MyClassroomFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("MyClassroomFragment")
    ViewModelFactory viewModelFactory;
    String TAG = "MyClassroomFragment";
    TeacherMyClassroomLayoutBinding binding;
    MyClassroomViewModel viewModel;
    boolean isStateRestore;
    MyClassRoomResModel myClassRoomResModel;
    List<MonthDataModel> monthDataModelList;
    MyClassroomAdapter leaderBoardAdapter;


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
        List<String> list = new ArrayList<>();
        list.add("odd");
        list.add("even");
        list.add("primary");
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        setDataOnUi();
        // viewModel.getTeacherProfileData("7503600686");

        viewModel.getTeacherProfileData(AuroApp.getAuroScholarModel().getMobileNumber());

    }

    private void setDataOnUi() {

    }

    private void monthSpinner() {
        String date = "";
        if (myClassRoomResModel != null && myClassRoomResModel.getTeacherResModel() != null && !TextUtil.isEmpty(myClassRoomResModel.getTeacherResModel().getRegistrationDate())) {
            date = myClassRoomResModel.getTeacherResModel().getRegistrationDate();
        }
        monthDataModelList = viewModel.teacherUseCase.monthDataModelList(date);
        if (!TextUtil.checkListIsEmpty(monthDataModelList)) {
            MonthSpinnerAdapter stateSpinnerAdapter = new MonthSpinnerAdapter(monthDataModelList);
            binding.monthSpinner.setAdapter(stateSpinnerAdapter);
            binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (leaderBoardAdapter != null && myClassRoomResModel != null && !TextUtil.checkListIsEmpty(myClassRoomResModel.getTeacherResModel().getStudentResModels())) {
                        leaderBoardAdapter.updateList(viewModel.teacherUseCase.makeStudentList(myClassRoomResModel.getTeacherResModel().getStudentResModels(), monthDataModelList.get(position).getMonthNumber(), monthDataModelList.get(position).getYear()));
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            int year = DateUtil.getcurrentYearNumber();
            int month = DateUtil.getcurrentMonthNumber();
            for (int i = 0; i < monthDataModelList.size(); i++) {
                MonthDataModel model = monthDataModelList.get(i);
                if (year == model.getYear() && month == model.getMonthNumber()) {
                    binding.monthSpinner.setSelection(i);
                }
            }
        }


    }


    private void setLanguageText(String text) {
        // binding.toolbarLayout.langEng.setText(text);
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
        } else {
            completeLink = completeLink + " https://bit.ly/3b1puWr";
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
                        handleProgress(1, "");
                        myClassRoomResModel = (MyClassRoomResModel) responseApi.data;
                        AppUtil.myClassRoomResModel = myClassRoomResModel;
                        monthSpinner();
                        if (myClassRoomResModel != null && myClassRoomResModel.getTeacherResModel() != null
                                && !TextUtil.checkListIsEmpty(myClassRoomResModel.getTeacherResModel().getStudentResModels())) {
                            setAdapter(viewModel.teacherUseCase.makeStudentList(myClassRoomResModel.getTeacherResModel().getStudentResModels(), DateUtil.getcurrentMonthNumber(), DateUtil.getcurrentYearNumber()));
                        } else {
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
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        AuroApp.getAppContext().startActivity(shareIntent);
    }


    private void sendWhatsapp(String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    private void shareAppLinkViaFacebook(String urlToShare) {
        try {
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
                }
            }
        } catch (Exception e) {
            shareWithFriends(urlToShare);
        }
    }
}
