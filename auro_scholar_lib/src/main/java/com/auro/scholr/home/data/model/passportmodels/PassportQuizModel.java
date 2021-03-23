package com.auro.scholr.home.data.model.passportmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class  PassportQuizModel {

    @SerializedName("topic_name")
    @Expose
    private String topicName;
    @SerializedName("quiz_detail")
    @Expose
    private List<PassportQuizDetailModel> quizDetail = null;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<PassportQuizDetailModel> getQuizDetail() {
        return quizDetail;
    }

    public void setQuizDetail(List<PassportQuizDetailModel> quizDetail) {
        this.quizDetail = quizDetail;
    }



}