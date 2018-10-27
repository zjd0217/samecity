package com.wxkj.tongcheng.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.util.TimeUnit;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.move.widget.XIndicator;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.ui.activity.group.goodsdetail.GroupGoodsDetailActivity;
import com.wxkj.tongcheng.ui.activity.group.search.GroupSearchActivity;
import com.wxkj.tongcheng.ui.fragment.city.city.ServiceTypeFragment;
import com.wxkj.tongcheng.ui.fragment.group.GroupFragment;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.GlideImageLoader;
import com.wxkj.tongcheng.util.LocationUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wxkj.tongcheng.util.CodeUtil.CHOOSE_GROUP_SERVICE_TYPE;

/**
 * @author Liu haijun
 * @create 2018/10/9 0009
 * @Describe 团购活动的适配器
 */
public class GroupCommodityAdapter extends RecyclerView.Adapter implements OnBannerListener {

    /** 数据源 */
    private GroupTitleBean groupTitleBean;
    private List<GroupTitleBean.TeambuyBean> list = new ArrayList<>();
    private Context context;
    /** 当前位置信息 */
    private Location location;
    private List<Fragment> serviceTypeFragmentList = new ArrayList();
    private GroupFragment fragment;
    private NoTitleViewPagerAdapter adapter;


    public GroupCommodityAdapter(Context context, GroupTitleBean groupTitleBean,
                                 Location location) {
        this.context = context;
        this.location = location;
        this.groupTitleBean = groupTitleBean;
    }

