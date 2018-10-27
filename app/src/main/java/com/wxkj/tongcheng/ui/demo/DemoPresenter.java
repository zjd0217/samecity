package com.wxkj.tongcheng.ui.demo;

import com.wxkj.tongcheng.bean.DownloadTypeBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.bean.SmsCodeBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;

import java.util.List;

/**
 * Created by cheng on 2018/10/7.
 */

public class DemoPresenter extends BasePresenter<DemoView> {

    void getSmsCode() {
        //不带弹窗使用CommonObserver并且调用addObserver方法 不带数据bean 访问服务器
        ApiController.getService().getSmsCode("18980276159", 5)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SmsCodeBean.class))
                .subscribe(addObserver(new CommonObserver<>(new ObserverOnNextListener<SmsCodeBean>() {
                    @Override
                    public void onNext(SmsCodeBean bean) {
                        view.getCodeSuccess("获取验证码成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }


    void register(String code) {
        //带弹窗使用ProgressObserver  不调用addObserver 不带数据bean 访问服务器
        ApiController.getService().register("18980276159", code, "123456", "")
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.registerSuccess("注册成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }

    void pwdLogin() {
        //带弹窗 带数据bean 访问服务器
        ApiController.getService().pwdLogin("18980276159", "123456")
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(UserInfo.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<UserInfo>() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        view.loginSuccess(userInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }


    void getListData() {
        //带弹窗 带集合数据bean 访问服务器
        ApiController.getService().getDownloadType()
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<List<DownloadTypeBean>>() {
                    @Override
                    public void onNext(List<DownloadTypeBean> downloadTypeList) {
                        view.getDownloadTypeSuccess(downloadTypeList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }
}
