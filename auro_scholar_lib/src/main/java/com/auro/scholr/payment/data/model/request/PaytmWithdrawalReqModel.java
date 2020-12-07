package com.auro.scholr.payment.data.model.request;

public class PaytmWithdrawalReqModel {
    String mobileno;
    String disbursementmonth;
    String disbursement;
    String purpose;
    String beneficiarymobileno;
    String beneficiaryname;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getDisbursementmonth() {
        return disbursementmonth;
    }

    public void setDisbursementmonth(String disbursementmonth) {
        this.disbursementmonth = disbursementmonth;
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

    public String getBeneficiarymobileno() {
        return beneficiarymobileno;
    }

    public void setBeneficiarymobileno(String beneficiarymobileno) {
        this.beneficiarymobileno = beneficiarymobileno;
    }

    public String getBeneficiaryname() {
        return beneficiaryname;
    }

    public void setBeneficiaryname(String beneficiaryname) {
        this.beneficiaryname = beneficiaryname;
    }
}
