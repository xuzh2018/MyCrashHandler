package com.test.xzh.mytest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Test3Activity extends AppCompatActivity {


    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.add)
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        setTitle("点击显示");
        ButterKnife.bind(this);
        /*observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);*/

    }

    Flowable observable = Flowable.create(new FlowableOnSubscribe() {
        @Override
        public void subscribe(FlowableEmitter e) throws Exception {
            e.onNext(sayMyname());
            e.onComplete();
        }
    }, BackpressureStrategy.BUFFER);
    Observer<String> subscriber1 = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String value) {
            text.append(value);
            text.append("\n");
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {
            text.append("complete");
            text.append("\n");
        }
    };

    private String sayMyname() {
        return "Hello, I am your friend, Spike!";
    }

    @OnClick(R.id.add)
    public void onClick() {
        doSomeThing();
    }

    private void doSomeThing() {
        getObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber1);

    }

    private Observable<String> getObservable() {
        return Observable.just("one", "two","three");
    }


}


