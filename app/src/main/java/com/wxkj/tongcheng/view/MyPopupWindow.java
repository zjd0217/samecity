package com.wxkj.tongcheng.view;

import android.graphics.drawable.ColorDrawable;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.wxkj.tongcheng.R;

/**
 * 分类的popupwindow
 * Created by cheng on 2018/10/12.
 */

public class MyPopupWindow extends PopupWindow{
    public MyPopupWindow() {
        // 设置外部可点击
        this.setOutsideTouchable(true);
//        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置弹出窗体的宽和高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置弹出窗体可点击
        this.setFocusable(true);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.popup_anim);
    }
}
