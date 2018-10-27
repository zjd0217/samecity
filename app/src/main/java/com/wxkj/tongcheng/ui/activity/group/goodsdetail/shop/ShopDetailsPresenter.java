package com.wxkj.tongcheng.ui.activity.group.goodsdetail.shop;

import com.wxkj.tongcheng.bean.EvaluateBean;
import com.wxkj.tongcheng.bean.ShopDetailGoodsBean;
import com.wxkj.tongcheng.bean.ShopDetailsBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/18 0018
 * @Describe
 */
public class ShopDetailsPresenter extends BasePresenter<ShopDetailsView> {

    /**
     * 获取店铺信息
     */
    void getShopDetail() {

        ApiController
                .getService()
                .getShopDetail(view.getShopId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(ShopDetailsBean.class))
                .subscribe(addObserver(new CommonObserver<ShopDetailsBean>(new ObserverOnNextListener<ShopDetailsBean>() {
                    @Override
                    public void onNext(ShopDetailsBean bean) {
                        view.getShopDetails(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));

    }

    /**
     * 获取店铺商品列表
     */
    void getShopGoodsList() {

        ApiController
                .getService()
                .getGoodsList(view.getShopId(), view.getOdby(), Util.getPage(view.getPage()))
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(new ProgressObserver<>(view.getContext(), new ObserverOnNextListener<List<ShopDetailGoodsBean>>() {
                    @Override
                    public void onNext(List<ShopDetailGoodsBean> list) {
                        view.getShopGoodsList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }


    /**
     * 店铺收藏
     */
    void collect() {
        ApiController
                .getService()
                .collect(SPUtil.getInstance(view.getContext()).getUserId(),
                        SPUtil.getInstance(view.getContext()).getToken(), 4,
                        view.getShopId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getContext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity bean) {
                        view.success();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }

    /**
     * 取消收藏
     */
    public void cancelCollect(int collect_id) {
        ApiController.getService().deleteCollect(SPUtil.getInstance(view.getContext()).getUserId(),
                SPUtil.getInstance(view.getContext()).getToken(), collect_id)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getContext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity t) {
                        view.success();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }

}
