package com.auro.scholr.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjStudentExamInfo {

    @SerializedName("StudentID")
    @Expose
    private String studentID;
    @SerializedName("ExamName")
    @Expose
    private String examName;
    @SerializedName("Grade")
    @Expose
    private String grade;
    @SerializedName("Month")
    @Expose
    private String month;
    @SerializedName("SubjectName")
    @Expose
    private String subjectName;
    @SerializedName("ExamLanguage")
    @Expose
    private String examLanguage;

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getExamLanguage() {
        return examLanguage;
    }

    public void setExamLanguage(String examLanguage) {
        this.examLanguage = examLanguage;
    }

}
