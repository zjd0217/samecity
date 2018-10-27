package com.wxkj.tongcheng.ui.activity.demand.category;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.CategoryAdapter;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.demand.SelectTypeDemandEntity;
import com.wxkj.tongcheng.ui.activity.demand.describe.DescribeActivity;

public class CategoryActivity extends MvpBaseActivity<CategoryPresenter> implements OnRetryListener, CategoryView {

    private RecyclerView mRecyclerView;
    private boolean byUser = false;
    private SelectTypeDemandEntity mEntity;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_category)
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
    protected void initData() {
        super.initData();
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mEntity = (SelectTypeDemandEntity) getIntent().getSerializableExtra("entity");
    }

    @Override
    protected CategoryPresenter initPresenter() {
        return new CategoryPresenter();
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.mIvVoice).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), DescribeActivity.class);
            intent.putExtra(DescribeActivity.ACTION_KEY, DescribeActivity.ACTION_SIMPLIFY);
            startActivity(intent);
        });
    }

    @Override
    protected void initView() {
        super.initView();
        if (presenter != null) {
            presenter.getDemandSecondLevelType();
        }
    }

    @Override
    public void onRetry() {
        byUser = false;
        if (presenter != null) {
            presenter.getDemandSecondLevelType();
        }
    }

    @Override
    public Context getAppContent() {
        return this;
    }

    @Override
    public int getTypeId() {
        return mEntity.type_id;
    }

    @Override
    public void getCategoryData(CategoryEntity mCategoryEntity) {
        if (mCategoryEntity != null) {
            mRecyclerView.setAdapter(new CategoryAdapter(getApplicationContext(), mCategoryEntity.type, entity -> {
                Intent intent = new Intent(getApplicationContext(), DescribeActivity.class);
                intent.putExtra(DescribeActivity.ACTION_KEY, DescribeActivity.ACTION_COMMON);
                intent.putExtra("typeLevel1", mEntity);
                intent.putExtra("typeLevel2", entity);
                intent.putExtra("categoryEntity", mCategoryEntity);
                startActivity(intent);
            }));
        }
        statusLayoutManager.showContent();
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
    }
}
