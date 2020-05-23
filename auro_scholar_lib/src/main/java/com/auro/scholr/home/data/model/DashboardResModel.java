package com.auro.scholr.home.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("error")
    @Expose
    private boolean error ;


    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;

    @SerializedName("auroid")
    @Expose
    private String auroid;

    @SerializedName("scholarid")
    @Expose
    private String scholarid;

    @SerializedName("student_id")
    @Expose
    private String student_id;

    @SerializedName("student_name")
    @Expose
    private String student_name;

    @SerializedName("email_id")
    @Expose
    private String email_id;

    @SerializedName("studentclass")
    @Expose
    private String studentclass;

    @SerializedName("walletbalance")
    @Expose
    private String walletbalance;

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("registrationdate")
    @Expose
    private String registrationdate;

    @SerializedName("regitration_source")
    @Expose
    private String regitration_source;

    @SerializedName("campaign")
    @Expose
    private String campaign;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("school_type")
    @Expose
    private String school_type;

    @SerializedName("board_type")
    @Expose
    private String board_type;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("SubjectName")
    @Expose
    private String subjectName;

    @SerializedName("Month")
    @Expose
    private String month;

    @SerializedName("idfront")
    @Expose
    private String idfront;
    @SerializedName("idback")
    @Expose
    private String idback;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("schoolid")
    @Expose
    private String schoolid;
    @SerializedName("quiz")
    @Expose
    private List<QuizResModel> quiz = null;

    @SerializedName("modify")
    @Expose
    private boolean modify;

    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("is_kyc_uploaded")
    @Expose
    private String is_kyc_uploaded;

    @SerializedName("is_kyc_verified")
    @Expose
    private String is_kyc_verified;

    @SerializedName("is_payment_lastmonth")
    @Expose
    private String is_payment_lastmonth;


    protected DashboardResModel(Parcel in) {
        status = in.readString();
        error = in.readByte() != 0;
        phonenumber = in.readString();
        auroid = in.readString();
        scholarid = in.readString();
        student_id = in.readString();
        student_name = in.readString();
        email_id = in.readString();
        studentclass = in.readString();
        walletbalance = in.readString();
        currency = in.readString();
        registrationdate = in.readString();
        regitration_source = in.readString();
        campaign = in.readString();
        gender = in.readString();
        school_type = in.readString();
        board_type = in.readString();
        language = in.readString();
        subjectName = in.readString();
        month = in.readString();
        idfront = in.readString();
        idback = in.readString();
        photo = in.readString();
        schoolid = in.readString();
        quiz = in.createTypedArrayList(QuizResModel.CREATOR);
        modify = in.readByte() != 0;
        message = in.readString();
        is_kyc_uploaded = in.readString();
        is_kyc_verified = in.readString();
        is_payment_lastmonth = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeByte((byte) (error ? 1 : 0));
        dest.writeString(phonenumber);
        dest.writeString(auroid);
        dest.writeString(scholarid);
        dest.writeString(student_id);
        dest.writeString(student_name);
        dest.writeString(email_id);
        dest.writeString(studentclass);
        dest.writeString(walletbalance);
        dest.writeString(currency);
        dest.writeString(registrationdate);
        dest.writeString(regitration_source);
        dest.writeString(campaign);
        dest.writeString(gender);
        dest.writeString(school_type);
        dest.writeString(board_type);
        dest.writeString(language);
        dest.writeString(subjectName);
        dest.writeString(month);
        dest.writeString(idfront);
        dest.writeString(idback);
        dest.writeString(photo);
        dest.writeString(schoolid);
        dest.writeTypedList(quiz);
        dest.writeByte((byte) (modify ? 1 : 0));
        dest.writeString(message);
        dest.writeString(is_kyc_uploaded);
        dest.writeString(is_kyc_verified);
        dest.writeString(is_payment_lastmonth);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DashboardResModel> CREATOR = new Creator<DashboardResModel>() {
        @Override
        public DashboardResModel createFromParcel(Parcel in) {
            return new DashboardResModel(in);
        }

        @Override
        public DashboardResModel[] newArray(int size) {
            return new DashboardResModel[size];
        }
    };

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getIs_kyc_uploaded() {
        return is_kyc_uploaded;
    }

    public void setIs_kyc_uploaded(String is_kyc_uploaded) {
        this.is_kyc_uploaded = is_kyc_uploaded;
    }

    public String getIs_kyc_verified() {
        return is_kyc_verified;
    }

    public void setIs_kyc_verified(String is_kyc_verified) {
        this.is_kyc_verified = is_kyc_verified;
    }

    public String getIs_payment_lastmonth() {
        return is_payment_lastmonth;
    }

    public void setIs_payment_lastmonth(String is_payment_lastmonth) {
        this.is_payment_lastmonth = is_payment_lastmonth;
    }

    public String getAuroid() {
        return auroid;
    }

    public void setAuroid(String auroid) {
        this.auroid = auroid;
    }

    public String getScholarid() {
        return scholarid;
    }

    public void setScholarid(String scholarid) {
        this.scholarid = scholarid;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getStudentclass() {
        return studentclass;
    }

    public void setStudentclass(String studentclass) {
        this.studentclass = studentclass;
    }

    public String getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(String registrationdate) {
        this.registrationdate = registrationdate;
    }

    public String getRegitration_source() {
        return regitration_source;
    }

    public void setRegitration_source(String regitration_source) {
        this.regitration_source = regitration_source;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchool_type() {
        return school_type;
    }

    public void setSchool_type(String school_type) {
        this.school_type = school_type;
    }

    public String getBoard_type() {
        return board_type;
    }

    public void setBoard_type(String board_type) {
        this.board_type = board_type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getWalletbalance() {
        return walletbalance;
    }

    public void setWalletbalance(String walletbalance) {
        this.walletbalance = walletbalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIdfront() {
        return idfront;
    }

    public void setIdfront(String idfront) {
        this.idfront = idfront;
    }

    public String getIdback() {
        return idback;
    }

    public void setIdback(String idback) {
        this.idback = idback;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public List<QuizResModel> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<QuizResModel> quiz) {
        this.quiz = quiz;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}