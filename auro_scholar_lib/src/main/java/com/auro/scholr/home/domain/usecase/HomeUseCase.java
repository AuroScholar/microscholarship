package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.QuizResModel;
import com.auro.scholr.util.AppLogger;

import java.util.ArrayList;

public class HomeUseCase {


    public ArrayList<QuizResModel> makeDummyQuizList() {
        ArrayList<QuizResModel> productModelList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productModelList.add(new QuizResModel());
        }

        return productModelList;
    }


    public ArrayList<KYCDocumentDatamodel> makeDummyDocumentList() {
        ArrayList<KYCDocumentDatamodel> kycDocumentList = new ArrayList<>();
        KYCDocumentDatamodel kyc_one = new KYCDocumentDatamodel();
        kyc_one.setDocumentId(AppConstant.documentType.ID_PROOF_FRONT_SIDE);
        kyc_one.setDocumentName(AuroApp.getAppContext().getString(R.string.id_proof_front_side));
        kyc_one.setDocumentFileName("No File chosen");
        kyc_one.setButtonText("choose File");

        KYCDocumentDatamodel kyc_two = new KYCDocumentDatamodel();
        kyc_two.setDocumentId(AppConstant.documentType.ID_PROOF_BACK_SIDE);
        kyc_two.setDocumentName("ID Proof Back Side");
        kyc_two.setDocumentFileName("No File chosen");
        kyc_two.setButtonText("choose File");

        KYCDocumentDatamodel kyc_three = new KYCDocumentDatamodel();
        kyc_three.setDocumentId(AppConstant.documentType.SCHOOL_ID_CARD);
        kyc_three.setDocumentName("School ID Card");
        kyc_three.setDocumentFileName("No File chosen");
        kyc_three.setButtonText("choose File");

        KYCDocumentDatamodel kyc_four = new KYCDocumentDatamodel();
        kyc_four.setDocumentId(AppConstant.documentType.UPLOAD_YOUR_PHOTO);
        kyc_four.setDocumentName("Upload Your Photo");
        kyc_four.setDocumentFileName("No File chosen");
        kyc_four.setButtonText("choose File");

        kycDocumentList.add(kyc_one);
        kycDocumentList.add(kyc_two);
        kycDocumentList.add(kyc_three);
        kycDocumentList.add(kyc_four);

        return kycDocumentList;

    }


}
