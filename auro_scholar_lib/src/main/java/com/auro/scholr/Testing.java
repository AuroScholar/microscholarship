package com.auro.scholr;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "tt_test")
public class Testing {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "inviteFriends")
    @SerializedName("inviteFriends")
    @Expose
    private Integer inviteFriends;

    public Integer getInviteFriends() {
        return inviteFriends;
    }

    public void setInviteFriends(Integer inviteFriends) {
        this.inviteFriends = inviteFriends;
    }





}