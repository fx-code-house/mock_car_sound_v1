package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javax.lang.model.element.ExecutableElement;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class ForgedMethod implements Method {
    private final Method basedOn;
    private final List<Parameter> contextParameters;
    private final boolean forgedNameBased;
    private final ForgedMethodHistory history;
    private final MappingReferences mappingReferences;
    private final Parameter mappingTargetParameter;
    private final String name;
    private final List<Parameter> parameters;
    private final Type returnType;
    private final List<Parameter> sourceParameters;
    private final List<Type> thrownTypes;

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getDeclaringMapper() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getDefiningType() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Parameter getTargetTypeParameter() {
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isDefault() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isLifecycleCallbackMethod() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isObjectFactory() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isStatic() {
        return false;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean overridesMethod() {
        return false;
    }

    public static ForgedMethod forParameterMapping(String str, Type type, Type type2, Method method) {
        return new ForgedMethod(str, type, type2, Collections.emptyList(), method, null, MappingReferences.empty(), false);
    }

    public static ForgedMethod forPropertyMapping(String str, Type type, Type type2, List<Parameter> list, Method method, ForgedMethodHistory forgedMethodHistory, MappingReferences mappingReferences, boolean z) {
        return new ForgedMethod(str, type, type2, list, method, forgedMethodHistory, mappingReferences == null ? MappingReferences.empty() : mappingReferences, z);
    }

    public static ForgedMethod forElementMapping(String str, Type type, Type type2, Method method, ForgedMethodHistory forgedMethodHistory, boolean z) {
        return new ForgedMethod(str, type, type2, method.getContextParameters(), method, forgedMethodHistory, MappingReferences.empty(), z);
    }

    private ForgedMethod(String str, Type type, Type type2, List<Parameter> list, Method method, ForgedMethodHistory forgedMethodHistory, MappingReferences mappingReferences, boolean z) {
        String safeVariableName = Strings.getSafeVariableName(type.getName(), new String[0]);
        ArrayList arrayList = new ArrayList(list.size() + 1);
        this.parameters = arrayList;
        arrayList.add(new Parameter(safeVariableName, type));
        arrayList.addAll(list);
        this.sourceParameters = Parameter.getSourceParameters(arrayList);
        this.contextParameters = Parameter.getContextParameters(arrayList);
        this.mappingTargetParameter = Parameter.getMappingTargetParameter(arrayList);
        this.returnType = type2;
        this.thrownTypes = new ArrayList();
        this.basedOn = method;
        this.name = Strings.sanitizeIdentifierName(str);
        this.history = forgedMethodHistory;
        this.mappingReferences = mappingReferences;
        this.forgedNameBased = z;
    }

    public ForgedMethod(String str, ForgedMethod forgedMethod) {
        List<Parameter> list = forgedMethod.parameters;
        this.parameters = list;
        this.returnType = forgedMethod.returnType;
        this.thrownTypes = new ArrayList();
        this.history = forgedMethod.history;
        this.sourceParameters = Parameter.getSourceParameters(list);
        this.contextParameters = Parameter.getContextParameters(list);
        this.mappingTargetParameter = Parameter.getMappingTargetParameter(list);
        this.mappingReferences = forgedMethod.mappingReferences;
        this.basedOn = forgedMethod.basedOn;
        this.name = str;
        this.forgedNameBased = forgedMethod.forgedNameBased;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean matches(List<Type> list, Type type) {
        if (!type.equals(this.returnType) || this.parameters.size() != list.size()) {
            return false;
        }
        Iterator<Type> it = list.iterator();
        Iterator<Parameter> it2 = this.parameters.iterator();
        while (it.hasNext() && it2.hasNext()) {
            if (!it.next().equals(it2.next().getType())) {
                return false;
            }
        }
        return true;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public String getName() {
        return this.name;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Parameter> getParameters() {
        return this.parameters;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Parameter> getSourceParameters() {
        return this.sourceParameters;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Parameter> getContextParameters() {
        return this.contextParameters;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public ParameterProvidedMethods getContextProvidedMethods() {
        return this.basedOn.getContextProvidedMethods();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Parameter getMappingTargetParameter() {
        return this.mappingTargetParameter;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Accessibility getAccessibility() {
        return Accessibility.PROTECTED;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getReturnType() {
        return this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Type> getThrownTypes() {
        return this.thrownTypes;
    }

    public ForgedMethodHistory getHistory() {
        return this.history;
    }

    public boolean isForgedNamedBased() {
        return this.forgedNameBased;
    }

    public void addThrownTypes(List<Type> list) {
        for (Type type : list) {
            if (!this.thrownTypes.contains(type)) {
                this.thrownTypes.add(type);
            }
        }
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getResultType() {
        Parameter parameter = this.mappingTargetParameter;
        return parameter != null ? parameter.getType() : this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<String> getParameterNames() {
        ArrayList arrayList = new ArrayList();
        Iterator<Parameter> it = getParameters().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getName());
        }
        return arrayList;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public ExecutableElement getExecutable() {
        return this.basedOn.getExecutable();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.returnType.toString());
        sb.append(StringUtils.SPACE);
        sb.append(getName()).append("(").append(Strings.join(this.parameters, ", ")).append(")");
        return sb.toString();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isUpdateMethod() {
        return getMappingTargetParameter() != null;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public MappingMethodOptions getOptions() {
        return this.basedOn.getOptions();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public String describe() {
        return getResultType().describe() + ":" + getName() + "(" + getMappingSourceType().describe() + ")";
    }

    public MappingReferences getMappingReferences() {
        return this.mappingReferences;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ForgedMethod forgedMethod = (ForgedMethod) obj;
        if (Objects.equals(this.parameters, forgedMethod.parameters)) {
            return Objects.equals(this.returnType, forgedMethod.returnType);
        }
        return false;
    }

    public int hashCode() {
        List<Parameter> list = this.parameters;
        int iHashCode = (list != null ? list.hashCode() : 0) * 31;
        Type type = this.returnType;
        return iHashCode + (type != null ? type.hashCode() : 0);
    }
}
