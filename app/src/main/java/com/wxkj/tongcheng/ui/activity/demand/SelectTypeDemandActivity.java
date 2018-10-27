package com.wxkj.tongcheng.ui.activity.demand;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.SelectTypeDemandAdapter;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.demand.voice.VoiceActivity;

import java.util.List;

public class SelectTypeDemandActivity extends MvpBaseActivity<SelectTypeDemandPresenter> implements OnRetryListener, SelectTypeDemandView {

    private RecyclerView mRecyclerView;

    @BindView(R.id.mTvVoice)
    TextView mTvVoice;

    private boolean byUser = false;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_select_type_demand)
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
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
        findViewById(R.id.mTvMyRelease).setOnClickListener(v -> {

        });
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
    }

    @Override
    protected void initView() {
        super.initView();
        presenter.getDemandTypeList();
    }

    @Override
    protected void setListener() {
        super.setListener();
        mTvVoice.setOnClickListener(v -> new RTPermission.Builder().permissions(Manifest.permission.RECORD_AUDIO).start(this, new OnPermissionResultListener() {
            @Override
            public void onAllGranted(String[] allPermissions) {
                startActivity(new Intent(getApplicationContext(), VoiceActivity.class));
            }

            @Override
            public void onDeined(String[] dinedPermissions) {

            }
        }));
    }

    @Override
    protected SelectTypeDemandPresenter initPresenter() {
        return new SelectTypeDemandPresenter();
    }

    @Override
    public void onRetry() {
        byUser = false;
        if (presenter != null) {
            statusLayoutManager.showLoading();
            presenter.getDemandTypeList();
        }
    }

    @Override
    public Context getAppContent() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
    }

    @Override
    public void getDemandTypeList(List<SelectTypeDemandEntity> mList) {
        byUser = true;
        if (mList != null && mList.size() > 0) {
            mRecyclerView.setAdapter(new SelectTypeDemandAdapter(getApplicationContext(), mList));
            statusLayoutManager.showContent();
        } else {
            statusLayoutManager.showEmptyData();
        }
    }
}
