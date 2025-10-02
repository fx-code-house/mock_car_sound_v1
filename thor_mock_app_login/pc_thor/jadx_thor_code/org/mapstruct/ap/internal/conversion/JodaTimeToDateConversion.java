package org.mapstruct.ap.internal.conversion;

import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class JodaTimeToDateConversion extends SimpleConversion {
    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getToExpression(ConversionContext conversionContext) {
        return "<SOURCE>.toDate()";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected String getFromExpression(ConversionContext conversionContext) {
        return "new " + conversionContext.getTargetType().createReferenceName() + "( <SOURCE> )";
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTargetType());
    }
}
