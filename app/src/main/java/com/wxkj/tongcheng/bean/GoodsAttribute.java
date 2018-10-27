package com.wxkj.tongcheng.bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Liu haijun
 * @create 2018/10/21 0021
 * @Describe
 */
public class GoodsAttribute implements Serializable {

    /**
     * goods_id : 8
     * goods_name : ipad air2金色512G
     * classify_id : 6
     * attribute_id : 9,8;10,9
     * attribute_value : 颜色,金色;内存,512G
     * num_stock_left : 333
     * goods_unit : 台
     * price_current : 380000
     * price_member : 378100
     */

    private int goods_id;
    private String goods_name;
    private int classify_id;
    private String attribute_id;
    private String attribute_value;
    private int num_stock_left;
    private String goods_unit;
    private int price_current;
    private int price_member;
    /** 商品数量 */
    private int shopCount = 1;
    /** 整理之后的原始数据 */
    private LinkedHashMap<String, String> map = new LinkedHashMap<>();
    /** 所有的键 */
    private LinkedHashSet<String> keySet = new LinkedHashSet<>();
    /** 所有的值 */
    private LinkedHashSet<String> valueSet = new LinkedHashSet<>();


    public LinkedHashSet<String> getValueSet() {
        LinkedHashSet<String> keySet = getKeySet();
        for (String str : keySet) {
            valueSet.add(map.get(str));
        }
        return valueSet;
    }

    public LinkedHashSet<String> getKeySet() {
        for (String str : getMap().keySet()) {
            keySet.add(str);
        }
        return keySet;
    }


    /**
     * 将原始数据分割
     *
     * @return
     */
    public LinkedHashMap<String, String> getMap() {
        if (!TextUtils.isEmpty(attribute_value)) {
            String[] split = attribute_value.split(";");
            for (int i = 0; i < split.length; i++) {
                String s = split[i];
                String[] split1 = s.split(",");
                if (split1.length == 2) {
                    map.put(split1[0], split1[1]);
                }
            }
        }
        return map;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    public String getAttribute_id() {
        return attribute_id;
    }

    public void setAttribute_id(String attribute_id) {
        this.attribute_id = attribute_id;
    }

    public String getAttribute_value() {
        return attribute_value;
    }

    public void setAttribute_value(String attribute_value) {
        this.attribute_value = attribute_value;
    }

    public int getNum_stock_left() {
        return num_stock_left;
    }

    public void setNum_stock_left(int num_stock_left) {
        this.num_stock_left = num_stock_left;
    }

    public String getGoods_unit() {
        return goods_unit;
    }

    public void setGoods_unit(String goods_unit) {
        this.goods_unit = goods_unit;
    }

    public int getPrice_current() {
        return price_current;
    }

    public void setPrice_current(int price_current) {
        this.price_current = price_current;
    }

    public int getPrice_member() {
        return price_member;
    }

    public void setPrice_member(int price_member) {
        this.price_member = price_member;
    }
}
