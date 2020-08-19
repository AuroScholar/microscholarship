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
    boolean isUploaded;
    String subject;
    int subjectPos;

    protected AssignmentReqModel(Parcel in) {
        registration_id = in.readString();
        exam_name = in.readString();
        quiz_attempt = in.readString();
        examlang = in.readString();
        eklavvya_exam_id = in.readString();
        imageBytes = in.createByteArray();
        isUploaded = in.readByte() != 0;
        subject = in.readString();
        subjectPos = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(registration_id);
        dest.writeString(exam_name);
        dest.writeString(quiz_attempt);
        dest.writeString(examlang);
        dest.writeString(eklavvya_exam_id);
        dest.writeByteArray(imageBytes);
        dest.writeByte((byte) (isUploaded ? 1 : 0));
        dest.writeString(subject);
        dest.writeInt(subjectPos);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public int getSubjectPos() {
        return subjectPos;
    }

    public void setSubjectPos(int subjectPos) {
        this.subjectPos = subjectPos;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

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


}
