package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class BigDecimalToBigIntegerConversion extends SimpleConversion {
    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toBigInteger()";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getFromExpression(ConversionContext conversionContext) {
        return "new " + ConversionUtils.bigDecimal(conversionContext) + "( <SOURCE> )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(BigDecimal.class));
    }
}
