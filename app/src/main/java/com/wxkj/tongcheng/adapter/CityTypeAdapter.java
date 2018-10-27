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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CityBannerBean;

import java.util.List;

/**
 * 同城服务类型适配器
 * Created by cheng on 2018/10/9.
 */

public class CityTypeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<CityBannerBean.TypeBean> list;
    private OnItemClickListener listener;

    public CityTypeAdapter(Context context, List<CityBannerBean.TypeBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.city_type_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        CityBannerBean.TypeBean bean = list.get(position);
        myView.text.setText(bean.getType_name());
        RequestOptions transform = new RequestOptions().transform(new CenterCrop());
        transform.error(R.drawable.load_failure);
        transform.placeholder(R.drawable.loading);
        Glide.with(context)
                .load(bean.getShow_img())
                .apply(transform)
                .into(myView.imageView);
        //点击事件
        myView.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    private class MyView extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView text;

        public MyView(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
