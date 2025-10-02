package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;

/* loaded from: classes3.dex */
public class PrimitiveToPrimitiveConversion extends SimpleConversion {
    private final Class<?> sourceType;

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>";
    }

    public PrimitiveToPrimitiveConversion(Class<?> cls) {
        this.sourceType = cls;
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getFromExpression(ConversionContext conversionContext) {
        return "(" + this.sourceType + ") <SOURCE>";
    }
}
