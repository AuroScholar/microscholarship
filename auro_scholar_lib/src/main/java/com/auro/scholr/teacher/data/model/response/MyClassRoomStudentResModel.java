package com.auro.scholr.teacher.data.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyClassRoomStudentResModel implements Parcelable {

    @SerializedName("student_name")
    @Expose
    private String sudentName;
    @SerializedName("student_mobile")
    @Expose
    private String sudentMobile;
    @SerializedName("total_score")
    @Expose
    private String totalScore;
    @SerializedName("student_photo")
    @Expose
    private String studentPhoto;
    @SerializedName("registration_date")
    @Expose
    private String registration_date;


    @SerializedName("invite_date")
    @Expose
    private String invite_date;

    int year;
    int monthNumber;

    protected MyClassRoomStudentResModel(Parcel in) {
        sudentName = in.readString();
        sudentMobile = in.readString();
        totalScore = in.readString();
        studentPhoto = in.readString();
        registration_date = in.readString();
        invite_date = in.readString();
        year = in.readInt();
        monthNumber = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sudentName);
        dest.writeString(sudentMobile);
        dest.writeString(totalScore);
        dest.writeString(studentPhoto);
        dest.writeString(registration_date);
        dest.writeString(invite_date);
        dest.writeInt(year);
        dest.writeInt(monthNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyClassRoomStudentResModel> CREATOR = new Creator<MyClassRoomStudentResModel>() {
        @Override
        public MyClassRoomStudentResModel createFromParcel(Parcel in) {
            return new MyClassRoomStudentResModel(in);
        }

        @Override
        public MyClassRoomStudentResModel[] newArray(int size) {
            return new MyClassRoomStudentResModel[size];
        }
    };

    public String getInvite_date() {
        return invite_date;
    }

    public void setInvite_date(String invite_date) {
        this.invite_date = invite_date;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        this.monthNumber = monthNumber;
    }

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
    }

    public String getSudentName() {
        return sudentName;
    }

    public void setSudentName(String sudentName) {
        this.sudentName = sudentName;
    }

    public String getSudentMobile() {
        return sudentMobile;
    }

    public void setSudentMobile(String sudentMobile) {
        this.sudentMobile = sudentMobile;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getStudentPhoto() {
        return studentPhoto;
    }

    public void setStudentPhoto(String studentPhoto) {
        this.studentPhoto = studentPhoto;
    }

}
