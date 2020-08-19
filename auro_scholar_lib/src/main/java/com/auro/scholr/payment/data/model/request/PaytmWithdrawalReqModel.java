package com.auro.scholr.payment.data.model.request;

public class PaytmWithdrawalReqModel {
    String mobileNumber;
    String upiAddress;
    String disbursementMonth;
    String disbursement;
    String bankAccount;
    String ifscCode;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUpiAddress() {
        return upiAddress;
    }

    public void setUpiAddress(String upiAddress) {
        this.upiAddress = upiAddress;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
