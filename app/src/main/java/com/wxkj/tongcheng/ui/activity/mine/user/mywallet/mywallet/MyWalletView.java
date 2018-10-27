package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mywallet;

import android.content.Context;

import com.wxkj.tongcheng.bean.WalletNumBean;

/**
 * Created by cheng on 2018/10/16.
 */

public interface MyWalletView {
    Context getcontext();
    void showMsg(String msg);
    void getWalletNumSuccess(WalletNumBean walletNumBean);
}
