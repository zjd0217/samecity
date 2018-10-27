package com.wxkj.tongcheng.ui.activity.demand;

import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;

import java.util.List;

public class SelectTypeDemandPresenter extends BasePresenter<SelectTypeDemandView> {

    void getDemandTypeList() {
        ApiController.getService().getDemandTypeList(SPUtil.getInstance(view.getAppContent()).getUserId(),
                SPUtil.getInstance(view.getAppContent()).getToken())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver<>(new ObserverOnNextListener<List<SelectTypeDemandEntity>>() {
                    @Override
                    public void onNext(List<SelectTypeDemandEntity> list) {
                        view.getDemandTypeList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }


}
