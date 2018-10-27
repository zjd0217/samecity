package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.ui.activity.demand.category.CategoryEntity;

import java.util.List;

public class AttributeListAdapter extends RecyclerView.Adapter<AttributeListAdapter.CategoryViewHolder> {

    private Context mContext;
    private List<CategoryEntity.ValueEntity> mListData;

    private OnItemClickListener mOnItemClickListener;

    public AttributeListAdapter(Context context, List<CategoryEntity.ValueEntity> list, OnItemClickListener listener) {
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
        CategoryEntity.ValueEntity entity = mListData.get(position);
        holder.mTvName.setText(entity.attribute_data_name);
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
        void onItemClick(CategoryEntity.ValueEntity entity);
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.mTvName);
        }
    }
}
