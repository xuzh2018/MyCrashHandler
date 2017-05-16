package com.test.xzh.mytest.view;

import android.content.Context;

import com.test.xzh.mytest.net.ApiException;
import com.test.xzh.mytest.net.ProgressCancelListener;

import io.reactivex.Observer;
import io.reactivex.subscribers.DisposableSubscriber;


/**
 * Created by xzh on 2017/3/3
 */
public abstract class ProgressSubscriber<T> extends DisposableSubscriber<T> implements ProgressCancelListener,Observer<T> {
    private SimpleLoadDialog dialogHandler;

    public ProgressSubscriber(Context context) {
        dialogHandler = new SimpleLoadDialog(context, this, true);
    }

    @Override
    public void onCancelProgress() {
        if (!this.isDisposed()) {
            this.dispose();
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }


    private void dismissProgressDialog() {
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();
            ;
            dialogHandler = null;
        }
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.show();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (false) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }
        dismissProgressDialog();
    }

    protected abstract void _onError(String s);

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    protected abstract void _onNext(T t);
}
