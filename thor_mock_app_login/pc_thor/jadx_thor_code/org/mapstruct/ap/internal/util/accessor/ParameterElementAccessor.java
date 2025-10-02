package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public class ParameterElementAccessor extends AbstractAccessor<Element> {
    protected final TypeMirror accessedType;
    protected final String name;

    @Override // org.mapstruct.ap.internal.util.accessor.AbstractAccessor, org.mapstruct.ap.internal.util.accessor.Accessor
    public /* bridge */ /* synthetic */ Element getElement() {
        return super.getElement();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.AbstractAccessor, org.mapstruct.ap.internal.util.accessor.Accessor
    public /* bridge */ /* synthetic */ Set getModifiers() {
        return super.getModifiers();
    }

    public ParameterElementAccessor(Element element, TypeMirror typeMirror, String str) {
        super(element);
        this.name = str;
        this.accessedType = typeMirror;
    }

    @Override // org.mapstruct.ap.internal.util.accessor.AbstractAccessor, org.mapstruct.ap.internal.util.accessor.Accessor
    public String getSimpleName() {
        String str = this.name;
        return str != null ? str : super.getSimpleName();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.Accessor
    public TypeMirror getAccessedType() {
        return this.accessedType;
    }

    public String toString() {
        return this.element.toString();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.Accessor
    public AccessorType getAccessorType() {
        return AccessorType.PARAMETER;
    }
}
