package com.wxkj.tongcheng.retrofit.observer.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.view.loaddialog.RxDialog;


/**
 * 网络请求的对话框控制类
 * Created by cheng on 2018/10/7.
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    private RxDialog pd;
    private Context context;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener
            mProgressCancelListener) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
    }
    private void initProgressDialog() {
        if (pd == null) {
            pd = new RxDialog(context, R.style.progress_dialog);
            pd.setCancelable(true);
            pd.setCanceledOnTouchOutside(false);
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mProgressCancelListener.onCancelProgress();
                }
            });
            if (!pd.isShowing()) {
                pd.show();
            }
        }
    }
    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

    public interface ProgressCancelListener {
        void onCancelProgress();
    }
}