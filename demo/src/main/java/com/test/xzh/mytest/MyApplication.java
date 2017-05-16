package com.test.xzh.mytest;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.squareup.leakcanary.LeakCanary;

import org.xutils.x;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by xzh on 2017/2/16
 */

public class MyApplication extends Application {
    private static MyApplication application;
    public static MyApplication getInstance(){
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...

        x.Ext.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Hawk.init(this).build();
        application = this;
    }
}
