package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentResModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error")
    @Expose
    private boolean error;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("StudentID")
    @Expose
    private String StudentID;

    @SerializedName("exam_name")
    @Expose
    private String exam_name;

    @SerializedName("quiz_attempt")
    @Expose
    private String quiz_attempt;

    @SerializedName("examlang")
    @Expose
    private String examlang;

    @SerializedName("ExamAssignmentID")
    @Expose
    private String ExamAssignmentID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getStudentID() {
        return StudentID;
    }

    public void setStudentID(String studentID) {
        StudentID = studentID;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getQuiz_attempt() {
        return quiz_attempt;
    }

    public void setQuiz_attempt(String quiz_attempt) {
        this.quiz_attempt = quiz_attempt;
    }

    public String getExamlang() {
        return examlang;
    }

    public void setExamlang(String examlang) {
        this.examlang = examlang;
    }

    public String getExamAssignmentID() {
        return ExamAssignmentID;
    }

    public void setExamAssignmentID(String examAssignmentID) {
        ExamAssignmentID = examAssignmentID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}