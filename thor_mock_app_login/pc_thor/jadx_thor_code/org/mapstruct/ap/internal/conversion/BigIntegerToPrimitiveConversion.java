package org.mapstruct.ap.internal.conversion;

import java.math.BigInteger;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class BigIntegerToPrimitiveConversion extends SimpleConversion {
    private final Class<?> targetType;

    public BigIntegerToPrimitiveConversion(Class<?> cls) {
        this.targetType = cls;
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>." + this.targetType.getName() + "Value()";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getFromExpression(ConversionContext conversionContext) {
        return ConversionUtils.bigInteger(conversionContext) + ".valueOf( " + ((this.targetType == Float.TYPE || this.targetType == Double.TYPE) ? "(long) " : "") + "<SOURCE> )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(BigInteger.class));
    }
}
