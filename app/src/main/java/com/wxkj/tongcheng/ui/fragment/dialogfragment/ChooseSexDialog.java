package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 选择性别弹窗
 * Created by cheng on 2018/10/14.
 */

public class ChooseSexDialog extends BaseDialogFragment implements View.OnClickListener {
    private LinearLayout root_layout;
    private TextView other_text,man_text,woman_text;

    @Override
    protected int setLayoutId() {
        return R.layout.choose_sex_dialog_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void setListener() {
        super.setListener();
        root_layout.setOnClickListener(this);
        man_text.setOnClickListener(this);
        woman_text.setOnClickListener(this);
        other_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.root_layout:
                this.dismiss();
                break;
            case R.id.other_text:
                choose(0);
                break;
            case R.id.man_text:
                choose(1);
                break;
            case R.id.woman_text:
                choose(2);
                break;
        }
    }

    private void choose(int type){
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.CHOOSE_SEX);
        bean.setType(type);
        EventBus.getDefault().post(bean);
        this.dismiss();
    }

    private void findview() {
        root_layout=findActivityViewById(R.id.root_layout);
        man_text=findActivityViewById(R.id.man_text);
        woman_text=findActivityViewById(R.id.woman_text);
        other_text=findActivityViewById(R.id.other_text);
    }
}
