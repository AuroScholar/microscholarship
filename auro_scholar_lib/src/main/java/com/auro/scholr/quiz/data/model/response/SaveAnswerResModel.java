package com.auro.scholr.quiz.data.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveAnswerResModel {
    @SerializedName("isSave")
    @Expose
    private Boolean isSave;

    public Boolean getSave() {
        return isSave;
    }

    public void setSave(Boolean save) {
        isSave = save;
    }
}
