package com.wxkj.tongcheng.ui.fragment.home;

import android.content.Context;

import com.wxkj.tongcheng.bean.HomeBannerClassifyBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/8.
 */

public interface HomeView {

    Context getAppContent();

    void showBannerMsg(String msg);

    void bannerClassify(HomeBannerClassifyBean mBean);

    void showSeckillMsg(String msg);

    void seckillList(List<HomeBannerClassifyBean.SeckillEntity> mList);
}
