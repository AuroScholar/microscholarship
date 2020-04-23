package com.auro.scholr.home.data.model;



public class AssignmentReqModel {

    String registration_id;
    String exam_name;
    String quiz_attempt;
    String examlang;

    public AssignmentReqModel() {

    }

    public String getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(String registration_id) {
        this.registration_id = registration_id;
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
}
