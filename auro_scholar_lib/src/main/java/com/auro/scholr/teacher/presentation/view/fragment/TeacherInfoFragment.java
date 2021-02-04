package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.FragmentTeacherInfoBinding;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.teacher.data.model.response.MyClassRoomTeacherResModel;
import com.auro.scholr.teacher.data.model.response.TeacherProgressModel;
import com.auro.scholr.teacher.data.model.response.ZohoAppointmentListModel;
import com.auro.scholr.teacher.presentation.view.adapter.TeacherProgressAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherInfoViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;

import javax.inject.Inject;
import javax.inject.Named;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class TeacherInfoFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {
    @Inject
    @Named("TeacherInfoFragment")
    ViewModelFactory viewModelFactory;
    FragmentTeacherInfoBinding binding;
    TeacherInfoViewModel viewModel;
    boolean isStateRestore;

    CustomProgressDialog customProgressDialog;
    Resources resources;

    TeacherProgressAdapter mteacherProgressAdapter;

    public TeacherInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding != null) {
            isStateRestore = true;
            return binding.getRoot();
        }
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TeacherInfoViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setTeacherInfoViewModel(viewModel);
        setRetainInstance(true);

        return binding.getRoot();
    }

    @Override
    protected void init() {
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        setListener();
        viewModel.getTeacherDashboardData(AuroApp.getAuroScholarModel().getMobileNumber());

        viewModel.getTeacherProgressData(AuroApp.getAuroScholarModel().getMobileNumber());
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }

        binding.button.setOnClickListener(this);
    }

    private void openProgressDialog() {
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle(AuroApp.getAppContext().getResources().getString(R.string.getting_webinar_slots));
        // customDialogModel.setContent(getActivity().getResources().getString(R.string.bullted_list));
        customDialogModel.setTwoButtonRequired(false);
        customProgressDialog = new CustomProgressDialog(customDialogModel);
        Objects.requireNonNull(customProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();
        customProgressDialog.setProgressDialogText(AuroApp.getAppContext().getResources().getString(R.string.getting_webinar_slots));


        // customProgressDialog.updateDataUi(0);
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {

                case LOADING:
                    if (responseApi.apiTypeStatus == Status.GET_ZOHO_APPOINTMENT) {
                        openProgressDialog();
                    }
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == Status.GET_TEACHER_PROGRESS_API) {
                        binding.parentLayoutTwo.setVisibility(View.VISIBLE);
                        TeacherProgressModel teacherProgressModel = (TeacherProgressModel) responseApi.data;
                        binding.rvDoucumentUpload.setLayoutManager(new LinearLayoutManager(getActivity()));
                        binding.rvDoucumentUpload.setHasFixedSize(true);
                        binding.rvDoucumentUpload.setNestedScrollingEnabled(false);
                        mteacherProgressAdapter = new TeacherProgressAdapter(teacherProgressModel.teacherProgressStatusModels, this);
                        binding.rvDoucumentUpload.setAdapter(mteacherProgressAdapter);

                    }
                    if (responseApi.apiTypeStatus == Status.GET_ZOHO_APPOINTMENT) {
                        if (responseApi.apiTypeStatus == Status.GET_ZOHO_APPOINTMENT) {
                            if (customProgressDialog != null) {
                                customProgressDialog.dismiss();
                            }
                        }

                        ZohoAppointmentListModel zohoAppointmentListModel = (ZohoAppointmentListModel) responseApi.data;

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(AppConstant.SENDING_DATA.APPOINTMENT_DATA, zohoAppointmentListModel);
                        SelectYourAppointmentDialogFragment fragment = new SelectYourAppointmentDialogFragment(); //where MyFragment is my fragment I want to show
                        fragment.setCancelable(true);
                        fragment.setArguments(bundle);
                        fragment.show(getActivity().getSupportFragmentManager(), TeacherInfoFragment.class.getSimpleName());

                    }
                    if (responseApi.apiTypeStatus == Status.GET_TEACHER_DASHBOARD_API) {
                        AppUtil.myClassRoomResModel = (MyClassRoomTeacherResModel) responseApi.data;
                    }
                    break;

                case FAIL:
                case NO_INTERNET:
                    if (responseApi.apiTypeStatus == Status.GET_TEACHER_PROGRESS_API) {
                        handleProgress(1);
                        showSnackbarError((String) responseApi.data);
                    }
                    if (responseApi.apiTypeStatus == Status.GET_ZOHO_APPOINTMENT) {
                        if (customProgressDialog != null) {
                            customProgressDialog.dismiss();
                        }
                        showSnackbarError((String) responseApi.data);
                    }

                    break;

                default:
                    if (responseApi.apiTypeStatus == Status.GET_ZOHO_APPOINTMENT) {
                        if (customProgressDialog != null) {
                            customProgressDialog.dismiss();
                        }
                    }

                    handleProgress(1);
                    showSnackbarError(getString(R.string.default_error));

                    break;
            }

        });
    }


    public void handleProgress(int status) {
        switch (status) {
            case 0:
                binding.button.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                break;
            case 1:
                binding.button.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.setListingActiveFragment(HomeActivity.TEACHER_INFO_FRAGMENT);
        resources = ViewUtil.getCustomResource(getActivity());
        init();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_teacher_info;
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case WEBINAR_CLICK:
                // viewModel.getZohoAppointment();
                break;
        }
    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppLogger.e("chhonker", "fragment requestCode=" + requestCode);

    }

    @Override
    public void onClick(View v) {

    }

}
