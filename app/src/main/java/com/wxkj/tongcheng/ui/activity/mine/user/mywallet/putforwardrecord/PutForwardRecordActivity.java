package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.putforwardrecord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.PutForwardRecordAdapter;
import com.wxkj.tongcheng.bean.PutForwardRecordBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.stickyitem.StickyItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 提现明细 页面
 * Created by cheng on 2018/10/19.
 */

public class PutForwardRecordActivity extends MvpBaseActivity<RecordPresenter> implements RecordView, OnRetryListener, OnRefreshLoadMoreListener {
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;

    private int page=0;
    private boolean byUser=false;
    private List<PutForwardRecordBean> recordList=new ArrayList<>();
    private PutForwardRecordAdapter recordAdapter;

    @Override
    protected RecordPresenter initPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
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
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "提现记录";
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView=findViewById(R.id.recyclerView);
        refreshLayout=findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器
        setAdapter();
        presenter.getRecordList();
    }

    @Override
    protected void setListener() {
        super.setListener();
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void clickBack() {
//        super.clickBack();
        for (PutForwardRecordBean bean:recordList){
            Log.e("qwer",bean.getYm()+"..."+bean.getType());
        }
    }

    @Override
    public Context getcontext() {
        return this;
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

    private String ym="";
    @Override
    public void getRecordSuccess(List<PutForwardRecordBean> recordList) {  //获取记录成功
        Util.finishRefreshLoadMore(refreshLayout);
        if (null!=recordList&&recordList.size()!=0){
            if (page==0) {
                this.recordList.clear();
                ym="";
            }
            for ( PutForwardRecordBean recordBean :recordList){
                String ym = recordBean.getYm();
                if (!this.ym.equals(ym)){
                    PutForwardRecordBean bean=new PutForwardRecordBean();
                    bean.setType(11);
                    bean.setYm(ym);
                    this.recordList.add(bean);
                }
                this.ym=ym;
                recordBean.setType(10);
                this.recordList.add(recordBean);
            }
            recordAdapter.notifyDataSetChanged();
        }
        statusLayoutManager.showContent();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        byUser=true;
        presenter.getRecordList();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page=0;
        byUser=true;
        presenter.getRecordList();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new StickyItemDecoration());
        recordAdapter = new PutForwardRecordAdapter(this,recordList);
        recyclerView.setAdapter(recordAdapter);
    }
}
