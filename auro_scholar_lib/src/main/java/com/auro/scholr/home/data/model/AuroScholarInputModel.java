package com.auro.scholr.home.data.model;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class AuroScholarInputModel implements Parcelable {

    String mobileNumber;
    Activity activity;
    String studentClass;
    String regitrationSource="";
    String referralLink="";
    String userPartnerId="";
    String partnerSource;
    String language="en";
    boolean applicationLang=false;
    String partnerLogoUrl="";
    String schoolName="";
    String schoolType="";
    String email="";
    String boardType="";
    String gender="";

    protected AuroScholarInputModel(Parcel in) {
        mobileNumber = in.readString();
        studentClass = in.readString();
        regitrationSource = in.readString();
        referralLink = in.readString();
        userPartnerId = in.readString();
        partnerSource = in.readString();
        language = in.readString();
        applicationLang = in.readByte() != 0;
        partnerLogoUrl = in.readString();
        schoolName = in.readString();
        schoolType = in.readString();
        email = in.readString();
        boardType = in.readString();
        gender = in.readString();
        partnerName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNumber);
        dest.writeString(studentClass);
        dest.writeString(regitrationSource);
        dest.writeString(referralLink);
        dest.writeString(userPartnerId);
        dest.writeString(partnerSource);
        dest.writeString(language);
        dest.writeByte((byte) (applicationLang ? 1 : 0));
        dest.writeString(partnerLogoUrl);
        dest.writeString(schoolName);
        dest.writeString(schoolType);
        dest.writeString(email);
        dest.writeString(boardType);
        dest.writeString(gender);
        dest.writeString(partnerName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuroScholarInputModel> CREATOR = new Creator<AuroScholarInputModel>() {
        @Override
        public AuroScholarInputModel createFromParcel(Parcel in) {
            return new AuroScholarInputModel(in);
        }

        @Override
        public AuroScholarInputModel[] newArray(int size) {
            return new AuroScholarInputModel[size];
        }
    };

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    String partnerName="";




    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getPartnerLogoUrl() {
        return partnerLogoUrl;
    }

    public void setPartnerLogoUrl(String partnerLogoUrl) {
        this.partnerLogoUrl = partnerLogoUrl;
    }

    public boolean isApplicationLang() {
        return applicationLang;
    }

    public void setApplicationLang(boolean applicationLang) {
        this.applicationLang = applicationLang;
    }

    public String getPartnerSource() {
        return partnerSource;
    }

    public void setPartnerSource(String partnerSource) {
        this.partnerSource = partnerSource;
    }

    public AuroScholarInputModel() {
    }




    public String getReferralLink() {
        return referralLink;
    }

    public void setReferralLink(String referralLink) {
        this.referralLink = referralLink;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getRegitrationSource() {
        return regitrationSource;
    }

    public void setRegitrationSource(String regitrationSource) {
        this.regitrationSource = regitrationSource;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



    public String getUserPartnerId() {
        return userPartnerId;
    }

    public void setUserPartnerId(String userPartnerId) {
        this.userPartnerId = userPartnerId;
    }

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language; }
}
