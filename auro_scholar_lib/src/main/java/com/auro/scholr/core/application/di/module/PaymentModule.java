package com.auro.scholr.core.application.di.module;

import com.auro.scholr.core.application.di.component.ViewModelFactory;
import com.auro.scholr.payment.data.datasource.remote.PaymentRemoteApi;
import com.auro.scholr.payment.data.datasource.remote.PaymentRemoteDataSourceImp;
import com.auro.scholr.payment.data.repository.PaymentRepo;
import com.auro.scholr.payment.domain.PaymentRemoteUseCase;
import com.auro.scholr.payment.domain.PaymentUseCase;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class PaymentModule {


    @Provides
    @Singleton
    PaymentRemoteApi providePaymentRemoteApi(Retrofit retrofit) {
        return retrofit.create(PaymentRemoteApi.class);
    }


    @Provides
    @Singleton
    PaymentRepo.PaymentRemoteData providePaymentRemoteDataSourceImp(PaymentRemoteApi paymentRemoteApi) {
        return new PaymentRemoteDataSourceImp(paymentRemoteApi);
    }


    @Provides
    @Singleton
    PaymentUseCase providePaymentUseCase() {
        return new PaymentUseCase();
    }


    @Provides
    @Singleton
    PaymentRemoteUseCase providePaymentRemoteUseCase(PaymentRepo.PaymentRemoteData paymentRemoteData) {
        return new PaymentRemoteUseCase(paymentRemoteData);
    }


    @Provides
    @Singleton
    @Named("SendMoneyFragment")
    ViewModelFactory provideSendMoneyFragmentViewModelFactory(PaymentUseCase paymentUseCase, PaymentRemoteUseCase paymentRemoteUseCase) {
        return new ViewModelFactory(paymentUseCase, paymentRemoteUseCase);
    }



    @Provides
    @Singleton
    @Named("PaytmFragment")
    ViewModelFactory providePaytmFragmentViewModelFactory(PaymentUseCase paymentUseCase, PaymentRemoteUseCase paymentRemoteUseCase) {
        return new ViewModelFactory(paymentUseCase, paymentRemoteUseCase);
    }

    @Provides
    @Singleton
    @Named("BankFragment")
    ViewModelFactory provideBankFragmentViewModelFactory(PaymentUseCase paymentUseCase, PaymentRemoteUseCase paymentRemoteUseCase) {
        return new ViewModelFactory(paymentUseCase, paymentRemoteUseCase);
    }


    @Provides
    @Singleton
    @Named("UPIFragment")
    ViewModelFactory provideUPIFragmentViewModelFactory(PaymentUseCase paymentUseCase, PaymentRemoteUseCase paymentRemoteUseCase) {
        return new ViewModelFactory(paymentUseCase, paymentRemoteUseCase);
    }
}
