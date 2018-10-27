package com.wxkj.tongcheng.ui.activity.mine.login.forgetpwd;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.RegisterBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginPresenter;
import com.wxkj.tongcheng.ui.activity.mine.login.LoginView;

import java.security.PrivilegedExceptionAction;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by cheng on 2018/10/10.
 */

public class ForgetPwdActivity extends MvpBaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    private TextView change_pwd_text;
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.code_edit)
    EditText code_edit;
    @BindView(R.id.pwd_edit)
    EditText pwd_edit;
    @BindView(R.id.repeat_pwd_edit)
    EditText repeat_pwd_edit;
    @BindView(R.id.get_code_text)
    TextView get_code_text;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.forget_pwd_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.register_title_layout;
    }

    @Override
    protected String titleString() {
        return "忘记密码";
    }

    @Override
    protected void initData() {
        super.initData();
        change_pwd_text=findViewById(R.id.register_text);
    }

    @Override
    protected void setListener() {
        super.setListener();
        get_code_text.setOnClickListener(this);
        change_pwd_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code_text:  //获取验证码
                getCode();
                break;
            case R.id.register_text:  //完成
                RegisterBean bean = new RegisterBean();
                bean.setPhone(phone_edit.getText().toString());
                bean.setCode(code_edit.getText().toString());
                bean.setPwd(pwd_edit.getText().toString());
                bean.setPwd2(repeat_pwd_edit.getText().toString());
                presenter.resetPwd(bean);
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
    public void loginSuccess(UserInfo userInfo) {  //这里不用

    }

    @Override
    public void getCodeSuccess(String s,String code) {  //获取验证码成功

    }

    @Override
    public void registerSuccess() {  //重置密码成功
        t("重置密码成功");
        finish();
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone = phone_edit.getText().toString();
        if (TextUtils.isEmpty(phone)||!phone.startsWith("1")){
            t("请输入正确的手机号码");
            return;
        }
        presenter.getCode(phone,3);
        get_code_text.setClickable(false);
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                try {
                    int time=60;
                    while (time>0){
                        emitter.onNext(time);
                        Thread.sleep(1000);
                        time--;
                    }
                    emitter.onComplete();
                }catch (Exception e){

                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        get_code_text.setText(("重新获取")+integer+"s");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        get_code_text.setText("重新获取");
                        get_code_text.setClickable(true);
                    }
                });
    }
}
