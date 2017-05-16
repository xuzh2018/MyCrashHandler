package com.test.xzh.mytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.xzh.mytest.entity.HttpResult;
import com.test.xzh.mytest.entity.Subject;
import com.test.xzh.mytest.net.ActivityLifeCycleEvent;
import com.test.xzh.mytest.net.HttpUtil;
import com.test.xzh.mytest.view.ProgressSubscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by xzh on 2017/3/2
 */

public class Test5Activity extends BaseActivity {
    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.text)
    TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test5);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn)
    public void onClick() {
        doGet();
    }

    private void doGet() {
        Observable<HttpResult<List<Subject>>> topMovie = Api.getService().getTopMovie(1, 100);
        HttpUtil.getInstance().toSubscribe(topMovie, new ProgressSubscriber<List<Subject>>(this) {


            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            protected void _onError(String s) {
                Toast.makeText(Test5Activity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected void _onNext(List<Subject> subjects) {
                Log.i("rxjava","show");
                String str = "";
                for (int i = 0; i < subjects.size(); i++) {
                    str += "电影名：" + subjects.get(i).getTitle() + "\n";
                }
                mText.setText(str);
            }


        }, "cacheKey", ActivityLifeCycleEvent.DESTROY, lifecycleSubject, false, false);
    }
}
