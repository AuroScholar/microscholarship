package com.auro.scholr.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DemographicResModel {

    @SerializedName("error")
    @Expose
    boolean error;

    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("Phonenumber")
    @Expose
    String Phonenumber;

    @SerializedName("gender")
    @Expose
    String gender;

    @SerializedName("school_type")
    @Expose
    String school_type;

    @SerializedName("board_type")
    @Expose
    String board_type;

    @SerializedName("language")
    @Expose
    String language;

    @SerializedName("latitude")
    @Expose
    String latitude="";


    @SerializedName("longitude")
    @Expose
    String longitude="";

    @SerializedName("mobile_model")
    @Expose
    String mobileModel="";

    @SerializedName("mobile_manufacturer")
    @Expose
    String manufacturer="";

    @SerializedName("mobile_version")
    @Expose
    String mobileVersion="";

    @SerializedName("is_private_tution")
    @Expose
    String isPrivateTution="";

    @SerializedName("private_tution_type")
    @Expose
    String privateTutionType="";

    public boolean isError() {
        return error;
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

    public String getMobileModel() {
        return mobileModel;
    }

    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMobileVersion() {
        return mobileVersion;
    }

    public void setMobileVersion(String mobileVersion) {
        this.mobileVersion = mobileVersion;
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

    public DemographicResModel() {

    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool_type() {
        return school_type;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public String getBoard_type() {
        return board_type;
    }

    public void setBoard_type(String board_type) {
        this.board_type = board_type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
