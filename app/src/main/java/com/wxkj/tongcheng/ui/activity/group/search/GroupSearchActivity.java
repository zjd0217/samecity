package com.wxkj.tongcheng.ui.activity.group.search;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.GroupSearchAdapter;
import com.wxkj.tongcheng.bean.CityScreenBean;
import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.searchresult.ScreenDialogFragment;
import com.wxkj.tongcheng.ui.activity.city.searchresult.SortPopupUtil;
import com.wxkj.tongcheng.ui.activity.city.searchresult.TypePopupUtil;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.GroupScreenDialogFragment;
import com.wxkj.tongcheng.util.AMapLocationUtil;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.MyPopupWindow;
import com.wxkj.tongcheng.view.recyclerview.SpacesItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/11 0011
 * @Describe 拼团的搜索
 */
public class GroupSearchActivity extends MvpBaseActivity<GroupSearchPresenter> implements
        OnRetryListener, GroupSearchView, OnRefreshListener, OnLoadMoreListener, SortPopupUtil.IChooseSortListener, OnPermissionResultListener, TypePopupUtil.IPoupuClickListener {

    private static final String TAG = "GroupSearchActivity";

    EditText search_edit;
    TextView search_text;
    @BindView(R.id.group_search_classification_text)
    TextView groupSearchClassificationText;
    @BindView(R.id.group_search_classification_icon)
    ImageView groupSearchClassificationIcon;
    @BindView(R.id.group_search_sort_text)
    TextView groupSearchSortText;
    @BindView(R.id.group_search_sort_icon)
    ImageView groupSearchSortIcon;
    @BindView(R.id.group_search_screen_text)
    TextView groupSearchScreenText;
    @BindView(R.id.group_search_screen_icon)
    ImageView groupSearchScreenIcon;

    @BindView(R.id.group_search_list)
    RecyclerView groupSearchList;
    @BindView(R.id.group_search_refreshLayout)
    SmartRefreshLayout groupSearchRefreshLayout;
    @BindView(R.id.group_to_top)
    ImageView groupToTop;

    private boolean byUser = false;
    private int page = 0;
    private String search_key = "";
    private List<GroupTitleBean.TeambuyBean> list = new ArrayList<>();
    private GroupSearchAdapter adapter;
    private int typeId = 0;
    private int sort = 0;
    private int goods_tp = -1;
    private String man_num_need = "";
    private List<CityTypeBean> typeList = new ArrayList<>();

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_group_search)
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
        fingview();
    }

    @Override
    protected void initView() {
        super.initView();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        groupSearchList.setLayoutManager(gridLayoutManager);
        adapter = new GroupSearchAdapter(list, null, this);
        groupSearchList.addItemDecoration(new SpacesItemDecoration(Util.dp2px(this, 5)));
        groupSearchList.setAdapter(adapter);

        //地图定位
        new RTPermission.Builder().permissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        ).start(this, new OnPermissionResultListener() {
            @Override
            public void onAllGranted(String[] allPermissions) {
                AMapLocationUtil.getInstance().getCurrentLocation(getContext(), location -> {
                    adapter.setLocation(location);
                });
            }

            @Override
            public void onDeined(String[] dinedPermissions) {

            }
        });


        //获取点击的typeid
        Intent intent = getIntent();
        typeId = intent.getIntExtra("type_id", 0);
        search_key = intent.getStringExtra("search_key");
        goods_tp = intent.getIntExtra("goods_tp", -1);

        presenter.getTeambuy(page, search_key);

    }

    private void fingview() {
        search_edit = findViewById(R.id.search_edit);
        search_text = findViewById(R.id.search_text);

        groupSearchRefreshLayout.setOnRefreshListener(this);
        groupSearchRefreshLayout.setOnLoadMoreListener(this);
        //点击搜索
        search_text.setOnClickListener(v -> {
            page = 0;
            statusLayoutManager.showLoading();
            search_key = search_edit.getText().toString();
            presenter.getTeambuy(page, search_key);
        });

    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    public void onRetry() {
        if (null != presenter) {
            statusLayoutManager.showLoading();
            byUser = false;
            presenter.getTeambuy(page, search_key);
        }
    }

    @Override
    protected GroupSearchPresenter initPresenter() {

        return new GroupSearchPresenter();
    }


    @OnClick({R.id.group_search_classification_view,
            R.id.group_search_sort_view, R.id.group_search_screen_view})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.group_search_classification_view:
                //分类
                goneAndShowIcon(groupSearchClassificationIcon);
                textColor(groupSearchClassificationText);
                if (typeList.size() == 0) {
                    presenter.getTypeList();
                } else {
                    showType();
                }

                break;
            case R.id.group_search_sort_view:
                //综合排序
                goneAndShowIcon(groupSearchSortIcon);
                textColor(groupSearchSortText);
                byUser = true;
                showSortPopup();

                break;
            case R.id.group_search_screen_view:
                //筛选
                showScreenDialog();
                break;
        }

    }

    private void showScreenDialog() {
        GroupScreenDialogFragment groupScreenDialogFragment = new GroupScreenDialogFragment();
        groupScreenDialogFragment.show(getSupportFragmentManager(),
                "groupScreenDialogFragment");
    }


    /**
     * 接收消息
     *
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean) {
        switch (bean.getCode()) {
            case CodeUtil.GROUP_GOODS_SCREEN_CODE:  //筛选
                man_num_need = bean.getMan_num_need();
                onRetry();
                break;
        }
    }

    /**
     * 显示排序popup弹窗
     */
    MyPopupWindow sortPopup;

    private void showSortPopup() {
        if (null == sortPopup)
            sortPopup = new SortPopupUtil().initPopup(this, this);
        sortPopup.showAsDropDown(groupSearchSortText);
        sortPopup.setOnDismissListener(() -> groupSearchSortText.setTextColor(getResources().getColor(R.color.color444)));
    }

    /**
     * 综合排序popup点击回调监听
     *
     * @param sort
     */
    @Override
    public void chooseSort(int sort) {
        this.sort = sort;
        if (null != sortPopup) sortPopup.dismiss();
        if (sort == 1) {  //获取定位权限
            new RTPermission.Builder()
                    .permissions(Manifest.permission.ACCESS_COARSE_LOCATION)
                    .start(this, this);
        } else {
            groupSearchRefreshLayout.autoRefresh();
        }
    }

    /**
     * 显示分类
     */
    MyPopupWindow typePopup;

    private void showType() {
        typePopup = new TypePopupUtil().initPopup(this, typeList, this);
        typePopup.showAsDropDown(groupSearchClassificationText);
        typePopup.setOnDismissListener(() -> {
            groupSearchClassificationText.setTextColor(getResources().getColor(R.color.color444));
            groupSearchClassificationIcon.setVisibility(View.INVISIBLE);
        });

    }

    /**
     * 分类点击之后的回调
     *
     * @param id
     */
    @Override
    public void popupClick(int id) {
        if (null != typePopup) {
            typePopup.dismiss();
        }
        this.typeId = id;
        groupSearchRefreshLayout.autoRefresh();
    }


    @Override
    public void onAllGranted(String[] allPermissions) {

    }

    @Override
    public void onDeined(String[] dinedPermissions) {

    }


    /**
     * 修改选中字体颜色和还原未选中颜色
     *
     * @param textView
     */
    private void textColor(TextView textView) {
        int color = getResources().getColor(R.color.colorf44);
        groupSearchClassificationText.setTextColor(color);
        groupSearchSortText.setTextColor(color);
        int color1 = getResources().getColor(R.color.colorff5);
        textView.setTextColor(color1);
    }

    /**
     * 隐藏和显示选中图标
     *
     * @param imageView
     */
    private void goneAndShowIcon(ImageView imageView) {
        groupSearchClassificationIcon.setVisibility(View.INVISIBLE);
        groupSearchSortIcon.setVisibility(View.INVISIBLE);
        imageView.setVisibility(View.VISIBLE);
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
        Util.finishRefreshLoadMore(groupSearchRefreshLayout);
    }

    /**
     * 获取到数据
     *
     * @param list
     */
    @Override
    public void getGroupTeambuyList(List<GroupTitleBean.TeambuyBean> list) {
        Util.finishRefreshLoadMore(groupSearchRefreshLayout);
        if (page == 0) {
            this.list.clear();
        }
        this.list.addAll(list);
        adapter.setList(this.list);
        Log.i(TAG, "getGroupTeambuyList: " + list);
        statusLayoutManager.showContent();
    }

    /**
     * @return 商品分类ID
     */
    @Override
    public int getTypeId() {

        return typeId;
    }

    @Override
    public int getSort() {
        return sort;
    }

    @Override
    public int getGoodsTp() {
        return goods_tp;
    }

    @Override
    public String getManNumNeed() {
        return man_num_need;
    }

    @Override
    public void getTypeListSuccess(List<CityTypeBean> list) {
        if (null != list && list.size() != 0) {
            this.typeList.clear();
            this.typeList.addAll(list);
            showType();
        } else
            t("分类数据为空，查看失败");
    }


    /**
     * 下拉刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (null != presenter) {
            page = 0;
            byUser = false;
            presenter.getTeambuy(page, search_key);
        }
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (null != presenter) {
            page++;
            byUser = true;
            presenter.getTeambuy(page, search_key);
        }
    }


}
