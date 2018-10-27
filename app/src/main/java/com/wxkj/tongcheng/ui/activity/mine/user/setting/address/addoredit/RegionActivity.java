package com.wxkj.tongcheng.ui.activity.mine.user.setting.address.addoredit;

import android.app.Activity;
import android.os.Bundle;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

/**
 * @author Liu haijun
 * @create 2018/10/20 0020
 * @Describe 行政区域的选择页面
 */
public class RegionActivity extends BaseActivity implements OnRetryListener {

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_region)
                .emptyDataView(R.layout.address_empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
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
    public void onRetry() {

    }
}
