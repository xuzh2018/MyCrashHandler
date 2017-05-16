package com.test.xzh.mytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.xzh.mytest.R;
import com.test.xzh.mytest.entity.FakeApi;
import com.test.xzh.mytest.entity.FakeThing;
import com.test.xzh.mytest.entity.FakeToken;
import com.test.xzh.mytest.net.MyNetwork;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xzh on 2017/3/8
 */

public class TokenFragment extends BaseFragment {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.requestBt)
    Button requestBt;
    @BindView(R.id.tokenTv)
    TextView tokenTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_token, container, false);
        ButterKnife.bind(this,view);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setColorSchemeColors(Color.RED,Color.YELLOW,Color.GREEN);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public int getTitle() {
        return R.string.title_token;
    }

    @Override
    public int getDialogTes() {
        return R.layout.dialog_token;
    }

    @OnClick(R.id.requestBt)
    public void onClick() {
        swipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        final FakeApi fakeApi = MyNetwork.getFakeApi();
        fakeApi.getFakeToken("fake_token")
                .flatMap(new Function<FakeToken, Observable<FakeThing>>() {
                    @Override
                    public Observable<FakeThing> apply(FakeToken fakeToken) throws Exception {
                        return fakeApi.getFakeThing(fakeToken);
                    }
                })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<FakeThing>() {
            @Override
            public void accept(FakeThing fakeThing) throws Exception {
                swipeRefreshLayout.setRefreshing(false);
                tokenTv.setText(getString(R.string.got_data, fakeThing.id, fakeThing.name));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
