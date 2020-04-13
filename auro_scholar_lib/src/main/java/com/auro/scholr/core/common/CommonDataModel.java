package com.auro.scholr.core.common;

import java.io.Serializable;

public class CommonDataModel implements Serializable {

    private int source;
    private Status clickType;
    private  Object object;


    public Status getClickType() {
        return clickType;
    }

    public void setClickType(Status clickType) {
        this.clickType = clickType;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }











}
