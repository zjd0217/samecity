package com.wxkj.tongcheng.ui.fragment.home.search;

import android.content.Context;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.bean.HotSearchBean;
import com.wxkj.tongcheng.bean.NearServiceBean;

import java.util.List;

public interface HomeSearchView {

    void showErrorMsg(String msg);

    void getGroupList(List<GroupTitleBean.TeambuyBean> mList);

    int getTypeId();

    int getSort();

    Context getAppContent();

    void getNearbyList(List<NearServiceBean> mList);

    void getNearbyHot(HotSearchBean searchBean);

}
