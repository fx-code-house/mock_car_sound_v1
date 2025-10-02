package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class BigDecimalToPrimitiveConversion extends SimpleConversion {
    private final Class<?> targetType;

    public BigDecimalToPrimitiveConversion(Class<?> cls) {
        this.targetType = cls;
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>." + this.targetType.getName() + "Value()";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getFromExpression(ConversionContext conversionContext) {
        return ConversionUtils.bigDecimal(conversionContext) + ".valueOf( <SOURCE> )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(BigDecimal.class));
    }
}
