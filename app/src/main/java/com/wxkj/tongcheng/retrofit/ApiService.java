package com.wxkj.tongcheng.retrofit;

import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.bean.ChangeUserHeadBean;
import com.wxkj.tongcheng.bean.CheckPhoneBean;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.CityScreenBean;
import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.bean.CollectGoodsBean;
import com.wxkj.tongcheng.bean.CouponBean;
import com.wxkj.tongcheng.bean.DefaultAddressBean;
import com.wxkj.tongcheng.bean.DownloadTypeBean;
import com.wxkj.tongcheng.bean.EvaluateBean;
import com.wxkj.tongcheng.bean.GoodsAttribute;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.bean.HomeBannerClassifyBean;
import com.wxkj.tongcheng.bean.HotSearchBean;
import com.wxkj.tongcheng.bean.LabelBean;
import com.wxkj.tongcheng.bean.MoreCollageBean;
import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.bean.PutForwardRecordBean;
import com.wxkj.tongcheng.bean.ReceivingAddressBean;
import com.wxkj.tongcheng.bean.ResponseModel;
import com.wxkj.tongcheng.bean.CollectServiceBean;
import com.wxkj.tongcheng.bean.ServiceDemandDetailBean;
import com.wxkj.tongcheng.bean.ShopDetailGoodsBean;
import com.wxkj.tongcheng.bean.ShopDetailsBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.bean.SmsCodeBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.bean.WalletNumBean;
import com.wxkj.tongcheng.ui.activity.demand.SelectTypeDemandEntity;
import com.wxkj.tongcheng.ui.activity.demand.category.CategoryEntity;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * Created by cheng on 2018/10/7.
 */

