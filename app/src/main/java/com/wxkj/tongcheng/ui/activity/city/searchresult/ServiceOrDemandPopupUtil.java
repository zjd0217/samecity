package com.wxkj.tongcheng.ui.activity.city.searchresult;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.view.MyPopupWindow;

/**
 * 服务 需求 popup
 * Created by cheng on 2018/10/18.
 */

public class ServiceOrDemandPopupUtil implements View.OnClickListener {

    private TextView service_text,demand_text;

    public MyPopupWindow initPopup(Context context, IChooseServiceDemandListener iChooseServiceDemandListener){
        this.iChooseServiceDemandListener=iChooseServiceDemandListener;
        MyPopupWindow popupWindow=new MyPopupWindow();
        View view = LayoutInflater.from(context).inflate(R.layout.city_service_demand_popup_layout, null);
        service_text = view.findViewById(R.id.service_text);
        demand_text=view.findViewById(R.id.demand_text);
        service_text.setOnClickListener(this);
        demand_text.setOnClickListener(this);
        popupWindow.setContentView(view);
        return popupWindow;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.service_text:
                choose(0);
                service_text.setTextColor(Color.parseColor("#ff5000"));
                demand_text.setTextColor(Color.parseColor("#333333"));
                break;
            case R.id.demand_text:
                choose(1);
                service_text.setTextColor(Color.parseColor("#333333"));
                demand_text.setTextColor(Color.parseColor("#ff5000"));
                break;
        }
    }

    private void choose(int i) {
        if (null!=iChooseServiceDemandListener)
            iChooseServiceDemandListener.chooseServiceDemand(i);
    }


    public interface IChooseServiceDemandListener{
        void chooseServiceDemand(int service);
    }
    public IChooseServiceDemandListener iChooseServiceDemandListener;
}
