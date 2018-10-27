package com.wxkj.tongcheng.bean;

import java.util.List;

/**
 * 服务 需求 详情bean
 * Created by cheng on 2018/10/10.
 */

public class ServiceDemandDetailBean {

    /**
     * service : {"service_need_id":26,"user_id":2,"shop_id":"","time_setup":1537155361,"time_overdue":1677772800,"info_title":"标题","for_top":"","for_top_ask":"","urgent_fg":"","user_nickname":"昵称","baidu_pos":"","baidu_lng":"","baidu_lat":"","contact_phone":"联系电话","info_describe":"详细内","info_voice":"","user_addr":"联系地址","region_id0":6,"region_id1":99,"region_path":"黑龙江省->佳木斯市->同江市","region_id":1098,"type_id_parent":54,"type_id":61,"valid_fg":1,"close_fg":"","sysuser_id":1,"time_check":1537923971,"info_check":"","visit_times":"","user_cname":"雨林同行","type_name":"租房一级","ptype_name":"房产","shop_name":"","shop_logo":"","sold_all":"","goods_all":"","time_on_work":"","shop_pos":"","shop_num":""}
     * attr : [{"attribute_id":9,"attribute_value":"特别需求","data_extend":"10","data_value":"厂房","label_fg":0},{"attribute_id":1,"attribute_value":"住房类型","data_extend":"1","data_value":"单间","label_fg":0},{"attribute_id":2,"attribute_value":"合租","data_extend":"4","data_value":"别墅","label_fg":0}]
     * pics : [{"need_pic_id":1,"pic_title":"","pic_path":"http://tc.liebianzhe.com/upload/upload/20181010/20181010210022256.jpg"},{"need_pic_id":3,"pic_title":"","pic_path":"http://tc.liebianzhe.com/upload/upload/20181010/20181010210025391.png"},{"need_pic_id":4,"pic_title":"","pic_path":"http://tc.liebianzhe.com/upload/upload/20181010/20181010210028951.png"}]
     * info_like : [{"service_need_id":3,"pic_path":"http://tc.liebianzhe.com/upload/upload/20181010/20181010210022256.jpg","info_title":"标题vv","time_setup":1539176431,"urgent_fg":0,"baidu_pos":"","info_describe":"详细内容\t","visit_times":0},{"service_need_id":3,"pic_path":"http://tc.liebianzhe.com/upload/upload/20181010/20181010210028951.png","info_title":"标题vv","time_setup":1539176431,"urgent_fg":0,"baidu_pos":"","info_describe":"详细内容\t","visit_times":0},{"service_need_id":3,"pic_path":"http://tc.liebianzhe.com/upload/upload/20181010/20181010210025391.png","info_title":"标题vv","time_setup":1539176431,"urgent_fg":0,"baidu_pos":"","info_describe":"详细内容\t","visit_times":0},{"service_need_id":9,"pic_path":null,"info_title":"","time_setup":1534846074,"urgent_fg":0,"baidu_pos":"","info_describe":"","visit_times":0}]
     */

    private ServiceBean service;
    private List<AttrBean> attr;
    private List<PicsBean> pics;
    private List<InfoLikeBean> info_like;

    public ServiceBean getService() {
        return service;
    }

    public void setService(ServiceBean service) {
        this.service = service;
    }

    public List<AttrBean> getAttr() {
        return attr;
    }

    public void setAttr(List<AttrBean> attr) {
        this.attr = attr;
    }

    public List<PicsBean> getPics() {
        return pics;
    }

    public void setPics(List<PicsBean> pics) {
        this.pics = pics;
    }

    public List<InfoLikeBean> getInfo_like() {
        return info_like;
    }

    public void setInfo_like(List<InfoLikeBean> info_like) {
        this.info_like = info_like;
    }

    public static class ServiceBean {
        /**
         * service_need_id : 26
         * user_id : 2
         * shop_id :
         * time_setup : 1537155361
         * time_overdue : 1677772800
         * info_title : 标题
         * for_top :
         * for_top_ask :
         * urgent_fg :
         * user_nickname : 昵称
         * baidu_pos :
         * baidu_lng :
         * baidu_lat :
         * contact_phone : 联系电话
         * info_describe : 详细内
         * info_voice :
         * user_addr : 联系地址
         * region_id0 : 6
         * region_id1 : 99
         * region_path : 黑龙江省->佳木斯市->同江市
         * region_id : 1098
         * type_id_parent : 54
         * type_id : 61
         * valid_fg : 1
         * close_fg :
         * sysuser_id : 1
         * time_check : 1537923971
         * info_check :
         * visit_times :
         * user_cname : 雨林同行
         * type_name : 租房一级
         * ptype_name : 房产
         * shop_name :
         * shop_logo :
         * sold_all :
         * goods_all :
         * time_on_work :
         * shop_pos :
         * shop_num :
         */

