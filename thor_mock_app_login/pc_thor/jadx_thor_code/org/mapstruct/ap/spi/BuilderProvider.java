package org.mapstruct.ap.spi;

import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public interface BuilderProvider {
    BuilderInfo findBuilderInfo(TypeMirror typeMirror);

    default void init(MapStructProcessingEnvironment mapStructProcessingEnvironment) {
    }
}
