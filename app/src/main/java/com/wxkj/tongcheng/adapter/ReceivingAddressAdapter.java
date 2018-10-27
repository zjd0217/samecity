package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.ReceivingAddressBean;
import com.wxkj.tongcheng.ui.activity.mine.user.setting.address.addoredit.AddOrEditAddressActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Liu haijun
 * @create 2018/10/19 0019
 * @Describe 收货地址
 */
public class ReceivingAddressAdapter extends RecyclerView.Adapter {


    private Context context;
    private List<ReceivingAddressBean> list = new ArrayList<>();
    private CompoundButton.OnCheckedChangeListener checkedChangeListener;

    public ReceivingAddressAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<ReceivingAddressBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setCheckedChangeListener(CompoundButton.OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_receiving_address, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        ReceivingAddressBean bean = list.get(position);
        vh.itemReceivingAddressName.setText(bean.getContact_man());
        vh.itemReceivingAddressPhone.setText(bean.getContact_number());
        vh.itemReceivingAddressAddress.setText("收货地址: " + bean.getAddr_detail());
        vh.itemReceivingAddressDefault.setOnCheckedChangeListener(null);
        vh.itemReceivingAddressDefault.setChecked(bean.getDefault_fg() == 1);
        if (checkedChangeListener != null) {
            vh.itemReceivingAddressDefault.setTag(position);
            vh.itemReceivingAddressDefault.setOnCheckedChangeListener(checkedChangeListener);
        }
        vh.itemReceivingAddressEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddOrEditAddressActivity.class);
            intent.putExtra("bean", bean);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_receiving_address_name)
        TextView itemReceivingAddressName;
        @BindView(R.id.item_receiving_address_phone)
        TextView itemReceivingAddressPhone;
        @BindView(R.id.item_receiving_address_address)
        TextView itemReceivingAddressAddress;
        @BindView(R.id.item_receiving_address_default)
        CheckBox itemReceivingAddressDefault;
        @BindView(R.id.item_receiving_address_edit)
        LinearLayout itemReceivingAddressEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
