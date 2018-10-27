package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CollectGoodsBean;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * 收藏商品列表 适配器
 * Created by cheng on 2018/10/21.
 */

public class CollectGoodsAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CollectGoodsBean> list;

    public CollectGoodsAdapter(Context context, List<CollectGoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.collect_goods_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        CollectGoodsBean bean = list.get(position);
        myView.title_text.setText(bean.getTitle());
        myView.price_text.setText("¥"+ Util.fenToYuan(bean.getPrice_current()));
        Glide.with(context).load(bean.getGoods_pic()).into(myView.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyView extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView title_text,price_text,pindan_text;
        public MyView(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            title_text=itemView.findViewById(R.id.title_text);
            price_text=itemView.findViewById(R.id.price_text);
            pindan_text=itemView.findViewById(R.id.pindan_text);
        }
    }
}
