package com.auro.scholr.core.application;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.auro.scholr.core.application.di.component.AppComponent;


import com.auro.scholr.core.application.di.component.DaggerAppComponent;
import com.auro.scholr.core.application.di.module.AppModule;
import com.auro.scholr.core.application.di.module.UtilsModule;

/**
 * Created by AAK on 09-Mar-2019.
 */

public class AuroApp  {

    public static  AppComponent appComponent;
    public static Context context;



    public static AppComponent getAppComponent() {

        return appComponent;
    }

    public static Context getAppContext() {

        return context;
    }

   public static void intialiseSdk(Activity context)
   {
       AuroApp.context=context;
       appComponent = DaggerAppComponent
               .builder()
               .appModule(new AppModule(context))
               .utilsModule(new UtilsModule())
               .build();

       appComponent.injectAppContext(context);
   }

}
