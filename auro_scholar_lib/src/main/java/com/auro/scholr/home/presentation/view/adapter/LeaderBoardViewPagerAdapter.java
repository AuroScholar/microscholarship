package com.auro.scholr.home.presentation.view.adapter;

import android.content.Context;
import android.os.Bundle;

import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardAddFragment;
import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardListFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LeaderBoardViewPagerAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;


    public LeaderBoardViewPagerAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        context = c;
        this.totalTabs = totalTabs;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
              //  PaytmFragment paytmFragment = new PaytmFragment();

                Bundle bundle = new Bundle();
                FriendsLeaderBoardListFragment friendsLeaderBoardListFragment = new FriendsLeaderBoardListFragment();
               // bundle.putParcelable(AppConstant.DASHBOARD_RES_MODEL, mdashboard);
                //friendsLeaderBoardListFragment.setArguments(bundle);
                return friendsLeaderBoardListFragment;
            case 1:
               // UPIFragment upiFragment = new UPIFragment();

               // Bundle bundleupi = new Bundle();
                FriendsLeaderBoardAddFragment friendsLeaderBoardAddFragment = new FriendsLeaderBoardAddFragment();
              //  bundleupi.putParcelable(AppConstant.DASHBOARD_RES_MODEL, mdashboard);
              //  upiFragment.setArguments(bundleupi);
                return friendsLeaderBoardAddFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
