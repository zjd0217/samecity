package com.wxkj.tongcheng.ui.activity.h5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;

public class WebActivity extends BaseActivity {

    @BindView(R.id.mTvTitle)
    TextView mTvTitle;

    @BindView(R.id.mLayoutWebView)
    LinearLayout mLayoutWebView;

    private WebView mWebView;

    public static final String KEY_TITLE = "key_title";
    public static final String KEY_URL = "key_url";

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_web).build();
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
    protected void initData() {
        super.initData();
        initWebView();
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        String title = intent.getStringExtra(KEY_TITLE);
        mTvTitle.setText(title.length() > 0 ? title : "详情");
        String url = intent.getStringExtra(KEY_URL);
        if (url != null && url.length() > 0 && (url.startsWith("http:")
                || url.startsWith("https:")
                || url.startsWith("file:"))) {
            mWebView.loadUrl(url);
        } else {
            showErrorMsg(true, "链接错误");
        }
    }

    @Override
    protected void setListener() {
        super.setListener();
        findViewById(R.id.mIvBack).setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mLayoutWebView.removeAllViews();
        mWebView = new WebView(getApplicationContext());
        mLayoutWebView.addView(mWebView);
        WebSettings mSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSettings.setLoadsImagesAutomatically(true);
        } else {
            mSettings.setLoadsImagesAutomatically(false);
        }
        mSettings.setDefaultTextEncodingName("UTF-8");
        mSettings.setBlockNetworkImage(false);
        mSettings.setJavaScriptEnabled(true);
        mSettings.setSaveFormData(false);
        mSettings.setAppCacheEnabled(true);
        mSettings.setDatabaseEnabled(false);
        mSettings.setDomStorageEnabled(true);
        mSettings.setBuiltInZoomControls(false);
        mSettings.setSupportZoom(false);
        mSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setUseWideViewPort(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
}
