package com.wxkj.tongcheng.bean;

import java.io.Serializable;

/**
 * 钱包金额数量bean
 * Created by cheng on 2018/10/16.
 */

public class WalletNumBean implements Serializable{

    /**
     * user_capital : 0
     * lock_capital : 0
     * get_capital : 0
     * user_pay_pass : 0
     * account_channel : 0
     * bank_addr :
     * account_info :
     * account_man :
     */

    private int user_capital;
    private int lock_capital;
    private int get_capital;
    private int user_pay_pass;
    private int account_channel;
    private String bank_addr;
    private String account_info;
    private String account_man;

    public int getUser_capital() {
        return user_capital;
    }

    public void setUser_capital(int user_capital) {
        this.user_capital = user_capital;
    }

    public int getLock_capital() {
        return lock_capital;
    }

    public void setLock_capital(int lock_capital) {
        this.lock_capital = lock_capital;
    }

    public int getGet_capital() {
        return get_capital;
    }

    public void setGet_capital(int get_capital) {
        this.get_capital = get_capital;
    }

    public int getUser_pay_pass() {
        return user_pay_pass;
    }

    public void setUser_pay_pass(int user_pay_pass) {
        this.user_pay_pass = user_pay_pass;
    }

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
