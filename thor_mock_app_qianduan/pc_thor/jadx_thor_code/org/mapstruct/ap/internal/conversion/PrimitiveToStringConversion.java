package org.mapstruct.ap.internal.conversion;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class PrimitiveToStringConversion extends AbstractNumberToStringConversion {
    private final Class<?> sourceType;
    private final Class<?> wrapperType;

    public PrimitiveToStringConversion(Class<?> cls) {
        super(NativeTypes.isNumber(cls));
        if (!cls.isPrimitive()) {
            throw new IllegalArgumentException(cls + " is no primitive type.");
        }
        this.sourceType = cls;
        this.wrapperType = NativeTypes.getWrapperType(cls);
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getToExpression(ConversionContext conversionContext) {
        if (!requiresDecimalFormat(conversionContext)) {
            return "String.valueOf( <SOURCE> )";
        }
        StringBuilder sb = new StringBuilder();
        appendDecimalFormatter(sb, conversionContext);
        sb.append(".format( <SOURCE> )");
        return sb.toString();
    }

    @Override // org.mapstruct.ap.internal.conversion.AbstractNumberToStringConversion, org.mapstruct.ap.internal.conversion.SimpleConversion
    public Set<Type> getToConversionImportTypes(ConversionContext conversionContext) {
        if (requiresDecimalFormat(conversionContext)) {
            return Collections.singleton(conversionContext.getTypeFactory().getType(DecimalFormat.class));
        }
        return Collections.emptySet();
    }

    @Override // org.mapstruct.ap.internal.conversion.SimpleConversion
    public String getFromExpression(ConversionContext conversionContext) {
        if (requiresDecimalFormat(conversionContext)) {
            StringBuilder sb = new StringBuilder();
            appendDecimalFormatter(sb, conversionContext);
            sb.append(".parse( <SOURCE> ).");
            sb.append(this.sourceType.getSimpleName());
            sb.append("Value()");
            return sb.toString();
        }
        return this.wrapperType.getSimpleName() + ".parse" + Strings.capitalize(this.sourceType.getSimpleName()) + "( <SOURCE> )";
    }

    @Override // org.mapstruct.ap.internal.conversion.AbstractNumberToStringConversion, org.mapstruct.ap.internal.conversion.SimpleConversion
    protected Set<Type> getFromConversionImportTypes(ConversionContext conversionContext) {
        if (requiresDecimalFormat(conversionContext)) {
            return Collections.singleton(conversionContext.getTypeFactory().getType(DecimalFormat.class));
        }
        return Collections.emptySet();
    }

    private void appendDecimalFormatter(StringBuilder sb, ConversionContext conversionContext) {
        sb.append("new ");
        sb.append(ConversionUtils.decimalFormat(conversionContext));
        sb.append("( ");
        if (conversionContext.getNumberFormat() != null) {
            sb.append("\"");
            sb.append(conversionContext.getNumberFormat());
            sb.append("\"");
        }
        sb.append(" )");
    }
}
