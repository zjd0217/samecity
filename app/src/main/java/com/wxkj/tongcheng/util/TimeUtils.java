package com.wxkj.tongcheng.util;

/**
 * @author Liu haijun
 * @create 2018/10/14 0014
 * @Describe 将时间戳转为时间
 */
public class TimeUtils {

    /**
     * 将时间转为 天:小时:分钟:秒
     *
     * @param time
     *
     * @return
     */
    public static String ddHHmmss(int time) {
        if (time < 0) {
            return "00:00:00:00";
        }
        int day, h, m, s;
        day = time / (24 * 60 * 60);
        h = time % (24 * 60 * 60) / (60 * 60);
        m = time % (60 * 60) / 60;
        s = time % 60;
        return "" + (day < 10 ? ("0" + day) : day) + ":"
                + (h < 10? ("0" + h) : h) + ":"
                + (m < 10 ? ("0" + m) : m) + ":"
                + (s < 10 ? ("0" + s) : s);
    }




}
