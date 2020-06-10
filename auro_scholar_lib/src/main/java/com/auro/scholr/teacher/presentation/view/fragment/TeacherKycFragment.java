package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.Intent;
import android.content.res.Resources;
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
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.databinding.FragmentTeacherKycBinding;
import com.auro.scholr.home.presentation.view.activity.CameraActivity;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.teacher.presentation.view.adapter.TeacherKycDocumentAdapter;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherKycViewModel;
import com.auro.scholr.util.AppUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.cropper.CropImageViews;
import com.auro.scholr.util.cropper.CropImages;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherKycFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherKycFragment extends BaseFragment implements CommonCallBackListner {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Inject
    @Named("TeacherKycFragment")
    ViewModelFactory viewModelFactory;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentTeacherKycBinding binding;
    TeacherKycViewModel teacherKycViewModel;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean isStateRestore;
    Resources resources;
    // TeacherDocumentViewModel viewModel;

    public TeacherKycFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherKycFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherKycFragment newInstance(String param1, String param2) {
        TeacherKycFragment fragment = new TeacherKycFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (binding != null) {
            isStateRestore = true;
            return binding.getRoot();
        }
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        teacherKycViewModel = ViewModelProviders.of(this, viewModelFactory).get(TeacherKycViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setTeacherKycViewModel(teacherKycViewModel);
        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    protected void init() {

        binding.headerTopParent.cambridgeHeading.setVisibility(View.GONE);
        setListener();
        setTeacherKycBoard();
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.setListingActiveFragment(HomeActivity.TEACHER_KYC_FRAGMENT);
        resources = ViewUtil.getCustomResource(getActivity());
        init();
        setDataOnUI();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_teacher_kyc;
    }

    public void setTeacherKycBoard() {

        binding.rvDoucumentUpload.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvDoucumentUpload.setHasFixedSize(true);
        binding.rvDoucumentUpload.setNestedScrollingEnabled(false);
        TeacherKycDocumentAdapter mteacherKycDocumentAdapter = new TeacherKycDocumentAdapter(teacherKycViewModel.teacherUseCase.makeListForTeacherDocumentModel(), this);
        binding.rvDoucumentUpload.setAdapter(mteacherKycDocumentAdapter);


    }

    private void setDataOnUI() {
        if (AppUtil.myClassRoomResModel != null && AppUtil.myClassRoomResModel.getTeacherResModel() != null) {
            if (!TextUtil.isEmpty(String.valueOf(AppUtil.myClassRoomResModel.getTeacherResModel().getScoreTotal()))) {
                binding.points.setText("" + AppUtil.myClassRoomResModel.getTeacherResModel().getScoreTotal());
            } else {
                binding.points.setText("0");
            }

            if (!TextUtil.isEmpty(String.valueOf(AppUtil.myClassRoomResModel.getTeacherResModel().getWalletBalance()))) {
                binding.walletBal.setText(" " + AuroApp.getAppContext().getResources().getString(R.string.rs) + AppUtil.myClassRoomResModel.getTeacherResModel().getWalletBalance());
            } else {
                binding.walletBal.setText("0");
            }
        }
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {
        switch (commonDataModel.getClickType()) {
            case DOCUMENT_CLICK:
                if (commonDataModel.getSource() == 3) {
                    openActivity();
                } else {
                    CropImages.activity()
                            .setGuidelines(CropImageViews.Guidelines.ON)
                            .start(getActivity());
                }
                break;
        }
    }

    public void openActivity() {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivityForResult(intent, AppConstant.CAMERA_REQUEST_CODE);
    }
}
