package com.wxkj.tongcheng.ui.fragment.home;

import com.wxkj.tongcheng.bean.HomeBannerClassifyBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    void getBannerData() {
        ApiController.getService()
                .getHomeBannerClassify(SPUtil.getInstance(view.getAppContent()).getUserId(),
                        SPUtil.getInstance(view.getAppContent()).getToken(),
                        "")
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver<>(new ObserverOnNextListener<HomeBannerClassifyBean>() {
                    @Override
                    public void onNext(HomeBannerClassifyBean homeBannerClassifyBean) {
                        view.bannerClassify(homeBannerClassifyBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showBannerMsg(e.getMessage());
                    }
                })));
    }


    void getSeckillList(int page) {
        ApiController.getService()
                .getHomeSeckillList(SPUtil.getInstance(view.getAppContent()).getUserId(),
                        SPUtil.getInstance(view.getAppContent()).getToken(), Util.getPage(page))
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver<>(new ObserverOnNextListener<List<HomeBannerClassifyBean.SeckillEntity>>() {
                    @Override
                    public void onNext(List<HomeBannerClassifyBean.SeckillEntity> mList) {
                        view.seckillList(mList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showSeckillMsg(e.getMessage());
                    }
                })));
    }

}
