package com.wxkj.tongcheng.ui.activity.group.goodsdetail.pay;

import android.view.View;

import com.wxkj.tongcheng.bean.DefaultAddressBean;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;

/**
 * @author Liu haijun
 * @create 2018/10/22 0022
 * @Describe 商品下单
 */
public class GoodsPayDetailPresenter extends BasePresenter<GoodsPayDetailView> {


    void getDefaultAddress() {
        ApiController.getService()
                .getDefaultAddress(SPUtil.getInstance(view.getContext()).getUserId(),
                        SPUtil.getInstance(view.getContext()).getToken())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(DefaultAddressBean.class))
                .subscribe(addObserver(new CommonObserver<DefaultAddressBean>(new ObserverOnNextListener<DefaultAddressBean>() {
                    @Override
                    public void onNext(DefaultAddressBean bean) {
                        view.getDefaultAddress(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    void saveOrder() {
        ApiController.getService()
                .saveOrder(SPUtil.getInstance(view.getContext()).getUserId(),
                        SPUtil.getInstance(view.getContext()).getToken(), 0, view.getAddressId(), -1,
                        0, "", -1, view.getAccountChannel(),
                        view.getGoodsIds(), view.getGoodsNums(), view.getGoodsPrices(), view.getGoodsIds()+"",
                        view.getBy())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(addObserver(new CommonObserver<SimpleResponseEntity>(new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity bean) {
                        view.success();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));

    }

}
