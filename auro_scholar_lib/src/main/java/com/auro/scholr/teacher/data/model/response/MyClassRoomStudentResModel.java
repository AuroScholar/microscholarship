package com.auro.scholr.teacher.data.model.response;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyClassRoomStudentResModel  implements Parcelable {

    @SerializedName("sudent_name")
    @Expose
    private String sudentName;
    @SerializedName("sudent_mobile")
    @Expose
    private String sudentMobile;
    @SerializedName("total_score")
    @Expose
    private String totalScore;
    @SerializedName("student_photo")
    @Expose
    private String studentPhoto;

    protected MyClassRoomStudentResModel(Parcel in) {
        sudentName = in.readString();
        sudentMobile = in.readString();
        totalScore = in.readString();
        studentPhoto = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sudentName);
        dest.writeString(sudentMobile);
        dest.writeString(totalScore);
        dest.writeString(studentPhoto);
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
