package com.auro.scholr.home.data.model;

import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;

import com.auro.scholr.core.common.SdkCallBack;
import com.auro.scholr.home.presentation.view.fragment.QuizHomeFragment;

public class AuroScholarDataModel implements Parcelable {

    String mobileNumber;
    Activity activity;
    int fragmentContainerUiId;
    String scholrId;
    String studentClass;
    String regitrationSource = "";
    String shareType = "";
    String shareIdentity = "";
    String referralLink = "";
    String userPartnerid = "";
    SdkCallBack sdkcallback;
    String partnerSource = "";
    int sdkType;
    int sdkFragmentType;
    boolean isEmailVerified;
    String language = "en";
    boolean applicationLang;
    String UTMLink="";
    protected AuroScholarDataModel(Parcel in) {
        mobileNumber = in.readString();
        fragmentContainerUiId = in.readInt();
        scholrId = in.readString();
        studentClass = in.readString();
        regitrationSource = in.readString();
        shareType = in.readString();
        shareIdentity = in.readString();
        referralLink = in.readString();
        userPartnerid = in.readString();
        partnerSource = in.readString();
        sdkType = in.readInt();
        sdkFragmentType = in.readInt();
        isEmailVerified = in.readByte() != 0;
        language = in.readString();
        applicationLang = in.readByte() != 0;
        UTMLink = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobileNumber);
        dest.writeInt(fragmentContainerUiId);
        dest.writeString(scholrId);
        dest.writeString(studentClass);
        dest.writeString(regitrationSource);
        dest.writeString(shareType);
        dest.writeString(shareIdentity);
        dest.writeString(referralLink);
        dest.writeString(userPartnerid);
        dest.writeString(partnerSource);
        dest.writeInt(sdkType);
        dest.writeInt(sdkFragmentType);
        dest.writeByte((byte) (isEmailVerified ? 1 : 0));
        dest.writeString(language);
        dest.writeByte((byte) (applicationLang ? 1 : 0));
        dest.writeString(UTMLink);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AuroScholarDataModel> CREATOR = new Creator<AuroScholarDataModel>() {
        @Override
        public AuroScholarDataModel createFromParcel(Parcel in) {
            return new AuroScholarDataModel(in);
        }

        @Override
        public AuroScholarDataModel[] newArray(int size) {
            return new AuroScholarDataModel[size];
        }
    };

    public String getUTMLink() {
        return UTMLink;
    }

    public void setUTMLink(String UTMLink) {
        this.UTMLink = UTMLink;
    }



    public AuroScholarDataModel() {

    }





    public boolean isApplicationLang() {
        return applicationLang;
    }

    public void setApplicationLang(boolean applicationLang) {
        this.applicationLang = applicationLang;
    }

    public String getPartnerSource() {
        return partnerSource;
    }

    public void setPartnerSource(String partnerSource) {
        this.partnerSource = partnerSource;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public int getSdkType() {
        return sdkType;
    }

    public void setSdkType(int sdkType) {
        this.sdkType = sdkType;
    }

    public int getSdkFragmentType() {
        return sdkFragmentType;
    }

    public void setSdkFragmentType(int sdkFragmentType) {
        this.sdkFragmentType = sdkFragmentType;
    }

    public SdkCallBack getSdkcallback() {
        return sdkcallback;
    }

    public void setSdkcallback(SdkCallBack sdkcallback) {
        this.sdkcallback = sdkcallback;
    }

    public String getReferralLink() {
        return referralLink;
    }

    public void setReferralLink(String referralLink) {
        this.referralLink = referralLink;
    }

    public String getScholrId() {
        return scholrId;
    }

    public void setScholrId(String scholrId) {
        this.scholrId = scholrId;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getRegitrationSource() {
        return regitrationSource;
    }

    public void setRegitrationSource(String regitrationSource) {
        this.regitrationSource = regitrationSource;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getShareIdentity() {
        return shareIdentity;
    }

    public void setShareIdentity(String shareIdentity) {
        this.shareIdentity = shareIdentity;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public int getFragmentContainerUiId() {
        return fragmentContainerUiId;
    }

    public void setFragmentContainerUiId(int fragmentContainerUiId) {
        this.fragmentContainerUiId = fragmentContainerUiId;
    }

    public String getUserPartnerid() {
        return userPartnerid;
    }

    public void setUserPartnerid(String userPartnerid) {
        this.userPartnerid = userPartnerid;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
