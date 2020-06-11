package com.auro.scholr.home.data.model;

import android.net.Uri;

import java.net.URI;

public class KYCDocumentDatamodel {


    int documentId;
    Uri documentURi;
    boolean documentstatus;
    boolean progress;
    boolean backgroundStatus;
    boolean modify;
    String buttonText;
    String documentName;
    String documentFileName;
    String id_name;
    String documentUrl;
    byte[] imageBytes;
    int viewType;

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public boolean isModify() {
        return modify;
    }

    public void setModify(boolean modify) {
        this.modify = modify;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }

    public boolean isBackgroundStatus() {
        return backgroundStatus;
    }

    public void setBackgroundStatus(boolean backgroundStatus) {
        this.backgroundStatus = backgroundStatus;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }


    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public String getDocumentFileName() {
        return documentFileName;
    }

    public void setDocumentFileName(String documentFileName) {
        this.documentFileName = documentFileName;
    }

    public Uri getDocumentURi() {
        return documentURi;
    }

    public void setDocumentURi(Uri documentURi) {
        this.documentURi = documentURi;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public boolean isDocumentstatus() {
        return documentstatus;
    }

    public void setDocumentstatus(boolean documentstatus) {
        this.documentstatus = documentstatus;
    }

    public KYCDocumentDatamodel() {
    }


}
