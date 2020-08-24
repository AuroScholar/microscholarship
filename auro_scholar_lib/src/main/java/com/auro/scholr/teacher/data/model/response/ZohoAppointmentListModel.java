package com.auro.scholr.teacher.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * User: dharmaraj
 * Date: 29/7/20
 * Time: 11:59 AM
 */
public class ZohoAppointmentListModel implements Parcelable {
    @Expose
    @SerializedName("status")
    public String status;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("success")
    public boolean success;
    @Expose
    @SerializedName("data")
    public List<String> data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.message);
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.data);
    }

    public ZohoAppointmentListModel() {
    }

    protected ZohoAppointmentListModel(Parcel in) {
        this.status = in.readString();
        this.message = in.readString();
        this.success = in.readByte() != 0;
        this.data = in.createStringArrayList();
    }

    public static final Creator<ZohoAppointmentListModel> CREATOR = new Creator<ZohoAppointmentListModel>() {
        @Override
        public ZohoAppointmentListModel createFromParcel(Parcel source) {
            return new ZohoAppointmentListModel(source);
        }

        @Override
        public ZohoAppointmentListModel[] newArray(int size) {
            return new ZohoAppointmentListModel[size];
        }
    };
}





