package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.GroupGoodsDetailActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.setting.address.ReceivingAddressActivity;
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
 * @Describe 搜索相关适配器
 */
public class GroupSearchAdapter extends RecyclerView.Adapter {

    private List<GroupTitleBean.TeambuyBean> list;
    private Context context;
    private Location location;


    public GroupSearchAdapter(List<GroupTitleBean.TeambuyBean> list, Location location, Context context) {
        this.list = list;
        this.location = location;
        this.context = context;
    }

    public void setList(List<GroupTitleBean.TeambuyBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setLocation(Location location) {
        this.location = location;
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
            GroupTitleBean.TeambuyBean teambuyBean = list.get(position);
            RequestOptions options = new RequestOptions();
            options.error(R.drawable.load_failure);
            options.placeholder(R.drawable.loading);
            Glide.with(context).load(teambuyBean.getGoods_pic())
                    .apply(options)
                    .into(vh.itemGroupSearchImg);

            String goods_labels = teambuyBean.getGoods_labels();
            String[] split1 = goods_labels.split(",");
            vh.itemGroupSearchTag1.setVisibility(View.INVISIBLE);
            vh.itemGroupSearchTag2.setVisibility(View.INVISIBLE);
            if (split1.length >= 1) {
                vh.itemGroupSearchTag1.setVisibility(View.VISIBLE);
                vh.itemGroupSearchTag1.setText(split1[0]);
            }
            if (split1.length >= 2) {
                vh.itemGroupSearchTag2.setVisibility(View.VISIBLE);
                vh.itemGroupSearchTag2.setText(split1[1]);
            }

            //团购名称
            vh.itemGroupSearchName.setText(teambuyBean.getTeambuy_name());
            //团购价格
            vh.itemGroupSearchPrice.setText("¥" + AmountUtil.changeF2Y(teambuyBean.getPrice_teambuy()));
            //已拼件
            vh.itemGroupSearchNum.setText(
                    "已拼件" + teambuyBean.getNum_sold());
            //距离
            String distance = "100m";
            if (location != null) {
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
            }
            vh.itemGroupSearchDistance.setText(distance);


            vh.itemGroupSearchMore.setOnClickListener(v -> {
                // TODO: 2018/10/11 0011  点击了更多的点点
                List<String> list = new ArrayList<>();
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                list.add("http://tc.liebianzhe.com/upload/upload/20180901/20180901161509531.png");
                //    PictureSelectorActivity.OpenPictureSelector(context, list);
                Intent intent = new Intent(context, ReceivingAddressActivity.class);
                context.startActivity(intent);
            });
            //商品详情
            vh.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GroupGoodsDetailActivity.class);
                intent.putExtra("goods_id", teambuyBean.getGoods_id());
                context.startActivity(intent);
            });


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
