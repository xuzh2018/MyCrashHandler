package com.test.xzh.mytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.xzh.mytest.R;
import com.test.xzh.mytest.entity.ZhuangbiImage;
import com.test.xzh.mytest.net.MyGankBeatiesResulttoItem;
import com.test.xzh.mytest.net.MyNetwork;
import com.test.xzh.mytest.view.MyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xzh on 2017/3/8
 */

public class MapFragment extends BaseFragment {

    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    @BindView(R.id.pageTv)
    TextView pageTv;
    @BindView(R.id.previousPageBt)
    Button previousPageBt;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private MyAdapter adapter = new MyAdapter();
    private int page = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(inflater.getContext()).inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);
        gridRv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        gridRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }
    @OnClick(R.id.previousPageBt)
    void prePage(){
        loadPage(--page);
        if (page == 1){
            previousPageBt.setEnabled(false);
        }
    }
    @OnClick(R.id.nextPageBt)
    void nextPage(){
        loadPage(++page);
        if (page == 2){
            previousPageBt.setEnabled(true);
        }
    }
    Observer<List<ZhuangbiImage>> observer = new Observer<List<ZhuangbiImage>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<ZhuangbiImage> images) {
            swipeRefreshLayout.setRefreshing(false);
            pageTv.setText(getString(R.string.page_with_number, page));
            adapter.setImages(images);
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

    private void loadPage(int i) {
        swipeRefreshLayout.setRefreshing(true);
        MyNetwork.getMapApi()
                .getBeaty(10,i)
                .map(MyGankBeatiesResulttoItem.getInstance())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public int getTitle() {
        return R.string.title_map;
    }

    @Override
    public int getDialogTes() {
        return R.layout.dialog_map;
    }
}
