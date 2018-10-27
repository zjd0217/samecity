package com.wxkj.tongcheng.statuslayout;

import com.wxkj.tongcheng.mvp.BasePresenter;

/**
 * Created by cheng on 2018/10/7.
 */

public abstract class MvpBaseActivity<p extends BasePresenter> extends BaseActivity {
    protected p presenter;

    @Override
    protected void initData() {
        super.initData();
        presenter=initPresenter();
        presenter.addView(this);
    }

    protected abstract p initPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null!=presenter)
            presenter.detattch();
    }
}
