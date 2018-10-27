package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.putforward;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.WalletNumBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.setforwardpwd.SetForwardPwdActivity;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ChoosePutwardModeFragment;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.paydialog.PayDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 提现页面
 * Created by cheng on 2018/10/16.
 */

public class PutForwardActivity extends MvpBaseActivity<PutForwardPresenter> implements PutForwardView, View.OnClickListener, TextWatcher, PayDialog.PayInterface {
    private TextView putward_mode_text,money_text,all_putward_text,submit_text,explain_text;
    private LinearLayout choose_mode_layout;
    private EditText putward_money_edit;
    private WalletNumBean walletNumBean;
    private BankCardListBean bankCardListBean;


    @Override
    protected PutForwardPresenter initPresenter() {
        return new PutForwardPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.put_forward_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "提现";
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
        //获取传过来的bean
        getIntentBean();
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    protected void setListener() {
        super.setListener();
        submit_text.setOnClickListener(this);
        all_putward_text.setOnClickListener(this);
        choose_mode_layout.setOnClickListener(this);
        putward_money_edit.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choose_mode_layout:  //选择提现方式
                ChoosePutwardModeFragment fragment = new ChoosePutwardModeFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("bean",walletNumBean);
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(),"ChoosePutwardModeFragment");
                break;
            case R.id.all_putward_text:  //全部提现
                putward_money_edit.setText((walletNumBean.getUser_capital()/100)+"");
                break;
            case R.id.submit_text:  //提交
                if (walletNumBean.getAccount_channel()==0&&bankCardListBean==null){
                    t("请选择提现方式");
                    return;
                }
                String money = putward_money_edit.getText().toString();
                if (TextUtils.isEmpty(money)){
                    t("请输入提现金额");
                    return;
                }
                new PayDialog(this,this).show();
                break;
        }
    }

    /**
     * 密码输入完毕
     * @param password
     */
    @Override
    public void Payfinish(String password) {
        String money = putward_money_edit.getText().toString();
        if (null!=bankCardListBean){
            presenter.putward(password,Integer.parseInt(money)*100,bankCardListBean.getAccount_channel(),bankCardListBean.getBank_addr(),bankCardListBean.getAccount_info(),bankCardListBean.getAccount_man());
        }else {
            presenter.putward(password,Integer.parseInt(money)*100,walletNumBean.getAccount_channel(),walletNumBean.getBank_addr(),walletNumBean.getAccount_info(),walletNumBean.getAccount_man());
        }
    }

    /**
     * 忘记密码
     */
    @Override
    public void onForget() {
        Intent intent=new Intent(this, SetForwardPwdActivity.class);
        intent.putExtra("title","重置提现密码");
        startActivity(intent);
    }

    /**
     * 获取传过来的bean
     */
    private void getIntentBean() {
        walletNumBean = (WalletNumBean) getIntent().getSerializableExtra("bean");
        if (null==walletNumBean) {
            finish();
            return;
        }
        money_text.setText("您当前在账户余额：¥"+ Util.fenToYuan(walletNumBean.getUser_capital()));
        String account_info = walletNumBean.getAccount_info();
        if (!TextUtils.isEmpty(account_info))
            putward_mode_text.setText("尾号"+(account_info.length()<4?account_info:account_info.substring(0,4))+"储蓄卡");
    }

    /**
     * 接收 选择银行卡返回
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.PUTWARD_MODE_CHOOSE_BACK:  //选择银行卡返回
                bankCardListBean = bean.getBankCardListBean();
                String account_info =bankCardListBean.getAccount_info();
                putward_mode_text.setText("尾号"+(account_info.length()<4?account_info:account_info.substring(0,4))+"储蓄卡");
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!TextUtils.isEmpty(s.toString())){
            long i = Long.parseLong(s.toString());
            //当前输入金额大于总金额
            submit_text.setClickable(!(i*100>walletNumBean.getUser_capital()));
            submit_text.setBackgroundResource(i*100>walletNumBean.getUser_capital()?R.drawable.no_click_bg_50dp:R.drawable.exit_login_bg);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void findview() {
        putward_mode_text=findViewById(R.id.putward_mode_text);
        money_text=findViewById(R.id.money_text);
        all_putward_text=findViewById(R.id.all_putward_text);
        submit_text=findViewById(R.id.submit_text);
        explain_text=findViewById(R.id.explain_text);
        choose_mode_layout=findViewById(R.id.choose_mode_layout);
        putward_money_edit=findViewById(R.id.putward_money_edit);
    }
}
