package com.wxkj.tongcheng.ui.activity.city.servicedemanddetail;

import android.util.Log;

import com.wxkj.tongcheng.bean.ServiceDemandDetailBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;

/**
 * Created by cheng on 2018/10/9.
 */

public class ServicePresenter extends BasePresenter<ServiceView> {
    /**
     * 获取服务详情
     */
    void getServiceDetail(){
        Log.e("qwer","userid:"+SPUtil.getInstance(view.getcontext()).getUserId());
        Log.e("qwer","token:"+ SPUtil.getInstance(view.getcontext()).getToken());

        ApiController.getService().getServiceDetail(SPUtil.getInstance(view.getcontext()).getUserId(),
                SPUtil.getInstance(view.getcontext()).getToken(),view.getServiceId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(ServiceDemandDetailBean.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<ServiceDemandDetailBean>() {
                    @Override
                    public void onNext(ServiceDemandDetailBean detailBean) {
                        view.getDetailSuccess(detailBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 获取需求详情
     */
    void getDemandDetail(){
        ApiController.getService().getDemandDetail(SPUtil.getInstance(view.getcontext()).getUserId(),
                SPUtil.getInstance(view.getcontext()).getToken(),view.getServiceId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(ServiceDemandDetailBean.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<ServiceDemandDetailBean>() {
                    @Override
                    public void onNext(ServiceDemandDetailBean detailBean) {
                        view.getDetailSuccess(detailBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }


    /**
     * 收藏
     * @param type
     */
    void collect(int type){
        ApiController.getService()
                .collect(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        type==0?2:1,
                        view.getServiceId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.collectSuccess("收藏成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }


    void deleteCollect(){
        ApiController.getService()
                .deleteCollect(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        view.getServiceId())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.deleteCollectSuccess("取消收藏成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }
}
