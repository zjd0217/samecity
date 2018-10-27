package com.wxkj.tongcheng.bean;

import com.wxkj.tongcheng.retrofit.IKeepProguard;

/**
 * Created by cheng on 2018/10/7.
 */

public class PageableDataBean implements IKeepProguard {


    /**
     * total : 1
     * pageNo : null
     * pageSize : null
     * last : 1
     * list : [{"id":"1","leaveType":"1","leaveDay":2,"applyerId":44,"createDate":1522662033000,"delFlag":"0","beginDate":1522598400000,"endDate":1522598400000,"leaveState":"2","updateDate":null,"auditId":null}]
     */

    private String total;
    private int pageNo;
    private int pageSize;
    private String last;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

}
