package com.wxkj.tongcheng.ui.fragment.mine.mine;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.MineBottomAdapter;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.listener.IClickItemListener;
import com.wxkj.tongcheng.statuslayout.HomeBaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.user.complaint.ComplaintActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.mycollect.MyCollectActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mywallet.MyWalletActiviyty;
import com.wxkj.tongcheng.ui.activity.mine.user.setting.SettingActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.userinfo.userinfo.UserInfoActivity;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by cheng on 2018/10/8.
 */

public class MineFragment extends HomeBaseFragment<MinePresenter> implements MineView, View.OnClickListener, IClickItemListener {
    private RecyclerView bottom_rv;
    private ImageView user_head_img,wechat_img;
    private TextView user_name_text;
    private LinearLayout not_spread_layout,is_spread_layout,user_info_layout,setting_layout;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.mine_fragment_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected boolean isImmersionBarEnabled() {
        return false;
    }

    @Override
    protected boolean initEnventBus() {
        return true;
    }

    @Override
    protected MinePresenter initPresenter() {
        return new MinePresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void initView() {
        super.initView();
        //设置底部功能区适配器
        setBottomAdapter();
        //适配个人信息
        setUserInfo();
    }

    @Override
    protected void setListener() {
        super.setListener();
        user_info_layout.setOnClickListener(this);
        setting_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info_layout:  //用户信息
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.setting_layout:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }
    }

    /**
     * 适配个人信息
     */
    private void setUserInfo() {
        Glide.with(this).load(SPUtil.getInstance(getContext()).getStringByKey("head_portrait"))
                .apply(RequestOptions.circleCropTransform().error(R.drawable.placehoderlogo_3x)).into(user_head_img);
        user_name_text.setText(SPUtil.getInstance(getContext()).getStringByKey("user_name"));
//        1为推广员 0为普通会员
        int spread_fg = SPUtil.getInstance(getContext()).getIntByKey("spread_fg");
        not_spread_layout.setVisibility(spread_fg==0?View.VISIBLE:View.GONE);
        is_spread_layout.setVisibility(spread_fg==0?View.GONE:View.VISIBLE);
        wechat_img.setVisibility(TextUtils.isEmpty(SPUtil.getInstance(getContext()).getStringByKey("user_wxid"))?View.GONE:View.VISIBLE);
    }

    /**
     * 设置底部功能区适配器
     */
    private void setBottomAdapter() {
        String[] name={"我的钱包","我的发布","我的收藏","订单核销","邀请商家",
                "邀请店主","我的认证","企业认证","我的优惠券","投诉建议","关于我们","设置"};
        int[] icon={R.drawable.user_wallet,R.drawable.user_release,R.drawable.user_collection,R.drawable.user_chack,R.drawable.user_yqsj,R.drawable.user_shopkeeper,
                R.drawable.user_authentication,R.drawable.user_qyrz,R.drawable.user_coupon,R.drawable.user_complaint,R.drawable.user_our,R.drawable.user_set};
        bottom_rv.setLayoutManager(new GridLayoutManager(getContext(),4,LinearLayoutManager.VERTICAL,false));
        MineBottomAdapter bottomAdapter = new MineBottomAdapter(getContext(),icon,name);
        bottom_rv.setAdapter(bottomAdapter);
        bottomAdapter.setClickItemListener(this);
    }

    /**
     * 底部功能区item 点击事件监听
     * @param position
     */
    @Override
    public void clickItemListener(int position) {
        Class<?> cls = getClassByPosition(position);
        if (null==cls) return;
        startActivity(new Intent(getContext(),cls));
    }

    public Class<?> getClassByPosition(int position){
        switch (position){
            case 0:
                return MyWalletActiviyty.class;
            case 2:
                return MyCollectActivity.class;
            case 9:
                return ComplaintActivity.class;
            default:
                return null;
        }
    }

    /**
     * 接收修改昵称成功的消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CHANGE_USER_NAME_SUCCESS:  //修改昵称成功
                user_name_text.setText(bean.getName());
                break;
            case CodeUtil.CHANGE_HEAD_SUCCESS:  //修改头像成功
                Glide.with(this).load(bean.getName())
                        .apply(RequestOptions.circleCropTransform().error(R.drawable.placehoderlogo_3x)).into(user_head_img);
                break;
        }
    }

    private void findview() {
        bottom_rv=findViewById(R.id.bottom_rv);
        user_head_img=findViewById(R.id.user_head_img);
        user_name_text=findViewById(R.id.user_name_text);
        not_spread_layout=findViewById(R.id.not_spread_layout);
        is_spread_layout=findViewById(R.id.is_spread_layout);
        user_info_layout=findViewById(R.id.user_info_layout);
        wechat_img=findViewById(R.id.wechat_img);
        setting_layout=findViewById(R.id.setting_layout);
    }
}
