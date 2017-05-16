package com.test.xzh.mytest;


import com.test.xzh.mytest.entity.HttpResult;
import com.test.xzh.mytest.entity.Subject;
import com.test.xzh.mytest.entity.UserEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface ApiService {
    @GET("/student/mobileRegister")
    Observable<HttpResult<UserEntity>> login(@Query("phone") String phone, @Query("password") String psw);


    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Observable<HttpResult<Subject>> getUser(@Query("touken") String touken);

}
