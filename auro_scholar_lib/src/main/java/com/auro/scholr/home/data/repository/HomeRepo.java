package com.auro.scholr.home.data.repository;

import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface HomeRepo {

    interface DashboardRemoteData {
        Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model);

        Single<Response<JsonObject>> uploadProfileImage(List<KYCDocumentDatamodel> list, KYCInputModel kycInputModel);

        Single<Response<JsonObject>> postDemographicData(DemographicResModel demographicResModel);

        Single<Response<JsonObject>> getAssignmentId(AssignmentReqModel assignmentReqModel);

        Single<Response<JsonObject>> getAzureData(AssignmentReqModel azureReqModel);


    }


    interface DashboardDbData {
        Single<Integer> getStoreDataCount();

    }

}
