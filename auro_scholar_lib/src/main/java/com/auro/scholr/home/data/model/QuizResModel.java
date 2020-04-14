package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizResModel {

    @SerializedName("number")
    @Expose
    private Integer number;
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
    private String scholarshipamount;
    @SerializedName("status")
    @Expose
    private String status;


    public QuizResModel() {

    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public String getScholarshipamount() {
        return scholarshipamount;
    }

    public void setScholarshipamount(String scholarshipamount) {
        this.scholarshipamount = scholarshipamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}