package com.wxkj.tongcheng.statuslayout;

import com.wxkj.tongcheng.mvp.BasePresenter;

/**
 * Created by cheng on 2018/10/7.
 */

public abstract class MvpBaseFragment<p extends BasePresenter> extends BaseFragment {
    protected p presenter;

    @Override
    protected void initView() {
        super.initView();
        presenter=initPresenter();
        presenter.addView(this);
    }

    protected abstract p initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null!=presenter)
            presenter.detattch();
    }
}
