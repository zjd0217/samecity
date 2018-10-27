package com.wxkj.tongcheng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/9 0009
 * @Describe 拼团的头部信息
 */
public class GroupTitleBean implements Serializable {


    private List<BannerBean> banner;
    private List<TypeBean> type;
    private List<TeambuyBean> teambuy;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<TypeBean> getType() {
        return type;
    }

    public void setType(List<TypeBean> type) {
        this.type = type;
    }

    public List<TeambuyBean> getTeambuy() {
        return teambuy;
    }

    public void setTeambuy(List<TeambuyBean> teambuy) {
        this.teambuy = teambuy;
    }

    public static class BannerBean {
        /**
         * ad_info_id : 2
         * ad_title : 标题
         * ad_pic : http://127.0.0.1/peng/samecity/public/upload/upload/20180818/20180818093655998.png
         * ad_url : 0
         */

        private int ad_info_id;
        private String ad_title;
        private String ad_pic;
        private String ad_url;

        public int getAd_info_id() {
            return ad_info_id;
        }

        public void setAd_info_id(int ad_info_id) {
            this.ad_info_id = ad_info_id;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getAd_pic() {
            return ad_pic;
        }

        public void setAd_pic(String ad_pic) {
            this.ad_pic = ad_pic;
        }

        public String getAd_url() {
            return ad_url;
        }

        public void setAd_url(String ad_url) {
            this.ad_url = ad_url;
        }
    }

    public static class TypeBean {
        /**
         * type_id : 59
         * type_name : 食品
         * show_img :
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

    public static class TeambuyBean {
        /**
         * teambuy_id : 1
         * goods_id : 4
         * teambuy_name : 第一个团购
         * price_teambuy : 495000
         * goods_name : ipad air 天蓝色
         * price_current : 500000
         * num_sold : 26
         * goods_pic : http://127.0.0.1/peng/samecity/public/upload/upload/20180901/20180901092329921.jpg
         * price_member : 485000
         * baidu_pos : 106.49833,29.542329
         * teambuy_time_end : 1720750237
         */

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
        private String goods_labels;

        public String getGoods_labels() {
            return goods_labels;
        }

        public void setGoods_labels(String goods_labels) {
            this.goods_labels = goods_labels;
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
    }
}
