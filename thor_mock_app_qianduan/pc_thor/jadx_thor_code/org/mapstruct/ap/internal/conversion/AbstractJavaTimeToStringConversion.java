package org.mapstruct.ap.internal.conversion;

import java.time.format.DateTimeFormatter;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public abstract class AbstractJavaTimeToStringConversion extends SimpleConversion {
    protected abstract String defaultFormatterSuffix();

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return dateTimeFormatter(conversionContext) + ".format( <SOURCE> )";
    }

    private String dateTimeFormatter(ConversionContext conversionContext) {
        if (!Strings.isEmpty(conversionContext.getDateFormat())) {
            return ConversionUtils.dateTimeFormatter(conversionContext) + ".ofPattern( \"" + conversionContext.getDateFormat() + "\" )";
        }
        return ConversionUtils.dateTimeFormatter(conversionContext) + "." + defaultFormatterSuffix();
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return conversionContext.getTargetType().createReferenceName() + ".parse( " + parametersListForParsing(conversionContext) + " )";
    }

    private String parametersListForParsing(ConversionContext conversionContext) {
        StringBuilder sb = new StringBuilder("<SOURCE>");
        if (!Strings.isEmpty(conversionContext.getDateFormat())) {
            sb.append(", ");
            sb.append(dateTimeFormatter(conversionContext));
        }
        return sb.toString();
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(DateTimeFormatter.class));
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return !Strings.isEmpty(conversionContext.getDateFormat()) ? Collections.asSet(conversionContext.getTargetType(), conversionContext.getTypeFactory().getType(DateTimeFormatter.class)) : Collections.asSet(conversionContext.getTargetType());
    }
}
