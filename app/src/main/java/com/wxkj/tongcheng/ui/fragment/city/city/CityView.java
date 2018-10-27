package com.wxkj.tongcheng.ui.fragment.city.city;

import com.wxkj.tongcheng.bean.CityBannerBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public interface CityView {
    void showMsg(String msg);
    void getBannerSuccess(CityBannerBean cityBannerList);
}
