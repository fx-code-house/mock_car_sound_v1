package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public class TypeHierarchyErroneousException extends RuntimeException {
    private static final long serialVersionUID = 1;
    private final TypeMirror type;

    public TypeHierarchyErroneousException(TypeElement typeElement) {
        this(typeElement.asType());
    }

    public TypeHierarchyErroneousException(TypeMirror typeMirror) {
        this.type = typeMirror;
    }

    public TypeMirror getType() {
        return this.type;
    }
}
