package com.auro.scholr.home.data.model;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

public class AuroScholarDataModel implements Parcelable {

    String mobileNumber;
    Activity activity;
    int fragmentContainerUiId;
    String studentClass;
    String regitrationSource="";

    public AuroScholarDataModel() {

    }


    protected AuroScholarDataModel(Parcel in) {
        mobileNumber = in.readString();
        fragmentContainerUiId = in.readInt();
        studentClass = in.readString();
        regitrationSource = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNumber);
        dest.writeInt(fragmentContainerUiId);
        dest.writeString(studentClass);
        dest.writeString(regitrationSource);
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



    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getRegitrationSource() {
        return regitrationSource;
    }

    public void setRegitrationSource(String regitrationSource) {
        this.regitrationSource = regitrationSource;
    }

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
