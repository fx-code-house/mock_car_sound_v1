package org.mapstruct.ap.internal.util;

import java.util.HashSet;
import java.util.Set;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public class RoundContext {
    private final AnnotationProcessorContext annotationProcessorContext;
    private final Set<TypeMirror> clearedTypes = new HashSet();

    public RoundContext(AnnotationProcessorContext annotationProcessorContext) {
        this.annotationProcessorContext = annotationProcessorContext;
    }

    public AnnotationProcessorContext getAnnotationProcessorContext() {
        return this.annotationProcessorContext;
    }

    public void addTypeReadyForProcessing(TypeMirror typeMirror) {
        this.clearedTypes.add(typeMirror);
    }

    public boolean isReadyForProcessing(TypeMirror typeMirror) {
        return this.clearedTypes.contains(typeMirror);
    }
}
