package org.mapstruct.ap.internal.conversion;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.TypeConversion;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public abstract class SimpleConversion implements ConversionProvider {
    protected abstract String getFromExpression(ConversionContext conversionContext);

    protected abstract String getToExpression(ConversionContext conversionContext);

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public Assignment to(ConversionContext conversionContext) {
        return new TypeConversion(getToConversionImportTypes(conversionContext), getToConversionExceptionTypes(conversionContext), getToExpression(conversionContext));
    }

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public Assignment from(ConversionContext conversionContext) {
        return new TypeConversion(getFromConversionImportTypes(conversionContext), getFromConversionExceptionTypes(conversionContext), getFromExpression(conversionContext));
    }

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        return Collections.emptyList();
    }

    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.emptySet();
    }

    protected Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        return Collections.emptySet();
    }

    protected List<Type> getToConversionExceptionTypes(ConversionContext conversionContext) {
        return Collections.emptyList();
    }

    protected List<Type> getFromConversionExceptionTypes(ConversionContext conversionContext) {
        return Collections.emptyList();
    }
}
