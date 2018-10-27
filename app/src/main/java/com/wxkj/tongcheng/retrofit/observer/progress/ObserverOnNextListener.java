package com.wxkj.tongcheng.retrofit.observer.progress;

/**
 * 网络请求的数据回调接口
 * Created by cheng on 2018/10/7.
 */

public interface ObserverOnNextListener<T> {

    public void onNext(T t);

    public void onError(Throwable e);
}
