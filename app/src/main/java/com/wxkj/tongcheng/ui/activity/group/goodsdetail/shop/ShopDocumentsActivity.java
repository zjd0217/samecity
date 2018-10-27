package com.wxkj.tongcheng.ui.activity.group.goodsdetail.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.ui.activity.pictureselector.PictureSelectorActivity;
import com.wxkj.tongcheng.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Liu haijun
 * @create 2018/10/20 0020
 * @Describe 店铺证件
 */
public class ShopDocumentsActivity extends BaseActivity {

    @BindView(R.id.shop_certificate)
    ImageView shopCertificate;
    @BindView(R.id.shop_licence)
    ImageView shopLicence;
    /** 营业执照 */
    private String shop_certificate;
    /** 许可证 */
    private String shop_licence;

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_shop_documents)
                .build();
        statusLayoutManager.showContent();
    }

    @Override
    protected int titleLayoutId() {
        return R.layout.base_title_layout;
    }

    @Override
    protected String titleString() {
        return "店铺证件";
    }

    @Override
    protected void initView() {
        super.initView();
        //修改状态栏
        mImmersionBar.statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.colorfca).init();
        if (!TextUtils.isEmpty(shop_certificate)) {
            shopCertificate.setImageBitmap(Util.stringToBitmap(shop_certificate));
        }
        if (!TextUtils.isEmpty(shop_licence)) {
            shopLicence.setImageBitmap(Util.stringToBitmap(shop_licence));
        }

    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        shop_certificate = intent.getStringExtra("shop_certificate");
        shop_licence = intent.getStringExtra("shop_licence");
    }

    @OnClick({R.id.shop_certificate, R.id.shop_licence})
    public void click(View view) {
        switch (view.getId()) {
            //许可证
            case R.id.shop_licence:
                List<String> list = new ArrayList<>();
                list.add(shop_licence);
                PictureSelectorActivity.OpenPictureSelector(this, true, list, 0);
                break;
            //营业执照
            case R.id.shop_certificate:
                list = new ArrayList<>();
                list.add(shop_certificate);
                PictureSelectorActivity.OpenPictureSelector(this, true, list, 0);

                break;


        }
    }

}
