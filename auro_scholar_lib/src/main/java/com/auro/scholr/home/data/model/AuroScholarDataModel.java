package com.auro.scholr.home.data.model;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class AuroScholarDataModel implements Parcelable {

    String mobileNumber;
    Activity activity;
    int fragmentContainerUiId;


    protected AuroScholarDataModel(Parcel in) {
        mobileNumber = in.readString();
        fragmentContainerUiId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNumber);
        dest.writeInt(fragmentContainerUiId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuroScholarDataModel> CREATOR = new Creator<AuroScholarDataModel>() {
        @Override
        public AuroScholarDataModel createFromParcel(Parcel in) {
            return new AuroScholarDataModel(in);
        }

        @Override
        public AuroScholarDataModel[] newArray(int size) {
            return new AuroScholarDataModel[size];
        }
    };

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getFragmentContainerUiId() {
        return fragmentContainerUiId;
    }

    public void setFragmentContainerUiId(int fragmentContainerUiId) {
        this.fragmentContainerUiId = fragmentContainerUiId;
    }
}
