package com.wxkj.tongcheng.ui.activity.demand.category;

import android.content.Context;

public interface CategoryView {

    Context getAppContent();

    int getTypeId();

    void getCategoryData(CategoryEntity entity);

    void showMsg(String msg);
}
