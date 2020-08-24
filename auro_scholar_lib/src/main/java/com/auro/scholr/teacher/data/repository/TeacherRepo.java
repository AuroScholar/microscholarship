package com.auro.scholr.teacher.data.repository;

import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
import com.auro.scholr.teacher.data.model.request.TeacherReqModel;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

public interface TeacherRepo {

    interface TeacherRemoteData {

        Single<Response<JsonObject>> updateTeacherProfileApi(TeacherReqModel model);

        Single<Response<JsonObject>> getTeacherDashboardApi(String mobileNumber);

        Single<Response<JsonObject>> getTeacherProgressApi(String mobileNumber);

        Single<Response<JsonObject>> getProfileTeacherApi(String mobileNumber);

        Single<Response<JsonObject>> uploadTeacherKYC(List<KYCDocumentDatamodel> list);

        Single<Response<JsonObject>> sendInviteNotificationApi(SendInviteNotificationReqModel reqModel);

        Single<Response<JsonObject>> getZohoAppointments();

        Single<Response<JsonObject>> bookZohoAppointments(String from_time, String name, String email, String phone_number);
    }


    interface TeacherDbData {

        Single<Long[]> insertStateList(List<StateDataModel> stateDataModelList);

        Single<Long[]> insertDistrictList(List<DistrictDataModel> stateDataModelList);

        Single<List<StateDataModel>> getStateList();

        Single<List<DistrictDataModel>> getDistrictList();


        Single<List<DistrictDataModel>> getStateDistrictList(String stateCode);

    }

}
