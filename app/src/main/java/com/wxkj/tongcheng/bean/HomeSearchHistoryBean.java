package com.wxkj.tongcheng.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "home_search_history")
public class HomeSearchHistoryBean {

    @Column(name = "id", isId = true)
    public int id;

    @Column(name = "type")
    public int type;

    @Column(name = "content")
    public String content;

}
