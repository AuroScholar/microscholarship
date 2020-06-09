package com.auro.scholr.teacher.data.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyClassRoomStudentResModel {

    @SerializedName("sudent_name")
    @Expose
    private String sudentName;
    @SerializedName("sudent_mobile")
    @Expose
    private String sudentMobile;
    @SerializedName("total_score")
    @Expose
    private String totalScore;
    @SerializedName("student_photo")
    @Expose
    private String studentPhoto;

    public String getSudentName() {
        return sudentName;
    }

    public void setSudentName(String sudentName) {
        this.sudentName = sudentName;
    }

    public String getSudentMobile() {
        return sudentMobile;
    }

    public void setSudentMobile(String sudentMobile) {
        this.sudentMobile = sudentMobile;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

}
