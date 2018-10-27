package com.wxkj.tongcheng.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 同城 筛选数据bean
 * Created by cheng on 2018/10/17.
 */

public class CityScreenBean implements Serializable{

    /**
     * service_attribute_id : 9
     * service_attribute_name : 特别需求
     * type_id : 61
     * type_name : 租房一级
     * value : [{"attribute_data_id":9,"service_attribute_id":9,"attribute_data_name":"空房"},{"attribute_data_id":10,"service_attribute_id":9,"attribute_data_name":"厂房"}]
     */

    private int service_attribute_id;
    private String service_attribute_name;
    private int type_id;
    private String type_name;
    private List<ValueBean> value;

    public int getService_attribute_id() {
        return service_attribute_id;
    }

    public void setService_attribute_id(int service_attribute_id) {
        this.service_attribute_id = service_attribute_id;
    }

    public String getService_attribute_name() {
        return service_attribute_name;
    }

    public void setService_attribute_name(String service_attribute_name) {
        this.service_attribute_name = service_attribute_name;
    }

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

    public List<ValueBean> getValue() {
        return value;
    }

    public void setValue(List<ValueBean> value) {
        this.value = value;
    }

    public static class ValueBean implements Serializable{
        /**
         * attribute_data_id : 9
         * service_attribute_id : 9
         * attribute_data_name : 空房
         */

        private int attribute_data_id;
        private int service_attribute_id;
        private String attribute_data_name;
        private boolean choose;

        public int getAttribute_data_id() {
            return attribute_data_id;
        }

        public void setAttribute_data_id(int attribute_data_id) {
            this.attribute_data_id = attribute_data_id;
        }

        public int getService_attribute_id() {
            return service_attribute_id;
        }

        public void setService_attribute_id(int service_attribute_id) {
            this.service_attribute_id = service_attribute_id;
        }

        public String getAttribute_data_name() {
            return attribute_data_name;
        }

        public void setAttribute_data_name(String attribute_data_name) {
            this.attribute_data_name = attribute_data_name;
        }

        public boolean isChoose() {
            return choose;
        }

        public void setChoose(boolean choose) {
            this.choose = choose;
        }
    }
}
