package com.auro.scholr.home.data.model.passportmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PassportSubjectModel {

    boolean isExpanded;



    @SerializedName("subject_name")
    @Expose
    private String subjectName;
    @SerializedName("quiz_data")
    @Expose
    private List<PassportQuizModel> quizData = null;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public List<PassportQuizModel> getQuizData() {
        return quizData;
    }

    public void setQuizData(List<PassportQuizModel> quizData) {
        this.quizData = quizData;
    }
    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}

