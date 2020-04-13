package com.auro.scholr.home.data.repository;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Response;

public interface HomeRepo {

    interface DashboardRemoteData {
        Single<Response<JsonObject>> getStoreOnlineData(String modifiedTime);
    }


    interface DashboardDbData {
        Single<Integer> getStoreDataCount();

    }

}
