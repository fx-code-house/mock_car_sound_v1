package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import javax.lang.model.element.AnnotationMirror;
import okhttp3.HttpUrl;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.IterableMappingMethod;
import org.mapstruct.ap.internal.model.MapMappingMethod;
import org.mapstruct.ap.internal.model.NestedPropertyMappingMethod;
import org.mapstruct.ap.internal.model.PropertyMapping;
import org.mapstruct.ap.internal.model.StreamMappingMethod;
import org.mapstruct.ap.internal.model.assignment.AdderWrapper;
import org.mapstruct.ap.internal.model.assignment.ArrayCopyWrapper;
import org.mapstruct.ap.internal.model.assignment.EnumConstantWrapper;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapper;
import org.mapstruct.ap.internal.model.assignment.StreamAdderWrapper;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.beanmapping.MappingReferences;
import org.mapstruct.ap.internal.model.beanmapping.PropertyEntry;
import org.mapstruct.ap.internal.model.beanmapping.SourceReference;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.DelegatingOptions;
import org.mapstruct.ap.internal.model.source.MappingControl;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.NativeTypes;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.ValueProvider;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;

/* loaded from: classes3.dex */
public class PropertyMapping extends ModelElement {
    private final Assignment assignment;
    private final boolean constructorMapping;
    private final Assignment defaultValueAssignment;
    private final Set<String> dependsOn;
    private final String name;
    private final String sourceBeanName;
    private final ValueProvider targetReadAccessorProvider;
    private final Type targetType;
    private final String targetWriteAccessorName;

    /* JADX INFO: Access modifiers changed from: private */
    static class MappingBuilderBase<T extends MappingBuilderBase<T>> extends AbstractBaseBuilder<T> {
        protected Set<String> dependsOn;
        protected Set<String> existingVariableNames;
        protected AnnotationMirror positionHint;
        protected String sourcePropertyName;
        protected BuilderType targetBuilderType;
        protected String targetPropertyName;
        protected Accessor targetReadAccessor;
        protected Type targetType;
        protected Accessor targetWriteAccessor;
        protected AccessorType targetWriteAccessorType;

        MappingBuilderBase(Class<T> cls) {
            super(cls);
        }

        public T sourceMethod(Method method) {
            return (T) super.method(method);
        }

        public T target(String str, Accessor accessor, Accessor accessor2) {
            this.targetPropertyName = str;
            this.targetReadAccessor = accessor;
            this.targetWriteAccessor = accessor2;
            this.targetType = this.ctx.getTypeFactory().getType(accessor2.getAccessedType());
            this.targetBuilderType = this.ctx.getTypeFactory().builderTypeFor(this.targetType, this.method.getOptions().getBeanMapping().getBuilder());
            this.targetWriteAccessorType = accessor2.getAccessorType();
            return this;
        }

        T mirror(AnnotationMirror annotationMirror) {
            this.positionHint = annotationMirror;
            return this;
        }

        public T sourcePropertyName(String str) {
            this.sourcePropertyName = str;
            return this;
        }

        public T dependsOn(Set<String> set) {
            this.dependsOn = set;
            return this;
        }

        public T existingVariableNames(Set<String> set) {
            this.existingVariableNames = set;
            return this;
        }

        protected boolean isFieldAssignment() {
            return this.targetWriteAccessorType.isFieldAssignment();
        }
    }

    public static class PropertyMappingBuilder extends MappingBuilderBase<PropertyMappingBuilder> {
        private String defaultJavaExpression;
        private String defaultValue;
        private boolean forceUpdateMethod;
        private MappingReferences forgeMethodWithMappingReferences;
        private boolean forgedNamedBased;
        private FormattingParameters formattingParameters;
        private MappingControl mappingControl;
        private NullValueCheckStrategyGem nvcs;
        private NullValuePropertyMappingStrategyGem nvpms;
        private SourceRHS rightHandSide;
        private SelectionParameters selectionParameters;
        private SourceReference sourceReference;

