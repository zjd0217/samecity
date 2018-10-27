package com.wxkj.tongcheng.ui.activity.group.goodsdetail.evaluate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.AllEvaluateAdapter;
import com.wxkj.tongcheng.bean.EvaluateBean;
import com.wxkj.tongcheng.bean.GroupGoodsDetailBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.fragment.dialogfragment.GoodsAttributeDialog;
import com.wxkj.tongcheng.util.AmountUtil;
import com.wxkj.tongcheng.util.SPUtil;
import com.wxkj.tongcheng.util.Util;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/17 0017
 * @Describe 全部评价
 */
public class AllEvaluateActivity extends MvpBaseActivity<AllEvaluatePresenter>
        implements OnRetryListener, AllEvaluateView {

    @BindView(R.id.all_evaluate_flow_layout)
    TagFlowLayout allEvaluateFlowLayout;
    @BindView(R.id.all_evaluate_list)
    RecyclerView allEvaluateList;
    @BindView(R.id.all_evaluate_swipeRefreshLayout)
    SmartRefreshLayout allEvaluateSwipeRefreshLayout;
    @BindView(R.id.all_evaluate_collection)
    LinearLayout allEvaluateCollection;
    @BindView(R.id.all_evaluate_customer)
    LinearLayout allEvaluateCustomer;
    @BindView(R.id.all_evaluate_alone_buy)
    TextView allEvaluateAloneBuy;
    @BindView(R.id.all_evaluate_collage_buy)
    TextView allEvaluateCollageBuy;
    @BindView(R.id.all_evaluate_collection_img)
    ImageView allEvaluateCollectionImg;

    private boolean byUser;
    /** 页码 */
    private int page = 0;
    /** 商品相关 */
    private GroupGoodsDetailBean.GoodsBean goods;
    private GroupGoodsDetailBean.TeamBuyBean teamBuy;
    private int size = 0;
    private String grade_goods;
    private List<EvaluateBean> list = new ArrayList<>();
    private Context mContext;
    private AllEvaluateAdapter adapter;
    /** 上次选中标签的下标 */
    private int preLabel = 0;
    private int collection_id;
    private boolean isCollect;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_all_evaluate)
                .emptyDataView(R.layout.empty_data_layout)
                .errorView(R.layout.error_layout)
                .loadingView(R.layout.loading_layout)
                .netWorkErrorView(R.layout.network_error_layout)
                .onRetryListener(this)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return 0;
    }

    @Override
    protected String titleString() {
        return null;
    }


    @Override
    protected void setListener() {
        super.setListener();
        //下拉刷新
        allEvaluateSwipeRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 0;
            byUser = false;
            list.clear();
            presenter.getEvaluateList();

        });
        //上拉加载
        allEvaluateSwipeRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page++;
            byUser = true;
            presenter.getEvaluateList();
        });
    }

    @Override
    protected AllEvaluatePresenter initPresenter() {
        return new AllEvaluatePresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        mContext = this;
        Intent intent = getIntent();
        goods = (GroupGoodsDetailBean.GoodsBean) intent.getSerializableExtra("goods");
        teamBuy = (GroupGoodsDetailBean.TeamBuyBean) intent.getSerializableExtra("teamBuy");
        size = intent.getIntExtra("size", 0);
        presenter.getEvaluateList();
        collection_id = goods.getCollection_id();
        isCollect = collection_id > 0;
        allEvaluateCollectionImg.setImageResource(collection_id > 0 ?
                R.drawable.bottom_collected : R.drawable.bottom_collection);

        initFlowLayout();

        initBottom();
    }

    /**
     * 初始化底部
     */
    private void initBottom() {

        if (size > 0) {
            //拼团
            allEvaluateCollageBuy.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_current()) + "元\n发起"
                    + teamBuy.getMan_num_need() + "人拼单");

            int price_market = 0;
            if (SPUtil.getInstance(this).getIntByKey("spread_fg") == 0) {
                //普通会员
                price_market = goods.getPrice_current();
            }
            if (SPUtil.getInstance(this).getIntByKey("spread_fg") == 1) {
                //推广会员
                price_market = goods.getPrice_member();
            }
            allEvaluateAloneBuy.setText("￥" + AmountUtil.changeF2Y(price_market) + "元\n单独购买");
        } else {
            allEvaluateAloneBuy.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_current()) + "元\n当前价格");
            allEvaluateCollageBuy.setText("￥" + AmountUtil.changeF2Y(goods.getPrice_member()) + "元\n会员价");
        }
    }

    /**
     * 初始化流式布局
     */
    private void initFlowLayout() {
        List<String> datas = new ArrayList<>();
        datas.add("全部（" + (goods.getDiscuss_all()) + "）");
        datas.add("好评（" + goods.getDiscuss_good() + "）");
        datas.add("中评（" + goods.getDiscuss_normal() + "）");
        datas.add("差评（" + goods.getDiscuss_bad() + "）");

        TagAdapter<String> adapter = new TagAdapter<String>(datas) {
            @Override
            public View getView(FlowLayout parent, int position, String str) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag, parent, false);
                TextView tv = view.findViewById(R.id.item_tag);
                tv.setText(str);
                return view;
            }
        };
        allEvaluateFlowLayout.setAdapter(adapter);
        adapter.setSelectedList(0);
        allEvaluateFlowLayout.setOnSelectListener(selectPosSet -> {
            if (selectPosSet.size() == 0) {
                adapter.setSelectedList(preLabel);
            }
            for (Integer position : selectPosSet) {
                if (position == preLabel) {

                } else {
                    adapter.setSelectedList(position);
                    if (position == 0) {
                        //全部
                        grade_goods = "";
                    } else if (position == 1) {
                        //好评
                        grade_goods = "5";
                    } else if (position == 2) {
                        //中评
                        grade_goods = "3";
                    } else if (position == 3) {
                        //差评
                        grade_goods = "1";
                    }
                    presenter.getEvaluateList();
                }
            }
            for (Integer i : allEvaluateFlowLayout.getSelectedList()) {
                preLabel = i;
            }

        });
    }

    @Override
    protected void initView() {
        super.initView();

        adapter = new AllEvaluateAdapter(mContext);
        allEvaluateList.setLayoutManager(new LinearLayoutManager(mContext));
        allEvaluateList.setAdapter(adapter);

        //修改状态栏
        mImmersionBar.statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.colorfca).init();

    }


    @Override
    public void onRetry() {
        if (null != presenter) {
            statusLayoutManager.showLoading();
            byUser = false;
            presenter.getEvaluateList();
        }
    }

    @OnClick({R.id.all_evaluate_title_back, R.id.all_evaluate_title_share, R.id.all_evaluate_collection
            , R.id.all_evaluate_customer, R.id.all_evaluate_alone_buy, R.id.all_evaluate_collage_buy})
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.all_evaluate_title_back:
                finish();

                break;
            //分享
            case R.id.all_evaluate_title_share:
                finish();

                break;
            //收藏
            case R.id.all_evaluate_collection:
                if (isCollect) {
                    presenter.cancelCollect(collection_id);
                } else {
                    presenter.collectGoods();
                }
                break;
            //客服
            case R.id.all_evaluate_customer:

                break;
            //单独购买
            case R.id.all_evaluate_alone_buy:
                GoodsAttributeDialog goodsAttributeDialog = new GoodsAttributeDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("classify_id", goods.getClassify_id());
                bundle.putString("goods_pic", goods.getGoods_pic());
                bundle.putInt("num_start", goods.getNum_start());
                bundle.putInt("num_span", goods.getNum_span());
                goodsAttributeDialog.setArguments(bundle);
                goodsAttributeDialog.show(getSupportFragmentManager(), "goodsAttributeDialog1");

                break;
            //拼团购买
            case R.id.all_evaluate_collage_buy:


                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(byUser, msg);
        Util.finishRefreshLoadMore(allEvaluateSwipeRefreshLayout);
    }

    @Override
    public int getGoodsId() {
        return goods.getGoods_id();
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public String getGradeGoods() {
        return grade_goods;
    }

    @Override
    public void getEvaluateBeanList(List<EvaluateBean> list) {
        Util.finishRefreshLoadMore(allEvaluateSwipeRefreshLayout);
        statusLayoutManager.showContent();
        if (page == 0) {
            this.list.clear();
        }
        this.list.addAll(list);
        if (adapter != null) {
            adapter.setList(this.list);
        }
    }

    @Override
    public void success(boolean isCollect) {
        this.isCollect = isCollect;
        allEvaluateCollectionImg.setImageResource(isCollect ?
                R.drawable.bottom_collected : R.drawable.bottom_collection);
    }


}
