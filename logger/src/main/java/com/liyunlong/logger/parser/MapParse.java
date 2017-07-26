package com.liyunlong.logger.parser;

import com.liyunlong.logger.utils.LogConvert;

import java.util.Map;
import java.util.Set;

/**
 * Map解析器
 *
 * @author liyunlong
 * @date 2017/7/21 17:32
 */
public class MapParse implements IParser<Map> {

    private static final String ITEM = "    %s -> %s";
    private static final String HEADER = "%s size = %d ";

    @Override
    public Class<Map> parseClassType() {
        return Map.class;
    }

    @Override
    public String parseString(Map map) {
        if (map != null) {
            String simpleName = map.getClass().getName();
            String header = String.format(LOCALE, HEADER, simpleName, map.size());
            StringBuilder builder = new StringBuilder(header);
            builder.append("[").append(LINE_SEPARATOR);
            if (!map.isEmpty()) {
                Set keys = map.keySet();
                for (Object key : keys) {
                    Object value = map.get(key);
                    if (value != null) {
                        if (value instanceof String) {
                            value = "\"" + value + "\"";
                        } else if (value instanceof Character) {
                            value = "\'" + value + "\'";
                        }
                    }
                    builder.append(String.format(ITEM, LogConvert.objectToString(key), LogConvert.objectToStringWithFormat(value)))
                            .append(LINE_SEPARATOR);
                }
            }
            builder.append("]");
            return builder.toString();
        }
        return null;
    }

}
