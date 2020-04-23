package com.auro.scholr.home.data.repository;

import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface HomeRepo {

    interface DashboardRemoteData {
        Single<Response<JsonObject>> getDashboardData(String mobileno);

        Single<Response<JsonObject>> uploadProfileImage(List<KYCDocumentDatamodel> list,String phonenumber);

        Single<Response<JsonObject>> postDemographicData(DemographicResModel demographicResModel);

        Single<Response<JsonObject>> getAssignmentId(AssignmentReqModel assignmentReqModel);


    }


    interface DashboardDbData {
        Single<Integer> getStoreDataCount();

    }

}
