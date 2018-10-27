package com.wxkj.tongcheng.ui.fragment.city.servicedemand;

import com.wxkj.tongcheng.bean.NearServiceBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public interface NearServiceView {
    int getType();
    int getPage();
    void showMsg(String msg);
    void getServiceListSuccess(List<NearServiceBean> serviceList);
}
