package com.wxkj.tongcheng.ui.activity.group.search;

import android.content.Context;

import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;

import java.util.List;


/**
 * @author Liu haijun
 * @create 2018/10/11 0011
 * @Describe
 */
public interface GroupSearchView {
    Context getContext();

    void showMsg(String msg);

    void getGroupTeambuyList(List<GroupTitleBean.TeambuyBean> list);

    int getTypeId();

    int getSort();

    int getGoodsTp();

    String getManNumNeed();

    void getTypeListSuccess(List<CityTypeBean> list);


}
