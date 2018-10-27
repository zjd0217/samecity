package com.wxkj.tongcheng.util;

/**
 * code码管理
 * Created by cheng on 2018/10/9.
 */

public class CodeUtil {
    /**
     * eventbus 相关码  必须保证唯一
     * ！！！本人使用 1-100 的code码，请其他开发者 使用100以后的code码 ，避免重复 ，并添加上注释。
     */
    //注册成功
    public static final int REGISTER_SUCCESS = 1;
    //登录成功
    public static final int LOGIN_SUCCESS = 2;
    //选择分享平台
    public static final int CHOOSE_SHARE_TYPE = 3;
    //选择同城服务需求 的类型
    public static final int CHOOSE_CITY_SERVICE_TYPE = 4;
    //选择拼团服务类型
    public static final int CHOOSE_GROUP_SERVICE_TYPE = 5;

    //登录页面选择更多弹窗
    public static final int LOGIN_CHOOSE_MORE = 5;
    //修改昵称成功
    public static final int CHANGE_USER_NAME_SUCCESS = 6;
    //选择拍照还是相册
    public static final int CHOOSE_CAMERA_ALBUM = 7;
    //选择性别
    public static final int CHOOSE_SEX = 8;
    //修改头像成功
    public static final int CHANGE_HEAD_SUCCESS = 9;
    //修改手机号下一步
    public static final int CHANGE_PHONE_NEXT_STEP = 10;
    //选择同城筛选
    public static final int CHOOSE_CITY_SCREEN = 11;
    //点击同城热门 历史搜索
    public static final int CITY_HOT_HISTORY_SEARCH_ITEM = 12;

    //确定解除银行卡绑定
    public static final int SURE_DELETE_BANK_CARD = 13;
    //设置提现密码成功
    public static final int SET_FORWARD_PWD_SUCCESS = 14;
    //添加银行卡 选择银行
    public static final int ADD_BANK_CHOOSE_BANK = 15;
    //添加银行卡成功
    public static final int ADD_BANK_SUCCESS = 16;
    //提现方式 中 选择银行卡
    public static final int PUTWARD_MODE_CHOOSE_BACK = 17;
    //添加（新增）收货地址成功 刷新列表界面
    public static final int REFRESH_ADDRESS_LIST = 18;
    //选择商品属性之后，点击确定
    public static final int GOODS_ATTRIBUTE_CODE = 19;
    //拼团商品筛选确认的返回
    public static final int GROUP_GOODS_SCREEN_CODE = 20;
    //选择优惠券的返回码
    public static final int CHECKED_COUPON_CODE = 21;
}
