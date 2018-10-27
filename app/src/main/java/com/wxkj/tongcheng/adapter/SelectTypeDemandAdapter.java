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
import com.bumptech.glide.Glide;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.ui.activity.demand.SelectTypeDemandEntity;
import com.wxkj.tongcheng.ui.activity.demand.category.CategoryActivity;

import java.util.List;

public class SelectTypeDemandAdapter extends RecyclerView.Adapter<SelectTypeDemandAdapter.SelectTypeDemandViewHolder> {

    private Context mContext;
    private List<SelectTypeDemandEntity> mListData;

    public SelectTypeDemandAdapter(Context context, List<SelectTypeDemandEntity> list) {
        this.mContext = context;
        this.mListData = list;
    }


    @NonNull
    @Override

    public SelectTypeDemandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectTypeDemandViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_select_type_demand, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTypeDemandViewHolder holder, int position) {
        SelectTypeDemandEntity entity = mListData.get(position);
        Glide.with(mContext).load(entity.show_img).into(holder.mIvPic);
        holder.mTvName.setText(entity.type_name);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CategoryActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("entity", entity);
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    static class SelectTypeDemandViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPic;
        private TextView mTvName;

        public SelectTypeDemandViewHolder(View itemView) {
            super(itemView);
            mIvPic = itemView.findViewById(R.id.mIvPic);
            mTvName = itemView.findViewById(R.id.mTvName);
        }
    }
}
