package com.jingqubao.tool.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * author：lsx
 * date：2015/12/3
 * description：定位工具类
 */
public class LocationUtils implements AMapLocationListener {

    private Context context;
    private LocationUtils locationUtils = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private double[] loc;
    public static double lat = 0;
    public static double lng = 0;

    private OnLocationListener locationListener;

    public LocationUtils(Context context) {
        this.context = context;
    }
//
//    public static synchronized LocationUtils getInstance(){
//        if (locationUtils == null){
//            locationUtils = new LocationUtils();
//        }
//        return locationUtils;
//    }

    private void init(long time_interval) {
        locationClient = new AMapLocationClient(context.getApplicationContext());
        // 设置定位监听
        locationClient.setLocationListener(this);
        locationOption = new AMapLocationClientOption();
        // //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        locationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        locationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒
        locationOption.setInterval(time_interval);
        //给定位客户端对象设置定位参数
        locationClient.setLocationOption(locationOption);
    }

    //启动定位
    public void startLocation(long time_interval, OnLocationListener locationListener) {
        this.locationListener = locationListener;
        init(time_interval);
        //ToastUtils.show(context, "正在获取您的位置...");
        locationClient.startLocation();
    }

    public void stopLocation() {
        locationClient.stopLocation();//停止定位
    }

    /*在对应的方法中调用*/
    public void onDestroy() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation loc) {
        if (null != loc) {
            String result = getLocationStr(loc);
            Log.d("tag", result);
        }
    }

    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @param //loc
     * @return
     */
    public synchronized String getLocationStr(AMapLocation location) {
        if (null == location) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {

            lat = location.getLatitude();
            lng = location.getLongitude();

            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");


            locationListener.onSuccess(location);

            if (location.getProvider().equalsIgnoreCase(
                    android.location.LocationManager.GPS_PROVIDER)) {
                // 以下信息只有提供者是GPS时才会有
                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : "
                        + location.getExtras().getInt("satellites", 0) + "\n");
            } else {
                // 供者是GPS时是没有以下信息的
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
            }
        } else {
            //定位失败
            sb.append("定位为失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
            locationListener.onError(location.getErrorCode(), location.getErrorInfo(), location.getLocationDetail());
        }
        return sb.toString();
    }

    public interface OnLocationListener {
        /*lng: 经度 lat: 纬度*/
        public void onSuccess(AMapLocation location);

        /*errorCode: 错误码  errorInfo: 错误信息 errorDetail: 错误描述*/
        public void onError(int errorCode, String errorInfo, String errorDetail);
    }
}
