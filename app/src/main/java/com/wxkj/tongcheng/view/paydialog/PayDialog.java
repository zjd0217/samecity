package com.wxkj.tongcheng.view.paydialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wxkj.tongcheng.R;


/**
 * ==========================================
 * 作   者：何高建
 * 日   期：2018/3/19
 * 描   述：
 * ===========================================
 */

public class PayDialog extends Dialog implements OnPasswordInputFinish{
    public PayPassView payPassView;
    PayInterface payInterface;
    public PayDialog(@NonNull Context context, PayInterface payInterface) {
        // 在构造方法里, 传入主题
        super(context, R.style.BottomDialogStyle);
        this.payInterface = payInterface;
        // 拿到Dialog的Window, 修改Window的属性
        Window window = getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化
        payPassView = new PayPassView(getContext());
        setContentView(payPassView);
        payPassView.setOnFinishInput(this);
        payPassView.paypass_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        payPassView.forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payInterface!=null){
                    payInterface.onForget();
                }
            }
        });
    }

    @Override
    public void inputFinish() {
        if(payInterface!=null){
            cancel();
            payInterface.Payfinish(payPassView.getStrPassword());
        }
    }

    @Override
    public void inputFirst() {
        payPassView.tips.setVisibility(View.INVISIBLE);
    }
    public interface PayInterface{
        void Payfinish(String password);
        void onForget();
    }
}
