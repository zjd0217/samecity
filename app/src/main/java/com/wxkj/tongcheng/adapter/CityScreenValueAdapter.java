package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CityScreenBean;

import java.util.List;

/**
 * 同城筛选属性值适配器
 * Created by cheng on 2018/10/17.
 */

public class CityScreenValueAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CityScreenBean.ValueBean> list;

    public CityScreenValueAdapter(Context context, List<CityScreenBean.ValueBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.city_screen_value_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        CityScreenBean.ValueBean valueBean = list.get(position);
        myView.value_text.setText(valueBean.getAttribute_data_name());
        myView.value_text.setBackgroundResource(valueBean.isChoose()?R.drawable.ff5_4dp_bg:R.drawable.f7_4dp_bg);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    private class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView value_text;
        public MyView(View itemView) {
            super(itemView);
            value_text=itemView.findViewById(R.id.value_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            CityScreenBean.ValueBean valueBean = list.get(getLayoutPosition());
            valueBean.setChoose(!valueBean.isChoose());
            notifyDataSetChanged();
        }
    }
}
