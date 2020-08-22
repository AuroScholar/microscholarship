package com.auro.scholr.payment.presentation.view.adapter;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.payment.presentation.view.fragment.BankFragment;
import com.auro.scholr.payment.presentation.view.fragment.PaytmFragment;

import com.auro.scholr.payment.presentation.view.fragment.UPIFragment;
import com.auro.scholr.payment.presentation.viewmodel.SendMoneyViewModel;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    DashboardResModel mdashboard;

    public ViewPagerAdapter(Context c, FragmentManager fm, int totalTabs, DashboardResModel mdashboard) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.mdashboard = mdashboard;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
              //  PaytmFragment paytmFragment = new PaytmFragment();

                Bundle bundle = new Bundle();
                PaytmFragment paytmFragment = new PaytmFragment();
                bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, mdashboard);
                paytmFragment.setArguments(bundle);
                return paytmFragment;
            case 1:
               // UPIFragment upiFragment = new UPIFragment();

                Bundle bundleupi = new Bundle();
                UPIFragment upiFragment = new UPIFragment();
                bundleupi.putParcelable(AppConstant.DASHBOARD_RES_MODEL, mdashboard);
                upiFragment.setArguments(bundleupi);
                return upiFragment;
            case 2:
             //   BankFragment bankFragment = new BankFragment();

                Bundle bundlebank = new Bundle();
                BankFragment bankFragment = new BankFragment();
                bundlebank.putParcelable(AppConstant.DASHBOARD_RES_MODEL, mdashboard);
                bankFragment.setArguments(bundlebank);
                return bankFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}