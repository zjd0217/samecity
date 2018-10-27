package com.wxkj.tongcheng.ui.activity.demand;

import android.content.Context;

import java.util.List;

public interface SelectTypeDemandView {

    Context getAppContent();

    void showMsg(String msg);

    void getDemandTypeList(List<SelectTypeDemandEntity> mList);

}
