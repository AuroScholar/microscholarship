package com.auro.scholr.home.presentation.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FragmentQuizHomeNewBinding;
import com.auro.scholr.home.presentation.view.adapter.QuizItemNewAdapter;
import com.auro.scholr.home.presentation.viewmodel.QuizViewNewModel;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizHomeNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizHomeNewFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    @Inject
    @Named("QuizHomeNewFragment")
    ViewModelFactory viewModelFactory;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentQuizHomeNewBinding binding;
    boolean isStateRestore;
    QuizViewNewModel quizViewNewModel;
    QuizItemNewAdapter quizItemAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public QuizHomeNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizHomeNewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizHomeNewFragment newInstance(String param1, String param2) {
        QuizHomeNewFragment fragment = new QuizHomeNewFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        quizViewNewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewNewModel.class);
        binding.setLifecycleOwner(this);
        binding.setQuizViewNewModel(quizViewNewModel);

        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel != null && TextUtil.isEmpty(prefModel.getUserLanguage())) {
            ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
        }
        setRetainInstance(true);
        return binding.getRoot();
      //  return inflater.inflate(R.layout.fragment_quiz_home_new, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void init() {
        setQuizListAdapter();
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_quiz_home_new;
    }

    @Override
    public void onClick(View view) {

    }


    private void setQuizListAdapter() {
        binding.quizTypeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quizTypeList.setHasFixedSize(true);
        quizItemAdapter = new QuizItemNewAdapter(this.getContext());
        binding.quizTypeList.setAdapter(quizItemAdapter);

    }
}
