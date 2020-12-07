package com.auro.scholr.core.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.google.gson.Gson;

import static com.auro.scholr.core.common.AppConstant.PREF_OBJECT;


public enum AppPref {
    INSTANCE;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferenceEditor;
    private String PreferenceName = AuroApp.getAppContext().getPackageName();
    private PrefModel prefModel;


    /**
     * GET PREF MODEL OBJECT
     * SET DATA IN MODEL
     */
    public PrefModel getModelInstance() {

        if (sharedPreferences == null) {
            sharedPreferences = AuroApp.getAppContext().getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        }

        if (getPref() != null) {
            return getPref();
        }

        if (prefModel == null) {
            prefModel = new PrefModel();
        }

        return prefModel;
    }

    /**
     * SAVE MODEL OBJECT IN SHARED PREF
     */
    public void setPref(PrefModel prefModel) {
        sharedPreferenceEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(prefModel);
        sharedPreferenceEditor.putString(PREF_OBJECT, json);
        sharedPreferenceEditor.apply(); // Thread safe // Save in background // Asynchronous
        //sharedPreferenceEditor.commit(); // Not Thread safe // Save immediately // Synchronous
    }


    private PrefModel getPref() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(PREF_OBJECT, "");
        if (json.isEmpty()) {
            return null;
        }
        return gson.fromJson(json, PrefModel.class);
    }


    public void setStringPref(String key, String value) {
        sharedPreferenceEditor = AuroApp.getAppContext().getSharedPreferences(PreferenceName, Context.MODE_PRIVATE).edit();
        sharedPreferenceEditor.putString(key, value).apply();
    }

    public void setBooleanTutorial(String key,boolean tutorial){
        sharedPreferenceEditor = AuroApp.getAppContext().getSharedPreferences(PreferenceName, Context.MODE_PRIVATE).edit();
        sharedPreferenceEditor.putBoolean(key, tutorial).apply();
    }
    public boolean getBooleanTutorial(String key){
        sharedPreferences = AuroApp.getAppContext().getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }

    public String getPrefStringValueNotNull(String key) {

        return getStringValue(key, "");
    }

    private String getStringValue(String key, String defaultValue) {
        sharedPreferences = AuroApp.getAppContext().getSharedPreferences(PreferenceName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }


}
