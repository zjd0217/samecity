package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.icu.text.PluralRules;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.ServiceDemandDetailBean;

import java.util.List;

/**
 * 同城详情中 扩展属性适配器
 * Created by cheng on 2018/10/11.
 */

public class CityAttrAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ServiceDemandDetailBean.AttrBean> list;

    public CityAttrAdapter(Context context, List<ServiceDemandDetailBean.AttrBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.city_attr_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        ServiceDemandDetailBean.AttrBean bean = list.get(position);
        myView.key_text.setText(bean.getAttribute_value());
        myView.value_text.setText(bean.getData_value());
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    private class MyView extends RecyclerView.ViewHolder {
        private TextView key_text,value_text;
        public MyView(View itemView) {
            super(itemView);
            key_text=itemView.findViewById(R.id.key_text);
            value_text=itemView.findViewById(R.id.value_text);
        }
    }
}
