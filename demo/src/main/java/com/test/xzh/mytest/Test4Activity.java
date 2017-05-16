package com.test.xzh.mytest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class Test4Activity extends AppCompatActivity {


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


    private String sayMyname() {
        return "Hello, I am your friend, Spike!";
    }

    @OnClick(R.id.add)
    public void onClick() {
        Toast.makeText(getApplication(),"dianji",Toast.LENGTH_SHORT).show();
        doSomeThing();
    }

    private void doSomeThing() {
        getFlowAble()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        text.append(s);
                        text.append("\n");
                    }
                });

    }

    private Flowable<String> getFlowAble() {
        return Flowable.just("one","two");
    }


}


