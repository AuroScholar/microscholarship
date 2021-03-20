package com.auro.scholr.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetStudentUpdateProfile {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email_id")
    @Expose
    private String emailId;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("grade")
    @Expose
    private String grade;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("school_type")
    @Expose
    private String schoolType;
    @SerializedName("board_type")
    @Expose
    private String boardType;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("scholarship_amount")
    @Expose
    private Integer scholarshipAmount;
    @SerializedName("user_profile_image")
    @Expose
    private String userProfileImage;
    @SerializedName("mobile_model")
    @Expose
    private String mobileModel;
    @SerializedName("mobile_manufacturer")
    @Expose
    private String mobileManufacturer;
    @SerializedName("mobile_version")
    @Expose
    private String mobileVersion;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_private_tution")
    @Expose
    private String isPrivateTution;
    @SerializedName("private_tution_type")
    @Expose
    private String privateTutionType;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(Integer scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    public String getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    public String getMobileManufacturer() {
        return mobileManufacturer;
    }

    public void setMobileManufacturer(String mobileManufacturer) {
        this.mobileManufacturer = mobileManufacturer;
    }

    public String getMobileVersion() {
        return mobileVersion;
    }

    public void setMobileVersion(String mobileVersion) {
        this.mobileVersion = mobileVersion;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIsPrivateTution() {
        return isPrivateTution;
    }

    public void setIsPrivateTution(String isPrivateTution) {
        this.isPrivateTution = isPrivateTution;
    }

    public String getPrivateTutionType() {
        return privateTutionType;
    }

    public void setPrivateTutionType(String privateTutionType) {
        this.privateTutionType = privateTutionType;
    }
}

