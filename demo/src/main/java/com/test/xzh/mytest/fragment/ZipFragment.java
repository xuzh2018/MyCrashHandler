package com.test.xzh.mytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.test.xzh.mytest.R;
import com.test.xzh.mytest.entity.ZhuangbiImage;
import com.test.xzh.mytest.net.MyGankBeatiesResulttoItem;
import com.test.xzh.mytest.net.MyNetwork;
import com.test.xzh.mytest.view.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xzh on 2017/3/8
 */

public class ZipFragment extends BaseFragment {
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    MyAdapter adapter = new MyAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zip, container, false);
        ButterKnife.bind(this, view);
        gridRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        gridRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @OnClick(R.id.zipLoadBt)
    void click() {
        swipeRefreshLayout.setRefreshing(true);
        unsubscribe();
        Observable.zip(MyNetwork.getMapApi().getBeaty(200, 1).map(MyGankBeatiesResulttoItem.getInstance()), MyNetwork.getZBApi().search("110"),
                new BiFunction<List<ZhuangbiImage>, List<ZhuangbiImage>, List<ZhuangbiImage>>() {
                    @Override
                    public List<ZhuangbiImage> apply(List<ZhuangbiImage> zhuangbiImages, List<ZhuangbiImage> zhuangbiImages2) throws Exception {
                        List<ZhuangbiImage> items = new ArrayList<ZhuangbiImage>();
                        for (int i = 0; i < zhuangbiImages.size() / 2 && i < zhuangbiImages2.size(); i++) {
                            items.add(zhuangbiImages.get(i * 2));
                            items.add(zhuangbiImages.get(i * 2 + 1));
                            ZhuangbiImage zhuangbiItem = new ZhuangbiImage();
                            ZhuangbiImage zhuangbiImage = zhuangbiImages.get(i);
                            zhuangbiItem.description = zhuangbiImage.description;
                            zhuangbiItem.image_url = zhuangbiImage.image_url;
                            items.add(zhuangbiItem);
                        }
                        return items;
                    }
                })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(observer);
        
    }
    Observer<List<ZhuangbiImage>> observer = new Observer<List<ZhuangbiImage>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<ZhuangbiImage> value) {
            swipeRefreshLayout.setRefreshing(false);
            adapter.setImages(value);
        }

        @Override
        public void onError(Throwable e) {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {

        }
    };


    @Override
    public int getTitle() {
        return R.string.title_zip;
    }

    @Override
    public int getDialogTes() {
        return R.layout.dialog_zip;
    }
}
