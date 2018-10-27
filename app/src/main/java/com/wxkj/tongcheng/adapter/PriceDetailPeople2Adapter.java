package com.wxkj.tongcheng.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxkj.tongcheng.R;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/25
 * @describe:
 */


public class PriceDetailPeople2Adapter extends BaseQuickAdapter<Object,BaseViewHolder> {



    public PriceDetailPeople2Adapter(@Nullable List<Object> data) {
        super(R.layout.layout_item_price_detail_people,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }


}
