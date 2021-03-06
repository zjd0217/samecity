package com.wxkj.tongcheng.ui.activity.mine.user.changepwd;

import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.bean.SmsCodeBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

/**
 * Created by cheng on 2018/10/15.
 */

public class ChangePwdPresenter extends BasePresenter<ChangePwdView> {
    /**
     * 获取验证码
     * @param phone
     */
    void getCode(String phone){
        ApiController.getService().getSmsCode(phone,3)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SmsCodeBean.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<SmsCodeBean>() {
                    @Override
                    public void onNext(SmsCodeBean smsCodeBean) {
                        view.getCodeSuccess(smsCodeBean.getCode());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 修改密码
     * @param code
     * @param pwd
     */
    void changePwd(String code,String pwd){
        ApiController.getService()
                .changePwd(SPUtil.getInstance(view.getcontext()).getUserId(),
                        code,
                        Util.strToMD5(pwd))
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.changePwdSuccess("修改密码成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }
}
