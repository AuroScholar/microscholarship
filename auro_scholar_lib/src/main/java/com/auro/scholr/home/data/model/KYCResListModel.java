package com.auro.scholr.home.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KYCResListModel implements Parcelable {

    @SerializedName("kyc_list")
    @Expose
    List<KYCResItemModel> list;

    @SerializedName("error")
    @Expose
    boolean error;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("status")
    @Expose
    String status;


    protected KYCResListModel(Parcel in) {
        list = in.createTypedArrayList(KYCResItemModel.CREATOR);
        error = in.readByte() != 0;
        message = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
        dest.writeByte((byte) (error ? 1 : 0));
        dest.writeString(message);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KYCResListModel> CREATOR = new Creator<KYCResListModel>() {
        @Override
        public KYCResListModel createFromParcel(Parcel in) {
            return new KYCResListModel(in);
        }

        @Override
        public KYCResListModel[] newArray(int size) {
            return new KYCResListModel[size];
        }
    };

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<KYCResItemModel> getList() {
        return list;
    }

    public void setList(List<KYCResItemModel> list) {
        this.list = list;
    }
}