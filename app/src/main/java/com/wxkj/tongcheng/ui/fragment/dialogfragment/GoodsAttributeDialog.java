package com.wxkj.tongcheng.ui.fragment.dialogfragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.GoodsAttribute;
import com.wxkj.tongcheng.bean.GoodsAttributeBean;
import com.wxkj.tongcheng.retrofit.ApiController;
import com.wxkj.tongcheng.retrofit.RxSchedulers;
import com.wxkj.tongcheng.retrofit.observer.progress.ObserverOnNextListener;
import com.wxkj.tongcheng.retrofit.observer.progress.ProgressObserver;
import com.wxkj.tongcheng.statuslayout.BaseDialogFragment;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.util.SPUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/21 0021
 * @Describe 商品属性
 */
public class GoodsAttributeDialog extends BaseDialogFragment {


    @BindView(R.id.goods_attribute_price)
    TextView goodsAttributePrice;
    @BindView(R.id.goods_attribute_img)
    ImageView goodsAttributeImg;
    @BindView(R.id.goods_attribute_number)
    TextView goodsAttributeNumber;
    @BindView(R.id.goods_attribute_color)
    TextView goodsAttributeColor;
    @BindView(R.id.goods_attribute_list)
    RecyclerView goodsAttributeList;
    private FragmentActivity activity;
    /** 数据源 */
    private List<GoodsAttribute> listPre = new ArrayList<>();
    /** 整理以后的数据源 */
    private List<GoodsAttributeBean> lists = new ArrayList<>();
    private MyAdapter adapter;
    /** 商品件数 */
    private int shopCount = 1;
    /** 选择之后的数据 */
    private LinkedHashMap<String, String> map = new LinkedHashMap<>();

    private Toast toast;
    private GoodsAttribute goodsAttribute;

    private int num_start, num_span;

    @Override

    protected int setLayoutId() {
        return R.layout.fragment_goods_attribute;
    }

