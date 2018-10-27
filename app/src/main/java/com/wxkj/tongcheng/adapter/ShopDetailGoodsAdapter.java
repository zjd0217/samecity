package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.bean.ShopDetailGoodsBean;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.GroupGoodsDetailActivity;
import com.wxkj.tongcheng.ui.activity.pictureselector.PictureSelectorActivity;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Liu haijun
 * @create 2018/10/11 0011
 * @Describe 店铺详情中商品的适配器
 */
public class ShopDetailGoodsAdapter extends RecyclerView.Adapter {

    private List<ShopDetailGoodsBean> list = new ArrayList<>();
    private Context context;


    public ShopDetailGoodsAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ShopDetailGoodsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_search, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            MyHolder vh = (MyHolder) holder;
            ShopDetailGoodsBean bean = list.get(position);
            RequestOptions options = new RequestOptions();
            options.error(R.drawable.load_failure);
            options.placeholder(R.drawable.loading);
            Glide.with(context).load(bean.getGoods_pic())
                    .apply(options)
                    .into(vh.itemGroupSearchImg);

            String goods_labels = bean.getGoods_labels();
            String[] split1 = goods_labels.split(",");
            vh.itemGroupSearchTag1.setVisibility(View.INVISIBLE);
            vh.itemGroupSearchTag2.setVisibility(View.INVISIBLE);
            if (split1.length >= 1 && !TextUtils.isEmpty(split1[0])) {
                vh.itemGroupSearchTag1.setVisibility(View.VISIBLE);
                vh.itemGroupSearchTag1.setText(split1[0]);
            }
            if (split1.length >= 2 && !TextUtils.isEmpty(split1[1])) {
                vh.itemGroupSearchTag2.setVisibility(View.VISIBLE);
                vh.itemGroupSearchTag2.setText(split1[1]);
            }

            //团购名称
            vh.itemGroupSearchName.setText(bean.getGoods_name());
            //团购价格
            vh.itemGroupSearchPrice.setText("¥" + AmountUtil.changeF2Y(bean.getPrice_current()));
            //已拼件
            vh.itemGroupSearchNum.setText(
                    "已拼件" + bean.getNum_sold());
            //距离
            String distance = "100m";
            /*if (location != null) {
                //获取当前纬度
                double lat = location.getLatitude();
                //获取当前经度
                double lng = location.getLongitude();
                String baidu_pos = teambuyBean.getBaidu_pos();
                String[] split = baidu_pos.split(",");
                //商品经度
                double lat1 = Double.parseDouble(split[0]);
                //商品纬度
                double lng1 = Double.parseDouble(split[1]);
                //计算当前位置和商品的距离
                double points = LocationUtil.distanceOfTwoPoints(lat1, lng1, lat, lng);
                distance = "" + points + "km";
            }*/
            vh.itemGroupSearchDistance.setText(distance);

        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_group_search_img)
        ImageView itemGroupSearchImg;
        @BindView(R.id.item_group_search_name)
        TextView itemGroupSearchName;
        @BindView(R.id.item_group_search_tag1)
        TextView itemGroupSearchTag1;
        @BindView(R.id.item_group_search_tag2)
        TextView itemGroupSearchTag2;
        @BindView(R.id.item_group_search_distance)
        TextView itemGroupSearchDistance;
        @BindView(R.id.item_group_search_price)
        TextView itemGroupSearchPrice;
        @BindView(R.id.item_group_search_num)
        TextView itemGroupSearchNum;

        @BindView(R.id.item_group_search_more)
        ImageView itemGroupSearchMore;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
