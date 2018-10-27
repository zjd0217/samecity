package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxkj.tongcheng.R;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListViewHolder> {

    private Context mContext;

    public MessageListAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MessageListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    static class MessageListViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIvPic;
        private TextView mTvNickname;
        private TextView mTvType;
        private TextView mTvTime;
        private TextView mTvMessage;

        public MessageListViewHolder(View itemView) {
            super(itemView);
            mIvPic = itemView.findViewById(R.id.mIvPic);
            mTvNickname = itemView.findViewById(R.id.mTvNickname);
            mTvType = itemView.findViewById(R.id.mTvType);
            mTvTime = itemView.findViewById(R.id.mTvTime);
            mTvMessage = itemView.findViewById(R.id.mTvMessage);
        }
    }
}
