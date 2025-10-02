package org.mapstruct.ap.internal.conversion;

import java.util.Collections;
import java.util.List;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;

/* loaded from: classes3.dex */
public class ReverseConversion implements ConversionProvider {
    private ConversionProvider conversionProvider;

    public static ReverseConversion inverse(ConversionProvider conversionProvider) {
        return new ReverseConversion(conversionProvider);
    }

    private ReverseConversion(ConversionProvider conversionProvider) {
        this.conversionProvider = conversionProvider;
    }

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public Assignment to(ConversionContext conversionContext) {
        return this.conversionProvider.from(conversionContext);
    }

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public Assignment from(ConversionContext conversionContext) {
        return this.conversionProvider.to(conversionContext);
    }

    @Override // org.mapstruct.ap.internal.conversion.ConversionProvider
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        return Collections.emptyList();
    }
}
