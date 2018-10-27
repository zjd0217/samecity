package com.wxkj.tongcheng.ui.activity.mine.login;

import android.content.Context;

import com.wxkj.tongcheng.bean.UserInfo;

/**
 * Created by cheng on 2018/10/10.
 */

public interface LoginView {
    Context getcontext();
    void showMsg(String msg);
    void loginSuccess(UserInfo userInfo);
    void getCodeSuccess(String s,String code);
    void registerSuccess();
}
