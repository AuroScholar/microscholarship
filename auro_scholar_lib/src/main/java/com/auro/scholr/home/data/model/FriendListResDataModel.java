package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FriendListResDataModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("friends")
    @Expose
    private List<FriendsLeaderBoardModel> friends = null;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FriendsLeaderBoardModel> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendsLeaderBoardModel> friends) {
        this.friends = friends;
    }
}
