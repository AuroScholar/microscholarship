package com.auro.scholr.core.application;

import android.app.Application;
import android.content.Context;

import com.auro.scholr.core.application.di.component.AppComponent;


import com.auro.scholr.core.application.di.component.DaggerAppComponent;
import com.auro.scholr.core.application.di.module.AppModule;
import com.auro.scholr.core.application.di.module.UtilsModule;

/**
 * Created by AAK on 09-Mar-2019.
 */

public class AuroApp extends Application {

    AppComponent appComponent;
    public static AuroApp context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();

        appComponent.injectAppContext(this);
    }

    public AppComponent getAppComponent() {

        return appComponent;
    }

    public static AuroApp getAppContext() {

        return context;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

}
