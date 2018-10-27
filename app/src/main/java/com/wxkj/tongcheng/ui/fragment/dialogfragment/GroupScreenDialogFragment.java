package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.CodeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.wxkj.tongcheng.util.CodeUtil.GROUP_GOODS_SCREEN_CODE;

/**
 * @author Liu haijun
 * @create 2018/10/24 0024
 * @Describe 拼团筛选框
 */
public class GroupScreenDialogFragment extends BaseDialogFragment {


    @BindViews({R.id.screen_need, R.id.screen_goods, R.id.screen_service,
            R.id.screen_two, R.id.screen_three, R.id.screen_five, R.id.screen_eight,
            R.id.screen_ten})
    List<TextView> textViews;

    private LinkedHashMap<Integer, Boolean> map;
    private Integer[] person = {0, 0, 0, 2, 3, 5, 8, 10};


    @Override
    protected int setLayoutId() {
        return R.layout.group_screen_dialog_fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.TOP | Gravity.END);
        mWindow.setWindowAnimations(R.style.RightDialog);
        mWindow.setLayout(mWidth, mHeight);
    }

    @Override
    protected void initView() {
        super.initView();
        map = new LinkedHashMap<>(textViews.size());
        reset();
    }

    @OnClick({R.id.screen_out, R.id.screen_need, R.id.screen_goods, R.id.screen_service,
            R.id.screen_two, R.id.screen_three, R.id.screen_five, R.id.screen_eight,
            R.id.screen_ten, R.id.screen_reset_text, R.id.screen_finish_text})
    public void click(View view) {
        switch (view.getId()) {
            //点击外边
            case R.id.screen_out:
                this.dismiss();
                break;
            //重置
            case R.id.screen_reset_text:
                reset();
                break;
            //完成
            case R.id.screen_finish_text:
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < map.size(); i++) {
                    if (map.get(i) && person[i] != 0) {
                        builder.append(person[i]).append(",");
                    }
                }
                String s = builder.toString();
                if (!TextUtils.isEmpty(s) && s.length() > 1) {
                    s = s.substring(0, s.length() - 1);
                }
                EventBusBean bean = new EventBusBean();
                bean.setCode(CodeUtil.GROUP_GOODS_SCREEN_CODE);
                bean.setMan_num_need(s);
                EventBus.getDefault().post(bean);
                this.dismiss();
                break;
            //需求
            case R.id.screen_need:
                map.put(0, !map.get(0));
                notifyView();
                break;
            //商品
            case R.id.screen_goods:
                map.put(1, !map.get(1));
                notifyView();
                break;
            //服务
            case R.id.screen_service:
                map.put(2, !map.get(2));
                notifyView();
                break;
            //2
            case R.id.screen_two:
                map.put(3, !map.get(3));
                notifyView();
                break;
            //3
            case R.id.screen_three:
                map.put(4, !map.get(4));
                notifyView();
                break;
            //5
            case R.id.screen_five:
                map.put(5, !map.get(5));
                notifyView();
                break;
            //8
            case R.id.screen_eight:
                map.put(6, !map.get(6));
                notifyView();
                break;
            //10
            case R.id.screen_ten:
                map.put(7, !map.get(7));
                notifyView();
                break;
        }


    }

    /**
     * 重置
     */
    private void reset() {
        for (int i = 0; i < textViews.size(); i++) {
            map.put(i, false);
        }
        notifyView();
    }

    /**
     * 更新view
     */
    private void notifyView() {
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).setBackgroundResource(map.get(i)
                    ? R.drawable.ff5_4dp_bg : R.drawable.f7_4dp_bg);
        }
    }

}
