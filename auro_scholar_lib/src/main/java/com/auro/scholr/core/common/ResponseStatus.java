package com.auro.scholr.core.common;

public class ResponseStatus {

    private Status status;
    private Object object;


    public ResponseStatus(Object object, Status status){

        this.status = status;
        this.object = object;
    }

    public Status getStatus() {
        return status;
    }

    public Object getObject() {
        return object;
    }
}
