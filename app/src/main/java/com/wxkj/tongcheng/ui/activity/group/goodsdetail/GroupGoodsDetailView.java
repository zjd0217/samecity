package com.wxkj.tongcheng.ui.activity.group.goodsdetail;

import android.content.Context;

import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/12 0012
 * @Describe
 */
public interface GroupGoodsDetailView {

    Context getContext();

    void showMsg(String msg);

    void getGoodsDetail(GroupGoodsDetailBean bean);

    int getGoodsId();

    int getTeambuy_id();

    void success();
}