        PropertyMappingBuilder() {
            super(PropertyMappingBuilder.class);
            this.forgedNamedBased = true;
        }

        public PropertyMappingBuilder sourceReference(SourceReference sourceReference) {
            this.sourceReference = sourceReference;
            return this;
        }

        public PropertyMappingBuilder selectionParameters(SelectionParameters selectionParameters) {
            this.selectionParameters = selectionParameters;
            return this;
        }

        public PropertyMappingBuilder formattingParameters(FormattingParameters formattingParameters) {
            this.formattingParameters = formattingParameters;
            return this;
        }

        public PropertyMappingBuilder defaultValue(String str) {
            this.defaultValue = str;
            return this;
        }

        public PropertyMappingBuilder defaultJavaExpression(String str) {
            this.defaultJavaExpression = str;
            return this;
        }

        public PropertyMappingBuilder forgeMethodWithMappingReferences(MappingReferences mappingReferences) {
            this.forgeMethodWithMappingReferences = mappingReferences;
            return this;
        }

        public PropertyMappingBuilder forceUpdateMethod(boolean z) {
            this.forceUpdateMethod = z;
            return this;
        }

        public PropertyMappingBuilder forgedNamedBased(boolean z) {
            this.forgedNamedBased = z;
            return this;
        }

        public PropertyMappingBuilder options(DelegatingOptions delegatingOptions) {
            this.mappingControl = delegatingOptions.getMappingControl(this.ctx.getElementUtils());
            this.nvcs = delegatingOptions.getNullValueCheckStrategy();
            if (this.method.isUpdateMethod()) {
                this.nvpms = delegatingOptions.getNullValuePropertyMappingStrategy();
            }
            return this;
        }

        public PropertyMapping build() {
            Assignment assignmentForge;
            this.rightHandSide = getSourceRHS(this.sourceReference);
            this.ctx.getMessager().note(2, Message.PROPERTYMAPPING_MAPPING_NOTE, this.rightHandSide, this.targetWriteAccessor);
            this.rightHandSide.setUseElementAsSourceTypeForMatching(this.targetWriteAccessorType == AccessorType.ADDER);
            SelectionCriteria selectionCriteriaForMappingMethods = SelectionCriteria.forMappingMethods(this.selectionParameters, this.mappingControl, this.targetPropertyName, (this.targetWriteAccessorType == AccessorType.ADDER || this.method.getMappingTargetParameter() == null) ? false : true);
            if (this.forgeMethodWithMappingReferences == null) {
                assignmentForge = this.ctx.getMappingResolver().getTargetAssignment(this.method, getForgedMethodHistory(this.rightHandSide), this.targetType, this.formattingParameters, selectionCriteriaForMappingMethods, this.rightHandSide, this.positionHint, new Supplier() { // from class: org.mapstruct.ap.internal.model.PropertyMapping$PropertyMappingBuilder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.forge();
                    }
                });
            } else {
                assignmentForge = forge();
            }
            Type sourceType = this.rightHandSide.getSourceType();
            if (assignmentForge != null) {
                this.ctx.getMessager().note(2, Message.PROPERTYMAPPING_SELECT_NOTE, assignmentForge);
                if (this.targetType.isCollectionOrMapType()) {
                    assignmentForge = assignToCollection(this.targetType, this.targetWriteAccessorType, assignmentForge);
                } else if (this.targetType.isArrayType() && sourceType.isArrayType() && assignmentForge.getType() == Assignment.AssignmentType.DIRECT) {
                    assignmentForge = assignToArray(this.targetType, assignmentForge);
                } else {
                    assignmentForge = assignToPlain(this.targetType, this.targetWriteAccessorType, assignmentForge);
                }
            } else {
                reportCannotCreateMapping();
            }
            Assignment assignment = assignmentForge;
            return new PropertyMapping(this.targetPropertyName, this.rightHandSide.getSourceParameterName(), this.targetWriteAccessor.getSimpleName(), ValueProvider.of(this.targetReadAccessor), this.targetType, assignment, this.dependsOn, getDefaultValueAssignment(assignment), this.targetWriteAccessorType == AccessorType.PARAMETER);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Assignment forge() {
            Assignment assignmentForgeIterableMapping;
            Type sourceType = this.rightHandSide.getSourceType();
            if ((sourceType.isCollectionType() || sourceType.isArrayType()) && this.targetType.isIterableType()) {
                assignmentForgeIterableMapping = forgeIterableMapping(sourceType, this.targetType, this.rightHandSide);
            } else if (sourceType.isMapType() && this.targetType.isMapType()) {
                assignmentForgeIterableMapping = forgeMapMapping(sourceType, this.targetType, this.rightHandSide);
            } else if ((sourceType.isIterableType() && this.targetType.isStreamType()) || ((sourceType.isStreamType() && this.targetType.isStreamType()) || (sourceType.isStreamType() && this.targetType.isIterableType()))) {
                assignmentForgeIterableMapping = forgeStreamMapping(sourceType, this.targetType, this.rightHandSide);
            } else {
                assignmentForgeIterableMapping = forgeMapping(this.rightHandSide);
            }
            if (assignmentForgeIterableMapping != null) {
                this.ctx.getMessager().note(2, Message.PROPERTYMAPPING_CREATE_NOTE, assignmentForgeIterableMapping);
            }
            return assignmentForgeIterableMapping;
        }

