package com.wxkj.tongcheng.ui.activity.mine.user.mycoupon;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CouponBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.shop.ShopDetailsActivity;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.TimeUtils;
import com.wxkj.tongcheng.util.Util;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Liu haijun
 * @create 2018/10/24 0024
 * @Describe 优惠券
 */
public class CouponActivity extends MvpBaseActivity<CouponPresenter>
        implements OnRetryListener, CouponView {

    @BindView(R.id.coupon_list)
    RecyclerView couponList;
    @BindView(R.id.coupon_refreshLayout)
    SmartRefreshLayout couponRefreshLayout;

    /**
     * 判断是不是购买的时候选择优惠券
     * goods_id==-1表示是个人中心过来的
     */
    private int goods_id = -1;
    private boolean byUser;
    private int page = 0;
    /** 数据源 */
    private List<CouponBean> list = new ArrayList<>();
    /** 适配器 */
    private MyAdapter adapter;


    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_coupon)
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
        return "我的优惠券";
    }

    @Override
    protected void initData() {
        super.initData();
        goods_id = getIntent().getIntExtra("goods_id", -1);
        presenter.getCouponList();
    }

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        couponList.setLayoutManager(manager);
        adapter = new MyAdapter();
        couponList.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        //下拉刷新
        couponRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            byUser = false;
            presenter.getCouponList();

        });
        //上拉加载
        couponRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            byUser = true;
            presenter.getCouponList();
        });

    }

    @Override
    protected CouponPresenter initPresenter() {
        return new CouponPresenter();
    }


    @Override
    public void onRetry() {
        statusLayoutManager.showLoading();
        byUser = false;
        presenter.getCouponList();
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
        Util.finishRefreshLoadMore(couponRefreshLayout);
    }

    @Override
    public int getGoodsId() {
        return goods_id;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void getCouponList(List<CouponBean> couponBeanList) {
        if (page == 0) {
            list.clear();
            if (couponBeanList == null || couponBeanList.size() == 0) {
                statusLayoutManager.showEmptyData();
                return;
            }
        }
        list.addAll(couponBeanList);
        statusLayoutManager.showContent();
        Util.finishRefreshLoadMore(couponRefreshLayout);
        adapter.notifyDataSetChanged();

    }

    /**
     * 适配器
     */
    class MyAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_coupon, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            MyHolder vh = (MyHolder) holder;
            CouponBean bean = list.get(position);
            RequestOptions options = new RequestOptions().transform(new CenterCrop());
            options.error(R.drawable.load_failure);
            options.placeholder(R.drawable.loading);
            Glide.with(getcontext())
                    .load(bean.getCoupon_pic())
                    .apply(options)
                    .into(vh.itemCouponImg);
            vh.itemCouponName.setText(bean.getCoupon_name());
            vh.itemCouponPrice.setText("￥" + AmountUtil.changeF2Y(bean.getCoupon_capital()));
            vh.itemCouponDescribe.setText("满" +
                    AmountUtil.changeF2Y(bean.getCoupon_need_capital()) + "可用");
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String start = format.format(new Date(bean.getTime_valid_begin() * 1000));
            String end = format.format(new Date(bean.getTime_valid_end() * 1000));
            vh.itemCouponTime.setText(start + "-" + end);
            vh.itemCouponUse.setOnClickListener(v -> {
                if (goods_id == -1) {
                    //打开对应的店铺
                    Intent intent = new Intent(getcontext(), ShopDetailsActivity.class);
                    intent.putExtra("shop_id", bean.getShop_id());
                    startActivity(intent);
                } else {
                    //选择对应的id需要返回
                    EventBusBean busBean = new EventBusBean();
                    busBean.setCode(CodeUtil.CHECKED_COUPON_CODE);
                    busBean.setCouponId(bean.getUser_coupon_id());
                    busBean.setCoupon_capital(bean.getCoupon_capital());
                    EventBus.getDefault().post(busBean);
                    finish();
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.item_coupon_img)
            ImageView itemCouponImg;
            @BindView(R.id.item_coupon_name)
            TextView itemCouponName;
            @BindView(R.id.item_coupon_describe)
            TextView itemCouponDescribe;
            @BindView(R.id.item_coupon_time)
            TextView itemCouponTime;
            @BindView(R.id.item_coupon_price)
            TextView itemCouponPrice;
            @BindView(R.id.item_coupon_use)
            LinearLayout itemCouponUse;

            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

            }
        }
    }

}
