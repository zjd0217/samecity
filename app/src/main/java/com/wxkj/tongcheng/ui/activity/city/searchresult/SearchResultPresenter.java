package com.wxkj.tongcheng.ui.activity.city.searchresult;

import com.wxkj.tongcheng.bean.CityScreenBean;
import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * Created by cheng on 2018/10/12.
 */

public class SearchResultPresenter extends BasePresenter<SearchResultView> {
    /**
     * 获取服务列表
     */
    void getServiceList(int typeId){
        ApiController.getService()
                .getCitySearchServiceList(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        typeId,
                        Util.getPage(view.getPage()),
                        view.getKey(),
                        view.attribute_data_id(),
                        view.getSort())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<NearServiceBean>>() {
                    @Override
                    public void onNext(List<NearServiceBean> list) {
                        view.getServiceListSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 获取需求列表
     */
    void getDemandList(int typeId){
        ApiController.getService()
                .getCitySearchDemandList(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        typeId,
                        Util.getPage(view.getPage()),
                        view.getKey(),
                        view.attribute_data_id(),
                        view.getSort())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<NearServiceBean>>() {
                    @Override
                    public void onNext(List<NearServiceBean> list) {
                        view.getServiceListSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 获取分类列表数据
     */
    void getTypeList(){
        ApiController.getService().getCityTypeList()
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<CityTypeBean>>() {
                    @Override
                    public void onNext(List<CityTypeBean> list) {
                        view.getTypeListSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 获取筛选集合数据
     */
    void getScreenList(){
        ApiController.getService()
                .getCityScreenList()
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<CityScreenBean>>() {
                    @Override
                    public void onNext(List<CityScreenBean> cityScreenBeans) {
                        view.getScreenListSuccess(cityScreenBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg("获取筛选数据失败");
                    }
                })));
    }

//    attribute_data_id
}
