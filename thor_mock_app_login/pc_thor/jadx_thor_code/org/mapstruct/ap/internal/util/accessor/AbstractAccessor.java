package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/* loaded from: classes3.dex */
abstract class AbstractAccessor<T extends Element> implements Accessor {
    protected final T element;

    AbstractAccessor(T t) {
        this.element = t;
    }

    @Override // org.mapstruct.ap.internal.util.accessor.Accessor
    public String getSimpleName() {
        return this.element.getSimpleName().toString();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.Accessor
    public Set<Modifier> getModifiers() {
        return this.element.getModifiers();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.Accessor
    public T getElement() {
        return this.element;
    }
}
