package com.wxkj.tongcheng.ui.fragment.home.search;

import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.bean.HotSearchBean;
import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

public class HomeSearchPresenter extends BasePresenter<HomeSearchView> {

    void getTeambuy(int page, String search_key) {
        ApiController.getService().getGroupTeambuyList(SPUtil.getInstance(view.getAppContent()).getUserId(),
                SPUtil.getInstance(view.getAppContent()).getToken(),
                Util.getPage(page), search_key, 0, "", view.getTypeId(), -1)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<GroupTitleBean.TeambuyBean>>() {
                    @Override
                    public void onNext(List<GroupTitleBean.TeambuyBean> list) {
                        view.getGroupList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 获取服务列表
     */
    void getServiceList(int page, String keyWords) {
        ApiController.getService()
                .getCitySearchServiceList(SPUtil.getInstance(view.getAppContent()).getUserId(),
                        SPUtil.getInstance(view.getAppContent()).getToken(),
                        view.getTypeId(),
                        Util.getPage(page),
                        keyWords,
                        "",
                        view.getSort())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<NearServiceBean>>() {
                    @Override
                    public void onNext(List<NearServiceBean> list) {
                        view.getNearbyList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 获取需求列表
     */
    void getDemandList(int page, String keyWords) {
        ApiController.getService()
                .getCitySearchDemandList(SPUtil.getInstance(view.getAppContent()).getUserId(),
                        SPUtil.getInstance(view.getAppContent()).getToken(),
                        view.getTypeId(),
                        Util.getPage(page),
                        keyWords,
                        "",
                        view.getSort())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<NearServiceBean>>() {
                    @Override
                    public void onNext(List<NearServiceBean> list) {
                        view.getNearbyList(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }
                })));
    }

    /**
     * //0是服务 1是需求
     */
    void getNearbyHotData(int type) {
        ApiController.getService()
                .getHotSearchData(type)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<HotSearchBean>() {
                    @Override
                    public void onNext(HotSearchBean hotSearchBeans) {
                        view.getNearbyHot(hotSearchBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showErrorMsg(e.getMessage());
                    }
                })));
    }
}
