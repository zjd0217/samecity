package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.BankCardListBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.WalletNumBean;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.ui.activity.mine.user.mywallet.mybankcard.MyBankCardAvtivity;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 选择提现方式 弹窗
 * Created by cheng on 2018/10/21.
 */

public class ChoosePutwardModeFragment extends BaseDialogFragment implements View.OnClickListener {
    private TextView other_text,bank_name_text,back_type_text;
    private LinearLayout choose_alipay_layout,choose_wechat_layout,choose_bank_card_layout,add_bank_card_layout;
    private WalletNumBean walletNumBean;

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.choose_putward_mode_dialog_layout;
    }

//    @Override
//    protected boolean initEventBus() {
//        return true;
//    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void initView() {
        super.initView();
        setData();
    }

    @Override
    protected void setListener() {
        super.setListener();
        other_text.setOnClickListener(this);
        choose_bank_card_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.other_text:
                this.dismiss();
                break;
            case R.id.choose_bank_card_layout:  //选择银行卡
                startActivityForResult(new Intent(getContext(),MyBankCardAvtivity.class),CodeUtil.PUTWARD_MODE_CHOOSE_BACK);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CodeUtil.PUTWARD_MODE_CHOOSE_BACK&&null!=data){
            BankCardListBean bankCardListBean= (BankCardListBean) data.getSerializableExtra("bean");
            EventBusBean bean = new EventBusBean();
            bean.setCode(CodeUtil.PUTWARD_MODE_CHOOSE_BACK);
            bean.setBankCardListBean(bankCardListBean);
            EventBus.getDefault().post(bean);
            this.dismiss();
        }
    }

    /**
     * 设置默认银行卡信息
     */
    private void setData() {
        walletNumBean = (WalletNumBean) getArguments().getSerializable("bean");
        String account_info = walletNumBean.getAccount_info();
        if (walletNumBean.getAccount_channel()!=0&&!TextUtils.isEmpty(account_info)){
            bank_name_text.setText(getBankName(walletNumBean.getAccount_channel()));
            back_type_text.setText("尾号"+(account_info.length()<4?account_info:account_info.substring(0,4))+"储蓄卡");
        }
    }

    private String getBankName(int channel){
        switch (channel){
            case 3:
                return "中国工商银行";
            case 4:
                return "中国建设银行";
            case 5:
                return "中国农业银行";
            case 6:
                return "中国银行";
            default:
                return "未知银行";
        }
    }

    private void findview() {
        other_text=findActivityViewById(R.id.other_text);
        choose_alipay_layout=findActivityViewById(R.id.choose_alipay_layout);
        choose_wechat_layout=findActivityViewById(R.id.choose_wechat_layout);
        choose_bank_card_layout=findActivityViewById(R.id.choose_bank_card_layout);
        add_bank_card_layout=findActivityViewById(R.id.add_bank_card_layout);
        bank_name_text=findActivityViewById(R.id.bank_name_text);
        back_type_text=findActivityViewById(R.id.back_type_text);
    }
}
