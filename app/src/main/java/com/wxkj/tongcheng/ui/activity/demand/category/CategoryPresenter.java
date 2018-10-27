package com.wxkj.tongcheng.ui.activity.demand.category;

import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;

public class CategoryPresenter extends BasePresenter<CategoryView> {

    void getDemandSecondLevelType() {
        ApiController.getService().getDemandSecondLevelType(SPUtil.getInstance(view.getAppContent()).getUserId(),
                SPUtil.getInstance(view.getAppContent()).getToken(), view.getTypeId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver<>(new ObserverOnNextListener<CategoryEntity>() {
                    @Override
                    public void onNext(CategoryEntity entity) {
                        view.getCategoryData(entity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

}
