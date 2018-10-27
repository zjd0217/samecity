package com.wxkj.tongcheng.ui.activity.mine.user.userinfo.userinfo;

import android.content.Context;

/**
 * Created by cheng on 2018/10/13.
 */

public interface UserInfoView {
    Context getcontext();
    void showMsg(String msg);
    void changeHeadSuccess(String s);
    void changeSexSuccess(String s);
    void changeBirthdaySuccess(String s);
}
