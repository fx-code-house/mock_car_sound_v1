package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.internal.gem.ObjectFactoryGem;
import org.mapstruct.ap.internal.model.common.Accessibility;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Parameter$$ExternalSyntheticLambda3;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class SourceMethod implements Method {
    private final Accessibility accessibility;
    private List<SourceMethod> applicablePrototypeMethods;
    private List<SourceMethod> applicableReversePrototypeMethods;
    private final List<Parameter> contextParameters;
    private final ParameterProvidedMethods contextProvidedMethods;
    private final Type declaringMapper;
    private final List<Type> exceptionTypes;
    private final ExecutableElement executable;
    private final boolean hasObjectFactoryAnnotation;
    private Boolean isIterableMapping;
    private Boolean isMapMapping;
    private final boolean isObjectFactory;
    private Boolean isStreamMapping;
    private Boolean isValueMapping;
    private final Type mapperToImplement;
    private final MappingMethodOptions mappingMethodOptions;
    private final Parameter mappingTargetParameter;
    private List<String> parameterNames;
    private final List<Parameter> parameters;
    private final List<SourceMethod> prototypeMethods;
    private final Type returnType;
    private final List<Parameter> sourceParameters;
    private final Parameter targetTypeParameter;
    private final TypeFactory typeFactory;
    private final Types typeUtils;
    private final boolean verboseLogging;

    public static class Builder {
        private ParameterProvidedMethods contextProvidedMethods;
        private EnumMappingOptions enumMappingOptions;
        private List<Type> exceptionTypes;
        private ExecutableElement executable;
        private Set<MappingOptions> mappings;
        private List<Parameter> parameters;
        private Types typeUtils;
        private List<ValueMappingOptions> valueMappings;
        private boolean verboseLogging;
        private Type declaringMapper = null;
        private Type definingType = null;
        private Type returnType = null;
        private IterableMappingOptions iterableMapping = null;
        private MapMappingOptions mapMapping = null;
        private BeanMappingOptions beanMapping = null;
        private TypeFactory typeFactory = null;
        private MapperOptions mapper = null;
        private List<SourceMethod> prototypeMethods = Collections.emptyList();

        public Builder setDeclaringMapper(Type type) {
            this.declaringMapper = type;
            return this;
        }

        public Builder setExecutable(ExecutableElement executableElement) {
            this.executable = executableElement;
            return this;
        }

        public Builder setParameters(List<Parameter> list) {
            this.parameters = list;
            return this;
        }

        public Builder setReturnType(Type type) {
            this.returnType = type;
            return this;
        }

        public Builder setExceptionTypes(List<Type> list) {
            this.exceptionTypes = list;
            return this;
        }

        public Builder setMappingOptions(Set<MappingOptions> set) {
            this.mappings = set;
            return this;
        }

        public Builder setIterableMappingOptions(IterableMappingOptions iterableMappingOptions) {
            this.iterableMapping = iterableMappingOptions;
            return this;
        }

        public Builder setMapMappingOptions(MapMappingOptions mapMappingOptions) {
            this.mapMapping = mapMappingOptions;
            return this;
        }

        public Builder setBeanMappingOptions(BeanMappingOptions beanMappingOptions) {
            this.beanMapping = beanMappingOptions;
            return this;
        }

        public Builder setValueMappingOptionss(List<ValueMappingOptions> list) {
            this.valueMappings = list;
            return this;
        }

        public Builder setEnumMappingOptions(EnumMappingOptions enumMappingOptions) {
            this.enumMappingOptions = enumMappingOptions;
            return this;
        }

        public Builder setTypeUtils(Types types) {
            this.typeUtils = types;
            return this;
        }

        public Builder setTypeFactory(TypeFactory typeFactory) {
            this.typeFactory = typeFactory;
            return this;
        }

        public Builder setMapper(MapperOptions mapperOptions) {
            this.mapper = mapperOptions;
            return this;
        }

        public Builder setPrototypeMethods(List<SourceMethod> list) {
            this.prototypeMethods = list;
            return this;
        }

        public Builder setDefininingType(Type type) {
            this.definingType = type;
            return this;
        }

        public Builder setContextProvidedMethods(ParameterProvidedMethods parameterProvidedMethods) {
            this.contextProvidedMethods = parameterProvidedMethods;
            return this;
        }

        public Builder setVerboseLogging(boolean z) {
            this.verboseLogging = z;
            return this;
        }

        public SourceMethod build() {
            if (this.mappings == null) {
                this.mappings = Collections.emptySet();
            }
            return new SourceMethod(this, new MappingMethodOptions(this.mapper, this.mappings, this.iterableMapping, this.mapMapping, this.beanMapping, this.enumMappingOptions, this.valueMappings));
        }
    }

    private SourceMethod(Builder builder, MappingMethodOptions mappingMethodOptions) {
        this.declaringMapper = builder.declaringMapper;
        ExecutableElement executableElement = builder.executable;
        this.executable = executableElement;
        List<Parameter> list = builder.parameters;
        this.parameters = list;
        this.returnType = builder.returnType;
        this.exceptionTypes = builder.exceptionTypes;
        this.accessibility = Accessibility.fromModifiers(builder.executable.getModifiers());
        this.mappingMethodOptions = mappingMethodOptions;
        this.sourceParameters = Parameter.getSourceParameters(list);
        this.contextParameters = Parameter.getContextParameters(list);
        this.contextProvidedMethods = builder.contextProvidedMethods;
        this.mappingTargetParameter = Parameter.getMappingTargetParameter(list);
        this.targetTypeParameter = Parameter.getTargetTypeParameter(list);
        this.hasObjectFactoryAnnotation = ObjectFactoryGem.instanceOn((Element) executableElement) != null;
        this.isObjectFactory = determineIfIsObjectFactory();
        this.typeUtils = builder.typeUtils;
        this.typeFactory = builder.typeFactory;
        this.prototypeMethods = builder.prototypeMethods;
        this.mapperToImplement = builder.definingType;
        this.verboseLogging = builder.verboseLogging;
    }

    private boolean determineIfIsObjectFactory() {
        return !isLifecycleCallbackMethod() && !this.returnType.isVoid() && (getMappingTargetParameter() == null) && (this.hasObjectFactoryAnnotation || getSourceParameters().isEmpty());
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getDeclaringMapper() {
        return this.declaringMapper;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public ExecutableElement getExecutable() {
        return this.executable;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public String getName() {
        return this.executable.getSimpleName().toString();
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
        return this.contextProvidedMethods;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<String> getParameterNames() {
        if (this.parameterNames == null) {
            ArrayList arrayList = new ArrayList(this.parameters.size());
            Iterator<Parameter> it = this.parameters.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getName());
            }
            this.parameterNames = Collections.unmodifiableList(arrayList);
        }
        return this.parameterNames;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getResultType() {
        Parameter parameter = this.mappingTargetParameter;
        return parameter != null ? parameter.getType() : this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getReturnType() {
        return this.returnType;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Accessibility getAccessibility() {
        return this.accessibility;
    }

    public boolean inverses(SourceMethod sourceMethod) {
        return sourceMethod.getDeclaringMapper() == null && sourceMethod.isAbstract() && getSourceParameters().size() == 1 && sourceMethod.getSourceParameters().size() == 1 && getMappingSourceType().isAssignableTo(sourceMethod.getResultType()) && getResultType().isAssignableTo(((Parameter) org.mapstruct.ap.internal.util.Collections.first(sourceMethod.getSourceParameters())).getType());
    }

    public boolean canInheritFrom(SourceMethod sourceMethod) {
        return sourceMethod.getDeclaringMapper() == null && sourceMethod.isAbstract() && isMapMapping() == sourceMethod.isMapMapping() && isIterableMapping() == sourceMethod.isIterableMapping() && MappingMethodUtils.isEnumMapping(this) == MappingMethodUtils.isEnumMapping(sourceMethod) && getResultType().isAssignableTo(sourceMethod.getResultType()) && allParametersAreAssignable(getSourceParameters(), sourceMethod.getSourceParameters());
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Parameter getMappingTargetParameter() {
        return this.mappingTargetParameter;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isObjectFactory() {
        return this.isObjectFactory;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Parameter getTargetTypeParameter() {
        return this.targetTypeParameter;
    }

    public boolean isIterableMapping() {
        if (this.isIterableMapping == null) {
            this.isIterableMapping = Boolean.valueOf(getSourceParameters().size() == 1 && getMappingSourceType().isIterableType() && getResultType().isIterableType());
        }
        return this.isIterableMapping.booleanValue();
    }

    public boolean isStreamMapping() {
        if (this.isStreamMapping == null) {
            boolean z = true;
            if (getSourceParameters().size() != 1 || ((!getMappingSourceType().isIterableType() || !getResultType().isStreamType()) && ((!getMappingSourceType().isStreamType() || !getResultType().isIterableType()) && (!getMappingSourceType().isStreamType() || !getResultType().isStreamType())))) {
                z = false;
            }
            this.isStreamMapping = Boolean.valueOf(z);
        }
        return this.isStreamMapping.booleanValue();
    }

    public boolean isMapMapping() {
        if (this.isMapMapping == null) {
            this.isMapMapping = Boolean.valueOf(getSourceParameters().size() == 1 && getMappingSourceType().isMapType() && getResultType().isMapType());
        }
        return this.isMapMapping.booleanValue();
    }

    public boolean isRemovedEnumMapping() {
        return MappingMethodUtils.isEnumMapping(this);
    }

    public boolean isValueMapping() {
        if (this.isValueMapping == null) {
            this.isValueMapping = Boolean.valueOf(MappingMethodUtils.isEnumMapping(this) && this.mappingMethodOptions.getMappings().isEmpty());
        }
        return this.isValueMapping.booleanValue();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(this.returnType.toString());
        sb.append(StringUtils.SPACE);
        Type type = this.declaringMapper;
        if (type != null) {
            sb.append(type).append(".");
        }
        sb.append(getName()).append("(").append(Strings.join(this.parameters, ", ")).append(")");
        return sb.toString();
    }

    public List<SourceMethod> getApplicablePrototypeMethods() {
        if (this.applicablePrototypeMethods == null) {
            this.applicablePrototypeMethods = new ArrayList();
            for (SourceMethod sourceMethod : this.prototypeMethods) {
                if (canInheritFrom(sourceMethod)) {
                    this.applicablePrototypeMethods.add(sourceMethod);
                }
            }
        }
        return this.applicablePrototypeMethods;
    }

    public List<SourceMethod> getApplicableReversePrototypeMethods() {
        if (this.applicableReversePrototypeMethods == null) {
            this.applicableReversePrototypeMethods = new ArrayList();
            for (SourceMethod sourceMethod : this.prototypeMethods) {
                if (inverses(sourceMethod)) {
                    this.applicableReversePrototypeMethods.add(sourceMethod);
                }
            }
        }
        return this.applicableReversePrototypeMethods;
    }

    private static boolean allParametersAreAssignable(List<Parameter> list, List<Parameter> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        HashSet hashSet = new HashSet(list2);
        for (Parameter parameter : list) {
            boolean z = false;
            for (Parameter parameter2 : list2) {
                if (parameter.getType().isAssignableTo(parameter2.getType())) {
                    hashSet.remove(parameter2);
                    z = true;
                }
            }
            if (!z) {
                return false;
            }
        }
        return hashSet.isEmpty();
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean overridesMethod() {
        return this.declaringMapper == null && this.executable.getModifiers().contains(Modifier.ABSTRACT);
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean matches(List<Type> list, Type type) {
        return new MethodMatcher(this.typeUtils, this.typeFactory, this).matches(list, type);
    }

    public static boolean containsTargetTypeParameter(List<Parameter> list) {
        return list.stream().anyMatch(new Parameter$$ExternalSyntheticLambda3());
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public List<Type> getThrownTypes() {
        return this.exceptionTypes;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public MappingMethodOptions getOptions() {
        return this.mappingMethodOptions;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isStatic() {
        return this.executable.getModifiers().contains(Modifier.STATIC);
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isDefault() {
        return Executables.isDefaultMethod(this.executable);
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public Type getDefiningType() {
        return this.mapperToImplement;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isLifecycleCallbackMethod() {
        return Executables.isLifecycleCallbackMethod(getExecutable());
    }

    public boolean isAfterMappingMethod() {
        return Executables.isAfterMappingMethod(getExecutable());
    }

    public boolean isBeforeMappingMethod() {
        return Executables.isBeforeMappingMethod(getExecutable());
    }

    public boolean isAbstract() {
        return this.executable.getModifiers().contains(Modifier.ABSTRACT);
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public boolean isUpdateMethod() {
        return getMappingTargetParameter() != null;
    }

    public boolean hasObjectFactoryAnnotation() {
        return this.hasObjectFactoryAnnotation;
    }

    @Override // org.mapstruct.ap.internal.model.source.Method
    public String describe() {
        if (this.verboseLogging) {
            return toString();
        }
        return getResultType().describe() + StringUtils.SPACE + (this.declaringMapper != null ? this.declaringMapper.getName() + "." : "") + getName() + "(" + ((String) getParameters().stream().map(new Function() { // from class: org.mapstruct.ap.internal.model.source.SourceMethod$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Parameter) obj).describe();
            }
        }).collect(Collectors.joining(", "))) + ")";
    }
}
