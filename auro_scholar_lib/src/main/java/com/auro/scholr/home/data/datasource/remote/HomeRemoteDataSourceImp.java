package com.auro.scholr.home.data.datasource.remote;

import com.auro.scholr.home.data.repository.HomeRepo;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Response;

public class HomeRemoteDataSourceImp implements HomeRepo.DashboardRemoteData {
    public HomeRemoteDataSourceImp(HomeRemoteApi homeRemoteApi) {
    }

    @Override
    public Single<Response<JsonObject>> getStoreOnlineData(String modifiedTime) {
        return null;
    }
}
