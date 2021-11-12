package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignmentResModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private boolean error;
    @SerializedName("StudentID")
    @Expose
    private String studentID;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("quiz_attempt")
    @Expose
    private String quizAttempt;
    @SerializedName("examlang")
    @Expose
    private String examlang;
    @SerializedName("time_interval")
    @Expose
    private String timeInterval;
    @SerializedName("ExamAssignmentID")
    @Expose
    private String examAssignmentID;
    @SerializedName("exam_id")
    @Expose
    private String examId;
    @SerializedName("img_normal_path")
    @Expose
    private String imgNormalPath;
    @SerializedName("img_path")
    @Expose
    private String imgPath;
    @SerializedName("quiz_id")
    @Expose
    private String quizId;

    @SerializedName("message")
    @Expose
    private String message;


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
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getQuizAttempt() {
        return quizAttempt;
    }

    public void setQuizAttempt(String quizAttempt) {
        this.quizAttempt = quizAttempt;
    }

    public String getExamlang() {
        return examlang;
    }

    public void setExamlang(String examlang) {
        this.examlang = examlang;
    }

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getExamAssignmentID() {
        return examAssignmentID;
    }

    public void setExamAssignmentID(String examAssignmentID) {
        this.examAssignmentID = examAssignmentID;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}