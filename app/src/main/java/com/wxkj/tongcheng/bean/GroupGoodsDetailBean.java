package com.wxkj.tongcheng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/12 0012
 * @Describe 拼团的商品详情
 */
public class GroupGoodsDetailBean implements Serializable {


    /**
     * t_teamb : []
     * goods_like : []
     * goods_discuss : [{"goods_name":"第一件测试商品","user_id":1,"discuss_id":2,"time_setup":1535731200,"discuss_content":"test1","user_cname":"用户中文名","head_portrait":"http://tc.liebianzhe.com/upload/upload/20180914/20180914155537746.png"}]
     * goodspic : [{"goods_pic_id":1,"pic_path":"http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png","classify_id":1,"goods_id":2,"show_order":0},{"goods_pic_id":2,"pic_path":"http://tc.liebianzhe.com/upload/upload/20180901/20180901161512131.jpg","classify_id":1,"goods_id":2,"show_order":0},{"goods_pic_id":3,"pic_path":"http://tc.liebianzhe.com/upload/upload/20180901/20180901161516708.png","classify_id":1,"goods_id":2,"show_order":0},{"goods_pic_id":4,"pic_path":"http://tc.liebianzhe.com/upload/upload/20180901/20180901161521219.png","classify_id":1,"goods_id":2,"show_order":0}]
     * goods : {"goods_id":2,"shop_id":1,"classify_id":1,"special_fg":0,"goods_name":"第一件测试商品","price_explain":"","goods_labels":"随时退货,好评如潮","attribute_value":"颜色,红色;接口,118接口;保质期,3年","discuss_all":0,"discuss_good":0,"discuss_normal":0,"discuss_bad":0,"goods_pic":"http://tc.liebianzhe.com/upload/upload/20180901/20180901161504665.png","type_id":8,"detail_info":"产品详情","price_market":580000,"price_current":500000,"price_member":450000,"pay_return":"100,3","pay_pattern":"1","coupon_need":1,"goods_unit":"件","num_stock_left":18,"num_start":1,"num_span":2,"shop_name":"店铺名称ddd","shop_logo":"http://tc.liebianzhe.com/upload/upload/20180904/20180904102731320.png","time_on_work":"营业时间","shop_addr":"详细地址","baidu_pos":"106.529753,29.544843","connect_num":"13800138000","sold_all":4,"goods_all":0}
     * man_have : 0
     * teambuy : []
     * teambuys : []
     * goods_sold : 4
     * goods_num : 0
     * goods_recommend : []
     * spread_fg : 0
     */

    private GoodsBean goods;
    private int man_have;
    private int goods_sold;
    private int goods_num;
    private int spread_fg;
    private List<GoodsLikeBean> goods_like;
    private List<GoodsDiscussBean> goods_discuss;
    private List<GoodspicBean> goodspic;
    private List<TeamBuyBean> teambuy;
    private List<TeamBuyBean> teambuys;
    private List<GoodsRecommendBean> goods_recommend;


