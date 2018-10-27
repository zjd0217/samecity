package com.wxkj.tongcheng.ui.fragment.mine.mycollect;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.CollectGoodsAdapter;
import com.wxkj.tongcheng.bean.CollectGoodsBean;
import com.wxkj.tongcheng.bean.CollectServiceBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseFragment;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏商品
 * Created by cheng on 2018/10/20.
 */

public class GoodsFragment extends MvpBaseFragment<CollectPresenter> implements CollectView, OnRetryListener {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private int page=0;
    private boolean byUser=false;
    private List<CollectGoodsBean> collectList=new ArrayList<>();
    private CollectGoodsAdapter goodsAdapter;

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
    protected void initView() {
        super.initView();
        //设置适配器
        setAdapter();
        presenter.getCollectGoodsList();
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
        showErrorMsg(byUser,msg);
        Util.finishRefreshLoadMore(refreshLayout);
    }

    @Override
    public void getServiceCollectListSuccess(List<CollectServiceBean> collectList) {

    }

    @Override
    public void getGoodsCollectListSuccess(List<CollectGoodsBean> collectList) {  //获取收藏商品成功
        Util.finishRefreshLoadMore(refreshLayout);
        if (page==0&&collectList.size()==0){
            statusLayoutManager.showEmptyData(0,"暂无此类收藏");
        }else {
            if (page==0) this.collectList.clear();
            this.collectList.addAll(collectList);
            goodsAdapter.notifyDataSetChanged();
            statusLayoutManager.showContent();
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        goodsAdapter = new CollectGoodsAdapter(getContext(),collectList);
        recyclerView.setAdapter(goodsAdapter);
    }

    private void findview() {
        refreshLayout=findViewById(R.id.refreshLayout);
        recyclerView=findViewById(R.id.recyclerView);
    }
}
