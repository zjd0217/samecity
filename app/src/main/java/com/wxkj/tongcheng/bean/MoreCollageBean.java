package com.wxkj.tongcheng.bean;

import java.io.Serializable;

/**
 * @author Liu haijun
 * @create 2018/10/17 0017
 * @Describe 更多拼团人员信息
 */
public class MoreCollageBean implements Serializable {


    /**
     * man_num_need : 20
     * man_num_have : 0
     * teambuy_id : 1
     * goods_id : 2
     * teambuy_name : 第一个团购信息
     * price_teambuy : 400
     * goods_name : 第一件测试商品
     * price_current : 500000
     * num_sold : 6
     * goods_pic : http://tc.liebianzhe.com/upload/upload/20180901/20180901161504665.png
     * price_member : 450000
     * baidu_pos : 106.529753,29.544843
     * teambuy_time_end : 1538321021
     * teambuy_time_begin : 1535565761
     * goods_labels : 随时退货,好评如潮
     * firsthead_portrait : null
     * firstuser_cname : null
     * user_id_first : 0
     */

    private int man_num_need;
    private int man_num_have;
    private int teambuy_id;
    private int goods_id;
    private String teambuy_name;
    private int price_teambuy;
    private String goods_name;
    private int price_current;
    private int num_sold;
    private String goods_pic;
    private int price_member;
    private String baidu_pos;
    private int teambuy_time_end;
    private int teambuy_time_begin;
    private String goods_labels;
    private String firsthead_portrait;
    private String firstuser_cname;
    private int user_id_first;

    public int getMan_num_need() {
        return man_num_need;
    }

    public void setMan_num_need(int man_num_need) {
        this.man_num_need = man_num_need;
    }

    public int getMan_num_have() {
        return man_num_have;
    }

    public void setMan_num_have(int man_num_have) {
        this.man_num_have = man_num_have;
    }

    public int getTeambuy_id() {
        return teambuy_id;
    }

    public void setTeambuy_id(int teambuy_id) {
        this.teambuy_id = teambuy_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getTeambuy_name() {
        return teambuy_name;
    }

    public void setTeambuy_name(String teambuy_name) {
        this.teambuy_name = teambuy_name;
    }

    public int getPrice_teambuy() {
        return price_teambuy;
    }

    public void setPrice_teambuy(int price_teambuy) {
        this.price_teambuy = price_teambuy;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getPrice_current() {
        return price_current;
    }

    public void setPrice_current(int price_current) {
        this.price_current = price_current;
    }

    public int getNum_sold() {
        return num_sold;
    }

    public void setNum_sold(int num_sold) {
        this.num_sold = num_sold;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public int getPrice_member() {
        return price_member;
    }

    public void setPrice_member(int price_member) {
        this.price_member = price_member;
    }

    public String getBaidu_pos() {
        return baidu_pos;
    }

    public void setBaidu_pos(String baidu_pos) {
        this.baidu_pos = baidu_pos;
    }

    public int getTeambuy_time_end() {
        return teambuy_time_end;
    }

    public void setTeambuy_time_end(int teambuy_time_end) {
        this.teambuy_time_end = teambuy_time_end;
    }

    public int getTeambuy_time_begin() {
        return teambuy_time_begin;
    }

    public void setTeambuy_time_begin(int teambuy_time_begin) {
        this.teambuy_time_begin = teambuy_time_begin;
    }

    public String getGoods_labels() {
        return goods_labels;
    }

    public void setGoods_labels(String goods_labels) {
        this.goods_labels = goods_labels;
    }

    public String getFirsthead_portrait() {
        return firsthead_portrait;
    }

    public void setFirsthead_portrait(String firsthead_portrait) {
        this.firsthead_portrait = firsthead_portrait;
    }

    public String getFirstuser_cname() {
        return firstuser_cname;
    }

    public void setFirstuser_cname(String firstuser_cname) {
        this.firstuser_cname = firstuser_cname;
    }

    public int getUser_id_first() {
        return user_id_first;
    }

    public void setUser_id_first(int user_id_first) {
        this.user_id_first = user_id_first;
    }
}
