package com.wxkj.tongcheng.ui.fragment.city.city;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.CityTypeAdapter;
import com.wxkj.tongcheng.bean.CityBannerBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.OnItemClick;

/**
 * 同城服务类型 fragment
 * Created by cheng on 2018/10/10.
 */

public class ServiceTypeFragment extends BaseFragment implements CityTypeAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private CityTypeAdapter cityTypeAdapter;
    private List<CityBannerBean.TypeBean> list;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.city_service_type_fragment_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void initView() {
        super.initView();
        setAdapter();
    }

    private void setAdapter() {
        list = (List<CityBannerBean.TypeBean>) getArguments().getSerializable("list");
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5, LinearLayoutManager.VERTICAL, false));
        cityTypeAdapter = new CityTypeAdapter(getContext(), list);
        recyclerView.setAdapter(cityTypeAdapter);

        cityTypeAdapter.setListener(this);
    }

    @Override
    public void onItemClick(int position) {
        int code = getArguments().getInt("code", -1);
        if (code==-1) return;
        CityBannerBean.TypeBean typeBean = list.get(position);
        EventBusBean bean = new EventBusBean();
        //code由初始化fragment bundle入参
        bean.setCode(code);
        bean.setTypeBean(typeBean);
        EventBus.getDefault().post(bean);
    }
}
