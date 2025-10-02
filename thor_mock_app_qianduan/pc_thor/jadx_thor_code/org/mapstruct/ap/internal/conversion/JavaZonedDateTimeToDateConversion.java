package org.mapstruct.ap.internal.conversion;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class JavaZonedDateTimeToDateConversion extends SimpleConversion {
    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return ConversionUtils.date(conversionContext) + ".from( <SOURCE>.toInstant() )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(Date.class));
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return ConversionUtils.zonedDateTime(conversionContext) + ".ofInstant( <SOURCE>.toInstant(), " + ConversionUtils.zoneId(conversionContext) + ".systemDefault() )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(ZonedDateTime.class), conversionContext.getTypeFactory().getType(ZoneId.class));
    }
}
