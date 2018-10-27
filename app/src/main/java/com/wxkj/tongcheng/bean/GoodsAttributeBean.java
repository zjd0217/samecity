package com.wxkj.tongcheng.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/21 0021
 * @Describe 商品属性整理之后的数据实体
 */
public class GoodsAttributeBean implements Serializable {
    private String attributeKey;
    private List<String> childrenList;
    /** 上次点击的位置 */
    private int preClickPosition = -1;


    public int getPreClickPosition() {
        return preClickPosition;
    }

    public void setPreClickPosition(int preClickPosition) {
        this.preClickPosition = preClickPosition;
    }

    public String getAttributeKey() {
        return attributeKey;
    }

    public void setAttributeKey(String attributeKey) {
        this.attributeKey = attributeKey;
    }

    public List<String> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<String> childrenList) {
        this.childrenList = childrenList;
    }


}