        private void reportCannotCreateMapping() {
            if (this.forgeMethodWithMappingReferences == null || !this.ctx.isErroneous()) {
                if ((this.method instanceof ForgedMethod) && ((ForgedMethod) this.method).getHistory() != null) {
                    ForgedMethodHistory forgedMethodHistory = getForgedMethodHistory(this.rightHandSide);
                    reportCannotCreateMapping(this.method, this.positionHint, forgedMethodHistory.createSourcePropertyErrorMessage(), forgedMethodHistory.getSourceType(), forgedMethodHistory.getTargetType(), forgedMethodHistory.createTargetPropertyName());
                } else {
                    reportCannotCreateMapping(this.method, this.positionHint, this.rightHandSide.getSourceErrorMessagePart(), this.rightHandSide.getSourceType(), this.targetType, this.targetPropertyName);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Assignment getDefaultValueAssignment(Assignment assignment) {
            if (this.defaultValue != null && (!assignment.getSourceType().isPrimitive() || assignment.getSourcePresenceCheckerReference() != null)) {
                return ((ConstantMappingBuilder) new ConstantMappingBuilder().constantExpression(this.defaultValue).formattingParameters(this.formattingParameters).selectionParameters(this.selectionParameters).dependsOn(this.dependsOn).existingVariableNames(this.existingVariableNames).mappingContext(this.ctx)).sourceMethod(this.method).target(this.targetPropertyName, this.targetReadAccessor, this.targetWriteAccessor).build().getAssignment();
            }
            if (this.defaultJavaExpression == null) {
                return null;
            }
            if (assignment.getSourceType().isPrimitive() && assignment.getSourcePresenceCheckerReference() == null) {
                return null;
            }
            return ((JavaExpressionMappingBuilder) new JavaExpressionMappingBuilder().javaExpression(this.defaultJavaExpression).dependsOn(this.dependsOn).existingVariableNames(this.existingVariableNames).mappingContext(this.ctx)).sourceMethod(this.method).target(this.targetPropertyName, this.targetReadAccessor, this.targetWriteAccessor).build().getAssignment();
        }

        private boolean hasDefaultValueAssignment(Assignment assignment) {
            return ((this.defaultValue == null && this.defaultJavaExpression == null) || (assignment.getSourceType().isPrimitive() && assignment.getSourcePresenceCheckerReference() == null)) ? false : true;
        }

        private Assignment assignToPlain(Type type, AccessorType accessorType, Assignment assignment) {
            if (accessorType == AccessorType.SETTER || accessorType.isFieldAssignment()) {
                return assignToPlainViaSetter(type, assignment);
            }
            return assignToPlainViaAdder(assignment);
        }

        private Assignment assignToPlainViaSetter(Type type, Assignment assignment) {
            if (assignment.isCallingUpdateMethod()) {
                if (this.targetReadAccessor == null) {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), this.positionHint, Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE, this.targetPropertyName);
                }
                return new UpdateWrapper(assignment, this.method.getThrownTypes(), ObjectFactoryMethodResolver.getFactoryMethod(this.method, type, SelectionParameters.forSourceRHS(this.rightHandSide), this.ctx), isFieldAssignment(), type, !assignment.isSourceReferenceParameter(), this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_NULL && !type.isPrimitive(), this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT);
            }
            boolean z = SetterWrapper.doSourceNullCheck(assignment, this.nvcs, this.nvpms, type) || hasDefaultValueAssignment(assignment);
            if (!z) {
                assignment.setSourceLocalVarName(null);
            }
            return new SetterWrapper(assignment, this.method.getThrownTypes(), isFieldAssignment(), z, z && this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_NULL && !type.isPrimitive(), this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT);
        }

        private Assignment assignToPlainViaAdder(Assignment assignment) {
            String str = this.sourcePropertyName == null ? this.targetPropertyName : this.sourcePropertyName;
            if (assignment.getSourceType().isCollectionType()) {
                return new AdderWrapper(assignment, this.method.getThrownTypes(), isFieldAssignment(), str);
            }
            if (assignment.getSourceType().isStreamType()) {
                return new StreamAdderWrapper(assignment, this.method.getThrownTypes(), isFieldAssignment(), str);
            }
            return new SetterWrapper(assignment, this.method.getThrownTypes(), isFieldAssignment(), true, this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_NULL && !this.targetType.isPrimitive(), this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT);
        }

        private Assignment assignToCollection(Type type, AccessorType accessorType, Assignment assignment) {
            return new CollectionAssignmentBuilder().mappingBuilderContext(this.ctx).method(this.method).targetReadAccessor(this.targetReadAccessor).targetType(type).targetPropertyName(this.targetPropertyName).targetAccessorType(accessorType).rightHandSide(this.rightHandSide).assignment(assignment).nullValueCheckStrategy(this.nvcs).nullValuePropertyMappingStrategy(this.nvpms).build();
        }

        private Assignment assignToArray(Type type, Assignment assignment) {
            return new ArrayCopyWrapper(assignment, this.targetPropertyName, this.ctx.getTypeFactory().getType(Arrays.class), type, isFieldAssignment(), this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_NULL && !type.isPrimitive(), this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT);
        }

        private SourceRHS getSourceRHS(SourceReference sourceReference) {
            Parameter parameter = sourceReference.getParameter();
            PropertyEntry deepestProperty = sourceReference.getDeepestProperty();
            if (deepestProperty == null) {
                return new SourceRHS(parameter.getName(), parameter.getType(), this.existingVariableNames, sourceReference.toString());
            }
            if (!sourceReference.isNested()) {
                return new SourceRHS(parameter.getName(), parameter.getName() + "." + ValueProvider.of(deepestProperty.getReadAccessor()), getSourcePresenceCheckerRef(sourceReference), deepestProperty.getType(), this.existingVariableNames, sourceReference.toString());
            }
            Type type = deepestProperty.getType();
            if (type.isPrimitive() && !this.targetType.isPrimitive()) {
                type = this.ctx.getTypeFactory().getWrappedType(type);
            }
            Type type2 = type;
            String safeVariableName = Strings.getSafeVariableName(Strings.joinAndCamelize(sourceReference.getElementNames()), this.ctx.getReservedNames());
            NestedPropertyMappingMethod nestedPropertyMappingMethodBuild = new NestedPropertyMappingMethod.Builder().method(ForgedMethod.forParameterMapping(safeVariableName, sourceReference.getParameter().getType(), type2, this.method)).propertyEntries(sourceReference.getPropertyEntries()).mappingContext(this.ctx).build();
            if (!this.ctx.getMappingsToGenerate().contains(nestedPropertyMappingMethodBuild)) {
                this.ctx.getMappingsToGenerate().add(nestedPropertyMappingMethodBuild);
            } else {
                safeVariableName = this.ctx.getExistingMappingMethod(nestedPropertyMappingMethodBuild).getName();
            }
            SourceRHS sourceRHS = new SourceRHS(parameter.getName(), safeVariableName + "( " + parameter.getName() + " )", getSourcePresenceCheckerRef(sourceReference), type2, this.existingVariableNames, sourceReference.toString());
            sourceRHS.setSourceLocalVarName(sourceRHS.createUniqueVarName(deepestProperty.getName()));
            return sourceRHS;
        }

        private String getSourcePresenceCheckerRef(SourceReference sourceReference) {
            if (!sourceReference.getPropertyEntries().isEmpty()) {
                Parameter parameter = sourceReference.getParameter();
                PropertyEntry shallowestProperty = sourceReference.getShallowestProperty();
                if (shallowestProperty.getPresenceChecker() != null) {
                    String str = parameter.getName() + "." + shallowestProperty.getPresenceChecker().getSimpleName() + "()";
                    String str2 = parameter.getName() + "." + shallowestProperty.getReadAccessor().getSimpleName() + "()";
                    for (int i = 1; i < sourceReference.getPropertyEntries().size(); i++) {
                        PropertyEntry propertyEntry = sourceReference.getPropertyEntries().get(i);
                        if (propertyEntry.getPresenceChecker() == null || propertyEntry.getReadAccessor() == null) {
                            return str;
                        }
                        str = str + " && " + str2 + " != null && " + str2 + "." + propertyEntry.getPresenceChecker().getSimpleName() + "()";
                        str2 = str2 + "." + propertyEntry.getReadAccessor().getSimpleName() + "()";
                    }
                    return str;
                }
            }
            return null;
        }

        private Assignment forgeStreamMapping(Type type, Type type2, SourceRHS sourceRHS) {
            return forgeWithElementMapping(type, type2, sourceRHS, new StreamMappingMethod.Builder());
        }

        private Assignment forgeIterableMapping(Type type, Type type2, SourceRHS sourceRHS) {
            return forgeWithElementMapping(type, type2, sourceRHS, new IterableMappingMethod.Builder());
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Assignment forgeWithElementMapping(Type type, Type type2, SourceRHS sourceRHS, ContainerMappingMethodBuilder<?, ? extends ContainerMappingMethod> containerMappingMethodBuilder) {
            ForgedMethod forgedMethodPrepareForgedMethod = prepareForgedMethod(type, type2.withoutBounds(), sourceRHS, HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return createForgedAssignment(sourceRHS, forgedMethodPrepareForgedMethod, ((ContainerMappingMethodBuilder) ((ContainerMappingMethodBuilder) containerMappingMethodBuilder.mappingContext(this.ctx)).method(forgedMethodPrepareForgedMethod)).selectionParameters(this.selectionParameters).callingContextTargetPropertyName(this.targetPropertyName).positionHint(this.positionHint).build());
        }

        private ForgedMethod prepareForgedMethod(Type type, Type type2, SourceRHS sourceRHS, String str) {
            return ForgedMethod.forElementMapping(Strings.getSafeVariableName(getName(type, type2), this.ctx.getReservedNames()), type, type2, this.method, getForgedMethodHistory(sourceRHS, str), this.forgedNamedBased);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private Assignment forgeMapMapping(Type type, Type type2, SourceRHS sourceRHS) {
            ForgedMethod forgedMethodPrepareForgedMethod = prepareForgedMethod(type, type2.withoutBounds(), sourceRHS, "{}");
            return createForgedAssignment(sourceRHS, forgedMethodPrepareForgedMethod, ((MapMappingMethod.Builder) ((MapMappingMethod.Builder) new MapMappingMethod.Builder().mappingContext(this.ctx)).method(forgedMethodPrepareForgedMethod)).build());
        }

        private Assignment forgeMapping(SourceRHS sourceRHS) {
            Type sourceType;
            Type typeCreateVoidType;
            if (this.targetWriteAccessorType == AccessorType.ADDER) {
                sourceType = sourceRHS.getSourceTypeForMatching();
            } else {
                sourceType = sourceRHS.getSourceType();
            }
            Type type = sourceType;
            if ((this.forgedNamedBased && !canGenerateAutoSubMappingBetween(type, this.targetType)) || type.isPrimitive() || this.targetType.isPrimitive()) {
                return null;
            }
            String safeVariableName = Strings.getSafeVariableName(getName(type, this.targetType), this.ctx.getReservedNames());
            ArrayList arrayList = new ArrayList(this.method.getContextParameters());
            if (!this.targetType.isEnumType() && ((this.method.isUpdateMethod() || this.forceUpdateMethod) && this.targetWriteAccessorType != AccessorType.ADDER)) {
                arrayList.add(Parameter.forForgedMappingTarget(this.targetType));
                typeCreateVoidType = this.ctx.getTypeFactory().createVoidType();
            } else {
                typeCreateVoidType = this.targetType;
            }
            return createForgedAssignment(sourceRHS, this.targetBuilderType, ForgedMethod.forPropertyMapping(safeVariableName, type, typeCreateVoidType, arrayList, this.method, getForgedMethodHistory(sourceRHS), this.forgeMethodWithMappingReferences, this.forgedNamedBased));
        }

        private ForgedMethodHistory getForgedMethodHistory(SourceRHS sourceRHS) {
            return getForgedMethodHistory(sourceRHS, "");
        }

        private ForgedMethodHistory getForgedMethodHistory(SourceRHS sourceRHS, String str) {
            return new ForgedMethodHistory(this.method instanceof ForgedMethod ? ((ForgedMethod) this.method).getHistory() : null, getSourceElementName() + str, this.targetPropertyName + str, sourceRHS.getSourceType(), this.targetType, true, "property");
        }

        private String getName(Type type, Type type2) {
            return Strings.decapitalize(getName(type) + "To" + getName(type2));
        }

        private String getName(Type type) {
            StringBuilder sb = new StringBuilder();
            Iterator<Type> it = type.getTypeParameters().iterator();
            while (it.hasNext()) {
                sb.append(it.next().getIdentification());
            }
            sb.append(type.getIdentification());
            return sb.toString();
        }

        private String getSourceElementName() {
            Parameter parameter = this.sourceReference.getParameter();
            List<PropertyEntry> propertyEntries = this.sourceReference.getPropertyEntries();
            if (propertyEntries.isEmpty()) {
                return parameter.getName();
            }
            if (propertyEntries.size() == 1) {
                return propertyEntries.get(0).getName();
            }
            return Strings.join(this.sourceReference.getElementNames(), ".");
        }
    }

    public static class ConstantMappingBuilder extends MappingBuilderBase<ConstantMappingBuilder> {
        private String constantExpression;
        private FormattingParameters formattingParameters;
        private MappingControl mappingControl;
        private SelectionParameters selectionParameters;

        static /* synthetic */ Assignment lambda$build$0() {
            return null;
        }

        ConstantMappingBuilder() {
            super(ConstantMappingBuilder.class);
        }

        public ConstantMappingBuilder constantExpression(String str) {
            this.constantExpression = str;
            return this;
        }

        public ConstantMappingBuilder formattingParameters(FormattingParameters formattingParameters) {
            this.formattingParameters = formattingParameters;
            return this;
        }

        public ConstantMappingBuilder selectionParameters(SelectionParameters selectionParameters) {
            this.selectionParameters = selectionParameters;
            return this;
        }

        public ConstantMappingBuilder options(MappingOptions mappingOptions) {
            this.mappingControl = mappingOptions.getMappingControl(this.ctx.getElementUtils());
            return this;
        }

        public PropertyMapping build() {
            String message;
            Class<?> literal;
            Assignment enumAssignment;
            Assignment assignment;
            Assignment setterWrapper;
            String str = "constant '" + this.constantExpression + "'";
            try {
                literal = NativeTypes.getLiteral(this.targetType.getFullyQualifiedName(), this.constantExpression);
                message = null;
            } catch (IllegalArgumentException e) {
                message = e.getMessage();
                literal = null;
            }
            if (literal == null) {
                this.constantExpression = "\"" + this.constantExpression + "\"";
                literal = String.class;
            }
            Type typeForLiteral = this.ctx.getTypeFactory().getTypeForLiteral(literal);
            SelectionCriteria selectionCriteriaForMappingMethods = SelectionCriteria.forMappingMethods(this.selectionParameters, this.mappingControl, this.targetPropertyName, this.method.getMappingTargetParameter() != null);
            if (!this.targetType.isEnumType()) {
                enumAssignment = this.ctx.getMappingResolver().getTargetAssignment(this.method, null, this.targetType, this.formattingParameters, selectionCriteriaForMappingMethods, new SourceRHS(this.constantExpression, typeForLiteral, this.existingVariableNames, str), this.positionHint, new Supplier() { // from class: org.mapstruct.ap.internal.model.PropertyMapping$ConstantMappingBuilder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return PropertyMapping.ConstantMappingBuilder.lambda$build$0();
                    }
                });
            } else {
                enumAssignment = getEnumAssignment();
            }
            Assignment assignment2 = enumAssignment;
            if (assignment2 != null) {
                if (this.targetWriteAccessor.getAccessorType() == AccessorType.SETTER || this.targetWriteAccessor.getAccessorType().isFieldAssignment()) {
                    if (assignment2.isCallingUpdateMethod()) {
                        if (this.targetReadAccessor == null) {
                            this.ctx.getMessager().printMessage(this.method.getExecutable(), this.positionHint, Message.CONSTANTMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE, this.targetPropertyName);
                        }
                        setterWrapper = new UpdateWrapper(assignment2, this.method.getThrownTypes(), ObjectFactoryMethodResolver.getFactoryMethod(this.method, this.targetType, null, this.ctx), isFieldAssignment(), this.targetType, false, false, false);
                    } else {
                        setterWrapper = new SetterWrapper(assignment2, this.method.getThrownTypes(), isFieldAssignment());
                    }
                } else {
                    setterWrapper = new GetterWrapperForCollectionsAndMaps(assignment2, this.method.getThrownTypes(), this.targetType, isFieldAssignment());
                }
                assignment = setterWrapper;
            } else {
                if (message == null) {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), this.positionHint, Message.CONSTANTMAPPING_MAPPING_NOT_FOUND, this.constantExpression, this.targetType.describe(), this.targetPropertyName);
                } else {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), this.positionHint, Message.CONSTANTMAPPING_MAPPING_NOT_FOUND_WITH_DETAILS, this.constantExpression, this.targetType.describe(), this.targetPropertyName, message);
                }
                assignment = assignment2;
            }
            return new PropertyMapping(this.targetPropertyName, this.targetWriteAccessor.getSimpleName(), ValueProvider.of(this.targetReadAccessor), this.targetType, assignment, this.dependsOn, (Assignment) null, this.targetWriteAccessorType == AccessorType.PARAMETER);
        }

