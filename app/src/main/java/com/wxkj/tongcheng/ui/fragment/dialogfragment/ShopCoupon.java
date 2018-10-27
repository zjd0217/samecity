package com.wxkj.tongcheng.ui.fragment.dialogfragment;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.bean.SimpleResponseEntity;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.SPUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/16 0016
 * @Describe 查看商家优惠劵
 */
public class ShopCoupon extends BaseDialogFragment {


    @BindView(R.id.shop_coupon_list)
    ListView shopCouponList;

    private int goods_id;

    private List<Map<String, String>> list = new ArrayList<>();
    private MyAdapter adapter;
    private FragmentActivity activity;

    @Override
    protected int setLayoutId() {
        return R.layout.look_shop_coupon;
    }

    @Override
    protected void initView() {
        super.initView();

        adapter = new MyAdapter();
        shopCouponList.setAdapter(adapter);
    }


    @Override
    protected void initData() {
        super.initData();
        activity = getActivity();
        goods_id = getArguments().getInt("goods_id", 0);
        SPUtil spUtil = SPUtil.getInstance(activity);
        ApiController.getService().getGoodsShopCouponList(
                spUtil.getIntByKey("user_id"), spUtil.getStringByKey("token"), goods_id)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(new ProgressObserver<>(activity, new ObserverOnNextListener<List<Map<String, String>>>() {
                    @Override
                    public void onNext(List<Map<String, String>> maps) {
                        list = maps;
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


    @OnClick(R.id.shop_coupon_sure)
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
                        .inflate(R.layout.item_look_shop_coupon, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            }
            vh = (ViewHolder) convertView.getTag();
            Map<String, String> map = list.get(position);
            vh.itemShopCouponPrice.setText("￥" + AmountUtil.changeF2Y(activity,
                    map.get("coupon_capital")));
            vh.itemShopCouponNeed.setText("满" + AmountUtil.changeF2Y(activity,
                    map.get("coupon_need_capital")) + "可用");
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            String end = format.format(new Date(Long.parseLong(map.get("time_valid_end")) * 1000));
            String start = format.format(new Date(Long.parseLong(map.get("time_valid_begin")) * 1000));
            vh.itemShopCouponTime.setText(start + "—" + end);
            //可以拥有的优惠券张数
            int limit = Integer.parseInt(map.get("coupon_limit_get"));
            //已经拥有的优惠券张数
            int have = Integer.parseInt(map.get("i_have"));
            vh.itemShopCouponObtain.setEnabled(true);
            vh.itemShopCouponObtain.setAlpha(1f);
            if (limit <= have) {
                //当前优惠券已经不能领取了
                vh.itemShopCouponObtain.setEnabled(false);
                vh.itemShopCouponObtain.setAlpha(0.5f);
                vh.itemShopCouponObtain.setText("已领取");
            }
            vh.itemShopCouponObtain.setOnClickListener(v -> {
                SPUtil spUtil = SPUtil.getInstance(activity);
                ApiController.getService().getGoodsShopCoupon(spUtil.getIntByKey("user_id"), spUtil.getStringByKey("token"), map.get("coupon_id"))
                        .compose(RxSchedulers.io_main())
                        .flatMap(ApiController.judgeData(SimpleResponseEntity.class))
                        .safeSubscribe(new ProgressObserver<SimpleResponseEntity>(activity, new ObserverOnNextListener<SimpleResponseEntity>() {
                            @Override
                            public void onNext(SimpleResponseEntity bean) {
                                Toast.makeText(activity, "领取成功", Toast.LENGTH_SHORT).show();
                                //刷新数据
                                initData();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));
            });
            return convertView;
        }

    }

    static class ViewHolder {
        @BindView(R.id.item_shop_coupon_price)
        TextView itemShopCouponPrice;
        @BindView(R.id.item_shop_coupon_need)
        TextView itemShopCouponNeed;
        @BindView(R.id.item_shop_coupon_time)
        TextView itemShopCouponTime;
        @BindView(R.id.item_shop_coupon_obtain)
        Button itemShopCouponObtain;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
