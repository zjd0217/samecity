package com.wxkj.tongcheng.ui.activity.mine.user.setting.address;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.CompoundButton;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.ReceivingAddressAdapter;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.ReceivingAddressBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.user.setting.address.addoredit.AddOrEditAddressActivity;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.Util;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.v7.widget.ListPopupWindow.MATCH_PARENT;

/**
 * @author Liu haijun
 * @create 2018/10/19 0019
 * @Describe 收货地址
 */
public class ReceivingAddressActivity extends MvpBaseActivity<ReceivingAddressPresenter>
        implements ReceivingAddressView, OnRetryListener {


    @BindView(R.id.receiving_address_list)
    SwipeMenuRecyclerView receivingAddressList;

    private ReceivingAddressAdapter adapter;
    private List<ReceivingAddressBean> list;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_receiving_address)
                .emptyDataView(R.layout.address_empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "收货地址";
    }

    @Override
    protected ReceivingAddressPresenter initPresenter() {
        return new ReceivingAddressPresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getAddressList();
    }

    @Override
    protected void initView() {
        super.initView();
        //初始化侧滑菜单
        setSideslipMenu();
        //修改状态栏
        mImmersionBar.statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.colorfca).init();
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected void setListener() {
        super.setListener();
        // 设置菜单Item点击监听
        receivingAddressList.setSwipeMenuItemClickListener(menuBridge -> {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            //在menuBridge中我们可以得到侧滑的这一项item的position (menuBridge.getAdapterPosition())
            int position = menuBridge.getAdapterPosition();
            int addr_id = list.get(position).getAddr_id();
            presenter.deleteAdress(addr_id);

        });
        adapter = new ReceivingAddressAdapter(this);
        receivingAddressList.setLayoutManager(new LinearLayoutManager(this));
        receivingAddressList.setAdapter(adapter);
        //设置是否为默认地址
        adapter.setCheckedChangeListener((buttonView, isChecked) -> {
            int position = (int) buttonView.getTag();
            ReceivingAddressBean bean = list.get(position);
            bean.setDefault_fg(isChecked ? 1 : 0);
            presenter.saveOrUpdateAddress(bean);
        });
    }

    /**
     * 初始化item侧滑菜单
     */
    private void setSideslipMenu() {
        // 设置菜单创建器
        receivingAddressList.setSwipeMenuCreator((swipeLeftMenu, swipeRightMenu, viewType) -> {
            SwipeMenuItem deleteItem = new SwipeMenuItem(getcontext())
                    // 背景颜色
                    .setBackgroundColor(getResources().getColor(R.color.colorff5))
                    // 文字。
                    .setText("删除")
                    // 文字颜色。
                    .setTextColor(Color.WHITE)
                    // 文字大小。
                    .setTextSize(15)
                    // 宽
                    .setWidth(Util.dp2px(getcontext(), 75.5f))
                    //高（MATCH_PARENT意为Item多高侧滑菜单多高 （推荐使用））
                    .setHeight(MATCH_PARENT);
            // 添加一个按钮到右侧侧菜单
            swipeRightMenu.addMenuItem(deleteItem);

        });

    }

    @OnClick(R.id.receiving_address_add)
    public void click() {
        //添加新的收货地址
        Intent intent = new Intent(this, AddOrEditAddressActivity.class);
        startActivity(intent);
    }

    /**
     * 接收选择分享平台的的消息
     *
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean) {
        switch (bean.getCode()) {
            //刷新列表
            case CodeUtil.REFRESH_ADDRESS_LIST:
                onRetry();
                break;
        }
    }

    @Override
    public void onRetry() {
        statusLayoutManager.showLoading();
        presenter.getAddressList();
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(false, msg);
    }

    @Override
    public void getAddressList(List<ReceivingAddressBean> list) {
        statusLayoutManager.showContent();
        this.list = list;
        adapter.setList(this.list);
    }

    @Override
    public void success() {
        presenter.getAddressList();
    }
}
