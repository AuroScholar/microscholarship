package com.auro.scholr.home.data.model;

public class NavItemModel {


    String mTitle;
    String mSubtitle;
    int mIcon;


    public NavItemModel(String title, String subtitle, int icon) {
        mTitle = title;
        mSubtitle = subtitle;
        mIcon = icon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public void setmSubtitle(String mSubtitle) {
        this.mSubtitle = mSubtitle;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public NavItemModel() {

    }
}
