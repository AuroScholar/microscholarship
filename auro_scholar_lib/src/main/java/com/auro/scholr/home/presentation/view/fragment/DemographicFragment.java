package com.auro.scholr.home.presentation.view.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.DemographicFragmentLayoutBinding;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;
import com.auro.scholr.util.AuroScholar;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.DEMOGRAPHIC_API;


public class DemographicFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("DemographicFragment")
    ViewModelFactory viewModelFactory;


    DemographicFragmentLayoutBinding binding;
    DemographicViewModel demographicViewModel;
    private int pos;
    List<String> genderLines;
    List<String> schooltypeLines;
    List<String> boardLines;
    List<String> languageLines;
    DashboardResModel dashboardResModel;
    Resources resources;

    DemographicResModel demographicResModel = new DemographicResModel();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        demographicViewModel = ViewModelProviders.of(this, viewModelFactory).get(DemographicViewModel.class);
        binding.setLifecycleOwner(this);
        HomeActivity.setListingActiveFragment(HomeActivity.DEMOGRAPHIC_FRAGMENT);
        return binding.getRoot();
    }


    @Override
    protected void init() {
        // Spinner Drop down Gender
        genderLines = Arrays.asList(getResources().getStringArray(R.array.genderlist));
        spinnermethodcall(genderLines, binding.SpinnerGender);

        // Spinner Drop down schooltype
        schooltypeLines = Arrays.asList(getResources().getStringArray(R.array.schooltypelist));
        spinnermethodcall(schooltypeLines, binding.SpinnerSchoolType);

        // Spinner Drop down boardlist
        boardLines = Arrays.asList(getResources().getStringArray(R.array.boardlist));
        spinnermethodcall(boardLines, binding.SpinnerBoard);

        // Spinner Drop down language
        languageLines = Arrays.asList(getResources().getStringArray(R.array.languagelist));
        spinnermethodcall(languageLines, binding.SpinnerLanguageMedium);

        if (getArguments() != null && dashboardResModel != null) {
            demographicResModel.setPhonenumber(dashboardResModel.getPhonenumber());
        }

        if (demographicViewModel != null && demographicViewModel.serviceLiveData().hasObservers()) {
            demographicViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }

        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel.getUserLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            setLanguageText(AppConstant.HINDI);
        } else {
            setLanguageText(AppConstant.ENGLISH);
        }

    }

    private void setLanguageText(String text) {
        binding.toolbarLayout.langEng.setText(text);
    }

    public void spinnermethodcall(List<String> languageLines, AppCompatSpinner spinner) {
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, languageLines);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.submitbutton.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);

        binding.SpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                demographicResModel.setGender(binding.SpinnerGender.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        binding.SpinnerSchoolType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                demographicResModel.setSchool_type(binding.SpinnerSchoolType.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        binding.SpinnerLanguageMedium.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                demographicResModel.setLanguage(binding.SpinnerLanguageMedium.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        binding.SpinnerBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                demographicResModel.setBoard_type(binding.SpinnerBoard.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }


    @Override
    protected int getLayout() {
        return R.layout.demographic_fragment_layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);

        }
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

    }


    private void observeServiceResponse() {

        demographicViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar
                    handleProgress(0, "");
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == DEMOGRAPHIC_API) {
                        handleProgress(1, "");
                        getActivity().getSupportFragmentManager().popBackStack();
                        //  DemographicResModel demographicResModel = (DemographicResModel) responseApi.data;
                        //  AuroScholar.openAuroDashboardFragment(AuroApp.getAuroScholarModel());
                    }

                    break;

                case NO_INTERNET:
//On fail
                    handleProgress(2, (String) responseApi.data);
                    break;

                case AUTH_FAIL:
                case FAIL_400:
                default:
                    handleProgress(1, (String) responseApi.data);
                    showSnackbarError(getString(R.string.default_error));
                    break;
            }

        });
    }


    private void handleProgress(int value, String message) {
        if (value == 0) {

            binding.submitbutton.setText("");
            binding.submitbutton.setEnabled(false);
            binding.progressBar.setVisibility(View.VISIBLE);

        } else if (value == 1) {

            binding.submitbutton.setText(getActivity().getString(R.string.submit));
            binding.submitbutton.setEnabled(true);
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submitbutton) {
            String validation = demographicViewModel.homeUseCase.validateDemographic(demographicResModel);
            if (TextUtil.isEmpty(validation)) {
                demographicViewModel.getDemographicData(demographicResModel);
            } else {
                showSnackbarError(validation);
            }
        } else if (v.getId() == R.id.lang_eng) {
            String text = binding.toolbarLayout.langEng.getText().toString();
            if (!TextUtil.isEmpty(text) && text.equalsIgnoreCase(AppConstant.HINDI)) {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_HI);
                resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.ENGLISH);
            } else {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
                resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.HINDI);
            }
            reloadFragment();

        }


    }


    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

}
