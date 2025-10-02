package org.mapstruct.ap.internal.util;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/* loaded from: classes3.dex */
public class AnnotationProcessingException extends RuntimeException {
    private final AnnotationMirror annotationMirror;
    private final AnnotationValue annotationValue;
    private final Element element;

    public AnnotationProcessingException(String str) {
        this(str, null, null, null);
    }

    public AnnotationProcessingException(String str, Element element) {
        this(str, element, null, null);
    }

    public AnnotationProcessingException(String str, Element element, AnnotationMirror annotationMirror) {
        this(str, element, annotationMirror, null);
    }

    public AnnotationProcessingException(String str, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        super(str);
        this.element = element;
        this.annotationMirror = annotationMirror;
        this.annotationValue = annotationValue;
    }

    public Element getElement() {
        return this.element;
    }

    public AnnotationMirror getAnnotationMirror() {
        return this.annotationMirror;
    }

    public AnnotationValue getAnnotationValue() {
        return this.annotationValue;
    }
}
