package com.auro.scholr.core.database;


import com.auro.scholr.home.data.model.AssignmentReqModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrefModel implements Serializable {

    private String userKYCProfilePhotoPath;
    private String userLanguage;

    private AssignmentReqModel assignmentReqModel;
    private boolean tooltipStatus;
    private List<AssignmentReqModel> listAzureImageList = new ArrayList<>();

    public AssignmentReqModel getAssignmentReqModel() {
        return assignmentReqModel;
    }

    public void setAssignmentReqModel(AssignmentReqModel assignmentReqModel) {
        this.assignmentReqModel = assignmentReqModel;
    }

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


    public boolean isTooltipStatus() {
        return tooltipStatus;
    }

    public void setTooltipStatus(boolean tooltipStatus) {
        this.tooltipStatus = tooltipStatus;
    }
}
