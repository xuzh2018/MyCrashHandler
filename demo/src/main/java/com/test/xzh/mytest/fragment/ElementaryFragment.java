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
import android.widget.RadioButton;

import com.test.xzh.mytest.R;
import com.test.xzh.mytest.entity.ZhuangbiImage;
import com.test.xzh.mytest.net.MyNetwork;
import com.test.xzh.mytest.view.MyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by xzh on 2017/3/8
 */

public class ElementaryFragment extends BaseFragment {
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    MyAdapter adapter = new MyAdapter();
    Observer<List<ZhuangbiImage>> observer = new Observer<List<ZhuangbiImage>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<ZhuangbiImage> images) {
            swipeRefreshLayout.setRefreshing(false);
            adapter.setImages(images);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };


    @OnCheckedChanged({R.id.searchRb1, R.id.searchRb2, R.id.searchRb3, R.id.searchRb4})
    void onTagChecked(RadioButton searchRb, boolean checked) {
        if (checked) {
            unsubscribe();
            adapter.setImages(null);
            swipeRefreshLayout.setRefreshing(true);
            search(searchRb.getText().toString());
        }
    }

    private void search(String s) {
            MyNetwork.getZBApi()
                    .search(s)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elementary, container, false);
        ButterKnife.bind(this, view);
        gridRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        gridRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;

    }

    @Override
    public int getTitle() {
        return R.string.title_elementary;
    }

    @Override
    public int getDialogTes() {
        return R.layout.dialog_elementary;
    }
}
