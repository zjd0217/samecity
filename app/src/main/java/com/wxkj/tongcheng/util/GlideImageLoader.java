package com.wxkj.tongcheng.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

/**
 * 轮播图加载
 * Created by cheng on 2018/10/9.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        try {
            Glide.with(context).load(path).apply(new RequestOptions().transform(new CenterCrop())).into(imageView);
        }catch (Exception e){

        }
    }
}