public interface ApiService {
    //获取验证码
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.GET_SMS_CODE)
    Observable<ResponseModel<SmsCodeBean>> getSmsCode(@Field(ApiConstants.PARAMS.MOBILE) String mobile,
                                                      @Field(ApiConstants.PARAMS.CODE_TYPE) int codeType);

    //注册
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.REGISTER)
    Observable<ResponseModel<SimpleResponseEntity>> register(@Field(ApiConstants.PARAMS.MOBILE) String mobile,
                                                             @Field(ApiConstants.PARAMS.SMS_CODE) String smsCode,
                                                             @Field(ApiConstants.PARAMS.PWD) String pwd,
                                                             @Field(ApiConstants.PARAMS.SPREAD_NUM) String spreadNum);

    //登录
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.PWD_LOGIN)
    Observable<ResponseModel<UserInfo>> pwdLogin(@Field(ApiConstants.PARAMS.MOBILE) String mobile,
                                                 @Field(ApiConstants.PARAMS.PWD) String pwd);

    //获取下载数据类型
    @GET(ApiConstants.DEMO_URL.GET_DWONLOAD_TYPE)
    Observable<ResponseModel<List<DownloadTypeBean>>> getDownloadType();


    @GET(ApiConstants.CITY.GET_BANNER_DATA)
    Observable<ResponseModel<CityBannerBean>> getCityBannerData();

    @GET(ApiConstants.CITY.GET_NEAR_SERVICE)
    Observable<ResponseModel<List<NearServiceBean>>> getNearServiceList(@Query(ApiConstants.PARAMS.NUM) String num,
                                                                        @Query(ApiConstants.PARAMS.TP) int tp);

    //获取拼团的Banner
    @GET(ApiConstants.GROUP.GET_COLLAGE_BANNER)
    Observable<ResponseModel<GroupTitleBean>>
    getGroupBannerData(@Query(ApiConstants.PARAMS.USER_ID) String userId,
                       @Query(ApiConstants.PARAMS.TOKEN) String token,
                       @Query(ApiConstants.PARAMS.NUM) String num);


    //获取拼团的超值热卖
    @GET(ApiConstants.GROUP.GET_COLLAGE_TEAMBUY)
    Observable<ResponseModel<List<GroupTitleBean.TeambuyBean>>>
    getGroupTeambuyList(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                        @Query(ApiConstants.PARAMS.TOKEN) String token,
                        @Query(ApiConstants.PARAMS.NUM) String num,
                        @Query(ApiConstants.PARAMS.SEARCH_KEY) String search_key,
                        @Query(ApiConstants.PARAMS.ODBY) int odby,
                        @Query(ApiConstants.PARAMS.MAN_NUM_NEED) String man_num_need,
                        @Query(ApiConstants.PARAMS.TYPE_ID) int type_id,
                        @Query(ApiConstants.PARAMS.GOODS_TP)int goods_tp
                        );

    //获取拼团的商品详情
    @GET(ApiConstants.GROUP.COLLAGE_GOODS_DETAIL)
    Observable<ResponseModel<GroupGoodsDetailBean>>
    getGoodsDetail(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                   @Query(ApiConstants.PARAMS.TOKEN) String token,
                   @Query(ApiConstants.PARAMS.GOODS_ID) int goods_id,
                   @Query(ApiConstants.PARAMS.TEAMBUY_ID) int teambuy_id);

    //获取服务详情
    @GET(ApiConstants.CITY.GET_SERVICE_DETAIL)
    Observable<ResponseModel<ServiceDemandDetailBean>> getServiceDetail(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                                        @Query(ApiConstants.PARAMS.TOKEN) String token,
                                                                        @Query(ApiConstants.PARAMS.SERVICE_PROVIDE_ID) int proviceId);

    //获取需求详情
    @GET(ApiConstants.CITY.GET_DEMAND_DETAIL)
    Observable<ResponseModel<ServiceDemandDetailBean>> getDemandDetail(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                                       @Query(ApiConstants.PARAMS.TOKEN) String token,
                                                                       @Query(ApiConstants.PARAMS.SERVICE_NEDD_ID) int needId);

    //重置密码
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.RESET_PWD)
    Observable<ResponseModel<SimpleResponseEntity>> resetPwd(@Field(ApiConstants.PARAMS.MOBILE) String phone,
                                                             @Field(ApiConstants.PARAMS.SMS_CODE) String smsCode,
                                                             @Field(ApiConstants.PARAMS.PWD) String pwd);

    //验证码登录
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.CODE_LOGIN)
    Observable<ResponseModel<UserInfo>> codeLogin(@Field(ApiConstants.PARAMS.MOBILE) String phone,
                                                  @Field(ApiConstants.PARAMS.SMS_CODE) String smsCode);

    //获取热门搜索词汇
    @GET(ApiConstants.CITY.GET_HOT_DATA)
    Observable<ResponseModel<HotSearchBean>> getHotSearchData(@Query(ApiConstants.PARAMS.TP) int tp);

    //收藏
    @FormUrlEncoded
    @POST(ApiConstants.CITY.COLLECT)
    Observable<ResponseModel<SimpleResponseEntity>> collect(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                            @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                            @Field(ApiConstants.PARAMS.COLLECTION_TYPE) int collect_type,
                                                            @Field(ApiConstants.PARAMS.DATA_ID) int dataId);

    //取消收藏
    @FormUrlEncoded
    @POST(ApiConstants.CITY.DELETE_COLLECT)
    Observable<ResponseModel<SimpleResponseEntity>> deleteCollect(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                  @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                  @Field(ApiConstants.PARAMS.COLLECTION_ID) int collect_id);

    //修改用户昵称
    @GET(ApiConstants.USER_URL.CHANGE_USER_NAME)
    Observable<ResponseModel<SimpleResponseEntity>> changeUserName(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                                   @Query(ApiConstants.PARAMS.TOKEN) String token,
                                                                   @Query(ApiConstants.PARAMS.USER_CNAME) String userName);

    //修改用户头像
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.CHANGE_USER_HEAD)
    Observable<ResponseModel<ChangeUserHeadBean>> changeUserHead(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                 @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                 @Field(ApiConstants.PARAMS.HEAD_PORTRAIT) String head_portrait);

    //修改用户性别
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.CHANGE_USER_SEX)
    Observable<ResponseModel<SimpleResponseEntity>> changeUserSex(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                  @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                  @Field(ApiConstants.PARAMS.USER_SEX) int sex);

    //修改用户性别
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.CHANGE_USER_BIRTHDAY)
    Observable<ResponseModel<SimpleResponseEntity>> changeUserBirthday(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                       @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                       @Field(ApiConstants.PARAMS.USER_BIRTHDAY) String birthday);

    //获取同城搜索 服务列表
    @GET(ApiConstants.CITY.GET_SEARCH_SERVICE_LIST)
    Observable<ResponseModel<List<NearServiceBean>>> getCitySearchServiceList(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                                              @Query(ApiConstants.PARAMS.TOKEN) String token,
                                                                              @Query(ApiConstants.PARAMS.TYPE_ID) int typeId,
                                                                              @Query(ApiConstants.PARAMS.NUM) String page,
                                                                              @Query(ApiConstants.PARAMS.SEARCH_KEY) String key,
                                                                              @Query(ApiConstants.PARAMS.ATTRIBUTE_DATA_ID) String attribute_data_id,
                                                                              @Query(ApiConstants.PARAMS.ODBY) int odby);

    //获取同城搜索 需求列表
    @GET(ApiConstants.CITY.GET_SEARCH_DEMAND_LIST)
    Observable<ResponseModel<List<NearServiceBean>>> getCitySearchDemandList(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                                             @Query(ApiConstants.PARAMS.TOKEN) String token,
                                                                             @Query(ApiConstants.PARAMS.TYPE_ID) int typeId,
                                                                             @Query(ApiConstants.PARAMS.NUM) String page,
                                                                             @Query(ApiConstants.PARAMS.SEARCH_KEY) String key,
                                                                             @Query(ApiConstants.PARAMS.ATTRIBUTE_DATA_ID) String attribute_data_id,
                                                                             @Query(ApiConstants.PARAMS.ODBY) int odby);

    //验证手机号验证码
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.CHECK_PHONE_CODE)
    Observable<ResponseModel<CheckPhoneBean>> checkPhoneCode(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                             @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                             @Field(ApiConstants.PARAMS.SMS_CODE) String code);

    //修改手机号
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.CHANGE_PHONE)
    Observable<ResponseModel<SimpleResponseEntity>> changePhone(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                @Field(ApiConstants.PARAMS.MSG_ID) int msgId,
                                                                @Field(ApiConstants.PARAMS.MOBILE) String phone,
                                                                @Field(ApiConstants.PARAMS.SMS_CODE) String code);

    //修改密码
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.CHANGE_PWD)
    Observable<ResponseModel<SimpleResponseEntity>> changePwd(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                              @Field(ApiConstants.PARAMS.SMS_CODE) String code,
                                                              @Field(ApiConstants.PARAMS.PWD) String pwd);

    //获取分类列表数据
    @GET(ApiConstants.CITY.GET_TYPE_LIST)
    Observable<ResponseModel<List<CityTypeBean>>> getCityTypeList();

    //获取商品列表
    @GET(ApiConstants.CITY.GET_GOODS_LIST)
    Observable<ResponseModel<List<CityTypeBean>>> getCityGoodsList(@Query(ApiConstants.PARAMS.TYPE_ID) int type,
                                                                   @Query(ApiConstants.PARAMS.NUM) String num);

    //获取同城搜索 服务列表
    @GET(ApiConstants.USER_URL.GET_WALLET_NUM)
    Observable<ResponseModel<WalletNumBean>> getWalletNum(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                          @Query(ApiConstants.PARAMS.TOKEN) String token);

    //获取筛选列表数据
    @GET(ApiConstants.CITY.GET_SCREEN_LIST)
    Observable<ResponseModel<List<CityScreenBean>>> getCityScreenList();

    //获取银行卡列表
    @GET(ApiConstants.USER_URL.GET_BANK_CARD_LIST)
    Observable<ResponseModel<List<BankCardListBean>>> getBankCardList(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                                      @Query(ApiConstants.PARAMS.TOKEN) String token);

    //添加银行卡信息
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.ADD_BANK_CARD)
    Observable<ResponseModel<SimpleResponseEntity>> addBankCard(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                @Field(ApiConstants.PARAMS.BANK_ADDR) String bankAddr,
                                                                @Field(ApiConstants.PARAMS.ACCOUNT_INFO) String bankCard,
                                                                @Field(ApiConstants.PARAMS.ACCOUNT_CHANNEL) int accountChannel,
                                                                @Field(ApiConstants.PARAMS.ACCOUNT_MAN) String name);

    //设置提现密码
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.SET_FORWARD_PWD)
    Observable<ResponseModel<SimpleResponseEntity>> setForwardPwd(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                  @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                  @Field(ApiConstants.PARAMS.SMS_CODE) String code,
                                                                  @Field(ApiConstants.PARAMS.USER_PAY_PASS) String pwd);

    //获取提现记录
    @GET(ApiConstants.USER_URL.GET_PUTWARD_RECORD)
    Observable<ResponseModel<List<PutForwardRecordBean>>> getPutwardRecord(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                                                                           @Query(ApiConstants.PARAMS.TOKEN) String token,
                                                                           @Query(ApiConstants.PARAMS.NUM) String page);

    //获取收藏列表
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.GET_COLLECT_LIST)
    Observable<ResponseModel<List<CollectServiceBean>>> getServiceDemanCollectList(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                                   @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                                   @Field(ApiConstants.PARAMS.NUM) String page,
                                                                                   @Field(ApiConstants.PARAMS.COLLECTION_TYPE) int type);

    Observable<ResponseModel<List<Object>>> getCollectList(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                @Field(ApiConstants.PARAMS.NUM) String page,
                                                                @Field(ApiConstants.PARAMS.COLLECTION_TYPE) int type);

    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.GET_COLLECT_LIST)
    Observable<ResponseModel<List<CollectGoodsBean>>> getCollectGoodsList(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                     @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                     @Field(ApiConstants.PARAMS.NUM) String page,
                                                                     @Field(ApiConstants.PARAMS.COLLECTION_TYPE) int type);

    //用户提现
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.USER_PUTWARD)
    Observable<ResponseModel<SimpleResponseEntity>> userPutWardMoney(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                     @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                     @Field(ApiConstants.PARAMS.USER_PAY_PASS) String pwd,
                                                                     @Field(ApiConstants.PARAMS.CAPITAL_NUM) int money,
                                                                     @Field(ApiConstants.PARAMS.ACCOUNT_CHANNEL) int channel,
                                                                     @Field(ApiConstants.PARAMS.BANK_ADDR) String backAddr,
                                                                     @Field(ApiConstants.PARAMS.ACCOUNT_INFO) String bankCard,
                                                                     @Field(ApiConstants.PARAMS.ACCOUNT_MAN) String userName);

    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.COMPLAIN)
    Observable<ResponseModel<SimpleResponseEntity>> submitComplain(@Field(ApiConstants.PARAMS.USER_ID) int userId,
                                                                   @Field(ApiConstants.PARAMS.TOKEN) String token,
                                                                   @Field(ApiConstants.PARAMS.TITLE) String title,
                                                                   @Field(ApiConstants.PARAMS.COMPLAIN_MAN) String name,
                                                                   @Field(ApiConstants.PARAMS.PHONE_NUM) String phone,
                                                                   @Field(ApiConstants.PARAMS.CONTENT) String content);


    //查看商品优惠券
    @GET(ApiConstants.GROUP.COLLAGE_GOODS_SHOPCOUPON)
    Observable<ResponseModel<List<Map<String, String>>>>
    getGoodsShopCouponList(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                           @Query(ApiConstants.PARAMS.TOKEN) String token,
                           @Query(ApiConstants.PARAMS.GOODS_ID) int goods_id);

    //用户获取优惠券
    @GET(ApiConstants.GROUP.GET_GOODS_SHOPCOUPON)
    Observable<ResponseModel<SimpleResponseEntity>> getGoodsShopCoupon(
            @Query(ApiConstants.PARAMS.USER_ID) int userId,
            @Query(ApiConstants.PARAMS.TOKEN) String token,
            @Query(ApiConstants.PARAMS.COUPON_ID) String coupon_id
    );

    //获取商品标签
    @GET(ApiConstants.GROUP.GET_LABEL_LIST)
    Observable<ResponseModel<List<LabelBean>>> getLabelList(@Query(ApiConstants.PARAMS.GOODS_ID) int goods_id);


    //获取商品的拼团人员列表
    @GET(ApiConstants.GROUP.GET_COLLAGE_TEAMBUY)
    Observable<ResponseModel<List<MoreCollageBean>>>
    getMoreCollageList(@Query(ApiConstants.PARAMS.GOODS_ID) int goods_id);

    //获取商品评论
    @GET(ApiConstants.GROUP.GET_EVALUATE_LIST)
    Observable<ResponseModel<List<EvaluateBean>>>
    getEvaluateList(@Query(ApiConstants.PARAMS.GOODS_ID) int goods_id,
                    @Query(ApiConstants.PARAMS.GRADE_GOODS) String grade_goods,
                    @Query(ApiConstants.PARAMS.NUM) String num);

    //获取店铺详情
    @GET(ApiConstants.GROUP.GET_SHOP_DETAIL)
    Observable<ResponseModel<ShopDetailsBean>>
    getShopDetail(@Query(ApiConstants.PARAMS.SHOP_ID) int shop_id);

    //获取商品列表
    @GET(ApiConstants.CITY.GET_GOODS_LIST)
    Observable<ResponseModel<List<ShopDetailGoodsBean>>>
    getGoodsList(@Query(ApiConstants.PARAMS.SHOP_ID) int shop_id,
                 @Query(ApiConstants.PARAMS.ODBY) int odby,
                 @Query(ApiConstants.PARAMS.NUM) String num);


    //获取收货地址
    @GET(ApiConstants.USER_URL.GET_ADDRESS_LIST)
    Observable<ResponseModel<List<ReceivingAddressBean>>> getAddressList(
            @Query(ApiConstants.PARAMS.USER_ID) int userId,
            @Query(ApiConstants.PARAMS.TOKEN) String token);


    //首页banner、分类、本地头条、秒杀活动
    @GET(ApiConstants.HOME_URL.GET_HOME_BANNER_CLASSITY)
    Observable<ResponseModel<HomeBannerClassifyBean>>
    getHomeBannerClassify(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                          @Query(ApiConstants.PARAMS.TOKEN) String token,
                          @Query(ApiConstants.PARAMS.NUM) String num);

    //首页秒杀列表
    @GET(ApiConstants.HOME_URL.GET_HOME_SECKILL_LIST)
    Observable<ResponseModel<List<HomeBannerClassifyBean.SeckillEntity>>>
    getHomeSeckillList(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                       @Query(ApiConstants.PARAMS.TOKEN) String token,
                       @Query(ApiConstants.PARAMS.NUM) String num);

    //删除一条收货地址
    @GET(ApiConstants.USER_URL.DELETE_ADDRESS)
    Observable<ResponseModel<SimpleResponseEntity>> deleteAddress(
            @Query(ApiConstants.PARAMS.USER_ID) int userId,
            @Query(ApiConstants.PARAMS.TOKEN) String token,
            @Query(ApiConstants.PARAMS.ADDR_ID) int addr_id);

    //保存（修改）收货地址
    @FormUrlEncoded
    @POST(ApiConstants.USER_URL.SAVE_OR_UPDATE_ADDRESS)
    Observable<ResponseModel<SimpleResponseEntity>> saveOrUpdateAddress(
            @Field(ApiConstants.PARAMS.USER_ID) int userId,
            @Field(ApiConstants.PARAMS.TOKEN) String token,
            @Field(ApiConstants.PARAMS.ADDR_ID) int addr_id,
            @Field(ApiConstants.PARAMS.REGION_ID) int region_id,
            @Field(ApiConstants.PARAMS.ADDR_DETAIL) String addr_detail,
            @Field(ApiConstants.PARAMS.BAIDU_POS) String baidu_pos,
            @Field(ApiConstants.PARAMS.CONTACT_MAN) String contact_man,
            @Field(ApiConstants.PARAMS.CONTACT_NUMBER) String contact_number,
            @Field(ApiConstants.PARAMS.MEMO) String memo,
            @Field(ApiConstants.PARAMS.DEFAULT_FG) int default_fg);

    //商品属性详情
    @GET(ApiConstants.GROUP.GET_CLASSIFY_GOODS)
    Observable<ResponseModel<List<GoodsAttribute>>> getClassifyGoods(
            @Query(ApiConstants.PARAMS.CLASSIFY_ID) int classify_id);


    //保存订单
    @FormUrlEncoded
    @POST(ApiConstants.GROUP.SAVE_ORDER)
    Observable<ResponseModel<SimpleResponseEntity>> saveOrder(
            @Field(ApiConstants.PARAMS.USER_ID) int userId,
            @Field(ApiConstants.PARAMS.TOKEN) String token,
            //购买模式
            @Field(ApiConstants.PARAMS.BUY_MODE) int buy_mode,
            //地址id
            @Field(ApiConstants.PARAMS.ADDR_ID) int addr_id,
            //优惠券id，没有传-1
            @Field(ApiConstants.PARAMS.USER_COUPON_ID) int user_coupon_id,
            //运费，没有就传0
            @Field(ApiConstants.PARAMS.TRANSPORT_FEE) int transport_fee,
            //支付密码（md5加密）
            @Field(ApiConstants.PARAMS.USER_PAY_PASS) String user_pay_pass,
            //商品分享者id，没有就传-1
            @Field(ApiConstants.PARAMS.USER_ID_SHAREBY) int user_id_shareby,
            //支付方式   1、支付宝     2、微信
            @Field(ApiConstants.PARAMS.ACCOUNT_CHANNEL) int account_channel,
            //商品id
            @Field(ApiConstants.PARAMS.GOODS_IDS) int goods_ids,
            //商品数量
            @Field(ApiConstants.PARAMS.GOODS_NUMS) int goods_nums,
            //商品价格（单位分）
            @Field(ApiConstants.PARAMS.GOODS_PRICES) String goods_prices,
            //模式数据id
            @Field(ApiConstants.PARAMS.MODE_IDS) String mode_ids,
            //消费方式
            @Field(ApiConstants.PARAMS.GET_BY) int get_by);

    //获取默认收货地址
    @GET(ApiConstants.GROUP.GET_DEFAULT_ADDRESS)
    Observable<ResponseModel<DefaultAddressBean>> getDefaultAddress(
            @Query(ApiConstants.PARAMS.USER_ID) int userId,
            @Query(ApiConstants.PARAMS.TOKEN) String token);

    //获取分类列表数据
    @GET(ApiConstants.CITY.GET_TYPE_LIST)
    Observable<ResponseModel<List<CityTypeBean>>>
    getGroupTypeList(@Query(ApiConstants.PARAMS.TYPE_ID) int type_id);


    //获取优惠券
    @GET(ApiConstants.USER_URL.GET_USER_COUPON)
    Observable<ResponseModel<List<CouponBean>>> getCouponList(
            @Query(ApiConstants.PARAMS.USER_ID) int userId,
            @Query(ApiConstants.PARAMS.TOKEN) String token,
            @Query(ApiConstants.PARAMS.NUM) String num,
            //商品id（查看商品可以使用的优惠券），不传(-1)表示查看所有优惠券
            @Query(ApiConstants.PARAMS.GOODS_ID) int goods_id);

    //获取需求的一级分类
    @GET(ApiConstants.DEMAND_URL.GET_DEMAND_TYPE)
    Observable<ResponseModel<List<SelectTypeDemandEntity>>>
    getDemandTypeList(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                      @Query(ApiConstants.PARAMS.TOKEN) String token);

    //获取需求的二级分类
    @GET(ApiConstants.DEMAND_URL.GET_DEMAND_SECOND_LEVEL_TYPE)
    Observable<ResponseModel<CategoryEntity>>
    getDemandSecondLevelType(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                             @Query(ApiConstants.PARAMS.TOKEN) String token,
                             @Query(ApiConstants.PARAMS.TYPE_ID) int type_id);

    //上传语音文件
    @POST(ApiConstants.DEMAND_URL.UPLOAD_VOICE_FILE)
    Observable<ResponseModel<SimpleResponseEntity>>
    uploadVoiceFile(@Query(ApiConstants.PARAMS.USER_ID) int userId,
                    @Query(ApiConstants.PARAMS.TOKEN) String token,
                    @Query(ApiConstants.PARAMS.SERVICE_TP) int service_tp,
                    @Query(ApiConstants.PARAMS.SERVICE_SOUND) String service_sound);

}
