package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/* loaded from: classes3.dex */
public abstract class NormalTypeMappingMethod extends MappingMethod {
    private final MethodReference factoryMethod;
    private final boolean mapNullToDefault;
    private final boolean overridden;

    NormalTypeMappingMethod(Method method, Collection<String> collection, MethodReference methodReference, boolean z, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2) {
        super(method, collection, list, list2);
        this.factoryMethod = methodReference;
        this.overridden = method.overridesMethod();
        this.mapNullToDefault = z;
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        if (this.factoryMethod == null && !isExistingInstanceMapping()) {
            if (getReturnType().getImplementationType() != null) {
                importTypes.addAll(getReturnType().getImplementationType().getImportTypes());
            }
        } else {
            MethodReference methodReference = this.factoryMethod;
            if (methodReference != null) {
                importTypes.addAll(methodReference.getImportTypes());
            }
        }
        return importTypes;
    }

    public boolean isMapNullToDefault() {
        return this.mapNullToDefault;
    }

    public boolean isOverridden() {
        return this.overridden;
    }

    public MethodReference getFactoryMethod() {
        return this.factoryMethod;
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod
    public int hashCode() {
        return (super.hashCode() * 31) + (getResultType() == null ? 0 : getResultType().hashCode());
    }

    @Override // org.mapstruct.ap.internal.model.MappingMethod
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        NormalTypeMappingMethod normalTypeMappingMethod = (NormalTypeMappingMethod) obj;
        if (!super.equals(obj) || !getResultType().equals(normalTypeMappingMethod.getResultType()) || getSourceParameters().size() != normalTypeMappingMethod.getSourceParameters().size()) {
            return false;
        }
        for (int i = 0; i < getSourceParameters().size(); i++) {
            if (!getSourceParameters().get(i).getType().equals(normalTypeMappingMethod.getSourceParameters().get(i).getType()) || !getSourceParameters().get(i).getType().getTypeParameters().equals(normalTypeMappingMethod.getSourceParameters().get(i).getType().getTypeParameters())) {
                return false;
            }
        }
        return isMapNullToDefault() == normalTypeMappingMethod.isMapNullToDefault();
    }
}
