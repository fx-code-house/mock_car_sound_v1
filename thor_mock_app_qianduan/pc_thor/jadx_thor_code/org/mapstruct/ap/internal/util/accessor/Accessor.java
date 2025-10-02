package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public interface Accessor {
    TypeMirror getAccessedType();

    AccessorType getAccessorType();

    Element getElement();

    Set<Modifier> getModifiers();

    String getSimpleName();
}
