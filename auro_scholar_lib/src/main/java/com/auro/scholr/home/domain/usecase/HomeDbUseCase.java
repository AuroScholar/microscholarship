package com.auro.scholr.home.domain.usecase;

import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.teacher.data.model.common.DistrictDataModel;
import com.auro.scholr.teacher.data.model.common.StateDataModel;

import java.util.List;

import io.reactivex.Single;

public class HomeDbUseCase {
    HomeRepo.DashboardDbData dashboardDbData;

    public HomeDbUseCase(HomeRepo.DashboardDbData dashboardDbData) {
        this.dashboardDbData = dashboardDbData;
    }

    public Single<Long[]> insertStateList(List<StateDataModel> list) {
        return dashboardDbData.insertStateList(list);
    }

    public Single<List<StateDataModel>> getStateList() {
        return dashboardDbData.getStateList();
    }

    public Single<Long[]> insertDistrictList(List<DistrictDataModel> list) {
        return dashboardDbData.insertDistrictList(list);
    }

    public Single<List<DistrictDataModel>> getDistrictList() {
        return dashboardDbData.getDistrictList();
    }

    public Single<List<DistrictDataModel>> getDistrictList(String stateCode) {
        return dashboardDbData.getStateDistrictList(stateCode);
    }

}
