package com.auro.scholr.home.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KYCResItemModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("id_name")
    @Expose
    private String id_name;

    protected KYCResItemModel(Parcel in) {
        status = in.readString();
        byte tmpError = in.readByte();
        error = tmpError == 0 ? null : tmpError == 1;
        message = in.readString();
        url = in.readString();
        id_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeByte((byte) (error == null ? 0 : error ? 1 : 2));
        dest.writeString(message);
        dest.writeString(url);
        dest.writeString(id_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<KYCResItemModel> CREATOR = new Creator<KYCResItemModel>() {
        @Override
        public KYCResItemModel createFromParcel(Parcel in) {
            return new KYCResItemModel(in);
        }

        @Override
        public KYCResItemModel[] newArray(int size) {
            return new KYCResItemModel[size];
        }
    };

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}