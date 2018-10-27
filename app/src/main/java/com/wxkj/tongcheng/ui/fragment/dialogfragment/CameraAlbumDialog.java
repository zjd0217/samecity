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
 * 拍照还是相册 选择弹窗
 * Created by cheng on 2018/10/14.
 */

public class CameraAlbumDialog extends BaseDialogFragment implements View.OnClickListener {
    private LinearLayout root_layout,album_layout,camera_layout;
    private TextView other_text;

    @Override
    protected int setLayoutId() {
        return R.layout.camera_album_dialog_layout;
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
        album_layout.setOnClickListener(this);
        camera_layout.setOnClickListener(this);
        other_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.root_layout:
                this.dismiss();
                break;
            case R.id.album_layout:
                choose(0);
                break;
            case R.id.camera_layout:
                choose(1);
                break;
        }
    }

    private void choose(int type){
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.CHOOSE_CAMERA_ALBUM);
        bean.setType(type);
        EventBus.getDefault().post(bean);
        this.dismiss();
    }

    private void findview() {
        root_layout=findActivityViewById(R.id.root_layout);
        album_layout=findActivityViewById(R.id.album_layout);
        camera_layout=findActivityViewById(R.id.camera_layout);
        other_text=findActivityViewById(R.id.other_text);
    }
}
