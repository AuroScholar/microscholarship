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
import com.auro.scholr.databinding.TeacherMyClassroomLayoutBinding;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.presentation.view.adapter.LeaderBoardAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.MonthSpinnerAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.MyClassroomAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.MyClassroomViewModel;
import com.auro.scholr.util.TextUtil;


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
    MyClassroomViewModel myClassroomViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        myClassroomViewModel = ViewModelProviders.of(this, viewModelFactory).get(MyClassroomViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        return binding.getRoot();
    }

    public void setAdapter() {
        binding.studentList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.studentList.setHasFixedSize(true);
        MyClassroomAdapter leaderBoardAdapter = new MyClassroomAdapter(getActivity(), myClassroomViewModel.teacherUseCase.makeListForFriendsLeaderBoard(true), null);
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

    }

    private void setDataOnUi() {

    }

    private void monthSpinner() {

        if (!TextUtil.checkListIsEmpty(myClassroomViewModel.teacherUseCase.monthDataModelList())) {
            MonthSpinnerAdapter stateSpinnerAdapter = new MonthSpinnerAdapter(myClassroomViewModel.teacherUseCase.monthDataModelList());
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

        /*if (myClassroomViewModel != null && myClassroomViewModel.serviceLiveData().hasObservers()) {
            myClassroomViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }*/
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
        setAdapter();

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

    }


}
