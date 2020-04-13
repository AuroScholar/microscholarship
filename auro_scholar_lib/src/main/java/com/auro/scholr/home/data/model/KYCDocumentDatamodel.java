package com.auro.scholr.home.data.model;

import java.net.URI;

public class KYCDocumentDatamodel {

    String documentName;
    boolean  progress;
    String documentFileName;

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    URI documentURi;
    int documentId;
    boolean documentstatus;
    String buttonText;

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

    public URI getDocumentURi() {
        return documentURi;
    }

    public void setDocumentURi(URI documentURi) {
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
