package com.wxkj.tongcheng.bean;

/**
 * eventbus bean
 * Created by cheng on 2018/10/9.
 */

public class EventBusBean {
    //唯一码  必须保证唯一 具体值到 CodeUtil中添加
    private int code;
    //以下get set方法自定义
    private String phone;
    private String name;
    private String pwd;
    private int type;
    /** 筛选多人拼团 */
    private String man_num_need;
    /** 优惠券id */
    private int couponId;
    /** 优惠券的金额 */
    private int coupon_capital;
    private CityBannerBean.TypeBean typeBean;
    /** 商品属性实体 */
    private GoodsAttribute goodsAttribute;
    private BankCardListBean bankCardListBean;


    public int getCoupon_capital() {
        return coupon_capital;
    }

    public void setCoupon_capital(int coupon_capital) {
        this.coupon_capital = coupon_capital;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getMan_num_need() {
        return man_num_need;
    }

    public void setMan_num_need(String man_num_need) {
        this.man_num_need = man_num_need;
    }

    public GoodsAttribute getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(GoodsAttribute goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }


    public BankCardListBean getBankCardListBean() {
        return bankCardListBean;
    }

    public void setBankCardListBean(BankCardListBean bankCardListBean) {
        this.bankCardListBean = bankCardListBean;
    }

    public CityBannerBean.TypeBean getTypeBean() {
        return typeBean;
    }

    public void setTypeBean(CityBannerBean.TypeBean typeBean) {
        this.typeBean = typeBean;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
