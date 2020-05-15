package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.FriendsLeaderBoardModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.util.TextUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeUseCase {


    public ArrayList<QuizResModel> makeDummyQuizList() {
        ArrayList<QuizResModel> productModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productModelList.add(new QuizResModel());
        }
        return productModelList;
    }


    public ArrayList<KYCDocumentDatamodel> makeAdapterDocumentList(DashboardResModel dashboardResModel) {
        ArrayList<KYCDocumentDatamodel> kycDocumentList = new ArrayList<>();
        KYCDocumentDatamodel kyc_one = new KYCDocumentDatamodel();
        kyc_one.setDocumentId(AppConstant.DocumentType.ID_PROOF_FRONT_SIDE);
        kyc_one.setDocumentName(AuroApp.getAppContext().getString(R.string.id_proof_front_side));
        kyc_one.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_one.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_one.setModify(dashboardResModel.isModify());
        kyc_one.setId_name(AppConstant.DocumentType.ID_PROOF);
        if (!TextUtil.isEmpty(dashboardResModel.getIdfront())) {
            kyc_one.setDocumentstatus(true);
            kyc_one.setDocumentUrl(dashboardResModel.getIdfront());
        }


        KYCDocumentDatamodel kyc_two = new KYCDocumentDatamodel();
        kyc_two.setDocumentId(AppConstant.DocumentType.ID_PROOF_BACK_SIDE);
        kyc_two.setDocumentName(AuroApp.getAppContext().getString(R.string.id_proof_back_side));
        kyc_two.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_two.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_two.setModify(dashboardResModel.isModify());
        kyc_two.setId_name(AppConstant.DocumentType.ID_PROOF_BACK);
        if (!TextUtil.isEmpty(dashboardResModel.getIdback())) {
            kyc_two.setDocumentstatus(true);
            kyc_two.setDocumentUrl(dashboardResModel.getIdback());
        }

        KYCDocumentDatamodel kyc_three = new KYCDocumentDatamodel();
        kyc_three.setDocumentId(AppConstant.DocumentType.SCHOOL_ID_CARD);
        kyc_three.setDocumentName(AuroApp.getAppContext().getString(R.string.school_id_card));
        kyc_three.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_three.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_three.setModify(dashboardResModel.isModify());
        kyc_three.setId_name(AppConstant.DocumentType.SCHOOL_ID);
        if (!TextUtil.isEmpty(dashboardResModel.getSchoolid())) {
            kyc_three.setDocumentstatus(true);
            kyc_three.setDocumentUrl(dashboardResModel.getSchoolid());
        }

        KYCDocumentDatamodel kyc_four = new KYCDocumentDatamodel();
        kyc_four.setDocumentId(AppConstant.DocumentType.UPLOAD_YOUR_PHOTO);
        kyc_four.setDocumentName(AuroApp.getAppContext().getString(R.string.upload_profile_pic));
        kyc_four.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_four.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_four.setModify(dashboardResModel.isModify());
        kyc_four.setId_name(AppConstant.DocumentType.STUDENT_PHOTO);
        if (!TextUtil.isEmpty(dashboardResModel.getPhoto())) {
            kyc_four.setDocumentstatus(true);
            kyc_four.setDocumentUrl(dashboardResModel.getPhoto());
        }

        kycDocumentList.add(kyc_one);
        kycDocumentList.add(kyc_two);
        kycDocumentList.add(kyc_three);
        kycDocumentList.add(kyc_four);

        return kycDocumentList;

    }

    public boolean checkUploadButtonStatus(ArrayList<KYCDocumentDatamodel> list) {
        if (list.get(0).isDocumentstatus() && list.get(1).isDocumentstatus() &&
                list.get(2).isDocumentstatus() && list.get(3).isDocumentstatus()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUploadButtonDoc(ArrayList<KYCDocumentDatamodel> list) {
        String noFileChosen = AuroApp.getAppContext().getString(R.string.no_file_chosen);
        if (list.get(0).getDocumentFileName().equalsIgnoreCase(noFileChosen) && list.get(1).getDocumentFileName().equalsIgnoreCase(noFileChosen) &&
                list.get(2).getDocumentFileName().equalsIgnoreCase(noFileChosen) && list.get(3).getDocumentFileName().equalsIgnoreCase(noFileChosen)) {
            return false;
        } else {
            return true;
        }

    }


    public AssignmentReqModel getAssignmentRequestModel(DashboardResModel dashboardResModel, QuizResModel quizResModel) {
        AssignmentReqModel assignmentReqModel = new AssignmentReqModel();
        assignmentReqModel.setExam_name(String.valueOf(quizResModel.getNumber()));
        assignmentReqModel.setQuiz_attempt(String.valueOf((quizResModel.getAttempt() + 1)));
        assignmentReqModel.setExamlang("E");
        assignmentReqModel.setRegistration_id(dashboardResModel.getAuroid());
        return assignmentReqModel;
    }


    public String validateDemographic(DemographicResModel demographicResModel) {
        if (demographicResModel.getGender().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_GENDER)) {
            return AppConstant.SpinnerType.PLEASE_SELECT_GENDER;
        }
        if (demographicResModel.getSchool_type().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_SCHOOL)) {
            return AppConstant.SpinnerType.PLEASE_SELECT_SCHOOL;
        }
        if (demographicResModel.getBoard_type().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_BOARD)) {
            return AppConstant.SpinnerType.PLEASE_SELECT_BOARD;
        }
        if (demographicResModel.getLanguage().equalsIgnoreCase(AppConstant.SpinnerType.PLEASE_SELECT_LANGUAGE_MEDIUM)) {
            return AppConstant.SpinnerType.PLEASE_SELECT_LANGUAGE_MEDIUM;
        } else {
            return "";
        }
    }

    public boolean checkKycStatus(DashboardResModel dashboardResModel) {
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getPhoto()) && !TextUtil.isEmpty(dashboardResModel.getSchoolid()) &&
                !TextUtil.isEmpty(dashboardResModel.getIdback()) && !TextUtil.isEmpty(dashboardResModel.getIdfront())) {
            return true;
        }

        return false;
    }

    public boolean checkDemographicStatus(DashboardResModel dashboardResModel) {
        if (dashboardResModel != null && !TextUtil.isEmpty(dashboardResModel.getGender()) && !TextUtil.isEmpty(dashboardResModel.getSchool_type()) &&
                !TextUtil.isEmpty(dashboardResModel.getBoard_type()) && !TextUtil.isEmpty(dashboardResModel.getLanguage())) {
            return true;
        }

        return false;
    }


    public int getQuizWonCount(List<QuizResModel> list) {
        int count = 0;
        for (QuizResModel resModel : list) {
            if (resModel.getScorepoints() > 7) {
                count = count + 1;
            }
        }
        for (int i = 0; i < count; i++) {
            list.get(i).setWonStatus(true);
        }

        return count;
    }


    public List<FriendsLeaderBoardModel> makeListForFriendsLeaderBoard(boolean status) {
        List<FriendsLeaderBoardModel> list = new ArrayList<>();

        FriendsLeaderBoardModel leaderBoardModel_01 = new FriendsLeaderBoardModel();
        leaderBoardModel_01.setScholarshipWon("1000");
        leaderBoardModel_01.setStudentName("Manish");
        leaderBoardModel_01.setStudentScore("90%");
        leaderBoardModel_01.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS3nRQYJ_9X8Z3LS-yOMwUNU7YGXXTB6SbEHcSqrgAnM7EoCqh_&usqp=CAU");
        if (status) {
            leaderBoardModel_01.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_01.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_01);


        FriendsLeaderBoardModel leaderBoardModel_02 = new FriendsLeaderBoardModel();
        leaderBoardModel_02.setScholarshipWon("900");
        leaderBoardModel_02.setStudentName("Kuldip");
        leaderBoardModel_02.setStudentScore("80%");
        leaderBoardModel_02.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTHJi1kXOjoXE_tAA1jT6kjvukiCuH3g8q1BYe2apkTQfppxUBN&usqp=CAU");

        if (status) {
            leaderBoardModel_02.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_02.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_02);

        FriendsLeaderBoardModel leaderBoardModel_03 = new FriendsLeaderBoardModel();
        leaderBoardModel_03.setScholarshipWon("600");
        leaderBoardModel_03.setStudentName("Rajat");
        leaderBoardModel_03.setStudentScore("60%");
        leaderBoardModel_03.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ-gpVj4yzkUuIi9vRM34su4nqucJrCsVx19sjDHqBXVUYvt5x2&usqp=CAU");
        if (status) {
            leaderBoardModel_03.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_03.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_03);


        FriendsLeaderBoardModel leaderBoardModel_04 = new FriendsLeaderBoardModel();
        leaderBoardModel_04.setScholarshipWon("500");
        leaderBoardModel_04.setStudentName("Aakash");
        leaderBoardModel_04.setStudentScore("50%");
        leaderBoardModel_04.setImagePath("https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQufQ4DogWSfi5pDnsXkUh0cXNX2O0Q0kFGkaM5YTl34eB-87pP&usqp=CAU");
        if (status) {
            leaderBoardModel_04.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_TYPE);
        } else {
            leaderBoardModel_04.setViewType(AppConstant.FriendsLeaderBoard.LEADERBOARD_INVITE_TYPE);
        }
        list.add(leaderBoardModel_04);

        return list;
    }

}
