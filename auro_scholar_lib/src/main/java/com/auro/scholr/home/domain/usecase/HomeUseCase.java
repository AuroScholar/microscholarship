package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.util.TextUtil;

import org.w3c.dom.Text;

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
}
