package com.wxkj.tongcheng.retrofit;

/**
 * 接口地址
 * Created by cheng on 2018/10/7.
 */

public interface ApiConstants {

    interface URL {
        String BASE_URL = "http://tc.liebianzhe.com/";
    }

    interface CITY {
        //获取同城banner
        String GET_BANNER_DATA = "index/Jkindex/samecity.html";
        //获取附近服务 需求信息
        String GET_NEAR_SERVICE = "index/Jkindex/service.html";
        //获取服务 详情
        String GET_SERVICE_DETAIL = "index/JkProvideInfo/detail.html";
        //获取需求详情
        String GET_DEMAND_DETAIL = "index/JkNeedInfo/detail.html";
        //获取热门词汇
        String GET_HOT_DATA = "index/Jkindex/service_key.html";
        //收藏
        String COLLECT = "index/JkDownloadCollection/dataadd.html";
        //删除收藏
        String DELETE_COLLECT = "index/JkDownloadCollection/datadel.html";
        //获取搜索的服务列表
        String GET_SEARCH_SERVICE_LIST = "index/JkProvideInfo.html";
        //获取搜索的需求列表
        String GET_SEARCH_DEMAND_LIST = "index/JkNeedInfo.html";
        //获取分类列表数据
        String GET_TYPE_LIST = "index/JkDownloadinfotp/goodstype.html";
        //获取筛选列表数据
        String GET_SCREEN_LIST = "index/JkDownloadinfotp/get_attr.html";
        //获取商品列表
        String GET_GOODS_LIST = "index/Jkindex/goods_list.html";

    }

    interface GROUP {
        //获取拼团banner和商品分类等头部信息
        String GET_COLLAGE_BANNER = "index/Jkindex/pteam.html";
        //获取拼团界面  团购信息
        String GET_COLLAGE_TEAMBUY = "index/Jkindex/teambuy.html";
        //获取拼团 商品详情
        String COLLAGE_GOODS_DETAIL = "index/Jkindex/goods_detail.html";
        //获取拼团 商品优惠券
        String COLLAGE_GOODS_SHOPCOUPON = "index/JkCoupon/shopcoupon.html";
        //用户获取优惠券
        String GET_GOODS_SHOPCOUPON = "index/JkCoupon/get.html";
        //获取商品标签
        String GET_LABEL_LIST = "index/JkDownloadinfotp/getlabels.html";
        //获取商品评论列表
        String GET_EVALUATE_LIST = "index/Jkindex/goods_discuss.html";
        //店铺详情
        String GET_SHOP_DETAIL = "index/Jkindex/shop_detail.html";
        //商品详情
        String GET_CLASSIFY_GOODS = "index/Jkindex/classify_goods.html";
        //保存订单
        String SAVE_ORDER = "index/Jkuserbuy/save_order.html";
        //获取默认地址
        String GET_DEFAULT_ADDRESS = "index/Jkconsigneeaddr/get_default.html";
    }

    /**
     * 个人中心模块接口地址
     */
    interface USER_URL {
        //获取验证码
        String GET_SMS_CODE = "index/jk_regist/get_msg_code.html";
        //账号密码登录
        String PWD_LOGIN = "index/jk_regist/login.html";
        //注册
        String REGISTER = "index/jk_regist/check_and_regist.html";
        //重置密码
        String RESET_PWD = "index/jk_regist/set_new_pass.html";
        //验证码登录
        String CODE_LOGIN = "index/jk_regist/loginbycode.html";
        //修改昵称
        String CHANGE_USER_NAME = "index/JkUserInfo/changeuser_cname.html";
        //修改用户头像
        String CHANGE_USER_HEAD = "index/JkUserInfo/change_portrait.html";
        //修改性别
        String CHANGE_USER_SEX = "index/JkUserInfo/changeuser_sex.html";
        //修改生日
        String CHANGE_USER_BIRTHDAY = "index/JkUserInfo/changeuser_birth.html";
        //验证手机号
        String CHECK_PHONE_CODE = "index/JkUserInfo/check_code.html";
        //修改手机号
        String CHANGE_PHONE = "index/JkUserInfo/change_mobile.html";
        //修改密码
        String CHANGE_PWD = "index/jk_regist/set_new_pass.html";
        //获取钱包金额
        String GET_WALLET_NUM = "index/JkUserInfo/getcapital.html";
        //获取银行卡号列表
        String GET_BANK_CARD_LIST = "index/JkUserInfo/getaccount.html";
        //获取收货地址列表
        String GET_ADDRESS_LIST = "index/Jkconsigneeaddr.html";
        //添加银行卡
        String ADD_BANK_CARD = "index/JkUserInfo/saveaccount.html";
        //设置 提现密码
        String SET_FORWARD_PWD = "index/JkUserInfo/setpaypass.html";
        //获取提现记录列表
        String GET_PUTWARD_RECORD = "index/JkUserInfo/enchashment_list.html";
        //获取收藏列表
        String GET_COLLECT_LIST = "index/JkDownloadCollection/datalist.html";
        //投诉建议
        String COMPLAIN = "index/JkUserInfo/user_complain.html";
        //删除地址信息
        String DELETE_ADDRESS = "index/Jkconsigneeaddr/del.html";
        //保存（修改）地址
        String SAVE_OR_UPDATE_ADDRESS = "index/Jkconsigneeaddr/add.html";
        //用户提现
        String USER_PUTWARD = "index/JkUserInfo/enchashment.html";
        //获取用户优惠券
        String GET_USER_COUPON = "index/JkCoupon.html";
    }

