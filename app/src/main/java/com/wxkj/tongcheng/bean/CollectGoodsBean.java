package com.wxkj.tongcheng.bean;

/**
 * 收藏 bean
 * Created by cheng on 2018/10/20.
 */

public class CollectGoodsBean {

    /**
     * collection_id : 8
     * data_id : 2
     * time_setup : 1540121951
     * title : 第一件测试商品
     * goods_pic : /upload/upload/20180901/20180901161504665.png
     * price_current : 500000
     */

    private int collection_id;
    private int data_id;
    private int time_setup;
    private String title;
    private String goods_pic;
    private int price_current;

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public int getTime_setup() {
        return time_setup;
    }

    public void setTime_setup(int time_setup) {
        this.time_setup = time_setup;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public int getPrice_current() {
        return price_current;
    }

    public void setPrice_current(int price_current) {
        this.price_current = price_current;
    }
}
