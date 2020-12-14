package com.auro.scholr.util.firebase;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class FirebaseEvent {

    FirebaseAnalytics mObjFirebaseAnalytics;

    public void setFirebaseAnalytics(FirebaseAnalytics mObjFirebaseAnalytics){
        this.mObjFirebaseAnalytics = mObjFirebaseAnalytics;
    }

    public void logEvent(String pStrEventName, Bundle bundle) {

        try {

            mObjFirebaseAnalytics.logEvent(pStrEventName, bundle);
            mObjFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        //    mObjFirebaseAnalytics.setMinimumSessionDuration(20000);
            mObjFirebaseAnalytics.setSessionTimeoutDuration(500);

        }catch (Exception e){


        }
    }
}
