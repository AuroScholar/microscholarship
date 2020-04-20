package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.util.TextUtil;

import java.util.ArrayList;

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
        kyc_one.setDocumentId(AppConstant.documentType.ID_PROOF_FRONT_SIDE);
        kyc_one.setDocumentName(AuroApp.getAppContext().getString(R.string.id_proof_front_side));
        kyc_one.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_one.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_one.setId_name(AppConstant.documentType.ID_PROOF);
        if (!TextUtil.isEmpty(dashboardResModel.getIdfront())) {
            //kyc_one.setDocumentstatus(true);
            kyc_one.setDocumentUrl(dashboardResModel.getIdfront());
        }


        KYCDocumentDatamodel kyc_two = new KYCDocumentDatamodel();
        kyc_two.setDocumentId(AppConstant.documentType.ID_PROOF_BACK_SIDE);
        kyc_two.setDocumentName(AuroApp.getAppContext().getString(R.string.id_proof_back_side));
        kyc_two.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_two.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_two.setId_name(AppConstant.documentType.ID_PROOF_BACK);
        if (!TextUtil.isEmpty(dashboardResModel.getIdback())) {
         //   kyc_two.setDocumentstatus(true);
            kyc_two.setDocumentUrl(dashboardResModel.getIdback());
        }

        KYCDocumentDatamodel kyc_three = new KYCDocumentDatamodel();
        kyc_three.setDocumentId(AppConstant.documentType.SCHOOL_ID_CARD);
        kyc_three.setDocumentName(AuroApp.getAppContext().getString(R.string.school_id_card));
        kyc_three.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_three.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_three.setId_name(AppConstant.documentType.SCHOOL_ID);
        if (!TextUtil.isEmpty(dashboardResModel.getSchoolid())) {
         //   kyc_three.setDocumentstatus(true);
            kyc_three.setDocumentUrl(dashboardResModel.getSchoolid());
        }

        KYCDocumentDatamodel kyc_four = new KYCDocumentDatamodel();
        kyc_four.setDocumentId(AppConstant.documentType.UPLOAD_YOUR_PHOTO);
        kyc_four.setDocumentName(AuroApp.getAppContext().getString(R.string.upload_profile_pic));
        kyc_four.setDocumentFileName(AuroApp.getAppContext().getString(R.string.no_file_chosen));
        kyc_four.setButtonText(AuroApp.getAppContext().getString(R.string.choose_file));
        kyc_four.setId_name(AppConstant.documentType.STUDENT_PHOTO);
        if (!TextUtil.isEmpty(dashboardResModel.getPhoto())) {
         //   kyc_four.setDocumentstatus(true);
            kyc_four.setDocumentUrl(dashboardResModel.getPhoto());
        }

        kycDocumentList.add(kyc_one);
        kycDocumentList.add(kyc_two);
        kycDocumentList.add(kyc_three);
        kycDocumentList.add(kyc_four);

        return kycDocumentList;

    }

    public boolean checkUploadButtonStatus(ArrayList<KYCDocumentDatamodel> list) {
        if (list.get(0).isDocumentstatus() || list.get(1).isDocumentstatus() ||
                list.get(2).isDocumentstatus() || list.get(3).isDocumentstatus()) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkUploadButtonDoc(ArrayList<KYCDocumentDatamodel> list) {
        String noFileChosen = AuroApp.getAppContext().getString(R.string.no_file_chosen);
        if (!list.get(0).getDocumentFileName().equalsIgnoreCase(noFileChosen) && !list.get(1).getDocumentFileName().equalsIgnoreCase(noFileChosen) &&
                !list.get(2).getDocumentFileName().equalsIgnoreCase(noFileChosen) && !list.get(3).getDocumentFileName().equalsIgnoreCase(noFileChosen)) {
            return true;
        } else {
            return false;
        }

    }


}
