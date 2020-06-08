package com.auro.scholr.teacher.data.model.common;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "state_table")
public class StateDataModel {
    @PrimaryKey(autoGenerate = true)
    private Long primaryId;

    @SerializedName("state_name")
    @Expose
    private String state_name;

    @SerializedName("state_code")
    @Expose
    private String state_code;

    @SerializedName("short_name")
    @Expose
    private String short_name;

    @SerializedName("active_status")
    @Expose
    private String active_status;

    @SerializedName("flag")
    @Expose
    private String flag;


    public StateDataModel() {

    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }
}
