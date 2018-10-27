package com.wxkj.tongcheng.ui.activity.mine.user.complaint;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

/**
 * 投诉建议 页面
 * Created by cheng on 2018/10/20.
 */

public class ComplaintActivity extends MvpBaseActivity<ComplaintPresenter> implements ComplaintView, View.OnClickListener {
    private EditText title_edit,describe_edit,contact_name_edit,phone_edit;
    private TextView submit_text;

    @Override
    protected ComplaintPresenter initPresenter() {
        return new ComplaintPresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.complaint_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.keyboardEnable(true).init();
    }

    @Override
    protected String titleString() {
        return "投诉建议";
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void setListener() {
        super.setListener();
        submit_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        presenter.submit(title_edit.getText().toString().trim(),
                describe_edit.getText().toString().trim(),
                contact_name_edit.getText().toString().trim(),
                phone_edit.getText().toString().trim());
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
    public void submitSuccess() {
        t("感谢您的建议");
        finish();
    }

    private void findview() {
        title_edit=findViewById(R.id.title_edit);
        describe_edit=findViewById(R.id.describe_edit);
        contact_name_edit=findViewById(R.id.contact_name_edit);
        phone_edit=findViewById(R.id.phone_edit);
        submit_text=findViewById(R.id.submit_text);
    }
}