    @Override
    public void onStart() {
        super.onStart();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.BottomDialog);
    }


    @Override
    protected void initData() {
        super.initData();
        activity = getActivity();
        Bundle arguments = getArguments();
        int classify_id = arguments.getInt("classify_id", 0);
        String goodsPic = arguments.getString("goods_pic");
        num_start = arguments.getInt("num_start");
        num_span = arguments.getInt("num_span");
        RequestOptions options = new RequestOptions().transform(new CenterCrop());
        options.error(R.drawable.load_failure).placeholder(R.drawable.loading);
        Glide.with(activity).load(goodsPic).apply(options).into(goodsAttributeImg);
        ApiController
                .getService()
                .getClassifyGoods(classify_id)
                .compose(RxSchedulers.io_main())
                .flatMap(ApiController.judgeListData())
                .subscribe(new ProgressObserver<>(activity,
                        new ObserverOnNextListener<List<GoodsAttribute>>() {
                            @Override
                            public void onNext(List<GoodsAttribute> goodsAttributes) {
                                listPre = goodsAttributes;
                                if (listPre != null && listPre.size() > 0) {
                                    GoodsAttribute attribute = listPre.get(0);
                                    setViewData(attribute);
                                }
                                //组装整理数据
                                arrangementData(goodsAttributes);

                                //整理数据完成
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

    @Override
    protected void initView() {
        super.initView();
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        goodsAttributeList.setLayoutManager(manager);
        adapter = new MyAdapter();
        goodsAttributeList.setAdapter(adapter);
    }

    /**
     * 给view设置值
     */
    private void setViewData(GoodsAttribute attribute) {


        if (SPUtil.getInstance(activity).getIntByKey("spread_fg") == 1) {
            //推广会员
            goodsAttributePrice.setText("￥" + AmountUtil.changeF2Y(attribute.getPrice_member()));
        } else {
            //普通会员
            goodsAttributePrice.setText("￥" + AmountUtil.changeF2Y(attribute.getPrice_current()));
        }
        goodsAttributeNumber.setText("库存" + attribute.getNum_stock_left()
                + attribute.getGoods_unit());
        LinkedHashSet<String> valueSet = attribute.getValueSet();
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : valueSet) {
            stringBuilder.append(str).append(" ");
        }
        goodsAttributeColor.setText("已选择 " + stringBuilder.toString());

    }

    /**
     * 组装整理数据
     *
     * @param listPre
     *         原始数据
     */
    private void arrangementData(List<GoodsAttribute> listPre) {
        lists.clear();
        //获取最长长度的一级值  start
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (int i = 0; i < listPre.size(); i++) {
            GoodsAttribute attribute = listPre.get(i);
            LinkedHashSet<String> keySet = attribute.getKeySet();
            if (keySet.size() > set.size()) {
                set = keySet;
            }
        }
        //end
        for (String str : set) {
            GoodsAttributeBean bean = new GoodsAttributeBean();
            ArrayList<String> strings = new ArrayList<>();
            bean.setAttributeKey(str);
            //记录所有的键
            map.put(str, "");

            for (int i = 0; i < listPre.size(); i++) {
                GoodsAttribute goodsAttribute = listPre.get(i);
                LinkedHashMap<String, String> map = goodsAttribute.getMap();
                LinkedHashSet<String> keySet = goodsAttribute.getKeySet();
                for (String s : keySet) {
                    String string = map.get(s);
                    if (str.equals(s) && !strings.contains(string)) {
                        strings.add(string);
                    }
                }
            }

            bean.setChildrenList(strings);
            lists.add(bean);
        }
    }


    @OnClick({R.id.goods_attribute_sure, R.id.goods_attribute_close})
    public void click(View view) {
        switch (view.getId()) {
            //关闭
            case R.id.goods_attribute_close:
                this.dismiss();

                break;
            //确认
            case R.id.goods_attribute_sure:
                if (goodsAttribute == null) {
                    showTips("请先选择商品属性");
                    return;
                }
                goodsAttribute.setShopCount(shopCount);
                EventBusBean event = new EventBusBean();
                event.setCode(CodeUtil.GOODS_ATTRIBUTE_CODE);
                event.setGoodsAttribute(goodsAttribute);
                EventBus.getDefault().post(event);
                break;
        }


    }


    /**
     * 显示toast
     *
     * @param str
     */
    private void showTips(String str) {
        if (toast == null) {
            toast = Toast.makeText(activity, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
            toast.show();
        }
    }

    /**
     * 适配器
     */
    private class MyAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == lists.size()) {
                //数量的布局
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.shop_add_delete_view, parent, false);
                return new BottomViewHolder(view);
            } else {
                //商品属性
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_goods_attribute, parent, false);
                return new ViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) holder;
                GoodsAttributeBean bean = lists.get(position);
                vh.itemGoodsAttributeName.setText(bean.getAttributeKey());

                List<String> datas = bean.getChildrenList();
                TagAdapter<String> adapter = new TagAdapter<String>(datas) {
                    @Override
                    public View getView(FlowLayout parent, int position, String str) {
                        View view = LayoutInflater.from(activity).inflate(R.layout.item_goods_attribute_tag, parent, false);
                        TextView tv = view.findViewById(R.id.item_goods_attribute_tag);
                        tv.setText(str);
                        return view;
                    }
                };
                vh.itemGoodsAttributeValue.setAdapter(adapter);
                adapter.setSelectedList(bean.getPreClickPosition());
                vh.itemGoodsAttributeValue.setOnSelectListener(selectPosSet -> {
                    int preClickPosition = bean.getPreClickPosition();
                    if (selectPosSet.size() == 0) {
                        //点击的取消
                        adapter.setSelectedList(preClickPosition);
                        return;
                    }
                    //获取这次点击效果
                    for (Integer pos : selectPosSet) {
                        if (pos != preClickPosition) {
                            adapter.setSelectedList(pos);
                            map.put(bean.getAttributeKey(), bean.getChildrenList().get(pos));
                        }
                    }
                    Set<String> keySet = map.keySet();
                    StringBuilder builder = new StringBuilder();
                    for (String key : keySet) {
                        //当前map的值不为null
                        if (!TextUtils.isEmpty(map.get(key))) {
                            builder.append(key).append(",").append(map.get(key))
                                    .append(";");
                        }
                    }
                    String s = builder.toString();
                    //去掉最后的";"
                    s = s.substring(0, s.length() - 1);
                    List<GoodsAttribute> list = new ArrayList<>();
                    for (int i = 0; i < listPre.size(); i++) {
                        GoodsAttribute goodsAttribute = listPre.get(i);
                        String attribute_value = goodsAttribute.getAttribute_value();
                        if (attribute_value.contains(s)) {
                            list.add(goodsAttribute);
                        }
                    }
                    if (list.size() == 0) {
                        //当前商品不存在
                        Log.i("onBindViewHolder: ", "==============");
                        for (int i = 0; i < lists.size(); i++) {
                            if (position == i) {
                                continue;
                            }
                            GoodsAttributeBean attributeBean = lists.get(i);
                            attributeBean.setPreClickPosition(-1);
                            map.put(attributeBean.getAttributeKey(), "");
                        }
                        goodsAttribute = null;
                        notifyDataSetChanged();
                    } else if (list.size() == 1
                            && list.get(0).getAttribute_value().equals(s)) {
                        //当前商品
                        Log.i("onBindViewHolder: ", "--------------");
                        goodsAttribute = list.get(0);
                        setViewData(goodsAttribute);
                    }

                    for (Integer i : vh.itemGoodsAttributeValue.getSelectedList()) {
                        //记录本次点击效果
                        bean.setPreClickPosition(i);
                    }


                });
            } else {
                BottomViewHolder vh = (BottomViewHolder) holder;
                vh.shopNumber.setText(shopCount + "");
                vh.shopAdd.setOnClickListener(v -> {
                    if (shopCount > 99999) {
                        showTips("抱歉，数量太多");
                        return;
                    }
                    shopCount = shopCount + num_span;
                    vh.shopNumber.setText(shopCount + "");
                });
                vh.shopDetails.setOnClickListener(v -> {
                    if (shopCount == num_start) {
                        showTips("抱歉,数量不能少于" + num_start);
                        return;
                    }
                    shopCount = shopCount - num_span;
                    vh.shopNumber.setText(shopCount + "");
                });
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            if (lists.size() == 0) {
                return 0;
            }
            return lists.size() + 1;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_goods_attribute_name)
        TextView itemGoodsAttributeName;
        @BindView(R.id.item_goods_attribute_value)
        TagFlowLayout itemGoodsAttributeValue;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_details)
        Button shopDetails;
        @BindView(R.id.shop_number)
        EditText shopNumber;
        @BindView(R.id.shop_add)
        Button shopAdd;

        public BottomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            shopNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s)) {
                        s = "0";
                    }
                    shopCount = Integer.parseInt(s.toString());
                    if (shopCount < num_start) {
                        shopCount = num_start;
                        shopNumber.setText("1");
                    }
                    if (shopCount > 99999) {
                        shopCount = 99999;
                        shopNumber.setText("99999");
                    }


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

}
