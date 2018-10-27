package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.LocationUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Liu haijun
 * @create 2018/10/14 0014
 * @Describe 拼团商品详情猜你喜欢的适配器
 */
public class GroupDetailGoodsLike extends BaseAdapter {

    private Context context;
    private List<GroupGoodsDetailBean.GoodsLikeBean> goods_like;
    private Location location;

    public GroupDetailGoodsLike(Context context, List<GroupGoodsDetailBean.GoodsLikeBean> goods_like) {
        this.context = context;
        this.goods_like = goods_like;
    }

    public void setGoods_like(List<GroupGoodsDetailBean.GoodsLikeBean> goods_like) {
        this.goods_like = goods_like;
        notifyDataSetChanged();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public int getCount() {
        return goods_like.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_group_search, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        vh = (ViewHolder) convertView.getTag();
        GroupGoodsDetailBean.GoodsLikeBean teambuyBean = goods_like.get(position);
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
        vh.itemGroupSearchName.setText(teambuyBean.getGoods_name());
        //团购价格
        vh.itemGroupSearchPrice.setText("¥" + AmountUtil.changeF2Y(teambuyBean.getPrice_current()));
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


        return convertView;
    }


    class ViewHolder {
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

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
