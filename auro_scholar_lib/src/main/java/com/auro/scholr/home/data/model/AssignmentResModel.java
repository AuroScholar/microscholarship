package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentResModel {

    @SerializedName("AssignExamToStudentResult")
    @Expose
    private String assignExamToStudentResult;

    public String getAssignExamToStudentResult() {
        return assignExamToStudentResult;
    }

    public void setAssignExamToStudentResult(String assignExamToStudentResult) {
        this.assignExamToStudentResult = assignExamToStudentResult;
    }

}