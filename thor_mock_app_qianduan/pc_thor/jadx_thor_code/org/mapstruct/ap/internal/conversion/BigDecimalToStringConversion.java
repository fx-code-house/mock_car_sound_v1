package org.mapstruct.ap.internal.conversion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class BigDecimalToStringConversion extends AbstractNumberToStringConversion {
    public BigDecimalToStringConversion() {
        super(true);
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getToExpression(ConversionContext conversionContext) {
        if (!requiresDecimalFormat(conversionContext)) {
            return "<SOURCE>.toString()";
        }
        StringBuilder sb = new StringBuilder();
        appendDecimalFormatter(sb, conversionContext);
        sb.append(".format( <SOURCE> )");
        return sb.toString();
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getFromExpression(ConversionContext conversionContext) {
        if (requiresDecimalFormat(conversionContext)) {
            StringBuilder sb = new StringBuilder();
            sb.append("(" + ConversionUtils.bigDecimal(conversionContext) + ") ");
            appendDecimalFormatter(sb, conversionContext);
            sb.append(".parse( <SOURCE> )");
            return sb.toString();
        }
        return "new " + ConversionUtils.bigDecimal(conversionContext) + "( <SOURCE> )";
    }

    @Override // org.mapstruct.ap.internal.conversion.AbstractNumberToStringConversion, org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        return Collections.asSet(conversionContext.getTypeFactory().getType(BigDecimal.class));
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion, org.mapstruct.ap.internal.conversion.ConversionProvider
    public List<HelperMethod> getRequiredHelperMethods(ConversionContext conversionContext) {
        ArrayList arrayList = new ArrayList();
        if (conversionContext.getNumberFormat() != null) {
            arrayList.add(new CreateDecimalFormat(conversionContext.getTypeFactory()));
        }
        return arrayList;
    }

    private void appendDecimalFormatter(StringBuilder sb, ConversionContext conversionContext) {
        sb.append("createDecimalFormat( ");
        if (conversionContext.getNumberFormat() != null) {
            sb.append("\"");
            sb.append(conversionContext.getNumberFormat());
            sb.append("\"");
        }
        sb.append(" )");
    }
}
