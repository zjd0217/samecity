package com.wxkj.tongcheng.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.util.Base64;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DecimalFormat;

/**
 * Created by cheng on 2018/10/7.
 */

public class Util {

    /**
     * 把分转换成元
     *
     * @param fen
     * @return
     */
    public static String fenToYuan(int fen) {
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        return df.format((float) fen / 100);
    }

    /**
     * 完成刷新加载
     *
     * @param refreshLayout
     */
    public static void finishRefreshLoadMore(SmartRefreshLayout refreshLayout) {
        try {
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
        } catch (Exception e) {

        }
    }

    /**
     * 获取后几页的数据
     *
     * @param page
     * @return
     */
    public static String getPage(int page) {
        return (page * 10) + ",10";
    }

    /**
     * string转md5
     *
     * @param str
     * @return
     */
    public static String strToMD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
            byte messageDigest[] = md5.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", b));
            }
            return hexString.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * dp转换成px
     */
    public static int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转换成px
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转换成sp
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * base64转Bitmap
     *
     * @param string
     * @return
     */
    public static Bitmap stringToBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * mp3文件转为Base64
     */
    public static String mp3ToBase64(String path) {
        File file = new File(path);
        return mp3ToBase64(file);
    }

    /**
     * mp3文件转为Base64
     */
    public static String mp3ToBase64(File file) {
        try {
            InputStream in = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.close();
            in.close();
            return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * base64文件转为mp3
     */
    public static File base64ToMp3(Context context, File file) {
        return base64ToMp3(context, file.getAbsoluteFile());
    }

    /**
     * base64文件转为mp3
     */
    public static File base64ToMp3(Context context, String base64Str) {
        FileOutputStream outputStream = null;
        File tempFile = null;
        try {
            tempFile = new File(context.getApplicationContext().getCacheDir(), "temp" + System.currentTimeMillis() + ".mp3");
            byte[] audioByte = Base64.decode(base64Str, Base64.DEFAULT);
            outputStream = new FileOutputStream(tempFile);
            outputStream.write(audioByte, 0, audioByte.length);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }

    public static void playerMp3(File file) {
        playerMp3(file.getAbsoluteFile());
    }

    public static void playerMp3(String path) {
        MediaPlayer player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}