    public void setGroupTitleBean(GroupFragment fragment, GroupTitleBean groupTitleBean, int page) {
        this.fragment = fragment;
        this.groupTitleBean = groupTitleBean;
        if (page == 0) {
            list.clear();
        }
        list.addAll(groupTitleBean.getTeambuy());
        notifyDataSetChanged();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void setLocation(Location location) {
        this.location = location;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType != -1) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_group_commodity, parent, false);
            return new MyViewHolder(view);
        } else {//头部
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_group_title, parent, false);
            return new TitleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (groupTitleBean == null) {
            return;
        }
        if (holder instanceof MyViewHolder) {//正文内容
            MyViewHolder vh = (MyViewHolder) holder;
            List<GroupTitleBean.TeambuyBean> list = groupTitleBean.getTeambuy();
            if (list == null || list.size() == 0) {
                return;
            }
            GroupTitleBean.TeambuyBean teambuyBean = list.get(position - 1);
            RequestOptions options = new RequestOptions().transform(new CenterCrop());
            options.error(R.drawable.load_failure);
            options.placeholder(R.drawable.loading);
            Glide.with(context).load(teambuyBean.getGoods_pic())
                    .apply(options)
                    .into(vh.itemGroupCommodityPic);
            //团购名称
            vh.itemGroupCommodityName.setText(teambuyBean.getTeambuy_name());
            //团购价格
            vh.itemGroupCommodityTeambuy.setText("¥" + AmountUtil.changeF2Y(teambuyBean.getPrice_teambuy()));
            //普通价格和购买数量
            vh.itemGroupCommodityCurrent.setText("单买价" + AmountUtil.changeF2Y(teambuyBean.getPrice_current())
                    + "  已拼" + teambuyBean.getNum_sold());
            //距离
            String distance = "距您100.00km";
            if (location != null) {
                //获取当前纬度
                double lat = location.getLatitude();
                //获取当前经度
                double lng = location.getLongitude();
                String baidu_pos = teambuyBean.getBaidu_pos();
                String[] split = baidu_pos.split(",");
                //商品经度
                double lat1 = Double.parseDouble(split[0]);
                //商品纬度
                double lng1 = Double.parseDouble(split[1]);
                //计算当前位置和商品的距离
                double points = LocationUtil.distanceOfTwoPoints(lat1, lng1, lat, lng);
                distance = "距您" + points + "km";
            }
            vh.itemGroupCommodityDistance.setText(distance);
            //点击立即抢购
            vh.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, GroupGoodsDetailActivity.class);
                intent.putExtra("goods_id", teambuyBean.getGoods_id());
                context.startActivity(intent);
            });

        }

        if (holder instanceof TitleHolder) {//标题
            TitleHolder vh = (TitleHolder) holder;
            List<GroupTitleBean.BannerBean> banner = groupTitleBean.getBanner();
            if (banner == null) {
                return;
            }
            initBanner(vh.mBanner, banner);

            List<GroupTitleBean.TypeBean> type = groupTitleBean.getType();
            if (type != null) {
                if (adapter == null) {
                    initServiceType(vh.groupRecyclerViewClassification, vh.indicator, type);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
            //搜索
            vh.groupInput.setOnClickListener(v ->
                    context.startActivity(new Intent(context, GroupSearchActivity.class))
            );
            vh.groupGoods.setOnClickListener(v -> {
                        Intent intent = new Intent(context, GroupSearchActivity.class);
                        intent.putExtra("goods_tp", 0);
                        context.startActivity(intent);
                    }
            );
            vh.groupService.setOnClickListener(v -> {
                Intent intent = new Intent(context, GroupSearchActivity.class);
                intent.putExtra("goods_tp", 1);
                context.startActivity(intent);
            });

        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return -1;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (groupTitleBean == null) {
            return 0;
        }
        return list.size() + 1;
    }

    /**
     * 初始化banner
     *
     * @param bannerList
     */
    private void initBanner(Banner banner, List<GroupTitleBean.BannerBean> bannerList) {
        if (null != banner) banner.releaseBanner();
        List<String> imageList = new ArrayList<>();
        if (bannerList.size() == 0) {  //banner是空
            return;
        }
        for (GroupTitleBean.BannerBean bean : bannerList) {
            imageList.add(bean.getAd_pic());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //指示器居中
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(imageList);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //设置banner item点击事件
        banner.setOnBannerListener(this);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    /**
     * Banner点击事件
     *
     * @param position
     */
    @Override
    public void OnBannerClick(int position) {

    }

    /**
     * 初始化服务类型
     */
    private void initServiceType(ViewPager type_vp, XIndicator indicator,
                                 List<GroupTitleBean.TypeBean> typeList) {
        List<CityBannerBean.TypeBean> type = new ArrayList<>();
        for (int i = 0; i < typeList.size(); i++) {
            CityBannerBean.TypeBean bean = new CityBannerBean.TypeBean();
            GroupTitleBean.TypeBean typeBean = typeList.get(i);
            type.add(bean);
            bean.setShow_img(typeBean.getShow_img());
            bean.setShow_order(typeBean.getShow_order());
            bean.setType_id(typeBean.getType_id());
            bean.setType_name(typeBean.getType_name());
        }

        if (null != type && type.size() != 0) {
            type_vp.removeAllViewsInLayout();
            int typeListSize = getTypeListSize(typeList);
            indicator.setIndicatorCount(typeListSize);
            serviceTypeFragmentList.clear();
            for (int i = 0; i < typeListSize; i++) {
                List<CityBannerBean.TypeBean> list = new ArrayList<>();
                ServiceTypeFragment fragment = new ServiceTypeFragment();
                Bundle bundle = new Bundle();
                if (i == typeListSize - 1)
                    list.addAll(type.subList(i * 10, type.size()));
                if (i < typeListSize - 1)
                    list.addAll(type.subList(i * 10, i * 10 + 10));
                bundle.putSerializable("list", (Serializable) list);
                bundle.putInt("code", CHOOSE_GROUP_SERVICE_TYPE);
                fragment.setArguments(bundle);
                serviceTypeFragmentList.add(fragment);

            }

            adapter = new NoTitleViewPagerAdapter(fragment.getChildFragmentManager(),
                    serviceTypeFragmentList);
            type_vp.setAdapter(adapter);
            indicator.setUpViewPager(type_vp);
        }
    }


    private int getTypeListSize(List<GroupTitleBean.TypeBean> list) {
        int size = list.size();
        int i = size / 10;
        if (size % 10 == 0)
            return i;
        else
            return i + 1;
    }

    /**
     * 团购头部
     */
    class TitleHolder extends RecyclerView.ViewHolder {
        /** 标题 */
        @BindView(R.id.group_title_view)
        LinearLayout views;
        @BindView(R.id.group_banner)
        Banner mBanner;
        /** 二人拼团 */
        @BindView(R.id.group_two)
        LinearLayout groupTwo;
        /** 多人拼团 */
        @BindView(R.id.group_more)
        LinearLayout groupMore;
        /** 拼商品 */
        @BindView(R.id.group_goods)
        LinearLayout groupGoods;
        /** 拼服务 */
        @BindView(R.id.group_service)
        LinearLayout groupService;
        /** 消息图标 */
        @BindView(R.id.group_msg)
        ImageView groupMsg;
        /** 分类的列表 */
        @BindView(R.id.group_recyclerView_classification)
        ViewPager groupRecyclerViewClassification;
        @BindView(R.id.group_indicator)
        XIndicator indicator;
        /** 输入框 */
        @BindView(R.id.group_input)
        LinearLayout groupInput;

        public TitleHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 团购活动数据
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_group_commodity_pic)
        ImageView itemGroupCommodityPic;
        @BindView(R.id.item_group_commodity_name)
        TextView itemGroupCommodityName;
        @BindView(R.id.item_group_commodity_teambuy)
        TextView itemGroupCommodityTeambuy;
        @BindView(R.id.item_group_commodity_current)
        TextView itemGroupCommodityCurrent;
        @BindView(R.id.item_group_commodity_distance)
        TextView itemGroupCommodityDistance;
        @BindView(R.id.item_group_commodity_buy)
        LinearLayout itemGroupCommodityBuy;


        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
