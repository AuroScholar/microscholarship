package com.auro.scholr.core.common;

import android.os.Parcel;
import android.os.Parcelable;

public class ValidationModel implements Parcelable {

    boolean status;
    String message;

    public ValidationModel(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    protected ValidationModel(Parcel in) {
        status = in.readByte() != 0;
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (status ? 1 : 0));
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ValidationModel> CREATOR = new Creator<ValidationModel>() {
        @Override
        public ValidationModel createFromParcel(Parcel in) {
            return new ValidationModel(in);
        }

        @Override
        public ValidationModel[] newArray(int size) {
            return new ValidationModel[size];
        }
    };

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
