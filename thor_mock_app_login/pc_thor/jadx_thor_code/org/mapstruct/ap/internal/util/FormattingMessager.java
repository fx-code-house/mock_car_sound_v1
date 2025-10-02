package org.mapstruct.ap.internal.util;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/* loaded from: classes3.dex */
public interface FormattingMessager {
    boolean isErroneous();

    void note(int i, Message message, Object... objArr);

    void printMessage(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue, Message message, Object... objArr);

    void printMessage(Element element, AnnotationMirror annotationMirror, Message message, Object... objArr);

    void printMessage(Element element, Message message, Object... objArr);

    void printMessage(Message message, Object... objArr);
}
