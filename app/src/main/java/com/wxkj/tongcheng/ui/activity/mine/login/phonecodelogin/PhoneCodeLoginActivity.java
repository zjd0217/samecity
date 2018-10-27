package com.wxkj.tongcheng.ui.activity.mine.login.phonecodelogin;

import android.content.Context;
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
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 手机号 验证码登录
 * Created by cheng on 2018/10/13.
 */

public class PhoneCodeLoginActivity extends MvpBaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.login_text)
    TextView login_text;
    @BindView(R.id.user_agree_text)
    TextView user_agree_text;
    @BindView(R.id.code_edit)
    EditText code_edit;
    @BindView(R.id.get_code_text)
    TextView get_code_text;

    private Disposable disposable;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.phone_code_login_content_layout)
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
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
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
        get_code_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
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
    public void loginSuccess(UserInfo userInfo) {  //登陆成功
        SPUtil.getInstance(this).saveLoginInfo(userInfo);
        EventBusBean bean=new EventBusBean();
        bean.setCode(CodeUtil.LOGIN_SUCCESS);
        EventBus.getDefault().post(bean);
        finish();
    }

    @Override
    public void getCodeSuccess(String s,String code) { //获取验证码成功
        t(s);
    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code_text: //获取验证码
                getCode();
                break;
            case R.id.login_text:  //登录
                presenter.codeLogin(phone_edit.getText().toString(),code_edit.getText().toString());
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone =phone_edit.getText().toString();
        if (phone.length()!=11||!phone.startsWith("1")){
            t("请输入正确的手机号");
            return;
        }
        presenter.getCode(phone,4);
        get_code_text.setClickable(false);

        disposable = Flowable.intervalRange(1, 60, 0, 1, TimeUnit.SECONDS)
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
        if (null!=disposable)
            disposable.dispose();
    }
}
