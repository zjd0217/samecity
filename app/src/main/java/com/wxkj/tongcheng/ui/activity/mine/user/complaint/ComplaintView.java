package com.wxkj.tongcheng.ui.activity.mine.user.complaint;

import android.content.Context;

/**
 * Created by cheng on 2018/10/20.
 */

public interface ComplaintView {
    Context getcontext();
    void showMsg(String msg);
    void submitSuccess();
}
