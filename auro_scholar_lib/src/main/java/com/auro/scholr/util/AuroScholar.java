package com.auro.scholr.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.FragmentUtil;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;

public class AuroScholar {
    public static int fragmentContainerUiId = 0;

    public static void initialiseSDK(Activity activity) {
        AuroApp.intialiseSdk(activity);
    }


    public static void openAuroScholarActivity(AuroScholarDataModel auroScholarDataModel, Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        intent.putExtra(AppConstant.AURO_DATA_MODEL, auroScholarDataModel);
        activity.startActivity(intent);

    }

//    public static void openQuizFragment(Context mContext, String mobileNumber, int fragmentContainerUiId) {
//        AuroScholar.fragmentContainerUiId = fragmentContainerUiId;
//        QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(AppConstant.MOBILE_NUMBER, mobileNumber);
//        quizHomeFragment.setArguments(bundle);
//        FragmentUtil.replaceFragment(mContext, quizHomeFragment, fragmentContainerUiId, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
//    }




}
