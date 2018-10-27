package com.wxkj.tongcheng.ui.fragment.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.HomeClassifyAdapter;
import com.wxkj.tongcheng.adapter.HomeCollageAdapter;
import com.wxkj.tongcheng.adapter.HomeSecAdapter;
import com.wxkj.tongcheng.bean.HomeBannerClassifyBean;
import com.wxkj.tongcheng.statuslayout.HomeBaseFragment;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.demand.SelectTypeDemandActivity;
import com.wxkj.tongcheng.ui.activity.group.search.GroupSearchActivity;
import com.wxkj.tongcheng.ui.activity.h5.WebActivity;
import com.wxkj.tongcheng.ui.activity.home.local.LocalHeadlineActivity;
import com.wxkj.tongcheng.ui.activity.home.location.CityListActivity;
import com.wxkj.tongcheng.ui.activity.home.message.MessageListActivity;
import com.wxkj.tongcheng.ui.activity.home.qr.ScanQRCodeActivity;
import com.wxkj.tongcheng.ui.activity.home.search.HomeSearchActivity;
import com.wxkj.tongcheng.util.AMapLocationUtil;
import com.wxkj.tongcheng.util.GlideImageLoader;
import com.wxkj.tongcheng.util.ItemDecorationGridDivider;
import com.wxkj.tongcheng.util.Util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public class HomeFragment extends HomeBaseFragment<HomePresenter> implements HomeView, OnRefreshLoadMoreListener, OnRetryListener {

    @BindView(R.id.mTvCity)
    TextView mTvCity;
    private RecyclerView mRvClassify;
    private Banner mBanner;
    private Banner mSecBanner;
    private RecyclerView mRvCollage;
    private NestedScrollView mNestedScrollView;
    private LinearLayout mLayoutLocal;
    private ImageView mIvLocal;
    private TextView mTvLocalMessage;
    private LinearLayout mLayoutSecParent;
    private FrameLayout mLayoutSec1;
    private ImageView mIvSecPic1;
    private TextView mTvSecTitle1;
    private TextView mTvSecContent1;
    private FrameLayout mLayoutSec2;
    private ImageView mIvSecPic2;
    private TextView mTvSecTitle2;
    private TextView mTvSecContent2;
    private FrameLayout mLayoutSec3;
    private ImageView mIvSecPic3;
    private TextView mTvSecTitle3;
    private TextView mTvSecContent3;
    private RecyclerView mSecRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private int page = 0;
    private HomeCollageAdapter mCollageAdapter;
    private boolean byUser = false;

    @Override
    protected void initStatusLayout() {

        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.fragment_home_layout)
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
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        mRvClassify = findViewById(R.id.mRvClassify);
        mRvClassify.setLayoutManager(new GridLayoutManager(getContext(), 5));
        mRvClassify.setNestedScrollingEnabled(false);
        mBanner = findViewById(R.id.mBanner);
        mRvCollage = findViewById(R.id.mRvCollage);
        mSecBanner = findViewById(R.id.mSecBanner);
        mRvCollage.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvCollage.setNestedScrollingEnabled(false);
        mNestedScrollView = findViewById(R.id.mNestedScrollView);
        mLayoutLocal = findViewById(R.id.mLayoutLocal);
        mIvLocal = findViewById(R.id.mIvLocal);
        mTvLocalMessage = findViewById(R.id.mTvLocalMessage);
        mSmartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        mLayoutSecParent = findViewById(R.id.mLayoutSecParent);
        mLayoutSec1 = findViewById(R.id.mLayoutSec1);
        mIvSecPic1 = findViewById(R.id.mIvSecPic1);
        mTvSecTitle1 = findViewById(R.id.mTvSecTitle1);
        mTvSecContent1 = findViewById(R.id.mTvSecContent1);
        mLayoutSec2 = findViewById(R.id.mLayoutSec2);
        mIvSecPic2 = findViewById(R.id.mIvSecPic2);
        mTvSecTitle2 = findViewById(R.id.mTvSecTitle2);
        mTvSecContent2 = findViewById(R.id.mTvSecContent2);
        mLayoutSec3 = findViewById(R.id.mLayoutSec3);
        mIvSecPic3 = findViewById(R.id.mIvSecPic3);
        mTvSecTitle3 = findViewById(R.id.mTvSecTitle3);
        mTvSecContent3 = findViewById(R.id.mTvSecContent3);
        mSecRecyclerView = findViewById(R.id.mSecRecyclerView);
        mSecRecyclerView.setNestedScrollingEnabled(false);
        mSecRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mSecRecyclerView.addItemDecoration(new ItemDecorationGridDivider(getContext(), Util.dp2px(getAppContent(), 1F), ContextCompat.getColor(getAppContent(), R.color.coloreee)));
    }

    @Override
    protected void initView() {
        super.initView();
        presenter.getBannerData();
        presenter.getSeckillList(page);
        new RTPermission.Builder().permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        ).start(getActivity(), new OnPermissionResultListener() {
            @Override
            public void onAllGranted(String[] allPermissions) {
                AMapLocationUtil.getInstance().getCurrentLocation(getContext(), location -> {
                    mTvCity.setText(location.getCity());
                    mCollageAdapter.setAMapLocation(location);
                });
            }

            @Override
            public void onDeined(String[] dinedPermissions) {

            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.mIvCode).setOnClickListener(v ->
                new RTPermission.Builder().permissions("android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE").start(getActivity(), new OnPermissionResultListener() {
                    @Override
                    public void onAllGranted(String[] allPermissions) {
                        startActivity(new Intent(getContext(), ScanQRCodeActivity.class));
                    }

                    @Override
                    public void onDeined(String[] dinedPermissions) {

                    }
                }));
        findViewById(R.id.mTvSearch).setOnClickListener(v -> startActivity(new Intent(getContext(), HomeSearchActivity.class)));
        findViewById(R.id.mIvTop).setOnClickListener(v -> mNestedScrollView.scrollTo(0, 0));
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        findViewById(R.id.mIvMessage).setOnClickListener(v -> startActivity(new Intent(getContext(), MessageListActivity.class)));
        mTvCity.setOnClickListener(v -> startActivity(new Intent(getActivity(), CityListActivity.class)));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (presenter != null) {
            byUser = true;
            presenter.getSeckillList(page);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (presenter != null) {
            byUser = false;
            page = 0;
            presenter.getBannerData();
            presenter.getSeckillList(page);
        }
    }

    @Override
    public Context getAppContent() {
        return getContext();
    }

    @Override
    public void showBannerMsg(String msg) {
        showErrorMsg(byUser, msg);
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
    }

    @Override
    public void bannerClassify(HomeBannerClassifyBean mBean) {
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
        if (mBean != null) {
            setBannerData(mBean.banner);
            setClassifyData(mBean.type);
            setToutiaoData(mBean.toutiao);
            setSecBannerData(mBean.seckill);
            setSecKillData(mBean.item);
            statusLayoutManager.showContent();
        }
    }

    @Override
    public void showSeckillMsg(String msg) {
        showErrorMsg(byUser, msg);
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
    }

    @Override
    public void seckillList(List<HomeBannerClassifyBean.SeckillEntity> mList) {
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
        if (mList != null && mList.size() > 0) {
            if (mCollageAdapter == null) {
                mCollageAdapter = new HomeCollageAdapter(getContext(), mList, null);
                mRvCollage.setAdapter(mCollageAdapter);
            } else {
                mCollageAdapter.addItems(mList, page);
            }
            page += 1;
        }
    }

    private void setBannerData(List<HomeBannerClassifyBean.BannerEntity> bannerList) {
        if (bannerList == null || bannerList.size() < 1) {
            return;
        }
        List<String> picList = new ArrayList<>();
        for (HomeBannerClassifyBean.BannerEntity entity : bannerList) {
            if (!TextUtils.isEmpty(entity.ad_pic)) {
                picList.add(entity.ad_pic);
            }
        }
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(picList);
        mBanner.setBannerAnimation(Transformer.DepthPage);
        mBanner.isAutoPlay(true);
        mBanner.setDelayTime(2000);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerListener(position -> {
            HomeBannerClassifyBean.BannerEntity entity = bannerList.get(position);
            if (entity != null) {
                Intent intent = new Intent(getContext(), WebActivity.class);
                intent.putExtra(WebActivity.KEY_TITLE, entity.ad_title);
                intent.putExtra(WebActivity.KEY_URL, entity.ad_url);
                startActivity(intent);
            }
        });
        mBanner.start();
    }

    private void setSecBannerData(List<HomeBannerClassifyBean.SeckillEntity> bannerList) {
        if (bannerList == null || bannerList.size() < 1) {
            return;
        }
        List<String> picList = new ArrayList<>();
        for (HomeBannerClassifyBean.SeckillEntity entity : bannerList) {
            if (!TextUtils.isEmpty(entity.goods_pic)) {
                picList.add(entity.goods_pic);
            }
        }
        mSecBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mSecBanner.setIndicatorGravity(BannerConfig.NOT_INDICATOR);
        mSecBanner.setImageLoader(new GlideImageLoader());
        mSecBanner.setImages(picList);
        mSecBanner.setBannerAnimation(Transformer.DepthPage);
        mSecBanner.isAutoPlay(true);
        mSecBanner.setDelayTime(2000);
        mSecBanner.setIndicatorGravity(BannerConfig.CENTER);
        mSecBanner.setOnBannerListener(position -> {
            HomeBannerClassifyBean.SeckillEntity entity = bannerList.get(position);
            if (entity != null) {
                Intent intent = new Intent(getContext(), SelectTypeDemandActivity.class);
                startActivity(intent);
            }
        });
        mSecBanner.start();
    }

    private void setClassifyData(List<HomeBannerClassifyBean.ClassifyEntity> classifyList) {
        if (classifyList == null || classifyList.size() < 1) {
            return;
        }
        mRvClassify.setAdapter(new HomeClassifyAdapter(classifyList));
    }

    private void setToutiaoData(List<HomeBannerClassifyBean.ToutiaoEntity> toutiaoList) {
        if (toutiaoList == null || toutiaoList.size() < 1) {
            mLayoutLocal.setVisibility(View.GONE);
            return;
        }
        mLayoutLocal.setVisibility(View.VISIBLE);
        HomeBannerClassifyBean.ToutiaoEntity entity = toutiaoList.get(0);
        Glide.with(this).load(entity.info_pic).into(mIvLocal);
        mTvLocalMessage.setText(entity.info_title);
        mLayoutLocal.setOnClickListener(v -> startActivity(new Intent(getContext(), LocalHeadlineActivity.class)));
    }

    private void setSecKillData(List<HomeBannerClassifyBean.ItemEntity> itemList) {
        if (itemList == null || itemList.size() < 1) {
            mLayoutSecParent.setVisibility(View.GONE);
            return;
        }
        mLayoutSecParent.setVisibility(View.VISIBLE);
        if (itemList.size() > 0) {
            setSecKillTop3Data(mLayoutSec1, mIvSecPic1, mTvSecTitle1, mTvSecContent1, itemList.get(0));
        }
        if (itemList.size() > 1) {
            setSecKillTop3Data(mLayoutSec2, mIvSecPic2, mTvSecTitle2, mTvSecContent2, itemList.get(1));
        }
        if (itemList.size() > 2) {
            setSecKillTop3Data(mLayoutSec3, mIvSecPic3, mTvSecTitle3, mTvSecContent3, itemList.get(2));
        }
        if (itemList.size() > 3) {
            List<HomeBannerClassifyBean.ItemEntity> mList = new ArrayList<>();
            for (int i = 3; i < itemList.size(); i++) {
                mList.add(itemList.get(i));
            }
            mSecRecyclerView.setAdapter(new HomeSecAdapter(getActivity(), mList));
        }
    }

    private void setSecKillTop3Data(FrameLayout layout, ImageView mIvPic, TextView mTvTitle, TextView mTvContent, HomeBannerClassifyBean.ItemEntity entity) {
        layout.setVisibility(View.VISIBLE);
        layout.setEnabled(true);
        Glide.with(this).load(entity.item_pic).into(mIvPic);
        mTvTitle.setText(entity.item_title);
        mTvContent.setText(entity.item_content);
        layout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GroupSearchActivity.class);
            intent.putExtra("type_id", "");
            startActivity(intent);
        });
    }

    @Override
    public void onRetry() {
        if (presenter != null) {
            byUser = false;
            statusLayoutManager.showLoading();
            presenter.getBannerData();
            page = 0;
            presenter.getSeckillList(page);
        }
    }
}
