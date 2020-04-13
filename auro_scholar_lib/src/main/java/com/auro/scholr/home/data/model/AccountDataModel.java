package com.auro.scholr.home.data.model;

public class AccountDataModel {
    private String accountType;

    public AccountDataModel(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
