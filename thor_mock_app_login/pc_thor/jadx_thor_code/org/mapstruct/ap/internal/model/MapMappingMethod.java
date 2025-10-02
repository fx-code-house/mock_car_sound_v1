package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import org.mapstruct.ap.internal.model.assignment.LocalVarWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.FormattingParameters;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

/* loaded from: classes3.dex */
public class MapMappingMethod extends NormalTypeMappingMethod {
    private IterableCreation iterableCreation;
    private final Assignment keyAssignment;
    private final Assignment valueAssignment;

    public static class Builder extends AbstractMappingMethodBuilder<Builder, MapMappingMethod> {
        private FormattingParameters keyFormattingParameters;
        private SelectionParameters keySelectionParameters;
        private FormattingParameters valueFormattingParameters;
        private SelectionParameters valueSelectionParameters;

        @Override // org.mapstruct.ap.internal.model.AbstractMappingMethodBuilder
        protected boolean shouldUsePropertyNamesInHistory() {
            return true;
        }

        public Builder() {
            super(Builder.class);
        }

        public Builder keySelectionParameters(SelectionParameters selectionParameters) {
            this.keySelectionParameters = selectionParameters;
            return this;
        }

        public Builder valueSelectionParameters(SelectionParameters selectionParameters) {
            this.valueSelectionParameters = selectionParameters;
            return this;
        }

        public Builder keyFormattingParameters(FormattingParameters formattingParameters) {
            this.keyFormattingParameters = formattingParameters;
            return this;
        }

        public Builder valueFormattingParameters(FormattingParameters formattingParameters) {
            this.valueFormattingParameters = formattingParameters;
            return this;
        }

