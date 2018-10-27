package com.wxkj.tongcheng.bean;

import java.io.Serializable;

/**
 * @author Liu haijun
 * @create 2018/10/17 0017
 * @Describe 商品标签
 */
public class LabelBean implements Serializable {


    private int label_id;
    private String label_name;
    private String label_disc;


    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public String getLabel_disc() {
        return label_disc;
    }

    public void setLabel_disc(String label_disc) {
        this.label_disc = label_disc;
    }
}
