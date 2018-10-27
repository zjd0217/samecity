package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.PutForwardRecordBean;
import com.wxkj.tongcheng.ui.fragment.home.HomePresenter;
import com.wxkj.tongcheng.util.Util;

import org.xutils.view.annotation.ContentView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 提现记录 适配器
 * Created by cheng on 2018/10/19.
 */

public class PutForwardRecordAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<PutForwardRecordBean> list;

    public PutForwardRecordAdapter(Context context, List<PutForwardRecordBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 10) {
            return new ContentView(LayoutInflater.from(context).inflate(R.layout.put_forward_record_content_item_layout, parent, false));
        }
        return new TitleView(LayoutInflater.from(context).inflate(R.layout.put_forward_record_title_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PutForwardRecordBean bean = list.get(position);
        if (holder instanceof TitleView){
            TitleView titleView = (TitleView) holder;
            titleView.title_text.setText(bean.getYm());
        }
        if (holder instanceof ContentView){
            ContentView contentView = (ContentView) holder;
            contentView.order_text.setText(bean.getOut_biz_no());
            contentView.money_text.setText(Util.fenToYuan(bean.getCapital_num()));
            contentView.time_text.setText(getTime(bean.getTime_setup()));
            contentView.status_text.setText(getStatusString(bean.getEnchashment_state()));
            contentView.status_text.setTextColor(getStatusColor(bean.getEnchashment_state()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType();
    }

    /**
     * 时间戳转字符串时间
     * @param time
     * @return
     */
    private String getTime(long time){
        return new SimpleDateFormat("dd号 HH:mm").format(new Date(time));
    }

    /**
     * 获取订单状态
     * @param status
     * @return
     */
    private String getStatusString(int status){
        switch (status){
            case 0:
                return "申请中";
            case 1:
                return "开始汇款";
            case 2:
                return "汇款中";
            case 3:
                return "汇款成功";
            case 4:
                return "汇款失败";
            case 5:
                return "汇款操作被拒绝";
            default:
                return "未知状态";
        }
    }

    /**
     * 获取状态字颜色
     * @param status
     * @return
     */
    private int getStatusColor(int status){
        Resources resources = context.getResources();
        switch (status){
            case 3:
                return resources.getColor(R.color.color249);
            case 4:
            case 5:
                return resources.getColor(R.color.colorff5);
            case 0:
            case 1:
            case 2:
            default:
                return resources.getColor(R.color.color000);
        }
    }

    private class TitleView extends RecyclerView.ViewHolder {
        private TextView title_text;
        public TitleView(View itemView) {
            super(itemView);
            title_text=itemView.findViewById(R.id.title_text);
            itemView.setTag(true);
        }
    }

    private class ContentView extends RecyclerView.ViewHolder {
        private TextView order_text,time_text,money_text,balance_text,status_text;
        public ContentView(View itemView) {
            super(itemView);
            order_text=itemView.findViewById(R.id.order_text);
            time_text=itemView.findViewById(R.id.time_text);
            money_text=itemView.findViewById(R.id.money_text);
            balance_text=itemView.findViewById(R.id.balance_text);
            status_text=itemView.findViewById(R.id.status_text);
            itemView.setTag(false);
        }
    }
}
