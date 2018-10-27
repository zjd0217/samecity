package com.wxkj.tongcheng.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxkj.tongcheng.R;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/25
 * @describe:
 */


public class PriceDetailDesAdapter extends BaseQuickAdapter<Object,BaseViewHolder> {
    public PriceDetailDesAdapter(@Nullable List<Object> data) {
        super(R.layout.layout_item_price_detail_item_image,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
