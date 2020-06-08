package com.auro.scholr.home.data.model;

public class SelectResponseModel {

    public String message;

    private int viewType;

    public SelectResponseModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
