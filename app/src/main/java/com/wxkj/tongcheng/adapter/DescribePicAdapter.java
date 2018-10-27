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
import com.wxkj.tongcheng.util.CropSquareTransformation;
import com.wxkj.tongcheng.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DescribePicAdapter extends RecyclerView.Adapter<DescribePicAdapter.DescribePicViewHolder> {

    private Context mContext;
    private List<String> mListData;
    private RequestOptions mOptions;
    private OnItemAddClickListener mOnItemAddClickListener;

    public DescribePicAdapter(Context context, OnItemAddClickListener listener) {
        this.mContext = context;
        this.mOnItemAddClickListener = listener;
        mListData = new ArrayList<>();
        int width = Util.dp2px(mContext, 70);
        mOptions = new RequestOptions().transform(new CropSquareTransformation(width));
    }

    public void addItems(List<String> pic) {
        mListData.addAll(pic);
        notifyDataSetChanged();
    }

    public List<String> getListData() {
        return mListData;
    }

    @NonNull
    @Override
    public DescribePicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DescribePicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_describe_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DescribePicViewHolder holder, int position) {
        if (position < mListData.size()) {
            Glide.with(mContext).load(mListData.get(position)).apply(mOptions).into(holder.mIvPic);
            holder.mIvClear.setVisibility(View.VISIBLE);
            holder.mIvClear.setOnClickListener(v -> {
                mListData.remove(position);
                notifyDataSetChanged();
            });
            holder.mIvPic.setEnabled(false);
        } else {
            Glide.with(mContext).load(R.drawable.icon_release_add_picture).apply(mOptions).into(holder.mIvPic);
            holder.mIvClear.setVisibility(View.GONE);
            holder.mIvPic.setEnabled(true);
            holder.mIvPic.setOnClickListener(v -> {
                if (mOnItemAddClickListener != null) {
                    mOnItemAddClickListener.onAddItem();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mListData.size() + 1;
    }

    public interface OnItemAddClickListener {
        void onAddItem();
    }

    static class DescribePicViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPic;
        private ImageView mIvClear;

        public DescribePicViewHolder(View itemView) {
            super(itemView);
            mIvPic = itemView.findViewById(R.id.mIvPic);
            mIvClear = itemView.findViewById(R.id.mIvClear);
        }
    }

}
