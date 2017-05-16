package com.test.xzh.mytest.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.test.xzh.mytest.R;
import com.test.xzh.mytest.entity.MyData;
import com.test.xzh.mytest.entity.MyDataBase;
import com.test.xzh.mytest.entity.ZhuangbiImage;
import com.test.xzh.mytest.view.MyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by xzh on 2017/3/8
 */

public class CacheFragment extends BaseFragment {
    @BindView(R.id.loadingTimeTv)
    AppCompatTextView loadingTimeTv;
    @BindView(R.id.loadBt)
    AppCompatButton loadBt;
    @BindView(R.id.tipBt)
    Button tipBt;
    @BindView(R.id.cacheRv)
    RecyclerView cacheRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.clearMemoryCacheBt)
    AppCompatButton clearMemoryCacheBt;
    @BindView(R.id.clearMemoryAndDiskCacheBt)
    AppCompatButton clearMemoryAndDiskCacheBt;
    MyAdapter adapter = new MyAdapter();
    private long startingTimep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cache, container, false);
        ButterKnife.bind(this, view);
        cacheRv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        cacheRv.setAdapter(adapter);
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        swipeRefreshLayout.setEnabled(false);
        return view;
    }

    @Override
    public int getTitle() {
        return R.string.title_cache;
    }

    @Override
    public int getDialogTes() {
        return R.layout.dialog_cache;
    }

    @OnClick({R.id.loadBt, R.id.clearMemoryCacheBt, R.id.clearMemoryAndDiskCacheBt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loadBt:
                swipeRefreshLayout.setRefreshing(true);
                startingTimep = System.currentTimeMillis();
                unsubscribe();
                MyData.getInstance().subscribeData(new Observer<List<ZhuangbiImage>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(List<ZhuangbiImage> list) {
                                swipeRefreshLayout.setRefreshing(false);
                                int loadingtime = (int) (System.currentTimeMillis()-startingTimep);
                                loadingTimeTv.setText(getString(R.string.loading_time_and_source, loadingtime+"", MyData.getInstance().getDataSourceText()));
                                adapter.setImages(list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.clearMemoryCacheBt:
                MyData.getInstance().clearMemoryCache();
                adapter.setImages(null);
                Toast.makeText(getActivity(), R.string.memory_cache_cleared, Toast.LENGTH_SHORT).show();

                break;
            case R.id.clearMemoryAndDiskCacheBt:
                MyData.getInstance().clearMemoryAndDiskCache();
                adapter.setImages(null);
                Toast.makeText(getActivity(), R.string.memory_and_disk_cache_cleared, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
