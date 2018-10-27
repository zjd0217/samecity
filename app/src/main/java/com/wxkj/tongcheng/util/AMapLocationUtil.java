package com.wxkj.tongcheng.util;

import android.content.Context;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

/**
 * 高德地铁图定位
 */
public class AMapLocationUtil {

    private AMapLocationUtil() {
    }

    private static AMapLocationUtil _instance;

    public synchronized static AMapLocationUtil getInstance() {
        if (_instance == null) {
            synchronized (AMapLocationUtil.class) {
                if (_instance == null) {
                    _instance = new AMapLocationUtil();
                }
            }
        }
        return _instance;
    }

    private AMapLocationClient mLocationClient = null;
    private AMapLocation mAMapLocation = null;


    public void getCurrentLocation(Context context, AMapLocationCallBack callBack) {
        if (mAMapLocation != null && callBack != null) {
            callBack.onLocationChanged(mAMapLocation);
        } else {
            if (mLocationClient == null) {
                mLocationClient = new AMapLocationClient(context.getApplicationContext());
            }
            mLocationClient.setLocationListener(aMapLocation -> {
                mAMapLocation = aMapLocation;
                if (callBack != null) {
                    callBack.onLocationChanged(aMapLocation);
                }
                mLocationClient.stopLocation();
            });
            mLocationClient.setLocationOption(getOption());
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }
    }

    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    public void destroy() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    private AMapLocationClientOption getOption() {
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setMockEnable(true);
        return mLocationOption;
    }

    public interface AMapLocationCallBack {
        void onLocationChanged(AMapLocation location);
    }

}
