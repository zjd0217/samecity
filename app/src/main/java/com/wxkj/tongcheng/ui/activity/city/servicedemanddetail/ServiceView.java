package com.wxkj.tongcheng.ui.activity.city.servicedemanddetail;

import android.content.Context;

import com.wxkj.tongcheng.bean.ServiceDemandDetailBean;

/**
 * Created by cheng on 2018/10/9.
 */

public interface ServiceView {
    Context getcontext();
    void showMsg(String msg);
    int getServiceId();
    void getDetailSuccess(ServiceDemandDetailBean detailBean);
    void collectSuccess(String s);
    void deleteCollectSuccess(String s);
}
