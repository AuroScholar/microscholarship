package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchStudentPrefResModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("is_set")
    @Expose
    private Boolean isSet;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("preference")
    @Expose
    private List<String> preference = null;
    @SerializedName("subjects")
    @Expose
    private List<CategorySubjectResModel> subjects = null;


    protected FetchStudentPrefResModel(Parcel in) {
        status = in.readString();
        byte tmpError = in.readByte();
        error = tmpError == 0 ? null : tmpError == 1;
        byte tmpIsSet = in.readByte();
        isSet = tmpIsSet == 0 ? null : tmpIsSet == 1;
        mobileNo = in.readString();
        message = in.readString();
        preference = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeByte((byte) (error == null ? 0 : error ? 1 : 2));
        dest.writeByte((byte) (isSet == null ? 0 : isSet ? 1 : 2));
        dest.writeString(mobileNo);
        dest.writeString(message);
        dest.writeStringList(preference);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FetchStudentPrefResModel> CREATOR = new Creator<FetchStudentPrefResModel>() {
        @Override
        public FetchStudentPrefResModel createFromParcel(Parcel in) {
            return new FetchStudentPrefResModel(in);
        }

        @Override
        public FetchStudentPrefResModel[] newArray(int size) {
            return new FetchStudentPrefResModel[size];
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

    public Boolean getIsSet() {
        return isSet;
    }

    public void setIsSet(Boolean isSet) {
        this.isSet = isSet;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getPreference() {
        return preference;
    }

    public void setPreference(List<String> preference) {
        this.preference = preference;
    }

    public List<CategorySubjectResModel> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<CategorySubjectResModel> subjects) {
        this.subjects = subjects;
    }
}