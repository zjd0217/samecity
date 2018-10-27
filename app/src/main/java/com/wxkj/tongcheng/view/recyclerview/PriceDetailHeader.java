package com.wxkj.tongcheng.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.PriceDetailDesAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: lujialei
 * @date: 2018/10/26
 * @describe:
 */


public class PriceDetailHeader extends LinearLayout {
    @BindView(R.id.recycler_des)
    RecyclerView recyclerDes;

    public PriceDetailHeader(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_header_price_detail, this, true);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        recyclerDes.setLayoutManager(linearLayoutManager);
        ArrayList list = new ArrayList();
        for (int i = 0; i < 5; i++) {
            list.add(1);
        }
        PriceDetailDesAdapter priceDetailDesAdapter = new PriceDetailDesAdapter(list);
        recyclerDes.setAdapter(priceDetailDesAdapter);
    }
}
