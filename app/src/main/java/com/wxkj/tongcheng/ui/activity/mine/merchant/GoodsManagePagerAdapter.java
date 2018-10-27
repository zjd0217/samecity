package com.wxkj.tongcheng.ui.activity.mine.merchant;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author: lujialei
 * @date: 2018/10/27
 * @describe:
 */


public class GoodsManagePagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list;
    private ArrayList<String> titleList;

    public GoodsManagePagerAdapter(FragmentManager fm) {
        super(fm);
        list = new ArrayList<>();
        list.add(new MerchanOrderFragment());
        list.add(new MerchanOrderFragment());
        titleList = new ArrayList<>();
        titleList.add("已上架");
        titleList.add("未上架");
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
