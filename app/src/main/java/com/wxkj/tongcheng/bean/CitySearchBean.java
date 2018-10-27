package com.wxkj.tongcheng.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 同城搜索 bean
 * Created by cheng on 2018/10/10.
 */

@Table(name="city_search")
public class CitySearchBean {
    @Column(name="id",isId = true)
    private int id;
    @Column(name = "type")
    private int type;
    @Column(name = "content")
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
