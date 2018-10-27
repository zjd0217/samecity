package com.wxkj.tongcheng.statuslayout;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.mvp.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.locks.ReentrantLock;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.wxkj.tongcheng.statuslayout.RootFrameLayout.LAYOUT_CONTENT_ID;


/**
 * 首页fragment 基类(包含标题布局)
 * Created by cheng on 2018/10/7.
 */

public abstract class HomeBaseFragment<p extends BasePresenter> extends Fragment {
    protected LinearLayout base_content_layout, base_title_layout;
    private View view;
    protected StatusLayoutManager statusLayoutManager;
    protected ImmersionBar mImmersionBar;
    protected p presenter;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.base_activity, container, false);
        base_content_layout = view.findViewById(R.id.base_content_layout);
        base_title_layout = view.findViewById(R.id.base_title_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化状态
        initStatusLayout();
        //初始化状态布局
        initStatusLayout();
        //添加状态布局
        RootFrameLayout rootLayout = (RootFrameLayout) statusLayoutManager.getRootLayout();
        //绑定黄油刀
        bind = ButterKnife.bind(this,rootLayout);

        base_content_layout.addView(rootLayout);
        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
            mImmersionBar.statusBarDarkFont(true, 0.2f).init();
        }
        if (titleLayoutId() == 0) base_title_layout.setVisibility(View.GONE);
        else {
            base_title_layout.addView(LayoutInflater.from(getContext()).inflate(titleLayoutId(), null));
            if (setTitleBacImg() == 0 && setTitleBacColor() != 0)
                base_title_layout.setBackgroundColor(setTitleBacColor());
            else if (setTitleBacImg() != 0 && setTitleBacColor() == 0)
                base_title_layout.setBackgroundResource(setTitleBacImg());
        }
        if (initEnventBus()&&!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
        //初始化mvp
        presenter = initPresenter();
        presenter.addView(this);
        //初始化数据
        initData();
        //view与数据绑定
        initView();
        //设置监听
        setListener();
    }

    protected abstract void initStatusLayout();

    protected abstract int titleLayoutId();

    protected abstract p initPresenter();

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.titleBar(base_title_layout).navigationBarEnable(false).init();
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void setListener() {
    }

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    protected int setTitleBacImg() {  //设置标题栏背景图
        return 0;
    }

    protected int setTitleBacColor() {  //设置标题栏背景颜色  默认白色
        return 0;
    }

    protected boolean initEnventBus(){
        return false;
    }

    protected void showErrorMsg(boolean byUser, String msg) {
        if (byUser)
            t(msg);
        else {
            if (null != statusLayoutManager) {
                if (msg.equals("网络异常，请稍后重试!"))
                    statusLayoutManager.showNetWorkError();
                else
                    statusLayoutManager.showError();
            }
        }
    }

    protected void t(String msg) {
        Toast.makeText(getContext(), TextUtils.isEmpty(msg) ? "未知错误" : msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 找id
     *
     * @param id
     * @param <T>
     *
     * @return
     */
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (initEnventBus()&&EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
        if (null != presenter) presenter.detattch();
        if (mImmersionBar != null) mImmersionBar.destroy();  //在BaseActivity里销毁
        if (bind != null) {
            //解绑黄油刀
            bind.unbind();
        }
    }
}
