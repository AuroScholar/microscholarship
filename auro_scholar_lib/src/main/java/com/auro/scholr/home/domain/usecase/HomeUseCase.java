package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.QuizResModel;

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
        kyc_one.setDocumentName("ID Proof Font Side");
        kyc_one.setDocumentFileName("No File chosen");
        kyc_one.setButtonText("choose File");

        KYCDocumentDatamodel kyc_two = new KYCDocumentDatamodel();
        kyc_two.setDocumentName("ID Proof Back Side");
        kyc_two.setDocumentFileName("No File chosen");
        kyc_two.setButtonText("choose File");

        KYCDocumentDatamodel kyc_three = new KYCDocumentDatamodel();
        kyc_three.setDocumentName("School ID Card");
        kyc_three.setDocumentFileName("No File chosen");
        kyc_three.setButtonText("choose File");

        KYCDocumentDatamodel kyc_four = new KYCDocumentDatamodel();
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
