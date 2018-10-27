package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CityScreenBean;

import java.util.List;

/**
 * 同城 筛选适配器
 * Created by cheng on 2018/10/17.
 */

public class CityScreenAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CityScreenBean> list;

    public CityScreenAdapter(Context context, List<CityScreenBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.city_screen_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        CityScreenBean bean = list.get(position);
        myView.key_text.setText(bean.getService_attribute_name());
        myView.recyclerView.setLayoutManager(new GridLayoutManager(context,3, LinearLayoutManager.VERTICAL,false));
        CityScreenValueAdapter valueAdapter=new CityScreenValueAdapter(context,list.get(position).getValue());
        myView.recyclerView.setAdapter(valueAdapter);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    private class MyView extends RecyclerView.ViewHolder {
        private TextView key_text;
        private RecyclerView recyclerView;
        public MyView(View itemView) {
            super(itemView);
            key_text=itemView.findViewById(R.id.key_text);
            recyclerView=itemView.findViewById(R.id.recyclerView);
        }
    }
}
