package com.wxkj.tongcheng.ui.activity.mine.user.setting.address;

import com.wxkj.tongcheng.bean.ReceivingAddressBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.bean.WalletNumBean;
import com.wxkj.tongcheng.mvp.BasePresenter;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.CommonObserver;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.util.SPUtil;

import java.util.List;

/**
 * @author Liu haijun
 * @create 2018/10/19 0019
 * @Describe
 */
public class ReceivingAddressPresenter extends BasePresenter<ReceivingAddressView> {

    /**
     * 获取收货地址列表
     */
    void getAddressList() {
        ApiController.getService()
                .getAddressList(SPUtil.getInstance(view.getcontext()).getUserId(),
                        SPUtil.getInstance(view.getcontext()).getToken())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(addObserver(new CommonObserver<List<ReceivingAddressBean>>(
                        new ObserverOnNextListener<List<ReceivingAddressBean>>() {
                            @Override
                            public void onNext(List<ReceivingAddressBean> list) {
                                view.getAddressList(list);
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showMsg(e.getMessage());
                            }
                        })));


    }

    /**
     * 删除一条地址信息
     *
     * @param addr_id
     */
    void deleteAdress(int addr_id) {

        ApiController.getService().deleteAddress(SPUtil.getInstance(view.getcontext()).getUserId(),
                SPUtil.getInstance(view.getcontext()).getToken(), addr_id)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<SimpleResponseEntity>(view.getcontext(),
                        new ObserverOnNextListener<SimpleResponseEntity>() {
                            @Override
                            public void onNext(SimpleResponseEntity simpleResponseEntity) {
                                view.success();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showMsg(e.getMessage());
                            }
                        }));
    }


    /**
     * 新增和修改一条地址信息
     *
     * @param bean
     */
    public void saveOrUpdateAddress(ReceivingAddressBean bean) {

        ApiController.getService().saveOrUpdateAddress(SPUtil.getInstance(view.getcontext()).getUserId(),
                SPUtil.getInstance(view.getcontext()).getToken(), bean.getAddr_id(), bean.getRegion_id(),
                bean.getAddr_detail(), bean.getBaidu_pos(), bean.getContact_man(),
                bean.getContact_number(), bean.getMemo(), bean.getDefault_fg())
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                .subscribe(new ProgressObserver<SimpleResponseEntity>(view.getcontext(),
                        new ObserverOnNextListener<SimpleResponseEntity>() {
                            @Override
                            public void onNext(SimpleResponseEntity simpleResponseEntity) {
                                view.success();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showMsg(e.getMessage());
                            }
                        }));
    }

}
