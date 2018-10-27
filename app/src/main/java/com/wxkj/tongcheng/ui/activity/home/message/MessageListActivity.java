package com.wxkj.tongcheng.ui.activity.home.message;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.MessageListAdapter;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

public class MessageListActivity extends BaseActivity implements OnRefreshLoadMoreListener {

    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;


    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_message_list)
                .build();
        statusLayoutManager.showContent();
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
    protected void initData() {
        super.initData();
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
        mSmartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected void initView() {
        super.initView();
        mRecyclerView.setAdapter(new MessageListAdapter(getApplicationContext()));
    }

    @Override
    protected void setListener() {
        super.setListener();
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
