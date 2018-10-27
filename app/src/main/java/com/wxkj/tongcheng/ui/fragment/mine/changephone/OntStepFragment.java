package com.wxkj.tongcheng.ui.fragment.mine.changephone;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 更换手机号 第一步
 * Created by cheng on 2018/10/14.
 */

public class OntStepFragment extends MvpBaseFragment<ChangePhonePresenter> implements ChangePhoneView, View.OnClickListener {
    private EditText code_edit;
    private TextView get_code_text,next_step_text;

    private Disposable subscribe;

    @Override
    protected ChangePhonePresenter initPresenter() {
        return new ChangePhonePresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.change_phone_one_step_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    public Context getcontext() {
        return getContext();
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
    public void checkCodeSuccess(int msgId) {  //验证 验证码成功
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.CHANGE_PHONE_NEXT_STEP);
        bean.setType(msgId);
        EventBus.getDefault().post(bean);
    }

    @Override
    public void changePhoneSuccess() {  //这里不用

    }

    @Override
    protected void setListener() {
        super.setListener();
        get_code_text.setOnClickListener(this);
        next_step_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code_text:  //获取验证码
                getCode();
                break;
            case R.id.next_step_text:  //下一步
                presenter.checkPhone(code_edit.getText().toString());
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone=SPUtil.getInstance(getContext()).getStringByKey("phone");
        if (phone.length() != 11 || !phone.startsWith("1")) {
            t("手机号有误，请重新登录");
            return;
        }
        presenter.getSmsCode(phone, 6);
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
        code_edit=findViewById(R.id.code_edit);
        get_code_text=findViewById(R.id.get_code_text);
        next_step_text=findViewById(R.id.next_step_text);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != subscribe)
            subscribe.dispose();
    }
}
