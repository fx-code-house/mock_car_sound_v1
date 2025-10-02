package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.util.NativeTypes;

/* loaded from: classes3.dex */
public class PrimitiveToWrapperConversion extends SimpleConversion {
    private final Class<?> sourceType;
    private final Class<?> targetType;

    public PrimitiveToWrapperConversion(Class<?> cls, Class<?> cls2) {
        this.sourceType = cls;
        this.targetType = NativeTypes.getPrimitiveType(cls2);
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getToExpression(ConversionContext conversionContext) {
        return this.sourceType == this.targetType ? "<SOURCE>" : "(" + this.targetType.getName() + ") <SOURCE>";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getFromExpression(ConversionContext conversionContext) {
        return "<SOURCE>." + this.sourceType.getName() + "Value()";
    }
}
