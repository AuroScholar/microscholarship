package com.auro.scholr.teacher.data.model.request;

public class TeacherReqModel {

    private String mobile_no;
    private String teacher_name;
    private String teacher_email;
    private String school_name;
    private String state_id;
    private String district_id;
    private String teacher_class;
    private String teacher_subject;
    private  String UTM_link;
    private String ip_address;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getTeacher_email() {
        return teacher_email;
    }

    public void setTeacher_email(String teacher_email) {
        this.teacher_email = teacher_email;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getTeacher_class() {
        return teacher_class;
    }

    public void setTeacher_class(String teacher_class) {
        this.teacher_class = teacher_class;
    }

    public String getTeacher_subject() {
        return teacher_subject;
    }

    public void setTeacher_subject(String teacher_subject) {
        this.teacher_subject = teacher_subject;
    }

    public String getUTM_link() {
        return UTM_link;
    }

    public void setUTM_link(String UTM_link) {
        this.UTM_link = UTM_link;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }
}
