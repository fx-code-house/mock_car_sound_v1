package org.mapstruct.ap.spi;

import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public interface AstModifyingAnnotationProcessor {
    boolean isTypeComplete(TypeMirror typeMirror);
}
