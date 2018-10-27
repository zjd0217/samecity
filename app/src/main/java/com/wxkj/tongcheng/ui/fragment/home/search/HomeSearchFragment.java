package com.wxkj.tongcheng.ui.fragment.home.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.GroupSearchAdapter;
import com.wxkj.tongcheng.adapter.NearServiceDemandAdapter;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.bean.HomeSearchHistoryBean;
import com.wxkj.tongcheng.bean.HotSearchBean;
import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.servicedemanddetail.ServiceDemandDeatailActivity;
import com.wxkj.tongcheng.ui.activity.home.search.HomeSearchActivity;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.flowlayout.FlowLayout;
import com.wxkj.tongcheng.view.flowlayout.TagAdapter;
import com.wxkj.tongcheng.view.flowlayout.TagFlowLayout;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchFragment extends MvpBaseFragment<HomeSearchPresenter> implements OnRefreshLoadMoreListener, HomeSearchView {

    private TagFlowLayout mLayoutHot, mLayoutHistory;
    private LinearLayout mLayoutTipHot;
    private ScrollView mScrollView;
    private FrameLayout mLayoutList;
    private TextView mTvClassify;
    private ImageView mIvClassifyIcon;
    private TextView mTvSort;
    private ImageView mIvSortIcon;
    private TextView mTvScreen;
    private ImageView mIvScreenIcon;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRecyclerView;

    private int orangeColor, grayColor;
    private DbManager db;
    private int type = 0;
    private int page = 0;
    private String keyWords = "";
    private boolean hasListData = false;
    private boolean byUser = false;

    //拼团适配器
    private GroupSearchAdapter mGroupAdapter;
    private List<GroupTitleBean.TeambuyBean> mGroupList;
    //附近相关适配器
    private NearServiceDemandAdapter mNearbyAdapter;
    private List<NearServiceBean> mNearList;

    //商品分类id
    private int typeId = 0;

    //商品排序
    private int sort = 0;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager
                .newBuilder(getContext())
                .contentView(R.layout.fragment_home_search)
                .build();
        statusLayoutManager.showContent();
        orangeColor = ContextCompat.getColor(getContext(), R.color.colorff5);
        grayColor = ContextCompat.getColor(getContext(), R.color.colorf44);

    }

    @Override
    protected void initData() {
        super.initData();
        mLayoutHot = findViewById(R.id.hot_search_layout);
        mLayoutTipHot = findViewById(R.id.hot_layout);
        mLayoutHistory = findViewById(R.id.history_search_layout);
        mScrollView = findViewById(R.id.mScrollView);
        mLayoutList = findViewById(R.id.mLayoutList);
        mTvClassify = findViewById(R.id.mTvClassify);
        mIvClassifyIcon = findViewById(R.id.mIvClassifyIcon);
        mTvSort = findViewById(R.id.mTvSort);
        mIvSortIcon = findViewById(R.id.mIvSortIcon);
        mTvScreen = findViewById(R.id.mTvScreen);
        mIvScreenIcon = findViewById(R.id.mIvScreenIcon);
        mSmartRefreshLayout = findViewById(R.id.mSmartRefreshLayout);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        db = x.getDb(new DbManager.DaoConfig());
    }

    @Override
    protected void initView() {
        super.initView();
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type", 0);
        }
        if (type == 0) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
            mGroupList = new ArrayList<>();
            mGroupAdapter = new GroupSearchAdapter(mGroupList, null, getContext());
            mRecyclerView.setAdapter(mGroupAdapter);
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mNearList = new ArrayList<>();
            mNearbyAdapter = new NearServiceDemandAdapter(getContext(), mNearList);
            mRecyclerView.setAdapter(mNearbyAdapter);
            mNearbyAdapter.setClickItemListener(position -> {
                Intent intent = new Intent(getContext(), ServiceDemandDeatailActivity.class);
                intent.putExtra("type", (type - 1));
                intent.putExtra("id", type == 1 ? mNearList.get(position).getService_provide_id() : mNearList.get(position).getService_need_id());
                startActivity(intent);
            });
        }
        //获取热门信息
        if (type == 0) {

        } else if (type == 1) {
            presenter.getNearbyHotData(0);
        } else if (type == 2) {
            presenter.getNearbyHotData(1);
        }
    }

    @Override
    protected HomeSearchPresenter initPresenter() {
        return new HomeSearchPresenter();
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.delete_history_img).setOnClickListener(v -> deleteLocalSearchHistory());
        findViewById(R.id.mLayoutClassify).setOnClickListener(v -> {
            mTvClassify.setTextColor(orangeColor);
            mIvClassifyIcon.setVisibility(View.VISIBLE);
            mTvSort.setTextColor(grayColor);
            mIvSortIcon.setVisibility(View.GONE);
            mSmartRefreshLayout.autoRefresh();
        });
        findViewById(R.id.mLayoutSort).setOnClickListener(v -> {
            mTvClassify.setTextColor(grayColor);
            mIvClassifyIcon.setVisibility(View.GONE);
            mTvSort.setTextColor(orangeColor);
            mIvSortIcon.setVisibility(View.VISIBLE);
            mSmartRefreshLayout.autoRefresh();
        });

        findViewById(R.id.mLayoutScreen).setOnClickListener(v -> {

        });
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        findViewById(R.id.mIvTop).setOnClickListener(v -> {
            if (mRecyclerView.getAdapter().getItemCount() > 0) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
    }

    public void search(String content) {
        byUser = true;
        page = 0;
        this.keyWords = content;
        if (type == 0) {
            presenter.getTeambuy(page, content);
        } else if (type == 1) {
            presenter.getServiceList(page, content);
        } else if (type == 2) {
            presenter.getDemandList(page, content);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getLocalSearchHistory();
    }


    private void getLocalSearchHistory() {
        if (hasListData) {
            mScrollView.setVisibility(View.GONE);
            mLayoutList.setVisibility(View.VISIBLE);
        } else {
            mScrollView.setVisibility(View.VISIBLE);
            mLayoutList.setVisibility(View.GONE);
            try {
                WhereBuilder builder = WhereBuilder.b("type", "=", type);
                List<HomeSearchHistoryBean> mList = db.selector(HomeSearchHistoryBean.class).where(builder).findAll();
                if (mList != null && mList.size() > 0) {
                    mLayoutHistory.setAdapter(new TagAdapter<HomeSearchHistoryBean>(mList) {
                        @Override
                        public View getView(FlowLayout parent, int position, HomeSearchHistoryBean citySearchBean) {
                            TextView text = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.city_search_item_layout, mLayoutHistory, false);
                            text.setText(citySearchBean.content);
                            return text;
                        }
                    });
                    mLayoutHistory.setOnTagClickListener((view, position, parent) -> {
                        HomeSearchHistoryBean bean = mList.get(position);
                        if (getActivity() instanceof HomeSearchActivity) {
                            ((HomeSearchActivity) getActivity()).setSearchContent(bean.content);
                            search(bean.content);
                        }
                        return true;
                    });
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteLocalSearchHistory() {
        try {
            WhereBuilder builder = WhereBuilder.b("type", "=", type);
            db.delete(HomeSearchHistoryBean.class, builder);
            mLayoutHistory.removeAllViews();
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        byUser = true;
        if (type == 0) {
            presenter.getTeambuy(page, keyWords);
        } else if (type == 1) {
            presenter.getServiceList(page, keyWords);
        } else if (type == 2) {
            presenter.getDemandList(page, keyWords);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        byUser = true;
        page = 0;
        if (type == 0) {
            presenter.getTeambuy(page, keyWords);
        } else if (type == 1) {
            presenter.getServiceList(page, keyWords);
        } else if (type == 2) {
            presenter.getDemandList(page, keyWords);
        }
    }


    @Override
    public void showErrorMsg(String msg) {
        showErrorMsg(byUser, msg);
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
    }

    @Override
    public void getGroupList(List<GroupTitleBean.TeambuyBean> mList) {
        hasListData = true;
        if (mLayoutList.getVisibility() != View.VISIBLE) {
            mScrollView.setVisibility(View.GONE);
            mLayoutList.setVisibility(View.VISIBLE);
        }
        if (mList != null && mList.size() > 0) {
            if (page == 0) {
                mGroupList.clear();
            }
            mGroupList.addAll(mList);
            mGroupAdapter.setList(mGroupList);
            page += 1;
        }
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
    }

    @Override
    public int getTypeId() {
        return typeId;
    }

    @Override
    public int getSort() {
        return sort;
    }

    @Override
    public Context getAppContent() {
        return getContext();
    }

    @Override
    public void getNearbyList(List<NearServiceBean> mList) {
        hasListData = true;
        if (mLayoutList.getVisibility() != View.VISIBLE) {
            mScrollView.setVisibility(View.GONE);
            mLayoutList.setVisibility(View.VISIBLE);
        }
        if (mList != null && mList.size() > 0) {
            if (page == 0) {
                mNearList.clear();
            }
            mNearList.addAll(mList);
            mNearbyAdapter.notifyDataSetChanged();
            page += 1;
        }
        Util.finishRefreshLoadMore(mSmartRefreshLayout);
    }

    @Override
    public void getNearbyHot(HotSearchBean searchBean) {
        if (searchBean == null || searchBean.getHotkey() == null || searchBean.getHotkey().size() < 1) {
            mLayoutHot.setVisibility(View.GONE);
            mLayoutTipHot.setVisibility(View.GONE);
        } else {
            mLayoutHot.setVisibility(View.VISIBLE);
            mLayoutTipHot.setVisibility(View.VISIBLE);
            List<HotSearchBean.HotkeyBean> mHotList = searchBean.getHotkey();
            mLayoutHot.setAdapter(new TagAdapter<HotSearchBean.HotkeyBean>(mHotList) {
                @Override
                public View getView(FlowLayout parent, int position, HotSearchBean.HotkeyBean citySearchBean) {
                    TextView text = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.city_search_item_layout, mLayoutHistory, false);
                    text.setText(citySearchBean.getKey_name());
                    return text;
                }
            });
            mLayoutHot.setOnTagClickListener((view, position, parent) -> {
                HotSearchBean.HotkeyBean bean = mHotList.get(position);
                if (getActivity() instanceof HomeSearchActivity) {
                    ((HomeSearchActivity) getActivity()).setSearchContent(bean.getKey_name());
                    search(bean.getKey_name());
                }
                return true;
            });
        }
    }
}
