package com.liyunlong.logger.utils;

import com.liyunlong.logger.parser.BitmapParse;
import com.liyunlong.logger.parser.BundleParse;
import com.liyunlong.logger.parser.CollectionParse;
import com.liyunlong.logger.parser.IParser;
import com.liyunlong.logger.parser.IntentParse;
import com.liyunlong.logger.parser.MapParse;
import com.liyunlong.logger.parser.MessageParse;
import com.liyunlong.logger.parser.ReferenceParse;
import com.liyunlong.logger.parser.SparseArrayParse;
import com.liyunlong.logger.parser.ThrowableParse;
import com.liyunlong.logger.parser.UriParse;

/**
 * 日志常量
 *
 * @author liyunlong
 * @date 2017/7/21 17:00
 */
public class Constants {

    /** TAG */
    public static final String TAG = "Logger";
    /** 换行符 */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    /** 每行最大日志长度 */
    public static final int LINE_MAX = 1024 * 3;
    /** 解析属性最大层级 */
    public static final int MAX_CHILD_LEVEL = 2;
    /** 堆栈信息下标 */
    public static final int MIN_STACK_OFFSET = 5;

    /** 分割线(顶部) */
    public static final int DIVIDER_TOP = 1;
    /** 分割线(底部) */
    public static final int DIVIDER_BOTTOM = 2;
    /** 分割线(中间) */
    public static final int DIVIDER_CENTER = 4;
    /** 分割线(普通) */
    public static final int DIVIDER_NORMAL = 3;

    /** 默认支持解析库 */
    public static final Class<? extends IParser>[] DEFAULT_PARSE_CLASS = new Class[]{
            UriParse.class,
            BundleParse.class,
            IntentParse.class,
            MessageParse.class,
            CollectionParse.class,
            SparseArrayParse.class,
            MapParse.class,
            BitmapParse.class,
            ThrowableParse.class,
            ReferenceParse.class
    };

}
