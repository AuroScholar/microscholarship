package com.auro.scholr.home.data.model;

import android.graphics.drawable.Drawable;

public class WalletResponseAmountResModel  {

    private String textTitle;
    private Drawable drawable;
    private String amount;


    public String getText() {
        return textTitle;
    }

    public void setText(String text) {
        this.textTitle = text;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
