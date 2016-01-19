package com.jingqubao.tool.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by AskSky on 2016/1/12.
 * 工具类
 */
public class Utils {

    public static void saveLog(String name, String desc) {
        File file = new File(Consts.LOG_PATH);
        file.mkdirs();
        try {
            RandomAccessFile logFile = new RandomAccessFile(Consts.LOG_PATH + "/" + name, "rw");
            long length = logFile.length();
            logFile.seek(length);
            if (length != 0) {
                logFile.write(new String("\n\n").getBytes());
            }
            logFile.write(desc.getBytes());
            logFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
