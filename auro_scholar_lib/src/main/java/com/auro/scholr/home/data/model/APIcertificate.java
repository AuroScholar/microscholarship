package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class APIcertificate implements Parcelable {


    @SerializedName("isSelect")
    @Expose
    boolean isSelect;

    @SerializedName("exam_month")
    @Expose
    private String examMonth = "";
    @SerializedName("certificate_image")
    @Expose
    private String certificateImage = "";
    @SerializedName("certificate_file")
    @Expose
    private String certificateFile = "";

    protected APIcertificate(Parcel in) {
        isSelect = in.readByte() != 0;
        examMonth = in.readString();
        certificateImage = in.readString();
        certificateFile = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeString(examMonth);
        dest.writeString(certificateImage);
        dest.writeString(certificateFile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<APIcertificate> CREATOR = new Creator<APIcertificate>() {
        @Override
        public APIcertificate createFromParcel(Parcel in) {
            return new APIcertificate(in);
        }

        @Override
        public APIcertificate[] newArray(int size) {
            return new APIcertificate[size];
        }
    };

    public String getExamMonth() {
        return examMonth;
    }

    public void setExamMonth(String examMonth) {
        this.examMonth = examMonth;
    }

    public String getCertificateImage() {
        return certificateImage;
    }

    public void setCertificateImage(String certificateImage) {
        this.certificateImage = certificateImage;
    }

    public String getCertificateFile() {
        return certificateFile;
    }

    public void setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

}
