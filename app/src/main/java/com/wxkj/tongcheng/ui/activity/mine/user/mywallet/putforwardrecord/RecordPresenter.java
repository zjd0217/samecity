package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.putforwardrecord;

import com.wxkj.tongcheng.bean.PutForwardRecordBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

/**
 * Created by cheng on 2018/10/19.
 */

public class RecordPresenter extends BasePresenter<RecordView> {
    void getRecordList(){
        ApiController.getService()
                .getPutwardRecord(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken(),
                        Util.getPage(view.getPage()))
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver(new ObserverOnNextListener<List<PutForwardRecordBean>>() {
                    @Override
                    public void onNext(List<PutForwardRecordBean> list) {
                        view.getRecordSuccess(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg(e.getMessage());
                    }
                })));
    }
}
