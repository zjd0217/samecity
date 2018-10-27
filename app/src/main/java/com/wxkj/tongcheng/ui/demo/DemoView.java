package com.wxkj.tongcheng.ui.demo;

import android.content.Context;

import com.wxkj.tongcheng.bean.DownloadTypeBean;
import com.wxkj.tongcheng.bean.UserInfo;

import java.util.List;

/**
 * Created by cheng on 2018/10/7.
 */

public interface DemoView {
    Context getcontext();
    void showMsg(String msg);
    void getCodeSuccess(String s);
    void registerSuccess(String s);
    void loginSuccess(UserInfo userInfo);
    void getDownloadTypeSuccess(List<DownloadTypeBean> downloadTypeList);
}
