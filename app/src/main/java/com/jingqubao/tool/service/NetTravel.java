package com.jingqubao.tool.service;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.jingqubao.tool.util.Consts;
import com.jingqubao.tool.util.L;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by AskSky on 2015/11/13
 */
public class NetTravel {
    public static final String TAG = NetTravel.class.getSimpleName();
    private static final String DATA = "data";

    private Context mContext;

    public NetTravel(Context context) {
        mContext = context;
    }

    public interface RequestCallback {
        void onResult(int errorCode, Object data, String... params);
    }

    //========================================公共接口==============================================

    public void isInScenic(String lat, String lng, final RequestCallback callback) {
        NetService.iSInScenic(mContext, lat, lng, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
                L.netD(TAG, statusCode, headers, response);
                if (callback == null) {
                    L.e(TAG, "callback is null!");
                    return;
                }
                try {
                    if (response.has(DATA)) {
                        JSONObject data = response.getJSONObject(DATA);
                        if (data.has("scenic_region_name")) {
                            String name = data.getString("scenic_region_name");
                            String rid = data.getString("rid");
                            callback.onResult(Consts.NetErrorCode.ERROR_SUCCESS, null, name, rid);
                        } else {
                            callback.onResult(Consts.NetErrorCode.ERROR_UNKNOWN, response.getString("msg"));
                        }
                    } else {
                        callback.onResult(Consts.NetErrorCode.ERROR_UNKNOWN, response.getString("msg"));
                    }
                } catch (Exception e) {
                    callback.onResult(Consts.NetErrorCode.ERROR_NETWORK_ISSUE, null);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                L.netE(TAG, statusCode, headers, throwable, errorResponse);
                if (callback == null) {
                    L.e(TAG, "callback is null!");
                    return;
                }
                callback.onResult(Consts.NetErrorCode.ERROR_UNKNOWN, null);
            }
        });
    }
}


//    @Override
//    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//        super.onSuccess(statusCode, headers, response);
//        L.netD(TAG, statusCode, headers, response);
//        if (callback == null) {
//            L.e(TAG, "callback is null!");
//            return;
//        }
//        try {
//            if (response.has(DATA)) {
//                JSONObject data = response.getJSONObject(DATA);
//            } else {
//                callback.onResult(Consts.NetErrorCode.ERROR_UNKNOWN, response.getString("msg"));
//            }
//        } catch (Exception e) {
//            callback.onResult(Consts.NetErrorCode.ERROR_NETWORK_ISSUE, null);
//        }
//    }
//
//    @Override
//    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//        super.onFailure(statusCode, headers, throwable, errorResponse);
//        Toast.makeText(mContext, mContext.getString(R.string.net_error), Toast.LENGTH_LONG).show();
//        L.netE(TAG, statusCode, headers, throwable, errorResponse);
//        if (callback == null) {
//            L.e(TAG, "callback is null!");
//            return;
//        }
//        callback.onResult(Consts.NetErrorCode.ERROR_UNKNOWN, null);
//    }