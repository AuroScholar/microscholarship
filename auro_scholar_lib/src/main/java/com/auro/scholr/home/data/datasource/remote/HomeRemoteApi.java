package com.auro.scholr.home.data.datasource.remote;

import com.auro.scholr.core.network.URLConstant;
import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface HomeRemoteApi {


    @POST(URLConstant.DASHBOARD_API)
    @FormUrlEncoded
    Single<Response<JsonObject>> getDashboardData(@FieldMap Map<String, String> params);

    @Multipart
    @POST(URLConstant.UPLOAD_IMAGE_URL)
    Single<Response<JsonObject>> uploadImage( @Part("Phonenumber") RequestBody description,
                                              @Part MultipartBody.Part imageFile);


}
