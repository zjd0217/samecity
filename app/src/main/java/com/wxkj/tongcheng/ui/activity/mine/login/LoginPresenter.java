package com.wxkj.tongcheng.ui.activity.mine.login;

import android.text.TextUtils;

import com.wxkj.tongcheng.bean.RegisterBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.bean.SmsCodeBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.Util;

/**
 * Created by cheng on 2018/10/10.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    /**
     * 手机号 密码登录
     *
     * @param phone
     * @param pwd
     */
    public void phoneLogin(String phone, String pwd) {
        if (TextUtils.isEmpty(phone) || !phone.startsWith("1")) {
            view.showMsg("请输入正确的手机号码");
            return;
        } else if (pwd.length() < 6) {
            view.showMsg("请输入6位以上密码");
            return;
        }
        ApiController.getService().pwdLogin(phone, Util.strToMD5(pwd))
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(UserInfo.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<UserInfo>() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        userInfo.setPhone(phone);
                        view.loginSuccess(userInfo);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void getCode(String phone, int code) {
        ApiController.getService().getSmsCode(phone, code)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SmsCodeBean.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<SmsCodeBean>() {
                    @Override
                    public void onNext(SmsCodeBean bean) {
                        view.getCodeSuccess("获取验证码成功", bean.getCode());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 注册
     *
     * @param bean
     */
    public void register(RegisterBean bean) {
        if (TextUtils.isEmpty(bean.getPhone()) || !bean.getPhone().startsWith("1")) {
            view.showMsg("请输入正确的手机号码");
        } else if (bean.getCode().length() != 6) {
            view.showMsg("请输入6位手机验证码");
        } else if (bean.getPwd().length() < 6) {
            view.showMsg("请输入6位以上密码");
        } else if (!bean.getPwd().equals(bean.getPwd2())) {
            view.showMsg("两次密码不一致");
        } else {
            ApiController.getService().register(bean.getPhone(), bean.getCode(), Util.strToMD5(bean.getPwd()), bean.getOtherPhone())
                    .compose(RxSchedulers.io_main())
                    .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                    .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                        @Override
                        public void onNext(SimpleResponseEntity simpleResponseEntity) {
                            view.registerSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showMsg(e.getMessage());
                        }
                    }));
        }
    }

    /**
     * 修改密码
     *
     * @param bean
     */
    public void resetPwd(RegisterBean bean) {
        if (TextUtils.isEmpty(bean.getPhone()) || !bean.getPhone().startsWith("1")) {
            view.showMsg("请输入正确的手机号码");
        } else if (bean.getCode().length() != 6) {
            view.showMsg("请输入6位手机验证码");
        } else if (bean.getPwd().length() < 6) {
            view.showMsg("请输入6位以上密码");
        } else if (!bean.getPwd().equals(bean.getPwd2())) {
            view.showMsg("两次密码不一致");
        } else {
            ApiController.getService().resetPwd(bean.getPhone(), bean.getCode(), Util.strToMD5(bean.getPwd()))
                    .compose(RxSchedulers.io_main())
                    .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                    .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                        @Override
                        public void onNext(SimpleResponseEntity simpleResponseEntity) {
                            view.registerSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showMsg(e.getMessage());
                        }
                    }));
        }
    }


    /**
     * 验证码登录
     *
     * @param phone
     * @param code
     */
    public void codeLogin(String phone, String code) {
        if (phone.length() != 11 || !phone.startsWith("1")) {
            view.showMsg("手机号有误");
        } else if (code.length() != 6) {
            view.showMsg("请输入6位手机验证码");
        } else {
            ApiController.getService().codeLogin(phone, code)
                    .compose(RxSchedulers.io_main())
                    .flatMap(ApiController.judgeData(UserInfo.class))
                    .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<UserInfo>() {
                        @Override
                        public void onNext(UserInfo userInfo) {
                            userInfo.setPhone(phone);
                            view.loginSuccess(userInfo);
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showMsg(e.getMessage());
                        }
                    }));

        }
    }
}
