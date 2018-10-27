package com.wxkj.tongcheng.ui.activity.mine.merchant;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wxkj.tongcheng.ui.fragment.home.HomeFragment;

import java.util.ArrayList;

/**
 * @author: lujialei
 * @date: 2018/10/26
 * @describe:
 */


public class MerchanOrderPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list;
    private ArrayList<String> titleList;

    public MerchanOrderPagerAdapter(FragmentManager fm) {
        super(fm);
        list = new ArrayList<>();
        list.add(new MerchanOrderFragment());
        list.add(new MerchanOrderFragment());
        list.add(new MerchanOrderFragment());
        list.add(new MerchanOrderFragment());
        list.add(new MerchanOrderFragment());
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("待支付");
        titleList.add("待发货");
        titleList.add("待核销");
        titleList.add("待评价");

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
