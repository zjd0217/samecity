package com.wxkj.tongcheng.ui.activity.mine.user.changepwd;

import android.content.Context;

/**
 * Created by cheng on 2018/10/15.
 */

public interface ChangePwdView {
    Context getcontext();
    void showMsg(String msg);
    void getCodeSuccess(String code);
    void changePwdSuccess(String s);
}
