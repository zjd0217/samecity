package com.wxkj.tongcheng.ui.activity.mine.login.historylogin;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginPresenter;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginView;
import com.wxkj.tongcheng.ui.activity.mine.login.forgetpwd.ForgetPwdActivity;
import com.wxkj.tongcheng.ui.activity.mine.login.phonepwdlogin.PhonePwdLoginActivity;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.LoginMoreFragmentDialog;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 历史账号密码登录
 * Created by cheng on 2018/10/10.
 */

public class HistoryPwdLoginActivity extends MvpBaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    @BindView(R.id.user_head_img)
    ImageView user_head_img;
    @BindView(R.id.user_name_text)
    TextView user_name_text;
    @BindView(R.id.forget_pwd_text)
    TextView forget_pwd_text;
    @BindView(R.id.login_text)
    TextView login_text;
    @BindView(R.id.pwd_edit)
    EditText pwd_edit;
    @BindView(R.id.code_login_text)
    TextView code_login_text;
    TextView more_text;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.history_pwd_login_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.history_pwd_login_title_layout;
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
    protected void initData() {
        super.initData();
        more_text=findViewById(R.id.more_text);
    }

    @Override
    protected void initView() {
        super.initView();
        //适配用户头像昵称
        Glide.with(this).load(SPUtil.getInstance(this).getStringByKey("head_portrait"))
                .apply(RequestOptions.circleCropTransform().error(R.drawable.placehoderlogo_3x)).into(user_head_img);
        user_name_text.setText(SPUtil.getInstance(this).getStringByKey("user_name"));
    }

    @Override
    protected void setListener() {
        super.setListener();
        forget_pwd_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
        code_login_text.setOnClickListener(this);
        more_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_pwd_text:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.login_text:
                String phone = SPUtil.getInstance(this).getStringByKey("phone");
                if (TextUtils.isEmpty(phone)||!phone.startsWith("1")){
                    t("该账号的手机号有误");
                    SPUtil.getInstance(this).removeByKey("user_id");
                    finish();
                    return;
                }
                presenter.phoneLogin(phone,pwd_edit.getText().toString());
                break;
            case R.id.code_login_text:
                startActivity(new Intent(this,HistoryCodeLoginActivity.class));
                break;
            case R.id.more_text:  //更多
                new LoginMoreFragmentDialog().show(getSupportFragmentManager(),"LoginMoreFragmentDialog");
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
    public void loginSuccess(UserInfo userInfo) {
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
    public void registerSuccess() { //这里不用

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
            case CodeUtil.LOGIN_CHOOSE_MORE:  //选择更多
                //0切换账户  1注册
                int type = bean.getType();
                Intent intent = new Intent(this, PhonePwdLoginActivity.class);
                intent.putExtra("type",type);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
