package com.wxkj.tongcheng.ui.activity.city.searchresult;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.view.MyPopupWindow;

/**
 * 综合排序popup
 * Created by cheng on 2018/10/18.
 */

public class SortPopupUtil implements View.OnClickListener {

    private TextView new_text,lately_text;

    public MyPopupWindow initPopup(Context context, IChooseSortListener iChooseSortListener){
        this.iChooseSortListener=iChooseSortListener;
        MyPopupWindow popupWindow=new MyPopupWindow();
        View view = LayoutInflater.from(context).inflate(R.layout.city_sort_popup_layout, null);
        new_text = view.findViewById(R.id.new_text);
        lately_text=view.findViewById(R.id.lately_text);
        new_text.setOnClickListener(this);
        lately_text.setOnClickListener(this);
        popupWindow.setContentView(view);
        return popupWindow;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_text:
                sort(0);
                lately_text.setTextColor(Color.parseColor("#333333"));
                new_text.setTextColor(Color.parseColor("#ff5000"));
                break;
            case R.id.lately_text:
                sort(1);
                new_text.setTextColor(Color.parseColor("#333333"));
                lately_text.setTextColor(Color.parseColor("#ff5000"));
                break;
        }
    }

    private void sort(int sort){
        if (null!=iChooseSortListener)
            iChooseSortListener.chooseSort(sort);
    }

    public IChooseSortListener iChooseSortListener;
    public interface IChooseSortListener{
        void chooseSort(int sort);
    }
}
