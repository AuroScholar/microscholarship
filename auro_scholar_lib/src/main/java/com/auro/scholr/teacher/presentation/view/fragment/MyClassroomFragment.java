package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.databinding.TeacherMyClassroomLayoutBinding;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.response.MyClassRoomResModel;
import com.auro.scholr.teacher.presentation.view.adapter.MonthSpinnerAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.MyClassroomAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.MyClassroomViewModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;


import java.util.ArrayList;
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

    public void setAdapter() {
        binding.studentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.studentList.setHasFixedSize(true);
        MyClassroomAdapter leaderBoardAdapter = new MyClassroomAdapter(getActivity(), myClassRoomResModel.getTeacherResModel().getStudentResModels(), null);
        binding.studentList.setAdapter(leaderBoardAdapter);
    }


    @Override
    protected void init() {

        List<String> list = new ArrayList<>();
        list.add("odd");
        list.add("even");
        list.add("primary");
        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        monthSpinner();
        setDataOnUi();
        viewModel.getTeacherProfileData("9654234507");

    }

    private void setDataOnUi() {

    }

    private void monthSpinner() {

        if (!TextUtil.checkListIsEmpty(viewModel.teacherUseCase.monthDataModelList())) {
            MonthSpinnerAdapter stateSpinnerAdapter = new MonthSpinnerAdapter(viewModel.teacherUseCase.monthDataModelList());
            binding.monthSpinner.setAdapter(stateSpinnerAdapter);
            binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
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
            case KYC_BUTTON_CLICK:

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
        shareWithFriends();
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
                    if (responseApi.apiTypeStatus == Status.GET_TEACHER_PROFILE_API) {
                        handleProgress(1, "");
                        myClassRoomResModel = (MyClassRoomResModel) responseApi.data;
                        AppUtil.myClassRoomResModel = myClassRoomResModel;
                        setAdapter();

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

                    }
                });
                break;
        }

    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }


    public void shareWithFriends() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, AuroApp.getAppContext().getResources().getString(R.string.share_msg));
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        AuroApp.getAppContext().startActivity(shareIntent);
    }
}
