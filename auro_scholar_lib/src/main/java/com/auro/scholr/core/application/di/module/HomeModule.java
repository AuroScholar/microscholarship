package com.auro.scholr.core.application.di.module;

import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.home.data.datasource.database.HomeDbDataSourceImp;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteApi;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteDataSourceImp;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeModule {

    //////////////////////////// DashBoard //////////////////////////////////

    @Provides
    @Singleton
    HomeRemoteApi provideDashBoardApi(Retrofit retrofit) {
        return retrofit.create(HomeRemoteApi.class);
    }

    @Provides
    @Singleton
    HomeRepo.DashboardDbData provideDashboardDbDataSourceImp() {
        return new HomeDbDataSourceImp();
    }


    @Provides
    @Singleton
    HomeRepo.DashboardRemoteData provideDashboardRemoteDataSourceImp(HomeRemoteApi homeRemoteApi) {
        return new HomeRemoteDataSourceImp(homeRemoteApi);
    }

    @Provides
    @Singleton
    HomeUseCase provideDashboardUseCase() {
        return new HomeUseCase();
    }


    @Provides
    @Singleton
    HomeDbUseCase provideHomeDbUseCase() {
        return new HomeDbUseCase();
    }


    @Provides
    @Singleton
    HomeRemoteUseCase provideHomeRemoteUseCase(HomeRepo.DashboardRemoteData dashboardRemoteData) {
        return new HomeRemoteUseCase(dashboardRemoteData);
    }


    @Provides
    @Singleton
    @Named("CardFragment")
    ViewModelFactory provideCardFragmentViewModelFactory(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        return new ViewModelFactory(homeUseCase, homeDbUseCase, homeRemoteUseCase);
    }

    @Provides
    @Singleton
    @Named("QuizHomeFragment")
    ViewModelFactory provideQuizHomeViewModelFactory(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        return new ViewModelFactory(homeUseCase, homeDbUseCase, homeRemoteUseCase);
    }


    @Provides
    @Singleton
    @Named("KYCFragment")
    ViewModelFactory provideKYCViewModelFactory(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        return new ViewModelFactory(homeUseCase, homeDbUseCase, homeRemoteUseCase);
    }


    @Provides
    @Singleton
    @Named("ScholarShipFragment")
    ViewModelFactory provideScholarShipViewModelFactory(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        return new ViewModelFactory(homeUseCase, homeDbUseCase, homeRemoteUseCase);
    }


    @Provides
    @Singleton
    @Named("HomeActivity")
    ViewModelFactory provideHomeActivityViewModelFactory(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        return new ViewModelFactory(homeUseCase, homeDbUseCase, homeRemoteUseCase);
    }


}
