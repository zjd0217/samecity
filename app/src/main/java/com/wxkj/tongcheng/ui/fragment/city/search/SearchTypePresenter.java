package com.wxkj.tongcheng.ui.fragment.city.search;

import com.wxkj.tongcheng.bean.HotSearchBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;

import java.util.List;

/**
 * Created by cheng on 2018/10/10.
 */

public class SearchTypePresenter extends BasePresenter<SearchTypeView> {
    /**
     * 获取热门词汇
     * @param type
     */
    void getHotData(int type){
        ApiController.getService()
                .getHotSearchData(type)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<HotSearchBean>() {
                    @Override
                    public void onNext(HotSearchBean hotSearchBeans) {
                        view.getHotSearchSuccess(hotSearchBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }
}