        /* JADX WARN: Type inference failed for: r14v1 */
        /* JADX WARN: Type inference failed for: r14v2, types: [java.lang.String, org.mapstruct.ap.internal.model.MapMappingMethod, org.mapstruct.ap.internal.model.source.SelectionParameters] */
        /* JADX WARN: Type inference failed for: r14v4 */
        @Override // org.mapstruct.ap.internal.model.AbstractMappingMethodBuilder
        public MapMappingMethod build() {
            ?? r14;
            Type type;
            List<Type> listDetermineTypeArguments = ((Parameter) Collections.first(this.method.getSourceParameters())).getType().determineTypeArguments(Map.class);
            List<Type> listDetermineTypeArguments2 = this.method.getResultType().determineTypeArguments(Map.class);
            final Type typeBound = listDetermineTypeArguments.get(0).getTypeBound();
            final Type typeBound2 = listDetermineTypeArguments2.get(0).getTypeBound();
            final SourceRHS sourceRHS = new SourceRHS("entry.getKey()", typeBound, new HashSet(), "map key");
            Assignment targetAssignment = this.ctx.getMappingResolver().getTargetAssignment(this.method, getDescription(), typeBound2, this.keyFormattingParameters, SelectionCriteria.forMappingMethods(this.keySelectionParameters, this.method.getOptions().getMapMapping().getKeyMappingControl(this.ctx.getElementUtils()), null, false), sourceRHS, null, new Supplier() { // from class: org.mapstruct.ap.internal.model.MapMappingMethod$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m2367xfb0a686c(sourceRHS, typeBound, typeBound2);
                }
            });
            if (targetAssignment == null) {
                if (this.method instanceof ForgedMethod) {
                    return null;
                }
                r14 = 0;
                type = typeBound2;
                reportCannotCreateMapping(this.method, String.format("%s \"%s\"", sourceRHS.getSourceErrorMessagePart(), sourceRHS.getSourceType().describe()), sourceRHS.getSourceType(), typeBound2, "");
            } else {
                r14 = 0;
                type = typeBound2;
                this.ctx.getMessager().note(2, Message.MAPMAPPING_SELECT_KEY_NOTE, targetAssignment);
            }
            final Type typeBound3 = listDetermineTypeArguments.get(1).getTypeBound();
            final Type typeBound4 = listDetermineTypeArguments2.get(1).getTypeBound();
            final SourceRHS sourceRHS2 = new SourceRHS("entry.getValue()", typeBound3, new HashSet(), "map value");
            Assignment targetAssignment2 = this.ctx.getMappingResolver().getTargetAssignment(this.method, getDescription(), typeBound4, this.valueFormattingParameters, SelectionCriteria.forMappingMethods(this.valueSelectionParameters, this.method.getOptions().getMapMapping().getValueMappingControl(this.ctx.getElementUtils()), r14, false), sourceRHS2, null, new Supplier() { // from class: org.mapstruct.ap.internal.model.MapMappingMethod$Builder$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m2368x87f77f8b(sourceRHS2, typeBound3, typeBound4);
                }
            });
            if (this.method instanceof ForgedMethod) {
                ForgedMethod forgedMethod = (ForgedMethod) this.method;
                if (targetAssignment != null) {
                    forgedMethod.addThrownTypes(targetAssignment.getThrownTypes());
                }
                if (targetAssignment2 != null) {
                    forgedMethod.addThrownTypes(targetAssignment2.getThrownTypes());
                }
            }
            if (targetAssignment2 == null) {
                if (this.method instanceof ForgedMethod) {
                    return r14;
                }
                reportCannotCreateMapping(this.method, String.format("%s \"%s\"", sourceRHS2.getSourceErrorMessagePart(), sourceRHS2.getSourceType().describe()), sourceRHS2.getSourceType(), typeBound4, "");
            } else {
                this.ctx.getMessager().note(2, Message.MAPMAPPING_SELECT_VALUE_NOTE, targetAssignment2);
            }
            boolean zIsReturnDefault = this.method.getOptions().getMapMapping().getNullValueMappingStrategy().isReturnDefault();
            MethodReference factoryMethod = !this.method.isUpdateMethod() ? ObjectFactoryMethodResolver.getFactoryMethod(this.method, r14, this.ctx) : r14;
            LocalVarWrapper localVarWrapper = new LocalVarWrapper(targetAssignment, this.method.getThrownTypes(), type, false);
            LocalVarWrapper localVarWrapper2 = new LocalVarWrapper(targetAssignment2, this.method.getThrownTypes(), typeBound4, false);
            HashSet hashSet = new HashSet(this.method.getParameterNames());
            return new MapMappingMethod(this.method, hashSet, localVarWrapper, localVarWrapper2, factoryMethod, zIsReturnDefault, LifecycleMethodResolver.beforeMappingMethods(this.method, r14, this.ctx, hashSet), LifecycleMethodResolver.afterMappingMethods(this.method, r14, this.ctx, hashSet));
        }

        /* renamed from: lambda$build$0$org-mapstruct-ap-internal-model-MapMappingMethod$Builder, reason: not valid java name */
        /* synthetic */ Assignment m2367xfb0a686c(SourceRHS sourceRHS, Type type, Type type2) {
            return forge(sourceRHS, type, type2, Message.MAPMAPPING_CREATE_KEY_NOTE);
        }

        /* renamed from: lambda$build$1$org-mapstruct-ap-internal-model-MapMappingMethod$Builder, reason: not valid java name */
        /* synthetic */ Assignment m2368x87f77f8b(SourceRHS sourceRHS, Type type, Type type2) {
            return forge(sourceRHS, type, type2, Message.MAPMAPPING_CREATE_VALUE_NOTE);
        }

        Assignment forge(SourceRHS sourceRHS, Type type, Type type2, Message message) {
            Assignment assignmentForgeMapping = forgeMapping(sourceRHS, type, type2);
            if (assignmentForgeMapping != null) {
                this.ctx.getMessager().note(2, message, assignmentForgeMapping);
            }
            return assignmentForgeMapping;
        }
    }

    private MapMappingMethod(Method method, Collection<String> collection, Assignment assignment, Assignment assignment2, MethodReference methodReference, boolean z, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2) {
        super(method, collection, methodReference, z, list, list2);
        this.keyAssignment = assignment;
        this.valueAssignment = assignment2;
    }

    public Parameter getSourceParameter() {
        for (Parameter parameter : getParameters()) {
            if (!parameter.isMappingTarget() && !parameter.isMappingContext()) {
                return parameter;
            }
        }
        throw new IllegalStateException("Method " + this + " has no source parameter.");
    }

    public List<Type> getSourceElementTypes() {
        return getSourceParameter().getType().determineTypeArguments(Map.class);
    }

    public List<Type> getResultElementTypes() {
        return getResultType().determineTypeArguments(Map.class);
    }

    public Assignment getKeyAssignment() {
        return this.keyAssignment;
    }

    public Assignment getValueAssignment() {
        return this.valueAssignment;
    }

    @Override // org.mapstruct.ap.internal.model.NormalTypeMappingMethod, org.mapstruct.ap.internal.model.MappingMethod, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        Set<Type> importTypes = super.getImportTypes();
        Assignment assignment = this.keyAssignment;
        if (assignment != null) {
            importTypes.addAll(assignment.getImportTypes());
        }
        Assignment assignment2 = this.valueAssignment;
        if (assignment2 != null) {
            importTypes.addAll(assignment2.getImportTypes());
        }
        IterableCreation iterableCreation = this.iterableCreation;
        if (iterableCreation != null) {
            importTypes.addAll(iterableCreation.getImportTypes());
        }
        return importTypes;
    }

    public String getKeyVariableName() {
        return Strings.getSafeVariableName("key", getParameterNames());
    }

    public String getValueVariableName() {
        return Strings.getSafeVariableName("value", getParameterNames());
    }

    public String getEntryVariableName() {
        return Strings.getSafeVariableName("entry", getParameterNames());
    }

    public IterableCreation getIterableCreation() {
        if (this.iterableCreation == null) {
            this.iterableCreation = IterableCreation.create(this, getSourceParameter());
        }
        return this.iterableCreation;
    }
}
