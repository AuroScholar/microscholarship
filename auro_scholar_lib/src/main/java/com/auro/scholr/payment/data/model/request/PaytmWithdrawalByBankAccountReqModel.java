package com.auro.scholr.payment.data.model.request;

public class PaytmWithdrawalByBankAccountReqModel {

    String mobileNo ;
    String disbursementMonth;
    String disbursement;
    String purpose;
    String bankaccountno;
    String ifsccode;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDisbursementMonth() {
        return disbursementMonth;
    }

    public void setDisbursementMonth(String disbursementMonth) {
        this.disbursementMonth = disbursementMonth;
    }

    public String getDisbursement() {
        return disbursement;
    }

    public void setDisbursement(String disbursement) {
        this.disbursement = disbursement;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getBankaccountno() {
        return bankaccountno;
    }

    public void setBankaccountno(String bankaccountno) {
        this.bankaccountno = bankaccountno;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }
}
