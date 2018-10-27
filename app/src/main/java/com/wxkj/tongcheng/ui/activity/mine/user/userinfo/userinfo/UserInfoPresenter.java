package com.wxkj.tongcheng.ui.activity.mine.user.userinfo.userinfo;

import com.wxkj.tongcheng.bean.ChangeUserHeadBean;
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

public class UserInfoPresenter extends BasePresenter<UserInfoView> {
    /**
     * 修改头像
     *
     * @param headBase64
     */
    void changeUserHead(String headBase64) {
        ApiController.getService().changeUserHead(SPUtil.getInstance(view.getcontext()).getUserId(),
                SPUtil.getInstance(view.getcontext()).getToken(),
                headBase64)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(ChangeUserHeadBean.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<ChangeUserHeadBean>() {
                    @Override
                    public void onNext(ChangeUserHeadBean userHeadBean) {
                        view.changeHeadSuccess(userHeadBean.getHead_portrait());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg("修改头像失败");
                    }

                }));


    }

    /**
     * 修改用户性别
     *
     * @param sex
     */
    void changeUserSex(int sex) {
        ApiController.getService().changeUserSex(SPUtil.getInstance(view.getcontext()).getUserId(),
                SPUtil.getInstance(view.getcontext()).getToken(),
                sex)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.changeSexSuccess("修改性别成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg("修改性别失败");
                    }
                }));
    }

    /**
     * 修改用户生日
     *
     * @param birthday
     */
    void changeUserBirthday(String birthday) {
        ApiController.getService().changeUserBirthday(SPUtil.getInstance(view.getcontext()).getUserId(),
                SPUtil.getInstance(view.getcontext()).getToken(),
                birthday)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<>(view.getcontext(), new ObserverOnNextListener<SimpleResponseEntity>() {
                    @Override
                    public void onNext(SimpleResponseEntity simpleResponseEntity) {
                        view.changeBirthdaySuccess("修改生日成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showMsg("修改生日失败");
                    }
                }));
    }

}
