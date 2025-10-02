package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.List;
import org.mapstruct.ap.internal.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class IterableMappingMethod extends ContainerMappingMethod {

    public static class Builder extends ContainerMappingMethodBuilder<Builder, IterableMappingMethod> {
        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected /* bridge */ /* synthetic */ ContainerMappingMethod instantiateMappingMethod(Method method, Collection collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List list, List list2, SelectionParameters selectionParameters) {
            return instantiateMappingMethod(method, (Collection<String>) collection, assignment, methodReference, z, str, (List<LifecycleCallbackMethodReference>) list, (List<LifecycleCallbackMethodReference>) list2, selectionParameters);
        }

        public Builder() {
            super(Builder.class, "collection element");
        }

        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected Type getElementType(Type type) {
            return type.isArrayType() ? type.getComponentType() : ((Type) Collections.first(type.determineTypeArguments(Iterable.class))).getTypeBound();
        }

        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected Assignment getWrapper(Assignment assignment, Method method) {
            Type resultType = method.getResultType();
            if (resultType.isArrayType()) {
                return new LocalVarWrapper(assignment, method.getThrownTypes(), resultType, false);
            }
            return new SetterWrapper(assignment, method.getThrownTypes(), false);
        }

        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected IterableMappingMethod instantiateMappingMethod(Method method, Collection<String> collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2, SelectionParameters selectionParameters) {
            return new IterableMappingMethod(method, collection, assignment, methodReference, z, str, list, list2, selectionParameters);
        }
    }

    private IterableMappingMethod(Method method, Collection<String> collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2, SelectionParameters selectionParameters) {
        super(method, collection, assignment, methodReference, z, str, list, list2, selectionParameters);
    }

    public Type getSourceElementType() {
        Type type = getSourceParameter().getType();
        if (type.isArrayType()) {
            return type.getComponentType();
        }
        return ((Type) Collections.first(type.determineTypeArguments(Iterable.class))).getTypeBound();
    }

    @Override // org.mapstruct.ap.internal.model.ContainerMappingMethod
    public Type getResultElementType() {
        if (getResultType().isArrayType()) {
            return getResultType().getComponentType();
        }
        return (Type) Collections.first(getResultType().determineTypeArguments(Iterable.class));
    }
}
