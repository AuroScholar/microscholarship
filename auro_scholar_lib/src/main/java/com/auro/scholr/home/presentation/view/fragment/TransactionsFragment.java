package com.auro.scholr.home.presentation.view.fragment;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.FragmentTransactionsBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.passportmodels.PassportMonthModel;
import com.auro.scholr.home.data.model.passportmodels.PassportQuizDetailModel;
import com.auro.scholr.home.data.model.passportmodels.PassportQuizGridModel;
import com.auro.scholr.home.data.model.passportmodels.PassportQuizModel;
import com.auro.scholr.home.data.model.passportmodels.PassportReqModel;
import com.auro.scholr.home.data.model.passportmodels.PassportSubjectModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.activity.StudentDashboardActivity;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.home.presentation.view.adapter.LeaderBoardAdapter;
import com.auro.scholr.home.presentation.view.adapter.MontlyWiseAdapter;
import com.auro.scholr.home.presentation.view.adapter.PassportSpinnerAdapter;
import com.auro.scholr.home.presentation.view.adapter.passportadapter.PassportMonthAdapter;
import com.auro.scholr.home.presentation.viewmodel.FriendsLeaderShipViewModel;
import com.auro.scholr.home.presentation.viewmodel.TransactionsViewModel;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.teacher.data.model.common.MonthDataModel;
import com.auro.scholr.util.AppLogger;
import com.auro.scholr.util.ConversionUtil;
import com.auro.scholr.util.DateUtil;
import com.auro.scholr.util.TextUtil;
import com.auro.scholr.util.ViewUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class    TransactionsFragment  extends BaseFragment implements View.OnClickListener{

    @Inject
    @Named("TransactionsFragment")
    ViewModelFactory viewModelFactory;
    FragmentTransactionsBinding binding;
    TransactionsViewModel viewModel;
    MontlyWiseAdapter leaderBoardAdapter;
    Resources resources;
    DashboardResModel dashboardResModel;
    List<MonthDataModel> monthDataModelList;
    String TAG = "TransactionsFragment";
    boolean isFirstTime = false;
    MonthDataModel spinnerMonth;
    MonthDataModel spinnerSubject;
    boolean userClick = false;
    PassportSpinnerAdapter subjectSpinner;
    List<MonthDataModel> subjectResModelList;
    boolean isMonthSelected = false;


    public TransactionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_transactions, container, false);

        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TransactionsViewModel.class);
        binding.setLifecycleOwner(this);
        setRetainInstance(true);
        ViewUtil.setLanguageonUi(getActivity());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
        setToolbar();
        setListener();
    }

    @Override
    protected void init() {

        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }
        HomeActivity.setListingActiveFragment(HomeActivity.TRANSACTION_FRAGMENT);

        setListener();
        //  setPassportAdapter();
      /*  PrefModel prefModel = AuroAppPref.INSTANCE.getModelInstance();
        if (prefModel.getUserLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            setLanguageText(AppConstant.HINDI);
        } else {
            setLanguageText(AppConstant.ENGLISH);
        }*/

        setData1OnUI();
        monthSpinner();
        SubjectSpinner(null, 0);

        spinnerMonth = new MonthDataModel();
        spinnerMonth.setMonthNumber(DateUtil.getcurrentMonthNumber()+1);
        spinnerMonth.setYear(DateUtil.getcurrentYearNumber());
        spinnerMonth.setMonth(DateUtil.getMonthNameForSpinner());

        AppLogger.e(TAG, "current month-" + DateUtil.getMonthNameForSpinner());

        spinnerSubject = new MonthDataModel();
        spinnerSubject.setMonth("All");
        selectCurrentMonthInSpinner();
        checkCallApiStatus();
        ViewUtil.setProfilePic(binding.imageView6);

    }

    private void selectCurrentMonthInSpinner() {
        AppLogger.e(TAG, "selectCurrentMonthInSpinner step 1");
        if (!TextUtil.checkListIsEmpty(monthDataModelList)) {
            AppLogger.e(TAG, "selectCurrentMonthInSpinner step 2");
            for (int i = 0; i < monthDataModelList.size(); i++) {

                MonthDataModel monthDataModel = monthDataModelList.get(i);
                AppLogger.e(TAG, "selectCurrentMonthInSpinner step 3 month -" + monthDataModel.getMonth() + "-current month-" + DateUtil.getMonthNameForSpinner());

                if (monthDataModel.getMonth().equalsIgnoreCase(DateUtil.getMonthNameForSpinner()) || monthDataModel.getMonth().contains(DateUtil.getMonthNameForSpinner())) {
                    binding.monthSpinner.setSelection(i);
                    binding.monthTitle.setText(monthDataModel.getMonth());
                }
            }
        }
    }

    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        ((StudentMainDashboardActivity)getActivity()).setListingActiveFragment(StudentMainDashboardActivity.PASSPORT_FRAGMENT);

        ((StudentMainDashboardActivity) getActivity()).loadPartnerLogo(binding.auroScholarLogo);

        binding.headerParent.cambridgeHeading.setVisibility(View.VISIBLE);
        binding.toolbarLayout.backArrow.setOnClickListener(this);
        binding.toolbarLayout.langEng.setOnClickListener(this);
        binding.monthParentLayout.setOnClickListener(this);
        binding.subjectParentLayout.setOnClickListener(this);
        if (viewModel != null && viewModel.serviceLiveData().hasObservers()) {
            viewModel.serviceLiveData().removeObservers(this);
        } else {
            observeServiceResponse();
        }

        binding.backButton.setOnClickListener(this);
        binding.cardView2.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_transactions;

    }

    public void setPassportAdapter(PassportMonthModel passportMonthModel) {
        binding.monthlyWiseList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.monthlyWiseList.setHasFixedSize(true);
        binding.monthlyWiseList.setNestedScrollingEnabled(false);
        PassportMonthAdapter passportSpinnerAdapter = new PassportMonthAdapter(getActivity(), makePassportList(passportMonthModel), null);
        binding.monthlyWiseList.setAdapter(passportSpinnerAdapter);
    }

    private List<PassportMonthModel> makePassportList(PassportMonthModel passportResModel) {
        List<PassportMonthModel> monthModelList = new ArrayList<>();
        passportResModel.setMonthName(binding.monthTitle.getText().toString());
        passportResModel.setExpanded(true);
        for (int i = 0; i < passportResModel.getPassportSubjectModelList().size(); i++) {
            PassportSubjectModel passportSubjectModel = passportResModel.getPassportSubjectModelList().get(i);
            if (i == 0) {
                passportSubjectModel.setExpanded(true);
            }
            AppLogger.e(TAG, "step  1-" + passportResModel.getPassportSubjectModelList().size());
            for (PassportQuizModel quizModel : passportSubjectModel.getQuizData()) {
                AppLogger.e(TAG, "step  2-" + passportSubjectModel.getQuizData().size());
                for (PassportQuizDetailModel detailModel : quizModel.getQuizDetail()) {
                    AppLogger.e(TAG, "step  3-" + quizModel.getQuizDetail().size());
                    List<PassportQuizGridModel> gridList = new ArrayList<>();
                    for (int j = 0; j < 4; j++) {
                        PassportQuizGridModel gridModel = new PassportQuizGridModel();
                        if (j == 0) {
                            gridModel.setQuizHead("Quiz No");
                            gridModel.setQuizData(detailModel.getExamName());
                            gridModel.setQuizImagePath(R.drawable.quiz);
                            gridModel.setQuizColor(ContextCompat.getColor(getActivity(), R.color.black));
                            gridList.add(gridModel);
                        }
                        if (j == 1) {
                            gridModel.setQuizHead("Quiz Attempt");
                            gridModel.setQuizData(detailModel.getQuizAttempt());
                            gridModel.setQuizImagePath(R.drawable.attempt);
                            gridModel.setQuizColor(ContextCompat.getColor(getActivity(), R.color.black));
                            gridList.add(gridModel);
                        }
                        if (j == 2) {
                            gridModel.setQuizHead("Score");
                            gridModel.setQuizData(detailModel.getScore() + "/10");
                            gridModel.setQuizImagePath(R.drawable.score);
                            gridModel.setQuizColor(ContextCompat.getColor(getActivity(),R.color.ufo_green));
                            gridList.add(gridModel);
                        }
                        if (j == 3) {
                            gridModel.setQuizHead("Status");
                            gridModel.setQuizData(detailModel.getAmount_status());
                            gridModel.setQuizImagePath(R.drawable.status);
                            gridModel.setQuizColor(ContextCompat.getColor(getActivity(),R.color.ufo_green));
                            gridList.add(gridModel);
                        }
                    }
                    AppLogger.e(TAG, "step  4-" + gridList.size());
                    detailModel.setPassportQuizGridModelList(gridList);

                }
            }
        }
        monthModelList.add(passportResModel);
        return monthModelList;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.backButton) {
            getActivity().onBackPressed();
        } else if (id == R.id.lang_eng) {
            changeLanguage();
        } else if (id == R.id.bt_transfer_money) {
            openSendMoneyFragment();
        } else if (id == R.id.month_parent_layout) {
            userClick = true;
            binding.monthSpinner.performClick();
        } else if (id == R.id.subject_parent_layout) {
            userClick = true;
            AppLogger.e(TAG,"subject_parent_layout on click");
            binding.subjectSpinner.performClick();
        }else if (id ==  R.id.cardView2){
            ((StudentMainDashboardActivity)getActivity()).openProfileFragment();
        }
    }

    private void changeLanguage() {

        reloadFragment();
    }

    private void setLanguageText(String text) {
        binding.toolbarLayout.langEng.setText(text);
    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private void setData1OnUI() {
        String rs = getActivity().getResources().getString(R.string.rs);
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getApproved_scholarship_money())) {
            binding.amountTrajection.txtAmountApproved.setText(rs + dashboardResModel.getApproved_scholarship_money());
        } else {
            binding.amountTrajection.txtAmountApproved.setText(rs + "0");
        }

        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getDisapproved_scholarship_money())) {
            binding.amountTrajection.txtAmountRejected.setText(rs + dashboardResModel.getDisapproved_scholarship_money());
        } else {
            binding.amountTrajection.txtAmountRejected.setText(rs + "0");
        }

        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getUnapproved_scholarship_money())) {
            binding.amountTrajection.txtAmountVerification.setText(rs + dashboardResModel.getUnapproved_scholarship_money());
        } else {
            binding.amountTrajection.txtAmountVerification.setText(rs + "0");
        }

        int approvedValue = ConversionUtil.INSTANCE.convertStringToInteger(dashboardResModel.getApproved_scholarship_money());
        if (approvedValue > 0) {
            binding.amountTrajection.btTransferMoney.setOnClickListener(this);
            binding.amountTrajection.btTransferMoney.setVisibility(View.GONE);
        } else {
            binding.amountTrajection.btTransferMoney.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }




    public void openSendMoneyFragment() {
        Bundle bundle = new Bundle();
        SendMoneyFragment sendMoneyFragment = new SendMoneyFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        sendMoneyFragment.setArguments(bundle);
        openFragment(sendMoneyFragment);
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, TransactionsFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }

    private void monthSpinner() {
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        DashboardResModel dashboardResModel = prefModel.getDashboardResModel();
        String date = dashboardResModel.getRegistrationdate();
        //  String date = "2020-05-01 16:22:56";
        monthDataModelList = DateUtil.monthDataModelList(date);
        if (!TextUtil.checkListIsEmpty(monthDataModelList)) {
            PassportSpinnerAdapter monthSpinnerAdapter = new PassportSpinnerAdapter(monthDataModelList);
            binding.monthSpinner.setAdapter(monthSpinnerAdapter);
            binding.monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (userClick) {
                        userClick = false;
                        isMonthSelected = true;
                        spinnerMonth = monthDataModelList.get(position);
                        binding.monthTitle.setText(monthDataModelList.get(position).getMonth());
                        checkCallApiStatus();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            int year = DateUtil.getcurrentYearNumber();
            int month = DateUtil.getcurrentMonthNumber()+1;
            if (month != 0) {
                //month = month - 1;
            }
            for (int i = 0; i < monthDataModelList.size(); i++) {
                MonthDataModel model = monthDataModelList.get(i);
                if (year == model.getYear() && month == model.getMonthNumber()) {
                    binding.monthSpinner.setSelection(i);
                }
            }
        }
    }

    private void SubjectSpinner(PassportMonthModel passportMonthModel, int status) {
        subjectResModelList = new ArrayList<>();
        if (status == 1) {
            for (String subjectName : passportMonthModel.getSubjects()) {
                MonthDataModel monthDataModelnew = new MonthDataModel();
                monthDataModelnew.setMonth(subjectName);
                subjectResModelList.add(monthDataModelnew);
            }
            if (!TextUtil.checkListIsEmpty(passportMonthModel.getSubjects()) && passportMonthModel.getSubjects().size() > 1) {
                MonthDataModel monthDataModel = new MonthDataModel();
                monthDataModel.setMonth("All");
                subjectResModelList.add(monthDataModel);
            }
        } else {
            MonthDataModel monthDataModel = new MonthDataModel();
            monthDataModel.setMonth("All");
            subjectResModelList.add(monthDataModel);
            binding.subjectTitle.setText(monthDataModel.getMonth());
        }
        /*MonthDataModel monthDataModel_1 = new MonthDataModel();
        monthDataModel_1.setMonth("Social Science");
        subjectResModelList.add(monthDataModel_1);
        MonthDataModel monthDataModel_2 = new MonthDataModel();
        monthDataModel_2.setMonth("English");
        subjectResModelList.add(monthDataModel_2);*/
        if (!TextUtil.checkListIsEmpty(subjectResModelList)) {
            if (subjectSpinner == null) {
                subjectSpinner = new PassportSpinnerAdapter(Collections.emptyList());
                binding.subjectSpinner.setAdapter(subjectSpinner);
            }
            subjectSpinner.setDataList(subjectResModelList);
            binding.subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AppLogger.e(TAG, "on subject selected");
                    if (userClick) {
                        userClick = false;
                        spinnerSubject = subjectResModelList.get(position);
                        AppLogger.e(TAG, "on subject selected" + spinnerSubject.getMonth());
                        binding.subjectTitle.setText(subjectResModelList.get(position).getMonth());
                        checkCallApiStatus();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }
        if (!TextUtil.checkListIsEmpty(subjectResModelList) && subjectResModelList.size() > 1) {
            if (isMonthSelected) {
                isMonthSelected = false;
                MonthDataModel monthDataModel = subjectResModelList.get(subjectResModelList.size() - 1);
                binding.subjectSpinner.setSelection(subjectResModelList.size() - 1);
                binding.subjectTitle.setText(monthDataModel.getMonth());
            } else {
                setCurrentSubject();
            }
        }
        if (!TextUtil.checkListIsEmpty(subjectResModelList) && subjectResModelList.size() ==1) {
            binding.subjectSpinner.setSelection(0);
            binding.subjectTitle.setText(subjectResModelList.get(0).getMonth());
        }
    }

    private void setCurrentSubject() {
        AppLogger.e(TAG, "setCurrentSubject step 1");
        if (!TextUtil.checkListIsEmpty(subjectResModelList)) {
            AppLogger.e(TAG, "setCurrentSubject step 2");
            for (int i = 0; i < subjectResModelList.size(); i++) {
                String subjectName = binding.subjectTitle.getText().toString();
                MonthDataModel monthDataModel = subjectResModelList.get(i);
                AppLogger.e(TAG, "setCurrentSubject step 3 month -" + monthDataModel.getMonth() + "-current month-" + subjectName);
                if (monthDataModel.getMonth().equalsIgnoreCase(subjectName)) {
                    AppLogger.e(TAG, "setCurrentSubject step 4 found -" + monthDataModel.getMonth() + "-current month-" + subjectName);
                    binding.subjectSpinner.setSelection(i);
                    binding.subjectTitle.setText(monthDataModel.getMonth());
                }
            }
        }
    }





    private void checkCallApiStatus() {
        if (spinnerSubject != null && !TextUtil.isEmpty(spinnerSubject.getMonth()) && spinnerMonth != null && !TextUtil.isEmpty(spinnerMonth.getMonth())) {
            callTransportApi();
        }
    }

    private void callTransportApi() {
        AppLogger.e("chhonker spinner month--",""+spinnerMonth.getMonthNumber());
        int monthnum = spinnerMonth.getMonthNumber();
        String monNum = "" + monthnum;
        if (monthnum < 10) {
            monNum = "0" + monthnum;
        }
        String isall = "0";
        if (!TextUtil.isEmpty(spinnerSubject.getMonth()) & spinnerSubject.getMonth().equalsIgnoreCase("All")) {
            isall = "1";
        }
        String month = "" + spinnerMonth.getYear() + monNum;
        AppLogger.e("callTransportApi-", month);
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        handleProgress(0, "");
        PassportReqModel passportReqModel = new PassportReqModel();
        passportReqModel.setMobileNumber(prefModel.getUserMobile());
        AppLogger.e("callTransportApi-", month);
        passportReqModel.setMonth(month);
        passportReqModel.setSubject(spinnerSubject.getMonth());
        passportReqModel.setIsAll(isall);
        // passportReqModel.setIsAll();
        viewModel.getPassportInternetCheck(passportReqModel);
        /*spinnerSubject=null;
        spinnerMonth=null;*/
    }


    private void observeServiceResponse() {

        viewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:

                    break;

                case SUCCESS:
                    PassportMonthModel passportMonthModel = (PassportMonthModel) responseApi.data;
                    AppLogger.e(TAG, "SUCCESS 1");
                    SubjectSpinner(passportMonthModel, 1);
                    if (!TextUtil.checkListIsEmpty(passportMonthModel.getPassportSubjectModelList())) {
                        setPassportAdapter(passportMonthModel);
                        handleProgress(1, "No Data Found!");
                        AppLogger.e(TAG, "SUCCESS 2");
                    } else {
                        AppLogger.e(TAG, "SUCCESS 3");
                        handleProgress(2, "No Data Found!");
                    }
                    break;

                case NO_INTERNET:
                case AUTH_FAIL:
                case FAIL_400:
                default:
                    AppLogger.e(TAG, "Fail 4");
                    handleProgress(3, (String) responseApi.data);
                    break;

            }
        });
    }


    public void handleProgress(int i, String message) {
        switch (i) {
            case 0:
                binding.monthlyWiseList.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.progressBar2.setVisibility(View.VISIBLE);
                break;

            case 1:
                binding.monthlyWiseList.setVisibility(View.VISIBLE);
                binding.errorConstraint.setVisibility(View.GONE);
                binding.progressBar2.setVisibility(View.GONE);
                break;

            case 2:
                binding.monthlyWiseList.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.errorLayout.btRetry.setVisibility(View.GONE);
                binding.errorLayout.errorIcon.setVisibility(View.GONE);
                binding.errorLayout.textError.setText(message);
                binding.progressBar2.setVisibility(View.GONE);
                break;
            case 3:
                binding.monthlyWiseList.setVisibility(View.GONE);
                binding.errorConstraint.setVisibility(View.VISIBLE);
                binding.errorLayout.btRetry.setVisibility(View.VISIBLE);
                binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkCallApiStatus();
                    }
                });
                binding.errorLayout.errorIcon.setVisibility(View.GONE);
                binding.errorLayout.textError.setText(message);
                binding.progressBar2.setVisibility(View.GONE);
                break;
        }
    }

}