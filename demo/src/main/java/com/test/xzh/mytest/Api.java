package com.test.xzh.mytest;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.test.xzh.mytest.net.Url;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;



import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xzh on 2017/3/2
 */

public class Api {
    private static ApiService SERVICE;
    private static final int DEFAULT_TIMEOUT = 10000;


    public static ApiService getService(){
            if (SERVICE == null){
                OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
                okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
                okHttpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        return response.newBuilder().header("key1", "value1").addHeader("key2", "value2").build();
                    }
                });
                SERVICE = new Retrofit.Builder()
                        .client(okHttpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(Url.BASE_URL)
                        .build().create(ApiService.class);
            }
        return SERVICE;
    }
}
