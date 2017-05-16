package com.test.xzh.mytest.net;

import android.util.Log;

import com.test.xzh.mytest.entity.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by xzh on 2017/3/3
 */

public class RxHelper {
    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> cycleSubject) {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {
               /* Observable<ActivityLifeCycleEvent> observable = cycleSubject.filter(new Predicate<ActivityLifeCycleEvent>() {
                    @Override
                    public boolean test(ActivityLifeCycleEvent activityLifeCycleEvent) throws Exception {
                        Log.i("rxjava","filter");
                        return activityLifeCycleEvent.equals(event);
                    }
                });*/
                return upstream.flatMap(new Function<HttpResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(HttpResult<T> objectHttpResult) throws Exception {
                        Log.i("rxjava","flatmap");
                        if (objectHttpResult.getCount() != 0) {
                            return createData(objectHttpResult.getSubjects());
                        } else {
                            return Observable.error(new ApiException(objectHttpResult.getCount()));
                        }
                    }
                }).takeUntil(cycleSubject).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    private static <T> ObservableSource<T> createData(final T subjects) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                Log.i("rxjava","onnextdata");
                e.onNext(subjects);
                e.onComplete();
            }
        });
    }
}
