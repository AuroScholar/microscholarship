package com.auro.scholr.core.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorDetail {

    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("description")
    @Expose
    private String description;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
