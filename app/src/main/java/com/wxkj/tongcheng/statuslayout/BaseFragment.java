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

import com.wxkj.tongcheng.R;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 普通fragment 基类(不含标题布局)
 * Created by cheng on 2018/10/7.
 */

public abstract class BaseFragment extends Fragment {
    protected LinearLayout base_content_layout;
    private View view;
    protected StatusLayoutManager statusLayoutManager;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.base_fragment_layout, container, false);
        base_content_layout = view.findViewById(R.id.base_content_layout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化状态
        initStatusLayout();
        //添加状态布局
        View rootLayout = statusLayoutManager.getRootLayout();
        //黄油刀
        unbinder = ButterKnife.bind(this, rootLayout);
        base_content_layout.addView(rootLayout);
        if (initEventBus()&&!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        //初始化数据
        initData();
        //view与数据绑定
        initView();
        //设置监听
        setListener();
    }

    protected abstract void initStatusLayout();

    protected void initData() {
    }

    protected void initView() {
    }

    protected void setListener() {
    }

    protected boolean initEventBus(){
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
     */
    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) view.findViewById(id);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        if (initEventBus()&&EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
