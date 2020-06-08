package com.auro.scholr.home.data.model;

public class TeacherDocumentModel {


    public String uploadDocumentname;

    private int viewType;

    public TeacherDocumentModel() {
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getViewType() {
        return viewType;
    }
    public String getUploadDocumentname() {
        return uploadDocumentname;
    }

    public void setUploadDocumentname(String uploadDocumentname) {
        this.uploadDocumentname = uploadDocumentname;
    }
}
