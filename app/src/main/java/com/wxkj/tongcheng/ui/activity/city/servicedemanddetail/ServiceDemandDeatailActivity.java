package com.wxkj.tongcheng.ui.activity.city.servicedemanddetail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.CityAttrAdapter;
import com.wxkj.tongcheng.adapter.CityYouLikeAdapter;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.ServiceDemandDetailBean;
import com.wxkj.tongcheng.listener.IClickItemListener;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ShareFragmentDialog;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 服务 需求详情页面
 * Created by cheng on 2018/10/9.
 */

public class ServiceDemandDeatailActivity extends MvpBaseActivity<ServicePresenter> implements ServiceView, OnRetryListener, OnBannerListener, IClickItemListener, View.OnClickListener {
    private Banner banner;
    private RecyclerView you_like_rv,attr_rv;
    private ImageView shop_img,shop_phone_text,share_img,collect_img;
    private LinearLayout you_like_layout,shop_layout,contact_phone_layout,collect_layout;
    private TextView title_text,time_text,look_num_text,classify_text,type_text,addr_text,shop_name_text,
            shop_num_text,shop_time_text,shop_addr_text,describe_text,collect_text;

    private int type;
    private String contact_phone="",shop_phone=""; //联系电话 商家电话
    private boolean byUser=false;

    @Override
    protected ServicePresenter initPresenter() {
        return new ServicePresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.service_demand_detail_content_layout)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    public void onRetry() {
        if (null!=presenter){
            statusLayoutManager.showLoading();
            byUser=false;
            getIntentType();
        }
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.city_service_detail_title_layout;
    }

    @Override
    protected String titleString() {
        return "详情";
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void initView() {
        super.initView();
        //获取传过来的type
        getIntentType();
    }

    @Override
    protected void setListener() {
        super.setListener();
        share_img.setOnClickListener(this);
        shop_phone_text.setOnClickListener(this);
        contact_phone_layout.setOnClickListener(this);
        collect_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_img:  //分享
                new ShareFragmentDialog().show(getSupportFragmentManager(),"ShareFragmentDialog");
                break;
            case R.id.shop_phone_text:  //拨打商家电话
                callPhone(shop_phone);
                break;
            case R.id.contact_phone_layout:
                callPhone(contact_phone);
                break;
            case R.id.collect_layout:  //收藏
                byUser=true;
                collect();
                break;
        }
    }

