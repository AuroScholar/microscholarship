package com.auro.scholr.home.data.model.passportmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PassportQuizDetailModel {

    List<PassportQuizGridModel> passportQuizGridModelList;

    @SerializedName("quiz_name")
    @Expose
    private String quizName;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("registration_id")
    @Expose
    private String registrationId;
    @SerializedName("student_id")
    @Expose
    private String studentId;
    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("quiz_attempt")
    @Expose
    private String quizAttempt;
    @SerializedName("exam_month")
    @Expose
    private String examMonth;
    @SerializedName("eklavvya_exam_id")
    @Expose
    private String eklavvyaExamId;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("question_details")
    @Expose
    private Object questionDetails;
    @SerializedName("exam_start")
    @Expose
    private Object examStart;
    @SerializedName("exam_end")
    @Expose
    private Object examEnd;
    @SerializedName("question_attemped")
    @Expose
    private String questionAttemped;
    @SerializedName("score")
    @Expose
    private String score;
    @SerializedName("attempted")
    @Expose
    private String attempted;
    @SerializedName("exam_compelete")
    @Expose
    private String examCompelete;
    @SerializedName("complete_by")
    @Expose
    private String completeBy;
    @SerializedName("exam_face_img")
    @Expose
    private Object examFaceImg;
    @SerializedName("face_checked")
    @Expose
    private String faceChecked;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("is_verified")
    @Expose
    private String isVerified;

    @SerializedName("amount_status")
    @Expose
    private String amount_status;


    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getExamMonth() {
        return examMonth;
    }

    public void setExamMonth(String examMonth) {
        this.examMonth = examMonth;
    }

    public String getEklavvyaExamId() {
        return eklavvyaExamId;
    }

    public void setEklavvyaExamId(String eklavvyaExamId) {
        this.eklavvyaExamId = eklavvyaExamId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Object getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestionDetails(Object questionDetails) {
        this.questionDetails = questionDetails;
    }

    public Object getExamStart() {
        return examStart;
    }

    public void setExamStart(Object examStart) {
        this.examStart = examStart;
    }

    public Object getExamEnd() {
        return examEnd;
    }

    public void setExamEnd(Object examEnd) {
        this.examEnd = examEnd;
    }

    public String getQuestionAttemped() {
        return questionAttemped;
    }

    public void setQuestionAttemped(String questionAttemped) {
        this.questionAttemped = questionAttemped;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAttempted() {
        return attempted;
    }

    public void setAttempted(String attempted) {
        this.attempted = attempted;
    }

    public String getExamCompelete() {
        return examCompelete;
    }

    public void setExamCompelete(String examCompelete) {
        this.examCompelete = examCompelete;
    }

    public String getCompleteBy() {
        return completeBy;
    }

    public void setCompleteBy(String completeBy) {
        this.completeBy = completeBy;
    }

    public Object getExamFaceImg() {
        return examFaceImg;
    }

    public void setExamFaceImg(Object examFaceImg) {
        this.examFaceImg = examFaceImg;
    }

    public String getFaceChecked() {
        return faceChecked;
    }

    public void setFaceChecked(String faceChecked) {
        this.faceChecked = faceChecked;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(String isVerified) {
        this.isVerified = isVerified;
    }


    public List<PassportQuizGridModel> getPassportQuizGridModelList() {
        return passportQuizGridModelList;
    }

    public void setPassportQuizGridModelList(List<PassportQuizGridModel> passportQuizGridModelList) {
        this.passportQuizGridModelList = passportQuizGridModelList;
    }

    public String getAmount_status() {
        return amount_status;
    }

    public void setAmount_status(String amount_status) {
        this.amount_status = amount_status;
    }
}
