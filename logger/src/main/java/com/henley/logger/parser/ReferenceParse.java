package com.henley.logger.parser;

import com.henley.logger.utils.LogConvert;

import java.lang.ref.Reference;

/**
 * {@link Reference}解析器
 *
 * @author Henley
 * @date 2017/7/21 17:41
 */
public class ReferenceParse implements IParser<Reference> {

    private static final String HEADER = "%s<%s> ";

    @Override
    public Class<Reference> parseClassType() {
        return Reference.class;
    }

    @Override
    public String parseString(Reference reference) {
        if (reference != null) {
            String simpleName = reference.getClass().getName();
            Object actual = reference.get();
            String actualName = actual.getClass().getSimpleName();
            String header = String.format(LOCALE, HEADER, simpleName, actualName);
            StringBuilder builder = new StringBuilder(header);
            builder.append("{")
                    .append("→")
                    .append(LogConvert.objectToString(actual))
                    .append("}");
            return builder.toString();
        }
        return null;
    }

}
