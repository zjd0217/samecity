package com.wxkj.tongcheng.view;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollEditText extends android.support.v7.widget.AppCompatEditText {

    private static int MAX_LINE = 7;
    private int mOffsetHeight;
    private boolean mBottomFlag = false;

    public ScrollEditText(Context context) {
        super(context);
    }

    public ScrollEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingTop;
        int paddingBottom;
        int mHeight;
        int mLayoutHeight;

        Layout mLayout = getLayout();
        mLayoutHeight = mLayout.getHeight();
        paddingTop = getTotalPaddingTop();
        paddingBottom = getTotalPaddingBottom();
        mHeight = getHeight();
        mOffsetHeight = mLayoutHeight + paddingTop + paddingBottom - mHeight;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (getLineCount() > MAX_LINE) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                mBottomFlag = false;
            }
            if (mBottomFlag) {
                event.setAction(MotionEvent.ACTION_CANCEL);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getLineCount() > MAX_LINE) {
            boolean result = super.onTouchEvent(event);
            if (!mBottomFlag) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            return result;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        if (getLineCount() > MAX_LINE) {
            if (vert == mOffsetHeight || vert == 0) {
                getParent().requestDisallowInterceptTouchEvent(false);
                mBottomFlag = true;
            }
        }
    }
}
