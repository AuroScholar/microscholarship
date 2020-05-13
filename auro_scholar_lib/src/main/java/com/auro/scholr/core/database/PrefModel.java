package com.auro.scholr.core.database;


import com.auro.scholr.home.data.model.AssignmentReqModel;

import java.io.Serializable;
import java.util.List;

public class PrefModel implements Serializable {

    private String userKYCProfilePhotoPath;
    private String userLanguage;
    private List<AssignmentReqModel> listAzureImageList;

    public String getUserLanguage() {
        return userLanguage;
    }

    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserKYCProfilePhotoPath() {
        return userKYCProfilePhotoPath;
    }

    public void setUserKYCProfilePhotoPath(String userKYCProfilePhotoPath) {
        this.userKYCProfilePhotoPath = userKYCProfilePhotoPath;
    }

    public List<AssignmentReqModel> getListAzureImageList() {
        return listAzureImageList;
    }

    public void setListAzureImageList(List<AssignmentReqModel> listAzureImageList) {
        this.listAzureImageList = listAzureImageList;
    }
}
