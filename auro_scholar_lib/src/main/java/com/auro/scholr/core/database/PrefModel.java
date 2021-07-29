package com.auro.scholr.core.database;


import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.DashboardResModel;
import com.auro.scholr.home.data.model.DynamiclinkResModel;
import com.auro.scholr.home.data.model.QuizResModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PrefModel implements Serializable {

    private String userKYCProfilePhotoPath;
    private String userLanguage;
    private boolean isDashbaordApiCall;

    private DashboardResModel dashboardResModel;

    private AssignmentReqModel assignmentReqModel;
    private boolean tooltipStatus;
    private String userMobile;

    private int studentClass;

    private  String deviceToken;

    private QuizResModel quizResModel;

    private boolean preLoginDisclaimer;

    private boolean preQuizDisclaimer;

    private boolean preKycDisclaimer;

    private boolean preMoneyTransferDisclaimer;

    public boolean isPreLoginDisclaimer() {
        return preLoginDisclaimer;
    }

    public void setPreLoginDisclaimer(boolean preLoginDisclaimer) {
        this.preLoginDisclaimer = preLoginDisclaimer;
    }

    public boolean isPreQuizDisclaimer() {
        return preQuizDisclaimer;
    }

    public void setPreQuizDisclaimer(boolean preQuizDisclaimer) {
        this.preQuizDisclaimer = preQuizDisclaimer;
    }

    public boolean isPreKycDisclaimer() {
        return preKycDisclaimer;
    }

    public void setPreKycDisclaimer(boolean preKycDisclaimer) {
        this.preKycDisclaimer = preKycDisclaimer;
    }

    public boolean isPreMoneyTransferDisclaimer() {
        return preMoneyTransferDisclaimer;
    }

    public void setPreMoneyTransferDisclaimer(boolean preMoneyTransferDisclaimer) {
        this.preMoneyTransferDisclaimer = preMoneyTransferDisclaimer;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


    private List<AssignmentReqModel> listAzureImageList = new ArrayList<>();

    private DynamiclinkResModel dynamiclinkResModel;

    public int getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(int studentClass) {
        this.studentClass = studentClass;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

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


    public QuizResModel getQuizResModel() {
        return quizResModel;
    }

    public void setQuizResModel(QuizResModel quizResModel) {
        this.quizResModel = quizResModel;
    }

    public boolean isDashbaordApiCall() {
        return isDashbaordApiCall;
    }

    public void setDashbaordApiCall(boolean dashbaordApiCall) {
        isDashbaordApiCall = dashbaordApiCall;
    }
}
