package com.wxkj.tongcheng.ui.demo;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lwkandroid.rtpermission.RTPermission;
import com.lwkandroid.rtpermission.listener.OnPermissionResultListener;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.DownloadTypeBean;
import com.wxkj.tongcheng.bean.UserInfo;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

import java.util.List;

import butterknife.BindView;


/**
 * Created by cheng on 2018/10/7.
 */

public class DemoActivity extends MvpBaseActivity<DemoPresenter> implements View.OnClickListener, DemoView, OnPermissionResultListener {
        @BindView(R.id.get_code_btn)
        Button get_code_btn;
        @BindView(R.id.login_btn)
        Button login_btn;
        @BindView(R.id.register)
        Button register;
        @BindView(R.id.download_btn)
        Button download_btn;
        @BindView(R.id.code_edit)
        EditText code_edit;
        @BindView(R.id.get_camera_btn)
        Button get_camera_btn;

        @Override
        protected void initStatusLayout() {

            //进入页面就要请求数据 使用该方式加载视图
//        statusLayoutManager= StatusLayoutManager.newBuilder(this)
//                .contentView(R.layout.login_content_layout)
//                .emptyDataView(R.layout.empty_data_layout)
//                .errorView(R.layout.error_layout)
//                .loadingView(R.layout.loading_layout)
//                .netWorkErrorView(R.layout.network_error_layout)
//                .onRetryListener(this)
//                .build();
//        statusLayoutManager.showLoading();

            //进入页面不需要访问服务器，直接显示页面，使用该方式加载视图
            statusLayoutManager = StatusLayoutManager.newBuilder(this)
                    .contentView(R.layout.demo_content_layout)
                    .build();
            statusLayoutManager.showContent();
        }

        @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "";
    }

    @Override
    protected DemoPresenter initPresenter() {
        return new DemoPresenter();
    }

    @Override
    protected void initData() {
        super.initData();
//        get_code_btn = findViewById(R.id.get_code_btn);
//        login_btn = findViewById(R.id.login_btn);
//        register = findViewById(R.id.register);
//
//        download_btn = findViewById(R.id.download_btn);
    }

    @Override
    protected void setListener() {
        super.setListener();
        get_code_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        register.setOnClickListener(this);
        download_btn.setOnClickListener(this);
        get_camera_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_code_btn:
                presenter.getSmsCode();
                break;
            case R.id.register:
                presenter.register(code_edit.getText().toString());
                break;
            case R.id.login_btn:
                presenter.pwdLogin();
                break;
            case R.id.download_btn:
                presenter.getListData();
                break;
            case R.id.get_camera_btn:
                new RTPermission.Builder()
                        .permissions(Manifest.permission.CAMERA)
                        .start(this,this);
                break;
        }
    }

    /**
     * 获取权限成功
     * @param allPermissions
     */
    @Override
    public void onAllGranted(String[] allPermissions) {
        t("获取相机权限成功");
    }

    /**
     * 获取权限失败
     * @param dinedPermissions
     */
    @Override
    public void onDeined(String[] dinedPermissions) {
        t("获取相机权限失败");
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(true, msg);
    }

    @Override
    public void getCodeSuccess(String s) {
        t(s);
    }

    @Override
    public void registerSuccess(String s) {
        t(s);
    }

    @Override
    public void loginSuccess(UserInfo userInfo) {
        Log.e("qwer", userInfo.getHead_portrait());
    }

    @Override
    public void getDownloadTypeSuccess(List<DownloadTypeBean> downloadTypeList) {
        for (DownloadTypeBean bean : downloadTypeList) {
            Log.e("qwer", bean.getType_id() + "");
        }
    }
}
