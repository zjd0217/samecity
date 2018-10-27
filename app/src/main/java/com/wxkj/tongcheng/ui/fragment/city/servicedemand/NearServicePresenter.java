package com.wxkj.tongcheng.ui.fragment.city.servicedemand;

import android.util.Log;

import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public class NearServicePresenter extends BasePresenter<NearServiceView> {
    void getNearServiceData(){
        ApiController.getService().getNearServiceList(Util.getPage(view.getPage()),view.getType())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<NearServiceBean>>() {
                    @Override
                    public void onNext(List<NearServiceBean> cityServiceBeans) {
                        view.getServiceListSuccess(cityServiceBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }
}
