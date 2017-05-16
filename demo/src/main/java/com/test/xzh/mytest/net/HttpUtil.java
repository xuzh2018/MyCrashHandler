package com.test.xzh.mytest.net;

import android.util.Log;

import com.test.xzh.mytest.entity.HttpResult;
import com.test.xzh.mytest.view.ProgressSubscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by xzh on 2017/3/3
 */

public class HttpUtil {
    private HttpUtil() {

    }

    private static class SingletoHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return SingletoHolder.INSTANCE;
    }

    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh) {
        ObservableTransformer<HttpResult<Object>, Object> transformer = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(transformer)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.i("rxjava","showdialog");
                        subscriber.showProgressDialog();
                    }
                });
        //RetrofitCache.load(cacheKey,observable,isSave,forceRefresh).subscribe((Observer) subscriber);
        observable.subscribe(subscriber);
    }
}
