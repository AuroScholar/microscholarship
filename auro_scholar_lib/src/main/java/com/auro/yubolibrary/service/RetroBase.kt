package com.auro.yubolibrary.service


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetroBase {

    private var retrofit: Retrofit? = null

    //    return retrofit instace
    companion object {

        fun getClient(baseUrl: String): Retrofit? {

        //        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            val httpClient = OkHttpClient.Builder()
            httpClient.connectTimeout(1, TimeUnit.MINUTES)
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.writeTimeout(15, TimeUnit.SECONDS)
            //        httpClient.addInterceptor(logging);
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}