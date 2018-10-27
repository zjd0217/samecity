package com.wxkj.tongcheng.view.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Liu haijun
 * @create 2018/10/11 0011
 * @Describe 设置2列RecyclerView中item的间距
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if (position % 2 == 1) {
            outRect.left = space;
        }
        if (position > 2) {
            outRect.top = space;
        }

    }
}
