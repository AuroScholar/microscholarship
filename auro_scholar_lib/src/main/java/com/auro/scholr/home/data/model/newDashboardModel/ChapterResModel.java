package com.auro.scholr.home.data.model.newDashboardModel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChapterResModel {

    @SerializedName("number")
    @Expose
    private Integer number;
    @SerializedName("attempt")
    @Expose
    private Integer attempt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("scorepoints")
    @Expose
    private Integer scorepoints;
    @SerializedName("totalpoints")
    @Expose
    private Integer totalpoints;
    @SerializedName("scholarshipamount")
    @Expose
    private Integer scholarshipamount;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScorepoints() {
        return scorepoints;
    }

    public void setScorepoints(Integer scorepoints) {
        this.scorepoints = scorepoints;
    }

    public Integer getTotalpoints() {
        return totalpoints;
    }

    public void setTotalpoints(Integer totalpoints) {
        this.totalpoints = totalpoints;
    }

    public Integer getScholarshipamount() {
        return scholarshipamount;
    }

    public void setScholarshipamount(Integer scholarshipamount) {
        this.scholarshipamount = scholarshipamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}