package com.wxkj.tongcheng.mvp;

import com.wxkj.tongcheng.retrofit.observer.CommonObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * presenter基类
 * Created by cheng on 2018/10/7.
 */

public class BasePresenter<V> {

    public V view;
    protected List<CommonObserver> list;

    protected CommonObserver addObserver(CommonObserver c){
        if (null==list) list=new ArrayList<>();
        list.add(c);
        return c;
    }

    //加载View,建立连接
    public void addView(V v) {
        this.view = v;
    }

    //断开连接
    public void detattch() {
        if (view != null) {
            view = null;
        }
        if (null!=list&&list.size()!=0){
            for (CommonObserver c:list){
                c.dispose();
            }
            list.clear();
            list=null;
        }
    }
}
