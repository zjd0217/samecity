package com.wxkj.tongcheng.ui.activity.mine.user.changepwd;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.SPUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 修改密码页面
 * Created by cheng on 2018/10/15.
 */

public class ChangePwdActivity extends MvpBaseActivity<ChangePwdPresenter> implements ChangePwdView, View.OnClickListener {
    @BindView(R.id.phone_text)
    TextView phone_text;
    @BindView(R.id.code_edit)
    EditText code_edit;
    @BindView(R.id.get_code_text)
    TextView get_code_text;
    @BindView(R.id.pwd_edit)
    EditText pwd_edit;
    @BindView(R.id.repeat_pwd_edit)
    EditText repeat_pwd_edit;
    @BindView(R.id.finish_text)
    TextView finish_text;

    private Disposable subscribe;

    @Override
    protected ChangePwdPresenter initPresenter() {
        return new ChangePwdPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.change_pwd_content_layout)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "修改密码";
    }

    @Override
    protected void initView() {
        super.initView();
        phone_text.setText(SPUtil.getInstance(this).getStringByKey("phone"));
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        t(msg);
    }

    @Override
    public void getCodeSuccess(String s) {
        t("验证码获取成功");
        code_edit.setText(s);
    }

    @Override
    public void changePwdSuccess(String s) {  //修改密码成功
        t(s);
        finish();
    }

    @Override
    protected void setListener() {
        super.setListener();
        get_code_text.setOnClickListener(this);
        finish_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code_text:  //获取验证码
                getCode();
                break;
            case R.id.finish_text:  //完成
                changePwd();
                break;
        }
    }

    /**
     * 修改密码
     */
    private void changePwd() {
        String code = code_edit.getText().toString().trim();
        String pwd = pwd_edit.getText().toString().trim();
        String repeat_pwd = repeat_pwd_edit.getText().toString().trim();
        if (code.length()!=6){
            t("请输入6位验证码");
            return;
        }
        if (pwd.length()<6||repeat_pwd.length()<6){
            t("请输入6位以上密码");
            return;
        }
        if (!pwd.equals(repeat_pwd)){
            t("两次密码不一致");
            return;
        }
        presenter.changePwd(code,pwd);
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone = phone_text.getText().toString();
        if (phone.length() != 11 || !phone.startsWith("1")) {
            t("手机号有误，请重新登录");
            return;
        }
        presenter.getCode(phone);
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
