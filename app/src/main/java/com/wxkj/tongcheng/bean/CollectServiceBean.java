package com.wxkj.tongcheng.bean;

/**
 * 收藏服务 bean
 * Created by cheng on 2018/10/23.
 */

public class CollectServiceBean {

    /**
     * collection_id : 9
     * service_provide_id : 4
     * data_id : 4
     * time_setup : 1537173107
     * title : 新房出租
     * baidu_pos :
     * visit_times : 0
     * urgent_fg : 1
     * pic : http://tc.liebianzhe.com/upload/upload/20180917/20180917163116629.png
     * label : []
     */

    private int collection_id;
    private int service_provide_id;
    private int data_id;
    private int time_setup;
    private String title;
    private String baidu_pos;
    private int visit_times;
    private int urgent_fg;
    private String pic;

    public int getCollection_id() {
        return collection_id;
    }

    public void setCollection_id(int collection_id) {
        this.collection_id = collection_id;
    }

    public int getService_provide_id() {
        return service_provide_id;
    }

    public void setService_provide_id(int service_provide_id) {
        this.service_provide_id = service_provide_id;
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

    public String getBaidu_pos() {
        return baidu_pos;
    }

    public void setBaidu_pos(String baidu_pos) {
        this.baidu_pos = baidu_pos;
    }

    public int getVisit_times() {
        return visit_times;
    }

    public void setVisit_times(int visit_times) {
        this.visit_times = visit_times;
    }

    public int getUrgent_fg() {
        return urgent_fg;
    }

    public void setUrgent_fg(int urgent_fg) {
        this.urgent_fg = urgent_fg;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
