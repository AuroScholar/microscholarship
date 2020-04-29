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
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFacebookIn;
import com.auro.scholr.home.presentation.view.fragment.ScholarShipFragment;

public class AuroScholar {

    public static void openAuroDashboardFragment(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.setAuroModel(auroScholarDataModel);
        QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
        FragmentUtil.replaceFragment(auroScholarDataModel.getActivity(), quizHomeFragment, AuroApp.getFragmentContainerUiId(), false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }

    public static void openAuroDashboarWebFragment(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.setAuroModel(auroScholarDataModel);
        ScholarShipFragment scholarShipFragment = new ScholarShipFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstant.AURO_DATA_MODEL, auroScholarDataModel);
        scholarShipFragment.setArguments(bundle);
        FragmentUtil.replaceFragment(auroScholarDataModel.getActivity(), scholarShipFragment,AuroApp.getFragmentContainerUiId(), false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }

    public static void openFaceBookLoginFragment(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.setAuroModel(auroScholarDataModel);
        ScholarShipFacebookIn scholarShipFacebookIn = new ScholarShipFacebookIn();
        FragmentUtil.replaceFragment(auroScholarDataModel.getActivity(), scholarShipFacebookIn, AuroApp.getFragmentContainerUiId(), false, AppConstant.NEITHER_LEFT_NOR_RIGHT);
    }

}
