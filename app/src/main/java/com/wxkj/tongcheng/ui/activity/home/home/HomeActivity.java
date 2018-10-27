package com.wxkj.tongcheng.ui.activity.home.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.demand.SelectTypeDemandActivity;
import com.wxkj.tongcheng.ui.activity.mine.login.login.LoginActicity;
import com.wxkj.tongcheng.ui.fragment.city.city.CityFragment;
import com.wxkj.tongcheng.ui.fragment.group.GroupFragment;
import com.wxkj.tongcheng.ui.fragment.home.HomeFragment;
import com.wxkj.tongcheng.ui.fragment.mine.merchant.MerchanMineFragment;
import com.wxkj.tongcheng.ui.fragment.mine.mine.MineFragment;
import com.wxkj.tongcheng.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 * Created by cheng on 2018/10/8.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.home_layout)
    RelativeLayout home_layout;
    @BindView(R.id.home_img_layout)
    LinearLayout home_img_layout;
    @BindView(R.id.city_layout)
    LinearLayout city_layout;
    @BindView(R.id.demand_layout)
    LinearLayout demand_layout;
    @BindView(R.id.group_layout)
    LinearLayout group_layout;
    @BindView(R.id.mine_layout)
    LinearLayout mine_layout;


    private Fragment preFragment;
    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.home_content_layout)
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
    protected void initView() {
        super.initView();
        initFragment();
    }

    @Override
    protected void setListener() {
        super.setListener();
        home_layout.setOnClickListener(this);
        city_layout.setOnClickListener(this);
        demand_layout.setOnClickListener(this);
        group_layout.setOnClickListener(this);
        mine_layout.setOnClickListener(this);
        home_layout.performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_layout:
                addContent(fragmentList.get(0));
                preFragment = fragmentList.get(0);
                city_layout.setSelected(false);
                demand_layout.setSelected(false);
                group_layout.setSelected(false);
                mine_layout.setSelected(false);
                home_img_layout.setVisibility(View.VISIBLE);
                break;
            case R.id.city_layout:
                mImmersionBar.statusBarColor(R.color.colorca).init();
                addContent(fragmentList.get(1));
                preFragment = fragmentList.get(1);
                tabSelected(city_layout);
                break;
            case R.id.demand_layout:
                startActivity(new Intent(getApplicationContext(), SelectTypeDemandActivity.class));
                break;
            case R.id.group_layout:
                mImmersionBar.statusBarColor(R.color.colorca).init();
                addContent(fragmentList.get(2));
                preFragment = fragmentList.get(2);
                tabSelected(group_layout);
                break;
            case R.id.mine_layout:
                //已登录
                if (SPUtil.getInstance(this).getLoginOrNot()) {
                    //商铺id是0  普通用户
//                       boolean isNormal = SPUtil.getInstance(this).getIntByKey("shop_id")==0;
                    boolean isNormal = false;//是否普通商户  测试用
                    if (isNormal) {
                        mImmersionBar.statusBarColor(R.color.colorff6).init();
                        addContent(fragmentList.get(3));
                        preFragment = fragmentList.get(3);
                    } else {  //商铺id不是0  是商家
                        addContent(fragmentList.get(4));
                        preFragment = fragmentList.get(4);
                    }
                    tabSelected(mine_layout);
                }else
                    startActivity(new Intent(this, LoginActicity.class));
                break;
        }
    }

    /**
     * 添加 fragment
     *
     * @param fragment
     */
    private void addContent(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            ft.hide(preFragment).add(R.id.frameLayout, fragment);

        } else {
            ft.hide(preFragment).show(fragment);
        }
        ft.commit();
    }

    /**
     * 底部按钮点击
     */
    private void tabSelected(LinearLayout linearLayout) {
        city_layout.setSelected(false);
        demand_layout.setSelected(false);
        group_layout.setSelected(false);
        mine_layout.setSelected(false);
        linearLayout.setSelected(true);
        home_img_layout.setVisibility(View.GONE);
    }

    /**
     * 初始化fragmnet
     */
    private void initFragment() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new CityFragment());
        fragmentList.add(new GroupFragment());
        fragmentList.add(new MineFragment());
        fragmentList.add(new MerchanMineFragment());
        preFragment = new Fragment();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return false;
    }

    private long mExitTime;

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            t("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
