package com.auro.scholr.util;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.SdkCallBack;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.AuroScholarInputModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;

public class AuroScholar {

    public static Fragment openAuroDashboardFragment(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.setAuroModel(auroScholarDataModel);
        QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
        return quizHomeFragment;
    }

    /*For generic with PhoneNumber and class*/
    public static Fragment startAuroSDK(AuroScholarInputModel inputModel) {
        AuroScholarDataModel auroScholarDataModel = new AuroScholarDataModel();
        auroScholarDataModel.setMobileNumber(inputModel.getMobileNumber());
        auroScholarDataModel.setStudentClass(inputModel.getStudentClass());
        auroScholarDataModel.setRegitrationSource(inputModel.getRegitrationSource());
        auroScholarDataModel.setActivity(inputModel.getActivity());
        auroScholarDataModel.setFragmentContainerUiId(inputModel.getFragmentContainerUiId());
        AuroApp.setAuroModel(auroScholarDataModel);
        QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
        return quizHomeFragment;
    }


    public static void startTeacherSDK(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.setAuroModel(auroScholarDataModel);
        auroScholarDataModel.getActivity().startActivity(new Intent(auroScholarDataModel.getActivity(), HomeActivity.class));
    }

    public static void setReferralLink(String referralLink) {
        if (AuroApp.getAuroScholarModel() != null) {
            AuroApp.getAuroScholarModel().setReferralLink(referralLink);
        }
    }


}
