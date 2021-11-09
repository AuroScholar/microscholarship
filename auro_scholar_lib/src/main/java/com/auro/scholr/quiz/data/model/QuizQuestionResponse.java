package com.auro.scholr.quiz.data.model;

import android.graphics.drawable.Drawable;

import com.auro.scholr.teacher.data.model.SelectResponseModel;

import java.util.List;

public class QuizQuestionResponse {

    String subjecTtitle;

    boolean isSaveAndNext;

    List<SelectResponseModel> list;

    List<String> ImageCapture;

    Drawable questionImage;

    String question;

    public Drawable getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(Drawable questionImage) {
        this.questionImage = questionImage;
    }

    public String getSubjecTtitle() {
        return subjecTtitle;
    }

    public void setSubjecTtitle(String subjecTtitle) {
        this.subjecTtitle = subjecTtitle;
    }

    public boolean isSaveAndNext() {
        return isSaveAndNext;
    }

    public List<SelectResponseModel> getList() {
        return list;
    }

    public void setList(List<SelectResponseModel> list) {
        this.list = list;
    }

    public void setSaveAndNext(boolean saveAndNext) {
        isSaveAndNext = saveAndNext;
    }

    public List<String> getImageCapture() {
        return ImageCapture;
    }

    public void setImageCapture(List<String> imageCapture) {
        ImageCapture = imageCapture;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
