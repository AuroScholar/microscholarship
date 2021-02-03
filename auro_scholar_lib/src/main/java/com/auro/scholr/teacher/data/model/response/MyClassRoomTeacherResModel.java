package com.auro.scholr.teacher.data.model.response;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyClassRoomTeacherResModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error=false;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("teacher_name")
    @Expose
    private String teacherName;
    @SerializedName("teacher_email")
    @Expose
    private String teacherEmail;
    @SerializedName("school_name")
    @Expose
    private String schoolName;
    @SerializedName("state_id")
    @Expose
    private String stateId;
    @SerializedName("district_id")
    @Expose
    private String districtId;
    @SerializedName("teacher_class")
    @Expose
    private String teacherClass;
    @SerializedName("teacher_subject")
    @Expose
    private String teacherSubject;

    @SerializedName("govt_id_front")
    @Expose
    private String govt_id_front;

    @SerializedName("govt_id_back")
    @Expose
    private String govt_id_back;

    @SerializedName("school_id_card")
    @Expose
    private String school_id_card;

    @SerializedName("teacher_photo")
    @Expose
    private String teacher_photo;

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

    @SerializedName("campaign")
    @Expose
    private String campaign;

    @SerializedName("regitration_source ")
    @Expose
    private String regitration_source ;

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getRegitration_source() {
        return regitration_source;
    }

    public void setRegitration_source(String regitration_source) {
        this.regitration_source = regitration_source;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getTeacherClass() {
        return teacherClass;
    }

    public void setTeacherClass(String teacherClass) {
        this.teacherClass = teacherClass;
    }

    public String getTeacherSubject() {
        return teacherSubject;
    }

    public void setTeacherSubject(String teacherSubject) {
        this.teacherSubject = teacherSubject;
    }

    public String getGovt_id_front() {
        return govt_id_front;
    }

    public void setGovt_id_front(String govt_id_front) {
        this.govt_id_front = govt_id_front;
    }

    public String getGovt_id_back() {
        return govt_id_back;
    }

    public void setGovt_id_back(String govt_id_back) {
        this.govt_id_back = govt_id_back;
    }

    public String getSchool_id_card() {
        return school_id_card;
    }

    public void setSchool_id_card(String school_id_card) {
        this.school_id_card = school_id_card;
    }

    public String getTeacher_photo() {
        return teacher_photo;
    }

    public void setTeacher_photo(String teacher_photo) {
        this.teacher_photo = teacher_photo;
    }


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



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}