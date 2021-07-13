package com.auro.scholr.home.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizResModel implements Parcelable {

    @SerializedName("number")
    @Expose
    private Integer number;

    @SerializedName("attempt")
    @Expose
    private Integer attempt;

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("scorepoints")
    @Expose
    private Integer scorepoints;
    @SerializedName("totalpoints")
    @Expose
    private Integer totalpoints;
    @SerializedName("scholarshipamount")
    @Expose
    private String scholarshipamount;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("scoreallpoints")
    @Expose
    private String scoreallpoints;

    @SerializedName("wonstatus")
    @Expose
    private boolean wonStatus;

    @SerializedName("subject")
    @Expose
    private String subjectName;


    @SerializedName("subjectPos")
    @Expose
    private int subjectPos;


    @SerializedName("subjectName")
    @Expose
    private String coreSubjectName;


    protected QuizResModel(Parcel in) {
        if (in.readByte() == 0) {
            number = null;
        } else {
            number = in.readInt();
        }
        if (in.readByte() == 0) {
            attempt = null;
        } else {
            attempt = in.readInt();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            scorepoints = null;
        } else {
            scorepoints = in.readInt();
        }
        if (in.readByte() == 0) {
            totalpoints = null;
        } else {
            totalpoints = in.readInt();
        }
        scholarshipamount = in.readString();
        status = in.readString();
        scoreallpoints = in.readString();
        wonStatus = in.readByte() != 0;
        subjectName = in.readString();
        subjectPos = in.readInt();
        coreSubjectName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (number == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(number);
        }
        if (attempt == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(attempt);
        }
        dest.writeString(name);
        if (scorepoints == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(scorepoints);
        }
        if (totalpoints == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalpoints);
        }
        dest.writeString(scholarshipamount);
        dest.writeString(status);
        dest.writeString(scoreallpoints);
        dest.writeByte((byte) (wonStatus ? 1 : 0));
        dest.writeString(subjectName);
        dest.writeInt(subjectPos);
        dest.writeString(coreSubjectName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuizResModel> CREATOR = new Creator<QuizResModel>() {
        @Override
        public QuizResModel createFromParcel(Parcel in) {
            return new QuizResModel(in);
        }

        @Override
        public QuizResModel[] newArray(int size) {
            return new QuizResModel[size];
        }
    };

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public boolean isWonStatus() {
        return wonStatus;
    }

    public void setWonStatus(boolean wonStatus) {
        this.wonStatus = wonStatus;
    }

    public Integer getAttempt() {
        return attempt;
    }

    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    public QuizResModel() {

    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScorepoints() {
        return scorepoints;
    }

    public void setScorepoints(Integer scorepoints) {
        this.scorepoints = scorepoints;
    }

    public Integer getTotalpoints() {
        return totalpoints;
    }

    public void setTotalpoints(Integer totalpoints) {
        this.totalpoints = totalpoints;
    }

    public String getScholarshipamount() {
        return scholarshipamount;
    }

    public void setScholarshipamount(String scholarshipamount) {
        this.scholarshipamount = scholarshipamount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSubjectPos() {
        return subjectPos;
    }

    public void setSubjectPos(int subjectPos) {
        this.subjectPos = subjectPos;
    }

    public String getScoreallpoints() {
        return scoreallpoints;
    }

    public void setScoreallpoints(String scoreallpoints) {
        this.scoreallpoints = scoreallpoints;
    }

    public String getCoreSubjectName() {
        return coreSubjectName;
    }

    public void setCoreSubjectName(String coreSubjectName) {
        this.coreSubjectName = coreSubjectName;
    }
}