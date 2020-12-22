package com.auro.scholr.teacher.presentation.view.fragment;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.databinding.FragmentTeacherSaveDetailBinding;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherKycViewModel;
import com.auro.scholr.teacher.presentation.viewmodel.TeacherSaveDetailViewModel;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherSaveDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherSaveDetailFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Inject
    @Named("TeacherSaveDetailFragment")
    ViewModelFactory viewModelFactory;
    boolean isStateRestore;
    TeacherSaveDetailViewModel teacherSaveDetailViewModel;


    FragmentTeacherSaveDetailBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TeacherSaveDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherSaveDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherSaveDetailFragment newInstance(String param1, String param2) {
        TeacherSaveDetailFragment fragment = new TeacherSaveDetailFragment();
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
        // Inflate the layout for this fragment
      //  return inflater.inflate(R.layout.fragment_teacher_save_detail, container, false);

        if (binding != null) {
            isStateRestore = true;
            return binding.getRoot();
        }
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        teacherSaveDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(TeacherSaveDetailViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setTeacherSaveDetailViewModel(teacherSaveDetailViewModel);
        setRetainInstance(true);
        return binding.getRoot();
    }

    @Override
    protected void init() {
        binding.headerParent.cambridgeHeading.setVisibility(View.GONE);

    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_teacher_save_detail;
    }
}
