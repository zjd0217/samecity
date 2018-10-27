package com.wxkj.tongcheng.ui.activity.mine.login.register;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.RegisterBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginPresenter;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginView;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 注册页面
 * Created by cheng on 2018/10/9.
 */

public class RegisterActivity extends MvpBaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    TextView register_text;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.code_edit)
    EditText code_edit;
    @BindView(R.id.get_code_text)
    TextView get_code_text;
    @BindView(R.id.pwd_edit)
    EditText pwd_edit;
    @BindView(R.id.repeat_pwd_edit)
    EditText repeat_pwd_edit;
    @BindView(R.id.other_phone_edit)
    EditText other_phone_edit;
    private Disposable subscribe;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.register_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.register_title_layout;
    }

    @Override
    protected String titleString() {
        return "注册";
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    protected void initData() {
        super.initData();
        register_text = findViewById(R.id.register_text);
    }

    @Override
    protected void setListener() {
        super.setListener();
        get_code_text.setOnClickListener(this);
        register_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code_text: //获取验证码
                getCode();
                break;
            case R.id.register_text:  //注册
                register();
                break;
        }
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(true, msg);
    }

    @Override
    public void loginSuccess(UserInfo userInfo) {  //这里不用

    }

    @Override
    public void getCodeSuccess(String s, String code) {  //获取验证按成功
        showErrorMsg(true, s);
        code_edit.setText(code);
    }

    @Override
    public void registerSuccess() {  //注册成功
        showErrorMsg(true, "注册成功");
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.REGISTER_SUCCESS);
        bean.setPhone(phone_edit.getText().toString());
        bean.setPwd(pwd_edit.getText().toString());
        EventBus.getDefault().post(bean);
        finish();
    }

    /**
     * 注册
     */
    private void register() {
        RegisterBean bean = new RegisterBean();
        bean.setPhone(phone_edit.getText().toString());
        bean.setCode(code_edit.getText().toString());
        bean.setPwd(pwd_edit.getText().toString());
        bean.setPwd2(repeat_pwd_edit.getText().toString());
        bean.setOtherPhone(other_phone_edit.getText().toString());
        presenter.register(bean);
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone = phone_edit.getText().toString();
        if (phone.length() != 11 || !phone.startsWith("1")) {
            t("请输入正确的手机号码");
            return;
        }
        presenter.getCode(phone, 5);
        get_code_text.setClickable(false);

        subscribe = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        get_code_text.setText(("重新获取") + (60 - aLong) + "s");
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        get_code_text.setText("重新获取");
                        get_code_text.setClickable(true);
                    }
                })
                .subscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != subscribe)
            subscribe.dispose();
    }
}
