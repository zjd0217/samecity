package com.wxkj.tongcheng.ui.activity.home.local;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.LocalHeadlineAdapter;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.Util;

public class LocalHeadlineActivity extends BaseActivity implements OnRetryListener, OnRefreshLoadMoreListener {

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager
                .newBuilder(this)
                .contentView(R.layout.activity_local_headline)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initData() {
        super.initData();
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mSmartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        findViewById(R.id.mIvTop).setOnClickListener(v -> {
            if (mRecyclerView.getAdapter().getItemCount() > 0) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setAdapter(new LocalHeadlineAdapter());
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
    public void onRetry() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
    }
}
