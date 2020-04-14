package com.auro.scholr.home.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardResModel implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("walletbalance")
    @Expose
    private Integer walletbalance;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("idfront")
    @Expose
    private String idfront;
    @SerializedName("idback")
    @Expose
    private String idback;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("schoolid")
    @Expose
    private String schoolid;
    @SerializedName("quiz")
    @Expose
    private List<QuizResModel> quiz = null;

    protected DashboardResModel(Parcel in) {
        status = in.readString();
        phonenumber = in.readString();
        if (in.readByte() == 0) {
            walletbalance = null;
        } else {
            walletbalance = in.readInt();
        }
        currency = in.readString();
        idfront = in.readString();
        idback = in.readString();
        photo = in.readString();
        schoolid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(phonenumber);
        if (walletbalance == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(walletbalance);
        }
        dest.writeString(currency);
        dest.writeString(idfront);
        dest.writeString(idback);
        dest.writeString(photo);
        dest.writeString(schoolid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DashboardResModel> CREATOR = new Creator<DashboardResModel>() {
        @Override
        public DashboardResModel createFromParcel(Parcel in) {
            return new DashboardResModel(in);
        }

        @Override
        public DashboardResModel[] newArray(int size) {
            return new DashboardResModel[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public Integer getWalletbalance() {
        return walletbalance;
    }

    public void setWalletbalance(Integer walletbalance) {
        this.walletbalance = walletbalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIdfront() {
        return idfront;
    }

    public void setIdfront(String idfront) {
        this.idfront = idfront;
    }

    public String getIdback() {
        return idback;
    }

    public void setIdback(String idback) {
        this.idback = idback;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public List<QuizResModel> getQuiz() {
        return quiz;
    }

    public void setQuiz(List<QuizResModel> quiz) {
        this.quiz = quiz;
    }

}