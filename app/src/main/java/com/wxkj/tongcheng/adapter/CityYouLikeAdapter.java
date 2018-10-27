package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.NearServiceBean;
import com.wxkj.tongcheng.bean.ServiceDemandDetailBean;
import com.wxkj.tongcheng.listener.IClickItemListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 同城猜你喜欢适配器
 * Created by cheng on 2018/10/8.
 */

public class CityYouLikeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ServiceDemandDetailBean.InfoLikeBean> list;

    public CityYouLikeAdapter(Context context, List<ServiceDemandDetailBean.InfoLikeBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==0)
            return new MyImgView(LayoutInflater.from(context).inflate(R.layout.near_service_demand_img_item_layout,parent,false));
        return new MyNoImgView(LayoutInflater.from(context).inflate(R.layout.near_service_demand_img_no_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ServiceDemandDetailBean.InfoLikeBean bean = list.get(position);
        //有图
        if (holder instanceof MyImgView){
            MyImgView view= (MyImgView) holder;
            view.title_text.setText(bean.getInfo_title());
            view.describe_text.setText(bean.getInfo_describe());
            Glide.with(context).load(bean.getPic_path()).apply(new RequestOptions().transform(new CenterCrop())).into(view.imageView);
            view.ji_img.setVisibility(bean.getUrgent_fg()==0?View.GONE:View.VISIBLE);
            view.time_text.setText(new SimpleDateFormat("MM-dd").format(new Date(bean.getTime_setup())));
            view.look_num_text.setText(bean.getVisit_times()+"");
        }else if (holder instanceof MyNoImgView){
            MyNoImgView view= (MyNoImgView) holder;
            view.title_text.setText(bean.getInfo_title());
            view.describe_text.setText(bean.getInfo_describe());
            view.ji_img.setVisibility(bean.getUrgent_fg()==0?View.GONE:View.VISIBLE);
            view.time_text.setText(new SimpleDateFormat("MM-dd").format(new Date(bean.getTime_setup())));
            view.look_num_text.setText(bean.getVisit_times()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TextUtils.isEmpty(list.get(position).getPic_path())?1:0;
    }

    private class MyImgView extends RecyclerView.ViewHolder {
        private ImageView imageView,ji_img;
        private TextView title_text,describe_text,time_text,distance_text,look_num_text;
        public MyImgView(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            ji_img=itemView.findViewById(R.id.ji_img);
            title_text=itemView.findViewById(R.id.title_text);
            describe_text=itemView.findViewById(R.id.describe_text);
            time_text=itemView.findViewById(R.id.time_text);
            distance_text=itemView.findViewById(R.id.distance_text);
            look_num_text=itemView.findViewById(R.id.look_num_text);
        }
    }

    private class MyNoImgView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ji_img;
        private TextView title_text,describe_text,time_text,distance_text,look_num_text;
        public MyNoImgView(View itemView) {
            super(itemView);
            ji_img=itemView.findViewById(R.id.ji_img);
            title_text=itemView.findViewById(R.id.title_text);
            describe_text=itemView.findViewById(R.id.describe_text);
            time_text=itemView.findViewById(R.id.time_text);
            distance_text=itemView.findViewById(R.id.distance_text);
            look_num_text=itemView.findViewById(R.id.look_num_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null!=clickItemListener) clickItemListener.clickItemListener(list.get(getLayoutPosition()).getService_need_id());
        }
    }

    public IClickItemListener clickItemListener;
    public void setClickItemListener(IClickItemListener clickItemListener){
        this.clickItemListener=clickItemListener;
    }
}
