package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.HomeBannerClassifyBean;
import com.wxkj.tongcheng.ui.activity.group.search.GroupSearchActivity;

import java.util.List;
import java.util.Random;

public class HomeSecAdapter extends RecyclerView.Adapter<HomeSecAdapter.HomeSecViewHolder> {

    private int[] colors = {R.color.color_FF2E43, R.color.color_3288FF, R.color.color_FF8C00, R.color.color_A145FE};
    private Context mContext;
    private List<HomeBannerClassifyBean.ItemEntity> mListData;

    public HomeSecAdapter(Context context, List<HomeBannerClassifyBean.ItemEntity> list) {
        this.mContext = context;
        this.mListData = list;
    }

    @NonNull
    @Override
    public HomeSecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeSecViewHolder(LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.item_home_sec, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeSecViewHolder holder, int position) {
        HomeBannerClassifyBean.ItemEntity entity = mListData.get(0);
        holder.mTvSecTitle.setText(entity.item_title);
        holder.mTvSecContent.setText(entity.item_content);
        Glide.with(mContext).load(entity.item_pic).into(holder.mIvSecPic);
        holder.mTvSecTitle.setTextColor(ContextCompat.getColor(mContext, colors[new Random().nextInt(colors.length)]));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, GroupSearchActivity.class);
            intent.putExtra("type_id", "");
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    static class HomeSecViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvSecPic;
        private TextView mTvSecTitle;
        private TextView mTvSecContent;

        public HomeSecViewHolder(View itemView) {
            super(itemView);
            mIvSecPic = itemView.findViewById(R.id.mIvSecPic);
            mTvSecTitle = itemView.findViewById(R.id.mTvSecTitle);
            mTvSecContent = itemView.findViewById(R.id.mTvSecContent);
        }
    }

}
