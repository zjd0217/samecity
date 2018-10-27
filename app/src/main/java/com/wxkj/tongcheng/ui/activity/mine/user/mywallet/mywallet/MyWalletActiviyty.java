package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mywallet;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.WalletNumBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mybankcard.MyBankCardAvtivity;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.putforward.PutForwardActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.putforwardrecord.PutForwardRecordActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.setforwardpwd.SetForwardPwdActivity;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.Util;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 我的钱包 页面
 * Created by cheng on 2018/10/16.
 */

public class MyWalletActiviyty extends MvpBaseActivity<MyWalletPresenter> implements MyWalletView, OnRetryListener, View.OnClickListener {
    private TextView put_forward_record_text,user_capital_text,lock_capital_text,get_capital_text,put_forward_text;
    private LinearLayout set_forward_pwd_layout,bank_card_layout;

//    private int user_capital=0,user_pay_pass=0; //可用金额  是否有设置支付密码 0没有
    private WalletNumBean walletNumBean;

    @Override
    protected MyWalletPresenter initPresenter() {
        return new MyWalletPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.my_wallet_content_layout)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    public void onRetry() {
        if (null!=presenter){
            statusLayoutManager.showLoading();
            presenter.getWalletNum();
        }
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.my_wallet_title_layout;
    }

    @Override
    protected String titleString() {
        return "我的钱包";
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
    protected void initView() {
        super.initView();
        presenter.getWalletNum();
    }

    @Override
    protected void setListener() {
        super.setListener();
        set_forward_pwd_layout.setOnClickListener(this);
        put_forward_text.setOnClickListener(this);
        put_forward_record_text.setOnClickListener(this);
        bank_card_layout.setOnClickListener(this);
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(false,msg);
    }

    @Override
    public void getWalletNumSuccess(WalletNumBean walletNumBean) {  //获取钱包金额成功
        this.walletNumBean=walletNumBean;
//        user_capital = walletNumBean.getUser_capital();
//        user_pay_pass = walletNumBean.getUser_pay_pass();
        user_capital_text.setText(Util.fenToYuan(walletNumBean.getUser_capital()));
        lock_capital_text.setText("冻结金额 "+Util.fenToYuan(walletNumBean.getLock_capital())+"元");
        get_capital_text.setText("¥"+Util.fenToYuan(walletNumBean.getGet_capital()));
        statusLayoutManager.showContent();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_forward_pwd_layout:  //设置提现密码
                if (null==walletNumBean) return;
                Intent intent=new Intent(this, SetForwardPwdActivity.class);
                intent.putExtra("title",walletNumBean.getUser_pay_pass()==0?"设置提现密码":"修改提现密码");
                startActivity(intent);
                break;
            case R.id.put_forward_record_text:  //提现明细
                startActivity(new Intent(this, PutForwardRecordActivity.class));
                break;
            case R.id.put_forward_text:  //提现
                if (null==walletNumBean) return;
                if (walletNumBean.getUser_pay_pass()==0){
                    t("请先设置提现密码");
                    set_forward_pwd_layout.performClick();
                    return;
                }
                if (walletNumBean.getUser_capital()<=0){
                    t("可提现金额为0，无法提现");
                    return;
                }
                Intent intent1=new Intent(this, PutForwardActivity.class);
                intent1.putExtra("bean",walletNumBean);
                startActivity(intent1);
                break;
            case R.id.bank_card_layout:  //我的银行卡
                startActivity(new Intent(this, MyBankCardAvtivity.class));
                break;
        }
    }

    /**
     * 接收消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.SET_FORWARD_PWD_SUCCESS:  //设置提现密码成功
                if (null!=walletNumBean)
                    walletNumBean.setUser_pay_pass(1);
                break;
        }
    }

    private void findview() {
        put_forward_record_text=findViewById(R.id.put_forward_record_text);
        user_capital_text=findViewById(R.id.user_capital_text);
        lock_capital_text=findViewById(R.id.lock_capital_text);
        get_capital_text=findViewById(R.id.get_capital_text);
        set_forward_pwd_layout=findViewById(R.id.set_forward_pwd_layout);
        put_forward_text=findViewById(R.id.put_forward_text);
        bank_card_layout=findViewById(R.id.bank_card_layout);
    }
}
