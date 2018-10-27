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
import com.wxkj.tongcheng.bean.BankCardListBean;

import java.util.List;

/**
 * 我的银行卡 页面
 * Created by cheng on 2018/10/18.
 */

public class MyBankCardAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BankCardListBean> list;

    public MyBankCardAdapter(Context context, List<BankCardListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.my_bank_card_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView = (MyView) holder;
        BankCardListBean bean = list.get(position);
        myView.bank_logo_img.setImageResource(getImageResId(bean.getAccount_channel()));
        myView.bank_name_text.setText(getBankName(bean.getAccount_channel()));
        String account_info = bean.getAccount_info();
        myView.bank_type_text.setText("尾号"+(account_info.length()<4?account_info:account_info.substring(0,4))+"储蓄卡");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private int getImageResId(int channel){
        switch (channel){
            case 3:
                return R.drawable.icbc;
            case 4:
                return R.drawable.cbc;
            case 5:
                return R.drawable.abc;
            case 6:
                return R.drawable.bc;
            default:
                return R.drawable.bc;
        }
    }

    private String getBankName(int channel){
        switch (channel){
            case 3:
                return "中国工商银行";
            case 4:
                return "中国建设银行";
            case 5:
                return "中国农业银行";
            case 6:
                return "中国银行";
            default:
                return "未知银行";
        }
    }

    private class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView bank_logo_img,choose_img;
        private TextView bank_name_text,bank_type_text;
        public MyView(View itemView) {
            super(itemView);
            bank_logo_img=itemView.findViewById(R.id.bank_logo_img);
            choose_img=itemView.findViewById(R.id.choose_img);
            bank_name_text=itemView.findViewById(R.id.bank_name_text);
            bank_type_text=itemView.findViewById(R.id.bank_type_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null!=iClickItemListener) iClickItemListener.clickItem(list.get(getLayoutPosition()));
        }
    }

    public interface IClickItemListener{
        void clickItem(BankCardListBean bean);
    }
    public IClickItemListener iClickItemListener;
    public void setiClickItemListener(IClickItemListener iClickItemListener){
        this.iClickItemListener=iClickItemListener;
    }
}
