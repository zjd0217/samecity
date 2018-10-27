package com.wxkj.tongcheng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public class CityBannerBean implements Serializable{

    private List<BannerBean> banner;
    private List<TypeBean> type;

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

    public static class BannerBean {
        /**
         * ad_info_id : 2
         * ad_title : 标题
         * ad_pic : http://tc.liebianzhe.com/upload/upload/20180925/20180925113300796.png
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

    public static class TypeBean implements Serializable{
        /**
         * type_id : 54
         * type_name : 房产
         * show_img : http://tc.liebianzhe.com/upload/upload/20180913/20180913092745290.png
         * show_order : 0
         */

        private int type_id;
        private String type_name;
        private String show_img;
        private int show_order;
        private int tp;
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

        public int getTp() {
            return tp;
        }

        public void setTp(int tp) {
            this.tp = tp;
        }
    }
}
