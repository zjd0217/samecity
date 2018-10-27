package com.wxkj.tongcheng.ui.activity.mine.login.historylogin;

import android.content.Context;
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
 * 历史账号验证码登录
 * Created by cheng on 2018/10/10.
 */

public class HistoryCodeLoginActivity extends MvpBaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {
    @BindView(R.id.user_head_img)
    ImageView user_head_img;
    @BindView(R.id.user_name_text)
    TextView user_name_text;
    @BindView(R.id.login_text)
    TextView login_text;
    @BindView(R.id.code_edit)
    EditText code_edit;
    @BindView(R.id.get_code_text)
    TextView get_code_text;
    @BindView(R.id.pwd_login_text)
    TextView pwd_login_text;
    private Disposable disposable;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.history_code_login_content_layout)
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
        get_code_text.setOnClickListener(this);
        pwd_login_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code_text:  //获取验证码
                getCode();
                break;
            case R.id.login_text:  //登录
                presenter.codeLogin(SPUtil.getInstance(this).getStringByKey("phone"),code_edit.getText().toString());
                break;
            case R.id.pwd_login_text:  //密码登录
                finish();
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
    public void getCodeSuccess(String s,String code) {
        t(s);
    }

    @Override
    public void registerSuccess() { //这里不用

    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone = SPUtil.getInstance(this).getStringByKey("phone");
        if (TextUtils.isEmpty(phone)||!phone.startsWith("1")){
            t("该账号的手机号有误");
            SPUtil.getInstance(this).removeByKey("user_id");
            finish();
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
