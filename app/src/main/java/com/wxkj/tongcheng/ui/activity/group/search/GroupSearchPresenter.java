package com.wxkj.tongcheng.ui.activity.group.search;

import android.view.View;

import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.bean.ServiceDemandDetailBean;
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
 * @create 2018/10/11 0011
 * @Describe
 */
public class GroupSearchPresenter extends BasePresenter<GroupSearchView> {

    void getTeambuy(int page, String search_key) {
        ApiController.getService().getGroupTeambuyList(SPUtil.getInstance(view.getContext()).getUserId(),
                SPUtil.getInstance(view.getContext()).getToken(),
                Util.getPage(page), search_key, view.getSort(), view.getManNumNeed(),
                view.getTypeId(), view.getGoodsTp())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<GroupTitleBean.TeambuyBean>>() {
                    @Override
                    public void onNext(List<GroupTitleBean.TeambuyBean> list) {
                        view.getGroupTeambuyList(list);
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
    void getTypeList() {
        ApiController.getService().getGroupTypeList(view.getTypeId())
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


}
