package com.wxkj.tongcheng.ui.fragment.group;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.GroupTitleBean;
import com.wxkj.tongcheng.bean.ResponseModel;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;

import java.util.List;

import io.reactivex.functions.Function;

/**
 * Created by cheng on 2018/10/8.
 */

public class GroupPresenter extends BasePresenter<GroupView> {
    private static final String TAG = "GroupPresenter";

    void getBannerData(Context context, int page) {
        SPUtil instance = SPUtil.getInstance(context);
        ApiController.getService().getGroupBannerData(instance.getIntByKey("user_id") + "",
                instance.getStringByKey("token"), Util.getPage(page))
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(GroupTitleBean.class))
                .subscribe(addObserver(new CommonObserver<>(new ObserverOnNextListener<GroupTitleBean>() {
                            @Override
                            public void onNext(GroupTitleBean groupTitleBean) {
                                view.getBannerSuccess(groupTitleBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showMsg(e.getMessage());
                            }
                        }))
                );
    }


}
