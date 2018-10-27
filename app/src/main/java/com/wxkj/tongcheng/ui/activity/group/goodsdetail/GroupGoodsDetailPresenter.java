package com.wxkj.tongcheng.ui.activity.group.goodsdetail;

import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;
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
 * @create 2018/10/12 0012
 * @Describe 拼团中商品详情P层
 */
public class GroupGoodsDetailPresenter extends BasePresenter<GroupGoodsDetailView> {

    /**
     * 商品详情
     */
    void getGroupGoodsDetail() {
        ApiController.getService().getGoodsDetail(SPUtil.getInstance(view.getContext()).getUserId(),
                SPUtil.getInstance(view.getContext()).getToken(), view.getGoodsId(), view.getTeambuy_id())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(GroupGoodsDetailBean.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<GroupGoodsDetailBean>() {
                    @Override
                    public void onNext(GroupGoodsDetailBean bean) {
                        view.getGoodsDetail(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 收藏商品
     */
    public void collectGoods() {
        ApiController.getService().collect(SPUtil.getInstance(view.getContext()).getUserId(),
                SPUtil.getInstance(view.getContext()).getToken(), 3, view.getGoodsId())
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
