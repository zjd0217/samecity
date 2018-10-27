package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EvaluateBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Liu haijun
 * @create 2018/10/17 0017
 * @Describe 查看所有评论的适配器
 */
public class AllEvaluateAdapter extends RecyclerView.Adapter {

    private List<EvaluateBean> list = new ArrayList<>();

    private Context context;

    public AllEvaluateAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<EvaluateBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_evaluate, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EvaluateBean evaluateBean = list.get(position);
        MyViewHolder vh = (MyViewHolder) holder;
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        requestOptions.placeholder(R.drawable.loading).error(R.drawable.load_failure);
        Glide.with(context).load(evaluateBean.getHead_portrait())
                .apply(requestOptions)
                .into(vh.itemAllEvaluateImg);
        vh.itemAllEvaluateName.setText(evaluateBean.getUser_cname());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = new Date(evaluateBean.getTime_setup() * 1000L);
        String t1 = format.format(d1);
        vh.itemAllEvaluateTime.setText(t1);
        vh.itemAllEvaluateContent.setText(evaluateBean.getDiscuss_content());
        vh.itemAllEvaluateGoodsName.setText(evaluateBean.getGoods_name());
        //加载九宫图片
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<EvaluateBean.PicsBean> pics = evaluateBean.getPics();
        if (pics != null) {
            for (EvaluateBean.PicsBean imageDetail : pics) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(imageDetail.getPic_path());
                info.setBigImageUrl(imageDetail.getPic_path());
                imageInfo.add(info);
            }
        }
        vh.itemAllEvaluateNineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_all_evaluate_img)
        ImageView itemAllEvaluateImg;
        @BindView(R.id.item_all_evaluate_name)
        TextView itemAllEvaluateName;
        @BindView(R.id.item_all_evaluate_time)
        TextView itemAllEvaluateTime;
        @BindView(R.id.item_all_evaluate_content)
        TextView itemAllEvaluateContent;
        @BindView(R.id.item_all_evaluate_nine_grid)
        NineGridView itemAllEvaluateNineGrid;
        @BindView(R.id.item_all_evaluate_goods_name)
        TextView itemAllEvaluateGoodsName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
