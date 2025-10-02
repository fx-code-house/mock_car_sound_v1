package org.mapstruct.ap.internal.conversion;

import java.sql.Date;
import java.util.Collections;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class DateToSqlDateConversion extends SimpleConversion {
    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return "new " + ConversionUtils.sqlDate(conversionContext) + "( <SOURCE>.getTime() )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.singleton(conversionContext.getTypeFactory().getType(Date.class));
    }
}
