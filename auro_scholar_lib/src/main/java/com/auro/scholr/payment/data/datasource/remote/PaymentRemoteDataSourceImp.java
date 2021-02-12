package com.auro.scholr.payment.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalByBankAccountReqModel;
import com.auro.scholr.payment.data.model.request.PaytmWithdrawalByUPIReqModel;
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
        params.put(AppConstant.paytmApiParam.RECEIVER_MOBILE_NUMBER, reqModel.getMobileno());
        params.put(AppConstant.paytmApiParam.RECEIVER_DISBURSEMENT_MONTH, reqModel.getDisbursementmonth());
        params.put(AppConstant.paytmApiParam.RECEIVER_DISBURSEMENT, reqModel.getDisbursement());
        params.put(AppConstant.paytmApiParam.RECEIVER_PURPOSE, reqModel.getPurpose());
        params.put(AppConstant.paytmApiParam.BENEFICIARY_MOBILE_NUMBER, reqModel.getBeneficiarymobileno());
        params.put(AppConstant.paytmApiParam.BENEFICIARY_NAME, reqModel.getBeneficiaryname());
        return paymentRemoteApi.paytmWithdrawalApi(params);
    }

    @Override
    public Single<Response<JsonObject>> paytmWithdrawalByUpiApi(PaytmWithdrawalByUPIReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.paytmUPITransfer.RECEIVER_MOBILE_NUMBER, reqModel.getMobileNo());
        params.put(AppConstant.paytmUPITransfer.RECEIVER_UPI_ADDRESS, reqModel.getUpiaddress());
        params.put(AppConstant.paytmUPITransfer.RECEIVER_DISBURSEMENT_MONTH, reqModel.getDisbursementMonth());
        params.put(AppConstant.paytmUPITransfer.RECEIVER_DISBURSEMENT, reqModel.getDisbursement());
        params.put(AppConstant.paytmUPITransfer.RECEIVER_PURPOSE, reqModel.getPurpose());
        return paymentRemoteApi.paytmUpiTransferApi(params);
    }

    @Override
    public Single<Response<JsonObject>> paytmWithdrawalByAccountApi(PaytmWithdrawalByBankAccountReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.paytmAccountTransferParam.RECEIVER_MOBILE_NUMBER, reqModel.getMobileNo());
        params.put(AppConstant.paytmAccountTransferParam.RECEIVER_DISBURSEMENT_MONTH, reqModel.getDisbursementMonth());
        params.put(AppConstant.paytmAccountTransferParam.RECEIVER_DISBURSEMENT, reqModel.getDisbursement());
        params.put(AppConstant.paytmAccountTransferParam.RECEIVER_PURPOSE, reqModel.getPurpose());
        params.put(AppConstant.paytmAccountTransferParam.RECEIVER_BANKACCOUNT_NO, reqModel.getBankaccountno());
        params.put(AppConstant.paytmAccountTransferParam.RECEIVER_IFSCCODE, reqModel.getIfsccode());
        return paymentRemoteApi.paytmAccountTransferApi(params);
    }

    @Override
    public Single<Response<JsonObject>> paymentTransferApi(PaytmWithdrawalByBankAccountReqModel reqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.paytmApiParam.STUDENT_MOBILE_NUM, reqModel.getMobileNo());
        params.put(AppConstant.paytmApiParam.STUDENT_ID, reqModel.getStudentId());
        params.put(AppConstant.paytmApiParam.PAYMENT_MODE, reqModel.getPaymentMode());
        params.put(AppConstant.paytmApiParam.MONTH, reqModel.getDisbursementMonth());
        params.put(AppConstant.paytmApiParam.BENEFICIARY_MOBILE_NUM, reqModel.getBeneficiary_mobileNum());
        params.put(AppConstant.paytmApiParam.BENEFICIARY_NAME_NEW, reqModel.getBeneficiary_name());
        params.put(AppConstant.paytmApiParam.BANK_ACCOUNT_NUM, reqModel.getBankaccountno());
        params.put(AppConstant.paytmApiParam.IFSC_CODE, reqModel.getIfsccode());
        params.put(AppConstant.paytmApiParam.UPI_ADDRESS, reqModel.getUpiAddress());
        params.put(AppConstant.paytmApiParam.AMOUNT, reqModel.getAmount());
        params.put(AppConstant.paytmApiParam.PURPOSE, reqModel.getPurpose());
        return paymentRemoteApi.paytmAccountTransferApi(params);
    }
}
