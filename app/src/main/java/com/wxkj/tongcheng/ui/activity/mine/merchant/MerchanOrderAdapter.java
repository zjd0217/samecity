package com.wxkj.tongcheng.ui.activity.mine.merchant;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxkj.tongcheng.R;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/26
 * @describe:
 */


public class MerchanOrderAdapter extends BaseQuickAdapter<Object,BaseViewHolder> {
    public MerchanOrderAdapter(@Nullable List<Object> data) {
        super(R.layout.layout_item_merchan_order,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

    }
}
