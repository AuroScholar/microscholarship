package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseDialog;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.DialogTeacherSelectYourAppointmentBinding;
import com.auro.scholr.teacher.data.model.response.ZohoAppointmentListModel;
import com.auro.scholr.teacher.presentation.view.adapter.SelectAppointmentAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.SelectYourAppointmentDialogModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SelectYourAppointmentDialogFragment extends BaseDialog implements View.OnClickListener, CommonCallBackListner {
    @Inject
    @Named("SelectYourAppointmentDialogFragment")
    ViewModelFactory viewModelFactory;
    SelectYourAppointmentDialogModel viewModel;

    SelectAppointmentAdapter mSelectAppointmentAdapter;
    DialogTeacherSelectYourAppointmentBinding binding;
    List<String> list;
    String selectedTimeDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ZohoAppointmentListModel zohoAppointmentListModel = getArguments().getParcelable(AppConstant.SENDING_DATA.APPOINTMENT_DATA);
            list = zohoAppointmentListModel.data;
        }
    }

    @Override
    protected void init() {
        selectMessageBoard();
        if (list.isEmpty()) {
            binding.tvNoSlot.setVisibility(View.VISIBLE);
            binding.viewBook.setVisibility(View.GONE);
            binding.viewEmail.setVisibility(View.GONE);
        }
        else{
            selectedTimeDate= list.get(0);
        }
        if (!TextUtil.isEmpty(AppUtil.myClassRoomResModel.getTeacherEmail())) {
            binding.editEmail.setText(AppUtil.myClassRoomResModel.getTeacherEmail());
        }

    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.closeButton.setOnClickListener(this);
        binding.button.setOnClickListener(this);
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_teacher_select_your_appointment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectYourAppointmentDialogModel.class);
        binding.setLifecycleOwner(this);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        init();
        setListener();

        return binding.getRoot();
    }

    public void selectMessageBoard() {

        binding.rvselectMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvselectMessage.setHasFixedSize(true);
        binding.rvselectMessage.setNestedScrollingEnabled(false);
        mSelectAppointmentAdapter = new SelectAppointmentAdapter(list, this);
        binding.rvselectMessage.setAdapter(mSelectAppointmentAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_button) {
            dismiss();
        } else if (v.getId() == R.id.button) {
            if (TextUtil.isEmpty(selectedTimeDate)) {
                showSnackbarError(getString(R.string.select_single_slot));
            } else {
                if(binding.editEmail.getText() == null || TextUtil.isEmpty(binding.editEmail.getText().toString())){
                    showSnackbarError("Please provide email id");
                }else{
                    viewModel.bookZohoAppointment(selectedTimeDate, AppUtil.myClassRoomResModel.getTeacherName(), AppUtil.myClassRoomResModel.getTeacherEmail(), AuroApp.getAuroScholarModel().getMobileNumber());
                }

            }
        }
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case GET_ZOHO_APPOINTMENT:
                selectedTimeDate = (String) commonDataModel.getObject();

                break;
        }
    }

    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {
            switch (responseApi.status) {
                case LOADING:

                    handleProgress(0);

                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == Status.GET_ZOHO_APPOINTMENT) {
                        ZohoAppointmentListModel zohoAppointmentListModel = (ZohoAppointmentListModel) responseApi.data;

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismiss();
                            }
                        }, 3000);

                        // showSnackbarError(getString(R.string.successfully_send));
                        handleProgress(1);
                    }
                    break;

                case FAIL:
                case NO_INTERNET:
                    handleProgress(1);
                    showSnackbarError((String) responseApi.data);

                    break;

                default:
                    handleProgress(1);
                    showSnackbarError(getString(R.string.default_error));
                    break;
            }

        });
    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    public void handleProgress(int status) {
        switch (status) {
            case 0:
                binding.button.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                break;
            case 1:

                showSnackbarError(getString(R.string.successfully_send));
                binding.button.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 3000);

                break;
        }
    }
}
