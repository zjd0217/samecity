package com.wxkj.tongcheng.ui.activity.mine.user.userinfo.changeusername;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * 修改用户昵称
 * Created by cheng on 2018/10/13.
 */

public class ChangeUserNameActivity extends MvpBaseActivity<ChangeUserNamePresenter> implements ChangeUserNameView, View.OnClickListener {
    @BindView(R.id.user_name_edit)
    EditText user_name_edit;
    @BindView(R.id.save_text)
    TextView save_text;

    @Override
    protected ChangeUserNamePresenter initPresenter() {
        return new ChangeUserNamePresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.change_user_name_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "修改昵称";
    }

    @Override
    protected void initView() {
        super.initView();
        user_name_edit.setText(SPUtil.getInstance(this).getStringByKey("user_name"));
    }

    @Override
    public Context getcontext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        t(msg);
    }

    @Override
    public void saveUserNameSuccess() {
        String name = user_name_edit.getText().toString().trim();
        SPUtil.getInstance(this).saveStringInfo("user_name",name);
        t("修改昵称成功");
        EventBusBean bean=new EventBusBean();
        bean.setCode(CodeUtil.CHANGE_USER_NAME_SUCCESS);
        bean.setName(name);
        EventBus.getDefault().post(bean);
        finish();
    }

    @Override
    protected void setListener() {
        super.setListener();
        save_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String trim = user_name_edit.getText().toString().trim();
        if (TextUtils.isEmpty(trim))
            t("请输入昵称");
        else if (trim.equals(SPUtil.getInstance(this).getStringByKey("user_name")))
            t("昵称不能重复");
        else
            presenter.saveUserName(trim);
    }
}
