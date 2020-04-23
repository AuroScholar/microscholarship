package com.auro.scholr.home.presentation.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.util.uiwidget.others.HideBottomNavigation;
import com.auro.scholr.databinding.QuizHomeLayoutBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.QuizItemAdapter;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.util.AuroScholar;
import com.auro.scholr.util.ViewUtil;
import com.auro.scholr.util.permission.PermissionHandler;
import com.auro.scholr.util.permission.PermissionUtil;
import com.auro.scholr.util.permission.Permissions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.DASHBOARD_API;


public class QuizHomeFragment extends BaseFragment implements View.OnClickListener, CommonCallBackListner {

    @Inject
    @Named("QuizHomeFragment")
    ViewModelFactory viewModelFactory;
    QuizHomeLayoutBinding binding;
    QuizViewModel quizViewModel;
    QuizItemAdapter quizItemAdapter;
    private static final String TAG = "CardFragment";
    DashboardResModel dashboardResModel;
    QuizResModel quizResModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        quizViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setQuizViewModel(quizViewModel);
        HomeActivity.setListingActiveFragment(HomeActivity.QUIZ_DASHBOARD_FRAGMENT);
        return binding.getRoot();
    }

    private void setAdapter(List<QuizResModel> resModelList) {
        binding.quizTypeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quizTypeList.setHasFixedSize(true);
        quizItemAdapter = new QuizItemAdapter(this.getContext(), resModelList, this);
        binding.quizTypeList.setAdapter(quizItemAdapter);

    }


    @Override
    protected void init() {
        if (getArguments() != null) {
            // mobileNumber = getArguments().getString(AppConstant.MOBILE_NUMBER);
        }
        if (quizViewModel != null && quizViewModel.serviceLiveData().hasObservers()) {
            quizViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
        quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel().getMobileNumber());
    }


    @Override
    protected void setToolbar() {
        /*Do code here*/
    }

    @Override
    protected void setListener() {
        binding.walletBalText.setOnClickListener(this);
    }


    @Override
    protected int getLayout() {
        return R.layout.quiz_home_layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        setToolbar();
        setListener();
        //checkJson();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void observeServiceResponse() {

        quizViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar
                    handleProgress(0, "");
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        handleProgress(1, "");
                        dashboardResModel = (DashboardResModel) responseApi.data;
                        if (dashboardResModel != null && dashboardResModel.getStatus().equalsIgnoreCase(AppConstant.FAILED)) {
                            handleProgress(2, dashboardResModel.getMessage());
                        } else {
                            setDataOnUi(dashboardResModel);
                        }

                    }

                    break;

                case NO_INTERNET:
//On fail
                    handleProgress(2, (String) responseApi.data);
                    break;

                case AUTH_FAIL:

// When Authrization is fail
                    handleProgress(2, (String) responseApi.data);
                    break;

                case FAIL_400:
                    //When 400 error occur
                    handleProgress(2, (String) responseApi.data);
                    break;


                default:
                    Log.d(TAG, "observeServiceResponse: default");
                    // ViewUtil.showSnackBar(binding.getRoot(), responseApi.data.toString());
                    handleProgress(2, (String) responseApi.data);
                    break;
            }

        });
    }

    private void handleProgress(int value, String message) {
        if (value == 0) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.mainParentLayout.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.startShimmer();
        } else if (value == 1) {
            binding.errorConstraint.setVisibility(View.GONE);
            binding.mainParentLayout.setVisibility(View.VISIBLE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.shimmerViewQuiz.stopShimmer();
        } else {
            binding.errorConstraint.setVisibility(View.VISIBLE);
            binding.mainParentLayout.setVisibility(View.GONE);
            binding.shimmerViewQuiz.setVisibility(View.GONE);
            binding.shimmerViewQuiz.stopShimmer();
            binding.errorLayout.textError.setText(message);
            binding.errorLayout.btRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quizViewModel.getDashBoardData(AuroApp.getAuroScholarModel().getMobileNumber());
                }
            });
        }

    }


    private void setDataOnUi(DashboardResModel dashboardResModel) {
        quizViewModel.walletBalance.setValue(getString(R.string.rs) + " " + dashboardResModel.getWalletbalance());
        setAdapter(dashboardResModel.getQuiz());
    }

    public void openQuizTestFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        QuizTestFragment quizTestFragment = new QuizTestFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        bundle.putParcelable(AppConstant.QUIZ_RES_MODEL, quizResModel);
        quizTestFragment.setArguments(bundle);
        openFragment(quizTestFragment);
    }

    public void openKYCFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);
        openFragment(kycFragment);
    }

    public void openKYCViewFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCViewFragment kycViewFragment = new KYCViewFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycViewFragment.setArguments(bundle);
        openFragment(kycViewFragment);
    }

    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this.getContext())).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, QuizHomeFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();


    }

    @Override
    public void onClick(View v) {
        if (quizViewModel.homeUseCase.checkKycStatus(dashboardResModel)) {
            openKYCViewFragment(dashboardResModel);
        } else {
            openKYCFragment(dashboardResModel);
        }
    }

    private void askPermission() {
        String rationale = "For taking the quiz camera permission is must.";
        Permissions.Options options = new Permissions.Options()
                .setRationaleDialogTitle("Info")
                .setSettingsDialogTitle("Warning");
        Permissions.check(getActivity(), PermissionUtil.mCameraPermissions, rationale, options, new PermissionHandler() {
            @Override
            public void onGranted() {
                openQuizTestFragment(dashboardResModel);
                //  openFragment(new ScholarShipFragment());
            }

            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                // permission denied, block the feature.
                ViewUtil.showSnackBar(binding.getRoot(), rationale);
            }
        });
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

        if (commonDataModel.getClickType() == Status.START_QUIZ_BUTON) {
            quizResModel = (QuizResModel) commonDataModel.getObject();
            askPermission();
        }

    }

}
