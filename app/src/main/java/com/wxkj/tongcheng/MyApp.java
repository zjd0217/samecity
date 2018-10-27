package com.wxkj.tongcheng;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.ninegrid.NineGridView;

import org.xutils.x;

/**
 * Created by cheng on 2018/10/7.
 */

public class MyApp extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //xutil 配置
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志
        //加载九宫格图片
        NineGridView.setImageLoader(new GlideImageLoader());
        //初始化
        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }


    /** Glide 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.loading);
            requestOptions.error(R.drawable.load_failure);
            Glide.with(context).load(url)
                    .apply(requestOptions)
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