        private Assignment getEnumAssignment() {
            String str = this.constantExpression;
            String strSubstring = str.substring(1, str.length() - 1);
            if (this.targetType.getEnumConstants().contains(strSubstring)) {
                return new EnumConstantWrapper(new SourceRHS(strSubstring, this.targetType, this.existingVariableNames, "constant '" + this.constantExpression + "'"), this.targetType);
            }
            this.ctx.getMessager().printMessage(this.method.getExecutable(), this.positionHint, Message.CONSTANTMAPPING_NON_EXISTING_CONSTANT, this.constantExpression, this.targetType.describe(), this.targetPropertyName);
            return null;
        }
    }

    public static class JavaExpressionMappingBuilder extends MappingBuilderBase<JavaExpressionMappingBuilder> {
        private String javaExpression;

        JavaExpressionMappingBuilder() {
            super(JavaExpressionMappingBuilder.class);
        }

        public JavaExpressionMappingBuilder javaExpression(String str) {
            this.javaExpression = str;
            return this;
        }

        public PropertyMapping build() {
            Assignment setterWrapper;
            SourceRHS sourceRHS = new SourceRHS(this.javaExpression, null, this.existingVariableNames, "");
            if (this.targetWriteAccessor.getAccessorType() == AccessorType.SETTER || this.targetWriteAccessor.getAccessorType().isFieldAssignment()) {
                setterWrapper = new SetterWrapper(sourceRHS, this.method.getThrownTypes(), isFieldAssignment());
            } else {
                setterWrapper = new GetterWrapperForCollectionsAndMaps(sourceRHS, this.method.getThrownTypes(), this.targetType, isFieldAssignment());
            }
            return new PropertyMapping(this.targetPropertyName, this.targetWriteAccessor.getSimpleName(), ValueProvider.of(this.targetReadAccessor), this.targetType, setterWrapper, this.dependsOn, (Assignment) null, this.targetWriteAccessorType == AccessorType.PARAMETER);
        }
    }

    private PropertyMapping(String str, String str2, ValueProvider valueProvider, Type type, Assignment assignment, Set<String> set, Assignment assignment2, boolean z) {
        this(str, (String) null, str2, valueProvider, type, assignment, set, assignment2, z);
    }

    private PropertyMapping(String str, String str2, String str3, ValueProvider valueProvider, Type type, Assignment assignment, Set<String> set, Assignment assignment2, boolean z) {
        this.name = str;
        this.sourceBeanName = str2;
        this.targetWriteAccessorName = str3;
        this.targetReadAccessorProvider = valueProvider;
        this.targetType = type;
        this.assignment = assignment;
        this.dependsOn = set == null ? Collections.emptySet() : set;
        this.defaultValueAssignment = assignment2;
        this.constructorMapping = z;
    }

    public String getName() {
        return this.name;
    }

    public String getSourceBeanName() {
        return this.sourceBeanName;
    }

    public String getTargetWriteAccessorName() {
        return this.targetWriteAccessorName;
    }

    public String getTargetReadAccessorName() {
        ValueProvider valueProvider = this.targetReadAccessorProvider;
        if (valueProvider == null) {
            return null;
        }
        return valueProvider.getValue();
    }

    public Type getTargetType() {
        return this.targetType;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    public Assignment getDefaultValueAssignment() {
        return this.defaultValueAssignment;
    }

    public boolean isConstructorMapping() {
        return this.constructorMapping;
    }

    @Override // org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        if (this.defaultValueAssignment == null) {
            return this.assignment.getImportTypes();
        }
        return org.mapstruct.ap.internal.util.Collections.asSet((Collection) this.assignment.getImportTypes(), this.defaultValueAssignment.getImportTypes());
    }

    public Set<String> getDependsOn() {
        return this.dependsOn;
    }

    public int hashCode() {
        String str = this.name;
        int iHashCode = (335 + (str != null ? str.hashCode() : 0)) * 67;
        Type type = this.targetType;
        return iHashCode + (type != null ? type.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PropertyMapping propertyMapping = (PropertyMapping) obj;
        return Objects.equals(this.name, propertyMapping.name) && Objects.equals(this.targetType, propertyMapping.targetType);
    }

    public String toString() {
        return "PropertyMapping {\n    name='" + this.name + "',\n    targetWriteAccessorName='" + this.targetWriteAccessorName + "',\n    targetReadAccessorName='" + getTargetReadAccessorName() + "',\n    targetType=" + this.targetType + ",\n    propertyAssignment=" + this.assignment + ",\n    defaultValueAssignment=" + this.defaultValueAssignment + ",\n    dependsOn=" + this.dependsOn + "\n}";
    }
}
