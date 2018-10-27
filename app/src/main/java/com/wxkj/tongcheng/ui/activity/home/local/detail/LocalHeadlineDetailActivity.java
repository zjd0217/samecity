package com.wxkj.tongcheng.ui.activity.home.local.detail;

import android.widget.ScrollView;
import android.widget.Toast;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.LocalShareFragmentDialog;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ShareFragmentDialog;

public class LocalHeadlineDetailActivity extends BaseActivity implements OnRetryListener {

    private ScrollView mScrollView;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager
                .newBuilder(this)
                .contentView(R.layout.activity_local_headline_detail)
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
        mScrollView = findViewById(R.id.mScrollView);
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.mIvShare).setOnClickListener(v -> new LocalShareFragmentDialog().show(getSupportFragmentManager(), "ShareFragmentDialog"));
        findViewById(R.id.mIvCollection).setOnClickListener(v -> Toast.makeText(getApplicationContext(), "收藏", Toast.LENGTH_SHORT).show());
        findViewById(R.id.mIvTop).setOnClickListener(v -> mScrollView.scrollTo(0, 0));
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
}
