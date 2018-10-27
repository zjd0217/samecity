package com.wxkj.tongcheng.ui.activity.city.searchresult;

import android.content.Context;

import com.wxkj.tongcheng.bean.CityScreenBean;
import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.bean.NearServiceBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/12.
 */

public interface SearchResultView {
    Context getcontext();
    void showMsg(String msg);
    int getType();
    int getPage();
    String getKey();
    String attribute_data_id();
    int getSort();
    void getServiceListSuccess(List<NearServiceBean> searchResultList);
    void getTypeListSuccess(List<CityTypeBean> typeList);
    void getScreenListSuccess(List<CityScreenBean> screenList);
}
