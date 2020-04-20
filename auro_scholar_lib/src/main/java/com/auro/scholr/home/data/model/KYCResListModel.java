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

    protected KYCResListModel(Parcel in) {
        list = in.createTypedArrayList(KYCResItemModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
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

    public List<KYCResItemModel> getList() {
        return list;
    }

    public void setList(List<KYCResItemModel> list) {
        this.list = list;
    }
}