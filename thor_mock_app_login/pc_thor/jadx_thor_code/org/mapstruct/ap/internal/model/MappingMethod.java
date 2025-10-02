package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public abstract class MappingMethod extends ModelElement {
    private final Accessibility accessibility;
    private final List<LifecycleCallbackMethodReference> afterMappingReferences;
    private final List<LifecycleCallbackMethodReference> beforeMappingReferencesWithMappingTarget;
    private final List<LifecycleCallbackMethodReference> beforeMappingReferencesWithoutMappingTarget;
    private final boolean isStatic;
    private final String name;
    private final List<Parameter> parameters;
    private final String resultName;
    private final Type returnType;
    private final List<Parameter> sourceParameters;
    private final Parameter targetParameter;
    private final List<Type> thrownTypes;

    protected MappingMethod(Method method, Collection<String> collection, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2) {
        this(method, method.getParameters(), collection, list, list2);
    }

    protected MappingMethod(Method method, List<Parameter> list, Collection<String> collection, List<LifecycleCallbackMethodReference> list2, List<LifecycleCallbackMethodReference> list3) {
        this.name = method.getName();
        this.parameters = list;
        this.sourceParameters = Parameter.getSourceParameters(list);
        this.returnType = method.getReturnType();
        this.targetParameter = method.getMappingTargetParameter();
        this.accessibility = method.getAccessibility();
        this.thrownTypes = method.getThrownTypes();
        this.isStatic = method.isStatic();
        this.resultName = initResultName(collection);
        this.beforeMappingReferencesWithMappingTarget = filterMappingTarget(list2, true);
        this.beforeMappingReferencesWithoutMappingTarget = filterMappingTarget(list2, false);
        this.afterMappingReferences = list3;
    }

    protected MappingMethod(Method method, List<Parameter> list) {
        this(method, list, new ArrayList(method.getParameterNames()), null, null);
    }

    protected MappingMethod(Method method) {
        this(method, new ArrayList(method.getParameterNames()), null, null);
    }

    protected MappingMethod(Method method, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2) {
        this(method, new ArrayList(method.getParameterNames()), list, list2);
    }

    private String initResultName(Collection<String> collection) {
        Parameter parameter = this.targetParameter;
        if (parameter != null) {
            return parameter.getName();
        }
        if (getResultType().isArrayType()) {
            String safeVariableName = Strings.getSafeVariableName(getResultType().getComponentType().getName() + "Tmp", collection);
            collection.add(safeVariableName);
            return safeVariableName;
        }
        String safeVariableName2 = Strings.getSafeVariableName(getResultType().getName(), collection);
        collection.add(safeVariableName2);
        return safeVariableName2;
    }

    public String getName() {
        return this.name;
    }

    public List<Parameter> getParameters() {
        return this.parameters;
    }

    public List<Parameter> getSourceParameters() {
        return this.sourceParameters;
    }

    public Type getResultType() {
        Parameter parameter = this.targetParameter;
        return parameter != null ? parameter.getType() : this.returnType;
    }

    public String getResultName() {
        return this.resultName;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    public Accessibility getAccessibility() {
        return this.accessibility;
    }

    public boolean isExistingInstanceMapping() {
        return this.targetParameter != null;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet();
        Iterator<Parameter> it = this.parameters.iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getType().getImportTypes());
        }
        hashSet.addAll(getReturnType().getImportTypes());
        Iterator<Type> it2 = this.thrownTypes.iterator();
        while (it2.hasNext()) {
            hashSet.addAll(it2.next().getImportTypes());
        }
        return hashSet;
    }

    protected List<String> getParameterNames() {
        ArrayList arrayList = new ArrayList(this.parameters.size());
        Iterator<Parameter> it = this.parameters.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }

    public List<Type> getThrownTypes() {
        return this.thrownTypes;
    }

    public String toString() {
        return this.returnType + StringUtils.SPACE + getName() + "(" + Strings.join(this.parameters, ", ") + ")";
    }

    private List<LifecycleCallbackMethodReference> filterMappingTarget(List<LifecycleCallbackMethodReference> list, boolean z) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (LifecycleCallbackMethodReference lifecycleCallbackMethodReference : list) {
            if (z == lifecycleCallbackMethodReference.hasMappingTargetParameter()) {
                arrayList.add(lifecycleCallbackMethodReference);
            }
        }
        return arrayList;
    }

    public List<LifecycleCallbackMethodReference> getAfterMappingReferences() {
        return this.afterMappingReferences;
    }

    public List<LifecycleCallbackMethodReference> getBeforeMappingReferencesWithMappingTarget() {
        return this.beforeMappingReferencesWithMappingTarget;
    }

    public List<LifecycleCallbackMethodReference> getBeforeMappingReferencesWithoutMappingTarget() {
        return this.beforeMappingReferencesWithoutMappingTarget;
    }

    public int hashCode() {
        List<Parameter> list = this.parameters;
        int iHashCode = (581 + (list != null ? list.hashCode() : 0)) * 83;
        Type type = this.returnType;
        return iHashCode + (type != null ? type.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MappingMethod mappingMethod = (MappingMethod) obj;
        return Objects.equals(this.parameters, mappingMethod.parameters) && Objects.equals(this.returnType, mappingMethod.returnType);
    }
}
