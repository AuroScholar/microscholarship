package com.auro.scholr.home.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class HomeRemoteDataSourceImp implements HomeRepo.DashboardRemoteData {

    HomeRemoteApi homeRemoteApi;

    public HomeRemoteDataSourceImp(HomeRemoteApi homeRemoteApi) {
        this.homeRemoteApi = homeRemoteApi;
    }


    @Override
    public Single<Response<JsonObject>> getDashboardData(String mobileno) {
        Map<String,String> params = new HashMap<String, String>();
        params.put(AppConstant.PHONE_NUMBER, mobileno);
        return homeRemoteApi.getDashboardData(params);
    }

    @Override
    public Single<Response<JsonObject>> uploadProfileImage(byte[] imageBytes) {
        Map<String,String> params = new HashMap<String, String>();
        params.put(AppConstant.PHONE_NUMBER, "7503600686");
        params.put("id_proof","image/jpeg");

        RequestBody phonenumber = RequestBody.create(okhttp3.MultipartBody.FORM, "7503600686");
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("id_proof", "image.jpg", requestFile);


        return homeRemoteApi.uploadImage(phonenumber,body);
    }
}
