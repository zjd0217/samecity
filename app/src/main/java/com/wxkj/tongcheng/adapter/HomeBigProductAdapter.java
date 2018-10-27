package com.wxkj.tongcheng.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.HomeBigProductBean;

import java.util.List;

/**
 * @author: lujialei
 * @date: 2018/10/24
 * @describe:
 */


public class HomeBigProductAdapter extends BaseQuickAdapter<HomeBigProductBean,BaseViewHolder> {
    public HomeBigProductAdapter(@Nullable List<HomeBigProductBean> data) {
        super(R.layout.layout_mine_big_product_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBigProductBean item) {

    }
}
