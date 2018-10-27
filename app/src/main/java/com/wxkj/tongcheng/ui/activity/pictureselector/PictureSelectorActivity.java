package com.wxkj.tongcheng.ui.activity.pictureselector;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.PictureExternalPreviewActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.tools.ToastManage;
import com.luck.picture.lib.widget.longimage.SubsamplingScaleImageView;
import com.wxkj.tongcheng.R;
import com.wxkj.tongcheng.statuslayout.BaseActivity;
import com.wxkj.tongcheng.statuslayout.OnRetryListener;
import com.wxkj.tongcheng.statuslayout.StatusLayoutManager;
import com.wxkj.tongcheng.util.Util;
import com.wxkj.tongcheng.view.PhotoViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Liu haijun
 * @create 2018/10/12 0012
 * @Describe 图片预览的Activity
 */
public class PictureSelectorActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = "PictureSelectorActivity";

    PhotoViewPager pictureSelectorViewpager;
    LinearLayout pictureSelectorIndicator;

    private SimpleFragmentAdapter adapter;

    private static List<String> pictureLists = new ArrayList<>();
    private static int location;

    private int mNum;
    private PictureSelectorActivity context;
    /** 是否是Base64加密的图片 */
    private static boolean base64;


    public static void OpenPictureSelector(Context context, List<String> pictureList, int position) {
        OpenPictureSelector(context, false, pictureList, position);
    }

    public static void OpenPictureSelector(Context context, List<String> pictureList) {
        OpenPictureSelector(context, pictureList, 0);
    }

    /**
     * 打开图片预览
     *
     * @param context
     *         上下文
     * @param isBase64
     *         是否是base64加密的图片
     * @param pictureList
     *         图片集合
     * @param position
     *         当前的位置
     */
    public static void OpenPictureSelector(Context context, boolean isBase64, List<String> pictureList, int position) {
        base64 = isBase64;
        pictureLists = pictureList;
        location = position;
        context.startActivity(new Intent(context, PictureSelectorActivity.class));
    }

    @Override
    protected void initStatusLayout() {
        statusLayoutManager = StatusLayoutManager.newBuilder(this)
                .contentView(R.layout.activity_picture_selector)
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
    protected void initView() {
        //修改状态栏
        mImmersionBar.statusBarDarkFont(false, 0.2f)
                .statusBarColor(R.color.black).init();

        context = PictureSelectorActivity.this;
        pictureSelectorViewpager = findViewById(R.id.picture_selector_viewpager);
        pictureSelectorIndicator = findViewById(R.id.picture_selector_indicator);

        adapter = new SimpleFragmentAdapter();
        pictureSelectorViewpager.setAdapter(adapter);
        pictureSelectorViewpager.addOnPageChangeListener(this);
        //一张图片不显示指示器
        if (pictureLists.size() <= 1) {
            pictureSelectorIndicator.setVisibility(View.INVISIBLE);
        }
        setIndicator();
        //设置当前的
        pictureSelectorViewpager.setCurrentItem(location);
        //第一次显示小白点
        pictureSelectorIndicator.getChildAt(location).setEnabled(true);
        //
        mImmersionBar.statusBarDarkFont(false, 0.2f).init();

    }

    /**
     * 设置指示器
     */
    private void setIndicator() {
        View view;
        for (int i = 0; i < pictureLists.size(); i++) {
            String pic = pictureLists.get(i);
            //创建底部指示器(小圆点)
            view = new View(context);
            view.setBackgroundResource(R.drawable.picture_selector_background);
            view.setEnabled(false);
            //设置宽高
            int j = Util.dp2px(context, 7.5f);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(j, j);
            //设置间隔
            if (i != 0) {
                int leftMargin = Util.dp2px(context, 10f);
                layoutParams.setMargins(leftMargin, 0, 0, 0);
            }

            //添加到LinearLayout
            pictureSelectorIndicator.addView(view, layoutParams);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pictureSelectorIndicator.getChildAt(mNum).setEnabled(false);
        pictureSelectorIndicator.getChildAt(position).setEnabled(true);
        mNum = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 适配器
     */
    public class SimpleFragmentAdapter extends PagerAdapter {

        public int getCount() {
            return pictureLists.size();
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            View contentView = LayoutInflater.from(context)
                    .inflate(R.layout.item_picture_selector, container, false);
            PhotoView photoView = contentView.findViewById(R.id.mPhotoView);
            RequestOptions options = new RequestOptions().autoClone();
            options.placeholder(R.drawable.loading);
            options.error(R.drawable.load_failure);
            String string = pictureLists.get(position);
            Log.i(TAG, string);
            if (base64) {
                //是图片进行了base64加密
                photoView.setImageBitmap(Util.stringToBitmap(string));
            } else {
                Glide.with(context)
                        .load(string)
                        .apply(options)
                        .into(photoView);
            }
            //单击退出当前页面
            photoView.setOnClickListener(v -> finish());

            container.addView(contentView);

            return contentView;
        }
    }


}
