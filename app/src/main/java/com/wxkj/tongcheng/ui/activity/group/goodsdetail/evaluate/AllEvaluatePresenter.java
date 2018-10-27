package com.wxkj.tongcheng.ui.activity.group.goodsdetail.evaluate;

import com.wxkj.tongcheng.bean.EvaluateBean;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
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
 * @create 2018/10/17 0017
 * @Describe
 */
public class AllEvaluatePresenter extends BasePresenter<AllEvaluateView> {


    void getEvaluateList() {
        ApiController
                .getService()
                .getEvaluateList(view.getGoodsId(), view.getGradeGoods(), Util.getPage(view.getPage()))
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(new ProgressObserver<List<EvaluateBean>>(view.getContext(), new ObserverOnNextListener<List<EvaluateBean>>() {
                    @Override
                    public void onNext(List<EvaluateBean> list) {
                        view.getEvaluateBeanList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
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
                        view.success(true);
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
                        view.success(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }


}
