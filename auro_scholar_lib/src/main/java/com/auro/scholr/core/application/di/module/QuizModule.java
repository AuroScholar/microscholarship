package com.auro.scholr.core.application.di.module;

import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.quiz.data.datasource.remote.QuizRemoteApi;
import com.auro.scholr.quiz.data.datasource.remote.QuizRemoteDataSourceImp;
import com.auro.scholr.quiz.data.repository.QuizRepo;
import com.auro.scholr.quiz.domain.QuizNativeRemoteUseCase;
import com.auro.scholr.quiz.domain.QuizNativeUseCase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class QuizModule {


    @Provides
    @Singleton
    QuizRemoteApi provideDashBoardApi(Retrofit retrofit) {
        return retrofit.create(QuizRemoteApi.class);
    }

    @Provides
    @Singleton
    QuizRepo.QuizRemoteData provideQuizRemoteDataSourceImp(QuizRemoteApi quizRemoteApi) {
        return new QuizRemoteDataSourceImp(quizRemoteApi);
    }

    @Provides
    @Singleton
    QuizNativeUseCase provideQuizNativeUseCase() {
        return new QuizNativeUseCase();
    }


    @Provides
    @Singleton
    QuizNativeRemoteUseCase provideQuizNativeRemoteUseCase(QuizRepo.QuizRemoteData quizRemoteData) {
        return new QuizNativeRemoteUseCase(quizRemoteData);
    }



    @Provides
    @Singleton
    @Named("QuizTestNativeFragment")
    ViewModelFactory provideQuizTestNativeFragmentModelFactory(QuizNativeUseCase quizNativeUseCase, QuizNativeRemoteUseCase quizNativeRemoteUseCase) {
        return new ViewModelFactory(quizNativeUseCase ,quizNativeRemoteUseCase);
    }



}
