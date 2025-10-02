package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/* loaded from: classes3.dex */
final class DateFormatValidationResult {
    private final boolean isValid;
    private final Message validationInfo;
    private final Object[] validationInfoArgs;

    DateFormatValidationResult(boolean z, Message message, Object... objArr) {
        this.isValid = z;
        this.validationInfo = message;
        this.validationInfoArgs = objArr;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void printErrorMessage(FormattingMessager formattingMessager, Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        formattingMessager.printMessage(element, annotationMirror, annotationValue, this.validationInfo, this.validationInfoArgs);
    }
}
