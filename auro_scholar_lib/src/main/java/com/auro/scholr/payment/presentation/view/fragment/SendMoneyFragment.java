package com.auro.scholr.payment.presentation.view.fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.application.base_component.BaseFragment;
import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.CommonCallBackListner;
import com.auro.scholr.core.common.CommonDataModel;
import com.auro.scholr.core.database.AppPref;
import com.auro.scholr.core.database.PrefModel;
import com.auro.scholr.databinding.SendMoneyFragmentLayoutBinding;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.fragment.KYCViewFragment;
import com.auro.scholr.home.presentation.viewmodel.DemographicViewModel;
import com.auro.scholr.payment.presentation.view.adapter.ViewPagerAdapter;
import com.auro.scholr.payment.presentation.viewmodel.SendMoneyViewModel;
import com.auro.scholr.util.ViewUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class SendMoneyFragment extends BaseFragment implements CommonCallBackListner, View.OnClickListener {

    @Inject
    @Named("SendMoneyFragment")
    ViewModelFactory viewModelFactory;
    SendMoneyFragmentLayoutBinding binding;
    SendMoneyViewModel viewModel;
    ViewPagerAdapter viewPagerAdapter;
    DashboardResModel dashboardResModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayout(), container, false);
        AuroApp.getAppComponent().doInjection(this);
        AuroApp.getAppContext().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SendMoneyViewModel.class);
        binding.setLifecycleOwner(this);
        ViewUtil.setLanguageonUi(getActivity());
        return binding.getRoot();
    }


    @Override
    protected void init() {

        binding.toolbarLayout.backArrow.setVisibility(View.VISIBLE);
        if (getArguments() != null) {
            dashboardResModel = getArguments().getParcelable(AppConstant.DASHBOARD_RES_MODEL);
        }
        initialiseTabs();
        PrefModel prefModel = AppPref.INSTANCE.getModelInstance();
        if (prefModel.getUserLanguage().equalsIgnoreCase(AppConstant.LANGUAGE_EN)) {
            setLanguageText(AppConstant.HINDI);
        } else {
            setLanguageText(AppConstant.ENGLISH);
        }


    }


    public void initialiseTabs() {
        for (int i = 0; i < getTabList().size(); i++) {
            binding.dineHomeTabs.addTab(binding.dineHomeTabs.newTab().setCustomView(getViewForEachTab(i)));
        }
        setViewPagerAdapter();
        binding.dineHomeTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSelectedTabText(tab.getPosition());
                binding.dineHomeTabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue_color));
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // do code here

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // do code here
                if (binding.dineHomeTabs.getSelectedTabPosition() == 0) {
                    setSelectedTabText(0);
                    binding.dineHomeTabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue_color));

                }

            }
        });
        setSelectedTabText(0);
        binding.dineHomeTabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.blue_color));
    }


    public void setViewPagerAdapter() {
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), getChildFragmentManager(), binding.dineHomeTabs.getTabCount(), dashboardResModel);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.dineHomeTabs));
    }

    private View getViewForEachTab(int tabNo) {
        View view = getLayoutInflater().inflate(R.layout.tab_text_layout, null);
        TextView tabTitle = view.findViewById(R.id.text_one);
        //tabTitle.setTextSize(8);
        tabTitle.setText(getTabList().get(tabNo));
        return view;
    }

    private void setSelectedTabText(int pos) {
        for (int i = 0; i < binding.dineHomeTabs.getTabCount(); i++) {
            View view = binding.dineHomeTabs.getTabAt(i).getCustomView();
            TextView text = view.findViewById(R.id.text_one);
            if (i == pos) {
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Regular.ttf");
                text.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.blue_color));
                text.setTypeface(face);
            } else {
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Poppins-Regular.ttf");
                text.setTextColor(AuroApp.getAppContext().getResources().getColor(R.color.white));
                text.setTypeface(face);
            }


        }
    }


    public List<String> getTabList() {
        List<String> tabList = new ArrayList<>();
        tabList.add("Paytm");
        tabList.add("UPI");
        tabList.add("Bank");
        return tabList;
    }

    private void setLanguageText(String text) {
        binding.toolbarLayout.langEng.setText(text);
    }


    @Override
    protected void setToolbar() {

    }

    @Override
    protected void setListener() {
        binding.toolbarLayout.langEng.setOnClickListener(this);
        binding.toolbarLayout.backArrow.setOnClickListener(this);

    }


    @Override
    protected int getLayout() {
        return R.layout.send_money_fragment_layout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {

        }
        init();
        setToolbar();
        setListener();
    }


    @Override
    public void onResume() {
        super.onResume();
        setKeyListner();
    }


    @Override
    public void commonEventListner(CommonDataModel commonDataModel) {

    }


    private void showSnackbarError(String message) {
        ViewUtil.showSnackBar(binding.getRoot(), message);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_arrow) {
            getActivity().getSupportFragmentManager().popBackStack();
            //openKycMoneyFragment();
        }

    }

    private void reloadFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    public void openKycMoneyFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
        Bundle bundle = new Bundle();
        KYCViewFragment sendMoneyFragment = new KYCViewFragment();
        bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, dashboardResModel);
        sendMoneyFragment.setArguments(bundle);
        openFragment(sendMoneyFragment);
    }

    private void openFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(AuroApp.getFragmentContainerUiId(), fragment, KYCViewFragment.class
                        .getSimpleName())
                .addToBackStack(null)
                .commitAllowingStateLoss();

    }

    private void setKeyListner() {
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();
        this.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    onBackPressed();
                    return true;
                }
                return false;
            }
        });
    }


    public void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void backButton() {
        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
