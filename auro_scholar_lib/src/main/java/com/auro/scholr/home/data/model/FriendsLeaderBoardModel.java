package com.auro.scholr.home.data.model;

public class FriendsLeaderBoardModel {
    private String studentName;
    private String studentScore;
    private String scholarshipWon;
    private String imagePath;
    private int viewType;

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


}
