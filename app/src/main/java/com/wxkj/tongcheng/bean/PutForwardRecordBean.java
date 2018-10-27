package com.wxkj.tongcheng.bean;

/**
 * 提现记录bean
 * Created by cheng on 2018/10/19.
 */

public class PutForwardRecordBean {

    /**
     * capital_num : 30
     * service_charge : 10
     * time_setup : 1534835334
     * enchashment_state : 0
     * out_biz_no : tc_1_0_20180821_1508534503
     * check_reson :
     * ym : 2018年08月
     */

    private int capital_num;
    private int service_charge;
    private int time_setup;
    private int enchashment_state;
    private String out_biz_no;
    private String check_reson;
    private String ym;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCapital_num() {
        return capital_num;
    }

    public void setCapital_num(int capital_num) {
        this.capital_num = capital_num;
    }

    public int getService_charge() {
        return service_charge;
    }

    public void setService_charge(int service_charge) {
        this.service_charge = service_charge;
    }

    public int getTime_setup() {
        return time_setup;
    }

    public void setTime_setup(int time_setup) {
        this.time_setup = time_setup;
    }

    public int getEnchashment_state() {
        return enchashment_state;
    }

    public void setEnchashment_state(int enchashment_state) {
        this.enchashment_state = enchashment_state;
    }

    public String getOut_biz_no() {
        return out_biz_no;
    }

    public void setOut_biz_no(String out_biz_no) {
        this.out_biz_no = out_biz_no;
    }

    public String getCheck_reson() {
        return check_reson;
    }

    public void setCheck_reson(String check_reson) {
        this.check_reson = check_reson;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }
}
