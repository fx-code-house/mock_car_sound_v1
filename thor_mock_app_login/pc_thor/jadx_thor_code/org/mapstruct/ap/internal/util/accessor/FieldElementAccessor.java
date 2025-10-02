package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public class FieldElementAccessor extends AbstractAccessor<VariableElement> {
    @Override // org.mapstruct.ap.internal.util.accessor.AbstractAccessor, org.mapstruct.ap.internal.util.accessor.Accessor
    public /* bridge */ /* synthetic */ Set getModifiers() {
        return super.getModifiers();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.AbstractAccessor, org.mapstruct.ap.internal.util.accessor.Accessor
    public /* bridge */ /* synthetic */ String getSimpleName() {
        return super.getSimpleName();
    }

    public FieldElementAccessor(VariableElement variableElement) {
        super(variableElement);
    }

    @Override // org.mapstruct.ap.internal.util.accessor.Accessor
    public TypeMirror getAccessedType() {
        return this.element.asType();
    }

    public String toString() {
        return this.element.toString();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.Accessor
    public AccessorType getAccessorType() {
        return AccessorType.FIELD;
    }
}
