package com.wxkj.tongcheng.ui.activity.group.goodsdetail.shop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.GroupSearchAdapter;
import com.wxkj.tongcheng.adapter.ShopDetailGoodsAdapter;
import com.wxkj.tongcheng.adapter.ShopPictureAdapter;
import com.wxkj.tongcheng.bean.ShopDetailGoodsBean;
import com.wxkj.tongcheng.bean.ShopDetailsBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.GroupGoodsDetailPresenter;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.recyclerview.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/18 0018
 * @Describe 店铺详情
 */
public class ShopDetailsActivity extends MvpBaseActivity<ShopDetailsPresenter> implements OnRetryListener,
        ShopDetailsView {
    private static final String TAG = "ShopDetailsActivity";

    @BindView(R.id.shop_details_title_name)
    TextView shopDetailsTitleName;
    @BindView(R.id.shop_details_pic)
    ImageView shopDetailsPic;
    @BindView(R.id.shop_details_logo)
    ImageView shopDetailsLogo;
    @BindView(R.id.shop_details_name)
    TextView shopDetailsName;
    @BindView(R.id.shop_details_sell_num)
    TextView shopDetailsSellNum;
    @BindView(R.id.shop_details_goods_num)
    TextView shopDetailsGoodsNum;
    @BindView(R.id.shop_details_grade)
    TextView shopDetailsGrade;
    @BindView(R.id.shop_detail_collection_img)
    ImageView shopDetailCollectionImg;
    @BindView(R.id.shop_details_address)
    TextView shopDetailsAddress;
    @BindView(R.id.shop_details_distance)
    TextView shopDetailsDistance;
    @BindView(R.id.shop_details_pic_list)
    RecyclerView shopDetailsPicList;
    @BindView(R.id.shop_details_tab_layout)
    TabLayout shopDetailsTabLayout;
    @BindView(R.id.shop_details_list)
    RecyclerView shopDetailsList;
    @BindView(R.id.shop_details_refreshLayout)
    SmartRefreshLayout shopDetailsRefreshLayout;

    /** 店铺id */
    private int shopId;
    private boolean byUser;
    /** 店铺相册适配器 */
    private ShopPictureAdapter pictureAdapter;
    private ShopDetailGoodsAdapter shopDetailGoodsAdapter;
    private String[] tabList = {"销量优先", "新品优先"};
    private int odby = 1;
    private int page;
    private List<ShopDetailGoodsBean> list = new ArrayList<>();
    private ShopDetailsBean bean;
    /** 店铺收藏的id */
    private int collectionId = -1;


    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_shop_details)
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
    protected ShopDetailsPresenter initPresenter() {
        return new ShopDetailsPresenter();
    }


    @Override
    protected void initData() {
        super.initData();
        shopId = getIntent().getIntExtra("shop_id", 0);
        presenter.getShopDetail();
        presenter.getShopGoodsList();
    }

    @Override
    protected void initView() {
        super.initView();
        //修改状态栏
        mImmersionBar.statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.colorfca).init();
        //设置图片适配器
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(LinearLayoutManager.HORIZONTAL);
        shopDetailsPicList.setLayoutManager(layout);
        pictureAdapter = new ShopPictureAdapter(this);
        shopDetailsPicList.setAdapter(pictureAdapter);
        //设置商品适配器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        shopDetailsList.setLayoutManager(gridLayoutManager);
        shopDetailsList.addItemDecoration(new SpacesItemDecoration(Util.dp2px(this, 5)));
        shopDetailGoodsAdapter = new ShopDetailGoodsAdapter(this);
        shopDetailsList.setAdapter(shopDetailGoodsAdapter);

        //设置tabLayout的值
        for (int i = 0; i < tabList.length; i++) {
            shopDetailsTabLayout.addTab(shopDetailsTabLayout.newTab());
            TabLayout.Tab tab = shopDetailsTabLayout.getTabAt(i);
            tab.setCustomView(R.layout.my_tab_layout);
            View view = tab.getCustomView();
            TextView textView = view.findViewById(R.id.tv_txt);
            textView.setText(tabList[i]);
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        //TabLayout设置监听
        shopDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < shopDetailsTabLayout.getTabCount(); i++) {
                    TabLayout.Tab tab1 = shopDetailsTabLayout.getTabAt(i);
                    if (tab == tab1) {
                        odby = i == 0 ? 1 : 0;
                        break;
                    }
                }
                page = 0;
                byUser = true;
                presenter.getShopGoodsList();
                Log.i(TAG, "onTabSelected: " + odby);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //上拉加载
        shopDetailsRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            byUser = false;
            presenter.getShopGoodsList();

        });
        //下拉刷新
        shopDetailsRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            byUser = true;
            presenter.getShopGoodsList();
        });
    }

    @Override
    public void onRetry() {
        page = 0;
        presenter.getShopDetail();
        presenter.getShopGoodsList();
    }

    @OnClick({R.id.shop_details_certificate, R.id.shop_detail_collection})
    public void click(View view) {
        switch (view.getId()) {
            //店铺证件
            case R.id.shop_details_certificate:
                if (bean != null) {
                    Intent intent = new Intent(this, ShopDocumentsActivity.class);
                    intent.putExtra("shop_certificate", bean.getShop().getShop_certificate());
                    intent.putExtra("shop_licence", bean.getShop().getShop_licence());
                    startActivity(intent);
                }
                break;
            //店铺收藏
            case R.id.shop_detail_collection:
                if (collectionId > 0) {
                    //取消收藏
                    presenter.cancelCollect(collectionId);
                } else {
                    //收藏
                    presenter.collect();
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
        Util.finishRefreshLoadMore(shopDetailsRefreshLayout);
        showErrorMsg(byUser, msg);
    }

    @Override
    public int getShopId() {
        return shopId;
    }

    @Override
    public int getOdby() {
        return odby;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void getShopDetails(ShopDetailsBean bean) {
        statusLayoutManager.showContent();
        this.bean = bean;
        ShopDetailsBean.ShopBean shop = this.bean.getShop();
        List<ShopDetailsBean.ShoppicBean> shoppic = bean.getShoppic();
        String pic = "";
        if (shoppic != null && shoppic.size() > 0) {
            pic = shoppic.get(0).getPic_path();
        }
        String shop_name = shop.getShop_name();
        shopDetailsTitleName.setText(shop_name);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading).error(R.drawable.load_failure);
        Glide.with(this).load(pic)
                .apply(requestOptions)
                .into(shopDetailsPic);
        Glide.with(this).load(shop.getShop_logo())
                .apply(requestOptions)
                .into(shopDetailsLogo);
        shopDetailsName.setText(shop_name);
        //商品销售件数
        SpannableString spanString = new SpannableString("已拼:" + shop.getSold_all() + "件");
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.colorff5));
        spanString.setSpan(span, 3, spanString.length() - 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        shopDetailsSellNum.setText(spanString);
        //商品总数
        spanString = new SpannableString("商品数量:" + shop.getGoods_all());
        spanString.setSpan(span, 5, spanString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        shopDetailsGoodsNum.setText(spanString);
        shopDetailsGrade.setText(shop.getShop_grade());
        shopDetailsAddress.setText(shop.getShop_addr());
        shopDetailsDistance.setText("距您" + "100m");
        if (shoppic != null && pictureAdapter != null) {
            pictureAdapter.setShoppic(shoppic);
        }
        collectionId = shop.getCollection_id();
        shopDetailCollectionImg.setImageResource(collectionId > 0 ?
                R.drawable.bottom_collected : R.drawable.bottom_collection);
    }

    @Override
    public void getShopGoodsList(List<ShopDetailGoodsBean> list) {
        statusLayoutManager.showContent();
        Util.finishRefreshLoadMore(shopDetailsRefreshLayout);
        if (page == 0) {
            this.list.clear();
            shopDetailsList.smoothScrollToPosition(0);
        }
        this.list.addAll(list);
        shopDetailGoodsAdapter.setList(this.list);
        shopDetailsList.invalidate();
    }

    @Override
    public void success() {
        presenter.getShopDetail();
    }


}
