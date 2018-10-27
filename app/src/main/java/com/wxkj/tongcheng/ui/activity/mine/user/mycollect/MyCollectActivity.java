package com.wxkj.tongcheng.ui.activity.mine.user.mycollect;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.ViewPagerAdapter;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.fragment.mine.mycollect.GoodsFragment;
import com.wxkj.tongcheng.ui.fragment.mine.mycollect.ServiceDemandFragment;
import com.wxkj.tongcheng.ui.fragment.mine.mycollect.StoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏 页面
 * Created by cheng on 2018/10/20.
 */

public class MyCollectActivity extends BaseActivity {
    private SlidingTabLayout tab_layout;
    private ViewPager view_pager;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.my_collect_content_layout)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.my_collect_title_layout;
    }

    @Override
    protected String titleString() {
        return "我的收藏";
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void initView() {
        super.initView();
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        List<String> titleList=new ArrayList<>();
        List<Fragment> fragmentList=new ArrayList<>();
        titleList.add("供应");
        titleList.add("需求");
        titleList.add("商品");
        titleList.add("店铺");
        ServiceDemandFragment serviceFragment=new ServiceDemandFragment();
        Bundle serviceBundle=new Bundle();
        serviceBundle.putInt("type",2);
        serviceFragment.setArguments(serviceBundle);
        fragmentList.add(serviceFragment);

        ServiceDemandFragment demandFragment=new ServiceDemandFragment();
        Bundle demandBundle=new Bundle();
        demandBundle.putInt("type",1);
        demandFragment.setArguments(demandBundle);
        fragmentList.add(demandFragment);

        GoodsFragment goodsFragment = new GoodsFragment();
        Bundle goodsBundle=new Bundle();
        goodsBundle.putInt("type",3);
        goodsFragment.setArguments(goodsBundle);
        fragmentList.add(goodsFragment);

        StoreFragment storeFragment = new StoreFragment();
        Bundle storeBundle=new Bundle();
        storeBundle.putInt("type",4);
        storeFragment.setArguments(storeBundle);
        fragmentList.add(storeFragment);

        view_pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList));
        tab_layout.setViewPager(view_pager);
    }

    private void findview() {
        tab_layout=findViewById(R.id.tab_layout);
        view_pager=findViewById(R.id.view_pager);
    }
}
