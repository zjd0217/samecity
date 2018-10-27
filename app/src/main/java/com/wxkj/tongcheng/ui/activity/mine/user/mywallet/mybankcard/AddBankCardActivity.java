package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mybankcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.searchresult.CitySearchResultActivity;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.ChooseBankFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 添加银行卡页面
 * Created by cheng on 2018/10/18.
 */

public class AddBankCardActivity extends MvpBaseActivity<MyBankCardPresenter> implements MyBankCardView, View.OnClickListener {
    private EditText name_edit,bank_card_edit,address_edit;
    private TextView submit_text,bank_text;
    private LinearLayout choose_bank_layout;

    private int channel=-1;

    @Override
    protected MyBankCardPresenter initPresenter() {
        return new MyBankCardPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.add_bank_card_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "添加银行卡";
    }

    @Override
    protected boolean initEventBus() {
        return true;
    }

    @Override
    protected void initData() {
        super.initData();
        fingview();
    }

    @Override
    protected void setListener() {
        super.setListener();
        submit_text.setOnClickListener(this);
        choose_bank_layout.setOnClickListener(this);
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
    public void getBankCardListSuccess(List<BankCardListBean> bankCardList) {  //这里不用

    }

    @Override
    public void addBankCardSuccess() {  //添加银行卡成功
        t("添加银行卡成功");
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.ADD_BANK_SUCCESS);
        EventBus.getDefault().post(bean);
        this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_text:  //提交
                presenter.addBankCard(name_edit.getText().toString(),bank_card_edit.getText().toString(),
                        address_edit.getText().toString(),channel);
                break;
            case R.id.choose_bank_layout:  //选择银行
                ChooseBankFragment fragment = new ChooseBankFragment();
                Bundle bundle=new Bundle();
                bundle.putString("name",bank_text.getText().toString());
                fragment.setArguments(bundle);
                fragment.show(getSupportFragmentManager(),"ChooseBankFragment");
                break;
        }
    }

    /**
     * 接受消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.ADD_BANK_CHOOSE_BANK:  //选择银行
                this.channel=bean.getType();
                bank_text.setText(bean.getName());
                break;
        }
    }

    private void fingview() {
        name_edit=findViewById(R.id.name_edit);
        bank_text=findViewById(R.id.bank_text);
        bank_card_edit=findViewById(R.id.bank_card_edit);
        address_edit=findViewById(R.id.address_edit);
        choose_bank_layout=findViewById(R.id.choose_bank_layout);
        submit_text=findViewById(R.id.submit_text);
    }
}
