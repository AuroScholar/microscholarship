package com.auro.scholr.home.data.model;

import android.graphics.drawable.Drawable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategorySubjectResModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("subjectname")
    @Expose
    private String subjectname;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("no_of_quiz")
    @Expose
    private String noOfQuiz;
    @SerializedName("max_attempted")
    @Expose
    private String maxAttempted;
    @SerializedName("is_active")
    @Expose
    private String isActive;
    @SerializedName("attempted")
    @Expose
    private Integer attempted;

    @SerializedName("subject_code")
    @Expose
    private String subjectCode;


    private Drawable backgroundImage;


    private boolean isSelected;

    private boolean isLock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNoOfQuiz() {
        return noOfQuiz;
    }

    public void setNoOfQuiz(String noOfQuiz) {
        this.noOfQuiz = noOfQuiz;
    }

    public String getMaxAttempted() {
        return maxAttempted;
    }

    public void setMaxAttempted(String maxAttempted) {
        this.maxAttempted = maxAttempted;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Integer getAttempted() {
        return attempted;
    }

    public void setAttempted(Integer attempted) {
        this.attempted = attempted;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Drawable getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(Drawable backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
