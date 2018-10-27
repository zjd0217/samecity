package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mybankcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.MyBankCardAdapter;
import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的银行卡页面
 * Created by cheng on 2018/10/18.
 */

public class MyBankCardAvtivity extends MvpBaseActivity<MyBankCardPresenter> implements MyBankCardView, OnRetryListener, View.OnClickListener, MyBankCardAdapter.IClickItemListener {
    private TextView manage_text;
    private RecyclerView recyclerView;
    private LinearLayout add_bank_layout;

    private List<BankCardListBean> bankCardList=new ArrayList<>();
    private MyBankCardAdapter bankCardAdapter;

    @Override
    protected MyBankCardPresenter initPresenter() {
        return new MyBankCardPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.my_bank_card_content_layout)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    public void onRetry() {
        statusLayoutManager.showLoading();
        presenter.getBankList();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.my_bank_title_layout;
    }

    @Override
    protected String titleString() {
        return "我的银行卡";
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
        //设置适配器
        setAdapter();
        presenter.getBankList();
    }

    @Override
    protected void setListener() {
        super.setListener();
        manage_text.setOnClickListener(this);
        add_bank_layout.setOnClickListener(this);
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
    public void getBankCardListSuccess(List<BankCardListBean> bankCardList) {  //获取银行卡信息成功
        if (null!=bankCardList&&bankCardList.size()!=0){
            this.bankCardList.clear();
            this.bankCardList.addAll(bankCardList);
            bankCardAdapter.notifyDataSetChanged();
            manage_text.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addBankCardSuccess() {  //这里不用

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manage_text:  //管理
                Intent intent=new Intent(this,DeleteBankCardActivity.class);
                intent.putExtra("list", (Serializable) bankCardList);
                startActivity(intent);
                break;
            case R.id.add_bank_layout:  //添加银行卡
                startActivity(new Intent(this,AddBankCardActivity.class));
                break;
        }
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bankCardAdapter = new MyBankCardAdapter(this,bankCardList);
        recyclerView.setAdapter(bankCardAdapter);
        bankCardAdapter.setiClickItemListener(this);
    }

    /**
     * 接受消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.ADD_BANK_SUCCESS:  //添加银行卡成功
                presenter.getBankList();
                break;
        }
    }

    @Override
    public void clickItem(BankCardListBean bean) {
        Intent intent=new Intent();
        intent.putExtra("bean",bean);
        setResult(CodeUtil.PUTWARD_MODE_CHOOSE_BACK,intent);
        finish();
//        EventBusBean eventBusBean = new EventBusBean();
//        //code唯一码为上一个页面传过来的值 避免重复， 在上一个页面接受eventbus值
//        eventBusBean.setCode(getIntent().getIntExtra("code",-1));
//        eventBusBean.setBankCardListBean(bean);
//        EventBus.getDefault().post(eventBusBean);
//        this.finish();
    }

    private void findview() {
        manage_text=findViewById(R.id.manage_text);
        recyclerView=findViewById(R.id.recyclerView);
        add_bank_layout=findViewById(R.id.add_bank_layout);
    }
}
