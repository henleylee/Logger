package com.liyunlong.logger.printer;

import android.text.TextUtils;
import android.util.Log;

import com.liyunlong.logger.Logger;
import com.liyunlong.logger.config.LogConfig;
import com.liyunlong.logger.utils.Constants;
import com.liyunlong.logger.utils.LogConvert;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.MissingFormatArgumentException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 日志树功能实现
 *
 * @author liyunlong
 * @date 2017/7/24 9:31
 */
public abstract class Printer implements IPrinter {

    private final LogConfig mLogConfig;
    private final ThreadLocal<String> localTags = new ThreadLocal<>();

    protected Printer() {
        mLogConfig = Logger.getLogConfig();
    }

    public IPrinter setTag(String tag) {
        if (!TextUtils.isEmpty(tag) && mLogConfig.isLogEnabled()) {
            localTags.set(tag);
        }
        return this;
    }

    @Override
    public void v(String message, Object... args) {
        logString(Log.VERBOSE, null, message, args);
    }

    @Override
    public void v(String tag, String message, Object... args) {
        logString(Log.VERBOSE, tag, message, args);
    }

    @Override
    public void v(Object object) {
        logObject(Log.VERBOSE, null, object);
    }

    @Override
    public void v(String tag, Object object) {
        logObject(Log.VERBOSE, tag, object);
    }

    @Override
    public void d(String message, Object... args) {
        logString(Log.DEBUG, null, message, args);
    }

    @Override
    public void d(String tag, String message, Object... args) {
        logString(Log.DEBUG, tag, message, args);
    }

    @Override
    public void d(Object object) {
        logObject(Log.DEBUG, null, object);
    }

    @Override
    public void d(String tag, Object object) {
        logObject(Log.DEBUG, tag, object);
    }

    @Override
    public void i(String message, Object... args) {
        logString(Log.INFO, null, message, args);
    }

    @Override
    public void i(String tag, String message, Object... args) {
        logString(Log.INFO, tag, message, args);
    }

    @Override
    public void i(Object object) {
        logObject(Log.INFO, null, object);
    }

    @Override
    public void i(String tag, Object object) {
        logObject(Log.INFO, tag, object);
    }

    @Override
    public void w(String message, Object... args) {
        logString(Log.WARN, null, message, args);
    }

    @Override
    public void w(String tag, String message, Object... args) {
        logString(Log.WARN, tag, message, args);
    }

    @Override
    public void w(Object object) {
        logObject(Log.WARN, null, object);
    }

    @Override
    public void w(String tag, Object object) {
        logObject(Log.WARN, tag, object);
    }

    @Override
    public void e(String message, Object... args) {
        logString(Log.ERROR, null, message, args);
    }

    @Override
    public void e(String tag, String message, Object... args) {
        logString(Log.ERROR, tag, message, args);
    }

    @Override
    public void e(Object object) {
        logObject(Log.ERROR, null, object);
    }

    @Override
    public void e(String tag, Object object) {
        logObject(Log.ERROR, tag, object);
    }

    @Override
    public void wtf(String message, Object... args) {
        logString(Log.ASSERT, null, message, args);
    }

    @Override
    public void wtf(String tag, String message, Object... args) {
        logString(Log.ASSERT, tag, message, args);
    }

    @Override
    public void wtf(Object object) {
        logObject(Log.ASSERT, null, object);
    }

    @Override
    public void wtf(String tag, Object object) {
        logObject(Log.ASSERT, tag, object);
    }

    @Override
    public void json(String json) {
        json(null, json);
    }

