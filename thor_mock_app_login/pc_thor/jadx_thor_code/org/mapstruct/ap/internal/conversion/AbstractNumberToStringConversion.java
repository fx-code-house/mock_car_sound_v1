package org.mapstruct.ap.internal.conversion;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public abstract class AbstractNumberToStringConversion extends SimpleConversion {
    private final boolean sourceTypeNumberSubclass;

    public AbstractNumberToStringConversion(boolean z) {
        this.sourceTypeNumberSubclass = z;
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        if (requiresDecimalFormat(conversionContext)) {
            return Collections.singleton(conversionContext.getTypeFactory().getType(DecimalFormat.class));
        }
        return super.getToConversionImportTypes(conversionContext);
    }

    protected boolean requiresDecimalFormat(ConversionContext conversionContext) {
        return this.sourceTypeNumberSubclass && conversionContext.getNumberFormat() != null;
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if (requiresDecimalFormat(conversionContext)) {
            return Collections.singleton(conversionContext.getTypeFactory().getType(DecimalFormat.class));
        }
        return super.getFromConversionImportTypes(conversionContext);
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    protected List<Type> getFromConversionExceptionTypes(ConversionContext conversionContext) {
        if (requiresDecimalFormat(conversionContext)) {
            return Collections.singletonList(conversionContext.getTypeFactory().getType(ParseException.class));
        }
        return super.getFromConversionExceptionTypes(conversionContext);
    }
}
