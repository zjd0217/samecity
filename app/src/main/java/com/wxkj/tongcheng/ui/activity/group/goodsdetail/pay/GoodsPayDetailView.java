package com.wxkj.tongcheng.ui.activity.group.goodsdetail.pay;

import android.content.Context;

import com.wxkj.tongcheng.bean.DefaultAddressBean;

/**
 * @author Liu haijun
 * @create 2018/10/22 0022
 * @Describe
 */
public interface GoodsPayDetailView {

    Context getContext();

    void showMsg(String msg);

    void getDefaultAddress(DefaultAddressBean bean);

    int getAddressId();

    int getAccountChannel();

    int getGoodsIds();

    int getGoodsNums();

    String getGoodsPrices();

    int getBy();

    void success();

}
