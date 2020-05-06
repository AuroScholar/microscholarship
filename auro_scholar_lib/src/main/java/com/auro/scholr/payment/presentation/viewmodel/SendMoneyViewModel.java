package com.auro.scholr.payment.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.auro.scholr.core.common.MessgeNotifyStatus;
import com.auro.scholr.home.domain.usecase.HomeDbUseCase;
import com.auro.scholr.home.domain.usecase.HomeRemoteUseCase;
import com.auro.scholr.home.domain.usecase.HomeUseCase;
import com.auro.scholr.payment.domain.PaymentRemoteUseCase;
import com.auro.scholr.payment.domain.PaymentUseCase;

import io.reactivex.disposables.CompositeDisposable;


public class SendMoneyViewModel extends ViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<MessgeNotifyStatus> notifyLiveData = new MutableLiveData<>();

    public MutableLiveData<String> mobileNumber = new MutableLiveData<>();

    public SendMoneyViewModel(PaymentUseCase paymentUseCase, PaymentRemoteUseCase paymentRemoteUseCase) {
    }



    public MutableLiveData<MessgeNotifyStatus> getNotifyLiveData() {
        return notifyLiveData;
    }


}
