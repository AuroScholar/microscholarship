package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectPreferenceResModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("subjects")
    @Expose
    private List<CategorySubjectResModel> subjects = null;

    protected SubjectPreferenceResModel(Parcel in) {
        status = in.readString();
        byte tmpError = in.readByte();
        error = tmpError == 0 ? null : tmpError == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeByte((byte) (error == null ? 0 : error ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubjectPreferenceResModel> CREATOR = new Creator<SubjectPreferenceResModel>() {
        @Override
        public SubjectPreferenceResModel createFromParcel(Parcel in) {
            return new SubjectPreferenceResModel(in);
        }

        @Override
        public SubjectPreferenceResModel[] newArray(int size) {
            return new SubjectPreferenceResModel[size];
        }
    };

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

    public List<CategorySubjectResModel> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<CategorySubjectResModel> subjects) {
        this.subjects = subjects;
    }
}

