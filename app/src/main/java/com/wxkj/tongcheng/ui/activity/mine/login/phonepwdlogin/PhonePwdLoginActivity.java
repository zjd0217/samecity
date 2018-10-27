package com.wxkj.tongcheng.ui.activity.mine.login.phonepwdlogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginPresenter;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginView;
import com.wxkj.tongcheng.ui.activity.mine.login.forgetpwd.ForgetPwdActivity;
import com.wxkj.tongcheng.ui.activity.mine.login.phonecodelogin.PhoneCodeLoginActivity;
import com.wxkj.tongcheng.ui.activity.mine.login.register.RegisterActivity;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 账号密码登录页面
 * Created by cheng on 2018/10/9.
 */

public class PhonePwdLoginActivity extends MvpBaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    @BindView(R.id.register_text)
    TextView register_text;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.pwd_edit)
    EditText pwd_edit;
    @BindView(R.id.forget_pwd_text)
    TextView forget_pwd_text;
    @BindView(R.id.login_text)
    TextView login_text;
    @BindView(R.id.user_agree_text)
    TextView user_agree_text;
    @BindView(R.id.phone_code_login_text)
    TextView phone_code_login_text;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.phone_pwd_login_content_layout)
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
        int type = getIntent().getIntExtra("type", -1);
        if (type==1)
            startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    protected void setListener() {
        super.setListener();
        register_text.setOnClickListener(this);
        forget_pwd_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
        forget_pwd_text.setOnClickListener(this);
        phone_code_login_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_text:  //注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.forget_pwd_text:  //忘记密码
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.login_text:  //登录
                presenter.phoneLogin(phone_edit.getText().toString(),pwd_edit.getText().toString());
                break;
            case R.id.phone_code_login_text:  //手机号 验证码登录
                startActivity(new Intent(this, PhoneCodeLoginActivity.class));
                break;
        }
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(true,msg);
    }

    @Override
    public void loginSuccess(UserInfo userInfo) { //登录成功
        SPUtil.getInstance(this).saveLoginInfo(userInfo);
        EventBusBean bean=new EventBusBean();
        bean.setCode(CodeUtil.LOGIN_SUCCESS);
        EventBus.getDefault().post(bean);
        finish();
    }

    @Override
    public void getCodeSuccess(String s,String code) {  //这里不用

    }

    @Override
    public void registerSuccess() {  //这里不用

    }

    /**
     * 接收 注册成功的消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.REGISTER_SUCCESS:  //注册成功
                phone_edit.setText(bean.getPhone());
                pwd_edit.setText(bean.getPwd());
                break;
            case CodeUtil.LOGIN_SUCCESS:
                finish();
                break;
        }
    }
}
