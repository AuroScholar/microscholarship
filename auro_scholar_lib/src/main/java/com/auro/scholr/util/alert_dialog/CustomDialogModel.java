package com.auro.scholr.util.alert_dialog;

import android.content.Context;

public class CustomDialogModel {

    String title;
    String content;
    boolean twoButtonRequired;
    Context context;


    public CustomDialogModel() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTwoButtonRequired() {
        return twoButtonRequired;
    }

    public void setTwoButtonRequired(boolean twoButtonRequired) {
        this.twoButtonRequired = twoButtonRequired;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
