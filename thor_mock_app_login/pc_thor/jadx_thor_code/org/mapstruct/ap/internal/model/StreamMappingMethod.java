package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.mapstruct.ap.internal.model.assignment.Java8FunctionWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Collections;

/* loaded from: classes3.dex */
public class StreamMappingMethod extends ContainerMappingMethod {
    private final Set<Type> helperImports;

    public static class Builder extends ContainerMappingMethodBuilder<Builder, StreamMappingMethod> {
        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected /* bridge */ /* synthetic */ ContainerMappingMethod instantiateMappingMethod(Method method, Collection collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List list, List list2, SelectionParameters selectionParameters) {
            return instantiateMappingMethod(method, (Collection<String>) collection, assignment, methodReference, z, str, (List<LifecycleCallbackMethodReference>) list, (List<LifecycleCallbackMethodReference>) list2, selectionParameters);
        }

        public Builder() {
            super(Builder.class, "stream element");
        }

        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected Type getElementType(Type type) {
            return StreamMappingMethod.getElementType(type);
        }

        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected Assignment getWrapper(Assignment assignment, Method method) {
            return new Java8FunctionWrapper(assignment);
        }

        @Override // org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder
        protected StreamMappingMethod instantiateMappingMethod(Method method, Collection<String> collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2, SelectionParameters selectionParameters) {
            HashSet hashSet = new HashSet();
            if (method.getResultType().isIterableType()) {
                hashSet.add(this.ctx.getTypeFactory().getType(Collectors.class));
            }
            Type type = ((Parameter) Collections.first(method.getSourceParameters())).getType();
            if (!type.isCollectionType() && !type.isArrayType() && type.isIterableType()) {
                hashSet.add(this.ctx.getTypeFactory().getType(StreamSupport.class));
            }
            return new StreamMappingMethod(method, collection, assignment, methodReference, z, str, list, list2, selectionParameters, hashSet);
        }
    }

    private StreamMappingMethod(Method method, Collection<String> collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2, SelectionParameters selectionParameters, Set<Type> set) {
        super(method, collection, assignment, methodReference, z, str, list, list2, selectionParameters);
        this.helperImports = set;
    }

    @Override // org.mapstruct.ap.internal.model.ContainerMappingMethod, org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        importTypes.addAll(this.helperImports);
        return importTypes;
    }

    public Type getSourceElementType() {
        return getElementType(getSourceParameter().getType());
    }

    @Override // org.mapstruct.ap.internal.model.ContainerMappingMethod
    public Type getResultElementType() {
        return getElementType(getResultType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Type getElementType(Type type) {
        if (type.isArrayType()) {
            return type.getComponentType();
        }
        if (type.isIterableType()) {
            return ((Type) Collections.first(type.determineTypeArguments(Iterable.class))).getTypeBound();
        }
        if (type.isStreamType()) {
            return ((Type) Collections.first(type.determineTypeArguments(Stream.class))).getTypeBound();
        }
        throw new IllegalArgumentException("Could not get the element type");
    }
}
