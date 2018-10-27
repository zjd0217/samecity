package com.wxkj.tongcheng.ui.fragment.mine.changephone;

import com.wxkj.tongcheng.bean.CheckPhoneBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.bean.SmsCodeBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;

/**
 * Created by cheng on 2018/10/14.
 */

public class ChangePhonePresenter extends BasePresenter<ChangePhoneView> {

    /**
     * 获取验证码
     * @param phone
     * @param code
     */
    void getSmsCode(String phone,int code){
        ApiController.getService().getSmsCode(phone,code)
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
     * 验证手机号和验证码
     * @param code
     */
    void checkPhone(String code){
        if (code.length()!=6){
            view.showMsg("请输入6位验证码");
            return;
        }
        ApiController.getService()
                .checkPhoneCode(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        code)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(CheckPhoneBean.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<CheckPhoneBean>() {
                    @Override
                    public void onNext(CheckPhoneBean checkPhoneBean) {
                        view.checkCodeSuccess(checkPhoneBean.getMsg_id());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }

    /**
     * 修改手机号
     */
    void changePhone(String phone,String code,int msgId){
        if (phone.length()!=11&&!phone.startsWith("1")){
            view.showMsg("请输入正确的手机号码");
            return;
        }
        if (code.length()!=6){
            view.showMsg("请输入6位验证码");
            return;
        }

        ApiController.getService()
                .changePhone(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        msgId,
                        phone,
                        code)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.changePhoneSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }
}
