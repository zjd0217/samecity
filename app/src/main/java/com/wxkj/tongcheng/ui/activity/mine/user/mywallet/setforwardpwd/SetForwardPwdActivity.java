package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.setforwardpwd;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
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
 * 设置提现密码 页面
 * Created by cheng on 2018/10/16.
 */

public class SetForwardPwdActivity extends MvpBaseActivity<PwdPresenter> implements PwdView, View.OnClickListener {
    private EditText phone_edit,code_edit,pwd_edit,repeat_pwd_edit;
    private TextView get_code_text,sure_text;

    @Override
    protected PwdPresenter initPresenter() {
        return new PwdPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.set_forward_pwd_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return getIntent().getStringExtra("title");
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
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
    public void getCodeSuccess(String code) {
        t("验证码获取成功");
        code_edit.setText(code);
    }

    @Override
    public void setPwdSuccess() { //设置提现密码成功
        t("操作成功");
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.SET_FORWARD_PWD_SUCCESS);
        EventBus.getDefault().post(bean);
        this.finish();
    }

    @Override
    protected void setListener() {
        super.setListener();
        get_code_text.setOnClickListener(this);
        sure_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code_text:  //获取验证码
                getCode();
                break;
            case R.id.sure_text:  //确定
                sure();
                break;
        }
    }

    /**
     * 确定
     */
    private void sure() {
        String code = code_edit.getText().toString().trim();
        String pwd = pwd_edit.getText().toString().trim();
        String repeat_pwd = repeat_pwd_edit.getText().toString().trim();
        if (code.length()!=6){
            t("请输入6位验证码");
            return;
        }
        if (pwd.length()!=6){
            t("请输入6位数字密码");
            return;
        }
        if (!pwd.equals(repeat_pwd)){
            t("两次密码不一致");
            return;
        }
        presenter.setPwd(code,pwd);
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

    private void findview() {
        phone_edit=findViewById(R.id.phone_edit);
        code_edit=findViewById(R.id.code_edit);
        pwd_edit=findViewById(R.id.pwd_edit);
        repeat_pwd_edit=findViewById(R.id.repeat_pwd_edit);
        get_code_text=findViewById(R.id.get_code_text);
        sure_text=findViewById(R.id.sure_text);
    }

    private Disposable subscribe;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != subscribe)
            subscribe.dispose();
    }
}
