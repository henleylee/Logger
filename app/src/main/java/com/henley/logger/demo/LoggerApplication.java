package com.henley.logger.demo;

import android.app.Application;
import android.util.Log;

import com.henley.logger.Logger;
import com.henley.logger.printer.ConsolePrinter;
import com.henley.logger.printer.FilePrinter;
import com.henley.logger.printer.LogcatPrinter;

/**
 * @author Henley
 * @date 2017/7/24 15:19
 */
public class LoggerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {

            Logger.getLogConfig()                   // 获取配置信息(可重新进行设置)
                    .setLogEnabled(true)            // 设置是否启用日志输出
                    .setShowThreadInfo(true)        // 设置是否打印线程信息
                    .setShowMethodInfo(true)        // 设置是否打印方法信息
                    .setLogMinLevel(Log.VERBOSE);   // 设置日志最小输出级别

            Logger.addPrinter(new ConsolePrinter());    // 添加控制台打印助手(输出日志信息到控制台)
            Logger.addPrinter(new FilePrinter(this));   // 添加文件打印助手(输出日志信息到文件)
            Logger.addPrinter(new LogcatPrinter(true)); // 添加Logcat打印助手-输出日志信息到Logcat

        }
    }
}
