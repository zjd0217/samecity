package com.wxkj.tongcheng.bean;

import java.util.List;

/**
 * 热门搜索词汇
 * Created by cheng on 2018/10/12.
 */

public class HotSearchBean {


    private List<HotkeyBean> hotkey;

    public List<HotkeyBean> getHotkey() {
        return hotkey;
    }

    public void setHotkey(List<HotkeyBean> hotkey) {
        this.hotkey = hotkey;
    }

    public static class HotkeyBean {
        /**
         * key_name : 货币
         */

        private String key_name;

        public String getKey_name() {
            return key_name;
        }

        public void setKey_name(String key_name) {
            this.key_name = key_name;
        }
    }
}
