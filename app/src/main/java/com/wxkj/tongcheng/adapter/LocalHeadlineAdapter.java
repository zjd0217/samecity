package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.LocalHeadlineBean;
import com.wxkj.tongcheng.ui.activity.home.local.detail.LocalHeadlineDetailActivity;
import com.wxkj.tongcheng.util.Util;

public class LocalHeadlineAdapter extends RecyclerView.Adapter<LocalHeadlineAdapter.BaseViewHolder> {

    private static final int BIG_PIC_MODE = 1;
    private static final int SMALL_PIC_MODE = 2;
    private static final int MORE_PIC_MODE = 3;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return BIG_PIC_MODE;
        } else if (position > 0 && position < 4) {
            return SMALL_PIC_MODE;
        } else if (position == 4) {
            return MORE_PIC_MODE;
        }
        return SMALL_PIC_MODE;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder holder = null;
        switch (viewType) {
            case BIG_PIC_MODE:
                holder = new BigPicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_headline_big_pic, parent, false));
                break;
            case SMALL_PIC_MODE:
                holder = new SmallPicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_headline_small_pic, parent, false));
                break;
            case MORE_PIC_MODE:
                holder = new MorePicViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_headline_more_pic, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBindViewHolder(holder, null);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static abstract class BaseViewHolder extends RecyclerView.ViewHolder {

        BaseViewHolder(View itemView) {
            super(itemView);
        }

        abstract void onBindViewHolder(BaseViewHolder holder, LocalHeadlineBean mBean);
    }


    private static class BigPicViewHolder extends BaseViewHolder {

        private ImageView mIvPic;

        BigPicViewHolder(View itemView) {
            super(itemView);
            mIvPic = itemView.findViewById(R.id.mIvPic);
        }

        @Override
        void onBindViewHolder(BaseViewHolder holder, LocalHeadlineBean mBean) {
            holder.itemView.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), LocalHeadlineDetailActivity.class)));
        }
    }


    private static class SmallPicViewHolder extends BaseViewHolder {

        private ImageView mIvPic;
        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvRead;
        private TextView mTvContent;

        SmallPicViewHolder(View itemView) {
            super(itemView);
            mIvPic = itemView.findViewById(R.id.mIvPic);
            mTvTitle = itemView.findViewById(R.id.mTvTitle);
            mTvTime = itemView.findViewById(R.id.mTvTime);
            mTvRead = itemView.findViewById(R.id.mTvRead);
            mTvContent = itemView.findViewById(R.id.mTvContent);
        }

        @Override
        void onBindViewHolder(BaseViewHolder holder, LocalHeadlineBean mBean) {
            holder.itemView.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), LocalHeadlineDetailActivity.class)));
        }
    }


    private static class MorePicViewHolder extends BaseViewHolder {

        private TextView mTvTitle;
        private TextView mTvTime;
        private TextView mTvRead;
        private ImageView mIvPic1;
        private ImageView mIvPic2;
        private ImageView mIvPic3;

        MorePicViewHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.mTvTitle);
            mTvTime = itemView.findViewById(R.id.mTvTime);
            mTvRead = itemView.findViewById(R.id.mTvRead);
            mIvPic1 = itemView.findViewById(R.id.mIvPic1);
            mIvPic2 = itemView.findViewById(R.id.mIvPic2);
            mIvPic3 = itemView.findViewById(R.id.mIvPic3);
            WindowManager wm = (WindowManager) itemView.getContext().getSystemService(Context.WINDOW_SERVICE);
            if (wm != null) {
                DisplayMetrics dm = new DisplayMetrics();
                wm.getDefaultDisplay().getMetrics(dm);
                int width = (dm.widthPixels - Util.dp2px(itemView.getContext(), 50)) / 3;
                setImgParams(mIvPic1, width);
                setImgParams(mIvPic2, width);
                setImgParams(mIvPic3, width);
            }
        }

        @Override
        void onBindViewHolder(BaseViewHolder holder, LocalHeadlineBean mBean) {
            holder.itemView.setOnClickListener(v -> v.getContext().startActivity(new Intent(v.getContext(), LocalHeadlineDetailActivity.class)));
        }

        private void setImgParams(ImageView mIvPic, int w) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mIvPic.getLayoutParams();
            params.width = w;
            params.height = w;
        }
    }
}
