package com.wxkj.tongcheng.ui.activity.mine.user.mywallet.putforwardrecord;

import android.content.Context;

import com.wxkj.tongcheng.bean.PutForwardRecordBean;

import java.util.List;

/**
 * Created by cheng on 2018/10/19.
 */

public interface RecordView {
    Context getcontext();
    int getPage();
    void showMsg(String msg);
    void getRecordSuccess(List<PutForwardRecordBean> recordList);
}
