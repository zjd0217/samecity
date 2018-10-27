package com.wxkj.tongcheng.ui.activity.mine.login.login;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.login.historylogin.HistoryPwdLoginActivity;
import com.wxkj.tongcheng.ui.activity.mine.login.phonepwdlogin.PhonePwdLoginActivity;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 登录页面
 * Created by cheng on 2018/10/7.
 */

public class LoginActicity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.phone_login_text)
    TextView phone_login_text;
    @BindView(R.id.user_agree_text)
    TextView user_agree_text;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.login_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "";
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected void initView() {
        super.initView();
        user_agree_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
        user_agree_text.getPaint().setAntiAlias(true);
    }

    @Override
    protected void setListener() {
        super.setListener();
        phone_login_text.setOnClickListener(this);
        user_agree_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.phone_login_text:
                //没有历史用户
                if (SPUtil.getInstance(this).getIntByKey("user_id")==0)
                    startActivity(new Intent(this, PhonePwdLoginActivity.class));
                else   //有历史用户
                    startActivity(new Intent(this, HistoryPwdLoginActivity.class));
                break;
            case R.id.user_agree_text:  //用户协议

                break;
        }
    }

    /**
     * 接收登录成功的消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.LOGIN_SUCCESS:  //登录成功
                finish();
                break;
        }
    }
}
