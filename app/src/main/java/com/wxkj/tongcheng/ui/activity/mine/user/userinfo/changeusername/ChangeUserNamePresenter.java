package com.wxkj.tongcheng.ui.activity.mine.user.userinfo.changeusername;

import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;

/**
 * Created by cheng on 2018/10/13.
 */

public class ChangeUserNamePresenter extends BasePresenter<ChangeUserNameView> {
    void saveUserName(String name){
        ApiController.getService()
                .changeUserName(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        name)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.saveUserNameSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }
}
