package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class KYCInputModel implements Parcelable {

    String aadhar_phone = "";
    String aadhar_dob = "";
    String aadhar_no = "";
    String aadhar_name = "";
    String school_phone = "";
    String school_dob = "";
    String user_phone;

    public KYCInputModel() {

    }


    protected KYCInputModel(Parcel in) {
        aadhar_phone = in.readString();
        aadhar_dob = in.readString();
        aadhar_no = in.readString();
        aadhar_name = in.readString();
        school_phone = in.readString();
        school_dob = in.readString();
        user_phone = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(aadhar_phone);
        dest.writeString(aadhar_dob);
        dest.writeString(aadhar_no);
        dest.writeString(aadhar_name);
        dest.writeString(school_phone);
        dest.writeString(school_dob);
        dest.writeString(user_phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KYCInputModel> CREATOR = new Creator<KYCInputModel>() {
        @Override
        public KYCInputModel createFromParcel(Parcel in) {
            return new KYCInputModel(in);
        }

        @Override
        public KYCInputModel[] newArray(int size) {
            return new KYCInputModel[size];
        }
    };

    public String getAadhar_phone() {
        return aadhar_phone;
    }

    public void setAadhar_phone(String aadhar_phone) {
        this.aadhar_phone = aadhar_phone;
    }

    public String getAadhar_dob() {
        return aadhar_dob;
    }

    public void setAadhar_dob(String aadhar_dob) {
        this.aadhar_dob = aadhar_dob;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getAadhar_name() {
        return aadhar_name;
    }

    public void setAadhar_name(String aadhar_name) {
        this.aadhar_name = aadhar_name;
    }

    public String getSchool_phone() {
        return school_phone;
    }

    public void setSchool_phone(String school_phone) {
        this.school_phone = school_phone;
    }

    public String getSchool_dob() {
        return school_dob;
    }

    public void setSchool_dob(String school_dob) {
        this.school_dob = school_dob;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }
}
