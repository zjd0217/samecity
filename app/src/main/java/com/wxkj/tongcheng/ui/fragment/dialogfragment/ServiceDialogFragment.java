package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.LabelBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Liu haijun
 * @create 2018/10/16 0016
 * @Describe
 */
public class ServiceDialogFragment extends BaseDialogFragment {

    @BindView(R.id.service_list)
    ListView serviceList;

    private MyAdapter adapter;
    private int goods_id;
    private List<LabelBean> list = new ArrayList<>();
    private FragmentActivity activity;

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_service_description;
    }

    @Override
    protected void initView() {
        super.initView();
        adapter = new MyAdapter();
        serviceList.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        goods_id = getArguments().getInt("goods_id ");
        activity = getActivity();
        ApiController
                .getService()
                .getLabelList(goods_id)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(new ProgressObserver<>(activity, new ObserverOnNextListener<List<LabelBean>>() {
                    @Override
                    public void onNext(List<LabelBean> labelBeans) {
                        list = labelBeans;
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    @OnClick({R.id.service_close})
    public void click(View view) {
        switch (view.getId()) {
            //关闭
            case R.id.service_close:
                this.dismiss();
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
        mWindow.setLayout(mWidth, mHeight);
    }

    /**
     * 适配器
     */
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_service_dialog_fragment, null);
            }
            LabelBean bean = list.get(position);
            TextView name = convertView.findViewById(R.id.item_service_dialog_name);
            name.setText(bean.getLabel_name());
            TextView detail = convertView.findViewById(R.id.item_service_dialog_detail);
            detail.setText(bean.getLabel_disc());
            return convertView;
        }
    }

}
