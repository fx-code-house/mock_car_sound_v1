package org.mapstruct.ap.internal.conversion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.TypeConversion;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class DateToStringConversion implements ConversionProvider {
    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public Assignment to(ConversionContext conversionContext) {
        return new TypeConversion(Collections.asSet(conversionContext.getTypeFactory().getType(SimpleDateFormat.class)), java.util.Collections.emptyList(), getConversionExpression(conversionContext, "format"));
    }

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public Assignment from(ConversionContext conversionContext) {
        return new TypeConversion(Collections.asSet(conversionContext.getTypeFactory().getType(SimpleDateFormat.class)), Arrays.asList(conversionContext.getTypeFactory().getType(ParseException.class)), getConversionExpression(conversionContext, "parse"));
    }

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        return java.util.Collections.emptyList();
    }

    private String getConversionExpression(ConversionContext conversionContext, String str) {
        StringBuilder sb = new StringBuilder("new ");
        sb.append(ConversionUtils.simpleDateFormat(conversionContext));
        sb.append('(');
        if (conversionContext.getDateFormat() != null) {
            sb.append(" \"");
            sb.append(conversionContext.getDateFormat());
            sb.append("\" ");
        }
        sb.append(").");
        sb.append(str);
        sb.append("( <SOURCE> )");
        return sb.toString();
    }
}
