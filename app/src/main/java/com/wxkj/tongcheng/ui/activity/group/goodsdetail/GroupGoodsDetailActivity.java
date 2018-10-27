package com.wxkj.tongcheng.ui.activity.group.goodsdetail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
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
import com.wxkj.tongcheng.adapter.GroupDetailGoodsLike;
import com.wxkj.tongcheng.adapter.GroupGoodsDetailAdapter;
import com.wxkj.tongcheng.adapter.ShopDetailGoodsAdapter;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.GoodsAttribute;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.evaluate.AllEvaluateActivity;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.pay.GoodsPayDetailActivity;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.shop.ShopDetailsActivity;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.GoodsAttributeDialog;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.MoreCollageDialogFragment;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ServiceDialogFragment;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ShareFragmentDialog;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ShopCoupon;
import com.wxkj.tongcheng.util.AMapLocationUtil;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.GlideImageLoader;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.TimeUtils;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.disposables.DisposableContainer;

/**
 * @author Liu haijun
 * @create 2018/10/12 0012
 * @Describe 拼团中商品详情
 */
public class GroupGoodsDetailActivity extends MvpBaseActivity<GroupGoodsDetailPresenter>
        implements OnRetryListener, OnBannerListener, GroupGoodsDetailView {
    private static final String TAG = "GroupGoodsDetailActivit";

    @BindView(R.id.group_detail_banner)
    Banner groupDetailBanner;

    @BindView(R.id.group_detail_title)
    FrameLayout groupDetailTitle;

    @BindView(R.id.group_detail_back1)
    ImageView groupDetailBack1;
    @BindView(R.id.group_detail_share1)
    ImageView groupDetailShare1;
    @BindView(R.id.group_detail_title1)
    RelativeLayout groupDetailTitle1;

    @BindView(R.id.group_detail_back2)
    ImageView groupDetailBack2;
    @BindView(R.id.group_detail_share2)
    ImageView groupDetailShare2;
    @BindView(R.id.group_detail_title2)
    RelativeLayout groupDetailTitle2;

    @BindView(R.id.group_detail_price)
    TextView groupDetailPrice;
    @BindView(R.id.group_detail_base_price)
    TextView groupDetailBasePrice;
    @BindView(R.id.group_detail_vip_price)
    TextView groupDetailVipPrice;
    @BindView(R.id.group_detail_sum_num)
    TextView groupDetailSumNum;
    @BindView(R.id.group_detail_num)
    TextView groupDetailNum;
    @BindView(R.id.group_detail_name)
    TextView groupDetailName;
    @BindView(R.id.group_detail_discount)
    TextView groupDetailDiscount;
    @BindView(R.id.group_detail_discount_view)
    RelativeLayout groupDetailDiscountView;
    @BindView(R.id.group_detail_label)
    LinearLayout groupDetailLabel;
    @BindView(R.id.group_detail_label_view)
    RelativeLayout groupDetailLabelView;
    @BindView(R.id.group_detail_num_people)
    TextView groupDetailNumPeople;
    @BindView(R.id.group_detail_look_more)
    LinearLayout groupDetailLookMore;
    @BindView(R.id.group_detail_collage_list)
    LinearLayout groupDetailCollageList;
    @BindView(R.id.group_detail_evaluate_num)
    TextView groupDetailEvaluateNum;
    @BindView(R.id.group_detail_evaluate_more)
    LinearLayout groupDetailEvaluateMore;
    @BindView(R.id.group_detail_evaluate_good)
    TextView groupDetailEvaluateGood;
    @BindView(R.id.group_detail_evaluate_normal)
    TextView groupDetailEvaluateNormal;
    @BindView(R.id.group_detail_evaluate_bad)
    TextView groupDetailEvaluateBad;

    @BindView(R.id.group_detail_evaluate_content)
    LinearLayout groupDetailEvaluateContent;
    @BindView(R.id.group_detail_shop_img)
    ImageView groupDetailShopImg;
    @BindView(R.id.group_detail_shop_name)
    TextView groupDetailShopName;
    @BindView(R.id.group_detail_shop_num)
    TextView groupDetailShopNum;
    @BindView(R.id.group_detail_shop_time)
    TextView groupDetailShopTime;
    @BindView(R.id.group_detail_in_shop)
    LinearLayout groupDetailInShop;
    @BindView(R.id.group_detail_shop_address)
    TextView groupDetailShopAddress;
    @BindView(R.id.group_detail_shop_phone)
    ImageView groupDetailShopPhone;
    @BindView(R.id.group_detail_recommend_list)
    RecyclerView groupDetailRecommendList;
    @BindView(R.id.group_detail_price_expalin)
    TextView groupDetailPriceExpalin;
    @BindView(R.id.group_detail_love_list)
    MyGridView groupDetailLoveList;
    @BindView(R.id.group_detail_collection)
    LinearLayout groupDetailCollection;
    @BindView(R.id.group_detail_collection_icon)
    ImageView groupDetailCollectionIcon;
    @BindView(R.id.group_detail_customer)
    LinearLayout groupDetailCustomer;
    @BindView(R.id.group_detail_alone_buy)
    TextView groupDetailAloneBuy;
    @BindView(R.id.group_detail_collage_buy)
    TextView groupDetailCollageBuy;

    @BindView(R.id.group_detail_scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.group_detail_back3)
    ImageView groupDetailBack3;
    @BindView(R.id.group_detail_more)
    ImageView groupDetailMore;

    @BindView(R.id.group_detail_title3)
    LinearLayout groupDetailTitle3;
    @BindView(R.id.group_detail_title3_bottom)
    LinearLayout groupDetailTitle3Bottom;

    @BindView(R.id.group_detail_evaluate_shop)
    RelativeLayout groupDetailEvaluateShop;
    @BindView(R.id.group_detail_goods_detail)
    LinearLayout groupDetailGoodsDetail;
    @BindView(R.id.group_detail_love)
    LinearLayout groupDetailLove;

    @BindView(R.id.group_detail_baby)
    TextView groupDetailBaby;
    @BindView(R.id.group_detail_evaluate)
    TextView groupDetailEvaluate;
    @BindView(R.id.group_detail_details)
    TextView groupDetailDetails;
    @BindView(R.id.group_detail_recommend)
    TextView groupDetailRecommend;

    /** 上次点击的TextView */
    private TextView preTextView;

    private int goods_id, teambuy_id;

    private boolean byUser;
    /** 商家号码 */
    private String phone;
    private List<GroupGoodsDetailBean.GoodsRecommendBean> goods_recommend = new ArrayList<>();
    private GroupGoodsDetailAdapter adapter;
    private List<GroupGoodsDetailBean.GoodsLikeBean> goods_like = new ArrayList<>();
    private GroupDetailGoodsLike adapter1;
    private GroupGoodsDetailBean bean;
    /** rxjava 的容器 */
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int collection_id;


    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_group_goods_detail)
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
        return 0;
    }

    @Override
    protected String titleString() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        mImmersionBar.statusBarColor(R.color.colorfca).init();


        Intent intent = getIntent();
        goods_id = intent.getIntExtra("goods_id", 0);
        teambuy_id = intent.getIntExtra("teambuy_id", 0);

        setIcon(groupDetailEvaluate);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        groupDetailRecommendList.setLayoutManager(layout);
        adapter = new GroupGoodsDetailAdapter(goods_recommend, this);
        groupDetailRecommendList.setAdapter(adapter);

        adapter1 = new GroupDetailGoodsLike(this, goods_like);
        groupDetailLoveList.setAdapter(adapter1);


        //地图定位
        new RTPermission.Builder().permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        ).start(this, new OnPermissionResultListener() {
            @Override
            public void onAllGranted(String[] allPermissions) {
                AMapLocationUtil.getInstance().getCurrentLocation(getContext(), location -> {
                    adapter1.setLocation(location);
                });
            }

            @Override
            public void onDeined(String[] dinedPermissions) {

            }
        });


        presenter.getGroupGoodsDetail();
    }


    @Override
    protected void setListener() {
        super.setListener();
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener)
                (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                    int top = getViewToTop(groupDetailBasePrice);
                    if (top <= 120) {
                        groupDetailTitle1.setVisibility(View.GONE);
                        groupDetailCollection.setVisibility(View.VISIBLE);
                        groupDetailCustomer.setVisibility(View.VISIBLE);
                        groupDetailTitle2.setVisibility(View.VISIBLE);
                        mImmersionBar.statusBarColor(R.color.colorPrimary)
                                .statusBarDarkFont(false, 0.2f).init();
                    } else {
                        groupDetailCollection.setVisibility(View.GONE);
                        groupDetailCustomer.setVisibility(View.GONE);
                        groupDetailTitle1.setVisibility(View.VISIBLE);
                        groupDetailTitle2.setVisibility(View.GONE);
                        mImmersionBar.statusBarColor(R.color.colorfca)
                                .statusBarDarkFont(false, 0.2f).init();
                    }

                    int dp = getViewToTop(groupDetailEvaluateNum);
                    if (dp <= 150) {
                        //显示  宝贝、评价、详情和推荐
                        groupDetailTitle3.setVisibility(View.VISIBLE);
                        groupDetailTitle3Bottom.setVisibility(View.VISIBLE);
                        groupDetailTitle2.setVisibility(View.GONE);
                        mImmersionBar.statusBarColor(R.color.colorfca)
                                .statusBarDarkFont(false, 0.2f).init();

                        int i = groupDetailTitle3Bottom.getTop() + Util.dp2px(getContext(), 30);
                        int j = groupDetailEvaluateShop.getTop() - i;
                        int j1 = groupDetailGoodsDetail.getTop() - i;
                        int j2 = groupDetailLove.getTop() - i;
                        if (scrollY >= j && scrollY < j1) {
                            //评价
                            setIcon(groupDetailEvaluate);
                        }
                        if (scrollY >= j1 && scrollY < j2) {
                            //详情
                            setIcon(groupDetailDetails);
                        }
                        if (scrollY >= j2) {
                            //推荐
                            setIcon(groupDetailRecommend);
                        }

                    } else {
                        groupDetailTitle3.setVisibility(View.GONE);
                        groupDetailTitle3Bottom.setVisibility(View.GONE);
                        if (top <= 120) {
                            groupDetailTitle1.setVisibility(View.GONE);
                            groupDetailTitle2.setVisibility(View.VISIBLE);
                            mImmersionBar.statusBarColor(R.color.colorPrimary)
                                    .statusBarDarkFont(false, 0.2f).init();
                        }

                    }

                });
    }

    @Override
    protected GroupGoodsDetailPresenter initPresenter() {
        return new GroupGoodsDetailPresenter();
    }

    /**
     * 重新加载数据
     */
    @Override
    public void onRetry() {
        presenter.getGroupGoodsDetail();
    }


    /**
     * 初始化banner
     *
     * @param bannerList
     */
    private void initBanner(Banner banner, List<GroupGoodsDetailBean.GoodspicBean> bannerList) {
        if (null != banner) banner.releaseBanner();
        List<String> imageList = new ArrayList<>();
        if (bannerList.size() == 0) {  //banner是空
            return;
        }
        for (GroupGoodsDetailBean.GoodspicBean bean : bannerList) {
            imageList.add(bean.getPic_path());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //指示器居中
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置banner item点击事件
        banner.setOnBannerListener(this);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    /**
     * banner的点击事件
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {

    }

    @OnClick({R.id.group_detail_back1, R.id.group_detail_back2, R.id.group_detail_share1, R.id.group_detail_share2
            , R.id.group_detail_baby, R.id.group_detail_evaluate, R.id.group_detail_details, R.id.group_detail_recommend
            , R.id.group_detail_customer, R.id.group_detail_collection, R.id.group_detail_shop_phone, R.id.group_detail_more
            , R.id.group_detail_discount_view, R.id.group_detail_label, R.id.group_detail_look_more, R.id.group_detail_evaluate_more
            , R.id.group_detail_in_shop, R.id.group_detail_alone_buy, R.id.group_detail_collage_buy})

    public void click(View view) {
        switch (view.getId()) {
            //返回
            case R.id.group_detail_back1:
            case R.id.group_detail_back2:
            case R.id.group_detail_back3:

                finish();

                break;
            //分享
            case R.id.group_detail_share1:
            case R.id.group_detail_share2:
            case R.id.group_detail_more:
                new ShareFragmentDialog().show(getSupportFragmentManager(), "ShareFragmentDialog");

                break;

            //电话
            case R.id.group_detail_shop_phone:
                new RTPermission.Builder()
                        .permissions(Manifest.permission.CALL_PHONE)
                        .start(this, new OnPermissionResultListener() {
                            @Override
                            public void onAllGranted(String[] allPermissions) {
                                //直接拨打电话
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                Uri data = Uri.parse("tel:" + phone);
                                intent.setData(data);
                                startActivity(intent);
                            }

                            @Override
                            public void onDeined(String[] dinedPermissions) {
                                t("拨打电话权限获取失败，无法拨打电话");
                            }
                        });

                break;
            //宝贝
            case R.id.group_detail_baby:
                scrollView.scrollTo(0, 0);

                break;
            //评价
            case R.id.group_detail_evaluate:
                int i = groupDetailTitle3Bottom.getTop() + Util.dp2px(getContext(), 30);
                int top = groupDetailEvaluateShop.getTop() - i;
                scrollView.scrollTo(0, top);

                break;
            //详情
            case R.id.group_detail_details:
                i = groupDetailTitle3Bottom.getTop() + Util.dp2px(getContext(), 30);
                top = groupDetailGoodsDetail.getTop() - i;
                scrollView.scrollTo(0, top);

                break;
            //推荐
            case R.id.group_detail_recommend:
                i = groupDetailTitle3Bottom.getTop() + Util.dp2px(getContext(), 30);
                top = groupDetailLove.getTop() - i;
                scrollView.scrollTo(0, top);

                break;
            //客服
            case R.id.group_detail_customer:


                break;
            //收藏
            case R.id.group_detail_collection:
                boolean b = loginOrNot("收藏需要登录哦~");
                if (b && collection_id == 0) {
                    presenter.collectGoods();
                } else {
                    //取消收藏
                    presenter.cancelCollect(collection_id);
                }
                break;
            //优惠劵
            case R.id.group_detail_discount_view:
                b = loginOrNot("收藏需要登录哦~");
                if (b) {
                    ShopCoupon shopCoupon = new ShopCoupon();
                    Bundle bundle = new Bundle();
                    bundle.putInt("goods_id", goods_id);
                    shopCoupon.setArguments(bundle);
                    shopCoupon.show(getSupportFragmentManager(), "ShopCoupon");
                }
                break;
            //服务说明
            case R.id.group_detail_label:
                ServiceDialogFragment serviceDialogFragment = new ServiceDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("goods_id", goods_id);
                serviceDialogFragment.setArguments(bundle);
                serviceDialogFragment.show(getSupportFragmentManager(), "ServiceDialogFragment");

                break;
            //查看更多拼单
            case R.id.group_detail_look_more:
                MoreCollageDialogFragment moreCollageDialogFragment = new MoreCollageDialogFragment();
                bundle = new Bundle();
                bundle.putInt("goods_id", goods_id);
                moreCollageDialogFragment.setArguments(bundle);
                moreCollageDialogFragment.show(getSupportFragmentManager(), "moreCollageDialogFragment");

                break;
            //查看更多商品评价
            case R.id.group_detail_evaluate_more:
                Intent intent = new Intent(GroupGoodsDetailActivity.this, AllEvaluateActivity.class);
                intent.putExtra("goods", bean.getGoods());
                List<GroupGoodsDetailBean.TeamBuyBean> teambuys = bean.getTeambuys();
                if (teambuys != null && teambuys.size() > 0) {
                    intent.putExtra("teamBuy", teambuys.get(0));
                    intent.putExtra("size", teambuys.size());
                }
                startActivity(intent);

                break;
            //进店看看(查看店铺详情)
            case R.id.group_detail_in_shop:
                intent = new Intent(GroupGoodsDetailActivity.this, ShopDetailsActivity.class);
                intent.putExtra("shop_id", bean.getGoods().getShop_id());
                startActivity(intent);

                break;
            //单独购买
            case R.id.group_detail_alone_buy:
                b = loginOrNot("购买需要登录哦~");
                if (b) {
                    GoodsAttributeDialog goodsAttributeDialog = new GoodsAttributeDialog();
                    bundle = new Bundle();
                    bundle.putInt("classify_id", bean.getGoods().getClassify_id());
                    bundle.putString("goods_pic", bean.getGoods().getGoods_pic());
                    bundle.putInt("num_start", bean.getGoods().getNum_start());
                    bundle.putInt("num_span", bean.getGoods().getNum_span());
                    goodsAttributeDialog.setArguments(bundle);
                    goodsAttributeDialog.show(getSupportFragmentManager(), "goodsAttributeDialog1");
                }

                break;
            //拼团购买
            case R.id.group_detail_collage_buy:


                break;

        }

    }

    /**
     * 还原上次选中，设置本次选中
     *
     * @param textView
     */
    private void setIcon(TextView textView) {
        if (preTextView != null) {
            //还原上次
            preTextView.setTextColor(getResources().getColor(R.color.color444));
            preTextView.setCompoundDrawablesWithIntrinsicBounds(null,
                    null, null, null);
        }
        textView.setTextColor(getResources().getColor(R.color.colorff5));
        Drawable drawableLeft = getResources().getDrawable(
                R.drawable.icon_city);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
        preTextView = textView;
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    /**
     * 获取控件距离顶部的距离
     *
     * @param view
     *
     * @return
     */
    private int getViewToTop(View view) {
        Rect globeRect = new Rect();
        view.getGlobalVisibleRect(globeRect);
        return Util.px2dp(this, globeRect.top);
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
    public void getGoodsDetail(GroupGoodsDetailBean bean) {
        statusLayoutManager.showContent();
        initBanner(groupDetailBanner, bean.getGoodspic());
        this.bean = bean;

        GroupGoodsDetailBean.GoodsBean goods = bean.getGoods();
        groupDetailName.setText(goods.getGoods_name());
        groupDetailPrice.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_current()));
        groupDetailBasePrice.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_market()));
        groupDetailVipPrice.setText("会员价" + AmountUtil.changeF2Y(goods.getPrice_member()));
        groupDetailSumNum.setText("已拼" + goods.getNum_sold() + goods.getGoods_unit());
        groupDetailNum.setText(bean.getMan_have() + "人");
        String pay_return = goods.getPay_return();
        String str = "暂无";
        if (!TextUtils.isEmpty(pay_return)) {
            String[] split = pay_return.split(",");
            str = "满" + split[0] + "元减" + split[1] + "元";
        }
        groupDetailDiscount.setText(str);
        //添加商品标签
        String goods_labels = goods.getGoods_labels();
        if (TextUtils.isEmpty(goods_labels)) {
            addLabel("暂无");
        } else {
            groupDetailLabel.removeAllViews();
            String[] split = goods_labels.split(",");
            int i = 0;
            for (String s : split) {
                if (i > 4) {
                    continue;
                }
                addLabel(s);
                i++;
            }
        }
        List<GroupGoodsDetailBean.TeamBuyBean> teambuy = bean.getTeambuy();
        int man_num_have = 0;
        if (teambuy != null && teambuy.size() > 0) {
            man_num_have = teambuy.get(0).getMan_num_have();
        }
        groupDetailNumPeople.setText(man_num_have + "人");
        //拼单人员列表
        collagePersonList();

        groupDetailEvaluateNum.setText("商品评价（" + goods.getDiscuss_all() + "）");
        int discuss_good = goods.getDiscuss_good();
        groupDetailEvaluateGood.setText("好评（" + discuss_good + "）");
        int discuss_normal = goods.getDiscuss_normal();
        groupDetailEvaluateNormal.setText("中评（" + discuss_normal + "）");
        int discuss_bad = goods.getDiscuss_bad();
        groupDetailEvaluateBad.setText("差评（" + discuss_bad + "）");

        //添加商品评价内容
        List<GroupGoodsDetailBean.GoodsDiscussBean> goods_discuss = bean.getGoods_discuss();
        if (goods_discuss != null) {
            groupDetailEvaluateContent.removeAllViews();
            for (int i = 0; i < goods_discuss.size(); i++) {
                if (i >= 2) {
                    continue;
                }
                GroupGoodsDetailBean.GoodsDiscussBean goodsDiscussBean = goods_discuss.get(i);
                addEvaluateContent(goodsDiscussBean);
            }
        }
        RequestOptions options = new RequestOptions().transform(new CenterCrop());
        options.error(R.drawable.load_failure);
        options.placeholder(R.drawable.loading);
        Glide.with(this).load(goods.getShop_logo())
                .apply(options)
                .into(groupDetailShopImg);
        groupDetailShopName.setText(goods.getShop_name());
        groupDetailShopNum.setText("商品数量：" + goods.getGoods_all() + "   已拼" + goods.getSold_all() + goods.getGoods_unit());
        groupDetailShopTime.setText("" + goods.getTime_on_work());
        groupDetailShopAddress.setText(goods.getShop_addr());
        //联系电话
        phone = goods.getConnect_num();

        groupDetailPriceExpalin.setText(goods.getPrice_explain());
        //推荐商品列表数据
        goods_recommend = bean.getGoods_recommend();
        adapter.setList(goods_recommend);
        //猜你喜欢
        goods_like = bean.getGoods_like();
        if (goods_like != null) {
            adapter1.setGoods_like(goods_like);
        }

        List<GroupGoodsDetailBean.TeamBuyBean> teambuy1 = bean.getTeambuys();
        if (teambuy1 != null && teambuy1.size() > 0) {
            //拼团
            GroupGoodsDetailBean.TeamBuyBean teamBuyBean = teambuy1.get(0);
            groupDetailCollageBuy.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_current()) + "元\n发起"
                    + teamBuyBean.getMan_num_need() + "人拼单");

            int price_market = 0;
            if (SPUtil.getInstance(this).getIntByKey("spread_fg") == 0) {
                //普通会员
                price_market = goods.getPrice_current();
            }
            if (SPUtil.getInstance(this).getIntByKey("spread_fg") == 1) {
                //推广会员
                price_market = goods.getPrice_member();
            }
            groupDetailAloneBuy.setText("￥" + AmountUtil.changeF2Y(price_market) + "元\n单独购买");
        } else {
            groupDetailAloneBuy.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_current()) + "元\n当前价格");
            groupDetailCollageBuy.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_member()) + "元\n会员价");
        }
        //已经收藏
        collection_id = goods.getCollection_id();
        groupDetailCollectionIcon.setImageResource(collection_id > 0 ?
                R.drawable.bottom_collected : R.drawable.bottom_collection);

    }

    /**
     * 拼单人员列表
     */
    private void collagePersonList() {
        List<GroupGoodsDetailBean.TeamBuyBean> teambuy = bean.getTeambuys();
        if (teambuy != null) {
            groupDetailCollageList.removeAllViews();
            for (int i = 0; i < teambuy.size(); i++) {
                if (i >= 2) {
                    //只显示2条
                    continue;
                }
                GroupGoodsDetailBean.TeamBuyBean teamBuyBean = teambuy.get(i);
                addCollage(teamBuyBean);
            }
        }
        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> emitter.onNext(12))
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        collagePersonList();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 添加商品评论内容
     *
     * @param bean
     */
    private void addEvaluateContent(GroupGoodsDetailBean.GoodsDiscussBean bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_group_goods_detail_evaluate_content, null);
        ImageView head = view.findViewById(R.id.item_evaluate_content_img);
        RequestOptions options = new RequestOptions().transform(new CenterCrop());
        options.error(R.drawable.load_failure);
        options.placeholder(R.drawable.loading);
        options.circleCrop();
        Glide.with(this).load(bean.getHead_portrait())
                .apply(options)
                .into(head);
        TextView name = view.findViewById(R.id.item_evaluate_content_name);
        name.setText(bean.getUser_cname());
        TextView time = view.findViewById(R.id.item_evaluate_content_time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(bean.getTime_setup() * 1000L);
        String t1 = format.format(d1);
        time.setText(t1);
        TextView content = view.findViewById(R.id.item_evaluate_content_content);
        content.setText(bean.getDiscuss_content());
        TextView goodsName = view.findViewById(R.id.item_evaluate_content_label);
        goodsName.setText(bean.getGoods_name());
        groupDetailEvaluateContent.addView(view);
    }

    /**
     * 添加拼团数据
     *
     * @param teamBuyBean
     */
    private void addCollage(GroupGoodsDetailBean.TeamBuyBean teamBuyBean) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_group_goods_detail_collage, null);
        ImageView head = view.findViewById(R.id.item_group_goods_collage_img);
        RequestOptions options = new RequestOptions().transform(new CenterCrop());
        options.error(R.drawable.load_failure);
        options.placeholder(R.drawable.loading);
        options.circleCrop();
        Glide.with(this).load(teamBuyBean.getHead_portrait())
                .apply(options)
                .into(head);
        TextView name = view.findViewById(R.id.item_group_goods_collage_name);
        name.setText(teamBuyBean.getUser_cname());
        TextView num = view.findViewById(R.id.item_group_goods_collage_num);
        num.setText("" + (teamBuyBean.getMan_num_need() - teamBuyBean.getMan_num_have()));
        TextView time = view.findViewById(R.id.item_group_goods_collage_time);
        int time_end = teamBuyBean.getTeambuy_time_end();
        int l = (int) (System.currentTimeMillis() / 1000);
        time.setText("剩余" + TimeUtils.ddHHmmss(time_end - l));

        LinearLayout go = view.findViewById(R.id.item_group_goods_collage_go);
        go.setOnClickListener(v -> {
            // TODO: 2018/10/14 0014 去拼单 

        });
        groupDetailCollageList.addView(view);
    }


    /**
     * 添加商品标签
     *
     * @param s
     *         标签
     */
    private void addLabel(String s) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_group_detail_goods_label, null);
        TextView tv = view.findViewById(R.id.item_group_detail_label_str);
        tv.setText(s);
        groupDetailLabel.addView(view);

    }

    @Override
    public int getGoodsId() {
        return goods_id;
    }

    @Override
    public int getTeambuy_id() {
        return teambuy_id;
    }

    @Override
    public void success() {
        onRetry();
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();

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
            case CodeUtil.GOODS_ATTRIBUTE_CODE:
                GoodsAttribute goodsAttribute = bean1.getGoodsAttribute();
                Intent intent = new Intent(this, GoodsPayDetailActivity.class);
                intent.putExtra("goodsAttribute", goodsAttribute);
                intent.putExtra("goodsBean", bean.getGoods());
                startActivity(intent);
                finish();
                break;
        }
    }
}
