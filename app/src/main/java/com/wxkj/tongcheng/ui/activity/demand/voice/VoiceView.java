package com.wxkj.tongcheng.ui.activity.demand.voice;

import android.content.Context;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;

public interface VoiceView {

    Context getContext();

    void showMsg(String msg);

    void uploadSuccess(SimpleResponseEntity entity);
}
