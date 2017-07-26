package com.liyunlong.logger.config;

import android.util.Log;

import com.liyunlong.logger.parser.IParser;
import com.liyunlong.logger.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志配置信息
 *
 * @author liyunlong
 * @date 2017/7/21 18:12
 */
public class LogConfig {

    private boolean isLogEnabled; // 是否启用日志输出
    private String commonTag; // 公共Tag
    private boolean showFormat; // 是否打印排版线条
    private boolean showThreadInfo; // 是否打印线程信息
    private boolean showMethodInfo; // 是否打印方法信息(类名/方法名/行号)
    private int logMinLevel; // 日志最小显示级别
    private List<IParser> parseList; // 自定义解析器集合

    public LogConfig() {
        this.isLogEnabled = true;
        this.showFormat = false;
        this.showThreadInfo = false;
        this.showMethodInfo = false;
        this.commonTag = Constants.TAG;
        this.logMinLevel = Log.VERBOSE;
        this.parseList = new ArrayList<>();
        this.addParserClass(Constants.DEFAULT_PARSE_CLASS);
    }

    /**
     * 生成默认的{@link LogConfig}
     */
    public static LogConfig getDefault() {
        return new LogConfig();
    }

    /**
     * 返回是否启用日志输出
     */
    public boolean isLogEnabled() {
        return isLogEnabled;
    }

    /**
     * 设置是否启用日志输出
     */
    public LogConfig setLogEnabled(boolean enabled) {
        this.isLogEnabled = enabled;
        return this;
    }

    /**
     * 返回公共标签
     */
    public String getCommonTag() {
        return commonTag;
    }

    /**
     * 设置公共标签
     */
    public LogConfig setCommonTag(String prefix) {
        this.commonTag = prefix;
        return this;
    }

    /**
     * 返回是否打印排版线条
     */
    public boolean isShowFormat() {
        return showFormat;
    }

    /**
     * 设置是否打印排版线条
     */
    public LogConfig setShowFormat(boolean showFormat) {
        this.showFormat = showFormat;
        return this;
    }

    /**
     * 返回是否打印线程信息
     */
    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    /**
     * 设置是否打印线程信息
     */
    public LogConfig setShowThreadInfo(boolean showThreadInfo) {
        this.showThreadInfo = showThreadInfo;
        return this;
    }

    /**
     * 返回是否打印方法信息
     */
    public boolean isShowMethodInfo() {
        return showMethodInfo;
    }

    /**
     * 设置是否打印方法信息
     */
    public LogConfig setShowMethodInfo(boolean showMethodInfo) {
        this.showMethodInfo = showMethodInfo;
        return this;
    }

    /**
     * 返回日志最小输出级别
     */
    public int getLogMinLevel() {
        return logMinLevel;
    }

    /**
     * 设置日志最小输出级别
     */
    public void setLogMinLevel(int logMinLevel) {
        this.logMinLevel = logMinLevel;
    }

    /**
     * 返回解析器集合
     */
    public List<IParser> getParseList() {
        return parseList;
    }

    /**
     * 添加解析器
     */
    public LogConfig addParser(IParser parser) {
        parseList.add(0, parser);
        return this;
    }

    /**
     * 添加默认解析器
     */
    private LogConfig addParserClass(Class<? extends IParser>... classes) {
        for (Class<? extends IParser> clazz : classes) {
            try {
                parseList.add(0, clazz.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return this;
    }

}
