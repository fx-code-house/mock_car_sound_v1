package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

/* loaded from: classes3.dex */
public class FormattingParameters {
    public static final FormattingParameters EMPTY = new FormattingParameters(null, null, null, null, null);
    private final String date;
    private final AnnotationValue dateAnnotationValue;
    private final Element element;
    private final AnnotationMirror mirror;
    private final String number;

    public FormattingParameters(String str, String str2, AnnotationMirror annotationMirror, AnnotationValue annotationValue, Element element) {
        this.date = str;
        this.number = str2;
        this.mirror = annotationMirror;
        this.dateAnnotationValue = annotationValue;
        this.element = element;
    }

    public String getDate() {
        return this.date;
    }

    public String getNumber() {
        return this.number;
    }

    public AnnotationMirror getMirror() {
        return this.mirror;
    }

    public AnnotationValue getDateAnnotationValue() {
        return this.dateAnnotationValue;
    }

    public Element getElement() {
        return this.element;
    }
}
