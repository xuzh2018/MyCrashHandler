package com.test.xzh.mytest.net;

import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.test.xzh.mytest.entity.FakeApi;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xzh on 2017/3/9
 */

public class MyNetwork {
    static ZBApi zbApi;
    static GankApi gankApi;
    static FakeApi fakeApi;
    static OkHttpClient okHttpClient = new OkHttpClient();
    static GsonConverterFactory factory = GsonConverterFactory.create();
    static RxJava2CallAdapterFactory rxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create();
    public static ZBApi getZBApi() {
        if (zbApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .baseUrl("http://www.zhuangbi.info/")
                    .build();
            zbApi = retrofit.create(ZBApi.class);
        }
            return zbApi;


    }


    public static GankApi getMapApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(factory)
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;

    }

    public static FakeApi getFakeApi() {
        if (fakeApi == null){
            fakeApi =new FakeApi();
        }
        return fakeApi;
    }
}
