package com.auro.scholr.teacher.presentation.view.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseDialog;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.databinding.DialogTeacherSelectYourMessageBinding;
import com.auro.scholr.home.presentation.viewmodel.InviteFriendViewModel;
import com.auro.scholr.teacher.data.model.SelectResponseModel;
import com.auro.scholr.teacher.presentation.view.adapter.SelectMessageAdapter;
import com.auro.scholr.teacher.presentation.view.adapter.TeacherKycDocumentAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.SelectYourMessageDialogModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectYourMessageDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectYourMessageDialogFragment extends BaseDialog implements View.OnClickListener, CommonCallBackListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Inject
    @Named("SelectYourMessageDialogFragment")
    ViewModelFactory viewModelFactory;
    SelectYourMessageDialogModel selectYourMessageDialogModel;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SelectMessageAdapter mteacherKycDocumentAdapter;
    DialogTeacherSelectYourMessageBinding binding;
    List<SelectResponseModel> list;

    public SelectYourMessageDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectYourMessageDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectYourMessageDialogFragment newInstance(String param1, String param2) {
        SelectYourMessageDialogFragment fragment = new SelectYourMessageDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        selectYourMessageDialogModel = ViewModelProviders.of(this, viewModelFactory).get(SelectYourMessageDialogModel.class);
        binding.setLifecycleOwner(this);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        init();
        setListener();

        return binding.getRoot();
    }

    public void selectMessageBoard() {
        list = selectYourMessageDialogModel.teacherUseCase.makeListForSelectMessageModel();
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
                mteacherKycDocumentAdapter.setData(list);
                break;
        }
    }
}