    public GoodsBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsBean goods) {
        this.goods = goods;
    }

    public int getMan_have() {
        return man_have;
    }

    public void setMan_have(int man_have) {
        this.man_have = man_have;
    }

    public int getGoods_sold() {
        return goods_sold;
    }

    public void setGoods_sold(int goods_sold) {
        this.goods_sold = goods_sold;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getSpread_fg() {
        return spread_fg;
    }

    public void setSpread_fg(int spread_fg) {
        this.spread_fg = spread_fg;
    }

    public List<GoodsLikeBean> getGoods_like() {
        return goods_like;
    }

    public void setGoods_like(List<GoodsLikeBean> goods_like) {
        this.goods_like = goods_like;
    }

    public List<GoodsDiscussBean> getGoods_discuss() {
        return goods_discuss;
    }

    public void setGoods_discuss(List<GoodsDiscussBean> goods_discuss) {
        this.goods_discuss = goods_discuss;
    }

    public List<GoodspicBean> getGoodspic() {
        return goodspic;
    }

    public void setGoodspic(List<GoodspicBean> goodspic) {
        this.goodspic = goodspic;
    }

    public List<TeamBuyBean> getTeambuy() {
        return teambuy;
    }

    public void setTeambuy(List<TeamBuyBean> teambuy) {
        this.teambuy = teambuy;
    }

    public List<TeamBuyBean> getTeambuys() {
        return teambuys;
    }

    public void setTeambuys(List<TeamBuyBean> teambuys) {
        this.teambuys = teambuys;
    }

    public List<GoodsRecommendBean> getGoods_recommend() {
        return goods_recommend;
    }

    public void setGoods_recommend(List<GoodsRecommendBean> goods_recommend) {
        this.goods_recommend = goods_recommend;
    }

    public static class GoodsBean implements Serializable {
        /**
         * goods_id : 2
         * shop_id : 1
         * classify_id : 1
         * special_fg : 0
         * goods_name : 第一件测试商品
         * price_explain :
         * goods_labels : 随时退货,好评如潮
         * attribute_value : 颜色,红色;接口,118接口;保质期,3年
         * discuss_all : 0
         * discuss_good : 0
         * discuss_normal : 0
         * discuss_bad : 0
         * goods_pic : http://tc.liebianzhe.com/upload/upload/20180901/20180901161504665.png
         * type_id : 8
         * detail_info : 产品详情
         * price_market : 580000
         * price_current : 500000
         * price_member : 450000
         * pay_return : 100,3
         * pay_pattern : 1
         * coupon_need : 1
         * goods_unit : 件
         * num_stock_left : 18
         * num_start : 1
         * num_span : 2
         * shop_name : 店铺名称ddd
         * shop_logo : http://tc.liebianzhe.com/upload/upload/20180904/20180904102731320.png
         * time_on_work : 营业时间
         * shop_addr : 详细地址
         * baidu_pos : 106.529753,29.544843
         * connect_num : 13800138000
         * sold_all : 4
         * goods_all : 0
         * collection : 1
         */

        private int goods_id;
        private int shop_id;
        private int classify_id;
        private int special_fg;
        private String goods_name;
        private String price_explain;
        private String goods_labels;
        private String attribute_value;
        private int discuss_all;
        private int discuss_good;
        private int discuss_normal;
        private int discuss_bad;
        private String goods_pic;
        private int type_id;
        private String detail_info;
        private int price_market;
        private int price_current;
        private int price_member;
        private String pay_return;
        private String pay_pattern;
        private int coupon_need;
        private String goods_unit;
        private int num_stock_left;
        private int num_start;
        private int num_span;
        private String shop_name;
        private String shop_logo;
        private String time_on_work;
        private String shop_addr;
        private String baidu_pos;
        private String connect_num;
        private int sold_all;
        private int goods_all;
        private int num_sold;
        private int collection_id;

        public int getNum_sold() {
            return num_sold;
        }

        public void setNum_sold(int num_sold) {
            this.num_sold = num_sold;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(int classify_id) {
            this.classify_id = classify_id;
        }

        public int getSpecial_fg() {
            return special_fg;
        }

        public void setSpecial_fg(int special_fg) {
            this.special_fg = special_fg;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getPrice_explain() {
            return price_explain;
        }

        public void setPrice_explain(String price_explain) {
            this.price_explain = price_explain;
        }

        public String getGoods_labels() {
            return goods_labels;
        }

        public void setGoods_labels(String goods_labels) {
            this.goods_labels = goods_labels;
        }

        public String getAttribute_value() {
            return attribute_value;
        }

        public void setAttribute_value(String attribute_value) {
            this.attribute_value = attribute_value;
        }

        public int getDiscuss_all() {
            return discuss_all;
        }

        public void setDiscuss_all(int discuss_all) {
            this.discuss_all = discuss_all;
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

        public String getDetail_info() {
            return detail_info;
        }

        public void setDetail_info(String detail_info) {
            this.detail_info = detail_info;
        }

        public int getPrice_market() {
            return price_market;
        }

        public void setPrice_market(int price_market) {
            this.price_market = price_market;
        }

        public int getPrice_current() {
            return price_current;
        }

        public void setPrice_current(int price_current) {
            this.price_current = price_current;
        }

        public int getPrice_member() {
            return price_member;
        }

        public void setPrice_member(int price_member) {
            this.price_member = price_member;
        }

        public String getPay_return() {
            return pay_return;
        }

        public void setPay_return(String pay_return) {
            this.pay_return = pay_return;
        }

        public String getPay_pattern() {
            return pay_pattern;
        }

        public void setPay_pattern(String pay_pattern) {
            this.pay_pattern = pay_pattern;
        }

        public int getCoupon_need() {
            return coupon_need;
        }

        public void setCoupon_need(int coupon_need) {
            this.coupon_need = coupon_need;
        }

        public String getGoods_unit() {
            return goods_unit;
        }

        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }

        public int getNum_stock_left() {
            return num_stock_left;
        }

        public void setNum_stock_left(int num_stock_left) {
            this.num_stock_left = num_stock_left;
        }

        public int getNum_start() {
            return num_start;
        }

        public void setNum_start(int num_start) {
            this.num_start = num_start;
        }

        public int getNum_span() {
            return num_span;
        }

        public void setNum_span(int num_span) {
            this.num_span = num_span;
        }

        public String getShop_name() {
            return shop_name;
        }

        public void setShop_name(String shop_name) {
            this.shop_name = shop_name;
        }

        public String getShop_logo() {
            return shop_logo;
        }

        public void setShop_logo(String shop_logo) {
            this.shop_logo = shop_logo;
        }

        public String getTime_on_work() {
            return time_on_work;
        }

        public void setTime_on_work(String time_on_work) {
            this.time_on_work = time_on_work;
        }

        public String getShop_addr() {
            return shop_addr;
        }

        public void setShop_addr(String shop_addr) {
            this.shop_addr = shop_addr;
        }

        public String getBaidu_pos() {
            return baidu_pos;
        }

        public void setBaidu_pos(String baidu_pos) {
            this.baidu_pos = baidu_pos;
        }

        public String getConnect_num() {
            return connect_num;
        }

        public void setConnect_num(String connect_num) {
            this.connect_num = connect_num;
        }

        public int getSold_all() {
            return sold_all;
        }

        public void setSold_all(int sold_all) {
            this.sold_all = sold_all;
        }

        public int getGoods_all() {
            return goods_all;
        }

        public void setGoods_all(int goods_all) {
            this.goods_all = goods_all;
        }

        public int getCollection_id() {
            return collection_id;
        }

        public void setCollection_id(int collection_id) {
            this.collection_id = collection_id;
        }
    }

    public static class GoodsLikeBean {

        private int goods_id;
        private String goods_name;
        private String goods_labels;
        private String goods_pic;
        private int price_market;
        private int shop_id;
        private int num_sold;
        private String goods_unit;
        private String baidu_pos;
        private int price_current;
        private int price_member;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
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

        public int getPrice_market() {
            return price_market;
        }

        public void setPrice_market(int price_market) {
            this.price_market = price_market;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getNum_sold() {
            return num_sold;
        }

        public void setNum_sold(int num_sold) {
            this.num_sold = num_sold;
        }

        public String getGoods_unit() {
            return goods_unit;
        }

        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }

        public String getBaidu_pos() {
            return baidu_pos;
        }

        public void setBaidu_pos(String baidu_pos) {
            this.baidu_pos = baidu_pos;
        }

        public int getPrice_current() {
            return price_current;
        }

        public void setPrice_current(int price_current) {
            this.price_current = price_current;
        }

        public int getPrice_member() {
            return price_member;
        }

        public void setPrice_member(int price_member) {
            this.price_member = price_member;
        }
    }

    public static class GoodsDiscussBean {
        /**
         * goods_name : 第一件测试商品
         * user_id : 1
         * discuss_id : 2
         * time_setup : 1535731200
         * discuss_content : test1
         * user_cname : 用户中文名
         * head_portrait : http://tc.liebianzhe.com/upload/upload/20180914/20180914155537746.png
         */

        private String goods_name;
        private int user_id;
        private int discuss_id;
        private int time_setup;
        private String discuss_content;
        private String user_cname;
        private String head_portrait;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getDiscuss_id() {
            return discuss_id;
        }

        public void setDiscuss_id(int discuss_id) {
            this.discuss_id = discuss_id;
        }

        public int getTime_setup() {
            return time_setup;
        }

        public void setTime_setup(int time_setup) {
            this.time_setup = time_setup;
        }

        public String getDiscuss_content() {
            return discuss_content;
        }

        public void setDiscuss_content(String discuss_content) {
            this.discuss_content = discuss_content;
        }

        public String getUser_cname() {
            return user_cname;
        }

        public void setUser_cname(String user_cname) {
            this.user_cname = user_cname;
        }

        public String getHead_portrait() {
            return head_portrait;
        }

        public void setHead_portrait(String head_portrait) {
            this.head_portrait = head_portrait;
        }
    }

    public static class GoodspicBean {
        /**
         * goods_pic_id : 1
         * pic_path : http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png
         * classify_id : 1
         * goods_id : 2
         * show_order : 0
         */

        private int goods_pic_id;
        private String pic_path;
        private int classify_id;
        private int goods_id;
        private int show_order;

        public int getGoods_pic_id() {
            return goods_pic_id;
        }

        public void setGoods_pic_id(int goods_pic_id) {
            this.goods_pic_id = goods_pic_id;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public int getClassify_id() {
            return classify_id;
        }

        public void setClassify_id(int classify_id) {
            this.classify_id = classify_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getShow_order() {
            return show_order;
        }

        public void setShow_order(int show_order) {
            this.show_order = show_order;
        }
    }

    public static class TeamBuyBean implements Serializable {
        /**
         * teambuy_id : 1
         * user_id : 2
         * sysuser_id : 0
         * shop_id : 1
         * teambuy_rule_id : 0
         * goods_id : 4
         * price_teambuy : 495000
         * teambuy_name : 第一个团购
         * man_num_need : 18
         * man_num_have : 1
         * user_id_first : 6
         * time_first_user : 0
         * capital_num_need : 0
         * teambuy_times : 0
         * teambuy_times_left : 0
         * teambuy_times_used : 0
         * goods_num : 18
         * goods_num_left : 17
         * goods_num_sold : 1
         * goods_num_order : 0
         * time_setup : 1537508259
         * teambuy_limit_num : 1
         * teambuy_time_begin : 1535565757
         * teambuy_time_end : 1720750237
         * time_end_mannum : 0
         * time_end_capital : 0
         * teambuy_state : 1
         * teambuy_lock : 0
         * memo : 沙发沙发
         * head_portrait : http://127.0.0.1/peng/samecity/public/upload/upload/20180919/20180919105409902.png
         * user_cname : u6_7400126
         */

        private int teambuy_id;
        private int user_id;
        private int sysuser_id;
        private int shop_id;
        private int teambuy_rule_id;
        private int goods_id;
        private int price_teambuy;
        private String teambuy_name;
        private int man_num_need;
        private int man_num_have;
        private int user_id_first;
        private int time_first_user;
        private int capital_num_need;
        private int teambuy_times;
        private int teambuy_times_left;
        private int teambuy_times_used;
        private int goods_num;
        private int goods_num_left;
        private int goods_num_sold;
        private int goods_num_order;
        private int time_setup;
        private int teambuy_limit_num;
        private int teambuy_time_begin;
        private int teambuy_time_end;
        private int time_end_mannum;
        private int time_end_capital;
        private int teambuy_state;
        private int teambuy_lock;
        private String memo;
        private String head_portrait;
        private String user_cname;

        public int getTeambuy_id() {
            return teambuy_id;
        }

        public void setTeambuy_id(int teambuy_id) {
            this.teambuy_id = teambuy_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getSysuser_id() {
            return sysuser_id;
        }

        public void setSysuser_id(int sysuser_id) {
            this.sysuser_id = sysuser_id;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public int getTeambuy_rule_id() {
            return teambuy_rule_id;
        }

        public void setTeambuy_rule_id(int teambuy_rule_id) {
            this.teambuy_rule_id = teambuy_rule_id;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public int getPrice_teambuy() {
            return price_teambuy;
        }

        public void setPrice_teambuy(int price_teambuy) {
            this.price_teambuy = price_teambuy;
        }

        public String getTeambuy_name() {
            return teambuy_name;
        }

        public void setTeambuy_name(String teambuy_name) {
            this.teambuy_name = teambuy_name;
        }

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

        public int getUser_id_first() {
            return user_id_first;
        }

        public void setUser_id_first(int user_id_first) {
            this.user_id_first = user_id_first;
        }

        public int getTime_first_user() {
            return time_first_user;
        }

        public void setTime_first_user(int time_first_user) {
            this.time_first_user = time_first_user;
        }

        public int getCapital_num_need() {
            return capital_num_need;
        }

        public void setCapital_num_need(int capital_num_need) {
            this.capital_num_need = capital_num_need;
        }

        public int getTeambuy_times() {
            return teambuy_times;
        }

        public void setTeambuy_times(int teambuy_times) {
            this.teambuy_times = teambuy_times;
        }

        public int getTeambuy_times_left() {
            return teambuy_times_left;
        }

        public void setTeambuy_times_left(int teambuy_times_left) {
            this.teambuy_times_left = teambuy_times_left;
        }

        public int getTeambuy_times_used() {
            return teambuy_times_used;
        }

        public void setTeambuy_times_used(int teambuy_times_used) {
            this.teambuy_times_used = teambuy_times_used;
        }

        public int getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(int goods_num) {
            this.goods_num = goods_num;
        }

        public int getGoods_num_left() {
            return goods_num_left;
        }

        public void setGoods_num_left(int goods_num_left) {
            this.goods_num_left = goods_num_left;
        }

        public int getGoods_num_sold() {
            return goods_num_sold;
        }

        public void setGoods_num_sold(int goods_num_sold) {
            this.goods_num_sold = goods_num_sold;
        }

        public int getGoods_num_order() {
            return goods_num_order;
        }

        public void setGoods_num_order(int goods_num_order) {
            this.goods_num_order = goods_num_order;
        }

        public int getTime_setup() {
            return time_setup;
        }

        public void setTime_setup(int time_setup) {
            this.time_setup = time_setup;
        }

        public int getTeambuy_limit_num() {
            return teambuy_limit_num;
        }

        public void setTeambuy_limit_num(int teambuy_limit_num) {
            this.teambuy_limit_num = teambuy_limit_num;
        }

        public int getTeambuy_time_begin() {
            return teambuy_time_begin;
        }

        public void setTeambuy_time_begin(int teambuy_time_begin) {
            this.teambuy_time_begin = teambuy_time_begin;
        }

        public int getTeambuy_time_end() {
            return teambuy_time_end;
        }

        public void setTeambuy_time_end(int teambuy_time_end) {
            this.teambuy_time_end = teambuy_time_end;
        }

        public int getTime_end_mannum() {
            return time_end_mannum;
        }

        public void setTime_end_mannum(int time_end_mannum) {
            this.time_end_mannum = time_end_mannum;
        }

        public int getTime_end_capital() {
            return time_end_capital;
        }

        public void setTime_end_capital(int time_end_capital) {
            this.time_end_capital = time_end_capital;
        }

        public int getTeambuy_state() {
            return teambuy_state;
        }

        public void setTeambuy_state(int teambuy_state) {
            this.teambuy_state = teambuy_state;
        }

        public int getTeambuy_lock() {
            return teambuy_lock;
        }

        public void setTeambuy_lock(int teambuy_lock) {
            this.teambuy_lock = teambuy_lock;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getHead_portrait() {
            return head_portrait;
        }

        public void setHead_portrait(String head_portrait) {
            this.head_portrait = head_portrait;
        }

        public String getUser_cname() {
            return user_cname;
        }

        public void setUser_cname(String user_cname) {
            this.user_cname = user_cname;
        }
    }

    public static class GoodsRecommendBean {

        /**
         * goods_id : 8
         * goods_name : 平台自营店第二件商品
         * goods_labels : 啊说法萨芬,7天无理由退货
         * goods_pic : http://127.0.0.1/peng/samecity/public/upload/upload/20180903/20180903174810992.png
         * num_sold : 0
         * goods_unit : 台
         * price_current : 550000
         * price_member : 533500
         */

        private int goods_id;
        private String goods_name;
        private String goods_labels;
        private String goods_pic;
        private int num_sold;
        private String goods_unit;
        private int price_current;
        private int price_member;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
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

        public int getNum_sold() {
            return num_sold;
        }

        public void setNum_sold(int num_sold) {
            this.num_sold = num_sold;
        }

        public String getGoods_unit() {
            return goods_unit;
        }

        public void setGoods_unit(String goods_unit) {
            this.goods_unit = goods_unit;
        }

        public int getPrice_current() {
            return price_current;
        }

        public void setPrice_current(int price_current) {
            this.price_current = price_current;
        }

        public int getPrice_member() {
            return price_member;
        }

        public void setPrice_member(int price_member) {
            this.price_member = price_member;
        }
    }
}
