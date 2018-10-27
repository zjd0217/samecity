package com.wxkj.tongcheng.ui.activity.mine.user.setting;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.home.home.HomeActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.changephone.ChangePhoneActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.changepwd.ChangePwdActivity;
import com.wxkj.tongcheng.util.SPUtil;

/**
 * Created by cheng on 2018/10/14.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout change_phone_layout,change_pwd_layout;
    private TextView wechat_text,exit_login_text;
    private String user_wxid;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.setting_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "设置";
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void initView() {
        super.initView();
        user_wxid = SPUtil.getInstance(this).getStringByKey("user_wxid");
        wechat_text.setText(TextUtils.isEmpty(user_wxid)?"未绑定": user_wxid);
    }

    @Override
    protected void setListener() {
        super.setListener();
        exit_login_text.setOnClickListener(this);
        change_phone_layout.setOnClickListener(this);
        change_pwd_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exit_login_text:  //退出登录
                SPUtil.getInstance(this).removeByKey("token");
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                break;
            case R.id.change_phone_layout:  //更换手机号
                loginOrNot("更换手机号需要先登录");
                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.change_pwd_layout:  //修改密码
                loginOrNot("修改密码需要先登录");
                startActivity(new Intent(this, ChangePwdActivity.class));
                break;
        }
    }

    private void findview() {
        wechat_text=findViewById(R.id.wechat_text);
        exit_login_text=findViewById(R.id.exit_login_text);
        change_phone_layout=findViewById(R.id.change_phone_layout);
        change_pwd_layout=findViewById(R.id.change_pwd_layout);
    }
}
