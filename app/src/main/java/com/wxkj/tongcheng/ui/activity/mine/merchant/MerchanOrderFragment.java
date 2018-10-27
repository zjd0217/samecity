package com.wxkj.tongcheng.ui.activity.mine.merchant;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: lujialei
 * @date: 2018/10/26
 * @describe:
 */


public class MerchanOrderFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.layout_fragment_merchan_order)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initView() {
        super.initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList list = new ArrayList();
        for (int i = 0; i < 20; i++) {
            list.add(1);
        }
        MerchanOrderAdapter adapter = new MerchanOrderAdapter(list);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
