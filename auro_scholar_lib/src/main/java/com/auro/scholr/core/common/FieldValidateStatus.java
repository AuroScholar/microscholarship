package com.auro.scholr.core.common;

public class FieldValidateStatus {

    private boolean isValid;
    private String msg;
    private Status status;

    public FieldValidateStatus(boolean isValid, String msg, Status status){
        this.msg = msg;
        this.isValid = isValid;
        this.status = status;
    }
    public boolean isValid() {
        return isValid;
    }


    public String getMsg() {
        return msg;
    }

    public Status getStatus() {
        return status;
    }
}
