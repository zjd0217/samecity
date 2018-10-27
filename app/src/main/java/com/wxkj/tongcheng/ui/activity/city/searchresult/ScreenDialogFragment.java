package com.wxkj.tongcheng.ui.activity.city.searchresult;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.CityScreenAdapter;
import com.wxkj.tongcheng.bean.CityScreenBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 同城筛选弹窗
 * Created by cheng on 2018/10/12.
 */

public class ScreenDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private TextView reset_text, finish_text, orther_text;
    private List<CityScreenBean> screenList;
    private CityScreenAdapter screenAdapter;

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.END);
        mWindow.setWindowAnimations(R.style.RightDialog);
        mWindow.setLayout(mWidth, mHeight);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.city_screen_dialog_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
    }

    @Override
    protected void initView() {
        super.initView();
        //获取传过来的集合
        screenList = (List<CityScreenBean>) getArguments().getSerializable("list");
        if (null == screenList || screenList.size() == 0) {
            this.dismiss();
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        screenAdapter = new CityScreenAdapter(getContext(), screenList);
        recyclerView.setAdapter(screenAdapter);

    }

    @Override
    protected void setListener() {
        super.setListener();
        reset_text.setOnClickListener(this);
        finish_text.setOnClickListener(this);
        orther_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_text:  //重置
                reset();
                break;
            case R.id.finish_text:  //完成
                finish();
                break;
            case R.id.orther_text:
                this.dismiss();
                break;
        }
    }

    private void finish() {
        try {
            StringBuffer buffer = new StringBuffer();
            for (CityScreenBean cityScreenBean : screenList) {
                List<CityScreenBean.ValueBean> value = cityScreenBean.getValue();
                for (CityScreenBean.ValueBean bean : value) {
                    if (bean.isChoose())
                        buffer.append(bean.getAttribute_data_id() + ",");
                }
            }

            EventBusBean bean = new EventBusBean();
            bean.setCode(CodeUtil.CHOOSE_CITY_SCREEN);
            bean.setName(buffer.toString());
            EventBus.getDefault().post(bean);
            this.dismiss();
        } catch (Exception e) {
            this.dismiss();
        }
    }

    /**
     * 重置
     */
    private void reset() {
        try {
            for (CityScreenBean cityScreenBean : screenList) {
                List<CityScreenBean.ValueBean> value = cityScreenBean.getValue();
                for (CityScreenBean.ValueBean bean : value) {
                    bean.setChoose(false);
                }
            }
            screenAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            this.dismiss();
        }
    }

    private void findview() {
        reset_text = findActivityViewById(R.id.reset_text);
        finish_text = findActivityViewById(R.id.finish_text);
        recyclerView = findActivityViewById(R.id.recyclerView);
        orther_text = findActivityViewById(R.id.orther_text);
    }
}
