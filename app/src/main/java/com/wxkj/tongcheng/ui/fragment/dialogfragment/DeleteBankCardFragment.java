package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.view.View;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EvaluateBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 删除银行卡 弹窗
 * Created by cheng on 2018/10/19.
 */

public class DeleteBankCardFragment extends BaseDialogFragment implements View.OnClickListener {
    private TextView cancle_text,sure_text;

    @Override
    protected int setLayoutId() {
        return R.layout.delete_bank_card_dialog_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        sure_text=findActivityViewById(R.id.sure_text);
        cancle_text=findActivityViewById(R.id.cancle_text);
    }

    @Override
    protected void setListener() {
        super.setListener();
        sure_text.setOnClickListener(this);
        cancle_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sure_text:
                EventBusBean bean = new EventBusBean();
                bean.setCode(CodeUtil.SURE_DELETE_BANK_CARD);
                EventBus.getDefault().post(bean);
                this.dismiss();
                break;
            case R.id.cancle_text:
                this.dismiss();
                break;
        }
    }
}
