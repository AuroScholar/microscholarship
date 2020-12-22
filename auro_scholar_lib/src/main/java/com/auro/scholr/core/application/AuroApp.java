package com.auro.scholr.core.application;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;


import com.auro.scholr.core.application.di.component.AppComponent;


import com.auro.scholr.core.application.di.component.DaggerAppComponent;
import com.auro.scholr.core.application.di.module.AppModule;
import com.auro.scholr.core.application.di.module.UtilsModule;
import com.auro.scholr.home.data.model.AuroScholarDataModel;

import java.util.Locale;

/**
 * Created by AAK on 09-Mar-2019.
 */

public class AuroApp {

    public static AppComponent appComponent;
    public static Activity context;
    public static AuroScholarDataModel auroScholarDataModel;
    public static int fragmentContainerUiId = 0;
    Context t;


    public static AuroScholarDataModel getAuroScholarModel() {
        return auroScholarDataModel;
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            intialiseSdk(getAuroScholarModel().getActivity());
        }
        return appComponent;
    }

    public static Activity getAppContext() {
        return context;
    }


    public static int getFragmentContainerUiId() {
        return fragmentContainerUiId;
    }


    public static void setAuroModel(AuroScholarDataModel model) {
        auroScholarDataModel = model;
        fragmentContainerUiId = model.getFragmentContainerUiId();
        intialiseSdk(model.getActivity());
    }

    public static void intialiseSdk(Activity context) {
        AuroApp.context = context;
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(context))
                .utilsModule(new UtilsModule())
                .build();
        appComponent.injectAppContext(context);
    }


}
