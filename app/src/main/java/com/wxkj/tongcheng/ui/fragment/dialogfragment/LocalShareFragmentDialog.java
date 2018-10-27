package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.view.Gravity;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;

public class LocalShareFragmentDialog extends BaseDialogFragment {

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
        mWindow.setLayout(mWidth, mHeight);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.local_share_dialog_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        findActivityViewById(R.id.qq_layout).setOnClickListener(v -> {
            this.dismiss();
        });
        findActivityViewById(R.id.circle_layout).setOnClickListener(v -> {
            this.dismiss();
        });
        findActivityViewById(R.id.qq_zone_layout).setOnClickListener(v -> {
            this.dismiss();
        });
        findActivityViewById(R.id.weibo_layout).setOnClickListener(v -> {
            this.dismiss();
        });
        findActivityViewById(R.id.wechat_layout).setOnClickListener(v -> {
            this.dismiss();
        });
        findActivityViewById(R.id.link_layout).setOnClickListener(v -> {
            this.dismiss();
        });
        findActivityViewById(R.id.picture_layout).setOnClickListener(v -> {
            this.dismiss();
        });
        findActivityViewById(R.id.cancle_text).setOnClickListener(v -> {
            this.dismiss();
        });
    }
}
