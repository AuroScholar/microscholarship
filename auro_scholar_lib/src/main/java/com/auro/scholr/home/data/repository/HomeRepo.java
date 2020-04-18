package com.auro.scholr.home.data.repository;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Response;

public interface HomeRepo {

    interface DashboardRemoteData {
        Single<Response<JsonObject>> getDashboardData(String mobileno);
        Single<Response<JsonObject>> uploadProfileImage(byte[] imageBytes);
    }


    interface DashboardDbData {
        Single<Integer> getStoreDataCount();

    }

}
