package com.auro.scholr.payment.data.model.request;

public class PaytmWithdrawalByUPIReqModel {


    String mobileNo;
    String upiaddress;
    String disbursementMonth;
    String disbursement;
    String purpose;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUpiaddress() {
        return upiaddress;
    }

    public void setUpiaddress(String upiaddress) {
        this.upiaddress = upiaddress;
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
}
