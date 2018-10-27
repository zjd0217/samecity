package com.wxkj.tongcheng.ui.activity.city.searchresult;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.CitySearchPopupAdaplter;
import com.wxkj.tongcheng.bean.CityTypeBean;
import com.wxkj.tongcheng.view.MyPopupWindow;

import java.util.List;

/**
 * Created by cheng on 2018/10/12.
 */

public class TypePopupUtil implements CitySearchPopupAdaplter.IClickItemListener {

    public MyPopupWindow initPopup(Context context, List<CityTypeBean> list,IPoupuClickListener iPoupuClickListener){
        this.iPoupuClickListener=iPoupuClickListener;
        MyPopupWindow popupWindow=new MyPopupWindow();
        View view = LayoutInflater.from(context).inflate(R.layout.search_city_popup_layout, null);
        RecyclerView recyclerView= view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        CitySearchPopupAdaplter leftAdaplter=new CitySearchPopupAdaplter(context,list);
        recyclerView.setAdapter(leftAdaplter);
        leftAdaplter.setClickItemListener(this);
        popupWindow.setContentView(view);
        return popupWindow;
    }

    /**
     * 左边item点击监听
     * @param typeId
     */
    @Override
    public void clickItem(int typeId) {
        if (null!=iPoupuClickListener) iPoupuClickListener.popupClick(typeId);
    }

    public IPoupuClickListener iPoupuClickListener;
    public interface IPoupuClickListener{
        void popupClick(int id);
    }
}
