package com.wxkj.tongcheng.ui.fragment.city.servicedemand;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.NearServiceDemandAdapter;
import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.listener.IClickItemListener;
import com.wxkj.tongcheng.statuslayout.MvpBaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.servicedemanddetail.ServiceDemandDeatailActivity;
import com.wxkj.tongcheng.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近服务 fragmnet
 * Created by cheng on 2018/10/8.
 */

public class NearServiceDemandFragment extends MvpBaseFragment<NearServicePresenter> implements NearServiceView, OnLoadMoreListener, IClickItemListener {
    private RecyclerView recyclerView;
    private SmartRefreshLayout refreshLayout;

    private List<NearServiceBean> nearServiceList=new ArrayList<>();
    private NearServiceDemandAdapter nearServiceDemandAdapter;
    private int page=0;
    private boolean byUser=false;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.near_service_demand_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initData() {
        super.initData();
        findView();
    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器
        setAdapter();
        presenter.getNearServiceData();
    }

    @Override
    protected void setListener() {
        super.setListener();
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadMoreListener(this);
    }

    @Override
    protected NearServicePresenter initPresenter() {
        return new NearServicePresenter();
    }

    @Override
    public int getType() {
        return getArguments().getInt("type",0);
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
    public void getServiceListSuccess(List<NearServiceBean> nearServiceList) {
        Util.finishRefreshLoadMore(refreshLayout);
        if (null!=nearServiceList&&nearServiceList.size()!=0){
            if (page==0) this.nearServiceList.clear();
            this.nearServiceList.addAll(nearServiceList);
            nearServiceDemandAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        nearServiceDemandAdapter = new NearServiceDemandAdapter(getContext(),nearServiceList);
        recyclerView.setAdapter(nearServiceDemandAdapter);
        nearServiceDemandAdapter.setClickItemListener(this);
    }

    /**
     * item点击
     * @param position
     */
    @Override
    public void clickItemListener(int position) {
        int type = getType();
        Intent intent=new Intent(getContext(), ServiceDemandDeatailActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("id",type==0?nearServiceList.get(position).getService_provide_id():nearServiceList.get(position).getService_need_id());
        startActivity(intent);
    }

    private void findView() {
        recyclerView=findViewById(R.id.recyclerView);
        refreshLayout=findViewById(R.id.refreshLayout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (null!=presenter){
            page++;
            byUser=true;
            presenter.getNearServiceData();
        }
    }
}
