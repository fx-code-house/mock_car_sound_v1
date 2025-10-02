package org.mapstruct.ap.internal.model;

import java.util.Set;
import java.util.function.Predicate;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class LifecycleCallbackMethodReference extends MethodReference {
    private final Type declaringType;
    private final Type methodResultType;
    private final Type methodReturnType;
    private final String targetVariableName;

    private LifecycleCallbackMethodReference(SelectedMethod<SourceMethod> selectedMethod, MapperReference mapperReference, Parameter parameter, Method method, Set<String> set) {
        super(selectedMethod.getMethod(), mapperReference, parameter, selectedMethod.getParameterBindings());
        this.declaringType = ((SourceMethod) selectedMethod.getMethod()).getDeclaringMapper();
        this.methodReturnType = method.getReturnType();
        this.methodResultType = method.getResultType();
        if (hasReturnType()) {
            String safeVariableName = Strings.getSafeVariableName("target", set);
            this.targetVariableName = safeVariableName;
            set.add(safeVariableName);
            return;
        }
        this.targetVariableName = null;
    }

    public Type getDeclaringType() {
        return this.declaringType;
    }

    public Type getMethodReturnType() {
        return this.methodReturnType;
    }

    public Type getMethodResultType() {
        return this.methodResultType;
    }

    public String getTargetVariableName() {
        return this.targetVariableName;
    }

    @Override // org.mapstruct.ap.internal.model.MethodReference, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Type type = this.declaringType;
        return type != null ? Collections.asSet(type) : java.util.Collections.emptySet();
    }

    public boolean hasMappingTargetParameter() {
        return getParameterBindings().stream().anyMatch(new Predicate() { // from class: org.mapstruct.ap.internal.model.LifecycleCallbackMethodReference$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ParameterBinding) obj).isMappingTarget();
            }
        });
    }

    public boolean hasReturnType() {
        return !getReturnType().isVoid();
    }

    public static LifecycleCallbackMethodReference forParameterProvidedMethod(SelectedMethod<SourceMethod> selectedMethod, Parameter parameter, Method method, Set<String> set) {
        return new LifecycleCallbackMethodReference(selectedMethod, null, parameter, method, set);
    }

    public static LifecycleCallbackMethodReference forMethodReference(SelectedMethod<SourceMethod> selectedMethod, MapperReference mapperReference, Method method, Set<String> set) {
        return new LifecycleCallbackMethodReference(selectedMethod, mapperReference, null, method, set);
    }
}