    interface HOME_URL {
        //首页banner、分类、本地头条、秒杀活动
        String GET_HOME_BANNER_CLASSITY = "index/Jkindex.html";
        //首页最下面的秒杀列表
        String GET_HOME_SECKILL_LIST = "index/Jkindex/seckill.html";
    }

    interface DEMAND_URL {
        //获取需求的一级分类
        String GET_DEMAND_TYPE = "index/JkDownloadinfotp.html";
        //获取需求的二级分类
        String GET_DEMAND_SECOND_LEVEL_TYPE = "index/JkDownloadinfotp/gettpson.html";
        //上传语音
        String UPLOAD_VOICE_FILE = "index/JkUserInfo/save_service_voice.html";
    }

    interface DEMO_URL {
        //获取下载服务类型数据
        String GET_DWONLOAD_TYPE = "index/JkDownloadinfotp.html";
    }

    interface PARAMS {
        String MOBILE = "mobile_num";
        String PWD = "user_pass";
        String SMS_CODE = "msg_code";
        String SPREAD_NUM = "mobile_num_spread";
        String NUM = "num";
        String TP = "tp";
        String USER_ID = "user_id";
        String TOKEN = "token";
        String SERVICE_NEDD_ID = "service_need_id";
        String SERVICE_PROVIDE_ID = "service_provide_id";
        String CODE_TYPE = "code_type";
        String SEARCH_KEY = "search_key";
        String TYPE_ID = "type_id";
        String GOODS_ID = "goods_id";
        String GOODS_IDS = "goods_ids";
        String TEAMBUY_ID = "teambuy_id";
        String COLLECTION_ID = "collection_id";
        String COLLECTION_TYPE = "collection_type";
        String DATA_ID = "data_id";
        String USER_CNAME = "user_cname";
        String HEAD_PORTRAIT = "head_portrait";
        String USER_SEX = "user_sex";
        String USER_BIRTHDAY = "user_birthday";
        String COUPON_ID = "coupon_id";
        String MSG_ID = "msg_id";
        String GRADE_GOODS = "grade_goods";
        String SHOP_ID = "shop_id";
        String ATTRIBUTE_DATA_ID = "attribute_data_id";
        String ODBY = "odby";
        String BANK_ADDR = "bank_addr";
        String ACCOUNT_INFO = "account_info";
        String ACCOUNT_MAN = "account_man";
        String USER_PAY_PASS = "user_pay_pass";
        String TITLE = "title";
        String COMPLAIN_MAN = "complain_man";
        String PHONE_NUM = "phone_num";
        String CONTENT = "content";
        String ACCOUNT_CHANNEL = "account_channel";
        String ADDR_ID = "addr_id";
        String REGION_ID = "region_id";
        String ADDR_TITLE = "addr_title";
        String ADDR_DETAIL = "addr_detail";
        String BAIDU_POS = "baidu_pos";
        String CONTACT_MAN = "contact_man";
        String CONTACT_NUMBER = "contact_number";
        String DEFAULT_FG = "default_fg";
        String MEMO = "memo";
        String CLASSIFY_ID = "classify_id";
        String BUY_MODE = "buy_mode";
        String USER_COUPON_ID = "user_coupon_id";
        String TRANSPORT_FEE = "transport_fee";
        String USER_ID_SHAREBY = "user_id_shareby";
        String GOODS_NUMS = "goods_nums";
        String GOODS_PRICES = "goods_prices";
        String MODE_IDS = "mode_ids";
        String GET_BY = "get_by";
        String MAN_NUM_NEED = "man_num_need";
        String GOODS_TP="goods_tp";
        String SERVICE_TP = "service_tp";
        String SERVICE_SOUND = "service_sound";
        String CAPITAL_NUM="capital_num";
    }
}
