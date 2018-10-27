package com.wxkj.tongcheng.ui.fragment.mine.merchant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.HomeBigProductAdapter;
import com.wxkj.tongcheng.bean.HomeBigProductBean;
import com.wxkj.tongcheng.statuslayout.HomeBaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.merchant.BigProductActivity;
import com.wxkj.tongcheng.ui.activity.mine.merchant.GoodsManageActivity;
import com.wxkj.tongcheng.ui.activity.mine.merchant.MerchanOrderActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MerchanMineFragment extends HomeBaseFragment<MerchanMinePresenter> {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;
    Unbinder unbinder1;
    @BindView(R.id.ll_goods_manage)
    LinearLayout llGoodsManage;
    private Context mContext;
    private HomeBigProductAdapter adapter;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.mine_fragment_layout_merchant)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        super.initView();
        mContext = getActivity();
        recyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        ArrayList list = new ArrayList();
        for (int i = 0; i < 6; i++) {
            list.add(new HomeBigProductBean(1));
        }
        adapter = new HomeBigProductAdapter(list);
        recyclerView.setAdapter(adapter);

        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), BigProductActivity.class));
            }
        });
        llGoodsManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), GoodsManageActivity.class));
            }
        });
    }

    @Override
    protected MerchanMinePresenter initPresenter() {
        return new MerchanMinePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_my_order)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), MerchanOrderActivity.class);
        startActivity(intent);
    }


}
