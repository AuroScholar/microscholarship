package com.auro.scholr.home.data.model.passportmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassportReqModel {

    @SerializedName("mobile_no")
    @Expose
    private String mobileNumber;

    @SerializedName("month")
    @Expose
    private String month;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("is_all")
    @Expose
    private String isAll;


    public String getIsAll() { return isAll; }

    public void setIsAll(String isAll) { this.isAll = isAll; }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
