package com.auro.scholr.util.permission;


import java.io.Serializable;

public class LocationModel implements Serializable {
    private String latitude;
    private String longitude;
    private String timestamp;
    private String oldLatitude;
    private String oldLongitude;

    public String getOldLatitude() {
        return oldLatitude;
    }

    public void setOldLatitude(String oldLatitude) {
        this.oldLatitude = oldLatitude;
    }

    public String getOldLongitude() {
        return oldLongitude;
    }

    public void setOldLongitude(String oldLongitude) {
        this.oldLongitude = oldLongitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
