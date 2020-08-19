package com.auro.scholr.payment.domain;

import com.auro.scholr.R;
import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.NetworkUtil;
import com.auro.scholr.core.common.ResponseApi;
import com.auro.scholr.core.common.Status;
import com.auro.scholr.core.network.NetworkUseCase;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalReqModel;
import com.auro.scholr.payment.data.repository.PaymentRepo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Response;

import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_200;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_400;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_401;
import static com.auro.scholr.core.common.AppConstant.ResponseConstatnt.RES_FAIL;

public class PaymentRemoteUseCase extends NetworkUseCase {

    PaymentRepo.PaymentRemoteData paymentRemoteData;
    Gson gson = new Gson();

    public PaymentRemoteUseCase(PaymentRepo.PaymentRemoteData paymentRemoteData) {
        this.paymentRemoteData = paymentRemoteData;
    }


    @Override
    public Single<Boolean> isAvailInternet() {
        return NetworkUtil.hasInternetConnection();
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

    private ResponseApi handleResponse(Response<JsonObject> response, Status apiTypeStatus) {

        switch (response.code()) {

            case RES_200:
                return response200(response, apiTypeStatus);

            case RES_401:
                return response401(apiTypeStatus);

            case RES_400:
                return responseFail400(response, apiTypeStatus);

            case RES_FAIL:
                return responseFail(apiTypeStatus);

            default:
                return ResponseApi.fail(AuroApp.getAppContext().getString(R.string.default_error), apiTypeStatus);
        }
    }

    public Single<ResponseApi> paytmWithdrawalApi(PaytmWithdrawalReqModel reqModel){
        return paymentRemoteData.paytmWithdrawalApi(reqModel).map(new Function<Response<JsonObject>, ResponseApi>() {
            @Override
            public ResponseApi apply(Response<JsonObject> response) throws Exception {

                if (response != null) {


                    return handleResponse(response, Status.PAYTM_WITHDRAWAL);


                } else {

                    return responseFail(null);
                }
            }
        });
    }

}
