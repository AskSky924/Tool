package com.jingqubao.tool.service;

import android.content.Context;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by AskSky on 2015/10/30
 */
public class NetService {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static final String TAG = NetService.class.getSimpleName();


    public static String domain = "http://v2.jingqubao.com/api_v3/";
//    public static String domain = "http://v2test.jingqubao.com/api_v3/";

    //根据经纬度判断是否在景区
    public static String IS_IN_SCENIC = domain + "Scenic/is_in_scenic";


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    //根据经纬度判断是否在景区 IS_IN_SCENIC
    public static void iSInScenic(Context context, String lat, String lng, JsonHttpResponseHandler responseHandler) {
        RequestParams params = new RequestParams();
        params.put("lat", lat);
        params.put("lng", lng);
        client.post(context, IS_IN_SCENIC, params, responseHandler);
    }

}
