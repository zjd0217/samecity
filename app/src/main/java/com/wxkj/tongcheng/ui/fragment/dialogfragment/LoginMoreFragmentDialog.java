package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 更多登录 弹窗
 * Created by cheng on 2018/10/13.
 */

public class LoginMoreFragmentDialog extends BaseDialogFragment implements View.OnClickListener {
    private TextView orther_text,change_user_text,register_text,cancle_text;


    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.login_more_dialog_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void setListener() {
        super.setListener();
        orther_text.setOnClickListener(this);
        change_user_text.setOnClickListener(this);
        register_text.setOnClickListener(this);
        cancle_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.orther_text:
            case R.id.cancle_text:
                this.dismiss();
                break;
            case R.id.change_user_text:
                choose(0);
                break;
            case R.id.register_text:
                choose(1);
                break;
        }
    }

    private void choose(int type){
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.LOGIN_CHOOSE_MORE);
        bean.setType(type);
        EventBus.getDefault().post(bean);
        this.dismiss();
    }

    private void findview() {
        orther_text=findActivityViewById(R.id.orther_text);
        change_user_text=findActivityViewById(R.id.change_user_text);
        register_text=findActivityViewById(R.id.register_text);
        cancle_text=findActivityViewById(R.id.cancle_text);
    }
}
