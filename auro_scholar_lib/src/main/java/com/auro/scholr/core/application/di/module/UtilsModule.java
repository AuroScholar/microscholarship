package com.auro.scholr.core.application.di.module;


import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.core.network.URLConstant;
import com.auro.scholr.util.DeviceUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AAK on 09-Mar-2019.
 * asif.abrar88@gmail.com
 * 9899982894, 9821900190 India.
 */

@Module
public class UtilsModule {

    @Provides
    @Singleton
    HttpLoggingInterceptor provideInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    @Singleton
    Gson provideGson() {

        // set the field name policy as you want to send like with underscores, lowercase, with dases policy
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader(HttpLoggingInterceptor httpLoggingInterceptor) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

      // httpClient.addInterceptor(httpLoggingInterceptor);

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header(AppConstant.CONTENT_TYPE, AppConstant.APPLICATION_JSON)
                    .header(AppConstant.DEVICE_ID, DeviceUtil.getDeviceId(AuroApp.getAppContext()))
                    .header(AppConstant.DEVICE_TYPE, AppConstant.PLATFORM_ANDROID)
                    .header(AppConstant.LANGUAGE, "EN")
                    .header("Authorization",  Credentials.basic("bhanu", "123"))
                    //.header(AUTH_TOKEN, AppPref.INSTANCE.getModelInstance().getOtpRes().getAuthToken())
                    .build();
            return chain.proceed(request);
        })
                .connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES);
        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(URLConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}
