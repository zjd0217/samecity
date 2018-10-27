package com.wxkj.tongcheng.ui.activity.group.goodsdetail.shop;

import android.content.Context;

import com.wxkj.tongcheng.bean.EvaluateBean;
import com.wxkj.tongcheng.bean.ShopDetailGoodsBean;
import com.wxkj.tongcheng.bean.ShopDetailsBean;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/18 0018
 * @Describe
 */
public interface ShopDetailsView {

    Context getContext();

    void showMsg(String msg);

    int getShopId();

    int getOdby();

    int getPage();

    void getShopDetails(ShopDetailsBean bean);

    void getShopGoodsList(List<ShopDetailGoodsBean> list);


    void success();
}
