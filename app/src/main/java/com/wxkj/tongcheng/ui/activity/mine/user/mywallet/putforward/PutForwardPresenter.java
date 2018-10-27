package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.putforward;

import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

/**
 * Created by cheng on 2018/10/16.
 */

public class PutForwardPresenter extends BasePresenter<PutForwardView> {
    /**
     * 提现
     */
    void putward(String pwd,int money,int channel,String bankAddr,String bankCard,String name){
        ApiController.getService()
                .userPutWardMoney(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        Util.strToMD5(pwd),
                        money,
                        channel,
                        bankAddr,
                        bankCard,
                        name)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }
}
