package com.auro.scholr.core.application.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AAK on 09-Mar-2019.
 * asif.abrar88@gmail.com
 * 9899982894, 9821900190 India.
 */
@Module
public class AppModule {

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }
}
