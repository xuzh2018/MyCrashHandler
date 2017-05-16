package com.test.xzh.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.test.xzh.mytest.net.ActivityLifeCycleEvent;

import io.reactivex.subjects.PublishSubject;


/**
 * Created by xzh on 2017/3/2
 */
public class BaseActivity extends AppCompatActivity {
    public final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(ActivityLifeCycleEvent.CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.START);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.STOP);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifecycleSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }
}
