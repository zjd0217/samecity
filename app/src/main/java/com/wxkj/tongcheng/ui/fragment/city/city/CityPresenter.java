package com.wxkj.tongcheng.ui.fragment.city.city;

import com.flyco.tablayout.widget.MsgView;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;

import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public class CityPresenter extends BasePresenter<CityView> {

    void getBannerData(){
        ApiController.getService().getCityBannerData()
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(CityBannerBean.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<CityBannerBean>() {
                    @Override
                    public void onNext(CityBannerBean cityBannerBeans) {
                        view.getBannerSuccess(cityBannerBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }
}
