package com.test.xzh.mytest.entity;

import android.support.annotation.IntDef;

import com.test.xzh.mytest.MyApplication;
import com.test.xzh.mytest.R;
import com.test.xzh.mytest.net.MyGankBeatiesResulttoItem;
import com.test.xzh.mytest.net.MyNetwork;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by xzh on 2017/3/14
 */

public class MyData {
    private static MyData INSTANCE;
    private static final int DATA_SOURCE_MEMORY = 1;
    private static final int DATA_SOURCE_DISK = 2;
    private static final int DATA_SOURCE_NETWORK = 3;

    public String getDataSourceText() {
        int dataSourceTextRes;
        switch (datasource) {
            case DATA_SOURCE_MEMORY:
                dataSourceTextRes = R.string.data_source_memory;
                break;
            case DATA_SOURCE_DISK:
                dataSourceTextRes = R.string.data_source_disk;
                break;
            case DATA_SOURCE_NETWORK:
                dataSourceTextRes = R.string.data_source_network;
                break;
            default:
                dataSourceTextRes = R.string.data_source_network;
        }
        return MyApplication.getInstance().getString(dataSourceTextRes);
    }

    @IntDef({DATA_SOURCE_MEMORY, DATA_SOURCE_DISK, DATA_SOURCE_NETWORK})
    @interface DataSource {
    }

    private int datasource;
    private BehaviorSubject<List<ZhuangbiImage>> cache;

    private MyData() {
    }

    public static MyData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyData();
        }
        return INSTANCE;

    }

    public void subscribeData(Observer<List<ZhuangbiImage>> observer) {
        if (cache == null) {
            cache = BehaviorSubject.create();

            Observable.create(new ObservableOnSubscribe<List<ZhuangbiImage>>() {
                @Override
                public void subscribe(ObservableEmitter<List<ZhuangbiImage>> e) throws Exception {
                    List<ZhuangbiImage> list = MyDataBase.getInstance().readLists();
                    if (list == null) {
                        setDatasource(DATA_SOURCE_NETWORK);
                        loadFromNet();
                    } else {
                        setDatasource(DATA_SOURCE_DISK);
                        e.onNext(list);
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .subscribe(cache);
        } else {
            setDatasource(DATA_SOURCE_MEMORY);

        }
        cache.observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private void loadFromNet() {
        MyNetwork.getMapApi()
                .getBeaty(100, 1)
                .subscribeOn(Schedulers.io())
                .map(MyGankBeatiesResulttoItem.getInstance())
                .doOnNext(new Consumer<List<ZhuangbiImage>>() {
                    @Override
                    public void accept(List<ZhuangbiImage> list) throws Exception {
                        MyDataBase.getInstance().writeLists(list);
                    }
                })
                .subscribe(new Consumer<List<ZhuangbiImage>>() {
                    @Override
                    public void accept(List<ZhuangbiImage> list) throws Exception {
                        cache.onNext(list);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    private void setDatasource(@DataSource int datasource) {
        this.datasource = datasource;

    }

    public void clearMemoryCache() {
        cache = null;
    }

    public void clearMemoryAndDiskCache() {
        clearMemoryCache();
        MyDataBase.getInstance().delete();
    }
}
