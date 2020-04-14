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
import com.auro.scholr.core.util.uiwidget.others.HideBottomNavigation;
import com.auro.scholr.databinding.QuizHomeLayoutBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.adapter.QuizItemAdapter;
import com.auro.scholr.home.presentation.viewmodel.QuizViewModel;
import com.auro.scholr.util.AuroScholar;
import com.auro.scholr.util.ViewUtil;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import static com.auro.scholr.core.common.Status.DASHBOARD_API;
import static com.auro.scholr.core.common.Status.LOADING;


public class QuizHomeFragment extends BaseFragment implements View.OnClickListener {

    @Inject
    @Named("QuizHomeFragment")
    ViewModelFactory viewModelFactory;


    QuizHomeLayoutBinding binding;
    QuizViewModel quizViewModel;
    String mobileNumber;

    private Activity mActivity;
    private HideBottomNavigation hideBottomNavigation;
    QuizItemAdapter quizItemAdapter;
    private static final String TAG = "CardFragment";
    DashboardResModel dashboardResModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        quizViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setQuizViewModel(quizViewModel);
        HomeActivity.setListingActiveFragment(HomeActivity.CARD_FRAGMENT);

        return binding.getRoot();
    }

    private void setAdapter(List<QuizResModel> resModelList) {
        binding.quizTypeList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.quizTypeList.setHasFixedSize(true);
        quizItemAdapter = new QuizItemAdapter(this.getContext(), resModelList);
        binding.quizTypeList.setAdapter(quizItemAdapter);

    }


    @Override
    protected void init() {
       // hideBottomNavigation = (HideBottomNavigation) mActivity;

        if (getArguments() != null) {
            mobileNumber = getArguments().getString(AppConstant.MOBILE_NUMBER);
        }
        if (quizViewModel != null && quizViewModel.serviceLiveData().hasObservers()) {
            quizViewModel.serviceLiveData().removeObservers(this);

        } else {
            observeServiceResponse();
        }
        quizViewModel.getDashBoardData(mobileNumber);
    }


    @Override
    protected void setToolbar() {

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
    }


    @Override
    public void onResume() {
        super.onResume();
      //  hideBottomNavigation.onOpen();
    }


    private void observeServiceResponse() {

        quizViewModel.serviceLiveData().observeForever(responseApi -> {

            switch (responseApi.status) {

                case LOADING:
                    //For ProgressBar
                    handleProgress(0);
                    break;

                case SUCCESS:
                    if (responseApi.apiTypeStatus == DASHBOARD_API) {
                        handleProgress(1);
                        dashboardResModel = (DashboardResModel) responseApi.data;
                        setDataOnUi(dashboardResModel);
                    }

                    break;

                case NO_INTERNET:
//On fail
                    handleProgress(2);
                    break;

                case AUTH_FAIL:

// When Authrization is fail
                    handleProgress(1);
                    break;

                case FAIL_400:
                    //When 400 error occur
                    handleProgress(1);
                    break;


                default:
                    Log.d(TAG, "observeServiceResponse: default");
                    ViewUtil.showSnackBar(binding.getRoot(), responseApi.data.toString());
                    handleProgress(1);
                    break;
            }

        });
    }

    private void handleProgress(int value) {
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
            binding.errorLayout.errorIcon.setImageDrawable(AuroApp.getAppContext().getResources().getDrawable(R.drawable.nointernet_ico));
            binding.errorLayout.textError.setText(getString(R.string.internet_check));
        }

    }


    private void setDataOnUi(DashboardResModel dashboardResModel) {
        quizViewModel.walletBalance.setValue(getString(R.string.rs) + " " + dashboardResModel.getWalletbalance());
        setAdapter(dashboardResModel.getQuiz());
    }

    public void openKYCFragment(DashboardResModel dashboardResModel) {
        Bundle bundle = new Bundle();
        KYCFragment kycFragment = new KYCFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        kycFragment.setArguments(bundle);
        openFragment(kycFragment);
    }

    private void openFragment(Fragment fragment) {
        ((AppCompatActivity) (this.getContext())).getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.home_container, fragment, QuizHomeFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClick(View v) {
        openKYCFragment(dashboardResModel);
    }
}
