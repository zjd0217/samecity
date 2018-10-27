package com.wxkj.tongcheng.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * 显示正方形图片
 */
public class CropSquareTransformation extends BitmapTransformation {

    private int mWidth;

    public CropSquareTransformation(int width) {
        this.mWidth = width;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if (mWidth == 0) {
            mWidth = Math.min(toTransform.getWidth(), toTransform.getHeight());
        }
        return cropBitmapToSquare(toTransform, mWidth);
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {

    }

    private Bitmap cropBitmapToSquare(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }
        Bitmap result = bitmap;
        int width = result.getWidth();
        int height = result.getHeight();
        int longerEdge = edgeLength * Math.max(width, height) / Math.min(width, height);
        int scaledWidth = width > height ? longerEdge : edgeLength;
        int scaledHeight = width > height ? edgeLength : longerEdge;
        Bitmap scaledBitmap;
        try {
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (scaledBitmap == null) {
            return null;
        }
        int xTopLeft = (scaledWidth - edgeLength) / 2;
        int yTopLeft = (scaledHeight - edgeLength) / 2;
        try {
            result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
}
