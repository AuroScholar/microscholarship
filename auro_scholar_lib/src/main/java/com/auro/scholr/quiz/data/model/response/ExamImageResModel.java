package com.auro.scholr.quiz.data.model.response;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamImageResModel {

    @SerializedName("id_name")
    @Expose
    private String idName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("eklavvya_exam_id")
    @Expose
    private String eklavvyaExamId;
    @SerializedName("url")
    @Expose
    private String url;

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

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

    public String getEklavvyaExamId() {
        return eklavvyaExamId;
    }

    public void setEklavvyaExamId(String eklavvyaExamId) {
        this.eklavvyaExamId = eklavvyaExamId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}