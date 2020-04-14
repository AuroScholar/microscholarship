package com.auro.scholr.home.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
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
}
