package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mywallet;

import com.wxkj.tongcheng.bean.WalletNumBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;

/**
 * Created by cheng on 2018/10/16.
 */

public class MyWalletPresenter extends BasePresenter<MyWalletView> {
    /**
     * 获取钱包金额
     */
    void getWalletNum(){
        ApiController.getService()
                .getWalletNum(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(WalletNumBean.class))
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<WalletNumBean>() {
                    @Override
                    public void onNext(WalletNumBean walletNumBean) {
                        view.getWalletNumSuccess(walletNumBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }
}
