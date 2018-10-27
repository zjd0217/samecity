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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * 更换手机号 第二步
 * Created by cheng on 2018/10/14.
 */

public class TwoStepFragment extends MvpBaseFragment<ChangePhonePresenter> implements ChangePhoneView, View.OnClickListener {
    private EditText phone_edit,code_edit;
    private TextView get_code_text,finish_text;

    private Disposable subscribe;
    private int msgId;

    @Override
    protected ChangePhonePresenter initPresenter() {
        return new ChangePhonePresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.change_phone_two_step_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void setListener() {
        super.setListener();
        get_code_text.setOnClickListener(this);
        finish_text.setOnClickListener(this);
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
    public void getCodeSuccess(String s) { //获取验证码成功
        t("验证码获取成功");
        code_edit.setText(s);
    }

    @Override
    public void checkCodeSuccess(int msgId) {  //这里不用

    }

    @Override
    public void changePhoneSuccess() {  //更换手机号成功
        SPUtil.getInstance(getContext()).saveStringInfo("phone",phone_edit.getText().toString());
        t("更换手机号成功");
        this.getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code_text:  //获取验证码
                getCode();
                break;
            case R.id.finish_text:  //完成
                presenter.changePhone(phone_edit.getText().toString().trim(),code_edit.getText().toString(),msgId);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        String phone = phone_edit.getText().toString();
        if (phone.length() != 11 || !phone.startsWith("1")) {
            t("请输入正确的手机号");
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


    /**
     * 接收msgId
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.CHANGE_PHONE_NEXT_STEP:  //获取msgid
                msgId = bean.getType();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != subscribe)
            subscribe.dispose();
    }


    private void findview() {
        phone_edit=findViewById(R.id.phone_edit);
        code_edit=findViewById(R.id.code_edit);
        get_code_text=findViewById(R.id.get_code_text);
        finish_text=findViewById(R.id.finish_text);
    }
}
