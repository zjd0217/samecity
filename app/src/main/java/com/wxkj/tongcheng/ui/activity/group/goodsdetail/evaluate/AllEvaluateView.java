package com.wxkj.tongcheng.ui.activity.group.goodsdetail.evaluate;

import android.content.Context;

import com.wxkj.tongcheng.bean.EvaluateBean;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/17 0017
 * @Describe
 */
public interface AllEvaluateView {

    Context getContext();

    void showMsg(String msg);

    int getGoodsId();

    int getPage();

    String getGradeGoods();

    void getEvaluateBeanList(List<EvaluateBean> list);

    void success(boolean isCollect);
}
