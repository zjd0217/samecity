package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cheng on 2018/10/11.
 */

public class ShareFragmentDialog extends BaseDialogFragment implements View.OnClickListener {
    private TextView other_text,cancle_text;
    private LinearLayout qq_layout,circle_layout,qq_zone_layout,weibo_layout,wechat_layout;


    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
        mWindow.setLayout(mWidth, mHeight);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.share_dialog_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void setListener() {
        super.setListener();
        other_text.setOnClickListener(this);
        cancle_text.setOnClickListener(this);
        qq_layout.setOnClickListener(this);
        circle_layout.setOnClickListener(this);
        qq_zone_layout.setOnClickListener(this);
        weibo_layout.setOnClickListener(this);
        wechat_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.other_text:
            case R.id.cancle_text:
                this.dismiss();
                break;
            case R.id.qq_layout:
                choose(1);
                break;
            case R.id.circle_layout:
                choose(2);
                break;
            case R.id.qq_zone_layout:
                choose(3);
                break;
            case R.id.weibo_layout:
                choose(4);
                break;
            case R.id.wechat_layout:
                choose(5);
                break;
        }
    }

    private void choose(int type){
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.CHOOSE_SHARE_TYPE);
        bean.setType(type);
        EventBus.getDefault().post(bean);
        this.dismiss();
    }

    private void findview() {
        other_text=findActivityViewById(R.id.other_text);
        cancle_text=findActivityViewById(R.id.cancle_text);
        qq_layout=findActivityViewById(R.id.qq_layout);
        circle_layout=findActivityViewById(R.id.circle_layout);
        qq_zone_layout=findActivityViewById(R.id.qq_zone_layout);
        weibo_layout=findActivityViewById(R.id.weibo_layout);
        wechat_layout=findActivityViewById(R.id.wechat_layout);
    }
}
