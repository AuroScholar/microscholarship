package com.auro.scholr.home.data.model.passportmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PassportMonthModel{

    boolean isExpanded;

    @SerializedName("month_name")
    @Expose
    private String monthName;

    @SerializedName("data_list")
    @Expose
    private List<PassportSubjectModel> passportSubjectModelList = null;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("subjects")
    @Expose
    private List<String> subjects = null;

    public List<PassportSubjectModel> getPassportSubjectModelList() {
        return passportSubjectModelList;
    }

    public void setPassportSubjectModelList(List<PassportSubjectModel> passportSubjectModelList) {
        this.passportSubjectModelList = passportSubjectModelList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }


    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
}
