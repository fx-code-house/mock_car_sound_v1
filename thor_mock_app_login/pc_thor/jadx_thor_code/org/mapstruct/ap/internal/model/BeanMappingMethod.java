package org.mapstruct.ap.internal.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.model.NestedTargetPropertyMappingHolder;
import org.mapstruct.ap.internal.model.PropertyMapping;
import org.mapstruct.ap.internal.model.beanmapping.MappingReference;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.beanmapping.SourceReference;
import org.mapstruct.ap.internal.model.beanmapping.TargetReference;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;
import org.mapstruct.ap.internal.util.accessor.ParameterElementAccessor;

/* loaded from: classes3.dex */
public class BeanMappingMethod extends NormalTypeMappingMethod {
    private final List<PropertyMapping> constantMappings;
    private final List<PropertyMapping> constructorConstantMappings;
    private final Map<String, List<PropertyMapping>> constructorMappingsByParameter;
    private final MethodReference finalizerMethod;
    private final MappingReferences mappingReferences;
    private final Map<String, List<PropertyMapping>> mappingsByParameter;
    private final List<PropertyMapping> propertyMappings;
    private final BuilderType returnTypeBuilder;
    private final Type returnTypeToConstruct;

    public static class Builder {
        private MappingBuilderContext ctx;
        private MethodReference factoryMethod;
        private boolean hasFactoryMethod;
        private MappingReferences mappingReferences;
        private Method method;
        private BuilderType returnTypeBuilder;
        private Set<String> targetProperties;
        private Map<String, Accessor> unprocessedConstructorProperties;
        private Map<String, Accessor> unprocessedSourceProperties;
        private Map<String, Accessor> unprocessedTargetProperties;
        private Type userDefinedReturnType;
        private final List<PropertyMapping> propertyMappings = new ArrayList();
        private final Set<Parameter> unprocessedSourceParameters = new HashSet();
        private final Set<String> existingVariableNames = new HashSet();
        private final Map<String, Set<MappingReference>> unprocessedDefinedTargets = new LinkedHashMap();

        public Builder mappingContext(MappingBuilderContext mappingBuilderContext) {
            this.ctx = mappingBuilderContext;
            return this;
        }

        public Builder userDefinedReturnType(Type type) {
            this.userDefinedReturnType = type;
            return this;
        }

        public Builder returnTypeBuilder(BuilderType builderType) {
            this.returnTypeBuilder = builderType;
            return this;
        }

        public Builder sourceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder forgedMethod(ForgedMethod forgedMethod) {
            this.method = forgedMethod;
            this.mappingReferences = forgedMethod.getMappingReferences();
            Parameter parameter = (Parameter) Collections.first(Parameter.getSourceParameters(forgedMethod.getParameters()));
            for (MappingReference mappingReference : this.mappingReferences.getMappingReferences()) {
                SourceReference sourceReference = mappingReference.getSourceReference();
                if (sourceReference != null) {
                    mappingReference.setSourceReference(new SourceReference.BuilderFromSourceReference().sourceParameter(parameter).sourceReference(sourceReference).build());
                }
            }
            return this;
        }

