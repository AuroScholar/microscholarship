
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
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


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


    //For Bypassing the SSL CERTIFICATE
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = builder.addInterceptor(interceptor).build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
