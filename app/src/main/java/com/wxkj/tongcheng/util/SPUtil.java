package com.wxkj.tongcheng.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wxkj.tongcheng.bean.UserInfo;

/**
 * sp类
 * Created by cheng on 2018/10/09.
 */

public class SPUtil {
    private volatile static SPUtil instance=null;
    private Context context;
    private SharedPreferences userinfo;

    private SPUtil(Context context){
        this.context=context.getApplicationContext();
        userinfo = this.context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
    }
    public static SPUtil getInstance(Context context){
        if (null==instance){
            synchronized (SPUtil.class){
                if (null==instance){
                    instance=new SPUtil(context);
                }
            }
        }
        return instance;
    }

    public void saveIntInfo(String key,int value){
        userinfo.edit().putInt(key, value).apply();
    }

    public void saveStringInfo(String key,String value){
        userinfo.edit().putString(key,value).apply();
    }

    public String getStringByKey(String key){
        return userinfo.getString(key,"");
    }

    public int getIntByKey(String key){
        return userinfo.getInt(key,0);
    }

    /**
     * 是否登录了
     * @return  true 登录了 false未登录
     */
    public boolean getLoginOrNot(){
        return !TextUtils.isEmpty(userinfo.getString("token",""));
    }

    public void saveLoginInfo(UserInfo info){
        saveIntInfo("user_id",info.getUser_id());
        saveIntInfo("spread_fg",info.getSpread_fg());
        saveIntInfo("shop_id",info.getShop_id());
        saveIntInfo("shop_valid_fg",info.getShop_valid_fg());
        saveIntInfo("sex",info.getSex());
        saveStringInfo("token",info.getToken());
        saveStringInfo("user_name",info.getUser_cname());
        saveStringInfo("head_portrait",info.getHead_portrait());
        saveStringInfo("phone",info.getPhone());
        saveStringInfo("birth",info.getBirth());
        saveStringInfo("user_wxid",info.getUser_wxid());
    }

    public int getUserId(){
        return getIntByKey("user_id");
    }

    public String getToken(){
        return getStringByKey("token");
    }

    /**
     * 清空某个键的值  退出登录只清空token!!!
     * @param key
     */
    public void removeByKey(String key){
        userinfo.edit().remove(key).apply();
    }
}
