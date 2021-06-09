package com.auro.scholr.home.data.model;

public class SelectChapterQuizModel {

    public String score ="00";

    public String chapter = "Chapter Name Goes Here...";

    public String serialNo;

    private boolean isCheck;

    public SelectChapterQuizModel() {
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
