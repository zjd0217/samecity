package com.wxkj.tongcheng.bean;

import java.io.Serializable;

/**
 * @author Liu haijun
 * @create 2018/10/24 0024
 * @Describe 用户优惠券
 */
public class CouponBean implements Serializable {


    /**
     * coupon_name : 所有分类
     * coupon_capital : 1000
     * coupon_pic : http://127.0.0.1/peng/samecity/public/upload/upload/20180920/20180920162827683.png
     * coupon_need_capital : 50000
     * lock_fg : 0
     * valid_fg : 1
     * type_name : 商品分类信息
     * shop_name : null
     * goods_name : null
     * time_valid_end : 1614326783
     * time_valid_begin : 1614326783
     * type_id : 1
     * shop_id : 0
     * goods_id : 0
     * user_coupon_id : 12
     * type_ids : 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,59,60
     */

    private String coupon_name;
    private int coupon_capital;
    private String coupon_pic;
    private int coupon_need_capital;
    private int lock_fg;
    private int valid_fg;
    private String type_name;
    private String shop_name;
    private String goods_name;
    private int time_valid_end;
    private int type_id;
    private int shop_id;
    private int goods_id;
    private int user_coupon_id;
    private String type_ids;
    private int time_valid_begin;

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public int getCoupon_capital() {
        return coupon_capital;
    }

    public void setCoupon_capital(int coupon_capital) {
        this.coupon_capital = coupon_capital;
    }

    public String getCoupon_pic() {
        return coupon_pic;
    }

    public void setCoupon_pic(String coupon_pic) {
        this.coupon_pic = coupon_pic;
    }

    public int getCoupon_need_capital() {
        return coupon_need_capital;
    }

    public void setCoupon_need_capital(int coupon_need_capital) {
        this.coupon_need_capital = coupon_need_capital;
    }

    public int getLock_fg() {
        return lock_fg;
    }

    public void setLock_fg(int lock_fg) {
        this.lock_fg = lock_fg;
    }

    public int getValid_fg() {
        return valid_fg;
    }

    public void setValid_fg(int valid_fg) {
        this.valid_fg = valid_fg;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public int getTime_valid_end() {
        return time_valid_end;
    }

    public void setTime_valid_end(int time_valid_end) {
        this.time_valid_end = time_valid_end;
    }

    public int getTime_valid_begin() {
        return time_valid_begin;
    }

    public void setTime_valid_begin(int time_valid_begin) {
        this.time_valid_begin = time_valid_begin;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getUser_coupon_id() {
        return user_coupon_id;
    }

    public void setUser_coupon_id(int user_coupon_id) {
        this.user_coupon_id = user_coupon_id;
    }

    public String getType_ids() {
        return type_ids;
    }

    public void setType_ids(String type_ids) {
        this.type_ids = type_ids;
    }
}
