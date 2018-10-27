package com.wxkj.tongcheng.ui.activity.mine.user.setting.address;

import android.content.Context;

import com.wxkj.tongcheng.bean.ReceivingAddressBean;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/19 0019
 * @Describe
 */
public interface ReceivingAddressView {

    Context getcontext();

    void showMsg(String msg);

    void getAddressList(List<ReceivingAddressBean> list);

    void success();
}
