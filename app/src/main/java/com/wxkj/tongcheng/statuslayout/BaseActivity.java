package com.wxkj.tongcheng.statuslayout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.ui.activity.mine.login.login.LoginActicity;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity基类
 * Created by cheng on 2018/10/7.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected StatusLayoutManager statusLayoutManager;
    protected ImmersionBar mImmersionBar;
    private LinearLayout base_title_layout;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //初始化状态布局
        initStatusLayout();
        //添加状态布局
        LinearLayout content_layout = findViewById(R.id.base_content_layout);
        View rootLayout = statusLayoutManager.getRootLayout();
        bind = ButterKnife.bind(this, rootLayout);
        content_layout.addView(rootLayout);
        //绑定标题布局
        base_title_layout = findViewById(R.id.base_title_layout);
        int titleLayoutId = titleLayoutId();
        if (titleLayoutId == 0)
            base_title_layout.setVisibility(View.GONE);
        else {
            View inflate = LayoutInflater.from(this).inflate(titleLayoutId, null);
            base_title_layout.addView(inflate);
            if (setTitleBacImg() == 0 && setTitleBacColor() != 0)
                base_title_layout.setBackgroundColor(setTitleBacColor());
            else if (setTitleBacImg() != 0 && setTitleBacColor() == 0)
                base_title_layout.setBackgroundResource(setTitleBacImg());
            titleBackClick();
        }

        //初始化沉浸式
        if (isImmersionBarEnabled()) {
            initImmersionBar();
            mImmersionBar.statusBarDarkFont(true, 0.2f).init();
        }

        //注册eventbus
        if (initEventBus() && !EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        //初始化数据
        initData();
        //view与数据绑定
        initView();
        //设置监听
        setListener();
        //监听标题和返回键点击事件
        titleBackClick();
    }

    /**
     * 监听标题和返回键点击事件
     */
    private void titleBackClick() {
        try {
            ImageView back_img = findViewById(R.id.base_back_img);
            TextView title_text = findViewById(R.id.base_title_text);
            title_text.setText(titleString());
            title_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickTitle();
                }
            });

            back_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickBack();
                }
            });

        } catch (Exception e) {
//            throw new RuntimeException("请在标题布局中写入隐藏的标题控件和返回控件");
        }
    }

    protected abstract void initStatusLayout();

    protected abstract int titleLayoutId();

    protected abstract String titleString();

    protected int setTitleBacImg() {  //设置标题栏背景图
        return 0;
    }

    protected int setTitleBacColor() {  //设置标题栏背景颜色  默认白色
        return 0;
    }

    //是否注册eventbus  默认不注册
    protected boolean initEventBus() {
        return false;
    }

    protected boolean loginOrNot(String msg) {
        //未登录
        if (!SPUtil.getInstance(this).getLoginOrNot()) {
            t(msg);
            startActivity(new Intent(this, LoginActicity.class));
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null) mImmersionBar.destroy();  //在BaseActivity里销毁
        //解除eventbus注册
        if (initEventBus() && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        if (bind != null) {
            bind.unbind();
        }
    }

    protected void initImmersionBar() {
        //在BaseActivity里初始化
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.navigationBarEnable(false).init();
    }

    protected void initData() {
    }

    protected void initView() {
    }

    protected void setListener() {
    }

    protected void clickTitle() {

    }

    protected void clickBack() {
        finish();
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
        Toast.makeText(this, TextUtils.isEmpty(msg) ? "未知错误" : msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 是否可以使用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
