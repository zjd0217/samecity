package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.ChooseBankAdapter;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.listener.IClickItemListener;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 选择银行卡 fragment
 * Created by cheng on 2018/10/20.
 */

public class ChooseBankFragment extends BaseDialogFragment implements ChooseBankAdapter.IClickItemListener, View.OnClickListener {
    private RecyclerView recyclerView;
    private TextView other_text;

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.choose_bank_dialog_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        other_text=findActivityViewById(R.id.other_text);
        recyclerView=findActivityViewById(R.id.recyclerView);
    }

    @Override
    protected void initView() {
        super.initView();
        setAdapter();
    }

    @Override
    protected void setListener() {
        super.setListener();
        other_text.setOnClickListener(this);
    }

    /**
     * 设置适配器
     */
    private void setAdapter() {
        String[] name={"中国工商银行","中国建设银行","中国农业银行","中国银行"};
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ChooseBankAdapter bankAdapter=new ChooseBankAdapter(getContext(),name,getArguments().getString("name",""));
        recyclerView.setAdapter(bankAdapter);
        bankAdapter.setiClickItemListener(this);
    }

    @Override
    public void clickItem(int position, String name) {
        EventBusBean bean=new EventBusBean();
        bean.setCode(CodeUtil.ADD_BANK_CHOOSE_BANK);
        bean.setType(position+3);
        bean.setName(name);
        EventBus.getDefault().post(bean);
        this.dismiss();
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
