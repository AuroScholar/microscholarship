package com.auro.scholr.home.data.datasource.database;

import com.auro.scholr.home.data.repository.HomeRepo;

import io.reactivex.Single;

public class HomeDbDataSourceImp implements HomeRepo.DashboardDbData {
    @Override
    public Single<Integer> getStoreDataCount() {
        return null;
    }
}
