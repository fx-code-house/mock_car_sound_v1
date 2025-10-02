package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;

/* loaded from: classes3.dex */
public interface EnumMappingStrategy {
    String getDefaultNullEnumConstant(TypeElement typeElement);

    String getEnumConstant(TypeElement typeElement, String str);

    TypeElement getUnexpectedValueMappingExceptionType();

    default void init(MapStructProcessingEnvironment mapStructProcessingEnvironment) {
    }
}
