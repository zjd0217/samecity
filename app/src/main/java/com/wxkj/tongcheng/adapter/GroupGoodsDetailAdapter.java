package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.GroupGoodsDetailActivity;
import com.wxkj.tongcheng.util.AmountUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

/**
 * @author Liu haijun
 * @create 2018/10/14 0014
 * @Describe 拼团商品详情中推荐商品的适配器
 */
public class GroupGoodsDetailAdapter extends RecyclerView.Adapter {

    private List<GroupGoodsDetailBean.GoodsRecommendBean> list;
    private Context context;

    public GroupGoodsDetailAdapter(List<GroupGoodsDetailBean.GoodsRecommendBean> list, Context context) {
        this.context = context;
    }

    public void setList(List<GroupGoodsDetailBean.GoodsRecommendBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_goods_detail, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder vh = (MyHolder) holder;
            GroupGoodsDetailBean.GoodsRecommendBean goodsRecommendBean = list.get(position);
            RequestOptions options = new RequestOptions().transform(new CenterCrop());
            options.error(R.drawable.load_failure);
            options.placeholder(R.drawable.loading);
            Glide.with(context).load(goodsRecommendBean.getGoods_pic())
                    .apply(options)
                    .into(vh.itemGroupDetailImg);
            vh.itemGroupDetailName.setText(goodsRecommendBean.getGoods_name());
            vh.itemGroupDetailPrice.setText("￥" + AmountUtil.changeF2Y(goodsRecommendBean.getPrice_current()));
            vh.itemGroupDetailNum.setText("已拼" + goodsRecommendBean.getNum_sold() + "件");
            vh.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GroupGoodsDetailActivity.class);
                intent.putExtra("goods_id",goodsRecommendBean.getGoods_id());
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_group_detail_img)
        ImageView itemGroupDetailImg;
        @BindView(R.id.item_group_detail_name)
        TextView itemGroupDetailName;
        @BindView(R.id.item_group_detail_price)
        TextView itemGroupDetailPrice;
        @BindView(R.id.item_group_detail_num)
        TextView itemGroupDetailNum;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
