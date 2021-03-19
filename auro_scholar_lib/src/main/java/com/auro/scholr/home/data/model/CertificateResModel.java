package com.auro.scholr.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CertificateResModel {

    @SerializedName("pdfPath")
    @Expose
    String pdfPath = "";

    @SerializedName("isSelect")
    @Expose
    boolean isSelect;

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("registration_id")
    @Expose
    private String registrationId;
    @SerializedName("APIcertificate")
    @Expose
    private List<APIcertificate> aPIcertificate = null;

    @SerializedName("student_name")
    @Expose
    private String  studentName ;

    @SerializedName("student_name_verified")
    @Expose
    private String  studentNameVerified ;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public List<APIcertificate> getAPIcertificate() {
        return aPIcertificate;
    }

    public void setAPIcertificate(List<APIcertificate> aPIcertificate) {
        this.aPIcertificate = aPIcertificate;
    }


    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentNameVerified() {
        return studentNameVerified;
    }

    public void setStudentNameVerified(String studentNameVerified) {
        this.studentNameVerified = studentNameVerified;
    }
}
