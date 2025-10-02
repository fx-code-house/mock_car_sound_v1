package org.mapstruct.ap.internal.conversion;

import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class StaticParseToStringConversion extends SimpleConversion {
    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toString()";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return conversionContext.getTargetType().createReferenceName() + ".parse( <SOURCE> )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTargetType());
    }
}
