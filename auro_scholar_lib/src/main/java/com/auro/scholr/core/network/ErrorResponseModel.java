package com.auro.scholr.core.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ErrorResponseModel {

    @SerializedName("timestamp")
    @Expose
    private Long timestamp;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("errorType")
    @Expose
    private String errorType;
    @SerializedName("errorDetails")
    @Expose
    private List<ErrorDetail> errorDetails = null;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<ErrorDetail> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }
}
