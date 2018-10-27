package com.wxkj.tongcheng.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 不含标题的 viewpager适配器
 * Created by cheng on 2018/10/10.
 */

public class NoTitleViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public NoTitleViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