    /**
     * 收藏
     */
    private void collect() {
        loginOrNot("收藏需要登录哦~");
        String s = collect_text.getText().toString();
        if (s.equals("收藏"))
            presenter.collect(type);
        else if (s.equals("取消收藏"))
            presenter.deleteCollect();
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser,msg);
    }

    @Override
    public int getServiceId() {
        return getIntent().getIntExtra("id",0);
    }

    @Override
    public void getDetailSuccess(ServiceDemandDetailBean detailBean) {
        List<ServiceDemandDetailBean.PicsBean> pics = detailBean.getPics();
        if (null!=pics&&pics.size()!=0){
            initBanner(pics);
        }else
            banner.setVisibility(View.GONE);
        //设置猜你喜欢适配器
        setYouLikeAdapter(detailBean.getInfo_like());
        //适配扩展属性适配器
        setAttrAdapter(detailBean.getAttr());
        //适配其他数据
        setOtherData(detailBean.getService());

        statusLayoutManager.showContent();
    }

    @Override
    public void collectSuccess(String s) {  //收藏成功
        t(s);
        collect_text.setText("取消收藏");
        collect_img.setImageResource(R.drawable.bottom_collected);
    }

    @Override
    public void deleteCollectSuccess(String s) {  //取消收藏成功
        t(s);
        collect_text.setText("收藏");
        collect_img.setImageResource(R.drawable.bottom_collection);
    }

    /**
     * 适配其他数据
     * @param bean
     */
    private void setOtherData(ServiceDemandDetailBean.ServiceBean bean) {
        this.contact_phone=bean.getContact_phone();
        title_text.setText(bean.getInfo_title());
        time_text.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(bean.getTime_setup())));
        look_num_text.setText(bean.getVisit_times()+"");
        classify_text.setText(bean.getPtype_name()+"-"+bean.getType_name());
        type_text.setText(type==0?"提供服务":"提供需求");
        addr_text.setText(bean.getUser_addr());
        String shop_name = bean.getShop_name();
        if (TextUtils.isEmpty(shop_name)){  //店铺名称是空
            shop_layout.setVisibility(View.GONE);
        }else {
            this.shop_phone=bean.getShop_num();
            shop_layout.setVisibility(View.VISIBLE);
            shop_name_text.setText(shop_name);
            shop_num_text.setText("商品数量："+bean.getShop_num()+"  已拼"+bean.getSold_all()+"件");
            shop_time_text.setText("营业时间："+bean.getTime_on_work());
            shop_addr_text.setText(bean.getShop_pos());
            Glide.with(this).load(bean.getShop_logo()).apply(new RequestOptions().transform(new CenterCrop())).into(shop_img);
        }
        describe_text.setText(bean.getInfo_describe());
        //是否收藏
        int collection_id = bean.getCollection_id();
        collect_img.setImageResource(collection_id==0?R.drawable.bottom_collection:R.drawable.bottom_collection);
        collect_text.setText(collection_id==0?"收藏":"取消收藏");
    }

    /**
     * 设置 扩展属性适配器
     * @param attr
     */
    private void setAttrAdapter(List<ServiceDemandDetailBean.AttrBean> attr) {
        if (null==attr||attr.size()==0)
            attr_rv.setVisibility(View.GONE);
        else {
            attr_rv.setVisibility(View.VISIBLE);
            attr_rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            attr_rv.setAdapter(new CityAttrAdapter(this,attr));
        }
    }

    /**
     * 设置猜你喜欢适配器
     * @param info_like
     */
    private void setYouLikeAdapter(List<ServiceDemandDetailBean.InfoLikeBean> info_like) {
        if (null==info_like||info_like.size()==0){
            you_like_layout.setVisibility(View.GONE);
            you_like_rv.setVisibility(View.GONE);
        }else {
            you_like_layout.setVisibility(View.VISIBLE);
            you_like_rv.setVisibility(View.VISIBLE);
            you_like_rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            CityYouLikeAdapter cityYouLikeAdapter = new CityYouLikeAdapter(this, info_like);
            you_like_rv.setAdapter(cityYouLikeAdapter);
            cityYouLikeAdapter.setClickItemListener(this);
        }
    }

    /**
     * 猜你喜欢 item点击监听
     * @param id
     */
    @Override
    public void clickItemListener(int id) {
        Intent intent=new Intent(this, ServiceDemandDeatailActivity.class);
        intent.putExtra("type",type);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    /**
     * 初始化banner
     * @param pics
     */
    private void initBanner(List<ServiceDemandDetailBean.PicsBean> pics) {
        if (null!=banner) banner.releaseBanner();
        List<String> imageList=new ArrayList<>();
        for (ServiceDemandDetailBean.PicsBean bean:pics){
            imageList.add(bean.getPic_path());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
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

    @Override
    public void OnBannerClick(int position) {

    }


    /**
     * 拨打商家电话
     */
    private void callPhone(String phone) {
        if (null==phone)
            t("电话号码有误，拨打失败");
        else {
            new RTPermission.Builder()
                    .permissions(Manifest.permission.CALL_PHONE)
                    .start(this, new OnPermissionResultListener() {
                        @Override
                        public void onAllGranted(String[] allPermissions) {
                            //直接拨打电话
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            Uri data = Uri.parse("tel:"+phone);
                            intent.setData(data);
                            startActivity(intent);
                        }

                        @Override
                        public void onDeined(String[] dinedPermissions) {
                            t("拨打电话权限获取失败，无法拨打电话");
                        }
                    });
        }
    }

    /**
     * 获取传过来的type
     */
    private void getIntentType() {
        type = getIntent().getIntExtra("type", -1);
        if (type ==-1){
            statusLayoutManager.showError();
            return;
        }
        //0是服务 1是需求
        if (type ==0)
            presenter.getServiceDetail();
        else
            presenter.getDemandDetail();
    }

    /**
     * 接收选择分享平台的的消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CHOOSE_SHARE_TYPE:  //选择分享平台
                //1 QQ  2 朋友圈  以此类推
                int type = bean.getType();
                Log.e("qwer",type+"");
                break;
        }
    }


    private void findview() {
        banner=findViewById(R.id.banner);
        you_like_rv=findViewById(R.id.you_like_rv);
        you_like_layout=findViewById(R.id.you_like_layout);
        attr_rv=findViewById(R.id.attr_rv);
        shop_img=findViewById(R.id.shop_img);
        shop_phone_text=findViewById(R.id.shop_phone_text);
        shop_layout=findViewById(R.id.shop_layout);
        title_text=findViewById(R.id.title_text);
        time_text=findViewById(R.id.time_text);
        look_num_text=findViewById(R.id.look_num_text);
        classify_text=findViewById(R.id.classify_text);
        type_text=findViewById(R.id.type_text);
        addr_text=findViewById(R.id.addr_text);
        shop_name_text=findViewById(R.id.shop_name_text);
        shop_num_text=findViewById(R.id.shop_num_text);
        shop_time_text=findViewById(R.id.shop_time_text);
        shop_addr_text=findViewById(R.id.shop_addr_text);
        describe_text=findViewById(R.id.describe_text);
        share_img=findViewById(R.id.share_img);
        contact_phone_layout=findViewById(R.id.contact_phone_layout);
        collect_layout=findViewById(R.id.collect_layout);
        collect_img=findViewById(R.id.collect_img);
        collect_text=findViewById(R.id.collect_text);
    }
}
