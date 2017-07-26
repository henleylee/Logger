package com.liyunlong.logger.printer;

import android.util.Log;

/**
 * Logcat打印助手-输出日志信息到Logcat
 *
 * @author liyunlong
 * @date 2017/7/24 10:33
 */
public class LogcatPrinter extends Printer {

    @Override
    protected void printLog(int level, String tag, String message) {
        switch (level) {
            case Log.VERBOSE:
                Log.v(tag, message);
                break;
            case Log.INFO:
                Log.i(tag, message);
                break;
            case Log.DEBUG:
                Log.d(tag, message);
                break;
            case Log.WARN:
                Log.w(tag, message);
                break;
            case Log.ERROR:
                Log.e(tag, message);
                break;
            case Log.ASSERT:
                Log.wtf(tag, message);
                break;
            default:
                break;
        }
    }

}
