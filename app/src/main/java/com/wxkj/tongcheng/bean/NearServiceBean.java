package com.wxkj.tongcheng.bean;

/**
 * 附近服务bean
 * Created by cheng on 2018/10/8.
 */

public class NearServiceBean {

    /**
     * service_need_id : 26
     * time_setup : 1537155361
     * time_overdue : 1677772800
     * info_title : 标题
     * urgent_fg : 0
     * user_nickname : 昵称
     * contact_phone : 联系电话
     * info_describe : 详细内
     * info_voice :
     * region_path : 黑龙江省->佳木斯市->同江市
     * baidu_pos :
     * pic_path :
     */

    private int service_need_id;
    private int service_provide_id;
    private int time_setup;
    private int time_overdue;
    private String info_title;
    private int urgent_fg;
    private String user_nickname;
    private String contact_phone;
    private String info_describe;
    private String info_voice;
    private String region_path;
    private String baidu_pos;
    private String pic_path;

    public int getService_need_id() {
        return service_need_id;
    }

    public void setService_need_id(int service_need_id) {
        this.service_need_id = service_need_id;
    }

    public int getService_provide_id() {
        return service_provide_id;
    }

    public void setService_provide_id(int service_provide_id) {
        this.service_provide_id = service_provide_id;
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

    public int getUrgent_fg() {
        return urgent_fg;
    }

    public void setUrgent_fg(int urgent_fg) {
        this.urgent_fg = urgent_fg;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
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

    public String getRegion_path() {
        return region_path;
    }

    public void setRegion_path(String region_path) {
        this.region_path = region_path;
    }

    public String getBaidu_pos() {
        return baidu_pos;
    }

    public void setBaidu_pos(String baidu_pos) {
        this.baidu_pos = baidu_pos;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }
}
