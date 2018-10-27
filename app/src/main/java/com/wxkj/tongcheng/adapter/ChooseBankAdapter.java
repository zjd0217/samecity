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
import com.wxkj.tongcheng.listener.IClickItemListener;

/**
 * 选择银行卡 适配器
 * Created by cheng on 2018/10/20.
 */

public class ChooseBankAdapter extends RecyclerView.Adapter {
    private Context context;
    private String[] namesArr;
    private String name;

    public ChooseBankAdapter(Context context,String[] namesArr,String name) {
        this.context = context;
        this.namesArr = namesArr;
        this.name=name;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyView(LayoutInflater.from(context).inflate(R.layout.choose_bank_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyView myView= (MyView) holder;
        myView.name_text.setText(namesArr[position]);
        if (name.equals(namesArr[position]))
            myView.imageView.setImageResource(R.drawable.choosed_icon);
        else
            myView.imageView.setImageResource(R.drawable.no_choose_icon);
    }

    @Override
    public int getItemCount() {
        return namesArr.length;
    }

    private class MyView extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageView;
        private TextView name_text;
        public MyView(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            name_text=itemView.findViewById(R.id.name_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null!=iClickItemListener){
                iClickItemListener.clickItem(getLayoutPosition(),name_text.getText().toString());
            }
        }
    }

    public interface IClickItemListener{
        void clickItem(int position,String name);
    }
    public IClickItemListener iClickItemListener;
    public void setiClickItemListener(IClickItemListener iClickItemListener){
        this.iClickItemListener=iClickItemListener;
    }
}
