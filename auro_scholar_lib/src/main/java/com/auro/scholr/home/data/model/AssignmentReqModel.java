package com.auro.scholr.home.data.model;


import android.os.Parcel;
import android.os.Parcelable;

public class AssignmentReqModel implements Parcelable {

    String registration_id;
    String exam_name;
    String quiz_attempt;
    String examlang;
    String eklavvya_exam_id;
    byte[] imageBytes;

    protected AssignmentReqModel(Parcel in) {
        registration_id = in.readString();
        exam_name = in.readString();
        quiz_attempt = in.readString();
        examlang = in.readString();
        eklavvya_exam_id = in.readString();
        imageBytes = in.createByteArray();
    }

    public static final Creator<AssignmentReqModel> CREATOR = new Creator<AssignmentReqModel>() {
        @Override
        public AssignmentReqModel createFromParcel(Parcel in) {
            return new AssignmentReqModel(in);
        }

        @Override
        public AssignmentReqModel[] newArray(int size) {
            return new AssignmentReqModel[size];
        }
    };

    public String getEklavvya_exam_id() {
        return eklavvya_exam_id;
    }

    public void setEklavvya_exam_id(String eklavvya_exam_id) {
        this.eklavvya_exam_id = eklavvya_exam_id;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(registration_id);
        parcel.writeString(exam_name);
        parcel.writeString(quiz_attempt);
        parcel.writeString(examlang);
        parcel.writeString(eklavvya_exam_id);
        parcel.writeByteArray(imageBytes);
    }
}
