package com.wxkj.tongcheng.ui.activity.mine.user.mycoupon;

import com.wxkj.tongcheng.bean.CouponBean;
import com.wxkj.tongcheng.bean.WalletNumBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/24 0024
 * @Describe
 */
public class CouponPresenter extends BasePresenter<CouponView> {

    /**
     * 获取优惠券列表
     */
    void getCouponList() {
        ApiController.getService()
                .getCouponList(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        Util.getPage(view.getPage()), view.getGoodsId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<CouponBean>>() {
                    @Override
                    public void onNext(List<CouponBean> couponBeanList) {
                        view.getCouponList(couponBeanList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

}
