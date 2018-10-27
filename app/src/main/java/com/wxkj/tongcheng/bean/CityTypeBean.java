package com.wxkj.tongcheng.bean;

/**
 * 分类bean
 * Created by cheng on 2018/10/16.
 */

public class CityTypeBean {

    /**
     * type_id : 2
     * type_name : 数码家电
     * show_img : http://tc.liebianzhe.com/upload/upload/20180913/20180913095125523.png
     * show_order : 0
     */

    private int type_id;
    private String type_name;
    private String show_img;
    private int show_order;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getShow_img() {
        return show_img;
    }

    public void setShow_img(String show_img) {
        this.show_img = show_img;
    }

    public int getShow_order() {
        return show_order;
    }

    public void setShow_order(int show_order) {
        this.show_order = show_order;
    }
}
