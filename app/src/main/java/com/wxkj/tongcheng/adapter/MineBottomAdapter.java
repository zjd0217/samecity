package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.listener.IClickItemListener;

/**
 * 我的 底部功能区 适配器
 * Created by cheng on 2018/10/13.
 */

public class MineBottomAdapter extends RecyclerView.Adapter {
    private Context context;
    private int [] icon;
    private String[] name;

    public MineBottomAdapter(Context context, int[] icon, String[] name) {
        this.context = context;
        this.icon = icon;
        this.name = name;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.mine_bottom_item_ayout,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        myView.image_view.setImageResource(icon[position]);
        myView.name_text.setText(name[position]);
    }

    @Override
    public int getItemCount() {
        return icon.length;
    }

    private class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image_view;
        private TextView name_text;
        public MyView(View itemView) {
            super(itemView);
            image_view=itemView.findViewById(R.id.image_view);
            name_text=itemView.findViewById(R.id.name_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null!=clickItemListener)
                clickItemListener.clickItemListener(getLayoutPosition());
        }
    }

    public IClickItemListener clickItemListener;
    public void setClickItemListener(IClickItemListener clickItemListener){
        this.clickItemListener=clickItemListener;
    }
}
