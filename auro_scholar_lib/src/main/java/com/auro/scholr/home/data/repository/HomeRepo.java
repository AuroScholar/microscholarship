package com.auro.scholr.home.data.repository;

import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.CertificateResModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.DynamiclinkResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.model.KYCInputModel;
import com.auro.scholr.home.data.model.SaveImageReqModel;
import com.auro.scholr.home.data.model.SendOtpReqModel;
import com.auro.scholr.home.data.model.StudentProfileModel;
import com.auro.scholr.home.data.model.VerifyOtpReqModel;
import com.auro.scholr.home.data.model.passportmodels.PassportReqModel;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;
import com.auro.scholr.teacher.data.model.request.SendInviteNotificationReqModel;
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

        Single<Response<JsonObject>> inviteFriendListApi();

        Single<Response<JsonObject>> sendInviteNotificationApi(SendInviteNotificationReqModel reqModel);

        Single<Response<JsonObject>> acceptInviteApi(SendInviteNotificationReqModel reqModel);

        Single<Response<JsonObject>> upgradeClass(AuroScholarDataModel model);

        Single<Response<JsonObject>> findFriendApi(double lat, double longt, double radius);

        Single<Response<JsonObject>> sendFriendRequestApi(int requested_by_id, int requested_user_id, String requested_by_phone, String requested_user_phone);

        Single<Response<JsonObject>> friendRequestListApi(int requested_by_id);

        Single<Response<JsonObject>> friendAcceptApi(int friend_request_id, String request_status);

        Single<Response<JsonObject>> getDynamicDataApi(DynamiclinkResModel model);

        Single<Response<JsonObject>> uploadStudentExamImage(SaveImageReqModel reqModel);

        Single<Response<JsonObject>> getCertificateApi(CertificateResModel model);

        Single<Response<JsonObject>> passportApi(PassportReqModel reqModel);

        Single<Response<JsonObject>> studentUpdateProfile(StudentProfileModel model);

        Single<Response<JsonObject>> sendOtpHomeRepo(SendOtpReqModel reqModel);

        Single<Response<JsonObject>> verifyOtpHomeRepo(VerifyOtpReqModel reqModel);
    }


    interface DashboardDbData {
        Single<Long[]> insertStateList(List<StateDataModel> stateDataModelList);

        Single<Long[]> insertDistrictList(List<DistrictDataModel> stateDataModelList);

        Single<List<StateDataModel>> getStateList();

        Single<List<DistrictDataModel>> getDistrictList();


        Single<List<DistrictDataModel>> getStateDistrictList(String stateCode);

    }

}
