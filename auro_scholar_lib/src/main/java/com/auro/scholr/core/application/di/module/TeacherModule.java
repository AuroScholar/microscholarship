package com.auro.scholr.core.application.di.module;

import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.home.data.datasource.database.HomeDbDataSourceImp;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteApi;
import com.auro.scholr.home.data.datasource.remote.HomeRemoteDataSourceImp;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;
import com.auro.scholr.teacher.data.datasource.database.TeacherDbDataSourceImp;
import com.auro.scholr.teacher.data.datasource.remote.TeacherRemoteApi;
import com.auro.scholr.teacher.data.datasource.remote.TeacherRemoteDataSourceImp;
import com.auro.scholr.teacher.data.repository.TeacherRepo;
import com.auro.scholr.teacher.domain.TeacherDbUseCase;
import com.auro.scholr.teacher.domain.TeacherRemoteUseCase;
import com.auro.scholr.teacher.domain.TeacherUseCase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class TeacherModule {

    //////////////////////////// DashBoard //////////////////////////////////

    @Provides
    @Singleton
    TeacherRemoteApi provideTeacherRemoteApi(Retrofit retrofit) {
        return retrofit.create(TeacherRemoteApi.class);
    }

    @Provides
    @Singleton
    TeacherRepo.TeacherDbData provideTeacherDbDataSourceImp() {
        return new TeacherDbDataSourceImp();
    }


    @Provides
    @Singleton
    TeacherRepo.TeacherRemoteData provideTeacherRemoteDataSourceImp(TeacherRemoteApi teacherRemoteApi) {
        return new TeacherRemoteDataSourceImp(teacherRemoteApi);
    }

    @Provides
    @Singleton
    TeacherUseCase provideTeacherUseCase() {
        return new TeacherUseCase();
    }


    @Provides
    @Singleton
    TeacherDbUseCase provideTeacherDbUseCase() {
        return new TeacherDbUseCase();
    }


    @Provides
    @Singleton
    TeacherRemoteUseCase provideTeacherRemoteUseCase(TeacherRepo.TeacherRemoteData teacherRemoteData) {
        return new TeacherRemoteUseCase(teacherRemoteData);
    }


    @Provides
    @Singleton
    @Named("MyClassroomFragment")
    ViewModelFactory provideMyClassRoomViewmodelViewModelFactory(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        return new ViewModelFactory(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
    }

    @Provides
    @Singleton
    @Named("TeacherKycFragment")
    ViewModelFactory provideTeacherKycFragmentViewModelFactory(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        return new ViewModelFactory(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
    }


}
