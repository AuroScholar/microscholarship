package com.auro.scholr.payment.data.repository;

import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalReqModel;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import retrofit2.Response;

public interface PaymentRepo {

    interface PaymentRemoteData {
        Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model);

        Single<Response<JsonObject>> paytmWithdrawalApi(PaytmWithdrawalReqModel reqModel);
    }




}
