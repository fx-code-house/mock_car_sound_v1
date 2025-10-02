package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/* loaded from: classes3.dex */
public class DefaultEnumMappingStrategy implements EnumMappingStrategy {
    protected Elements elementUtils;
    protected Types typeUtils;

    @Override // org.mapstruct.ap.spi.EnumMappingStrategy
    public String getDefaultNullEnumConstant(TypeElement typeElement) {
        return null;
    }

    @Override // org.mapstruct.ap.spi.EnumMappingStrategy
    public String getEnumConstant(TypeElement typeElement, String str) {
        return str;
    }

    @Override // org.mapstruct.ap.spi.EnumMappingStrategy
    public void init(MapStructProcessingEnvironment mapStructProcessingEnvironment) {
        this.elementUtils = mapStructProcessingEnvironment.getElementUtils();
        this.typeUtils = mapStructProcessingEnvironment.getTypeUtils();
    }

    @Override // org.mapstruct.ap.spi.EnumMappingStrategy
    public TypeElement getUnexpectedValueMappingExceptionType() {
        return this.elementUtils.getTypeElement(getUnexpectedValueMappingExceptionClass().getCanonicalName());
    }

    protected Class<? extends Exception> getUnexpectedValueMappingExceptionClass() {
        return IllegalArgumentException.class;
    }
}
