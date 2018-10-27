package com.wxkj.tongcheng.bean;

/**
 * 用户token bean
 * Created by cheng on 2018/10/7.
 */

public class UserInfo {

    /**
     * user_id : 42
     * token : e230de766e8f0b7f68d24778149dfaeef6b19fe9977e6d8284
     * user_name : u42_421892
     * user_cname : u42_421892
     * spread_fg : 0  0是普通会员,1是推广员
     * head_portrait : http://tc.liebianzhe.com/upload/upload/20180914/20180914155537746.png
     */

    private int user_id;
    private String token;
    private String user_name;
    private String user_cname;
    private int spread_fg;
    private String head_portrait;
    private int shop_id;  //拥有的店铺id  为0就是没有店铺
    private int shop_valid_fg;  //店铺状态
    private String phone;
    private int sex;
    private String birth;
    private String user_wxid;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_cname() {
        return user_cname;
    }

    public void setUser_cname(String user_cname) {
        this.user_cname = user_cname;
    }

    public int getSpread_fg() {
        return spread_fg;
    }

    public void setSpread_fg(int spread_fg) {
        this.spread_fg = spread_fg;
    }

    public String getHead_portrait() {
        return head_portrait;
    }

    public void setHead_portrait(String head_portrait) {
        this.head_portrait = head_portrait;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public int getShop_valid_fg() {
        return shop_valid_fg;
    }

    public void setShop_valid_fg(int shop_valid_fg) {
        this.shop_valid_fg = shop_valid_fg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getUser_wxid() {
        return user_wxid;
    }

    public void setUser_wxid(String user_wxid) {
        this.user_wxid = user_wxid;
    }
}
