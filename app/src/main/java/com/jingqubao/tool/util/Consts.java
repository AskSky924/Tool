package com.jingqubao.tool.util;

import android.os.Environment;

/**
 * Created by AskSky on 2015/10/30
 */
public class Consts {

    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tool";
    public static final String LOG_PATH = FILE_PATH + "/LOG"; //日志路径
    public static final String CACHE_PATH = FILE_PATH + "/cache"; //缓存路径
    public static final String MY_LOG = "my_log.txt"; //缓存路径
    public static final String SYSTEM_LOG = "system_log.txt"; //缓存路径

    //信息提示
    public static class NetErrorCode {
        public static final int ERROR_SUCCESS = 0x001;
        public static final int ERROR_NETWORK_ISSUE = 0x002;
        public static final int USER_TOKEN_ERROR = 0x003;
        public static final int ERROR_UNKNOWN = 0x000;
    }

    //网络状态
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
}
