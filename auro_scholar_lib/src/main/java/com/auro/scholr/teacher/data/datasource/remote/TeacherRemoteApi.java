package com.auro.scholr.teacher.data.datasource.remote;

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

public interface TeacherRemoteApi {


    @POST(URLConstant.TEACHER_PROFILE_UPDATE_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> updateTeacherProfileApi(@FieldMap Map<String, String> params);

    @POST(URLConstant.GET_PROFILE_UPDATE_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getTeacherDashboardApi(@FieldMap Map<String, String> params);

    @POST(URLConstant.GET_PROFILE_TEACHER_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getProfileTeacherApi(@FieldMap Map<String, String> params);

}
