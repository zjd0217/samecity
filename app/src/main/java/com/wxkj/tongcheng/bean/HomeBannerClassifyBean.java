package com.wxkj.tongcheng.bean;

import java.util.List;

public class HomeBannerClassifyBean {


    public List<BannerEntity> banner;
    public List<ClassifyEntity> type;
    public List<ToutiaoEntity> toutiao;
    public List<SeckillEntity> seckill;
    public List<ItemEntity> item;

    public static class BannerEntity {

        public String ad_info_id;
        public String ad_title;
        public String ad_pic;
        public String ad_url;
    }

    public static class ClassifyEntity {

        public String type_id;
        public String type_name;
        public String show_img;
        public String show_order;
    }

    public static class ToutiaoEntity {

        public String info_id;
        public String info_title;
        public String info_pic;
        public String info_content;
    }

    public static class SeckillEntity {

        public String seckill_id;
        public int goods_id;
        public String seckill_name;
        public String price_seckill;
        public String goods_name;
        public String price_current;
        public String num_sold;
        public String goods_pic;
        public String price_member;
        public String baidu_pos;
        public String seckill_time_end;
    }

    public static class ItemEntity {

        public String item_id;
        public String item_title;
        public String item_content;
        public String item_pic;
        public String item_url;
    }
}
