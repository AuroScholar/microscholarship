package com.auro.scholr.quiz.data.model.getQuestionModel;


import com.auro.scholr.quiz.data.model.submitQuestionModel.OptionResModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionResModel {

    @SerializedName("ImageName")
    @Expose
    private String imageName;
    @SerializedName("OptionDetails")
    @Expose
    private String optionDetails;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("QuestionID")
    @Expose
    private Integer questionID;
    @SerializedName("StudentExamQuestionsID")
    @Expose
    private Integer studentExamQuestionsID;

    @SerializedName("OptionsResModel")
    @Expose
    private List<OptionResModel> OptionResModelList;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getOptionDetails() {
        return optionDetails;
    }

    public void setOptionDetails(String optionDetails) {
        this.optionDetails = optionDetails;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    public Integer getStudentExamQuestionsID() {
        return studentExamQuestionsID;
    }

    public void setStudentExamQuestionsID(Integer studentExamQuestionsID) {
        this.studentExamQuestionsID = studentExamQuestionsID;
    }

    public List<OptionResModel> getOptionResModelList() {
        return OptionResModelList;
    }

    public void setOptionResModelList(List<OptionResModel> optionResModelList) {
        OptionResModelList = optionResModelList;
    }
}