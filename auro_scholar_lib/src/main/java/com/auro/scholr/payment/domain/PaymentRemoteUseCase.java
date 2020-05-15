package com.auro.scholr.payment.domain;

import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.network.NetworkUseCase;
import com.auro.scholr.payment.data.repository.PaymentRepo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Response;

public class PaymentRemoteUseCase extends NetworkUseCase {

    PaymentRepo.PaymentRemoteData paymentRemoteData;
    Gson gson = new Gson();

    public PaymentRemoteUseCase(PaymentRepo.PaymentRemoteData paymentRemoteData) {
        this.paymentRemoteData = paymentRemoteData;
    }

    @Override
    public Single<Boolean> isAvailInternet() {
        return null;
    }

    @Override
    public ResponseApi response200(Response<JsonObject> response, Status status) {
        return null;
    }

    @Override
    public ResponseApi response401(Status status) {
        return null;
    }

    @Override
    public ResponseApi responseFail400(Response<JsonObject> response, Status status) {
        return null;
    }

    @Override
    public ResponseApi responseFail(Status status) {
        return null;
    }
}
