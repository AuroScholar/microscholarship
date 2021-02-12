package com.auro.scholr.payment.data.model.request;

public class PaytmWithdrawalByBankAccountReqModel {

    String mobileNo="";
    String studentId="";
    String paymentMode="";
    String disbursementMonth="";
    String beneficiary_mobileNum="";
    String beneficiary_name;
    String bankaccountno="";
    String ifsccode="";
    String upiAddress="";
    String amount;
    String disbursement="";
    String purpose="";

    public String getBeneficiary_name() {
        return beneficiary_name;
    }

    public void setBeneficiary_name(String beneficiary_name) {
        this.beneficiary_name = beneficiary_name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBeneficiary_mobileNum() {
        return beneficiary_mobileNum;
    }

    public void setBeneficiary_mobileNum(String beneficiary_mobileNum) {
        this.beneficiary_mobileNum = beneficiary_mobileNum;
    }

    public String getUpiAddress() {
        return upiAddress;
    }

    public void setUpiAddress(String upiAddress) {
        this.upiAddress = upiAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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
