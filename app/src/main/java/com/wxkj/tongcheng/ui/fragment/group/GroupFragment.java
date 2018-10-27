package com.wxkj.tongcheng.ui.fragment.group;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.GroupCommodityAdapter;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.statuslayout.HomeBaseFragment;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.group.search.GroupSearchActivity;
import com.wxkj.tongcheng.util.AMapLocationUtil;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.GlideImageLoader;
import com.wxkj.tongcheng.util.Util;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Liu haijun
 * @create 2018/10/8 0008
 * @Describe 拼团
 */
public class GroupFragment extends HomeBaseFragment<GroupPresenter> implements GroupView, OnBannerListener, OnRetryListener, OnLoadMoreListener, OnRefreshListener {

    /** 商品的列表 */
    @BindView(R.id.group_recyclerView_commodity)
    RecyclerView groupRecyclerViewCommodity;
    @BindView(R.id.group_refreshLayout)
    SmartRefreshLayout refreshLayout;

    private Context context;
    private boolean byUser = false;
    private GroupTitleBean groupTitleBean;
    private GroupCommodityAdapter adapter;
    private int page;

    @Override
    protected void initStatusLayout() {
        context = getActivity();
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .contentView(R.layout.group_fragment_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showLoading();

    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected void initData() {

        presenter.getBannerData(context, page);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initView() {
        //团购活动
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setSmoothScrollbarEnabled(true);
        groupRecyclerViewCommodity.setLayoutManager(layoutManager);
        adapter = new GroupCommodityAdapter(context, groupTitleBean, null);
        groupRecyclerViewCommodity.setAdapter(adapter);
        //地图定位
        new RTPermission.Builder().permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        ).start(getActivity(), new OnPermissionResultListener() {
            @Override
            public void onAllGranted(String[] allPermissions) {
                AMapLocationUtil.getInstance().getCurrentLocation(getContext(), location -> {
                    adapter.setLocation(location);
                });
            }

            @Override
            public void onDeined(String[] dinedPermissions) {

            }
        });


    }


    @Override
    protected GroupPresenter initPresenter() {
        return new GroupPresenter();
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    /***
     * banner点击事件
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {

    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
        Util.finishRefreshLoadMore(refreshLayout);
    }

    @Override
    public void getBannerSuccess(GroupTitleBean groupTitleBean) {
        Util.finishRefreshLoadMore(refreshLayout);
        statusLayoutManager.showContent();
        adapter.setGroupTitleBean(GroupFragment.this, groupTitleBean, page);
    }


    @Override
    protected boolean initEnventBus() {
        return true;
    }

    @Override
    public void onRetry() {
        if (null != presenter) {
            statusLayoutManager.showLoading();
            byUser = false;
            presenter.getBannerData(context, page);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (null != presenter) {
            page++;
            byUser = true;
            presenter.getBannerData(context, page);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (null != presenter) {
            page = 0;
            byUser = false;
            presenter.getBannerData(context, page);
        }
    }

    /**
     * 接收选择服务需求类型的消息
     *
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean) {
        switch (bean.getCode()) {
            case CodeUtil.CHOOSE_GROUP_SERVICE_TYPE:  //选择服务需求类型
                CityBannerBean.TypeBean typeBean = bean.getTypeBean();
                Intent intent = new Intent(context, GroupSearchActivity.class);
                intent.putExtra("type_id", typeBean.getType_id());
                context.startActivity(intent);
                break;
        }
    }

}