        /* JADX WARN: Removed duplicated region for block: B:15:0x003d  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0040 A[PHI: r4
          0x0040: PHI (r4v18 org.mapstruct.ap.internal.model.common.Type) = 
          (r4v17 org.mapstruct.ap.internal.model.common.Type)
          (r4v17 org.mapstruct.ap.internal.model.common.Type)
          (r4v13 org.mapstruct.ap.internal.model.common.Type)
          (r4v13 org.mapstruct.ap.internal.model.common.Type)
          (r4v20 org.mapstruct.ap.internal.model.common.Type)
          (r4v20 org.mapstruct.ap.internal.model.common.Type)
         binds: [B:27:0x0067, B:29:0x006d, B:20:0x004b, B:22:0x0051, B:11:0x0034, B:13:0x003a] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public org.mapstruct.ap.internal.model.BeanMappingMethod build() {
            /*
                Method dump skipped, instructions count: 536
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.mapstruct.ap.internal.model.BeanMappingMethod.Builder.build():org.mapstruct.ap.internal.model.BeanMappingMethod");
        }

        private void initializeMappingReferencesIfNeeded(Type type) {
            if (this.mappingReferences == null && (this.method instanceof SourceMethod)) {
                HashSet hashSet = new HashSet(this.unprocessedTargetProperties.keySet());
                hashSet.addAll(type.getPropertyReadAccessors().keySet());
                this.mappingReferences = MappingReferences.forSourceMethod((SourceMethod) this.method, type, hashSet, this.ctx.getMessager(), this.ctx.getTypeFactory());
            }
        }

        private boolean isBuilderRequired() {
            return (this.returnTypeBuilder == null || (this.method.isUpdateMethod() && this.method.isMappingTargetAssignableToReturnType())) ? false : true;
        }

        private boolean shouldCallFinalizerMethod(Type type) {
            return (type == null || type.isAssignableTo(this.method.getReturnType()) || this.returnTypeBuilder == null) ? false : true;
        }

        private MethodReference getFinalizerMethod() {
            return BuilderFinisherMethodResolver.getBuilderFinisherMethod(this.method, this.returnTypeBuilder, this.ctx);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void handleUnprocessedDefinedTargets() {
            Iterator<Map.Entry<String, Set<MappingReference>>> it = this.unprocessedDefinedTargets.entrySet().iterator();
            while (it.hasNext()) {
                String key = it.next().getKey();
                if (this.unprocessedTargetProperties.containsKey(key)) {
                    List<Parameter> sourceParameters = this.method.getSourceParameters();
                    boolean z = sourceParameters.size() > 1;
                    Iterator<Parameter> it2 = sourceParameters.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            SourceReference sourceReferenceBuild = new SourceReference.BuilderFromProperty().sourceParameter(it2.next()).name(key).build();
                            Accessor accessor = this.method.getResultType().getPropertyReadAccessors().get(key);
                            MappingReferences mappingReferencesExtractMappingReferences = extractMappingReferences(key, true);
                            PropertyMapping propertyMappingBuild = ((PropertyMapping.PropertyMappingBuilder) new PropertyMapping.PropertyMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).target(key, accessor, this.unprocessedTargetProperties.get(key)).sourceReference(sourceReferenceBuild).existingVariableNames(this.existingVariableNames).dependsOn(mappingReferencesExtractMappingReferences.collectNestedDependsOn()).forgeMethodWithMappingReferences(mappingReferencesExtractMappingReferences).forceUpdateMethod(z).forgedNamedBased(false).build();
                            if (propertyMappingBuild != null) {
                                this.unprocessedTargetProperties.remove(key);
                                this.unprocessedConstructorProperties.remove(key);
                                this.unprocessedSourceProperties.remove(key);
                                it.remove();
                                this.propertyMappings.add(propertyMappingBuild);
                                break;
                            }
                        }
                    }
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void handleUnmappedConstructorProperties() {
            for (Map.Entry<String, Accessor> entry : this.unprocessedConstructorProperties.entrySet()) {
                Accessor value = entry.getValue();
                Type type = this.ctx.getTypeFactory().getType(value.getAccessedType());
                this.propertyMappings.add(((PropertyMapping.JavaExpressionMappingBuilder) new PropertyMapping.JavaExpressionMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).javaExpression(type.getNull()).existingVariableNames(this.existingVariableNames).target(entry.getKey(), null, value).dependsOn(java.util.Collections.emptySet()).mirror(null).build());
            }
            this.unprocessedConstructorProperties.clear();
        }

        private void sortPropertyMappingsByDependencies() {
            GraphAnalyzer.GraphAnalyzerBuilder graphAnalyzerBuilderBuilder = GraphAnalyzer.builder();
            for (PropertyMapping propertyMapping : this.propertyMappings) {
                graphAnalyzerBuilderBuilder.withNode(propertyMapping.getName(), propertyMapping.getDependsOn());
            }
            final GraphAnalyzer graphAnalyzerBuild = graphAnalyzerBuilderBuilder.build();
            if (!graphAnalyzerBuild.getCycles().isEmpty()) {
                HashSet hashSet = new HashSet();
                Iterator<List<String>> it = graphAnalyzerBuild.getCycles().iterator();
                while (it.hasNext()) {
                    hashSet.add(Strings.join(it.next(), " -> "));
                }
                this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.BEANMAPPING_CYCLE_BETWEEN_PROPERTIES, Strings.join(hashSet, ", "));
                return;
            }
            this.propertyMappings.sort(Comparator.comparingInt(new ToIntFunction() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$Builder$$ExternalSyntheticLambda6
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return graphAnalyzerBuild.getTraversalSequence(((PropertyMapping) obj).getName());
                }
            }));
        }

        private boolean canResultTypeFromBeanMappingBeConstructed(Type type) {
            if (type.isAbstract()) {
                this.ctx.getMessager().printMessage(this.method.getExecutable(), this.method.getOptions().getBeanMapping().getMirror(), Message.BEANMAPPING_ABSTRACT, type.describe(), this.method.getResultType().describe());
                return false;
            }
            if (!type.isAssignableTo(this.method.getResultType())) {
                this.ctx.getMessager().printMessage(this.method.getExecutable(), this.method.getOptions().getBeanMapping().getMirror(), Message.BEANMAPPING_NOT_ASSIGNABLE, type.describe(), this.method.getResultType().describe());
                return false;
            }
            if (type.hasAccessibleConstructor()) {
                return true;
            }
            this.ctx.getMessager().printMessage(this.method.getExecutable(), this.method.getOptions().getBeanMapping().getMirror(), Message.GENERAL_NO_SUITABLE_CONSTRUCTOR, type.describe());
            return false;
        }

        private boolean canReturnTypeBeConstructed(Type type) {
            if (type.isAbstract()) {
                this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.GENERAL_ABSTRACT_RETURN_TYPE, type.describe());
                return false;
            }
            if (type.hasAccessibleConstructor()) {
                return true;
            }
            this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.GENERAL_NO_SUITABLE_CONSTRUCTOR, type.describe());
            return false;
        }

        private void initializeFactoryMethod(Type type, SelectionParameters selectionParameters) {
            BuilderType builderType;
            List<SelectedMethod<SourceMethod>> matchingFactoryMethods = ObjectFactoryMethodResolver.getMatchingFactoryMethods(this.method, type, selectionParameters, this.ctx);
            if (matchingFactoryMethods.isEmpty()) {
                if (this.factoryMethod != null || (builderType = this.returnTypeBuilder) == null) {
                    return;
                }
                MethodReference builderFactoryMethod = ObjectFactoryMethodResolver.getBuilderFactoryMethod(this.method, builderType);
                this.factoryMethod = builderFactoryMethod;
                this.hasFactoryMethod = builderFactoryMethod != null;
                return;
            }
            if (matchingFactoryMethods.size() == 1) {
                this.factoryMethod = ObjectFactoryMethodResolver.getFactoryMethodReference(this.method, (SelectedMethod) Collections.first(matchingFactoryMethods), this.ctx);
                this.hasFactoryMethod = true;
            } else {
                this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.GENERAL_AMBIGUOUS_FACTORY_METHOD, type.describe(), matchingFactoryMethods.stream().map(new BeanMappingMethod$Builder$$ExternalSyntheticLambda0()).map(new BeanMappingMethod$Builder$$ExternalSyntheticLambda1()).collect(Collectors.joining(", ")));
                this.hasFactoryMethod = true;
            }
        }

        private ConstructorAccessor getConstructorAccessor(Type type) {
            ExecutableElement executableElement;
            if (type.isRecord()) {
                List<Element> recordComponents = type.getRecordComponents();
                ArrayList arrayList = new ArrayList(recordComponents.size());
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Element element : recordComponents) {
                    TypeMirror typeMirrorAsMemberOf = this.ctx.getTypeUtils().asMemberOf(type.getTypeMirror(), element);
                    String string = element.getSimpleName().toString();
                    Accessor accessorCreateConstructorAccessor = createConstructorAccessor(element, typeMirrorAsMemberOf, string);
                    linkedHashMap.put(string, accessorCreateConstructorAccessor);
                    arrayList.add(ParameterBinding.fromTypeAndName(this.ctx.getTypeFactory().getType(typeMirrorAsMemberOf), accessorCreateConstructorAccessor.getSimpleName()));
                }
                return new ConstructorAccessor(arrayList, linkedHashMap);
            }
            List listConstructorsIn = ElementFilter.constructorsIn(type.getTypeElement().getEnclosedElements());
            ArrayList arrayList2 = new ArrayList(listConstructorsIn.size());
            ArrayList arrayList3 = new ArrayList();
            Iterator it = listConstructorsIn.iterator();
            ExecutableElement executableElement2 = null;
            while (true) {
                if (!it.hasNext()) {
                    executableElement = null;
                    break;
                }
                executableElement = (ExecutableElement) it.next();
                if (!executableElement.getModifiers().contains(Modifier.PRIVATE)) {
                    if (hasDefaultAnnotationFromAnyPackage(executableElement)) {
                        break;
                    }
                    if (executableElement.getParameters().isEmpty()) {
                        executableElement2 = executableElement;
                    } else {
                        arrayList2.add(executableElement);
                    }
                    if (executableElement.getModifiers().contains(Modifier.PUBLIC)) {
                        arrayList3.add(executableElement);
                    }
                }
            }
            if (executableElement != null) {
                return getConstructorAccessor(type, executableElement);
            }
            if (arrayList3.size() == 1) {
                ExecutableElement executableElement3 = (ExecutableElement) arrayList3.get(0);
                if (executableElement3.getParameters().isEmpty()) {
                    return null;
                }
                return getConstructorAccessor(type, executableElement3);
            }
            if (executableElement2 != null || arrayList2.isEmpty()) {
                return null;
            }
            if (arrayList2.size() > 1) {
                this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.GENERAL_AMBIGUOUS_CONSTRUCTORS, type, Strings.join(listConstructorsIn, ", "));
                return null;
            }
            return getConstructorAccessor(type, (ExecutableElement) arrayList2.get(0));
        }

        private ConstructorAccessor getConstructorAccessor(Type type, ExecutableElement executableElement) {
            List<String> arrayValues;
            List<Parameter> parameters = this.ctx.getTypeFactory().getParameters((DeclaredType) type.getTypeMirror(), executableElement);
            Iterator it = executableElement.getAnnotationMirrors().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                AnnotationMirror annotationMirror = (AnnotationMirror) it.next();
                if (annotationMirror.getAnnotationType().asElement().getSimpleName().contentEquals("ConstructorProperties")) {
                    for (Map.Entry entry : annotationMirror.getElementValues().entrySet()) {
                        if (((ExecutableElement) entry.getKey()).getSimpleName().contentEquals("value")) {
                            arrayValues = getArrayValues((AnnotationValue) entry.getValue());
                            break;
                        }
                    }
                }
            }
            arrayValues = null;
            if (arrayValues == null) {
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                ArrayList arrayList = new ArrayList(parameters.size());
                for (Parameter parameter : parameters) {
                    String name = parameter.getName();
                    Accessor accessorCreateConstructorAccessor = createConstructorAccessor(parameter.getElement(), parameter.getType().getTypeMirror(), name);
                    linkedHashMap.put(name, accessorCreateConstructorAccessor);
                    arrayList.add(ParameterBinding.fromTypeAndName(parameter.getType(), accessorCreateConstructorAccessor.getSimpleName()));
                }
                return new ConstructorAccessor(arrayList, linkedHashMap);
            }
            if (arrayValues.size() != parameters.size()) {
                this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.GENERAL_CONSTRUCTOR_PROPERTIES_NOT_MATCHING_PARAMETERS, type);
                return null;
            }
            LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            ArrayList arrayList2 = new ArrayList(arrayValues.size());
            for (int i = 0; i < arrayValues.size(); i++) {
                String str = arrayValues.get(i);
                Parameter parameter2 = parameters.get(i);
                Accessor accessorCreateConstructorAccessor2 = createConstructorAccessor(parameter2.getElement(), parameter2.getType().getTypeMirror(), str);
                linkedHashMap2.put(str, accessorCreateConstructorAccessor2);
                arrayList2.add(ParameterBinding.fromTypeAndName(parameter2.getType(), accessorCreateConstructorAccessor2.getSimpleName()));
            }
            return new ConstructorAccessor(arrayList2, linkedHashMap2);
        }

        private Accessor createConstructorAccessor(Element element, TypeMirror typeMirror, String str) {
            String safeVariableName = Strings.getSafeVariableName(str, this.existingVariableNames);
            this.existingVariableNames.add(safeVariableName);
            return new ParameterElementAccessor(element, typeMirror, safeVariableName);
        }

        private boolean hasDefaultAnnotationFromAnyPackage(Element element) {
            Iterator it = element.getAnnotationMirrors().iterator();
            while (it.hasNext()) {
                if (((AnnotationMirror) it.next()).getAnnotationType().asElement().getSimpleName().contentEquals("Default")) {
                    return true;
                }
            }
            return false;
        }

        private List<String> getArrayValues(AnnotationValue annotationValue) {
            if (!(annotationValue.getValue() instanceof List)) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<AnnotationValue> it = getValueAsList(annotationValue).iterator();
            while (it.hasNext()) {
                Object value = it.next().getValue();
                if (!(value instanceof String)) {
                    return null;
                }
                arrayList.add((String) value);
            }
            return arrayList;
        }

        private List<AnnotationValue> getValueAsList(AnnotationValue annotationValue) {
            return (List) annotationValue.getValue();
        }

        private boolean handleDefinedMappings(Type type) {
            String shallowestPropertyName;
            HashSet hashSet = new HashSet();
            boolean zHandleDefinedNestedTargetMapping = this.mappingReferences.hasNestedTargetReferences() ? handleDefinedNestedTargetMapping(hashSet, type) : false;
            for (MappingReference mappingReference : this.mappingReferences.getMappingReferences()) {
                if (mappingReference.isValid()) {
                    if (!hashSet.contains(mappingReference.getTargetReference().getShallowestPropertyName()) && handleDefinedMapping(mappingReference, type, hashSet)) {
                        zHandleDefinedNestedTargetMapping = true;
                    }
                    if (mappingReference.getSourceReference() != null && (shallowestPropertyName = mappingReference.getSourceReference().getShallowestPropertyName()) != null) {
                        this.unprocessedSourceProperties.remove(shallowestPropertyName);
                    }
                } else {
                    zHandleDefinedNestedTargetMapping = true;
                }
            }
            for (String str : hashSet) {
                this.unprocessedTargetProperties.remove(str);
                this.unprocessedConstructorProperties.remove(str);
                this.unprocessedDefinedTargets.remove(str);
            }
            return zHandleDefinedNestedTargetMapping;
        }

        private boolean handleDefinedNestedTargetMapping(Set<String> set, Type type) {
            NestedTargetPropertyMappingHolder nestedTargetPropertyMappingHolderBuild = new NestedTargetPropertyMappingHolder.Builder().mappingContext(this.ctx).method(this.method).targetPropertiesWriteAccessors(this.unprocessedTargetProperties).targetPropertyType(type).mappingReferences(this.mappingReferences).existingVariableNames(this.existingVariableNames).build();
            this.unprocessedSourceParameters.removeAll(nestedTargetPropertyMappingHolderBuild.getProcessedSourceParameters());
            this.propertyMappings.addAll(nestedTargetPropertyMappingHolderBuild.getPropertyMappings());
            set.addAll(nestedTargetPropertyMappingHolderBuild.getHandledTargets());
            for (Map.Entry<String, Set<MappingReference>> entry : nestedTargetPropertyMappingHolderBuild.getUnprocessedDefinedTarget().entrySet()) {
                if (!entry.getValue().isEmpty()) {
                    this.unprocessedDefinedTargets.put(entry.getKey(), entry.getValue());
                }
            }
            return nestedTargetPropertyMappingHolderBuild.hasErrorOccurred();
        }

        /* JADX WARN: Multi-variable type inference failed */
        private boolean handleDefinedMapping(MappingReference mappingReference, Type type, Set<String> set) {
            Message message;
            Object[] objArr;
            String[] strArr;
            Message message2;
            TargetReference targetReference = mappingReference.getTargetReference();
            MappingOptions mapping = mappingReference.getMapping();
            boolean z = false;
            for (String str : mapping.getDependsOn()) {
                if (!this.targetProperties.contains(str)) {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), mapping.getMirror(), mapping.getDependsOnAnnotationValue(), Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_DEPENDS_ON, str);
                    z = true;
                }
            }
            final String str2 = (String) Collections.first(targetReference.getPropertyEntries());
            if (this.unprocessedDefinedTargets.containsKey(str2)) {
                return false;
            }
            Accessor accessor = this.unprocessedTargetProperties.get(str2);
            Accessor accessor2 = type.getPropertyReadAccessors().get(str2);
            if (accessor == null) {
                if (accessor2 == null) {
                    if (mapping.getInheritContext() != null && mapping.getInheritContext().isForwarded() && mapping.getInheritContext().getTemplateMethod().isUpdateMethod() != this.method.isUpdateMethod()) {
                        return false;
                    }
                    String mostSimilarWord = Strings.getMostSimilarWord(str2, type.getPropertyReadAccessors().keySet());
                    if (targetReference.getPathProperties().isEmpty()) {
                        message2 = Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_RESULTTYPE;
                        strArr = new String[]{str2, type.describe(), mostSimilarWord};
                    } else {
                        ArrayList arrayList = new ArrayList(targetReference.getPathProperties());
                        arrayList.add(mostSimilarWord);
                        Message message3 = Message.BEANMAPPING_UNKNOWN_PROPERTY_IN_TYPE;
                        strArr = new String[]{str2, type.describe(), mapping.getTargetName(), Strings.join(arrayList, ".")};
                        message2 = message3;
                    }
                    this.ctx.getMessager().printMessage(mapping.getElement(), mapping.getMirror(), mapping.getTargetAnnotationValue(), message2, strArr);
                    return true;
                }
                if (mapping.getInheritContext() != null && mapping.getInheritContext().isReversed()) {
                    return false;
                }
                if (!mapping.isIgnored()) {
                    if (Objects.equals(str2, mapping.getTargetName())) {
                        message = Message.BEANMAPPING_PROPERTY_HAS_NO_WRITE_ACCESSOR_IN_RESULTTYPE;
                        objArr = new Object[]{mapping.getTargetName(), type.describe()};
                    } else {
                        message = Message.BEANMAPPING_PROPERTY_HAS_NO_WRITE_ACCESSOR_IN_TYPE;
                        objArr = new Object[]{str2, type.describe(), mapping.getTargetName()};
                    }
                    this.ctx.getMessager().printMessage(mapping.getElement(), mapping.getMirror(), mapping.getTargetAnnotationValue(), message, objArr);
                    return true;
                }
            }
            PropertyMapping propertyMappingBuild = null;
            if (mapping.isIgnored()) {
                if (accessor != null && accessor.getAccessorType() == AccessorType.PARAMETER) {
                    propertyMappingBuild = ((PropertyMapping.JavaExpressionMappingBuilder) new PropertyMapping.JavaExpressionMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).javaExpression(this.ctx.getTypeFactory().getType(accessor.getAccessedType()).getNull()).existingVariableNames(this.existingVariableNames).target(str2, accessor2, accessor).dependsOn(mapping.getDependsOn()).mirror(mapping.getMirror()).build();
                }
                set.add(str2);
            } else if (mapping.getConstant() != null) {
                propertyMappingBuild = ((PropertyMapping.ConstantMappingBuilder) new PropertyMapping.ConstantMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).constantExpression(mapping.getConstant()).target(str2, accessor2, accessor).formattingParameters(mapping.getFormattingParameters()).selectionParameters(mapping.getSelectionParameters()).options(mapping).existingVariableNames(this.existingVariableNames).dependsOn(mapping.getDependsOn()).mirror(mapping.getMirror()).build();
                set.add(str2);
            } else if (mapping.getJavaExpression() != null) {
                propertyMappingBuild = ((PropertyMapping.JavaExpressionMappingBuilder) new PropertyMapping.JavaExpressionMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).javaExpression(mapping.getJavaExpression()).existingVariableNames(this.existingVariableNames).target(str2, accessor2, accessor).dependsOn(mapping.getDependsOn()).mirror(mapping.getMirror()).build();
                set.add(str2);
            } else {
                SourceReference sourceReference = mappingReference.getSourceReference();
                if (sourceReference == null) {
                    Iterator<Parameter> it = this.method.getSourceParameters().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        SourceReference sourceRefByTargetName = getSourceRefByTargetName(it.next(), str2);
                        if (sourceRefByTargetName != null) {
                            if (sourceReference != null) {
                                this.ctx.getMessager().printMessage(this.method.getExecutable(), mappingReference.getMapping().getMirror(), Message.BEANMAPPING_SEVERAL_POSSIBLE_SOURCES, str2);
                                z = true;
                                break;
                            }
                            sourceReference = sourceRefByTargetName;
                        }
                    }
                }
                if (sourceReference == null) {
                    sourceReference = (SourceReference) this.method.getSourceParameters().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$Builder$$ExternalSyntheticLambda2
                        @Override // java.util.function.Predicate
                        public final boolean test(Object obj) {
                            return str2.equals(((Parameter) obj).getName());
                        }
                    }).findAny().map(new Function() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$Builder$$ExternalSyntheticLambda3
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return new SourceReference.BuilderFromProperty().sourceParameter((Parameter) obj).name(str2).build();
                        }
                    }).orElse(null);
                }
                if (sourceReference != null) {
                    if (sourceReference.isValid()) {
                        propertyMappingBuild = ((PropertyMapping.PropertyMappingBuilder) new PropertyMapping.PropertyMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).target(str2, accessor2, accessor).sourcePropertyName(mapping.getSourceName()).sourceReference(sourceReference).selectionParameters(mapping.getSelectionParameters()).formattingParameters(mapping.getFormattingParameters()).existingVariableNames(this.existingVariableNames).dependsOn(mapping.getDependsOn()).defaultValue(mapping.getDefaultValue()).defaultJavaExpression(mapping.getDefaultJavaExpression()).mirror(mapping.getMirror()).options(mapping).build();
                        set.add(str2);
                        this.unprocessedSourceParameters.remove(sourceReference.getParameter());
                    }
                } else if (this.method.getSourceParameters().size() == 1) {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), mapping.getMirror(), mapping.getTargetAnnotationValue(), Message.PROPERTYMAPPING_CANNOT_DETERMINE_SOURCE_PROPERTY_FROM_TARGET, this.method.getSourceParameters().get(0).getName(), str2);
                } else {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), mapping.getMirror(), mapping.getTargetAnnotationValue(), Message.PROPERTYMAPPING_CANNOT_DETERMINE_SOURCE_PARAMETER_FROM_TARGET, str2);
                }
                z = true;
            }
            if (propertyMappingBuild != null) {
                this.propertyMappings.add(propertyMappingBuild);
            }
            return z;
        }

        private void applyTargetThisMapping() {
            final HashSet hashSet = new HashSet();
            Iterator<MappingReference> it = this.mappingReferences.getTargetThisReferences().iterator();
            while (it.hasNext()) {
                List<SourceReference> list = (List) it.next().getSourceReference().push(this.ctx.getTypeFactory(), this.ctx.getMessager(), this.method).stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$Builder$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.m2365x34a95774(hashSet, (SourceReference) obj);
                    }
                }).collect(Collectors.toList());
                applyPropertyNameBasedMapping(list);
                hashSet.addAll((Collection) list.stream().map(new Function() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$Builder$$ExternalSyntheticLambda5
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((SourceReference) obj).getDeepestPropertyName();
                    }
                }).collect(Collectors.toList()));
            }
        }

        /* renamed from: lambda$applyTargetThisMapping$3$org-mapstruct-ap-internal-model-BeanMappingMethod$Builder, reason: not valid java name */
        /* synthetic */ boolean m2365x34a95774(Set set, SourceReference sourceReference) {
            return this.unprocessedTargetProperties.containsKey(sourceReference.getDeepestPropertyName()) || set.contains(sourceReference.getDeepestPropertyName());
        }

        private void applyPropertyNameBasedMapping() {
            ArrayList arrayList = new ArrayList();
            for (String str : this.unprocessedTargetProperties.keySet()) {
                Iterator<Parameter> it = this.method.getSourceParameters().iterator();
                while (it.hasNext()) {
                    SourceReference sourceRefByTargetName = getSourceRefByTargetName(it.next(), str);
                    if (sourceRefByTargetName != null) {
                        arrayList.add(sourceRefByTargetName);
                    }
                }
            }
            applyPropertyNameBasedMapping(arrayList);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void applyPropertyNameBasedMapping(List<SourceReference> list) {
            for (SourceReference sourceReference : list) {
                String deepestPropertyName = sourceReference.getDeepestPropertyName();
                Accessor accessorRemove = this.unprocessedTargetProperties.remove(deepestPropertyName);
                this.unprocessedConstructorProperties.remove(deepestPropertyName);
                if (accessorRemove == null) {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.BEANMAPPING_SEVERAL_POSSIBLE_SOURCES, deepestPropertyName);
                } else {
                    PropertyMapping propertyMappingBuild = ((PropertyMapping.PropertyMappingBuilder) new PropertyMapping.PropertyMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).target(deepestPropertyName, this.method.getResultType().getPropertyReadAccessors().get(deepestPropertyName), accessorRemove).sourceReference(sourceReference).existingVariableNames(this.existingVariableNames).forgeMethodWithMappingReferences(extractMappingReferences(deepestPropertyName, false)).options(this.method.getOptions().getBeanMapping()).build();
                    this.unprocessedSourceParameters.remove(sourceReference.getParameter());
                    if (propertyMappingBuild != null) {
                        this.propertyMappings.add(propertyMappingBuild);
                    }
                    this.unprocessedDefinedTargets.remove(deepestPropertyName);
                    this.unprocessedSourceProperties.remove(deepestPropertyName);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private void applyParameterNameBasedMapping() {
            Iterator<Map.Entry<String, Accessor>> it = this.unprocessedTargetProperties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Accessor> next = it.next();
                Iterator<Parameter> it2 = this.unprocessedSourceParameters.iterator();
                while (it2.hasNext()) {
                    Parameter next2 = it2.next();
                    if (next2.getName().equals(next.getKey())) {
                        SourceReference sourceReferenceBuild = new SourceReference.BuilderFromProperty().sourceParameter(next2).name(next.getKey()).build();
                        Accessor accessor = this.method.getResultType().getPropertyReadAccessors().get(next.getKey());
                        this.propertyMappings.add(((PropertyMapping.PropertyMappingBuilder) new PropertyMapping.PropertyMappingBuilder().mappingContext(this.ctx)).sourceMethod(this.method).target(next.getKey(), accessor, next.getValue()).sourceReference(sourceReferenceBuild).existingVariableNames(this.existingVariableNames).forgeMethodWithMappingReferences(extractMappingReferences(next.getKey(), false)).options(this.method.getOptions().getBeanMapping()).build());
                        it.remove();
                        it2.remove();
                        this.unprocessedDefinedTargets.remove(next.getKey());
                        this.unprocessedSourceProperties.remove(next.getKey());
                        if (!next2.getType().isPrimitive() && !next2.getType().isArrayType()) {
                            Iterator<String> it3 = next2.getType().getPropertyReadAccessors().keySet().iterator();
                            while (it3.hasNext()) {
                                this.unprocessedSourceProperties.remove(it3.next());
                            }
                        }
                        this.unprocessedConstructorProperties.remove(next.getKey());
                    }
                }
            }
        }

        private SourceReference getSourceRefByTargetName(Parameter parameter, String str) {
            Accessor accessor;
            if (parameter.getType().isPrimitive() || parameter.getType().isArrayType() || (accessor = parameter.getType().getPropertyReadAccessors().get(str)) == null) {
                return null;
            }
            return new SourceReference.BuilderFromProperty().sourceParameter(parameter).type(this.ctx.getTypeFactory().getReturnType(parameter.getType().getTypeMirror(), accessor)).readAccessor(accessor).presenceChecker(parameter.getType().getPropertyPresenceCheckers().get(str)).name(str).build();
        }

        private MappingReferences extractMappingReferences(String str, boolean z) {
            if (this.unprocessedDefinedTargets.containsKey(str)) {
                return new MappingReferences(this.unprocessedDefinedTargets.get(str), z);
            }
            return null;
        }

        private ReportingPolicyGem getUnmappedTargetPolicy() {
            if (this.mappingReferences.isForForgedMethods()) {
                return ReportingPolicyGem.IGNORE;
            }
            return this.method.getOptions().getMapper().unmappedTargetPolicy();
        }

        private void reportErrorForUnmappedTargetPropertiesIfRequired() {
            ReportingPolicyGem unmappedTargetPolicy = getUnmappedTargetPolicy();
            if ((this.method instanceof ForgedMethod) && this.targetProperties.isEmpty()) {
                ForgedMethod forgedMethod = (ForgedMethod) this.method;
                if (forgedMethod.getHistory() == null) {
                    Type type = this.method.getParameters().get(0).getType();
                    Type returnType = this.method.getReturnType();
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.PROPERTYMAPPING_FORGED_MAPPING_NOT_FOUND, type.describe(), returnType.describe(), returnType.describe(), type.describe());
                    return;
                } else {
                    ForgedMethodHistory history = forgedMethod.getHistory();
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.PROPERTYMAPPING_FORGED_MAPPING_WITH_HISTORY_NOT_FOUND, history.createSourcePropertyErrorMessage(), history.getTargetType().describe(), history.createTargetPropertyName(), history.getTargetType().describe(), history.getSourceType().describe());
                    return;
                }
            }
            if (this.unprocessedTargetProperties.isEmpty() || !unmappedTargetPolicy.requiresReport()) {
                return;
            }
            if (!(this.method instanceof ForgedMethod)) {
                this.ctx.getMessager().printMessage(this.method.getExecutable(), unmappedTargetPolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ? Message.BEANMAPPING_UNMAPPED_TARGETS_ERROR : Message.BEANMAPPING_UNMAPPED_TARGETS_WARNING, MessageFormat.format("{0,choice,1#property|1<properties}: \"{1}\"", Integer.valueOf(this.unprocessedTargetProperties.size()), Strings.join(this.unprocessedTargetProperties.keySet(), ", ")));
                return;
            }
            if (this.ctx.isErroneous()) {
                return;
            }
            Message message = unmappedTargetPolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ? Message.BEANMAPPING_UNMAPPED_FORGED_TARGETS_ERROR : Message.BEANMAPPING_UNMAPPED_FORGED_TARGETS_WARNING;
            String strDescribe = this.method.getParameters().get(0).getType().describe();
            String strDescribe2 = this.method.getReturnType().describe();
            if (((ForgedMethod) this.method).getHistory() != null) {
                ForgedMethodHistory history2 = ((ForgedMethod) this.method).getHistory();
                String strCreateSourcePropertyErrorMessage = history2.createSourcePropertyErrorMessage();
                strDescribe2 = MessageFormat.format("\"{0} {1}\"", history2.getTargetType().describe(), history2.createTargetPropertyName());
                strDescribe = strCreateSourcePropertyErrorMessage;
            }
            this.ctx.getMessager().printMessage(this.method.getExecutable(), message, MessageFormat.format("{0,choice,1#property|1<properties}: \"{1}\"", Integer.valueOf(this.unprocessedTargetProperties.size()), Strings.join(this.unprocessedTargetProperties.keySet(), ", ")), strDescribe, strDescribe2);
        }

        private ReportingPolicyGem getUnmappedSourcePolicy() {
            if (this.mappingReferences.isForForgedMethods()) {
                return ReportingPolicyGem.IGNORE;
            }
            return this.method.getOptions().getMapper().unmappedSourcePolicy();
        }

        private void reportErrorForUnmappedSourcePropertiesIfRequired() {
            ReportingPolicyGem unmappedSourcePolicy = getUnmappedSourcePolicy();
            if (this.unprocessedSourceProperties.isEmpty() || !unmappedSourcePolicy.requiresReport()) {
                return;
            }
            this.ctx.getMessager().printMessage(this.method.getExecutable(), unmappedSourcePolicy.getDiagnosticKind() == Diagnostic.Kind.ERROR ? Message.BEANMAPPING_UNMAPPED_SOURCES_ERROR : Message.BEANMAPPING_UNMAPPED_SOURCES_WARNING, MessageFormat.format("{0,choice,1#property|1<properties}: \"{1}\"", Integer.valueOf(this.unprocessedSourceProperties.size()), Strings.join(this.unprocessedSourceProperties.keySet(), ", ")));
        }
    }

    private static class ConstructorAccessor {
        private final Map<String, Accessor> constructorAccessors;
        private final List<ParameterBinding> parameterBindings;

        private ConstructorAccessor(List<ParameterBinding> list, Map<String, Accessor> map) {
            this.parameterBindings = list;
            this.constructorAccessors = map;
        }
    }

    private BeanMappingMethod(Method method, Collection<String> collection, List<PropertyMapping> list, MethodReference methodReference, boolean z, Type type, BuilderType builderType, List<LifecycleCallbackMethodReference> list2, List<LifecycleCallbackMethodReference> list3, MethodReference methodReference2, MappingReferences mappingReferences) {
        super(method, collection, methodReference, z, list2, list3);
        this.propertyMappings = list;
        this.returnTypeBuilder = builderType;
        this.finalizerMethod = methodReference2;
        this.mappingReferences = mappingReferences;
        this.mappingsByParameter = new HashMap();
        this.constantMappings = new ArrayList(list.size());
        this.constructorMappingsByParameter = new LinkedHashMap();
        this.constructorConstantMappings = new ArrayList();
        Set set = (Set) getSourceParameters().stream().map(new Function() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Parameter) obj).getName();
            }
        }).collect(Collectors.toSet());
        for (PropertyMapping propertyMapping : list) {
            if (propertyMapping.isConstructorMapping()) {
                if (set.contains(propertyMapping.getSourceBeanName())) {
                    this.constructorMappingsByParameter.computeIfAbsent(propertyMapping.getSourceBeanName(), new Function() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$$ExternalSyntheticLambda2
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return BeanMappingMethod.lambda$new$0((String) obj);
                        }
                    }).add(propertyMapping);
                } else {
                    this.constructorConstantMappings.add(propertyMapping);
                }
            } else if (set.contains(propertyMapping.getSourceBeanName())) {
                this.mappingsByParameter.computeIfAbsent(propertyMapping.getSourceBeanName(), new Function() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return BeanMappingMethod.lambda$new$1((String) obj);
                    }
                }).add(propertyMapping);
            } else {
                this.constantMappings.add(propertyMapping);
            }
        }
        this.returnTypeToConstruct = type;
    }

    static /* synthetic */ List lambda$new$0(String str) {
        return new ArrayList();
    }

    static /* synthetic */ List lambda$new$1(String str) {
        return new ArrayList();
    }

    public List<PropertyMapping> getConstantMappings() {
        return this.constantMappings;
    }

    public List<PropertyMapping> getConstructorConstantMappings() {
        return this.constructorConstantMappings;
    }

    public List<PropertyMapping> propertyMappingsByParameter(Parameter parameter) {
        return this.mappingsByParameter.getOrDefault(parameter.getName(), java.util.Collections.emptyList());
    }

    public List<PropertyMapping> constructorPropertyMappingsByParameter(Parameter parameter) {
        return this.constructorMappingsByParameter.getOrDefault(parameter.getName(), java.util.Collections.emptyList());
    }

    public Type getReturnTypeToConstruct() {
        return this.returnTypeToConstruct;
    }

    public boolean hasConstructorMappings() {
        return (this.constructorMappingsByParameter.isEmpty() && this.constructorConstantMappings.isEmpty()) ? false : true;
    }

    public MethodReference getFinalizerMethod() {
        return this.finalizerMethod;
    }

    @Override // org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        for (PropertyMapping propertyMapping : this.propertyMappings) {
            importTypes.addAll(propertyMapping.getImportTypes());
            if (propertyMapping.isConstructorMapping()) {
                importTypes.addAll(propertyMapping.getTargetType().getImportTypes());
            }
        }
        Type type = this.returnTypeToConstruct;
        if (type != null) {
            importTypes.addAll(type.getImportTypes());
        }
        BuilderType builderType = this.returnTypeBuilder;
        if (builderType != null) {
            importTypes.add(builderType.getOwningType());
        }
        return importTypes;
    }

    public List<Parameter> getSourceParametersExcludingPrimitives() {
        return (List) getSourceParameters().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return BeanMappingMethod.lambda$getSourceParametersExcludingPrimitives$2((Parameter) obj);
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$getSourceParametersExcludingPrimitives$2(Parameter parameter) {
        return !parameter.getType().isPrimitive();
    }

    public List<Parameter> getSourcePrimitiveParameters() {
        return (List) getSourceParameters().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.model.BeanMappingMethod$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Parameter) obj).getType().isPrimitive();
            }
        }).collect(Collectors.toList());
    }

    @Override // org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod
    public int hashCode() {
        return super.hashCode();
    }

    @Override // org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        BeanMappingMethod beanMappingMethod = (BeanMappingMethod) obj;
        return super.equals(obj) && Objects.equals(this.propertyMappings, beanMappingMethod.propertyMappings) && Objects.equals(this.mappingReferences, beanMappingMethod.mappingReferences);
    }
}
