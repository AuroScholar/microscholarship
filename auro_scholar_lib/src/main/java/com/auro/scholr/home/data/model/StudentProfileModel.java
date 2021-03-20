package com.auro.scholr.home.data.model;

public class StudentProfileModel {



    String Phonenumber="";
    String gender="";
    String school_type="";
    String board_type="";
    String language="";
    String latitude="";
    String longitude="";
    String manufacturer="";
    String mobileVersion="";
    String mobile_model = "";
    String isPrivateTution="";
    String privateTutionType="";
    String emailID="";
    byte[] imageBytes;
    String userName="";

    public String getMobile_model() {
        return mobile_model;
    }

    public void setMobile_model(String mobile_model) {
        this.mobile_model = mobile_model;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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



    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
