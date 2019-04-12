package com.henley.logger.parser;

import com.henley.logger.utils.LogConstants;

import java.util.Locale;

/**
 * 解析器接口
 *
 * @author Henley
 * @date 2017/7/21 16:59
 */
public interface IParser<T> {

    String LINE_SEPARATOR = LogConstants.LINE_SEPARATOR;
    Locale LOCALE = Locale.getDefault();

    Class<T> parseClassType();

    String parseString(T t);
}
