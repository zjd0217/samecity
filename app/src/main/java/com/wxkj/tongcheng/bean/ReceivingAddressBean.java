package com.wxkj.tongcheng.bean;

import java.io.Serializable;

/**
 * @author Liu haijun
 * @create 2018/10/19 0019
 * @Describe 收货地址
 */
public class ReceivingAddressBean implements Serializable {


    /**
     * addr_id : 2
     * region_id : 374
     * region_path : 北京市->北京市->西城区
     * addr_title : 新家
     * addr_detail : 北京大元三路25号
     * baidu_pos : 116.507053,39.973045
     * contact_man : 古秋实
     * contact_number : 13900000000
     * default_fg : 0
     * show_order : 4
     */

    private int addr_id;
    private int region_id;
    private String region_path;
    private String addr_title;
    private String addr_detail;
    private String baidu_pos;
    private String contact_man;
    private String contact_number;
    private String memo;
    private int default_fg;
    private int show_order;

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(int addr_id) {
        this.addr_id = addr_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_path() {
        return region_path;
    }

    public void setRegion_path(String region_path) {
        this.region_path = region_path;
    }

    public String getAddr_title() {
        return addr_title;
    }

    public void setAddr_title(String addr_title) {
        this.addr_title = addr_title;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public void setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
    }

    public String getBaidu_pos() {
        return baidu_pos;
    }

    public void setBaidu_pos(String baidu_pos) {
        this.baidu_pos = baidu_pos;
    }

    public String getContact_man() {
        return contact_man;
    }

    public void setContact_man(String contact_man) {
        this.contact_man = contact_man;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public int getDefault_fg() {
        return default_fg;
    }

    public void setDefault_fg(int default_fg) {
        this.default_fg = default_fg;
    }

    public int getShow_order() {
        return show_order;
    }

    public void setShow_order(int show_order) {
        this.show_order = show_order;
    }
}
