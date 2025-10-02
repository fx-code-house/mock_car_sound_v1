package org.mapstruct.ap.internal.model.common;

import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class DefaultConversionContext implements ConversionContext {
    private final String dateFormat;
    private final FormattingParameters formattingParameters;
    private final FormattingMessager messager;
    private final String numberFormat;
    private final Type sourceType;
    private final Type targetType;
    private final TypeFactory typeFactory;

    public DefaultConversionContext(TypeFactory typeFactory, FormattingMessager formattingMessager, Type type, Type type2, FormattingParameters formattingParameters) {
        this.typeFactory = typeFactory;
        this.messager = formattingMessager;
        this.sourceType = type;
        this.targetType = type2;
        this.formattingParameters = formattingParameters;
        this.dateFormat = formattingParameters.getDate();
        this.numberFormat = formattingParameters.getNumber();
        validateDateFormat();
    }

    private void validateDateFormat() {
        if (Strings.isEmpty(this.dateFormat)) {
            return;
        }
        DateFormatValidationResult dateFormatValidationResultValidate = DateFormatValidatorFactory.forTypes(this.sourceType, this.targetType).validate(this.dateFormat);
        if (dateFormatValidationResultValidate.isValid()) {
            return;
        }
        dateFormatValidationResultValidate.printErrorMessage(this.messager, this.formattingParameters.getElement(), this.formattingParameters.getMirror(), this.formattingParameters.getDateAnnotationValue());
    }

    @Override // org.mapstruct.ap.internal.model.common.ConversionContext
    public Type getTargetType() {
        return this.targetType;
    }

    @Override // org.mapstruct.ap.internal.model.common.ConversionContext
    public String getNumberFormat() {
        return this.numberFormat;
    }

    @Override // org.mapstruct.ap.internal.model.common.ConversionContext
    public String getDateFormat() {
        return this.dateFormat;
    }

    @Override // org.mapstruct.ap.internal.model.common.ConversionContext
    public TypeFactory getTypeFactory() {
        return this.typeFactory;
    }

    protected FormattingMessager getMessager() {
        return this.messager;
    }
}
