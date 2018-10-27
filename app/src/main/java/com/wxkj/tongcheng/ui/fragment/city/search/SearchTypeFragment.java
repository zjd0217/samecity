package com.wxkj.tongcheng.ui.fragment.city.search;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.bean.CitySearchBean;
import com.wxkj.tongcheng.bean.EventBusBean;
import com.wxkj.tongcheng.bean.HotSearchBean;
import com.wxkj.tongcheng.statuslayout.MvpBaseFragment;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.CodeUtil;
import com.wxkj.tongcheng.view.flowlayout.FlowLayout;
import com.wxkj.tongcheng.view.flowlayout.TagAdapter;
import com.wxkj.tongcheng.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.xutils.DbManager;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 附近需求 附近服务 搜索fragment
 * Created by cheng on 2018/10/10.
 */

public class SearchTypeFragment extends MvpBaseFragment<SearchTypePresenter> implements SearchTypeView, TagFlowLayout.OnTagClickListener, View.OnClickListener {
    private TagFlowLayout hot_search_layout,history_search_layout;
    private LinearLayout hot_layout,history_layout;
    private ImageView delete_history_img;

    private List<CitySearchBean> searchList=new ArrayList<>();
    private DbManager db;
    private int type;

    @Override
    protected SearchTypePresenter initPresenter() {
        return new SearchTypePresenter();
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(getContext())
                .contentView(R.layout.search_type_fragment_content_layout)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected void initData() {
        super.initData();
        findview();
        db = x.getDb(new DbManager.DaoConfig());
        //0是服务 1是需求
        type = getArguments().getInt("type",0);
    }

    @Override
    protected void initView() {
        super.initView();
        //获取热门搜索数据
        presenter.getHotData(type);
    }

    @Override
    public void showMsg(String msg) {
        showErrorMsg(false,msg);
    }

    @Override
    public void getHotSearchSuccess(HotSearchBean searchBean) {  //获取热门搜索词汇成功
        if (null==searchBean||searchBean.getHotkey()==null||searchBean.getHotkey().size()==0){
            hot_search_layout.setVisibility(View.GONE);
            hot_layout.setVisibility(View.GONE);
        }else {
            hot_search_layout.setVisibility(View.VISIBLE);
            hot_layout.setVisibility(View.VISIBLE);
            List<HotSearchBean.HotkeyBean> hotkey = searchBean.getHotkey();
            //设置适配器
            hot_search_layout.setAdapter(new TagAdapter<HotSearchBean.HotkeyBean>(hotkey) {
                @Override
                public View getView(FlowLayout parent, int position, HotSearchBean.HotkeyBean citySearchBean) {
                    TextView text= (TextView) LayoutInflater.from(getContext()).inflate(R.layout.city_search_item_layout, history_search_layout, false);
                    text.setText(citySearchBean.getKey_name());
                    return text;
                }
            });
            hot_search_layout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    EventBusBean bean = new EventBusBean();
                    bean.setCode(CodeUtil.CITY_HOT_HISTORY_SEARCH_ITEM);
                    bean.setName(hotkey.get(position).getKey_name());
                    EventBus.getDefault().post(bean);
                    return true;
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取历史搜索数据
        getHistorySearch();
    }

    @Override
    protected void setListener() {
        super.setListener();
        delete_history_img.setOnClickListener(this);
        history_search_layout.setOnTagClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_history_img:  //删除历史搜索
                deleteHistory();
                break;
        }
    }

    /**
     * 删除历史搜索
     */
    private void deleteHistory() {
        try {
            List<CitySearchBean> all = db.findAll(CitySearchBean.class);
            for (CitySearchBean bean:all){
                if (bean.getType()==type)
                    db.delete(bean);
            }
            getHistorySearch();
        }catch (Exception e){

        }
    }

    /**
     * 历史搜索 item点击
     */
    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        EventBusBean bean = new EventBusBean();
        bean.setCode(CodeUtil.CITY_HOT_HISTORY_SEARCH_ITEM);
        bean.setName(searchList.get(position).getContent());
        EventBus.getDefault().post(bean);
        return true;
    }

    /**
     * 获取历史搜索数据
     */
    private void getHistorySearch() {
        try {
            List<CitySearchBean> all = db.findAll(CitySearchBean.class);
            if (all.size()!=0){
                history_search_layout.setVisibility(View.VISIBLE);
                history_layout.setVisibility(View.VISIBLE);
                searchList.clear();
                for (CitySearchBean bean:all){
                    if (bean.getType()==type)
                        searchList.add(0,bean);
                }
                //设置适配器
                history_search_layout.setAdapter(new TagAdapter<CitySearchBean>(searchList) {
                    @Override
                    public View getView(FlowLayout parent, int position, CitySearchBean citySearchBean) {
                        TextView text= (TextView) LayoutInflater.from(getContext()).inflate(R.layout.city_search_item_layout, history_search_layout, false);
                        text.setText(citySearchBean.getContent());
                        return text;
                    }
                });
            }else {
                history_search_layout.setVisibility(View.GONE);
                history_layout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findview() {
        hot_search_layout=findViewById(R.id.hot_search_layout);
        history_search_layout=findViewById(R.id.history_search_layout);
        hot_layout=findViewById(R.id.hot_layout);
        history_layout=findViewById(R.id.history_layout);
        delete_history_img=findViewById(R.id.delete_history_img);
    }
}
