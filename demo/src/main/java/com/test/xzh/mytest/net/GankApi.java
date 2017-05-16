package com.test.xzh.mytest.net;

import com.test.xzh.mytest.entity.GankBeautyResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by xzh on 2017/3/9
 */

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBeautyResult> getBeaty(@Path("number") int number, @Path("page") int page);
}
