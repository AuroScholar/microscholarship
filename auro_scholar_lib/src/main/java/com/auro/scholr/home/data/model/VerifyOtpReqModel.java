package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpReqModel implements Parcelable {

    @SerializedName("mobile_no")
    @Expose
    String mobileNumber;

    @SerializedName("otp_val")
    @Expose
    String otpVerify;

    @SerializedName("regitration_source")
    @Expose
    String resgistrationSource;

    @SerializedName("user_type")
    @Expose
    int userType;

    @SerializedName("device_token")
    @Expose
    String deviceToken;

    public VerifyOtpReqModel() {

    }

    protected VerifyOtpReqModel(Parcel in) {
        mobileNumber = in.readString();
        otpVerify = in.readString();
        resgistrationSource = in.readString();
        userType = in.readInt();
        deviceToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNumber);
        dest.writeString(otpVerify);
        dest.writeString(resgistrationSource);
        dest.writeInt(userType);
        dest.writeString(deviceToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VerifyOtpReqModel> CREATOR = new Creator<VerifyOtpReqModel>() {
        @Override
        public VerifyOtpReqModel createFromParcel(Parcel in) {
            return new VerifyOtpReqModel(in);
        }

        @Override
        public VerifyOtpReqModel[] newArray(int size) {
            return new VerifyOtpReqModel[size];
        }
    };

    public String getResgistrationSource() {
        return resgistrationSource;
    }

    public void setResgistrationSource(String resgistrationSource) {
        this.resgistrationSource = resgistrationSource;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtpVerify() {
        return otpVerify;
    }

    public void setOtpVerify(String otpVerify) {
        this.otpVerify = otpVerify;
    }
}
