package com.wxkj.tongcheng.ui.activity.home.location;

import android.widget.EditText;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

public class CityListActivity extends MvpBaseActivity<CityListPresenter> implements OnRetryListener, CityListView {

    private EditText mEtSearch;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_city_list)
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
        return R.layout.city_search_title_layout;
    }

    @Override
    protected String titleString() {
        return "";
    }

    @Override
    protected void initData() {
        super.initData();
        mEtSearch = findViewById(R.id.search_edit);
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.search_text).setOnClickListener(v -> {
            String content = mEtSearch.getText().toString().trim();

        });
    }

    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    public void onRetry() {

    }


    @Override
    protected CityListPresenter initPresenter() {
        return new CityListPresenter();
    }
}
