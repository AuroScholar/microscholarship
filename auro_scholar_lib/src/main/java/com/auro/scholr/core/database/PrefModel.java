package com.auro.scholr.core.database;


import java.io.Serializable;

public class PrefModel implements Serializable {

    private boolean isTour;
    private boolean isLogin;
    private String userLoginId;
    private String userCountry;
    private String countryNameCode;
    private String countryPhoneCode;

    private String currentLatitude;
    private String currentLongitude;
    private boolean isEmail;
    private String signupPrefilledData;

    private String emailId;

    private String deviceToken;


    private String updateMobileNum;
    private String updateEmailId;

    // Verify Number Response
    // Verify Login Response
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
    private String message;
    private String profileTimestamp;



    public String getProfileTimestamp() {
        return profileTimestamp;
    }

    public void setProfileTimestamp(String profileTimestamp) {
        this.profileTimestamp = profileTimestamp;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isTour() {
        return isTour;
    }

    public void setTour(boolean tour) {
        isTour = tour;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


    public boolean isLogin() {
        return isLogin;
    }

    public String getSignupPrefilledData() {
        return signupPrefilledData;
    }

    public void setSignupPrefilledData(String signupPrefilledData) {
        this.signupPrefilledData = signupPrefilledData;
    }

    public boolean isEmail() {
        return isEmail;
    }

    public void setEmail(boolean email) {
        isEmail = email;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getCountryNameCode() {
        return countryNameCode;
    }

    public void setCountryNameCode(String countryNameCode) {
        this.countryNameCode = countryNameCode;
    }

    public String getCountryPhoneCode() {
        return countryPhoneCode;
    }

    public void setCountryPhoneCode(String countryPhoneCode) {
        this.countryPhoneCode = countryPhoneCode;
    }


    public String getCurrentLatitude() {
        return currentLatitude;
    }

    public void setCurrentLatitude(String currentLatitude) {
        this.currentLatitude = currentLatitude;
    }

    public String getCurrentLongitude() {
        return currentLongitude;
    }

    public void setCurrentLongitude(String currentLongitude) {
        this.currentLongitude = currentLongitude;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUpdateMobileNum() {
        return updateMobileNum;
    }

    public void setUpdateMobileNum(String updateMobileNum) {
        this.updateMobileNum = updateMobileNum;
    }

    public String getUpdateEmailId() {
        return updateEmailId;
    }

    public void setUpdateEmailId(String updateEmailId) {
        this.updateEmailId = updateEmailId;
    }

}
