package com.auro.scholr.teacher.data.model.response;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyClassRoomTeacherResModel {

    @SerializedName("registration_date")
    @Expose
    private String registrationDate;
    @SerializedName("wallet_balance")
    @Expose
    private Integer walletBalance;
    @SerializedName("score_total")
    @Expose
    private Integer scoreTotal;
    @SerializedName("APImyclassroomstudent")
    @Expose
    private List<MyClassRoomStudentResModel> studentResModels = null;

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Integer walletBalance) {
        this.walletBalance = walletBalance;
    }

    public Integer getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Integer scoreTotal) {
        this.scoreTotal = scoreTotal;
    }


    public List<MyClassRoomStudentResModel> getStudentResModels() {
        return studentResModels;
    }

    public void setStudentResModels(List<MyClassRoomStudentResModel> studentResModels) {
        this.studentResModels = studentResModels;
    }
}