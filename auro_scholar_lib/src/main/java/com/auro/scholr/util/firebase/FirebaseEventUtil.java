package com.auro.scholr.util.firebase;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;

public class FirebaseEventUtil {

    private FirebaseAnalytics mObjFirebaseAnalytics;


    public void logEvent(Context context, String pStrEventName, Map<String,String> detailsList) {

        try {

            mObjFirebaseAnalytics = FirebaseAnalytics.getInstance(context);


            Bundle bundle = new Bundle();

            for (Map.Entry<String,String> eventDetails: detailsList.entrySet()){
                Log.e("EventDetails",eventDetails.getKey()+ " ====" +eventDetails.getValue());
                if(isNumeric( eventDetails.getValue())) {
                    int lIntVal = convertToNumber(eventDetails.getValue());
                    if(lIntVal!=-1){
                        bundle.putInt(eventDetails.getKey(),lIntVal);
                    }
                    else {
                        bundle.putString(eventDetails.getKey(), eventDetails.getValue());
                    }
                }
                else {
                    bundle.putString(eventDetails.getKey(), eventDetails.getValue());
                }
            }

            mObjFirebaseAnalytics.logEvent(pStrEventName, bundle);

            mObjFirebaseAnalytics.setAnalyticsCollectionEnabled(true);

            mObjFirebaseAnalytics.setMinimumSessionDuration(20000);

            mObjFirebaseAnalytics.setSessionTimeoutDuration(500);

        }catch (Exception e){


        }
    }

    private int convertToNumber(String pValue){
        int lIntResult = -1;
        try {
            if(!pValue.equals("") && checkNumberFormat(pValue)){
                lIntResult = Integer.parseInt(pValue);
            }
        }
        catch (NumberFormatException e){

        }

        return lIntResult;
    }

    private boolean checkNumberFormat(String number) {
        String regex = "[0-9]+";
        return number.matches(regex);
    }

    public boolean isNumeric(String str)
    {
        boolean result = false;
        try {
            if (str.matches("-?\\d+(\\.\\d+)?")) {
                result = true;
            } else {
                result = false;
            }
        }
        catch (Exception e){
        }
        return  result;
    }
}
