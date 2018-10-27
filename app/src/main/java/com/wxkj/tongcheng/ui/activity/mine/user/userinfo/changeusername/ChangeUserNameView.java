package com.wxkj.tongcheng.ui.activity.mine.user.userinfo.changeusername;

import android.content.Context;

/**
 * Created by cheng on 2018/10/13.
 */

public interface ChangeUserNameView {
    Context getcontext();
    void showMsg(String msg);
    void saveUserNameSuccess();
}
