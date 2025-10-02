package org.mapstruct.ap.internal.conversion;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class JavaLocalDateTimeToDateConversion extends SimpleConversion {
    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return ConversionUtils.date(conversionContext) + ".from( <SOURCE>.toInstant( " + ConversionUtils.zoneOffset(conversionContext) + ".UTC ) )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(Date.class), conversionContext.getTypeFactory().getType(ZoneOffset.class));
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return ConversionUtils.localDateTime(conversionContext) + ".ofInstant( <SOURCE>.toInstant(), " + ConversionUtils.zoneId(conversionContext) + ".of( \"UTC\" ) )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(LocalDateTime.class), conversionContext.getTypeFactory().getType(ZoneId.class));
    }
}