    @Override
    public void json(String tag, String json) {
        if (TextUtils.isEmpty(json)) {
            d(tag, "JSON{json is empty}");
            return;
        }
        int indent = 4;
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String msg = jsonObject.toString(indent);
                i(tag, msg);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String msg = jsonArray.toString(indent);
                i(tag, msg);
            }
        } catch (JSONException e) {
            e(tag, "json = " + json + "\n\n" + e.toString());
        }
    }

    @Override
    public void xml(String xml) {
        xml(null, xml);
    }

    @Override
    public void xml(String tag, String xml) {
        if (TextUtils.isEmpty(xml)) {
            d(tag, "XML{xml is empty}");
            return;
        }
        try {
            xml = xml.trim();
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            i(tag, xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(tag, "xml = " + xml + "\n\n" + e.toString());
        }
    }

    private void logObject(int level, String tag, Object object) {
        logString(level, tag, LogConvert.objectToString(object));
    }

    private void logString(int level, String tag, String message, Object... args) {
        logString(level, false, tag, message, args);
    }

    private void logString(int level, boolean isPart, String tag, String message, Object... args) {
        if (!mLogConfig.isLogEnabled()) {//判定是否显示日志
            return;
        }
        if (level < mLogConfig.getLogMinLevel()) {//判断日志显示最小级别
            return;
        }
        if (TextUtils.isEmpty(tag)) {
            tag = generateTag();
        }
        //判断信息是否超过一行最大显示
        if (message.length() > Constants.LINE_MAX) { // 超过一行
            if (mLogConfig.isShowFormat()) { // 是否打印排版线条
                printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_TOP));
                if (mLogConfig.isShowThreadInfo()) { // 是否打印线程信息
                    printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_NORMAL) + getThreadInfo());
                    printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_CENTER));
                }
                if (mLogConfig.isShowMethodInfo()) { // 是否打印方法信息(类名/方法名/行号)
                    printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_NORMAL) + getTopStackInfo());
                    printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_CENTER));
                }
            }
            for (String subMsg : LogConvert.largeStringToList(message)) {
                logString(level, tag, subMsg, true, args);
            }
            if (mLogConfig.isShowFormat()) {
                printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_BOTTOM));
            }
        } else {
            if (args != null && args.length > 0) {//有格式化参数
                try {
                    message = String.format(message, args);
                } catch (MissingFormatArgumentException e) {
                    e.printStackTrace();
                }
            }
            //判断是否显示排版线条
            if (mLogConfig.isShowFormat()) { // 是否打印排版线条
                //判定是否需要分段显示
                if (isPart) {//需要分段显示
                    for (String sub : message.split(Constants.LINE_SEPARATOR)) {
                        printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_NORMAL) + sub);
                    }
                } else {//不需要分段显示
                    printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_TOP));
                    if (mLogConfig.isShowThreadInfo()) { // 是否打印线程信息
                        printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_NORMAL) + getThreadInfo());
                        printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_CENTER));
                    }
                    if (mLogConfig.isShowMethodInfo()) { // 是否打印方法信息(类名/方法名/行号)
                        printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_NORMAL) + getTopStackInfo());
                        printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_CENTER));
                    }
                    for (String sub : message.split(Constants.LINE_SEPARATOR)) {
                        printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_NORMAL) + sub);
                    }
                    printLog(level, tag, LogConvert.printDividingLine(Constants.DIVIDER_BOTTOM));
                }
            } else { // 直接显示
                StringBuilder builder = new StringBuilder();
                if (mLogConfig.isShowThreadInfo()) { // 是否打印线程信息
                    builder.append(getThreadInfo()).append(Constants.LINE_SEPARATOR);
                }
                if (mLogConfig.isShowMethodInfo()) { // 是否打印方法信息(类名/方法名/行号)
                    builder.append(getTopStackInfo()).append(Constants.LINE_SEPARATOR);
                }
                builder.append(message);
                printLog(level, tag, builder.toString());
            }
        }
    }

    /**
     * 输出日志的具体实现方式，可以是打印、文件存储等
     *
     * @param level
     * @param tag
     * @param message
     */
    protected abstract void printLog(int level, String tag, String message);

    /**
     * 生成标签
     */
    private String generateTag() {
        String tempTag = localTags.get();
        if (!TextUtils.isEmpty(tempTag)) {
            localTags.remove();
            return tempTag;
        }
        return mLogConfig.getCommonTag();
    }

    /**
     * 返回线程信息
     */
    private String getThreadInfo() {
        Thread thread = Thread.currentThread();
        StringBuilder builder = new StringBuilder();
        builder.append("Thread: ")
                .append(thread.getName())
                .append(" [")
                .append("ID: ")
                .append(thread.getId())
                .append(", ")
                .append("Priority: ")
                .append(thread.getPriority())
                .append(", ")
                .append("State: ")
                .append(thread.getState().name())
                .append(", ")
                .append("Group: ")
                .append(thread.getThreadGroup() == null ? null : thread.getThreadGroup().getName())
                .append("]");
        return builder.toString();
    }

    /**
     * 获取顶部堆栈信息
     */
    private String getTopStackInfo() {
        StackTraceElement caller = getCurrentStackTrace();
        if (caller == null) {
            return "";
        }
        String callerClazzName = caller.getClassName();
        String simpleClassName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        String methodName = caller.getMethodName();
        String fileName = caller.getFileName();
        int lineNumber = caller.getLineNumber();
        String stackInfo;
        if (caller.isNativeMethod()) {
            stackInfo = "(Native Method)";
        } else {
            if (fileName != null && lineNumber >= 0) {
                stackInfo = String.format("(%s:%d)", fileName, lineNumber);
            } else if (fileName != null) {
                stackInfo = String.format("(%s)", fileName);
            } else {
                stackInfo = "(Unknown Source)";
            }
        }
        String stackTrace = "%s.%s%s";
        return String.format(stackTrace, simpleClassName, methodName, stackInfo);
    }

    /**
     * 获取当前堆栈信息
     */
    private StackTraceElement getCurrentStackTrace() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(trace, Logger.class);
        if (stackOffset == -1) {
            return null;
        }
        return trace[stackOffset];
    }

    /**
     * 获取堆栈信息下标
     *
     * @param trace
     * @param clazz
     */
    private int getStackOffset(StackTraceElement[] trace, Class clazz) {
        for (int i = Constants.MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (clazz.equals(Logger.class)
                    && i < trace.length - 1
                    && trace[i + 1].getClassName().equals(Logger.class.getName())) {
                continue;
            }
            if (name.equals(clazz.getName())) {
                return ++i;
            }
        }
        return -1;
    }

}
