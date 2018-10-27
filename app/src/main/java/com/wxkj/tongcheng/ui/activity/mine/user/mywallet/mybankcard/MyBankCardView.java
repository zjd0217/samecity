package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mybankcard;

import android.content.Context;

import com.wxkj.tongcheng.bean.BankCardListBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/18.
 */

public interface MyBankCardView {
    Context getcontext();
    void showMsg(String msg);
    void getBankCardListSuccess(List<BankCardListBean> bankCardList);
    void addBankCardSuccess();
}
