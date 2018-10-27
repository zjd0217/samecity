package com.wxkj.tongcheng.ui.activity.mine.user.mycoupon;

import android.content.Context;

import com.wxkj.tongcheng.bean.CouponBean;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/24 0024
 * @Describe
 */
public interface CouponView {

    Context getcontext();

    void showMsg(String msg);

    int getGoodsId();

    int getPage();

    void getCouponList(List<CouponBean> couponBeanList);

}
