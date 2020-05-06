package com.auro.scholr.payment.presentation.view.adapter;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.auro.scholr.payment.presentation.view.fragment.BankFragment;
import com.auro.scholr.payment.presentation.view.fragment.PaytmFragment;
import com.auro.scholr.payment.presentation.view.fragment.SendMoneyFragment;
import com.auro.scholr.payment.presentation.view.fragment.UPIFragment;
import com.auro.scholr.payment.presentation.viewmodel.SendMoneyViewModel;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public ViewPagerAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PaytmFragment paytmFragment = new PaytmFragment();
                return paytmFragment;
            case 1:
                UPIFragment upiFragment = new UPIFragment();
                return upiFragment;
            case 2:
                BankFragment bankFragment = new BankFragment();
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