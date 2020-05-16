package com.auro.scholr.home.data.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.auro.scholr.core.common.CommonCallBackListner;

public class CustomSnackBarModel implements Parcelable {

    View view;
    Context context;
    int status;
    CommonCallBackListner commonCallBackListner;

    protected CustomSnackBarModel(Parcel in) {
        status = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CustomSnackBarModel> CREATOR = new Creator<CustomSnackBarModel>() {
        @Override
        public CustomSnackBarModel createFromParcel(Parcel in) {
            return new CustomSnackBarModel(in);
        }

        @Override
        public CustomSnackBarModel[] newArray(int size) {
            return new CustomSnackBarModel[size];
        }
    };

    public CommonCallBackListner getCommonCallBackListner() {
        return commonCallBackListner;
    }

    public void setCommonCallBackListner(CommonCallBackListner commonCallBackListner) {
        this.commonCallBackListner = commonCallBackListner;
    }

    public CustomSnackBarModel() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
