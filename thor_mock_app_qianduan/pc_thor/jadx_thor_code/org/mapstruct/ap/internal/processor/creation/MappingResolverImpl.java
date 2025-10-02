package org.mapstruct.ap.internal.processor.creation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.ap.MappingProcessor$$ExternalSyntheticLambda1;
import org.mapstruct.ap.internal.conversion.ConversionProvider;
import org.mapstruct.ap.internal.conversion.Conversions;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;
import org.mapstruct.ap.internal.model.ForgedMethodHistory;
import org.mapstruct.ap.internal.model.HelperMethod;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.MappingBuilderContext;
import org.mapstruct.ap.internal.model.MethodReference;
import org.mapstruct.ap.internal.model.SupportingField;
import org.mapstruct.ap.internal.model.SupportingMappingMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.DefaultConversionContext;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMappingMethods;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.processor.creation.MappingResolverImpl;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.MessageConstants;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class MappingResolverImpl implements MappingBuilderContext.MappingResolver {
    private static final String JL_OBJECT_NAME = Object.class.getName();
    private static final int LIMIT_REPORTING_AMBIGUOUS = 5;
    private final BuiltInMappingMethods builtInMethods;
    private final Conversions conversions;
    private final List<MapperReference> mapperReferences;
    private final FormattingMessager messager;
    private final MethodSelectors methodSelectors;
    private final List<Method> sourceModel;
    private final TypeFactory typeFactory;
    private final Types typeUtils;
    private final Set<SupportingMappingMethod> usedSupportedMappings = new HashSet();
    private final boolean verboseLogging;

    public MappingResolverImpl(FormattingMessager formattingMessager, Elements elements, Types types, TypeFactory typeFactory, List<Method> list, List<MapperReference> list2, boolean z) {
        this.messager = formattingMessager;
        this.typeUtils = types;
        this.typeFactory = typeFactory;
        this.sourceModel = list;
        this.mapperReferences = list2;
        this.conversions = new Conversions(typeFactory);
        this.builtInMethods = new BuiltInMappingMethods(typeFactory);
        this.methodSelectors = new MethodSelectors(types, elements, typeFactory, formattingMessager);
        this.verboseLogging = z;
    }

    @Override // org.mapstruct.ap.internal.model.MappingBuilderContext.MappingResolver
    public Assignment getTargetAssignment(Method method, ForgedMethodHistory forgedMethodHistory, Type type, FormattingParameters formattingParameters, SelectionCriteria selectionCriteria, SourceRHS sourceRHS, AnnotationMirror annotationMirror, Supplier<Assignment> supplier) {
        return new ResolvingAttempt(this.sourceModel, method, forgedMethodHistory, formattingParameters, sourceRHS, selectionCriteria, annotationMirror, supplier, this.builtInMethods.getBuiltInMethods(), this.messager, this.verboseLogging).getTargetAssignment(sourceRHS.getSourceTypeForMatching(), type);
    }

    @Override // org.mapstruct.ap.internal.model.MappingBuilderContext.MappingResolver
    public Set<SupportingMappingMethod> getUsedSupportedMappings() {
        return this.usedSupportedMappings;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MapperReference findMapperReference(Method method) {
        for (MapperReference mapperReference : this.mapperReferences) {
            if (mapperReference.getType().equals(method.getDeclaringMapper())) {
                mapperReference.setUsed(mapperReference.isUsed() || !method.isStatic());
                mapperReference.setTypeRequiresImport(true);
                return mapperReference;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ResolvingAttempt {
        private final List<BuiltInMethod> builtIns;
        private final ForgedMethodHistory description;
        private final Supplier<Assignment> forger;
        private final FormattingParameters formattingParameters;
        private final Method mappingMethod;
        private final FormattingMessager messager;
        private final List<Method> methods;
        private final AnnotationMirror positionHint;
        private final int reportingLimitAmbiguous;
        private final SelectionCriteria selectionCriteria;
        private final SourceRHS sourceRHS;
        private final Set<SupportingMappingMethod> supportingMethodCandidates;

        private ResolvingAttempt(List<Method> list, Method method, ForgedMethodHistory forgedMethodHistory, FormattingParameters formattingParameters, SourceRHS sourceRHS, SelectionCriteria selectionCriteria, AnnotationMirror annotationMirror, Supplier<Assignment> supplier, List<BuiltInMethod> list2, FormattingMessager formattingMessager, boolean z) {
            this.mappingMethod = method;
            this.description = forgedMethodHistory;
            this.methods = filterPossibleCandidateMethods(list);
            this.formattingParameters = formattingParameters == null ? FormattingParameters.EMPTY : formattingParameters;
            this.sourceRHS = sourceRHS;
            this.supportingMethodCandidates = new HashSet();
            this.selectionCriteria = selectionCriteria;
            this.positionHint = annotationMirror;
            this.forger = supplier;
            this.builtIns = list2;
            this.messager = formattingMessager;
            this.reportingLimitAmbiguous = z ? Integer.MAX_VALUE : 5;
        }

        private <T extends Method> List<T> filterPossibleCandidateMethods(List<T> list) {
            ArrayList arrayList = new ArrayList(list.size());
            for (T t : list) {
                if (isCandidateForMapping(t)) {
                    arrayList.add(t);
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Assignment getTargetAssignment(Type type, Type type2) {
            ConversionAssignment conversionAssignmentResolveViaConversion;
            if (allowMappingMethod()) {
                List bestMatch = getBestMatch(this.methods, type, type2);
                reportErrorWhenAmbiguous(bestMatch, type2);
                if (!bestMatch.isEmpty()) {
                    Assignment methodRef = toMethodRef((SelectedMethod) Collections.first(bestMatch));
                    methodRef.setAssignment(this.sourceRHS);
                    return methodRef;
                }
            }
            if (!hasQualfiers() && ((type.isAssignableTo(type2) || isAssignableThroughCollectionCopyConstructor(type, type2)) && allowDirect(type, type2))) {
                return this.sourceRHS;
            }
            if (type.isLiteral() && "java.lang.String".equals(type.getFullyQualifiedName()) && type2.isNative()) {
                return null;
            }
            if (allowConversion()) {
                if (!hasQualfiers() && (conversionAssignmentResolveViaConversion = resolveViaConversion(type, type2)) != null) {
                    conversionAssignmentResolveViaConversion.reportMessageWhenNarrowing(this.messager, this);
                    conversionAssignmentResolveViaConversion.getAssignment().setAssignment(this.sourceRHS);
                    return conversionAssignmentResolveViaConversion.getAssignment();
                }
                if (!hasQualfiers()) {
                    List bestMatch2 = getBestMatch(this.builtIns, type, type2);
                    reportErrorWhenAmbiguous(bestMatch2, type2);
                    if (!bestMatch2.isEmpty()) {
                        Assignment buildInRef = toBuildInRef((SelectedMethod) Collections.first(bestMatch2));
                        buildInRef.setAssignment(this.sourceRHS);
                        MappingResolverImpl.this.usedSupportedMappings.addAll(this.supportingMethodCandidates);
                        return buildInRef;
                    }
                }
            }
            if (allow2Steps()) {
                Assignment bestMatch3 = MethodMethod.getBestMatch(this, type, type2);
                if (bestMatch3 != null) {
                    MappingResolverImpl.this.usedSupportedMappings.addAll(this.supportingMethodCandidates);
                    return bestMatch3;
                }
                Assignment bestMatch4 = ConversionMethod.getBestMatch(this, type, type2);
                if (bestMatch4 != null) {
                    MappingResolverImpl.this.usedSupportedMappings.addAll(this.supportingMethodCandidates);
                    return bestMatch4;
                }
                this.selectionCriteria.setPreferUpdateMapping(false);
                Assignment bestMatch5 = MethodConversion.getBestMatch(this, type, type2);
                if (bestMatch5 != null) {
                    MappingResolverImpl.this.usedSupportedMappings.addAll(this.supportingMethodCandidates);
                    return bestMatch5;
                }
            }
            if (hasQualfiers()) {
                if ((type.isCollectionType() || type.isArrayType()) && type2.isIterableType()) {
                    return this.forger.get();
                }
                printQualifierMessage(this.selectionCriteria);
            } else if (allowMappingMethod()) {
                return this.forger.get();
            }
            return null;
        }

        private boolean hasQualfiers() {
            SelectionCriteria selectionCriteria = this.selectionCriteria;
            return selectionCriteria != null && selectionCriteria.hasQualfiers();
        }

        private void printQualifierMessage(SelectionCriteria selectionCriteria) {
            final Class<DeclaredType> cls = DeclaredType.class;
            Stream<TypeMirror> streamFilter = selectionCriteria.getQualifiers().stream().filter(new Predicate() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ResolvingAttempt$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return cls.isInstance((TypeMirror) obj);
                }
            });
            final Class<DeclaredType> cls2 = DeclaredType.class;
            List list = (List) streamFilter.map(new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ResolvingAttempt$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (DeclaredType) cls2.cast((TypeMirror) obj);
                }
            }).map(new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ResolvingAttempt$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((DeclaredType) obj).asElement();
                }
            }).map(new MappingProcessor$$ExternalSyntheticLambda1()).map(new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ResolvingAttempt$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((Name) obj).toString();
                }
            }).map(new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ResolvingAttempt$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MappingResolverImpl.ResolvingAttempt.lambda$printQualifierMessage$0((String) obj);
                }
            }).collect(Collectors.toList());
            List<String> qualifiedByNames = selectionCriteria.getQualifiedByNames();
            if (!list.isEmpty() && !qualifiedByNames.isEmpty()) {
                this.messager.printMessage(this.mappingMethod.getExecutable(), this.positionHint, Message.GENERAL_NO_QUALIFYING_METHOD_COMBINED, Strings.join(qualifiedByNames, MessageConstants.AND), Strings.join(list, MessageConstants.AND));
            } else if (!list.isEmpty()) {
                this.messager.printMessage(this.mappingMethod.getExecutable(), this.positionHint, Message.GENERAL_NO_QUALIFYING_METHOD_ANNOTATION, Strings.join(list, MessageConstants.AND));
            } else {
                this.messager.printMessage(this.mappingMethod.getExecutable(), this.positionHint, Message.GENERAL_NO_QUALIFYING_METHOD_NAMED, Strings.join(qualifiedByNames, MessageConstants.AND));
            }
        }

        static /* synthetic */ String lambda$printQualifierMessage$0(String str) {
            return "@" + str;
        }

        private boolean allowDirect(Type type, Type type2) {
            SelectionCriteria selectionCriteria = this.selectionCriteria;
            return (selectionCriteria != null && selectionCriteria.isAllowDirect()) || allowDirect(type) || allowDirect(type2);
        }

        private boolean allowDirect(Type type) {
            if (type.isPrimitive()) {
                return true;
            }
            if (type.isArrayType()) {
                return type.isJavaLangType() || type.getComponentType().isPrimitive();
            }
            if (type.isIterableOrStreamType()) {
                List<Type> typeParameters = type.getTypeParameters();
                return typeParameters.isEmpty() || allowDirect((Type) Collections.first(typeParameters));
            }
            if (type.isMapType()) {
                List<Type> typeParameters2 = type.getTypeParameters();
                if (typeParameters2.isEmpty()) {
                    return true;
                }
                return allowDirect(typeParameters2.get(0)) && allowDirect(typeParameters2.get(1));
            }
            return type.isJavaLangType();
        }

        private boolean allowConversion() {
            SelectionCriteria selectionCriteria = this.selectionCriteria;
            return selectionCriteria != null && selectionCriteria.isAllowConversion();
        }

        private boolean allowMappingMethod() {
            SelectionCriteria selectionCriteria = this.selectionCriteria;
            return selectionCriteria != null && selectionCriteria.isAllowMappingMethod();
        }

        private boolean allow2Steps() {
            SelectionCriteria selectionCriteria = this.selectionCriteria;
            return selectionCriteria != null && selectionCriteria.isAllow2Steps();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ConversionAssignment resolveViaConversion(Type type, Type type2) {
            ConversionProvider conversion = MappingResolverImpl.this.conversions.getConversion(type, type2);
            if (conversion == null) {
                return null;
            }
            DefaultConversionContext defaultConversionContext = new DefaultConversionContext(MappingResolverImpl.this.typeFactory, this.messager, type, type2, this.formattingParameters);
            Iterator<HelperMethod> it = conversion.getRequiredHelperMethods(defaultConversionContext).iterator();
            while (it.hasNext()) {
                MappingResolverImpl.this.usedSupportedMappings.add(new SupportingMappingMethod(it.next()));
            }
            if (conversion.to(defaultConversionContext) != null) {
                return new ConversionAssignment(type, type2, conversion.to(defaultConversionContext));
            }
            return null;
        }

        private boolean isCandidateForMapping(Method method) {
            return isCreateMethodForMapping(method) || isUpdateMethodForMapping(method);
        }

        private boolean isCreateMethodForMapping(Method method) {
            return method.getSourceParameters().size() == 1 && !method.getReturnType().isVoid() && method.getMappingTargetParameter() == null && !method.isLifecycleCallbackMethod();
        }

        private boolean isUpdateMethodForMapping(Method method) {
            return (method.getSourceParameters().size() != 1 || method.getMappingTargetParameter() == null || method.isLifecycleCallbackMethod()) ? false : true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public <T extends Method> List<SelectedMethod<T>> getBestMatch(List<T> list, Type type, Type type2) {
            return MappingResolverImpl.this.methodSelectors.getMatchingMethods(this.mappingMethod, list, java.util.Collections.singletonList(type), type2, this.selectionCriteria);
        }

        private <T extends Method> void reportErrorWhenAmbiguous(List<SelectedMethod<T>> list, Type type) {
            String sourceErrorMessagePart;
            if (list.size() > 1) {
                ForgedMethodHistory forgedMethodHistory = this.description;
                if (forgedMethodHistory != null) {
                    sourceErrorMessagePart = forgedMethodHistory.createSourcePropertyErrorMessage();
                } else {
                    sourceErrorMessagePart = this.sourceRHS.getSourceErrorMessagePart();
                }
                if (this.sourceRHS.getSourceErrorMessagePart() != null) {
                    this.messager.printMessage(this.mappingMethod.getExecutable(), this.positionHint, Message.GENERAL_AMBIGUOUS_MAPPING_METHOD, sourceErrorMessagePart, type.describe(), join(list));
                } else {
                    this.messager.printMessage(this.mappingMethod.getExecutable(), this.positionHint, Message.GENERAL_AMBIGUOUS_FACTORY_METHOD, type.describe(), join(list));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Assignment toMethodRef(SelectedMethod<Method> selectedMethod) {
            return MethodReference.forMapperReference(selectedMethod.getMethod(), MappingResolverImpl.this.findMapperReference(selectedMethod.getMethod()), selectedMethod.getParameterBindings());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Assignment toBuildInRef(SelectedMethod<BuiltInMethod> selectedMethod) {
            BuiltInMethod builtInMethod = (BuiltInMethod) selectedMethod.getMethod();
            HashSet hashSet = new HashSet(MappingResolverImpl.this.mapperReferences);
            SupportingField.addAllFieldsIn(this.supportingMethodCandidates, hashSet);
            this.supportingMethodCandidates.add(new SupportingMappingMethod(builtInMethod, hashSet));
            MethodReference methodReferenceForBuiltInMethod = MethodReference.forBuiltInMethod(builtInMethod, new DefaultConversionContext(MappingResolverImpl.this.typeFactory, this.messager, builtInMethod.getMappingSourceType(), builtInMethod.getResultType(), this.formattingParameters));
            methodReferenceForBuiltInMethod.setAssignment(this.sourceRHS);
            return methodReferenceForBuiltInMethod;
        }

        private boolean isAssignableThroughCollectionCopyConstructor(Type type, Type type2) {
            if (!((type.isCollectionType() && type2.isCollectionType()) || (type.isMapType() && type2.isMapType()))) {
                return false;
            }
            if (type2.getImplementationType() != null) {
                type2 = type2.getImplementationType();
            }
            return hasCompatibleCopyConstructor(type, type2);
        }

        private boolean hasCompatibleCopyConstructor(Type type, Type type2) {
            if (type2.isPrimitive()) {
                return false;
            }
            for (ExecutableElement executableElement : ElementFilter.constructorsIn(type2.getTypeElement().getEnclosedElements())) {
                if (executableElement.getParameters().size() == 1) {
                    DeclaredType declaredType = (TypeMirror) Collections.first(MappingResolverImpl.this.typeUtils.asMemberOf(type2.getTypeMirror(), executableElement).getParameterTypes());
                    if (declaredType.getKind() == TypeKind.DECLARED) {
                        DeclaredType declaredType2 = declaredType;
                        ArrayList arrayList = new ArrayList(declaredType2.getTypeArguments().size());
                        Iterator it = declaredType2.getTypeArguments().iterator();
                        while (it.hasNext()) {
                            arrayList.add(MappingResolverImpl.this.typeFactory.getTypeBound((TypeMirror) it.next()));
                        }
                        declaredType = MappingResolverImpl.this.typeUtils.getDeclaredType(declaredType2.asElement(), (TypeMirror[]) arrayList.toArray(new TypeMirror[arrayList.size()]));
                    }
                    if (MappingResolverImpl.this.typeUtils.isAssignable(type.getTypeMirror(), declaredType)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public <T extends Method> String join(List<SelectedMethod<T>> list) {
            String str = (String) list.stream().limit(this.reportingLimitAmbiguous).map(new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ResolvingAttempt$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((SelectedMethod) obj).getMethod().describe();
                }
            }).collect(Collectors.joining(", "));
            return list.size() > this.reportingLimitAmbiguous ? str + String.format("... and %s more", Integer.valueOf(list.size() - this.reportingLimitAmbiguous)) : str;
        }
    }

    private static class ConversionAssignment {
        private final Assignment assignment;
        private final Type sourceType;
        private final Type targetType;

        ConversionAssignment(Type type, Type type2, Assignment assignment) {
            this.sourceType = type;
            this.targetType = type2;
            this.assignment = assignment;
        }

        Assignment getAssignment() {
            return this.assignment;
        }

        void reportMessageWhenNarrowing(FormattingMessager formattingMessager, ResolvingAttempt resolvingAttempt) {
            if (NativeTypes.isNarrowing(this.sourceType.getFullyQualifiedName(), this.targetType.getFullyQualifiedName())) {
                ReportingPolicyGem reportingPolicyGemTypeConversionPolicy = resolvingAttempt.mappingMethod.getOptions().getMapper().typeConversionPolicy();
                if (reportingPolicyGemTypeConversionPolicy == ReportingPolicyGem.WARN) {
                    report(formattingMessager, resolvingAttempt, Message.CONVERSION_LOSSY_WARNING);
                } else if (reportingPolicyGemTypeConversionPolicy == ReportingPolicyGem.ERROR) {
                    report(formattingMessager, resolvingAttempt, Message.CONVERSION_LOSSY_ERROR);
                }
            }
        }

        private void report(FormattingMessager formattingMessager, ResolvingAttempt resolvingAttempt, Message message) {
            formattingMessager.printMessage(resolvingAttempt.mappingMethod.getExecutable(), resolvingAttempt.positionHint, message, resolvingAttempt.sourceRHS.getSourceErrorMessagePart(), this.sourceType.describe(), this.targetType.describe());
        }

        String shortName() {
            return this.sourceType.getName() + "-->" + this.targetType.getName();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ConversionAssignment conversionAssignment = (ConversionAssignment) obj;
            return this.sourceType.equals(conversionAssignment.sourceType) && this.targetType.equals(conversionAssignment.targetType);
        }

        public int hashCode() {
            return Objects.hash(this.sourceType, this.targetType);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MethodMethod<T1 extends Method, T2 extends Method> {
        private final ResolvingAttempt attempt;
        private boolean hasResult = false;
        private Assignment result = null;
        private final Function<SelectedMethod<T1>, Assignment> xCreate;
        private final List<T1> xMethods;
        private final Function<SelectedMethod<T2>, Assignment> yCreate;
        private final List<T2> yMethods;

        static Assignment getBestMatch(final ResolvingAttempt resolvingAttempt, Type type, Type type2) {
            List list = resolvingAttempt.methods;
            List list2 = resolvingAttempt.methods;
            resolvingAttempt.getClass();
            Function function = new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return resolvingAttempt.toMethodRef((SelectedMethod) obj);
                }
            };
            resolvingAttempt.getClass();
            MethodMethod<T1, T2> bestMatch = new MethodMethod(resolvingAttempt, list, list2, function, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return resolvingAttempt.toMethodRef((SelectedMethod) obj);
                }
            }).getBestMatch(type, type2);
            if (!((MethodMethod) bestMatch).hasResult) {
                List list3 = resolvingAttempt.methods;
                List list4 = resolvingAttempt.builtIns;
                resolvingAttempt.getClass();
                Function function2 = new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return resolvingAttempt.toMethodRef((SelectedMethod) obj);
                    }
                };
                resolvingAttempt.getClass();
                MethodMethod<T1, T2> bestMatch2 = new MethodMethod(resolvingAttempt, list3, list4, function2, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return resolvingAttempt.toBuildInRef((SelectedMethod) obj);
                    }
                }).getBestMatch(type, type2);
                if (!((MethodMethod) bestMatch2).hasResult) {
                    List list5 = resolvingAttempt.builtIns;
                    List list6 = resolvingAttempt.methods;
                    resolvingAttempt.getClass();
                    Function function3 = new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda5
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return resolvingAttempt.toBuildInRef((SelectedMethod) obj);
                        }
                    };
                    resolvingAttempt.getClass();
                    MethodMethod<T1, T2> bestMatch3 = new MethodMethod(resolvingAttempt, list5, list6, function3, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda6
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return resolvingAttempt.toMethodRef((SelectedMethod) obj);
                        }
                    }).getBestMatch(type, type2);
                    if (!((MethodMethod) bestMatch3).hasResult) {
                        List list7 = resolvingAttempt.builtIns;
                        List list8 = resolvingAttempt.builtIns;
                        resolvingAttempt.getClass();
                        Function function4 = new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda7
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return resolvingAttempt.toBuildInRef((SelectedMethod) obj);
                            }
                        };
                        resolvingAttempt.getClass();
                        return ((MethodMethod) new MethodMethod(resolvingAttempt, list7, list8, function4, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda8
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                return resolvingAttempt.toBuildInRef((SelectedMethod) obj);
                            }
                        }).getBestMatch(type, type2)).result;
                    }
                    return ((MethodMethod) bestMatch3).result;
                }
                return ((MethodMethod) bestMatch2).result;
            }
            return ((MethodMethod) bestMatch).result;
        }

        MethodMethod(ResolvingAttempt resolvingAttempt, List<T1> list, List<T2> list2, Function<SelectedMethod<T1>, Assignment> function, Function<SelectedMethod<T2>, Assignment> function2) {
            this.attempt = resolvingAttempt;
            this.xMethods = list;
            this.yMethods = list2;
            this.xCreate = function;
            this.yCreate = function2;
        }

        private MethodMethod<T1, T2> getBestMatch(Type type, Type type2) {
            HashSet hashSet = new HashSet();
            final LinkedHashMap linkedHashMap = new LinkedHashMap();
            final LinkedHashMap linkedHashMap2 = new LinkedHashMap();
            this.attempt.selectionCriteria.setPreferUpdateMapping(false);
            for (T2 t2 : this.yMethods) {
                final Type typeResolveTypeVarToType = t2.getMappingSourceType().resolveTypeVarToType(type2, t2.getResultType());
                Type resultType = t2.getResultType();
                if (typeResolveTypeVarToType != null && resultType.isRawAssignableTo(type2) && !MappingResolverImpl.JL_OBJECT_NAME.equals(typeResolveTypeVarToType.getFullyQualifiedName())) {
                    List bestMatch = this.attempt.getBestMatch(this.xMethods, type, typeResolveTypeVarToType);
                    if (!bestMatch.isEmpty()) {
                        bestMatch.stream().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda9
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                MappingResolverImpl.MethodMethod.lambda$getBestMatch$8(linkedHashMap, (SelectedMethod) obj);
                            }
                        });
                        bestMatch.stream().forEach(new Consumer() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda10
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                MappingResolverImpl.MethodMethod.lambda$getBestMatch$9(linkedHashMap2, typeResolveTypeVarToType, (SelectedMethod) obj);
                            }
                        });
                        hashSet.add(t2);
                    }
                }
            }
            this.attempt.selectionCriteria.setPreferUpdateMapping(true);
            ArrayList arrayList = new ArrayList(hashSet);
            Iterator<Map.Entry<SelectedMethod<T1>, List<SelectedMethod<T2>>>> it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<SelectedMethod<T1>, List<SelectedMethod<T2>>> next = it.next();
                next.getValue().addAll(this.attempt.getBestMatch(arrayList, (Type) linkedHashMap2.get(next.getKey()), type2));
                if (next.getValue().isEmpty()) {
                    it.remove();
                }
            }
            if (linkedHashMap.isEmpty()) {
                return this;
            }
            this.hasResult = true;
            if (linkedHashMap.size() == 1 && ((List) Collections.firstValue(linkedHashMap)).size() == 1) {
                Assignment assignment = (Assignment) this.yCreate.apply(Collections.first((Collection) Collections.firstValue(linkedHashMap)));
                Assignment assignment2 = (Assignment) this.xCreate.apply(Collections.firstKey(linkedHashMap));
                assignment.setAssignment(assignment2);
                assignment2.setAssignment(this.attempt.sourceRHS);
                this.result = assignment;
            } else {
                reportAmbiguousError(linkedHashMap, type2);
            }
            return this;
        }

        static /* synthetic */ void lambda$getBestMatch$8(Map map, SelectedMethod selectedMethod) {
        }

        static /* synthetic */ void lambda$getBestMatch$9(Map map, Type type, SelectedMethod selectedMethod) {
        }

        void reportAmbiguousError(Map<SelectedMethod<T1>, List<SelectedMethod<T2>>> map, Type type) {
            final StringBuilder sb = new StringBuilder();
            map.entrySet().stream().limit(this.attempt.reportingLimitAmbiguous).forEach(new Consumer() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodMethod$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2375x67218ea0(sb, (Map.Entry) obj);
                }
            });
            this.attempt.messager.printMessage(this.attempt.mappingMethod.getExecutable(), this.attempt.positionHint, Message.GENERAL_AMBIGUOUS_MAPPING_METHODY_METHODX, this.attempt.sourceRHS.getSourceType().getName() + StringUtils.SPACE + this.attempt.sourceRHS.getSourceParameterName(), type.getName(), sb.toString());
        }

        /* renamed from: lambda$reportAmbiguousError$10$org-mapstruct-ap-internal-processor-creation-MappingResolverImpl$MethodMethod, reason: not valid java name */
        /* synthetic */ void m2375x67218ea0(StringBuilder sb, Map.Entry entry) {
            sb.append("method(s)Y: ").append(this.attempt.join((List) entry.getValue())).append(", methodX: ").append(((SelectedMethod) entry.getKey()).getMethod().describe()).append("; ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ConversionMethod<T extends Method> {
        private final ResolvingAttempt attempt;
        private final Function<SelectedMethod<T>, Assignment> create;
        private final List<T> methods;
        private boolean hasResult = false;
        private Assignment result = null;

        static Assignment getBestMatch(final ResolvingAttempt resolvingAttempt, Type type, Type type2) {
            List list = resolvingAttempt.methods;
            resolvingAttempt.getClass();
            ConversionMethod<T> bestMatch = new ConversionMethod(resolvingAttempt, list, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ConversionMethod$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return resolvingAttempt.toMethodRef((SelectedMethod) obj);
                }
            }).getBestMatch(type, type2);
            if (!((ConversionMethod) bestMatch).hasResult) {
                List list2 = resolvingAttempt.builtIns;
                resolvingAttempt.getClass();
                return ((ConversionMethod) new ConversionMethod(resolvingAttempt, list2, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ConversionMethod$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return resolvingAttempt.toBuildInRef((SelectedMethod) obj);
                    }
                }).getBestMatch(type, type2)).result;
            }
            return ((ConversionMethod) bestMatch).result;
        }

        ConversionMethod(ResolvingAttempt resolvingAttempt, List<T> list, Function<SelectedMethod<T>, Assignment> function) {
            this.attempt = resolvingAttempt;
            this.methods = list;
            this.create = function;
        }

        private ConversionMethod<T> getBestMatch(Type type, Type type2) {
            ConversionAssignment conversionAssignmentResolveViaConversion;
            ArrayList arrayList = new ArrayList();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (T t : this.methods) {
                Type typeResolveTypeVarToType = t.getMappingSourceType().resolveTypeVarToType(type2, t.getResultType());
                Type resultType = t.getResultType();
                if (typeResolveTypeVarToType != null && resultType.isRawAssignableTo(type2) && !MappingResolverImpl.JL_OBJECT_NAME.equals(typeResolveTypeVarToType.getFullyQualifiedName()) && (conversionAssignmentResolveViaConversion = this.attempt.resolveViaConversion(type, typeResolveTypeVarToType)) != null) {
                    linkedHashMap.put(conversionAssignmentResolveViaConversion, new ArrayList());
                    arrayList.add(t);
                }
            }
            Iterator<Map.Entry<ConversionAssignment, List<SelectedMethod<T>>>> it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<ConversionAssignment, List<SelectedMethod<T>>> next = it.next();
                next.getValue().addAll(this.attempt.getBestMatch(arrayList, next.getKey().targetType, type2));
                if (next.getValue().isEmpty()) {
                    it.remove();
                }
            }
            if (linkedHashMap.isEmpty()) {
                return this;
            }
            this.hasResult = true;
            if (linkedHashMap.size() == 1 && ((List) Collections.firstValue(linkedHashMap)).size() == 1) {
                Assignment assignment = (Assignment) this.create.apply(Collections.first((Collection) Collections.firstValue(linkedHashMap)));
                ConversionAssignment conversionAssignment = (ConversionAssignment) Collections.firstKey(linkedHashMap);
                conversionAssignment.reportMessageWhenNarrowing(this.attempt.messager, this.attempt);
                assignment.setAssignment(conversionAssignment.assignment);
                conversionAssignment.assignment.setAssignment(this.attempt.sourceRHS);
                this.result = assignment;
            } else {
                reportAmbiguousError(linkedHashMap, type2);
            }
            return this;
        }

        void reportAmbiguousError(Map<ConversionAssignment, List<SelectedMethod<T>>> map, Type type) {
            final StringBuilder sb = new StringBuilder();
            map.entrySet().stream().limit(this.attempt.reportingLimitAmbiguous).forEach(new Consumer() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$ConversionMethod$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2373xecc718da(sb, (Map.Entry) obj);
                }
            });
            this.attempt.messager.printMessage(this.attempt.mappingMethod.getExecutable(), this.attempt.positionHint, Message.GENERAL_AMBIGUOUS_MAPPING_METHODY_CONVERSIONX, this.attempt.sourceRHS.getSourceType().getName() + StringUtils.SPACE + this.attempt.sourceRHS.getSourceParameterName(), type.getName(), sb.toString());
        }

        /* renamed from: lambda$reportAmbiguousError$2$org-mapstruct-ap-internal-processor-creation-MappingResolverImpl$ConversionMethod, reason: not valid java name */
        /* synthetic */ void m2373xecc718da(StringBuilder sb, Map.Entry entry) {
            sb.append("method(s)Y: ").append(this.attempt.join((List) entry.getValue())).append(", conversionX: ").append(((ConversionAssignment) entry.getKey()).shortName()).append("; ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class MethodConversion<T extends Method> {
        private final ResolvingAttempt attempt;
        private final Function<SelectedMethod<T>, Assignment> create;
        private final List<T> methods;
        private boolean hasResult = false;
        private Assignment result = null;

        static Assignment getBestMatch(final ResolvingAttempt resolvingAttempt, Type type, Type type2) {
            List list = resolvingAttempt.methods;
            resolvingAttempt.getClass();
            MethodConversion<T> bestMatch = new MethodConversion(resolvingAttempt, list, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodConversion$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return resolvingAttempt.toMethodRef((SelectedMethod) obj);
                }
            }).getBestMatch(type, type2);
            if (!((MethodConversion) bestMatch).hasResult) {
                List list2 = resolvingAttempt.builtIns;
                resolvingAttempt.getClass();
                return ((MethodConversion) new MethodConversion(resolvingAttempt, list2, new Function() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodConversion$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return resolvingAttempt.toBuildInRef((SelectedMethod) obj);
                    }
                }).getBestMatch(type, type2)).result;
            }
            return ((MethodConversion) bestMatch).result;
        }

        MethodConversion(ResolvingAttempt resolvingAttempt, List<T> list, Function<SelectedMethod<T>, Assignment> function) {
            this.attempt = resolvingAttempt;
            this.methods = list;
            this.create = function;
        }

        private MethodConversion<T> getBestMatch(Type type, Type type2) {
            ConversionAssignment conversionAssignmentResolveViaConversion;
            ArrayList arrayList = new ArrayList();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (T t : this.methods) {
                Type returnType = t.getReturnType();
                Type mappingSourceType = t.getMappingSourceType();
                Type typeResolveTypeVarToType = returnType.resolveTypeVarToType(type, mappingSourceType);
                if (typeResolveTypeVarToType != null && !t.isUpdateMethod() && type.isRawAssignableTo(mappingSourceType) && !MappingResolverImpl.JL_OBJECT_NAME.equals(typeResolveTypeVarToType.getFullyQualifiedName()) && (conversionAssignmentResolveViaConversion = this.attempt.resolveViaConversion(typeResolveTypeVarToType, type2)) != null) {
                    linkedHashMap.put(conversionAssignmentResolveViaConversion, new ArrayList());
                    arrayList.add(t);
                }
            }
            Iterator<Map.Entry<ConversionAssignment, List<SelectedMethod<T>>>> it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<ConversionAssignment, List<SelectedMethod<T>>> next = it.next();
                next.getValue().addAll(this.attempt.getBestMatch(arrayList, type, next.getKey().sourceType));
                if (next.getValue().isEmpty()) {
                    it.remove();
                }
            }
            if (linkedHashMap.isEmpty()) {
                return this;
            }
            this.hasResult = true;
            if (linkedHashMap.size() == 1 && ((List) Collections.firstValue(linkedHashMap)).size() == 1) {
                Assignment assignment = (Assignment) this.create.apply(Collections.first((Collection) Collections.firstValue(linkedHashMap)));
                ConversionAssignment conversionAssignment = (ConversionAssignment) Collections.firstKey(linkedHashMap);
                conversionAssignment.reportMessageWhenNarrowing(this.attempt.messager, this.attempt);
                assignment.setAssignment(this.attempt.sourceRHS);
                conversionAssignment.assignment.setAssignment(assignment);
                this.result = conversionAssignment.assignment;
            } else {
                reportAmbiguousError(linkedHashMap, type2);
            }
            return this;
        }

        void reportAmbiguousError(Map<ConversionAssignment, List<SelectedMethod<T>>> map, Type type) {
            final StringBuilder sb = new StringBuilder();
            map.entrySet().stream().limit(this.attempt.reportingLimitAmbiguous).forEach(new Consumer() { // from class: org.mapstruct.ap.internal.processor.creation.MappingResolverImpl$MethodConversion$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.m2374xb72fcc1a(sb, (Map.Entry) obj);
                }
            });
            this.attempt.messager.printMessage(this.attempt.mappingMethod.getExecutable(), this.attempt.positionHint, Message.GENERAL_AMBIGUOUS_MAPPING_CONVERSIONY_METHODX, this.attempt.sourceRHS.getSourceType().getName() + StringUtils.SPACE + this.attempt.sourceRHS.getSourceParameterName(), type.getName(), sb.toString());
        }

        /* renamed from: lambda$reportAmbiguousError$2$org-mapstruct-ap-internal-processor-creation-MappingResolverImpl$MethodConversion, reason: not valid java name */
        /* synthetic */ void m2374xb72fcc1a(StringBuilder sb, Map.Entry entry) {
            sb.append("conversionY: ").append(((ConversionAssignment) entry.getKey()).shortName()).append(", method(s)X: ").append(this.attempt.join((List) entry.getValue())).append("; ");
        }
    }
}
