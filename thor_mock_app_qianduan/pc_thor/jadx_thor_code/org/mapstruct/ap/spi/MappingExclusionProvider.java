package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;

/* loaded from: classes3.dex */
public interface MappingExclusionProvider {
    boolean isExcluded(TypeElement typeElement);
}
