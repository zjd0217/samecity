package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mybankcard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.DeleteBankCardAdapter;
import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.listener.IClickItemListener;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.city.searchresult.CitySearchResultActivity;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mywallet.MyWalletPresenter;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.DeleteBankCardFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除银行卡 页面
 * Created by cheng on 2018/10/19.
 */

public class DeleteBankCardActivity extends MvpBaseActivity<MyBankCardPresenter> implements MyBankCardView, IClickItemListener {
    private RecyclerView recyclerView;

    private List<BankCardListBean> bankCardList=new ArrayList<>();
    private DeleteBankCardAdapter cardAdapter;
    private int position=-1;

    @Override
    protected MyBankCardPresenter initPresenter() {
        return new MyBankCardPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.delete_bank_card_content_layout)
                .build();
        statusLayoutManager.showLoading();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
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
        recyclerView=findViewById(R.id.recyclerView);
    }

    @Override
    protected void initView() {
        super.initView();
        //设置适配器
        setAdapter();
        //获取传过来的银行卡集合
        getBankCardList();
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
    public void addBankCardSuccess() {  //解除绑定成功
        t("解除绑定成功");
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.ADD_BANK_SUCCESS);
        EventBus.getDefault().post(bean);
        bankCardList.remove(position);
        cardAdapter.notifyDataSetChanged();
    }

    /**
     * 获取传过来的银行卡集合
     */
    private void getBankCardList() {
        List<BankCardListBean> bankCardList = (List<BankCardListBean>) getIntent().getSerializableExtra("list");
        if (null==bankCardList) {
            finish();
            return;
        }
        this.bankCardList.clear();
        this.bankCardList.addAll(bankCardList);
        cardAdapter.notifyDataSetChanged();
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        cardAdapter = new DeleteBankCardAdapter(this,bankCardList);
        recyclerView.setAdapter(cardAdapter);
        cardAdapter.setClickItemListener(this);
    }

    /**
     * item点击事件监听
     * @param position
     */
    @Override
    public void clickItemListener(int position) {
        this.position=position;
        new DeleteBankCardFragment().show(getSupportFragmentManager(),"DeleteBankCardFragment");
    }

    /**
     * 接收消息
     * @param bean
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventReceiveMsg(EventBusBean bean){
        switch (bean.getCode()){
            case CodeUtil.SURE_DELETE_BANK_CARD:  //确定解除绑定银行卡
                presenter.deleteBankCard(bankCardList.get(position).getAccount_channel());
                break;
        }
    }
}
