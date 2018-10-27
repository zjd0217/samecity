package com.wxkj.tongcheng.ui.fragment.city.search;

import com.wxkj.tongcheng.bean.HotSearchBean;

/**
 * Created by cheng on 2018/10/10.
 */

public interface SearchTypeView {
    void showMsg(String msg);
    void getHotSearchSuccess(HotSearchBean searchBean);
}
