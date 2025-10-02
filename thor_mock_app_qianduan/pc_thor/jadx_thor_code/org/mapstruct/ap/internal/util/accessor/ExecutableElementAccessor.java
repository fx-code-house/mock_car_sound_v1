package org.mapstruct.ap.internal.util.accessor;

import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

/* loaded from: classes3.dex */
public class ExecutableElementAccessor extends AbstractAccessor<ExecutableElement> {
    private final TypeMirror accessedType;
    private final AccessorType accessorType;

    @Override // org.mapstruct.ap.internal.util.accessor.AbstractAccessor, org.mapstruct.ap.internal.util.accessor.Accessor
    public /* bridge */ /* synthetic */ Set getModifiers() {
        return super.getModifiers();
    }

    @Override // org.mapstruct.ap.internal.util.accessor.AbstractAccessor, org.mapstruct.ap.internal.util.accessor.Accessor
    public /* bridge */ /* synthetic */ String getSimpleName() {
        return super.getSimpleName();
    }

    public ExecutableElementAccessor(ExecutableElement executableElement, TypeMirror typeMirror, AccessorType accessorType) {
        super(executableElement);
        this.accessedType = typeMirror;
        this.accessorType = accessorType;
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
        return this.accessorType;
    }
}
