package com.wxkj.tongcheng.ui.activity.mine.user.complaint;

import android.text.TextUtils;

import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;

/**
 * Created by cheng on 2018/10/20.
 */

public class ComplaintPresenter extends BasePresenter<ComplaintView> {
    void submit(String title,String describe,String name,String phone){
        if (TextUtils.isEmpty(title)){
            view.showMsg("请输入标题");
            return;
        }
        if (TextUtils.isEmpty(describe)){
            view.showMsg("请输入描述");
            return;
        }
        if (TextUtils.isEmpty(name)){
            view.showMsg("请输入联系人");
            return;
        }
        if (phone.length()!=11||!phone.startsWith("1")){
            view.showMsg("请输入正确的手机号");
            return;
        }

        ApiController.getService()
                .submitComplain(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        title,name,phone,describe)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.submitSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));
    }
}
