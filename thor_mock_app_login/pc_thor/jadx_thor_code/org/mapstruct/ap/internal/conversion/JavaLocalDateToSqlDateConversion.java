package org.mapstruct.ap.internal.conversion;

import java.sql.Date;
import java.time.ZoneOffset;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class JavaLocalDateToSqlDateConversion extends SimpleConversion {
    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toLocalDate()";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return "new " + ConversionUtils.sqlDate(conversionContext) + "( <SOURCE>.atStartOfDay( " + ConversionUtils.zoneOffset(conversionContext) + ".UTC ).toInstant().toEpochMilli() )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(Date.class), conversionContext.getTypeFactory().getType(ZoneOffset.class));
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(ZoneOffset.class));
    }
}
