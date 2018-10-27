package com.wxkj.tongcheng.ui.fragment.mine.changephone;

import android.content.Context;

/**
 * Created by cheng on 2018/10/14.
 */

public interface ChangePhoneView {
    Context getcontext();
    void showMsg(String msg);
    void getCodeSuccess(String s);
    void checkCodeSuccess(int msgId);
    void changePhoneSuccess();
}
