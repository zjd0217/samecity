package com.wxkj.tongcheng.ui.activity.demand.voice;

import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;

public class VoicePresenter extends BasePresenter<VoiceView> {


    void upload(String voice) {
        ApiController.getService().uploadVoiceFile(SPUtil.getInstance(view.getContext()).getUserId(),
                SPUtil.getInstance(view.getContext()).getToken(), -1, voice)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getContext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.uploadSuccess(simpleResponseEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                }));

    }

}
