package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.widget.Toast;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;

public class DescribeSubmitSuccessDialogFragment extends BaseDialogFragment {

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_fragment_describe_submit_success;
    }

    @Override
    protected void setListener() {
        super.setListener();
        findActivityViewById(R.id.mTvCancel).setOnClickListener(v -> dismiss());
        findActivityViewById(R.id.mTvLook).setOnClickListener(v -> {
            Toast.makeText(getContext(), "前往查看", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }
}
