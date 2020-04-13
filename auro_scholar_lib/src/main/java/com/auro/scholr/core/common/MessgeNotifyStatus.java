package com.auro.scholr.core.common;


public class MessgeNotifyStatus {
    private Status status = null;
    private Object data = null;

    public MessgeNotifyStatus(Status status, Object data){
        this.status = status;
        this.data = data;
    }


    public Status getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
