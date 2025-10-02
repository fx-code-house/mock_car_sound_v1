package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class MethodReference extends ModelElement implements Assignment {
    private Assignment assignment;
    private final String contextParam;
    private final MapperReference declaringMapper;
    private final Type definingType;
    private final Set<Type> importTypes;
    private final boolean isConstructor;
    private final boolean isStatic;
    private final boolean isUpdateMethod;
    private final String name;
    private final List<ParameterBinding> parameterBindings;
    private final Parameter providingParameter;
    private final Type returnType;
    private final List<Parameter> sourceParameters;
    private final List<Type> thrownTypes;

    protected MethodReference(Method method, MapperReference mapperReference, Parameter parameter, List<ParameterBinding> list) {
        this.declaringMapper = mapperReference;
        this.sourceParameters = Parameter.getSourceParameters(method.getParameters());
        this.returnType = method.getReturnType();
        this.providingParameter = parameter;
        this.parameterBindings = list;
        this.contextParam = null;
        HashSet hashSet = new HashSet();
        Iterator<Type> it = method.getThrownTypes().iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getImportTypes());
        }
        Iterator<ParameterBinding> it2 = list.iterator();
        while (it2.hasNext()) {
            hashSet.addAll(it2.next().getImportTypes());
        }
        this.importTypes = Collections.unmodifiableSet(hashSet);
        this.thrownTypes = method.getThrownTypes();
        this.isUpdateMethod = method.getMappingTargetParameter() != null;
        this.definingType = method.getDefiningType();
        this.isStatic = method.isStatic();
        this.name = method.getName();
        this.isConstructor = false;
    }

    private MethodReference(BuiltInMethod builtInMethod, ConversionContext conversionContext) {
        this.sourceParameters = Parameter.getSourceParameters(builtInMethod.getParameters());
        this.returnType = builtInMethod.getReturnType();
        this.declaringMapper = null;
        this.providingParameter = null;
        this.contextParam = builtInMethod.getContextParameter(conversionContext);
        this.importTypes = Collections.emptySet();
        this.thrownTypes = Collections.emptyList();
        this.definingType = null;
        this.isUpdateMethod = builtInMethod.getMappingTargetParameter() != null;
        this.parameterBindings = ParameterBinding.fromParameters(builtInMethod.getParameters());
        this.isStatic = builtInMethod.isStatic();
        this.name = builtInMethod.getName();
        this.isConstructor = false;
    }

    private MethodReference(String str, Type type, boolean z) {
        this.name = str;
        this.definingType = type;
        this.sourceParameters = Collections.emptyList();
        this.returnType = null;
        this.declaringMapper = null;
        this.importTypes = Collections.emptySet();
        this.thrownTypes = Collections.emptyList();
        this.isUpdateMethod = false;
        this.contextParam = null;
        this.parameterBindings = Collections.emptyList();
        this.providingParameter = null;
        this.isStatic = z;
        this.isConstructor = false;
    }

    private MethodReference(Type type, List<ParameterBinding> list) {
        this.name = null;
        this.definingType = type;
        this.sourceParameters = Collections.emptyList();
        this.returnType = null;
        this.declaringMapper = null;
        this.thrownTypes = Collections.emptyList();
        this.isUpdateMethod = false;
        this.contextParam = null;
        this.parameterBindings = list;
        this.providingParameter = null;
        this.isStatic = false;
        this.isConstructor = true;
        if (list.isEmpty()) {
            this.importTypes = Collections.emptySet();
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<ParameterBinding> it = list.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(it.next().getType());
        }
        linkedHashSet.add(type);
        this.importTypes = Collections.unmodifiableSet(linkedHashSet);
    }

    public MapperReference getDeclaringMapper() {
        return this.declaringMapper;
    }

    public Parameter getProvidingParameter() {
        return this.providingParameter;
    }

    public String getMapperVariableName() {
        return this.declaringMapper.getVariableName();
    }

    public String getContextParam() {
        return this.contextParam;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    public String getName() {
        return this.name;
    }

    public List<Parameter> getSourceParameters() {
        return this.sourceParameters;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceReference() {
        Assignment assignment = this.assignment;
        if (assignment != null) {
            return assignment.getSourceReference();
        }
        return null;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourcePresenceCheckerReference() {
        return this.assignment.getSourcePresenceCheckerReference();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Type getSourceType() {
        return this.assignment.getSourceType();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String createUniqueVarName(String str) {
        return this.assignment.createUniqueVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLocalVarName() {
        return this.assignment.getSourceLocalVarName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLocalVarName(String str) {
        this.assignment.setSourceLocalVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceLoopVarName() {
        return this.assignment.getSourceLoopVarName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public void setSourceLoopVarName(String str) {
        this.assignment.setSourceLoopVarName(str);
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public String getSourceParameterName() {
        return this.assignment.getSourceParameterName();
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isSourceReferenceParameter() {
        return this.assignment.isSourceReferenceParameter();
    }

    public Type getSingleSourceParameterType() {
        for (Parameter parameter : getSourceParameters()) {
            if (!parameter.isTargetType()) {
                return parameter.getType();
            }
        }
        return null;
    }

    public Type getDefiningType() {
        return this.definingType;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(this.importTypes);
        Assignment assignment = this.assignment;
        if (assignment != null) {
            hashSet.addAll(assignment.getImportTypes());
        }
        if (isStatic()) {
            hashSet.add(this.definingType);
        }
        return hashSet;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public List<Type> getThrownTypes() {
        ArrayList arrayList = new ArrayList(this.thrownTypes);
        Assignment assignment = this.assignment;
        if (assignment != null) {
            arrayList.addAll(assignment.getThrownTypes());
        }
        return arrayList;
    }

    /* renamed from: org.mapstruct.ap.internal.model.MethodReference$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType;

        static {
            int[] iArr = new int[Assignment.AssignmentType.values().length];
            $SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType = iArr;
            try {
                iArr[Assignment.AssignmentType.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType[Assignment.AssignmentType.MAPPED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType[Assignment.AssignmentType.TYPE_CONVERTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public Assignment.AssignmentType getType() {
        int i = AnonymousClass1.$SwitchMap$org$mapstruct$ap$internal$model$common$Assignment$AssignmentType[this.assignment.getType().ordinal()];
        if (i == 1) {
            return Assignment.AssignmentType.MAPPED;
        }
        if (i == 2) {
            return Assignment.AssignmentType.MAPPED_TWICE;
        }
        if (i != 3) {
            return null;
        }
        return Assignment.AssignmentType.TYPE_CONVERTED_MAPPED;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.common.Assignment
    public boolean isCallingUpdateMethod() {
        return this.isUpdateMethod;
    }

    public boolean isStatic() {
        return this.isStatic;
    }

    public boolean isConstructor() {
        return this.isConstructor;
    }

    public List<ParameterBinding> getParameterBindings() {
        return this.parameterBindings;
    }

    public Type inferTypeWhenEnum(Type type) {
        return "java.lang.Enum".equals(type.getFullyQualifiedName()) ? type.getTypeParameters().get(0) : type;
    }

    public int hashCode() {
        int iHashCode = super.hashCode() * 31;
        MapperReference mapperReference = this.declaringMapper;
        int iHashCode2 = (iHashCode + (mapperReference == null ? 0 : mapperReference.hashCode())) * 31;
        Parameter parameter = this.providingParameter;
        return iHashCode2 + (parameter != null ? parameter.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        MethodReference methodReference = (MethodReference) obj;
        return Objects.equals(this.declaringMapper, methodReference.declaringMapper) && Objects.equals(this.providingParameter, methodReference.providingParameter);
    }

    public static MethodReference forBuiltInMethod(BuiltInMethod builtInMethod, ConversionContext conversionContext) {
        return new MethodReference(builtInMethod, conversionContext);
    }

    public static MethodReference forForgedMethod(Method method, List<ParameterBinding> list) {
        return new MethodReference(method, null, null, list);
    }

    public static MethodReference forParameterProvidedMethod(Method method, Parameter parameter, List<ParameterBinding> list) {
        return new MethodReference(method, null, parameter, list);
    }

    public static MethodReference forMapperReference(Method method, MapperReference mapperReference, List<ParameterBinding> list) {
        return new MethodReference(method, mapperReference, null, list);
    }

    public static MethodReference forStaticBuilder(String str, Type type) {
        return new MethodReference(str, type, true);
    }

    public static MethodReference forMethodCall(String str) {
        return new MethodReference(str, null, false);
    }

    public static MethodReference forConstructorInvocation(Type type, List<ParameterBinding> list) {
        return new MethodReference(type, list);
    }

    public String toString() {
        final String sourceReference;
        MapperReference mapperReference = this.declaringMapper;
        String name = mapperReference != null ? mapperReference.getType().getName() : "";
        if (getAssignment() != null) {
            sourceReference = getAssignment().toString();
        } else {
            sourceReference = getSourceReference() != null ? getSourceReference() : "";
        }
        Type type = this.returnType;
        return (type != null ? type.toString() : "") + StringUtils.SPACE + name + "#" + this.name + "(" + Strings.join((List) this.sourceParameters.stream().map(new Function() { // from class: org.mapstruct.ap.internal.model.MethodReference$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return MethodReference.lambda$toString$0(sourceReference, (Parameter) obj);
            }
        }).collect(Collectors.toList()), ",") + ")";
    }

    static /* synthetic */ String lambda$toString$0(String str, Parameter parameter) {
        return (parameter.isMappingContext() || parameter.isMappingTarget() || parameter.isTargetType()) ? parameter.getName() : str;
    }
}
