package com.wxkj.tongcheng.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * @author: lujialei
 * @date: 2018/10/24
 * @describe:
 */


public class HomeBigProductBean implements MultiItemEntity {
    public static final int TYPE_GOODS = 1;
    public static final int TYPE_TIME = 2;
    private int type;

    public HomeBigProductBean(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
