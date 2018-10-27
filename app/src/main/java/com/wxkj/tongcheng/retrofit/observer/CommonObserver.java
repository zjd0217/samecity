package com.wxkj.tongcheng.retrofit.observer;

import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;

import java.net.ConnectException;
import java.net.SocketException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 普通的observer
 * Created by cheng on 2018/10/7.
 */

public class CommonObserver<T> implements Observer<T> {
    private ObserverOnNextListener<T> listener;
    private Disposable d;

    public CommonObserver(ObserverOnNextListener<T> listener) {
        this.listener=listener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof Error) {
            listener.onError(e);
        } else {
            if (e instanceof ConnectException || e instanceof TimeoutException || e instanceof SocketException) {
                listener.onError(new Error("网络异常，请稍后重试!"));
            }else {
                listener.onError(new Error("服务器出错"));
            }
        }
    }

    @Override
    public void onComplete() {

    }

    public boolean isDisposed() {
        return d == null || d.isDisposed();
    }

    public void dispose() {
        if (!isDisposed())
            d.dispose();
    }

}
