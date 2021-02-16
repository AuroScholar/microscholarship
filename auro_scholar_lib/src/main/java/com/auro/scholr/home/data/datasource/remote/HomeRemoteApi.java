package com.auro.scholr.home.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.network.URLConstant;
import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HomeRemoteApi {


    @POST(URLConstant.DASHBOARD_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getDashboardData(@FieldMap Map<String, String> params);

    @POST(URLConstant.DASHBOARD_SDK_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getDashboardSDKData(@FieldMap Map<String, String> params);



    @POST(URLConstant.DEMOGRAPHIC_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> postDemographicData(@FieldMap Map<String, String> params);

    @Multipart
    @POST(URLConstant.UPLOAD_IMAGE_URL)
    Single<Response<JsonObject>> uploadImage(@Part(AppConstant.MOBILE_NUMBER) RequestBody description,
                                             @Part(AppConstant.DocumentType.AADHAR_NAME) RequestBody aadhar_name,
                                             @Part(AppConstant.DocumentType.AADHAR_DOB) RequestBody aadhar_dob,
                                             @Part(AppConstant.DocumentType.AADHAR_PHONE) RequestBody aadhar_phone,
                                             @Part(AppConstant.DocumentType.AADHAR_NO) RequestBody aadhar_no,
                                             @Part(AppConstant.DocumentType.SCHOOL_DOB) RequestBody school_dob,
                                             @Part(AppConstant.DocumentType.SCHOOL_PHONE) RequestBody schhol_phone,
                                             @Part MultipartBody.Part id_front,
                                             @Part MultipartBody.Part id_back,
                                             @Part MultipartBody.Part student_id,
                                             @Part MultipartBody.Part student_photo);

    @POST(URLConstant.GET_ASSIGNMENT_ID)
    @FormUrlEncoded
    Single<Response<JsonObject>> getAssignmentId(@FieldMap  Map<String, String> params);

    @Multipart
    @POST(URLConstant.AZURE_API)
    Single<Response<JsonObject>> getAzureApiData(
            @Part(AppConstant.AzureApiParams.REGISTRATION_ID) RequestBody registration_id,
            @Part(AppConstant.AzureApiParams.EKLAVVYA_EXAM_ID) RequestBody exam_id,
            @Part(AppConstant.AzureApiParams.EXAM_NAME) RequestBody exam_name,
            @Part(AppConstant.AzureApiParams.QUIZ_ATTEMPT) RequestBody quiz_attempt,
            @Part(AppConstant.AzureApiParams.SUBJECT) RequestBody subject,
            @Part MultipartBody.Part exam_face_img);



    @POST(URLConstant.INVITE_FRIEND_LIST_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> inviteFriendListApi(@FieldMap Map<String, String> params);

    @POST(URLConstant.SEND_NOTIFICATION_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> sendInviteNotificationApi(@FieldMap Map<String, String> params);

    @POST(URLConstant.ACCEPT_STUDENT_INVITE)
    @FormUrlEncoded
    Single<Response<JsonObject>> acceptInviteApi(@FieldMap Map<String, String> params);

    @POST(URLConstant.FIND_FRIEND_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> findFriendApi(@FieldMap Map<String, Double> params);

    @POST(URLConstant.SEND_FRIEND_REQUEST_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> sendFriendRequestApi(@FieldMap Map<String, Object> params);

    @POST(URLConstant.FRIEND_REQUEST_LIST_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> friendRequestListApi(@FieldMap Map<String, Integer> params);

    @POST(URLConstant.FRIEND_ACCEPT_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> friendAcceptApi(@FieldMap Map<String, String> params);


    @POST(URLConstant.GRADE_UPGRADE)
    @FormUrlEncoded
    Single<Response<JsonObject>> gradeUpgrade(@FieldMap Map<String, String> params);

    @POST(URLConstant.REFFERAL_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getRefferalapi(@FieldMap Map<String,String> params);

    @Multipart
    @POST(URLConstant.EXAM_IMAGE_API)
    Single<Response<JsonObject>> uploadImage(@Part(AppConstant.AssignmentApiParams.EKLAVYA_EXAM_ID) RequestBody description,
                                             @Part MultipartBody.Part exam_face_image);

}
