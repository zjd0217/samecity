package com.wxkj.tongcheng.ui.activity.city.searchresult;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.NearServiceDemandAdapter;
import com.wxkj.tongcheng.bean.CityScreenBean;
import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.listener.IClickItemListener;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.servicedemanddetail.ServiceDemandDeatailActivity;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.MyPopupWindow;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 同城搜索结果 页面
 * Created by cheng on 2018/10/12.
 */

public class CitySearchResultActivity extends MvpBaseActivity<SearchResultPresenter> implements SearchResultView, View.OnClickListener, IClickItemListener, OnRefreshLoadMoreListener, OnRetryListener, TextView.OnEditorActionListener, TypePopupUtil.IPoupuClickListener, SortPopupUtil.IChooseSortListener, OnPermissionResultListener, ServiceOrDemandPopupUtil.IChooseServiceDemandListener {
    private SmartRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TextView type_text,search_edit,sort_text,service_demand_text;
    private LinearLayout screen_layout;

    private List<NearServiceBean> searchResultList=new ArrayList<>();
    private List<CityTypeBean> typeList=new ArrayList<>();
    private List<CityScreenBean> screenList=new ArrayList<>();
    private int page=0,oldPage=0,typeId=0,sort=0;
    private boolean byUser=false;
    private NearServiceDemandAdapter searchResultAdapter;
    private String attribute_data_id="",key="";
    private int type=0;

