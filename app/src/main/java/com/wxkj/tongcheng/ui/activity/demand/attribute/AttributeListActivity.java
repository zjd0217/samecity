package com.wxkj.tongcheng.ui.activity.demand.attribute;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.adapter.AttributeListAdapter;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.demand.category.CategoryEntity;

import java.util.List;

public class AttributeListActivity extends BaseActivity {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.mTvTitle)
    TextView mTvTitle;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_attribute_list).build();
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
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initView() {
        super.initView();
        String json = getIntent().getStringExtra("json");
        List<CategoryEntity.ValueEntity> mList = new Gson().fromJson(json, new TypeToken<List<CategoryEntity.ValueEntity>>() {
        }.getType());
        String title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setAdapter(new AttributeListAdapter(getApplicationContext(), mList, entity -> {
            Intent intent = new Intent();
            intent.putExtra("entity", entity);
            setResult(Activity.RESULT_OK, intent);
            onBackPressed();
        }));
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
    }
}
