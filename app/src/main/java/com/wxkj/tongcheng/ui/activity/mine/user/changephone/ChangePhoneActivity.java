package com.wxkj.tongcheng.ui.activity.mine.user.changephone;

import android.support.v4.app.Fragment;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.NoTitleViewPagerAdapter;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.fragment.mine.changephone.OntStepFragment;
import com.wxkj.tongcheng.ui.fragment.mine.changephone.TwoStepFragment;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.view.MyViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 更换手机号 页面
 * Created by cheng on 2018/10/14.
 */

public class ChangePhoneActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    MyViewPager viewPager;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.change_phone_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "更换手机号";
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
        setAdapter();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new OntStepFragment());
        fragmentList.add(new TwoStepFragment());
        viewPager.setAdapter(new NoTitleViewPagerAdapter(getSupportFragmentManager(),fragmentList));
    }

    /**
     * 接收 更换手机号 fragment 的消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CHANGE_PHONE_NEXT_STEP:  //下一步
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
