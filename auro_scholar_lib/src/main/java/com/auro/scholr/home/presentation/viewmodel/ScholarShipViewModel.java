package com.auro.scholr.home.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.core.common.MessgeNotifyStatus;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;

import io.reactivex.disposables.CompositeDisposable;

public class ScholarShipViewModel extends ViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<MessgeNotifyStatus> notifyLiveData = new MutableLiveData<>();

    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();

    public ScholarShipViewModel(HomeUseCase homeUseCase, HomeDbUseCase homeDbUseCase, HomeRemoteUseCase homeRemoteUseCase) {
    }

   /* public CardViewModel(SignupUseCase signupUseCase, SignUpDbUseCase signUpDbUseCase, SignUpRemoteUseCase signUpRemoteUseCase) {
    }*/

    public MutableLiveData<MessgeNotifyStatus> getNotifyLiveData() {
        return notifyLiveData;
    }
}
