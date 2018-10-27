package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.ui.activity.demand.category.CategoryEntity;
import com.wxkj.tongcheng.ui.activity.demand.describe.DescribeActivity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<CategoryEntity.TypeEntity> mListData;

    private OnItemClickListener mOnItemClickListener;

    public CategoryAdapter(Context context, List<CategoryEntity.TypeEntity> list, OnItemClickListener listener) {
        this.mContext = context;
        this.mListData = list;
        this.mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryEntity.TypeEntity entity = mListData.get(position);
        holder.mTvName.setText(entity.type_name);
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(entity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryEntity.TypeEntity entity);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.mTvName);
        }
    }
}
