package com.auro.scholr.home.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChallengeAccepResModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("challenge_by")
    @Expose
    private String challengeBy;
    @SerializedName("challenge_to")
    @Expose
    private String challengeTo;
    @SerializedName("is_accepted")
    @Expose
    private Integer isAccepted;
    @SerializedName("accepted_date")
    @Expose
    private String acceptedDate;

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

    public String getChallengeBy() {
        return challengeBy;
    }

    public void setChallengeBy(String challengeBy) {
        this.challengeBy = challengeBy;
    }

    public String getChallengeTo() {
        return challengeTo;
    }

    public void setChallengeTo(String challengeTo) {
        this.challengeTo = challengeTo;
    }

    public Integer getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Integer isAccepted) {
        this.isAccepted = isAccepted;
    }

    public String getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(String acceptedDate) {
        this.acceptedDate = acceptedDate;
    }

}