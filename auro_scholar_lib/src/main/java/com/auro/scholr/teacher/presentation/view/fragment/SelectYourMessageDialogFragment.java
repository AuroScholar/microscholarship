package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.databinding.DialogTeacherSelectYourMessageBinding;
import com.auro.scholr.teacher.data.model.SelectResponseModel;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomStudentResModel;
import com.auro.scholr.teacher.data.model.response.MyProfileResModel;
import com.auro.scholr.teacher.data.model.response.TeacherResModel;
import com.auro.scholr.teacher.presentation.view.adapter.SelectMessageAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.SelectYourMessageDialogModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class SelectYourMessageDialogFragment extends BaseDialog implements View.OnClickListener, CommonCallBackListner {
    @Inject
    @Named("SelectYourMessageDialogFragment")
    ViewModelFactory viewModelFactory;
    SelectYourMessageDialogModel viewModel;

    SelectMessageAdapter mteacherKycDocumentAdapter;
    DialogTeacherSelectYourMessageBinding binding;
    List<SelectResponseModel> list;
    MyClassRoomStudentResModel myClassRoomStudentResModel;
    SendInviteNotificationReqModel reqModel = new SendInviteNotificationReqModel();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myClassRoomStudentResModel = getArguments().getParcelable(AppConstant.SENDING_DATA.STUDENT_DATA);
        }
    }

    @Override
    protected void init() {
        selectMessageBoard();

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
        return R.layout.dialog_teacher_select_your_message;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SelectYourMessageDialogModel.class);
        binding.setLifecycleOwner(this);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        init();
        setListener();

        return binding.getRoot();
    }

    public void selectMessageBoard() {
        list = viewModel.teacherUseCase.makeListForSelectMessageModel();
        binding.rvselectMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvselectMessage.setHasFixedSize(true);
        binding.rvselectMessage.setNestedScrollingEnabled(false);
        mteacherKycDocumentAdapter = new SelectMessageAdapter(list, this);
        binding.rvselectMessage.setAdapter(mteacherKycDocumentAdapter);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_button) {
            dismiss();
        } else if (v.getId() == R.id.button) {
            callSendInvitApi();
        }
    }

    private void callSendInvitApi() {
        if (myClassRoomStudentResModel != null && !TextUtil.isEmpty(myClassRoomStudentResModel.getSudentMobile())) {
            reqModel.setNotification_title("Invitation");
            reqModel.setReceiver_mobile_no(myClassRoomStudentResModel.getSudentMobile());
            reqModel.setSender_mobile_no(AuroApp.getAuroScholarModel().getMobileNumber());
            ValidationModel validationModel = viewModel.teacherUseCase.validateSendInviteReq(reqModel);
            if (validationModel.isStatus()) {
                viewModel.sendInviteNotificationApi(reqModel);
            } else {
                showSnackbarError(validationModel.getMessage());
            }
        }
    }

    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case MESSAGE_SELECT_CLICK:
                for (int i = 0; i < list.size(); i++) {
                    if (i == commonDataModel.getSource()) {
                        list.get(i).setCheck(true);
                    } else {
                        list.get(i).setCheck(false);
                    }
                }

                reqModel.setNotification_message(list.get(commonDataModel.getSource()).getMessage());
                mteacherKycDocumentAdapter.setData(list);
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
                    if (responseApi.apiTypeStatus == Status.SEND_INVITE_API) {
                        TeacherResModel teacherResModel = (TeacherResModel) responseApi.data;

                        if (teacherResModel.getError()) {
                            showSnackbarError("Something Went Wrong! While Sending Invitation");
                        }
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
                    showSnackbarError(getActivity().getResources().getString(R.string.default_error));
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

                showSnackbarError(getActivity().getResources().getString(R.string.successfully_send));
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
