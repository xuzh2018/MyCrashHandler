package com.test.xzh.mytest.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.test.xzh.mytest.R;

import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * Created by xzh on 2017/3/8
 */

public abstract class BaseFragment extends Fragment {
    protected Disposable disposable;
    @OnClick(R.id.tipBt)
    void tip(){
        new AlertDialog.Builder(getActivity())
                .setTitle(getTitle())
                .setView(getActivity().getLayoutInflater().inflate(getDialogTes(),null))
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
    }

    public void unsubscribe() {
        if (disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }

    }




    public abstract int getTitle();
    public abstract int getDialogTes();
}
