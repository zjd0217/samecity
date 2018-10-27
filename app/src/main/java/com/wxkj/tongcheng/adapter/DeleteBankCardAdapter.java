package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.listener.IClickItemListener;

import java.util.List;

/**
 * 删除银行卡 适配器
 * Created by cheng on 2018/10/19.
 */

public class DeleteBankCardAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BankCardListBean> list;

    public DeleteBankCardAdapter(Context context, List<BankCardListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.delete_bank_card_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        String account_info = list.get(position).getAccount_info();
        myView.card_name_text.setText("储蓄卡（尾号"+(account_info.length()<4?account_info:account_info.substring(0,4))+"）");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView card_name_text,delete_text;
        public MyView(View itemView) {
            super(itemView);
            delete_text=itemView.findViewById(R.id.delete_text);
            card_name_text=itemView.findViewById(R.id.card_name_text);
            delete_text.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null!=clickItemListener)
                clickItemListener.clickItemListener(getLayoutPosition());
        }
    }

    public IClickItemListener clickItemListener;
    public void setClickItemListener(IClickItemListener clickItemListener){
        this.clickItemListener=clickItemListener;
    }
}
