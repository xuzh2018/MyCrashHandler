package com.test.xzh.mytest.net;

import com.test.xzh.mytest.entity.ZhuangbiImage;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by xzh on 2017/3/9
 */

public interface ZBApi {
    @GET("search")
    Observable<List<ZhuangbiImage>> search(@Query("q") String query);
}
