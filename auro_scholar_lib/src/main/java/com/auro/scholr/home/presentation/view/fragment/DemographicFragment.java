package com.auro.scholr.home.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
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
import com.auro.scholr.core.common.ValidationModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.DemographicFragmentLayoutBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.DeviceUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.alert_dialog.CustomDialogModel;
import com.auro.scholr.util.alert_dialog.CustomProgressDialog;
import com.auro.scholr.util.permission.LocationHandler;
import com.auro.scholr.util.permission.LocationModel;
import com.auro.scholr.util.permission.LocationUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.DEMOGRAPHIC_API;
import static com.auro.scholr.util.permission.LocationHandler.REQUEST_CHECK_SETTINGS_GPS;


public class DemographicFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("DemographicFragment")
    ViewModelFactory viewModelFactory;

    String TAG = "DemographicFragment";

    DemographicFragmentLayoutBinding binding;
    DemographicViewModel demographicViewModel;
    private int pos;
    List<String> genderLines;
    List<String> schooltypeLines;
    List<String> boardLines;
    List<String> languageLines;
    List<String> privateTutionList;
    List<String> privateTutionTypeList;
    DashboardResModel dashboardResModel;
    Resources resources;
    boolean isLocationFine;
    CustomProgressDialog customProgressDialog;

    DemographicResModel demographicResModel = new DemographicResModel();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        demographicViewModel = ViewModelProviders.of(this, viewModelFactory).get(DemographicViewModel.class);
        binding.setLifecycleOwner(this);
        HomeActivity.setListingActiveFragment(HomeActivity.DEMOGRAPHIC_FRAGMENT);
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }


    @Override
    protected void init() {
        binding.toolbarLayout.backArrow.setVisibility(View.VISIBLE);
        setKeyListner();
        if (TextUtil.isEmpty(dashboardResModel.getLatitude()) && TextUtil.isEmpty(dashboardResModel.getLongitude())) {
            askPermission();
        }

        if (!TextUtil.isEmpty(dashboardResModel.getIsPrivateTution())) {
            demographicResModel.setIsPrivateTution(dashboardResModel.getIsPrivateTution());
        } else {
            demographicResModel.setIsPrivateTution(AppConstant.DocumentType.NO);
            dashboardResModel.setIsPrivateTution(AppConstant.DocumentType.NO);
            demographicResModel.setPrivateTutionType("");
        }

        // Spinner Drop down Gender
        genderLines = Arrays.asList(getResources().getStringArray(R.array.genderlist));
        spinnermethodcall(genderLines, binding.SpinnerGender);
        for (int i = 0; i < genderLines.size(); i++) {
            String gender = genderLines.get(i);
            if (!TextUtil.isEmpty(dashboardResModel.getGender()) && gender.equalsIgnoreCase(dashboardResModel.getGender())) {
                binding.SpinnerGender.setSelection(i);
                break;
            }
        }

        // Spinner Drop down schooltype
        schooltypeLines = Arrays.asList(getResources().getStringArray(R.array.schooltypelist));
        spinnermethodcall(schooltypeLines, binding.SpinnerSchoolType);
        for (int i = 0; i < schooltypeLines.size(); i++) {
            String school = schooltypeLines.get(i);
            if (!TextUtil.isEmpty(dashboardResModel.getSchool_type()) && school.equalsIgnoreCase(dashboardResModel.getSchool_type())) {
                binding.SpinnerSchoolType.setSelection(i);
                break;
            }
        }

        // Spinner Drop down boardlist
        boardLines = Arrays.asList(getResources().getStringArray(R.array.boardlist));
        spinnermethodcall(boardLines, binding.SpinnerBoard);
        for (int i = 0; i < boardLines.size(); i++) {
            String board = boardLines.get(i);
            if (!TextUtil.isEmpty(dashboardResModel.getBoard_type()) && board.equalsIgnoreCase(dashboardResModel.getBoard_type())) {
                binding.SpinnerBoard.setSelection(i);
                break;
            }
        }

        // Spinner Drop down language
        languageLines = Arrays.asList(getResources().getStringArray(R.array.languagelist));
        spinnermethodcall(languageLines, binding.SpinnerLanguageMedium);
        for (int i = 0; i < languageLines.size(); i++) {
            String lang = languageLines.get(i);
            if (!TextUtil.isEmpty(dashboardResModel.getLanguage()) && lang.equalsIgnoreCase(dashboardResModel.getLanguage())) {
                binding.SpinnerLanguageMedium.setSelection(i);
                break;
            }
        }

        // Spinner Drop down tution
        privateTutionList = Arrays.asList(getResources().getStringArray(R.array.privateTutionList));
        spinnermethodcall(privateTutionList, binding.spinnerPrivateTution);
        for (int i = 0; i < privateTutionList.size(); i++) {
            String s = privateTutionList.get(i);
            if (!TextUtil.isEmpty(dashboardResModel.getIsPrivateTution()) && s.equalsIgnoreCase(dashboardResModel.getIsPrivateTution())) {
                binding.spinnerPrivateTution.setSelection(i);
                break;
            }
        }


        // Spinner Drop down tution  Type
        privateTutionTypeList = Arrays.asList(getResources().getStringArray(R.array.privateTypeList));
        spinnermethodcall(privateTutionTypeList, binding.spinnerPrivateType);
        for (int i = 0; i < privateTutionTypeList.size(); i++) {
            String lang = privateTutionTypeList.get(i);
            if (!TextUtil.isEmpty(dashboardResModel.getPrivateTutionType()) && lang.equalsIgnoreCase(dashboardResModel.getPrivateTutionType())) {
                binding.spinnerPrivateType.setSelection(i);
                break;
            }
        }


        if (getArguments() != null && dashboardResModel != null) {
            demographicResModel.setPhonenumber(dashboardResModel.getPhonenumber());
        }

        demographicResModel.setMobileModel(DeviceUtil.getModelName());
        demographicResModel.setMobileVersion(DeviceUtil.getVersionName());
        demographicResModel.setManufacturer(DeviceUtil.getManufacturer());

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
        //askPermission();
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
        binding.toolbarLayout.backArrow.setOnClickListener(this);

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


        binding.spinnerPrivateTution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String value = binding.spinnerPrivateTution.getSelectedItem().toString();
                demographicResModel.setIsPrivateTution(binding.spinnerPrivateTution.getSelectedItem().toString());
                if (value.equalsIgnoreCase(AppConstant.DocumentType.YES)) {
                    binding.spinnerPrivateType.setVisibility(View.VISIBLE);
                    binding.tvPrivateType.setVisibility(View.VISIBLE);
                    binding.privateTypeArrow.setVisibility(View.VISIBLE);
                } else {
                    binding.spinnerPrivateType.setVisibility(View.GONE);
                    binding.tvPrivateType.setVisibility(View.GONE);
                    binding.privateTypeArrow.setVisibility(View.GONE);
                    demographicResModel.setPrivateTutionType("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        binding.spinnerPrivateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String value = binding.spinnerPrivateType.getSelectedItem().toString();
                if (value.equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_PRIVATE_TUTION)) {
                    demographicResModel.setPrivateTutionType("");
                } else {
                    demographicResModel.setPrivateTutionType(binding.spinnerPrivateType.getSelectedItem().toString());
                }
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
                        DemographicResModel demographicResModel = (DemographicResModel) responseApi.data;
                        if (!demographicResModel.getError()) {
                            handleProgress(1, "");
                            getActivity().getSupportFragmentManager().popBackStack();
                        } else {
                            showSnackbarError(getString(R.string.default_error));
                        }

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
            ValidationModel validation = demographicViewModel.homeUseCase.validateDemographic(demographicResModel);
            if (validation.isStatus()) {
                demographicViewModel.getDemographicData(demographicResModel);
            } else {
                showSnackbarError(validation.getMessage());
            }
        } else if (v.getId() == R.id.lang_eng) {
            String text = binding.toolbarLayout.langEng.getText().toString();
            if (!TextUtil.isEmpty(text) && text.equalsIgnoreCase(AppConstant.HINDI)) {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_HI);
                //resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.ENGLISH);
            } else {
                ViewUtil.setLanguage(AppConstant.LANGUAGE_EN);
             //   resources = ViewUtil.getCustomResource(getActivity());
                setLanguageText(AppConstant.HINDI);
            }
            reloadFragment();

        } else if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
        }


    }


    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private void askPermission() {
        String rationale = "Please give location permission for provide you the better service.";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mLocationPermission, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                getCurrentLocation();
                AppLogger.e(TAG, "Location Permission Granted");
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                //getActivity().getSupportFragmentManager().popBackStack();
                AppLogger.e(TAG, "Location Permission Denied");

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS_GPS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    AppLogger.e(TAG, "GPS on Allow");
                    getCurrentLocation();
                    break;
                case Activity.RESULT_CANCELED:
                    AppLogger.e(TAG, "GPS on Denied");
                    break;
                default:
                    // do nothing here
                    break;

            }

        }
    }

    public void getCurrentLocation() {
        LocationHandler locationHandlerUpdate = new LocationHandler();
        locationHandlerUpdate.setUpGoogleClient(getActivity());
        if (LocationUtil.isGPSEnabled(getActivity())) {
            callServiceWhenLocationReceived();
        }
    }

    private void callServiceWhenLocationReceived() {
        LocationModel locationModel = LocationUtil.getLocationData();
        openProgressDialog();
        if (locationModel != null && locationModel.getLatitude() != null && !locationModel.getLatitude().isEmpty()) {
            AppLogger.e(TAG, "Location Found");
            if (customProgressDialog != null) {
                customProgressDialog.dismiss();
            }
            demographicResModel.setLatitude(locationModel.getLatitude());
            demographicResModel.setLongitude(locationModel.getLongitude());
        } else {
            Handler handler = new Handler();
            handler.postDelayed(this::callServiceWhenLocationReceived, 2000);
        }
    }

    private void openProgressDialog() {
        if (customProgressDialog != null && customProgressDialog.isShowing()) {
            return;
        }
        CustomDialogModel customDialogModel = new CustomDialogModel();
        customDialogModel.setContext(getActivity());
        customDialogModel.setTitle("Fetching Your Location...");
        customDialogModel.setTwoButtonRequired(true);
        customProgressDialog = new CustomProgressDialog(customDialogModel);
        Objects.requireNonNull(customProgressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customProgressDialog.setCancelable(false);
        customProgressDialog.show();
        customProgressDialog.updateDataUi(0);
    }

    private void setKeyListner() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });
    }
}
