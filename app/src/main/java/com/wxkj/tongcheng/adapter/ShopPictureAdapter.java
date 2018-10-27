package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.ShopDetailsBean;
import com.wxkj.tongcheng.ui.activity.pictureselector.PictureSelectorActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Liu haijun
 * @create 2018/10/18 0018
 * @Describe 店铺相册
 */
public class ShopPictureAdapter extends RecyclerView.Adapter {

    List<ShopDetailsBean.ShoppicBean> shoppic = new ArrayList<>();

    private Context context;


    public ShopPictureAdapter(Context context) {
        this.context = context;
    }

    public void setShoppic(List<ShopDetailsBean.ShoppicBean> shoppic) {
        this.shoppic = shoppic;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_picture, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ShopDetailsBean.ShoppicBean shoppicBean = shoppic.get(position);
        MyViewHolder vh = (MyViewHolder) holder;
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loading).error(R.drawable.load_failure);
        Glide.with(context)
                .load(shoppicBean.getPic_path())
                .apply(requestOptions)
                .into(vh.iv);
        vh.iv.setOnClickListener(v -> {
            List<String> list = new ArrayList<>();
            for (ShopDetailsBean.ShoppicBean bean : shoppic) {
                list.add(bean.getPic_path());
            }
            PictureSelectorActivity.OpenPictureSelector(context, list, position);
        });
    }

    @Override
    public int getItemCount() {
        return shoppic.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_shop_picture_img)
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
