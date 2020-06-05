package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpgradeResModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("Message")
    @Expose
    private String message;

    protected UpgradeResModel(Parcel in) {
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

    public static final Creator<UpgradeResModel> CREATOR = new Creator<UpgradeResModel>() {
        @Override
        public UpgradeResModel createFromParcel(Parcel in) {
            return new UpgradeResModel(in);
        }

        @Override
        public UpgradeResModel[] newArray(int size) {
            return new UpgradeResModel[size];
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
