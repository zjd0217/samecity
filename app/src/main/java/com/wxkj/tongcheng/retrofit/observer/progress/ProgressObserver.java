package com.wxkj.tongcheng.retrofit.observer.progress;

import android.content.Context;

import java.net.ConnectException;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 带有对话框的observer
 * Created by cheng on 2018/10/7.
 */

public class ProgressObserver<T> implements Observer<T>, ProgressDialogHandler.ProgressCancelListener {
    private ObserverOnNextListener<T> listener;
    private ProgressDialogHandler mProgressDialogHandler;
    private Context context;
    private Disposable d;

    public ProgressObserver(Context context, ObserverOnNextListener<T> listener) {
        this.listener = listener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, this);
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        if (e instanceof Error) {
            listener.onError(e);
        } else {
            if (e instanceof ConnectException || e instanceof TimeoutException || e instanceof SocketException) {
                listener.onError(new Error("网络异常，请稍后重试!"));
            } else {
                listener.onError(new Error("服务器出错"));
            }
        }
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    public boolean isDisposed() {
        return d == null || d.isDisposed();
    }

    public void dispose() {
        if (!isDisposed())
            d.dispose();
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
        }
    }
}
