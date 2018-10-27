package com.wxkj.tongcheng.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.HomeBigProductBean;

import java.util.List;

import static com.wxkj.tongcheng.bean.HomeBigProductBean.TYPE_GOODS;
import static com.wxkj.tongcheng.bean.HomeBigProductBean.TYPE_TIME;

/**
 * @author: lujialei
 * @date: 2018/10/24
 * @describe:
 */


public class BigProductAdapter extends BaseMultiItemQuickAdapter<HomeBigProductBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BigProductAdapter(List<HomeBigProductBean> data) {
        super(data);
        addItemType(TYPE_GOODS, R.layout.layout_item_type_big_goods);
        addItemType(TYPE_TIME, R.layout.layout_item_type_big_goods_time);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeBigProductBean item) {

    }
}
