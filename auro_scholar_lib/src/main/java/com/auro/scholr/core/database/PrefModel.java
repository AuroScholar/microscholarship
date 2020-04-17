package com.auro.scholr.core.database;


import java.io.Serializable;

public class PrefModel implements Serializable {

    private String userKYCProfilePhotoPath;


    public String getUserKYCProfilePhotoPath() {
        return userKYCProfilePhotoPath;
    }

    public void setUserKYCProfilePhotoPath(String userKYCProfilePhotoPath) {
        this.userKYCProfilePhotoPath = userKYCProfilePhotoPath;
    }
}
