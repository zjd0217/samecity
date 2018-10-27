package com.wxkj.tongcheng.ui.activity.demand.describe;

import android.content.Context;

public interface DescribeView {

    Context getContext();

    void showMsg(String msg);

    int getLevelType1();

    int getLevelType2();

    String getInfoTitle();

    String getNickname();

    String getMobile();

    String getTimeOverdue();

    String getBaiduPos();

    String getAreaId();

    String getAddress();

    String getDesc();

    String getInfoVoice();

}
