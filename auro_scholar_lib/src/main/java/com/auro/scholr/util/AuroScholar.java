package com.auro.scholr.util;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.AuroScholarInputModel;
import com.auro.scholr.home.presentation.view.activity.HomeActivity;
import com.auro.scholr.home.presentation.view.activity.newDashboard.StudentMainDashboardActivity;
import com.auro.scholr.home.presentation.view.fragment.FriendsLeaderBoardFragment;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;

public class AuroScholar {

    public static Fragment openAuroDashboardFragment(AuroScholarDataModel auroScholarDataModel) {
        if (auroScholarDataModel == null || auroScholarDataModel.getActivity() == null) {
            AppLogger.e("Auro scholar sdk not initialise", "error");
            return null;
        }
        if (auroScholarDataModel != null) {
            String input = auroScholarDataModel.getMobileNumber() + "\n" + auroScholarDataModel.getScholrId() + "\n" + auroScholarDataModel.isEmailVerified() + "\n" +
                    auroScholarDataModel.getRegitrationSource() + "\n" + auroScholarDataModel.getReferralLink();
            AppLogger.e("Auro scholar input data", input);
        }
        if (TextUtil.isEmpty(auroScholarDataModel.getPartnerSource())) {
            auroScholarDataModel.setPartnerSource("");
        }
        if (TextUtil.isEmpty(auroScholarDataModel.getShareIdentity())) {
            auroScholarDataModel.setShareIdentity("");
        }
        if (TextUtil.isEmpty(auroScholarDataModel.getShareType())) {
            auroScholarDataModel.setShareType("");
        }

        if (TextUtil.isEmpty(auroScholarDataModel.getRegitrationSource())) {
            auroScholarDataModel.setRegitrationSource("");
        }
        AuroApp.setAuroModel(auroScholarDataModel);

        if (AuroApp.getAuroScholarModel() != null) {
            switch (AuroApp.getAuroScholarModel().getSdkFragmentType()) {
                case AppConstant.FragmentType.FRIENDS_LEADER_BOARD:
                    if (auroScholarDataModel == null || auroScholarDataModel.getActivity() == null) {
                        AppLogger.e("Auro scholar sdk not initialise 2", "error");
                        return null;
                    }
                    return new FriendsLeaderBoardFragment();
                default:
                    return new QuizHomeFragment();
            }
        } else {
            return new QuizHomeFragment();
        }
    }

    /*For generic with PhoneNumber and class*/
    public static void startAuroSDK(AuroScholarInputModel inputModel) {
        AuroScholarDataModel auroScholarDataModel = new AuroScholarDataModel();
        auroScholarDataModel.setMobileNumber(inputModel.getMobileNumber());
        auroScholarDataModel.setStudentClass(inputModel.getStudentClass());
        auroScholarDataModel.setRegitrationSource(inputModel.getRegitrationSource());
        auroScholarDataModel.setActivity(inputModel.getActivity());
        auroScholarDataModel.setFragmentContainerUiId(R.id.home_container);
        auroScholarDataModel.setReferralLink(inputModel.getReferralLink());
        auroScholarDataModel.setLanguage(inputModel.getLanguage());
        auroScholarDataModel.setUserPartnerid(inputModel.getUserPartnerId());
        auroScholarDataModel.setApplicationLang(inputModel.isApplicationLang());
        auroScholarDataModel.setPartnerLogo(inputModel.getPartnerLogoUrl());
        auroScholarDataModel.setGender(inputModel.getGender());
        auroScholarDataModel.setEmail(inputModel.getEmail());
        auroScholarDataModel.setPartnerName(inputModel.getPartnerName());
        if (!TextUtil.isEmpty(inputModel.getSchoolName())) {
            auroScholarDataModel.setSchoolName(inputModel.getSchoolName());
        } else {
            auroScholarDataModel.setSchoolName("");
        }

        if (!TextUtil.isEmpty(inputModel.getBoardType())) {
            auroScholarDataModel.setBoardType(inputModel.getBoardType());
        } else {
            auroScholarDataModel.setBoardType("");
        }


        if (!TextUtil.isEmpty(inputModel.getSchoolType())) {
            auroScholarDataModel.setSchoolType(inputModel.getSchoolType());
        } else {
            auroScholarDataModel.setSchoolType("");
        }


        if (!TextUtil.isEmpty(inputModel.getGender())) {
            auroScholarDataModel.setGender(inputModel.getGender());
        } else {
            auroScholarDataModel.setGender("");
        }

        if (!TextUtil.isEmpty(inputModel.getEmail())) {
            auroScholarDataModel.setEmail(inputModel.getEmail());
        } else {
            auroScholarDataModel.setEmail("");
        }


        if (TextUtil.isEmpty(inputModel.getPartnerSource())) {
            auroScholarDataModel.setPartnerSource("");
        } else {
            auroScholarDataModel.setPartnerSource(inputModel.getPartnerSource());
        }
        AuroApp.setAuroModel(auroScholarDataModel);
        if (auroScholarDataModel != null && auroScholarDataModel.getActivity() == null) {
            return;
        }
        // QuizHomeFragment quizHomeFragment = new QuizHomeFragment();
        auroScholarDataModel.getActivity().startActivity(new Intent(auroScholarDataModel.getActivity(), StudentMainDashboardActivity.class));

        //  return quizHomeFragment;
    }


    public static void startTeacherSDK(AuroScholarDataModel auroScholarDataModel) {
        if (auroScholarDataModel != null) {
            String input = auroScholarDataModel.getMobileNumber() + "\n" + auroScholarDataModel.getScholrId() + "\n" + auroScholarDataModel.isEmailVerified() + "\n" +
                    auroScholarDataModel.getRegitrationSource() + "\n" + auroScholarDataModel.getReferralLink();
            AppLogger.e("Auro scholar input data", input);
        }

        if (TextUtil.isEmpty(auroScholarDataModel.getPartnerSource())) {
            auroScholarDataModel.setPartnerSource("");
        }
        if (TextUtil.isEmpty(auroScholarDataModel.getShareIdentity())) {
            auroScholarDataModel.setShareIdentity("");
        }
        if (TextUtil.isEmpty(auroScholarDataModel.getShareType())) {
            auroScholarDataModel.setShareType("");
        }

        if (TextUtil.isEmpty(auroScholarDataModel.getRegitrationSource())) {
            auroScholarDataModel.setRegitrationSource("");
        }

        AuroApp.setAuroModel(auroScholarDataModel);
        auroScholarDataModel.getActivity().startActivity(new Intent(auroScholarDataModel.getActivity(), HomeActivity.class));
    }

    public static void setReferralLink(String referralLink) {
        if (AuroApp.getAuroScholarModel() != null) {
            AuroApp.getAuroScholarModel().setReferralLink(referralLink);
        }
    }


}
