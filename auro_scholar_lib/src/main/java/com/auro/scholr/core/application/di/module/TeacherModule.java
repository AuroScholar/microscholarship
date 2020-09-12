package com.auro.scholr.core.application.di.module;

import com.auro.scholr.core.application.di.component.ViewModelFactory;
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
    TeacherDbUseCase provideTeacherDbUseCase(TeacherRepo.TeacherDbData teacherDbData) {
        return new TeacherDbUseCase(teacherDbData);
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

    @Provides
    @Singleton
    @Named("TeacherInfoFragment")
    ViewModelFactory provideTeacherInfoFragmentViewModelFactory(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        return new ViewModelFactory(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
    }

    @Provides
    @Singleton
    @Named("TeacherSaveDetailFragment")
    ViewModelFactory provideTeacherSaveDetailViewModelFactory(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        return new ViewModelFactory(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
    }

    @Provides
    @Singleton
    @Named("SelectYourMessageDialogFragment")
    ViewModelFactory provideSelectYourMessageDialogModelFactory(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        return new ViewModelFactory(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
    }

    @Provides
    @Singleton
    @Named("TeacherProfileFragment")
    ViewModelFactory provideTeacherProfileViewModelFactory(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        return new ViewModelFactory(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
    }

    @Provides
    @Singleton
    @Named("SelectYourAppointmentDialogFragment")
    ViewModelFactory provideSelectYourAppointmentDialogModelFactory(TeacherUseCase teacherUseCase, TeacherDbUseCase teacherDbUseCase, TeacherRemoteUseCase teacherRemoteUseCase) {
        return new ViewModelFactory(teacherUseCase, teacherDbUseCase, teacherRemoteUseCase);
    }

}
