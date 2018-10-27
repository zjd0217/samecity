package com.wxkj.tongcheng.ui.fragment.group;

import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public interface GroupView {

    void showMsg(String msg);

    void getBannerSuccess(GroupTitleBean groupTitleBean);


}
