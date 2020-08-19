package com.auro.scholr.payment.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalReqModel;
import com.auro.scholr.payment.data.repository.PaymentRepo;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import retrofit2.Response;

public class PaymentRemoteDataSourceImp implements PaymentRepo.PaymentRemoteData {

    PaymentRemoteApi paymentRemoteApi;

    public PaymentRemoteDataSourceImp(PaymentRemoteApi paymentRemoteApi) {
        this.paymentRemoteApi = paymentRemoteApi;
    }


    @Override
    public Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model) {
        /*These param for generic sdk*/
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, model.getMobileNumber());
        params.put(AppConstant.DashBoardParams.STUDENT_CLASS, model.getStudentClass());
        params.put(AppConstant.DashBoardParams.REGISTRATION_SOURCE, model.getRegitrationSource());
        return paymentRemoteApi.getDashboardData(params);
    }



    @Override
    public Single<Response<JsonObject>> paytmWithdrawalApi(PaytmWithdrawalReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.paytmApiParam.RECEIVER_MOBILE_NUMBER, reqModel.getMobileNumber());
        params.put(AppConstant.paytmApiParam.RECEIVER_UPI_ADDRESS, reqModel.getUpiAddress());
        params.put(AppConstant.paytmApiParam.DISBURSEMENT_MONTH, reqModel.getDisbursementMonth());
        params.put(AppConstant.paytmApiParam.DISBURSEMENT, reqModel.getDisbursement());
        params.put(AppConstant.paytmApiParam.BANKACCOUNTNO, reqModel.getBankAccount());
        params.put(AppConstant.paytmApiParam.IFSCCODE, reqModel.getIfscCode());
        return paymentRemoteApi.paytmWithdrawalApi(params);
    }
}
