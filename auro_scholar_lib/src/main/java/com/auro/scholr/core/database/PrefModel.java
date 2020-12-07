package com.auro.scholr.core.database;


import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DynamiclinkResModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrefModel implements Serializable {

    private String userKYCProfilePhotoPath;
    private String userLanguage;

    private DashboardResModel dashboardResModel;

    private AssignmentReqModel assignmentReqModel;
    private boolean tooltipStatus;
    private List<AssignmentReqModel> listAzureImageList = new ArrayList<>();

    private DynamiclinkResModel dynamiclinkResModel;


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

    public DashboardResModel getDashboardResModel() {
        return dashboardResModel;
    }

    public void setDashboardResModel(DashboardResModel dashboardResModel) {
        this.dashboardResModel = dashboardResModel;
    }

    public DynamiclinkResModel getDynamiclinkResModel() {
        return dynamiclinkResModel;
    }

    public void setDynamiclinkResModel(DynamiclinkResModel dynamiclinkResModel) {
        this.dynamiclinkResModel = dynamiclinkResModel;
    }
}
