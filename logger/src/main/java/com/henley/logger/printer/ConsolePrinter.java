package com.henley.logger.printer;

/**
 * 控制台打印助手(输出日志信息到控制台)
 *
 * @author Henley
 * @date 2017/7/24 10:34
 */
public class ConsolePrinter extends Printer {

    @Override
    protected void printLog(int level, String tag, String message) {
        System.out.println(tag + "\t" + message);
    }

}
