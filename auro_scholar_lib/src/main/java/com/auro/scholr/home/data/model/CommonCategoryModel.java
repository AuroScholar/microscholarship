package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CommonCategoryModel implements Parcelable {

    private String title;

    public CommonCategoryModel(String title) {
        this.title = title;
    }

    protected CommonCategoryModel(Parcel in) {
        title = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonCategoryModel> CREATOR = new Creator<CommonCategoryModel>() {
        @Override
        public CommonCategoryModel createFromParcel(Parcel in) {
            return new CommonCategoryModel(in);
        }

        @Override
        public CommonCategoryModel[] newArray(int size) {
            return new CommonCategoryModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
