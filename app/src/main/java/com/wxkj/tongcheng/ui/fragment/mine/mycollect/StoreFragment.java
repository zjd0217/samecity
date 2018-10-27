package com.wxkj.tongcheng.ui.fragment.mine.mycollect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CollectGoodsBean;
import com.wxkj.tongcheng.bean.CollectServiceBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseFragment;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

import java.util.List;

/**
 * 收藏店铺
 * Created by cheng on 2018/10/20.
 */

public class StoreFragment extends MvpBaseFragment<CollectPresenter> implements CollectView, OnRetryListener {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int page=0;
    private boolean byUser=false;

    @Override
    protected CollectPresenter initPresenter() {
        return new CollectPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.recyclerview_content_layout)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    public void onRetry() {

    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    public Context getcontext() {
        return getContext();
    }

    @Override
    public int getType() {
        return getArguments().getInt("type");
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void getServiceCollectListSuccess(List<CollectServiceBean> collectList) {  //获取收藏列表成功

    }

    @Override
    public void getGoodsCollectListSuccess(List<CollectGoodsBean> collectList) {

    }

    private void findview() {
        refreshLayout=findViewById(R.id.refreshLayout);
        recyclerView=findViewById(R.id.recyclerView);
    }
}
