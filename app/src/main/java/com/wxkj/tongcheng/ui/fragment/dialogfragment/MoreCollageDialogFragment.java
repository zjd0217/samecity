package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.LabelBean;
import com.wxkj.tongcheng.bean.MoreCollageBean;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author Liu haijun
 * @create 2018/10/17 0017
 * @Describe 查看更多拼团人员信息
 */
public class MoreCollageDialogFragment extends BaseDialogFragment {

    @BindView(R.id.more_collage_list)
    ListView moreCollageList;
    private FragmentActivity activity;
    /** 数据源 */
    private List<MoreCollageBean> list = new ArrayList<>();
    private MyAdapter myAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected int setLayoutId() {
        return R.layout.more_collage_dialog_fragment;
    }


    @Override
    protected void initView() {
        super.initView();
        myAdapter = new MyAdapter();
        moreCollageList.setAdapter(myAdapter);
    }


    @Override
    protected void initData() {
        super.initData();
        int goods_id = getArguments().getInt("goods_id", 0);
        activity = getActivity();
        ApiController
                .getService()
                .getMoreCollageList(goods_id)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(new ProgressObserver<>(activity, new ObserverOnNextListener<List<MoreCollageBean>>() {
                    @Override
                    public void onNext(List<MoreCollageBean> moreCollageBeans) {
                        list = moreCollageBeans;
                        if (myAdapter != null) {
                            myAdapter.notifyDataSetChanged();
                        }
                        notifyAdapter();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    /**
     * 刷新适配器，实现倒计时
     */
    private void notifyAdapter() {

        Observable
                .create((ObservableOnSubscribe<Integer>) emitter -> emitter.onNext(12))
                .delay(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        myAdapter.notifyDataSetChanged();
                        notifyAdapter();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @OnClick({R.id.more_collage_close})
    public void click() {
        this.dismiss();
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
            ViewHolder vh = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_more_collage_dialog_fragment, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            }
            vh = (ViewHolder) convertView.getTag();
            MoreCollageBean moreCollageBean = list.get(position);

            RequestOptions options = new RequestOptions().transform(new CenterCrop());
            options.error(R.drawable.load_failure);
            options.placeholder(R.drawable.loading);
            options.circleCrop();
            Glide.with(activity).load(moreCollageBean.getFirsthead_portrait())
                    .apply(options)
                    .into(vh.itemMoreCollageImg);

            vh.itemMoreCollageName.setText(moreCollageBean.getFirstuser_cname());
            int num = moreCollageBean.getMan_num_need() - moreCollageBean.getMan_num_have();
            vh.itemMoreCollageNum.setText("还差" + num + "人拼成");
            int time_end = moreCollageBean.getTeambuy_time_end();
            int l = (int) (System.currentTimeMillis() / 1000);
            vh.itemMoreCollageTime.setText("剩余" + TimeUtils.ddHHmmss(time_end - l));

            vh.itemItemMoreCollageGo.setOnClickListener(v -> {
                // TODO: 2018/10/17 0017 去拼单 
            });

            return convertView;
        }


    }

    static class ViewHolder {
        @BindView(R.id.item_more_collage_img)
        ImageView itemMoreCollageImg;
        @BindView(R.id.item_more_collage_name)
        TextView itemMoreCollageName;
        @BindView(R.id.item_more_collage_num)
        TextView itemMoreCollageNum;
        @BindView(R.id.item_more_collage_time)
        TextView itemMoreCollageTime;
        @BindView(R.id.item_item_more_collage_go)
        LinearLayout itemItemMoreCollageGo;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onDestroy() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        super.onDestroy();
    }
}
