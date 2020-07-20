package com.auro.scholr.home.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FriendsLeaderBoardModel {


    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;

    @SerializedName("student_name")
    @Expose
    private String studentName;

    @SerializedName("quiz_score")
    @Expose
    private String studentScore;

    @SerializedName("profile_pic")
    @Expose
    private String imagePath;

    @SerializedName("challenge_count")
    @Expose
    private int challengeCount;

    @SerializedName("challenged_you")
    @Expose
    private boolean challengedYou;


    @SerializedName("sent_text")
    @Expose
    private String sentText;


    boolean progress;

    boolean sent;

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    private String scholarshipWon;

    private int viewType;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public FriendsLeaderBoardModel() {

    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(String studentScore) {
        this.studentScore = studentScore;
    }

    public String getScholarshipWon() {
        return scholarshipWon;
    }

    public void setScholarshipWon(String scholarshipWon) {
        this.scholarshipWon = scholarshipWon;
    }


    public boolean isChallengedYou() {
        return challengedYou;
    }

    public void setChallengedYou(boolean challengedYou) {
        this.challengedYou = challengedYou;
    }

    public int getChallengeCount() {
        return challengeCount;
    }

    public void setChallengeCount(int challengeCount) {
        this.challengeCount = challengeCount;
    }

    public String getSentText() {
        return sentText;
    }

    public void setSentText(String sentText) {
        this.sentText = sentText;
    }
}
