package com.wxkj.tongcheng.ui.activity.group.goodsdetail.pay;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.DefaultAddressBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.GoodsAttribute;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.user.mycoupon.CouponActivity;
import com.wxkj.tongcheng.util.AMapLocationUtil;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.LocationUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/22 0022
 * @Describe 商品支付详情界面
 */
public class GoodsPayDetailActivity extends MvpBaseActivity<GoodsPayDetailPresenter>
        implements OnRetryListener, GoodsPayDetailView {


    @BindView(R.id.goods_pay_not_address)
    LinearLayout goodsPayNotAddress;
    @BindView(R.id.goods_pay_have_address)
    LinearLayout goodsPayHaveAddress;

    @BindView(R.id.goods_pay_address_name)
    TextView goodsPayAddressName;
    @BindView(R.id.goods_pay_address_phone)
    TextView goodsPayAddressPhone;
    @BindView(R.id.goods_pay_address_detail)
    TextView goodsPayAddressDetail;

    @BindView(R.id.goods_pay_shop_img)
    ImageView goodsPayShopImg;
    @BindView(R.id.goods_pay_shop_name)
    TextView goodsPayShopName;
    @BindView(R.id.goods_pay_shop_distance)
    TextView goodsPayShopDistance;

    @BindView(R.id.goods_pay_goods_img)
    ImageView goodsPayGoodsImg;
    @BindView(R.id.goods_pay_goods_name)
    TextView goodsPayGoodsName;
    @BindView(R.id.goods_pay_goods_attribute)
    TextView goodsPayGoodsAttribute;
    @BindView(R.id.goods_pay_goods_price)
    TextView goodsPayGoodsPrice;

    @BindView(R.id.goods_pay_goods_number)
    EditText goodsPayGoodsNumber;
    /** 付款方式 */
    @BindView(R.id.goods_pay_style_we_chat_checked)
    ImageView goodsPayStyleWeChatChecked;
    @BindView(R.id.goods_pay_style_remainder_checked)
    ImageView goodsPayStyleRemainderChecked;
    @BindView(R.id.goods_pay_style_al_pay_checked)
    ImageView goodsPayStyleAlPayChecked;
    /** 消费方式 */
    @BindView(R.id.goods_consume_style_home_checked)
    ImageView goodsConsumeStyleHomeChecked;
    @BindView(R.id.goods_consume_style_shop_checked)
    ImageView goodsConsumeStyleShopChecked;

    @BindView(R.id.goods_pay_number)
    TextView goodsPayNumber;
    @BindView(R.id.goods_pay_less)
    TextView goodsPayLess;
    @BindView(R.id.goods_pay_coupon)
    TextView goodsPayCoupon;

    /**
     * 支付方式    0、余额    1、支付宝    2、微信
     */
    private int payChannel = 2;
    /**
     * 消费方式   0、送货上门     1、到店消费
     */
    private int getBy = 0;
    /** 商品数量 */
    private int goodsNumber = 1;
    /** 优惠券id */
    private int couponId = -1;
    /** 当前一件商品价格 */
    private int price_market;
    /** 收货地址id */
    private int addrId = -1;
    /** 商品id */
    private int goods_id;
    /** 商品市场价 */
    private int priceMarket;
    private boolean byUser;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_goods_pay_detail)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "支付";
    }

    @Override
    protected GoodsPayDetailPresenter initPresenter() {
        return new GoodsPayDetailPresenter();
    }

    @Override
    protected void initView() {
        super.initView();
        //修改状态栏
        mImmersionBar.statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.colorfca).init();
        //获取默认的购物地址
        presenter.getDefaultAddress();
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        GoodsAttribute goodsAttribute =
                (GoodsAttribute) intent.getSerializableExtra("goodsAttribute");
        GroupGoodsDetailBean.GoodsBean goodsBean =
                (GroupGoodsDetailBean.GoodsBean) intent.getSerializableExtra("goodsBean");
        goods_id = goodsAttribute.getGoods_id();
        RequestOptions options = new RequestOptions().transform(new CenterCrop());
        options.error(R.drawable.load_failure);
        options.placeholder(R.drawable.loading);
        options.circleCrop();
        Glide.with(this).load(goodsBean.getShop_logo())
                .apply(options)
                .into(goodsPayShopImg);
        goodsPayShopName.setText(goodsBean.getShop_name());
        Glide.with(this).load(goodsBean.getGoods_pic())
                .apply(options)
                .into(goodsPayGoodsImg);
        goodsPayGoodsName.setText(goodsAttribute.getGoods_name());
        goodsPayGoodsAttribute.setText(goodsAttribute.getAttribute_value()
                .replace(";", " "));
        price_market = 0;
        if (SPUtil.getInstance(this).getIntByKey("spread_fg") == 0) {
            //普通会员
            price_market = goodsBean.getPrice_current();
        }
        if (SPUtil.getInstance(this).getIntByKey("spread_fg") == 1) {
            //推广会员
            price_market = goodsBean.getPrice_member();
        }
        goodsNumber = goodsAttribute.getShopCount();
        goodsPayGoodsPrice.setText("￥" + AmountUtil.changeF2Y(price_market));
        goodsPayNumber.setText("￥" + AmountUtil.changeF2Y(goodsNumber * price_market));
        priceMarket = goodsBean.getPrice_market();
        String y = AmountUtil.changeF2Y((priceMarket - price_market) * goodsNumber);
        goodsPayLess.setText("比直接购买省" + y + "元");
        goodsPayGoodsNumber.setText(String.valueOf(goodsNumber));
        //地图定位
        new RTPermission.Builder().permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        ).start(this, new OnPermissionResultListener() {
            @Override
            public void onAllGranted(String[] allPermissions) {
                AMapLocationUtil.getInstance().getCurrentLocation(getContext(), location -> {
                    if (location != null) {
                        //获取当前纬度
                        double lat = location.getLatitude();
                        //获取当前经度
                        double lng = location.getLongitude();
                        String baidu_pos = goodsBean.getBaidu_pos();
                        String[] split = baidu_pos.split(",");
                        //商品经度
                        double lat1 = Double.parseDouble(split[0]);
                        //商品纬度
                        double lng1 = Double.parseDouble(split[1]);
                        //计算当前位置和商品的距离
                        double points = LocationUtil.distanceOfTwoPoints(lat1, lng1, lat, lng);
                        String distance = "" + points + "km";
                        goodsPayShopDistance.setText("距您" + distance + "导航");
                    }

                });
            }

            @Override
            public void onDeined(String[] dinedPermissions) {

            }
        });
    }

    /**
     * 选择付款方式
     *
     * @param imageView
     *         当前选中的图标
     */
    private void checkedPayStyle(ImageView imageView) {
        goodsPayStyleWeChatChecked.setVisibility(View.INVISIBLE);
        goodsPayStyleRemainderChecked.setVisibility(View.INVISIBLE);
        goodsPayStyleAlPayChecked.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }

    /**
     * 消费方式
     *
     * @param imageView
     *         当前选中的图标
     */
    private void consumeStyle(ImageView imageView) {
        goodsConsumeStyleHomeChecked.setVisibility(View.INVISIBLE);
        goodsConsumeStyleShopChecked.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRetry() {

    }

    @Override
    protected void setListener() {
        super.setListener();
        goodsPayGoodsNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                goodsNumber = Integer.parseInt(s.toString());
                if (goodsNumber > 99999) {
                    goodsNumber = 99999;
                    goodsPayGoodsNumber.setText("99999");
                }
                if (goodsNumber < 1) {
                    goodsNumber = 1;
                    goodsPayGoodsNumber.setText("1");
                }
                goodsPayNumber.setText("￥" + AmountUtil.changeF2Y(goodsNumber * price_market));
                String y = AmountUtil.changeF2Y((priceMarket - price_market) * goodsNumber);
                goodsPayLess.setText("比直接购买省" + y + "元");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @OnClick({R.id.goods_pay_address_checked, R.id.goods_pay_add_address, R.id.goods_pay_shop_navigation,
            R.id.goods_pay_goods_delete, R.id.goods_pay_goods_add, R.id.goods_pay_look_coupon,
            R.id.goods_pay_style_we_chat, R.id.goods_pay_style_al_pay, R.id.goods_pay_style_remainder,
            R.id.goods_consume_style_home, R.id.goods_consume_style_shop, R.id.goods_pay_send})
    public void click(View view) {
        switch (view.getId()) {
            //选择收货地址
            case R.id.goods_pay_address_checked:

                break;
            //添加收货地址
            case R.id.goods_pay_add_address:

                break;
            //导航
            case R.id.goods_pay_shop_navigation:

                break;
            //数量减1
            case R.id.goods_pay_goods_delete:
                goodsNumber = goodsNumber - 2;
                goodsPayGoodsNumber.setText(String.valueOf(goodsNumber));

                break;
            //数量加一
            case R.id.goods_pay_goods_add:
                goodsNumber = goodsNumber + 2;
                goodsPayGoodsNumber.setText(String.valueOf(goodsNumber));

                break;
            //优惠券
            case R.id.goods_pay_look_coupon:
                Intent intent = new Intent(getContext(), CouponActivity.class);
                intent.putExtra("goods_id", goods_id);
                startActivity(intent);
                break;
            //微信支付
            case R.id.goods_pay_style_we_chat:
                checkedPayStyle(goodsPayStyleWeChatChecked);
                payChannel = 2;

                break;
            //支付宝支付
            case R.id.goods_pay_style_al_pay:
                checkedPayStyle(goodsPayStyleAlPayChecked);
                payChannel = 1;

                break;
            //余额支付
            case R.id.goods_pay_style_remainder:
                checkedPayStyle(goodsPayStyleRemainderChecked);
                payChannel = 0;

                break;
            //送货上门
            case R.id.goods_consume_style_home:
                consumeStyle(goodsConsumeStyleHomeChecked);
                getBy = 0;

                break;
            //到店支付
            case R.id.goods_consume_style_shop:
                consumeStyle(goodsConsumeStyleShopChecked);
                getBy = 1;

                break;
            //立即支付
            case R.id.goods_pay_send:
                if (addrId != -1) {
                    byUser = true;
                    presenter.saveOrder();
                }
                break;

        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
    }

    @Override
    public void getDefaultAddress(DefaultAddressBean bean) {
        statusLayoutManager.showContent();
        if (bean == null) {
            //没有默认地址
            goodsPayNotAddress.setVisibility(View.VISIBLE);
            goodsPayHaveAddress.setVisibility(View.GONE);
        } else {
            goodsPayNotAddress.setVisibility(View.GONE);
            goodsPayHaveAddress.setVisibility(View.VISIBLE);
            addrId = bean.getAddr_id();
            goodsPayAddressName.setText("收货人: " + bean.getContact_man());
            goodsPayAddressPhone.setText(bean.getContact_number());
            goodsPayAddressDetail.setText(bean.getAddr_detail());
        }

    }

    @Override
    public int getAddressId() {
        return addrId;
    }

    @Override
    public int getAccountChannel() {
        return payChannel;
    }

    @Override
    public int getGoodsIds() {
        return goods_id;
    }

    @Override
    public int getGoodsNums() {
        return goodsNumber;
    }

    @Override
    public String getGoodsPrices() {
        return price_market + "";
    }

    @Override
    public int getBy() {
        return getBy;
    }

    @Override
    public void success() {
        byUser = false;
    }

    /**
     * 接收消息
     *
     * @param bean1
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean1) {
        switch (bean1.getCode()) {
            //商品属性
            case CodeUtil.CHECKED_COUPON_CODE:
                couponId = bean1.getCouponId();
                goodsPayCoupon.setText("已减" + AmountUtil.changeF2Y(bean1.getCoupon_capital()));
                break;
        }
    }
}
