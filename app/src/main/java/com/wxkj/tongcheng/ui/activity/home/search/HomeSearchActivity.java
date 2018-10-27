package com.wxkj.tongcheng.ui.activity.home.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.EditText;
import com.flyco.tablayout.SlidingTabLayout;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.ViewPagerAdapter;
import com.wxkj.tongcheng.bean.HomeSearchHistoryBean;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.fragment.home.search.HomeSearchFragment;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchActivity extends BaseActivity {

    private EditText mEtContent;
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private DbManager db;
    private List<Fragment> fragmentList;
    private HomeSearchFragment mCurrentFragment;

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
    protected void initData() {
        super.initData();
        mEtContent = findViewById(R.id.search_edit);
        mSlidingTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.viewPager);
        db = x.getDb(new DbManager.DaoConfig());
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.search_text).setOnClickListener(v -> {
            String content = mEtContent.getText().toString().trim();
            addSearchHistory(content);
            mCurrentFragment.search(content);
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (fragmentList != null) {
                    mCurrentFragment = (HomeSearchFragment) fragmentList.get(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setSearchContent(String content) {
        mEtContent.setText(content);
        mEtContent.setSelection(content.length());
    }

    @Override
    protected int setTitleBacColor() {
        return getResources().getColor(R.color.colorf9);
    }

    @Override
    protected void initView() {
        super.initView();
        setTabAdapter();
    }

    private void setTabAdapter() {
        List<String> titleList = new ArrayList<>();
        titleList.add("拼团");
        titleList.add("附近服务");
        titleList.add("附近需求");
        fragmentList = new ArrayList<>();
        mCurrentFragment = getSearchFragment(0);
        fragmentList.add(mCurrentFragment);
        fragmentList.add(getSearchFragment(1));
        fragmentList.add(getSearchFragment(2));
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    private HomeSearchFragment getSearchFragment(int type) {
        HomeSearchFragment fragment = new HomeSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void addSearchHistory(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        List<HomeSearchHistoryBean> mList = null;
        try {
            mList = db.findAll(HomeSearchHistoryBean.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        boolean isAdd = true;
        if (mList != null) {
            for (HomeSearchHistoryBean bean : mList) {
                if (TextUtils.equals(bean.content, content)) {
                    isAdd = false;
                    break;
                }
            }
        }
        if (isAdd) {
            HomeSearchHistoryBean bean = new HomeSearchHistoryBean();
            bean.type = mViewPager.getCurrentItem();
            bean.content = content;
            try {
                db.save(bean);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }
}
