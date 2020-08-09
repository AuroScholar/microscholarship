package com.auro.scholr.home.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubjectResModel implements Parcelable {

    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("chapter")
    @Expose
    private List<QuizResModel> chapter = null;
    @SerializedName("quiz_won")
    @Expose
    private int quizWonAmount;

    @SerializedName("quizOpen")
    @Expose
    private boolean quizOpen;

    protected SubjectResModel(Parcel in) {
        subject = in.readString();
        description = in.readString();
        chapter = in.createTypedArrayList(QuizResModel.CREATOR);
        quizWonAmount = in.readInt();
        quizOpen = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subject);
        dest.writeString(description);
        dest.writeTypedList(chapter);
        dest.writeInt(quizWonAmount);
        dest.writeByte((byte) (quizOpen ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SubjectResModel> CREATOR = new Creator<SubjectResModel>() {
        @Override
        public SubjectResModel createFromParcel(Parcel in) {
            return new SubjectResModel(in);
        }

        @Override
        public SubjectResModel[] newArray(int size) {
            return new SubjectResModel[size];
        }
    };

    public boolean isQuizOpen() {
        return quizOpen;
    }

    public void setQuizOpen(boolean quizOpen) {
        this.quizOpen = quizOpen;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuizResModel> getChapter() {
        return chapter;
    }

    public void setChapter(List<QuizResModel> chapter) {
        this.chapter = chapter;
    }

    public int getQuizWonAmount() {
        return quizWonAmount;
    }

    public void setQuizWonAmount(int quizWonAmount) {
        this.quizWonAmount = quizWonAmount;
    }
}