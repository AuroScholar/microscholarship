package com.auro.scholr.home.data.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "faq_ques")
public class CategoryQuestion implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long primaryId;


    @SerializedName("questionTitle")
    @Expose
    private String questionTitle;
    @SerializedName("answer")
    @Expose
    private String answer;

    protected CategoryQuestion(Parcel in) {
        if (in.readByte() == 0) {
            primaryId = null;
        } else {
            primaryId = in.readLong();
        }
        questionTitle = in.readString();
        answer = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (primaryId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(primaryId);
        }
        dest.writeString(questionTitle);
        dest.writeString(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryQuestion> CREATOR = new Creator<CategoryQuestion>() {
        @Override
        public CategoryQuestion createFromParcel(Parcel in) {
            return new CategoryQuestion(in);
        }

        @Override
        public CategoryQuestion[] newArray(int size) {
            return new CategoryQuestion[size];
        }
    };

    public Long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public CategoryQuestion() {

    }






}
