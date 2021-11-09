package com.auro.scholr.quiz.data.datasource.remote;

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

public interface QuizRemoteApi {


    @POST(URLConstant.FETCH_QUIZ_DATA)
    @FormUrlEncoded
    Single<Response<JsonObject>> fetchQuizData(@FieldMap Map<String, String> params);

    @POST(URLConstant.SAVE_QUIZ_DATA)
    @FormUrlEncoded
    Single<Response<JsonObject>> saveQuizData(@FieldMap Map<String, String> params);

    @POST(URLConstant.FINISH_QUIZ_DATA)
    @FormUrlEncoded
    Single<Response<JsonObject>> finishQuizApi(@FieldMap Map<String, String> params);

/*
    @Multipart
    @POST(URLConstant.EXAM_IMAGE_API)
    Single<Response<JsonObject>> uploadImage(@Part(AppConstant.AssignmentApiParams.EKLAVYA_EXAM_ID) RequestBody description,
                                             @Part MultipartBody.Part exam_face_image);
*/


    @Multipart
    @POST(URLConstant.EXAM_IMAGE_API)
    Single<Response<JsonObject>> uploadImage(@Part(AppConstant.AssignmentApiParams.EKLAVYA_EXAM_ID) RequestBody description,
                                             @Part(AppConstant.AssignmentApiParams.REGISTRATION_ID) RequestBody registration_id,
                                             @Part(AppConstant.AssignmentApiParams.IS_MOBILE) RequestBody isMobile,
                                             @Part(AppConstant.AssignmentApiParams.QUIZ_ID) RequestBody quiz_id,
                                             @Part(AppConstant.AssignmentApiParams.IMG_NORMAL_PATH) RequestBody img_normal_path,
                                             @Part(AppConstant.AssignmentApiParams.IMG_PATH) RequestBody img_path,
                                             @Part MultipartBody.Part exam_face_image);

    @POST(URLConstant.DASHBOARD_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getDashboardData(@FieldMap Map<String, String> params);

    @POST(URLConstant.DASHBOARD_SDK_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getDashboardSDKData(@FieldMap Map<String, String> params);

}
