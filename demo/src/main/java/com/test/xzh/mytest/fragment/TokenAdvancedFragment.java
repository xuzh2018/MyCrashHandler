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
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableFlatMap;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xzh on 2017/3/8
 */

public class TokenAdvancedFragment extends BaseFragment {


    @BindView(R.id.tokenTv)
    TextView tokenTv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.requestBt)
    Button requestBt;
    @BindView(R.id.invalidateTokenBt)
    Button invalidateTokenBt;

    FakeToken cachedFakeToken = new FakeToken();
    boolean tokenUpdated;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_token_advanced, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    public int getTitle() {
        return R.string.title_token_advanced;
    }

    @Override
    public int getDialogTes() {
        return R.layout.dialog_token_advanced;
    }

    @OnClick({R.id.requestBt, R.id.invalidateTokenBt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invalidateTokenBt:
                cachedFakeToken.expired = true;
                Toast.makeText(getActivity(), R.string.token_destroyed, Toast.LENGTH_SHORT).show();
                break;
            case R.id.requestBt:
                tokenUpdated = false;
                swipeRefreshLayout.setRefreshing(true);
                unsubscribe();
                final FakeApi fakeApi = MyNetwork.getFakeApi();
                Observable.just(Observable.<FakeThing>error(new NullPointerException("Token is null!")))
                        .flatMap(new Function<Object, Observable<FakeThing>>() {
                            @Override
                            public Observable<FakeThing> apply(Object o) throws Exception {

                                return cachedFakeToken.token == null ? Observable.<FakeThing>error(new NullPointerException("Token is null!")) : fakeApi.getFakeThing(cachedFakeToken);
                            }
                        })
                        .retryWhen(new Function<Observable<Throwable>, Observable<?>>() {
                            @Override
                            public Observable<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                                    @Override
                                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                        if (throwable instanceof IllegalArgumentException || throwable instanceof NullPointerException) {
                                            return fakeApi.getFakeToken("MynewToken")
                                                    .doOnNext(new Consumer<FakeToken>() {
                                                        @Override
                                                        public void accept(FakeToken fakeToken) throws Exception {
                                                            tokenUpdated = true;
                                                            cachedFakeToken.token = fakeToken.token;
                                                            cachedFakeToken.expired = fakeToken.expired;
                                                        }
                                                    });
                                        }
                                        return Observable.error(throwable);
                                    }
                                });
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<FakeThing>() {
                                       @Override
                                       public void accept(FakeThing fakeThing) throws Exception {
                                           swipeRefreshLayout.setRefreshing(false);
                                           String token = cachedFakeToken.token;
                                           if (tokenUpdated) {
                                               token += "(" + getString(R.string.updated) + ")";
                                           }
                                           tokenTv.setText(getString(R.string.got_token_and_data, token, fakeThing.id, fakeThing.name));
                                       }
                                   }, new Consumer<Throwable>() {
                                       @Override
                                       public void accept(Throwable throwable) throws Exception {
                                           swipeRefreshLayout.setRefreshing(false);
                                           Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
                                       }
                                   }
                        );
                break;
        }
    }
}
