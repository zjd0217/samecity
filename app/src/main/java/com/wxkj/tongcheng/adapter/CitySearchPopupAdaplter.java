package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CityTypeBean;

import java.util.List;

/**
 * 同城搜索结果分类左边的适配器
 * Created by cheng on 2018/10/12.
 */

public class CitySearchPopupAdaplter extends RecyclerView.Adapter {
    private Context context;
    private List<CityTypeBean> list;
    private int clickPosition=-1;

    public CitySearchPopupAdaplter(Context context, List<CityTypeBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.city_search_popup_left_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        myView.text.setText(list.get(position).getType_name());
        myView.text.setTextColor(clickPosition==position?context.getResources().getColor(R.color.colorff5):context.getResources().getColor(R.color.color555));
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    private class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView text;
        public MyView(View itemView) {
            super(itemView);
            text=itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null!=clickItemListener){
                clickItemListener.clickItem(list.get(getLayoutPosition()).getType_id());
                clickPosition=getLayoutPosition();
                notifyDataSetChanged();
            }
        }
    }

    public interface IClickItemListener{
        void clickItem(int id);
    }
    public IClickItemListener clickItemListener;
    public void setClickItemListener(IClickItemListener clickItemListener){
        this.clickItemListener=clickItemListener;
    }
}
