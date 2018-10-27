package com.wxkj.tongcheng.ui.fragment.mine.mycollect;

import android.content.Context;

import com.wxkj.tongcheng.bean.CollectGoodsBean;
import com.wxkj.tongcheng.bean.CollectServiceBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/20.
 */

public interface CollectView {
    Context getcontext();
    int getType();
    int getPage();
    void showMsg(String msg);
    void getServiceCollectListSuccess(List<CollectServiceBean> collectList);
    void getGoodsCollectListSuccess(List<CollectGoodsBean> collectList);
}
