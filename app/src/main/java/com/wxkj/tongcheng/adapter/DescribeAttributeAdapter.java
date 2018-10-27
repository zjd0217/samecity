package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.ui.activity.demand.category.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class DescribeAttributeAdapter extends RecyclerView.Adapter<DescribeAttributeAdapter.DescribeAttributeViewHolder> {

    private Context mContext;
    private ArrayMap<Integer, EditText> mArrayMap;
    private List<CategoryEntity.AttributeEntity> mListData;
    private List<CategoryEntity.ValueEntity> mValueList;
    private List<CategoryEntity.ValueEntity> mSelectValueList;
    private OnItemClickListener mOnItemClickListener;
    private StringBuilder builder;

    public DescribeAttributeAdapter(Context context, List<CategoryEntity.AttributeEntity> mList, List<CategoryEntity.ValueEntity> mValueList, OnItemClickListener onItemClickListener) {
        this.mArrayMap = new ArrayMap<>();
        this.mContext = context;
        this.mListData = mList;
        this.mValueList = mValueList;
        this.mSelectValueList = new ArrayList<>();
        this.builder = new StringBuilder();
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setValueList(CategoryEntity.ValueEntity valueList) {
        boolean isHas = false;
        for (CategoryEntity.ValueEntity entity : mSelectValueList) {
            if (entity.attribute_data_id == valueList.attribute_data_id) {
                isHas = true;
                break;
            }
        }
        if (!isHas) {
            this.mSelectValueList.add(valueList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public DescribeAttributeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_describe_attribute, parent, false);
        return new DescribeAttributeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DescribeAttributeViewHolder holder, int position) {
        mArrayMap.put(position, holder.mEtAttributes);
        CategoryEntity.AttributeEntity mEntity = mListData.get(position);
        holder.mTvAttributes.setText(mEntity.service_attribute_name);
        if (mEntity.need_must == 1) {
            holder.mTvAttributes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_red_asterisk, 0);
        } else {
            holder.mTvAttributes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_red_asterisk, 0);
        }
        builder.delete(0, builder.length());
        for (CategoryEntity.ValueEntity entity : mSelectValueList) {
            if (entity.service_attribute_id == mEntity.service_attribute_id) {
                if (builder.length() > 0) {
                    builder.append("，");
                }
                builder.append(entity.attribute_data_name);
            }
        }
        holder.mEtAttributes.setText(builder.toString());
        boolean isHas = false;
        for (CategoryEntity.ValueEntity entity : mValueList) {
            if (entity.service_attribute_id == mEntity.service_attribute_id) {
                isHas = true;
                break;
            }
        }
        holder.mEtAttributes.setFocusableInTouchMode(!isHas);
        holder.mEtAttributes.setFocusable(!isHas);
        if (isHas) {
            holder.mEtAttributes.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.icon_next, 0);
            holder.mEtAttributes.setHint("请选择");
            holder.mEtAttributes.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    List<CategoryEntity.ValueEntity> mList = new ArrayList<>();
                    for (CategoryEntity.ValueEntity entity : mValueList) {
                        if (entity.service_attribute_id == mEntity.service_attribute_id) {
                            mList.add(entity);
                        }
                    }
                    mOnItemClickListener.onItemClick(mList, mEntity.service_attribute_name);
                }
            });
        } else {
            holder.mEtAttributes.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.mEtAttributes.setHint("请输入");
            holder.mEtAttributes.setOnClickListener(null);
        }

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(List<CategoryEntity.ValueEntity> mList, String title);
    }

    static class DescribeAttributeViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvAttributes;
        private EditText mEtAttributes;

        public DescribeAttributeViewHolder(View itemView) {
            super(itemView);
            mTvAttributes = itemView.findViewById(R.id.mTvAttributes);
            mEtAttributes = itemView.findViewById(R.id.mEtAttributes);
        }
    }
}
