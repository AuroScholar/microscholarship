package com.auro.scholr.home.data.model;

import java.util.ArrayList;
import java.util.List;

public class RandomInviteFriendsDataModel {

    String TextTitle;

    Float TextTitleSize;

    String ButtonTitle;

    Float ButtonSize;




    public RandomInviteFriendsDataModel(String textTitle, Float textTitleSize, String buttonTitle, Float buttonSize) {
        TextTitle = textTitle;
        TextTitleSize = textTitleSize;
        ButtonTitle = buttonTitle;
        ButtonSize = buttonSize;


    }

    public String getTextTitle() {
        return TextTitle;
    }

    public void setTextTitle(String textTitle) {
        TextTitle = textTitle;
    }

    public Float getTextTitleSize() {
        return TextTitleSize;
    }

    public void setTextTitleSize(Float textTitleSize) {
        TextTitleSize = textTitleSize;
    }

    public String getButtonTitle() {
        return ButtonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        ButtonTitle = buttonTitle;
    }

    public Float getButtonSize() {
        return ButtonSize;
    }

    public void setButtonSize(Float buttonSize) {
        ButtonSize = buttonSize;
    }
}
