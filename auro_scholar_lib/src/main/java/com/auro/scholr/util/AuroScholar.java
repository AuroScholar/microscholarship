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

    public static void openAuroDashboardFragment(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.intialiseSdk(auroScholarDataModel.getActivity());
        AuroScholar.fragmentContainerUiId = auroScholarDataModel.getFragmentContainerUiId();
        QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.MOBILE_NUMBER, auroScholarDataModel.getMobileNumber());
        quizHomeFragment.setArguments(bundle);
        FragmentUtil.replaceFragment(auroScholarDataModel.getActivity(), quizHomeFragment, fragmentContainerUiId, false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }

    public static void openAuroDashboarWebFragment(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.intialiseSdk(auroScholarDataModel.getActivity());
        AuroScholar.fragmentContainerUiId = auroScholarDataModel.getFragmentContainerUiId();
        ScholarShipFragment scholarShipFragment = new ScholarShipFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.AURO_DATA_MODEL, auroScholarDataModel);
        scholarShipFragment.setArguments(bundle);
        FragmentUtil.replaceFragment(auroScholarDataModel.getActivity(), scholarShipFragment,auroScholarDataModel.getFragmentContainerUiId(), false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }


}
