package com.wxkj.tongcheng.ui.activity.mine.merchant;

import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

import butterknife.BindView;

/**
 * @author: lujialei
 * @date: 2018/10/27
 * @describe:
 */


public class GoodsManageActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.layout_activity_goods_manage)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initView() {
        super.initView();
        viewPager.setAdapter(new GoodsManagePagerAdapter(getSupportFragmentManager()));
        tabLayout.setViewPager(viewPager);
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected String titleString() {
        return null;
    }
}
