package org.mapstruct.ap.spi;

import javax.lang.model.element.ExecutableElement;

/* loaded from: classes3.dex */
public interface AccessorNamingStrategy {
    @Deprecated
    String getCollectionGetterName(String str);

    String getElementName(ExecutableElement executableElement);

    MethodType getMethodType(ExecutableElement executableElement);

    String getPropertyName(ExecutableElement executableElement);

    default void init(MapStructProcessingEnvironment mapStructProcessingEnvironment) {
    }
}
