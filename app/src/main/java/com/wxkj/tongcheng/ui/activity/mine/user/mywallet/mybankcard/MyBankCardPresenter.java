package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mybankcard;

import android.text.TextUtils;

import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;

import java.util.List;

/**
 * Created by cheng on 2018/10/18.
 */

public class MyBankCardPresenter extends BasePresenter<MyBankCardView> {
    /**
     * 获取银行卡列表
     */
    void getBankList(){
        ApiController.getService()
                .getBankCardList(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<BankCardListBean>>() {
                    @Override
                    public void onNext(List<BankCardListBean> bankCardListBeans) {
                        view.getBankCardListSuccess(bankCardListBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }

    /**
     * 添加银行卡
     */
    void addBankCard(String name,String bankCard,String address,int channel){
        if (TextUtils.isEmpty(name))
            view.showMsg("请输入您的名字");
        else if (TextUtils.isEmpty(bankCard))
            view.showMsg("请输入银行卡号");
        else if (TextUtils.isEmpty(address))
            view.showMsg("请输入开户地址");
        else if (channel==-1)
            view.showMsg("请选择开户行");
        else {
            ApiController.getService()
                    .addBankCard(SPUtil.getInstance(view.getcontext()).getUserId(),
                            SPUtil.getInstance(view.getcontext()).getToken(),
                            address,
                            bankCard,
                            channel,
                            name)
                    .compose(RxSchedulers.io_main())
                    .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                    .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                        @Override
                        public void onNext(SimpleResponseEntity simpleResponseEntity) {
                            view.addBankCardSuccess();
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.showMsg(e.getMessage());
                        }
                    }));
        }
    }

    /**
     * 解除银行卡绑定
     */
    void deleteBankCard(int channel){
        ApiController.getService()
                .addBankCard(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        "",
                        "",
                        channel,
                        "")
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.addBankCardSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }
}
