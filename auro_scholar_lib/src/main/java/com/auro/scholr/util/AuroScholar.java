package com.auro.scholr.util;

import androidx.fragment.app.Fragment;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.common.SdkCallBack;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.AuroScholarInputModel;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;

public class AuroScholar {

    public static Fragment openAuroDashboardFragment(AuroScholarDataModel auroScholarDataModel) {
        AuroApp.setAuroModel(auroScholarDataModel);
        if (auroScholarDataModel.getSdkType() == AppConstant.SdkType.STUDENT_SDK) {
            QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
            return quizHomeFragment;
        } else {
            QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
            return quizHomeFragment;
        }

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


}
