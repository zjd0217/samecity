package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amap.api.location.AMapLocation;
import com.bumptech.glide.Glide;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.HomeBannerClassifyBean;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.GroupGoodsDetailActivity;
import com.wxkj.tongcheng.util.LocationUtil;

import java.util.List;

public class HomeCollageAdapter extends RecyclerView.Adapter<HomeCollageAdapter.HomeCollageViewHolder> {

    private Context mContext;
    private List<HomeBannerClassifyBean.SeckillEntity> mListData;
    private AMapLocation mLocation;

    public HomeCollageAdapter(Context context, List<HomeBannerClassifyBean.SeckillEntity> list, AMapLocation mLocation) {
        this.mContext = context;
        this.mListData = list;
        this.mLocation = mLocation;
    }

    public void setAMapLocation(AMapLocation location) {
        if (mLocation == null) {
            mLocation = location;
            notifyDataSetChanged();
        }
    }

    public void addItems(List<HomeBannerClassifyBean.SeckillEntity> list, int page) {
        if (page == 0) {
            mListData.clear();
        }
        mListData.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeCollageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeCollageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_collage, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCollageViewHolder holder, int position) {
        HomeBannerClassifyBean.SeckillEntity entity = mListData.get(0);
        Glide.with(mContext).load(entity.goods_pic).into(holder.mIvPic);
        holder.mTvTitle.setText(entity.seckill_name);
        holder.mTvPresentPrice.setText("¥" + entity.price_seckill);
        holder.mTvCollagePrice.setText("单买价" + entity.price_current + "   已拼" + entity.num_sold);
        if (mLocation != null) {
            String[] location = entity.baidu_pos.split(",");
            double d = LocationUtil.distanceOfTwoPoints(mLocation.getLatitude(), mLocation.getLongitude(), Double.parseDouble(location[1]), Double.parseDouble(location[0]));
            holder.mTvLocal.setText(d + "m");
        } else {
            holder.mTvLocal.setText("00m");
        }
        holder.mTvRushBuy.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, GroupGoodsDetailActivity.class);
            intent.putExtra("goods_id", entity.goods_id);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static class HomeCollageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPic;
        private TextView mTvTitle;
        private TextView mTvRushBuy;
        private TextView mTvPresentPrice;
        private TextView mTvCollagePrice;
        private TextView mTvLocal;

        public HomeCollageViewHolder(View itemView) {
            super(itemView);
            mIvPic = itemView.findViewById(R.id.mIvPic);
            mTvTitle = itemView.findViewById(R.id.mTvTitle);
            mTvRushBuy = itemView.findViewById(R.id.mTvRushBuy);
            mTvPresentPrice = itemView.findViewById(R.id.mTvPresentPrice);
            mTvCollagePrice = itemView.findViewById(R.id.mTvCollagePrice);
            mTvLocal = itemView.findViewById(R.id.mTvLocal);
        }
    }


}
