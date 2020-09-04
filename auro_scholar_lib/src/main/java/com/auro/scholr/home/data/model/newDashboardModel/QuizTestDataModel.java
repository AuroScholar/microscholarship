package com.auro.scholr.home.data.model.newDashboardModel;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizTestDataModel {

    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("ScorePercentage")
    @Expose
    private Integer scorePercentage;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("chapter")
    @Expose
    private List<ChapterResModel> chapter = null;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(Integer scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ChapterResModel> getChapter() {
        return chapter;
    }

    public void setChapter(List<ChapterResModel> chapter) {
        this.chapter = chapter;
    }

}