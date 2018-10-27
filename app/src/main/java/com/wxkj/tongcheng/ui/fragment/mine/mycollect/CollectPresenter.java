package com.wxkj.tongcheng.ui.fragment.mine.mycollect;

import com.wxkj.tongcheng.bean.CollectGoodsBean;
import com.wxkj.tongcheng.bean.CollectServiceBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * Created by cheng on 2018/10/20.
 */

public class CollectPresenter extends BasePresenter<CollectView> {
    /**
     * 获取服务和需求的收藏
     */
    void getServiceDemand(){
        ApiController.getService()
                .getServiceDemanCollectList(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        Util.getPage(view.getPage()),
                        view.getType())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<CollectServiceBean>>() {
                    @Override
                    public void onNext(List<CollectServiceBean> collectBeans) {
                        view.getServiceCollectListSuccess(collectBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 获取收藏商品 列表
     */
    void getCollectGoodsList(){
        ApiController.getService()
                .getCollectGoodsList(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        Util.getPage(view.getPage()),
                        view.getType())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<CollectGoodsBean>>() {
                    @Override
                    public void onNext(List<CollectGoodsBean> collectBeans) {
                        view.getGoodsCollectListSuccess(collectBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }
}
