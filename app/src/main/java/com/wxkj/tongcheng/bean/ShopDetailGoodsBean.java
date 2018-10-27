package com.wxkj.tongcheng.bean;

import java.io.Serializable;

/**
 * @author Liu haijun
 * @create 2018/10/18 0018
 * @Describe 店铺详情中的实体
 */
public class ShopDetailGoodsBean implements Serializable {


    /**
     * goods_id : 5
     * classify_id : 1
     * goods_name : ipad air金色
     * brand_name :
     * special_fg : 0
     * goods_labels : 啊说法萨芬,啊撒发生发射点发
     * goods_pic : http://127.0.0.1/peng/samecity/public/upload/upload/20180901/20180901092329921.jpg
     * type_id : 8
     * num_sold : 12
     * price_current : 500000
     * pay_return : 100,3
     * num_stock_left : 45
     * discuss_good : 0
     * discuss_normal : 0
     * discuss_bad : 0
     */

    private int goods_id;
    private int classify_id;
    private String goods_name;
    private String brand_name;
    private int special_fg;
    private String goods_labels;
    private String goods_pic;
    private int type_id;
    private int price_current;
    private String pay_return;
    private int num_stock_left;
    private int discuss_good;
    private int discuss_normal;
    private int discuss_bad;
    private int num_sold;

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public int getSpecial_fg() {
        return special_fg;
    }

    public void setSpecial_fg(int special_fg) {
        this.special_fg = special_fg;
    }

    public String getGoods_labels() {
        return goods_labels;
    }

    public void setGoods_labels(String goods_labels) {
        this.goods_labels = goods_labels;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getPrice_current() {
        return price_current;
    }

    public void setPrice_current(int price_current) {
        this.price_current = price_current;
    }

    public String getPay_return() {
        return pay_return;
    }

    public void setPay_return(String pay_return) {
        this.pay_return = pay_return;
    }

    public int getNum_stock_left() {
        return num_stock_left;
    }

    public void setNum_stock_left(int num_stock_left) {
        this.num_stock_left = num_stock_left;
    }

    public int getDiscuss_good() {
        return discuss_good;
    }

    public void setDiscuss_good(int discuss_good) {
        this.discuss_good = discuss_good;
    }

    public int getDiscuss_normal() {
        return discuss_normal;
    }

    public void setDiscuss_normal(int discuss_normal) {
        this.discuss_normal = discuss_normal;
    }

    public int getDiscuss_bad() {
        return discuss_bad;
    }

    public void setDiscuss_bad(int discuss_bad) {
        this.discuss_bad = discuss_bad;
    }

    public int getNum_sold() {
        return num_sold;
    }

    public void setNum_sold(int num_sold) {
        this.num_sold = num_sold;
    }
}


