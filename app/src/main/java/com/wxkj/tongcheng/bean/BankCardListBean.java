package com.wxkj.tongcheng.bean;

import java.io.Serializable;

/**
 * 银行卡号列表bean
 * Created by cheng on 2018/10/18.
 */

public class BankCardListBean implements Serializable{

    /**
     * account_channel : 3
     * bank_addr : 就
     * account_info : 94949464545454545451
     * account_man : 看看
     */

    private int account_channel;
    private String bank_addr;
    private String account_info;
    private String account_man;

    public int getAccount_channel() {
        return account_channel;
    }

    public void setAccount_channel(int account_channel) {
        this.account_channel = account_channel;
    }

    public String getBank_addr() {
        return bank_addr;
    }

    public void setBank_addr(String bank_addr) {
        this.bank_addr = bank_addr;
    }

    public String getAccount_info() {
        return account_info;
    }

    public void setAccount_info(String account_info) {
        this.account_info = account_info;
    }

    public String getAccount_man() {
        return account_man;
    }

    public void setAccount_man(String account_man) {
        this.account_man = account_man;
    }
}
