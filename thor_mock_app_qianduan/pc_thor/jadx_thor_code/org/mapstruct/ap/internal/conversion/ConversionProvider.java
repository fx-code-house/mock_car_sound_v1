package org.mapstruct.ap.internal.conversion;

import java.util.List;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;

/* loaded from: classes3.dex */
public interface ConversionProvider {
    Assignment from(ConversionContext conversionContext);

    List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext);

    Assignment to(ConversionContext conversionContext);
}
