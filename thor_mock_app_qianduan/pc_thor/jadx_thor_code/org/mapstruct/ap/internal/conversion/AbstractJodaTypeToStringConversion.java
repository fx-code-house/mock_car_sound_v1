package org.mapstruct.ap.internal.conversion;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

/* loaded from: classes3.dex */
public abstract class AbstractJodaTypeToStringConversion extends SimpleConversion {
    protected abstract String formatStyle();

    protected abstract String parseMethod();

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return conversionString(conversionContext, "print") + ".trim()";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        if (conversionContext.getDateFormat() != null) {
            return Collections.singleton(conversionContext.getTypeFactory().getType(JodaTimeConstants.DATE_TIME_FORMAT_FQN));
        }
        return org.mapstruct.ap.internal.util.Collections.asSet(conversionContext.getTypeFactory().getType(JodaTimeConstants.DATE_TIME_FORMAT_FQN), conversionContext.getTypeFactory().getType(Locale.class));
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return conversionString(conversionContext, parseMethod());
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if (conversionContext.getDateFormat() != null) {
            return Collections.singleton(conversionContext.getTypeFactory().getType(JodaTimeConstants.DATE_TIME_FORMAT_FQN));
        }
        return org.mapstruct.ap.internal.util.Collections.asSet(conversionContext.getTypeFactory().getType(JodaTimeConstants.DATE_TIME_FORMAT_FQN), conversionContext.getTypeFactory().getType(Locale.class));
    }

    private String conversionString(ConversionContext conversionContext, String str) {
        return ConversionUtils.dateTimeFormat(conversionContext) + dateFormatPattern(conversionContext) + "." + str + "( <SOURCE> )";
    }

    private String dateFormatPattern(ConversionContext conversionContext) {
        StringBuilder sb = new StringBuilder(".forPattern(");
        String dateFormat = conversionContext.getDateFormat();
        if (dateFormat == null) {
            sb.append(defaultDateFormatPattern(conversionContext));
        } else {
            sb.append(" \"");
            sb.append(dateFormat);
            sb.append("\"");
        }
        sb.append(" )");
        return sb.toString();
    }

    private String defaultDateFormatPattern(ConversionContext conversionContext) {
        return StringUtils.SPACE + ConversionUtils.dateTimeFormat(conversionContext) + ".patternForStyle( \"" + formatStyle() + "\", " + ConversionUtils.locale(conversionContext) + ".getDefault() )";
    }
}
