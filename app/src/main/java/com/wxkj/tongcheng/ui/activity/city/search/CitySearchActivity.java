package com.wxkj.tongcheng.ui.activity.city.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.ViewPagerAdapter;
import com.wxkj.tongcheng.bean.CitySearchBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.searchresult.CitySearchResultActivity;
import com.wxkj.tongcheng.ui.fragment.city.search.SearchTypeFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 同城搜索 页面
 * Created by cheng on 2018/10/10.
 */

public class CitySearchActivity extends BaseActivity implements View.OnClickListener {
    private EditText search_edit;
    private TextView search_text;
    private SlidingTabLayout tab_layout;
    private ViewPager viewPager;
    private DbManager db;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.city_search_content_layout)
                .build();
        statusLayoutManager.showContent();
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
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected int setTitleBacColor() {
        return getResources().getColor(R.color.colorf9);
    }

    @Override
    protected void initData() {
        super.initData();
        fingview();
        db = x.getDb(new DbManager.DaoConfig());
    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器
        setAdapter();
    }

    @Override
    protected void setListener() {
        super.setListener();
        search_text.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        search(search_edit.getText().toString().trim());
    }

    /**
     * 搜索
     * @param content
     */
    private void search(String content) {
        try {
            if (TextUtils.isEmpty(content)) return;

            List<CitySearchBean> all = db.findAll(CitySearchBean.class);
            boolean tag=false;
            if (null != all) {
                for (CitySearchBean bean:all){
                    if (bean.getContent().equals(content)){
                        tag=true;
                        break;
                    }
                }
            }
            if (!tag){
                CitySearchBean bean = new CitySearchBean();
                bean.setType(viewPager.getCurrentItem());
                bean.setContent(content);
                db.save(bean);
            }

            Intent intent = new Intent(this, CitySearchResultActivity.class);
            intent.putExtra("key",content);
            intent.putExtra("type",viewPager.getCurrentItem());
            startActivity(intent);
        }catch (Exception e){

        }
    }

    private void setAdapter() {
        List<String> titleList=new ArrayList<>();
        titleList.add("附近服务");
        titleList.add("附近需求");

        List<Fragment> fragmentList = new ArrayList<>();
        SearchTypeFragment serviceFragment=new SearchTypeFragment();
        Bundle serviceBundle=new Bundle();
        serviceBundle.putInt("type",0);
        serviceFragment.setArguments(serviceBundle);
        fragmentList.add(serviceFragment);

        SearchTypeFragment demandFragment=new SearchTypeFragment();
        Bundle demandBundle=new Bundle();
        demandBundle.putInt("type",1);
        demandFragment.setArguments(demandBundle);
        fragmentList.add(demandFragment);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,titleList));
        tab_layout.setViewPager(viewPager);
    }

    /**
     * 接收消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CITY_HOT_HISTORY_SEARCH_ITEM:  //点击了 热门搜索 历史搜索 item
                search(bean.getName());
                break;
        }
    }

    private void fingview() {
        search_edit=findViewById(R.id.search_edit);
        search_text=findViewById(R.id.search_text);
        tab_layout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.viewPager);
    }
}
