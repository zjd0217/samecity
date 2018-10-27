package com.wxkj.tongcheng.ui.activity.mine.merchant;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.PriceDetailDesAdapter;
import com.wxkj.tongcheng.adapter.PriceDetailPeople2Adapter;
import com.wxkj.tongcheng.adapter.PriceDetailPeopleAdapter;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.fragment.mine.merchant.FullyLinearLayoutManager;
import com.wxkj.tongcheng.view.recyclerview.PriceDetailHeader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: lujialei
 * @date: 2018/10/24
 * @describe:
 */


public class PriceDetailActivity extends BaseActivity {
    @BindView(R.id.recycler_people)
    RecyclerView recyclerPeople;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.layout_activity_price_detail)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initView() {
        ArrayList list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(1);
        }
        recyclerPeople.setLayoutManager(new FullyLinearLayoutManager(getBaseContext()));
        PriceDetailPeople2Adapter priceDetailPeopleAdapter = new PriceDetailPeople2Adapter(list);
        priceDetailPeopleAdapter.addHeaderView(new PriceDetailHeader(getBaseContext()));
        recyclerPeople.setHasFixedSize(true);
        recyclerPeople.setNestedScrollingEnabled(false);
        recyclerPeople.setAdapter(priceDetailPeopleAdapter);
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_merchan_title_layout;
    }

    @Override
    protected String titleString() {
        return "报价详情";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
