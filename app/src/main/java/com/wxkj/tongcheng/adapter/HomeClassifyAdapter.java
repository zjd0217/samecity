package com.wxkj.tongcheng.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
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

public class HomeClassifyAdapter extends RecyclerView.Adapter<HomeClassifyAdapter.HomeClassifyViewHolder> {

    private List<HomeBannerClassifyBean.ClassifyEntity> mListData;

    public HomeClassifyAdapter(List<HomeBannerClassifyBean.ClassifyEntity> list) {
        this.mListData = list;
    }

    @NonNull
    @Override
    public HomeClassifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeClassifyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_classify, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeClassifyViewHolder holder, int position) {
        final HomeBannerClassifyBean.ClassifyEntity mBean = mListData.get(position);
        Glide.with(holder.itemView.getContext()).load(mBean.show_img).into(holder.mIvIcon);
        holder.mTvClassify.setText(mBean.type_name);
        holder.mIvHot.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), GroupSearchActivity.class);
            intent.putExtra("type_id", mBean.type_id);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    static class HomeClassifyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvIcon;
        private TextView mTvClassify;
        private ImageView mIvHot;

        public HomeClassifyViewHolder(View itemView) {
            super(itemView);
            mIvIcon = itemView.findViewById(R.id.mIvIcon);
            mTvClassify = itemView.findViewById(R.id.mTvClassify);
            mIvHot = itemView.findViewById(R.id.mIvHot);
        }
    }

}