        private int service_need_id;
        private int user_id;
        private String shop_id;
        private int time_setup;
        private int time_overdue;
        private String info_title;
        private String for_top;
        private String for_top_ask;
        private String urgent_fg;
        private String user_nickname;
        private String baidu_pos;
        private String baidu_lng;
        private String baidu_lat;
        private String contact_phone;
        private String info_describe;
        private String info_voice;
        private String user_addr;
        private int region_id0;
        private int region_id1;
        private String region_path;
        private int region_id;
        private int type_id_parent;
        private int type_id;
        private int valid_fg;
        private String close_fg;
        private int sysuser_id;
        private int time_check;
        private String info_check;
        private String visit_times;
        private String user_cname;
        private String type_name;
        private String ptype_name;
        private String shop_name;
        private String shop_logo;
        private String sold_all;
        private String goods_all;
        private String time_on_work;
        private String shop_pos;
        private String shop_num;
        private int collection_id;

        public int getCollection_id() {
            return collection_id;
        }

        public void setCollection_id(int collection_id) {
            this.collection_id = collection_id;
        }

        public int getService_need_id() {
            return service_need_id;
        }

        public void setService_need_id(int service_need_id) {
            this.service_need_id = service_need_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public int getTime_setup() {
            return time_setup;
        }

        public void setTime_setup(int time_setup) {
            this.time_setup = time_setup;
        }

        public int getTime_overdue() {
            return time_overdue;
        }

        public void setTime_overdue(int time_overdue) {
            this.time_overdue = time_overdue;
        }

        public String getInfo_title() {
            return info_title;
        }

        public void setInfo_title(String info_title) {
            this.info_title = info_title;
        }

        public String getFor_top() {
            return for_top;
        }

        public void setFor_top(String for_top) {
            this.for_top = for_top;
        }

        public String getFor_top_ask() {
            return for_top_ask;
        }

        public void setFor_top_ask(String for_top_ask) {
            this.for_top_ask = for_top_ask;
        }

        public String getUrgent_fg() {
            return urgent_fg;
        }

        public void setUrgent_fg(String urgent_fg) {
            this.urgent_fg = urgent_fg;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getBaidu_pos() {
            return baidu_pos;
        }

        public void setBaidu_pos(String baidu_pos) {
            this.baidu_pos = baidu_pos;
        }

        public String getBaidu_lng() {
            return baidu_lng;
        }

        public void setBaidu_lng(String baidu_lng) {
            this.baidu_lng = baidu_lng;
        }

        public String getBaidu_lat() {
            return baidu_lat;
        }

        public void setBaidu_lat(String baidu_lat) {
            this.baidu_lat = baidu_lat;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }

        public String getInfo_describe() {
            return info_describe;
        }

        public void setInfo_describe(String info_describe) {
            this.info_describe = info_describe;
        }

        public String getInfo_voice() {
            return info_voice;
        }

        public void setInfo_voice(String info_voice) {
            this.info_voice = info_voice;
        }

        public String getUser_addr() {
            return user_addr;
        }

        public void setUser_addr(String user_addr) {
            this.user_addr = user_addr;
        }

        public int getRegion_id0() {
            return region_id0;
        }

        public void setRegion_id0(int region_id0) {
            this.region_id0 = region_id0;
        }

        public int getRegion_id1() {
            return region_id1;
        }

        public void setRegion_id1(int region_id1) {
            this.region_id1 = region_id1;
        }

        public String getRegion_path() {
            return region_path;
        }

        public void setRegion_path(String region_path) {
            this.region_path = region_path;
        }

        public int getRegion_id() {
            return region_id;
        }

        public void setRegion_id(int region_id) {
            this.region_id = region_id;
        }

        public int getType_id_parent() {
            return type_id_parent;
        }

        public void setType_id_parent(int type_id_parent) {
            this.type_id_parent = type_id_parent;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public int getValid_fg() {
            return valid_fg;
        }

        public void setValid_fg(int valid_fg) {
            this.valid_fg = valid_fg;
        }

        public String getClose_fg() {
            return close_fg;
        }

        public void setClose_fg(String close_fg) {
            this.close_fg = close_fg;
        }

        public int getSysuser_id() {
            return sysuser_id;
        }

        public void setSysuser_id(int sysuser_id) {
            this.sysuser_id = sysuser_id;
        }

        public int getTime_check() {
            return time_check;
        }

        public void setTime_check(int time_check) {
            this.time_check = time_check;
        }

        public String getInfo_check() {
            return info_check;
        }

        public void setInfo_check(String info_check) {
            this.info_check = info_check;
        }

        public String getVisit_times() {
            return visit_times;
        }

        public void setVisit_times(String visit_times) {
            this.visit_times = visit_times;
        }

        public String getUser_cname() {
            return user_cname;
        }

        public void setUser_cname(String user_cname) {
            this.user_cname = user_cname;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getPtype_name() {
            return ptype_name;
        }

        public void setPtype_name(String ptype_name) {
            this.ptype_name = ptype_name;
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

        public String getSold_all() {
            return sold_all;
        }

        public void setSold_all(String sold_all) {
            this.sold_all = sold_all;
        }

        public String getGoods_all() {
            return goods_all;
        }

        public void setGoods_all(String goods_all) {
            this.goods_all = goods_all;
        }

        public String getTime_on_work() {
            return time_on_work;
        }

        public void setTime_on_work(String time_on_work) {
            this.time_on_work = time_on_work;
        }

        public String getShop_pos() {
            return shop_pos;
        }

        public void setShop_pos(String shop_pos) {
            this.shop_pos = shop_pos;
        }

        public String getShop_num() {
            return shop_num;
        }

        public void setShop_num(String shop_num) {
            this.shop_num = shop_num;
        }
    }

    public static class AttrBean {
        /**
         * attribute_id : 9
         * attribute_value : 特别需求
         * data_extend : 10
         * data_value : 厂房
         * label_fg : 0
         */

        private int attribute_id;
        private String attribute_value;
        private String data_extend;
        private String data_value;
        private int label_fg;

        public int getAttribute_id() {
            return attribute_id;
        }

        public void setAttribute_id(int attribute_id) {
            this.attribute_id = attribute_id;
        }

        public String getAttribute_value() {
            return attribute_value;
        }

        public void setAttribute_value(String attribute_value) {
            this.attribute_value = attribute_value;
        }

        public String getData_extend() {
            return data_extend;
        }

        public void setData_extend(String data_extend) {
            this.data_extend = data_extend;
        }

        public String getData_value() {
            return data_value;
        }

        public void setData_value(String data_value) {
            this.data_value = data_value;
        }

        public int getLabel_fg() {
            return label_fg;
        }

        public void setLabel_fg(int label_fg) {
            this.label_fg = label_fg;
        }
    }

    public static class PicsBean {
        /**
         * need_pic_id : 1
         * pic_title :
         * pic_path : http://tc.liebianzhe.com/upload/upload/20181010/20181010210022256.jpg
         */

        private int need_pic_id;
        private String pic_title;
        private String pic_path;

        public int getNeed_pic_id() {
            return need_pic_id;
        }

        public void setNeed_pic_id(int need_pic_id) {
            this.need_pic_id = need_pic_id;
        }

        public String getPic_title() {
            return pic_title;
        }

        public void setPic_title(String pic_title) {
            this.pic_title = pic_title;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }
    }

    public static class InfoLikeBean {
        /**
         * service_need_id : 3
         * pic_path : http://tc.liebianzhe.com/upload/upload/20181010/20181010210022256.jpg
         * info_title : 标题vv
         * time_setup : 1539176431
         * urgent_fg : 0
         * baidu_pos :
         * info_describe : 详细内容
         * visit_times : 0
         */
        private int service_need_id;
        private String pic_path;
        private String info_title;
        private int time_setup;
        private int urgent_fg;
        private String baidu_pos;
        private String info_describe;
        private int visit_times;

        public int getService_need_id() {
            return service_need_id;
        }

        public void setService_need_id(int service_need_id) {
            this.service_need_id = service_need_id;
        }

        public String getPic_path() {
            return pic_path;
        }

        public void setPic_path(String pic_path) {
            this.pic_path = pic_path;
        }

        public String getInfo_title() {
            return info_title;
        }

        public void setInfo_title(String info_title) {
            this.info_title = info_title;
        }

        public int getTime_setup() {
            return time_setup;
        }

        public void setTime_setup(int time_setup) {
            this.time_setup = time_setup;
        }

        public int getUrgent_fg() {
            return urgent_fg;
        }

        public void setUrgent_fg(int urgent_fg) {
            this.urgent_fg = urgent_fg;
        }

        public String getBaidu_pos() {
            return baidu_pos;
        }

        public void setBaidu_pos(String baidu_pos) {
            this.baidu_pos = baidu_pos;
        }

        public String getInfo_describe() {
            return info_describe;
        }

        public void setInfo_describe(String info_describe) {
            this.info_describe = info_describe;
        }

        public int getVisit_times() {
            return visit_times;
        }

        public void setVisit_times(int visit_times) {
            this.visit_times = visit_times;
        }
    }
}
