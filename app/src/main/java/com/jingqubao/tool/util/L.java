package com.jingqubao.tool.util;

import android.util.Log;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by AskSky on 2015/10/28
 * 统一Log管理类
 */
public class L {
    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "TIPS";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    //对网络访问进行Log输出
    public static void netD(String tag, int statusCode, Header[] headers, JSONObject response) {
        if (isDebug)
            Log.d(tag, "statusCode:" + statusCode + "\n headers" + Arrays.toString(headers) + "\n response:" + response);
    }

    public static void netE(String tag, int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        if (isDebug)
            Log.e(tag, "statusCode:" + statusCode + "\n headers" + Arrays.toString(headers) + "\n errorResponse:" + errorResponse + "\n throwable:" + throwable);
    }
}