    @Override
    protected SearchResultPresenter initPresenter() {
        return new SearchResultPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.search_result_content_layout)
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
        if (null!=presenter){
            page=1;
            byUser=false;
            if (type==0) presenter.getServiceList(typeId);
            else if (type==1) presenter.getDemandList(typeId);
        }
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.search_result_title_layout;
    }

    @Override
    protected String titleString() {
        return "";
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
        type = getIntent().getIntExtra("type", 0);
        key = getIntent().getStringExtra("key");
    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器
        setAdapter();
        if (type==0) presenter.getServiceList(typeId);
        else if (type==1) presenter.getDemandList(typeId);
    }

    @Override
    protected void setListener() {
        super.setListener();
        type_text.setOnClickListener(this);
        screen_layout.setOnClickListener(this);
        sort_text.setOnClickListener(this);
        service_demand_text.setOnClickListener(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        search_edit.setOnEditorActionListener(this);
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser,msg);
        Util.finishRefreshLoadMore(refreshLayout);
    }

    @Override
    public int getType() {  //0服务  1需求
        return type;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String attribute_data_id() {
        return attribute_data_id;
    }

    @Override
    public int getSort() {
        return sort;
    }

    @Override
    public void getServiceListSuccess(List<NearServiceBean> searchResultList) {
        Util.finishRefreshLoadMore(refreshLayout);
        if (null!=searchResultList&&searchResultList.size()!=0){
            if (page==0) this.searchResultList.clear();
            this.searchResultList.addAll(searchResultList);
            searchResultAdapter.notifyDataSetChanged();
        }else{
            t("没有获取到相关信息");
            page=oldPage;
        }
        statusLayoutManager.showContent();
    }

    @Override
    public void getTypeListSuccess(List<CityTypeBean> typeList) {  //获取分类数据成功
        if (null!=typeList&&typeList.size()!=0){
            this.typeList.clear();
            this.typeList.addAll(typeList);
            showType();
        }else
            t("分类数据为空，查看失败");
    }

    @Override
    public void getScreenListSuccess(List<CityScreenBean> screenList) {  //获取筛选数据成功
        if (null!=screenList){
            this.screenList.clear();
            this.screenList.addAll(screenList);
            showScreenDialog();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.type_text:  //分类
                byUser=true;
                if (null==typeList||typeList.size()==0)
                    presenter.getTypeList();
                else
                    showType();
                break;
            case R.id.screen_layout:  //筛选
                byUser=true;
                if (screenList.size()==0){
                    presenter.getScreenList();
                }else
                    showScreenDialog();
                break;
            case R.id.sort_text:  //综合排序
                byUser=true;
                showSortPopup();
                break;
            case R.id.service_demand_text:  //供应
                byUser=true;
                showChooseServiceDemandPopup();
                break;
        }
    }

    private void showScreenDialog(){
        ScreenDialogFragment fragment = new ScreenDialogFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("list", (Serializable) screenList);
        fragment.setArguments(bundle);
        fragment.show(getSupportFragmentManager(),"ScreenDialogFragment");
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        searchResultAdapter = new NearServiceDemandAdapter(this,searchResultList);
        recyclerView.setAdapter(searchResultAdapter);
        searchResultAdapter.setClickItemListener(this);
    }

    /**
     * 搜索结果列表item 点击监听
     * @param position
     */
    @Override
    public void clickItemListener(int position) {
        int type = getType();
        Intent intent=new Intent(this, ServiceDemandDeatailActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("id",type==0?searchResultList.get(position).getService_provide_id():searchResultList.get(position).getService_need_id());
        startActivity(intent);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (null!=presenter){
            page=0;
            oldPage=page;
            byUser=true;
            if (type==0) presenter.getServiceList(typeId);
            else if (type==1) presenter.getDemandList(typeId);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (null!=presenter){
            page++;
            oldPage=page;
            byUser=true;
            if (type==0) presenter.getServiceList(typeId);
            else if (type==1) presenter.getDemandList(typeId);
        }
    }

    /**
     * 键盘搜索键 点击监听
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId== EditorInfo.IME_ACTION_SEARCH){
            // 先隐藏键盘
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(this.getCurrentFocus()
                            .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            String searchContext = search_edit.getText().toString().trim();
            if (!TextUtils.isEmpty(searchContext)){
                //搜索
                key=searchContext;
                refreshLayout.autoRefresh();
                return true;
            }
        }
        return false;
    }

    MyPopupWindow typePopup;
    private void showType() {
        typePopup = new TypePopupUtil().initPopup(this,typeList,this);
        typePopup.showAsDropDown(type_text);
        type_text.setTextColor(getResources().getColor(R.color.colorff5));
        typePopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                type_text.setTextColor(getResources().getColor(R.color.color444));
            }
        });
    }

    /**
     *分类列表点击
     * @param id
     */
    @Override
    public void popupClick(int id) {
        if (null!=typePopup) typePopup.dismiss();
        this.typeId=id;
        refreshLayout.autoRefresh();
    }

    /**
     * 显示排序popup弹窗
     */
    MyPopupWindow sortPopup;
    private void showSortPopup() {
        if (null==sortPopup)
            sortPopup = new SortPopupUtil().initPopup(this, this);
        sortPopup.showAsDropDown(sort_text);
        sort_text.setTextColor(getResources().getColor(R.color.colorff5));
        sortPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                sort_text.setTextColor(getResources().getColor(R.color.color444));
            }
        });
    }

    /**
     * 综合排序popup点击回调监听
     * @param sort
     */
    @Override
    public void chooseSort(int sort) {
        this.sort=sort;
        if (null!=sortPopup) sortPopup.dismiss();
        if (sort==1){  //获取定位权限
            new RTPermission.Builder()
                    .permissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .start(this,this);
        }else
            refreshLayout.autoRefresh();
    }

    @Override
    public void onAllGranted(String[] allPermissions) {
//        LocationUtil.getCurrentLocation();
    }

    @Override
    public void onDeined(String[] dinedPermissions) {

    }

    /**
     * 显示选择服务 还是需求的popup
     */
    MyPopupWindow serviceDemandPopup;
    private void showChooseServiceDemandPopup() {
        if (null==serviceDemandPopup)
            serviceDemandPopup = new ServiceOrDemandPopupUtil().initPopup(this, this);
        serviceDemandPopup.showAsDropDown(sort_text);
        service_demand_text.setTextColor(getResources().getColor(R.color.colorff5));
        serviceDemandPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                service_demand_text.setTextColor(getResources().getColor(R.color.color444));
            }
        });
    }

    @Override
    public void chooseServiceDemand(int service) {
        this.type=service;
        refreshLayout.autoRefresh();
        if (null!=serviceDemandPopup) serviceDemandPopup.dismiss();
    }

    /**
     * 接收消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CHOOSE_CITY_SCREEN:  //筛选
                attribute_data_id = bean.getName();
                refreshLayout.autoRefresh();
                break;
        }
    }

    private void findview() {
        refreshLayout=findViewById(R.id.refreshLayout);
        recyclerView=findViewById(R.id.recyclerView);
        type_text=findViewById(R.id.type_text);
        screen_layout=findViewById(R.id.screen_layout);
        search_edit=findViewById(R.id.search_edit);
        sort_text=findViewById(R.id.sort_text);
        service_demand_text=findViewById(R.id.service_demand_text);
    }
}
