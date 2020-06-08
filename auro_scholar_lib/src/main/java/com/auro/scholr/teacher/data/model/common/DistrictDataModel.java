package com.auro.scholr.teacher.data.model.common;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "district_table")
public class DistrictDataModel {
    @PrimaryKey(autoGenerate = true)
    private Long primaryId;

    @SerializedName("district_name")
    @Expose
    private String district_name;

    @SerializedName("district_code")
    @Expose
    private String district_code;

    @SerializedName("state_code")
    @Expose
    private String state_code;

    @SerializedName("active_status")
    @Expose
    private String active_status;

    @SerializedName("flag")
    @Expose
    private String flag;


    public DistrictDataModel() {

    }


    public Long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(Long primaryId) {
        this.primaryId = primaryId;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    public String getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(String district_code) {
        this.district_code = district_code;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
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
}
