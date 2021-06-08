package com.auro.scholr.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveImageReqModel {

    @SerializedName("QuestionID")
    @Expose
    private String examId;

    @SerializedName("QuestionID")
    @Expose
    private String registration_id;

    @SerializedName("img_normal_path")
    @Expose
    private String imgNormalPath="";

    @SerializedName("img_path")
    @Expose
    private String imgPath="";

    @SerializedName("quiz_id")
    @Expose
    private String quizId="";


    byte[] imageBytes;

    public String getImgNormalPath() {
        return imgNormalPath;
    }

    public void setImgNormalPath(String imgNormalPath) {
        this.imgNormalPath = imgNormalPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
    }
}