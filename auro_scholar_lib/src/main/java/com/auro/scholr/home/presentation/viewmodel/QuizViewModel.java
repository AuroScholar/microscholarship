package com.auro.scholr.home.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.core.common.MessgeNotifyStatus;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;

import io.reactivex.disposables.CompositeDisposable;


public class QuizViewModel extends ViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<MessgeNotifyStatus> notifyLiveData = new MutableLiveData<>();

    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();
    public HomeUseCase homeUseCase;

    public QuizViewModel(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
        this.homeUseCase = homeUseCase;
    }


    public MutableLiveData<MessgeNotifyStatus> getNotifyLiveData() {
        return notifyLiveData;
    }


}
