package com.auro.scholr.payment.data.repository;

import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface PaymentRepo {

    interface PaymentRemoteData {
        Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model);



    }




}
