package com.wxkj.tongcheng.ui.fragment.city.city;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.move.widget.XIndicator;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.NoTitleViewPagerAdapter;
import com.wxkj.tongcheng.adapter.ViewPagerAdapter;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.HomeBaseFragment;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.search.CitySearchActivity;
import com.wxkj.tongcheng.ui.activity.city.searchresult.CitySearchResultActivity;
import com.wxkj.tongcheng.ui.fragment.city.servicedemand.NearServiceDemandFragment;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 同城 fragmnet
 * Created by cheng on 2018/10/8.
 */

public class CityFragment extends HomeBaseFragment<CityPresenter> implements CityView, OnRetryListener, OnBannerListener, View.OnClickListener {
    private SlidingTabLayout tab_layout;
    private ViewPager viewPager,type_vp;
    private LinearLayout search_layout;
    private XIndicator indicator;
    private Banner banner;

    private List<String> titleList=new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<Fragment> serviceTypeFragmentList = new ArrayList<>();
    private boolean byUser=false;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.city_fragment_layout)
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
            statusLayoutManager.showLoading();
            byUser=false;
            presenter.getBannerData();
        }
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected CityPresenter initPresenter() {
        return new CityPresenter();
    }

    @Override
    protected int setTitleBacColor() {
        return getResources().getColor(R.color.color4d9);
    }

    @Override
    protected boolean initEnventBus() {
        return true;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        finView();
    }

    @Override
    protected void initView() {
        super.initView();
        initFragment();
        setAdapter();
        //获取banner
        presenter.getBannerData();
    }

    @Override
    protected void setListener() {
        super.setListener();
        search_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_layout:  //去搜索
                startActivity(new Intent(getContext(), CitySearchActivity.class));
                break;
        }
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser,msg);
    }

    @Override
    public void getBannerSuccess(CityBannerBean cityBanner) {  //获取banner成功
        //初始化banner
        initBanner(cityBanner.getBanner());
        //初始化服务类型fragment
        initServiceType(cityBanner.getType());
        statusLayoutManager.showContent();
    }

    /**
     * 初始化服务类型
     * @param type
     */
    private void initServiceType(List<CityBannerBean.TypeBean> type) {
        if (null!=type&&type.size()!=0){
            type_vp.removeAllViewsInLayout();
            int typeListSize = getTypeListSize(type);
            indicator.setIndicatorCount(typeListSize);
            serviceTypeFragmentList.clear();
            for (int i = 0; i < typeListSize; i++) {
                List<CityBannerBean.TypeBean> list=new ArrayList<>();
                ServiceTypeFragment fragment = new ServiceTypeFragment();
                Bundle bundle=new Bundle();
                if (i==typeListSize-1)
                    list.addAll(type.subList(i*10,type.size()));
                if (i<typeListSize-1)
                    list.addAll(type.subList(i*10,i*10+10));
                bundle.putSerializable("list", (Serializable) list);
                bundle.putInt("code",CodeUtil.CHOOSE_CITY_SERVICE_TYPE);
                fragment.setArguments(bundle);
                serviceTypeFragmentList.add(fragment);
            }
            type_vp.setAdapter(new NoTitleViewPagerAdapter(getChildFragmentManager(),serviceTypeFragmentList));
            indicator.setUpViewPager(type_vp);
        }
    }

    private int getTypeListSize(List<CityBannerBean.TypeBean> list){
        int size = list.size();
        int i = size / 10;
        if (size%10==0)
            return i;
        else
            return i+1;
    }

    /**
     * 初始化banner
     * @param bannerList
     */
    private void initBanner(List<CityBannerBean.BannerBean> bannerList) {
        if (null!=banner) banner.releaseBanner();
        List<String> imageList=new ArrayList<>();
        if (bannerList.size()==0){  //banner是空
            return;
        }
        for (CityBannerBean.BannerBean bean:bannerList){
            imageList.add(bean.getAd_pic());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //指示器居中
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置banner item点击事件
        banner.setOnBannerListener(this);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    @Override
    public void OnBannerClick(int position) {

    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        //viewpager适配器
        viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(),fragmentList,titleList));
        tab_layout.setViewPager(viewPager);
    }

    private void initFragment() {
        titleList.add("附近服务");
        titleList.add("附近需求");
        NearServiceDemandFragment serviceFragment = new NearServiceDemandFragment();
        Bundle serviceBundle=new Bundle();
        serviceBundle.putInt("type",0);
        serviceFragment.setArguments(serviceBundle);
        fragmentList.add(serviceFragment);

        NearServiceDemandFragment demandFragment = new NearServiceDemandFragment();
        Bundle demandBundle=new Bundle();
        demandBundle.putInt("type",1);
        demandFragment.setArguments(demandBundle);
        fragmentList.add(demandFragment);
    }

    /**
     * 接收选择服务需求类型的消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CHOOSE_CITY_SERVICE_TYPE:  //选择服务需求类型
                CityBannerBean.TypeBean typeBean = bean.getTypeBean();
                Intent intent = new Intent(getContext(), CitySearchResultActivity.class);
                intent.putExtra("key",typeBean.getType_name());
                intent.putExtra("type",typeBean.getTp());
                startActivity(intent);
                break;
        }
    }

    private void finView() {
        tab_layout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewPager);
        banner=findViewById(R.id.banner);
        type_vp=findViewById(R.id.type_vp);
        indicator=findViewById(R.id.indicator);
        search_layout=findViewById(R.id.search_layout);
    }
}
