package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.setforwardpwd;

import android.content.Context;

/**
 * Created by cheng on 2018/10/16.
 */

public interface PwdView {
    Context getcontext();
    void showMsg(String msg);
    void getCodeSuccess(String code);
    void setPwdSuccess();
}
