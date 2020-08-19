package com.auro.scholr.home.data.model.newDashboardModel;


import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizNewDashboardModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("quiztest")
    @Expose
    private List<QuizTestDataModel> quiztest = null;

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

    public List<QuizTestDataModel> getQuiztest() {
        return quiztest;
    }

    public void setQuiztest(List<QuizTestDataModel> quiztest) {
        this.quiztest = quiztest;
    }

}