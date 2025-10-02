package org.mapstruct.ap.internal.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import javax.lang.model.element.AnnotationMirror;
import org.mapstruct.ap.internal.model.ContainerMappingMethod;
import org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder;
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
public abstract class ContainerMappingMethodBuilder<B extends ContainerMappingMethodBuilder<B, M>, M extends ContainerMappingMethod> extends AbstractMappingMethodBuilder<B, M> {
    private String callingContextTargetPropertyName;
    private String errorMessagePart;
    private FormattingParameters formattingParameters;
    private AnnotationMirror positionHint;
    private SelectionParameters selectionParameters;

    protected abstract Type getElementType(Type type);

    protected abstract Assignment getWrapper(Assignment assignment, Method method);

    protected abstract M instantiateMappingMethod(Method method, Collection<String> collection, Assignment assignment, MethodReference methodReference, boolean z, String str, List<LifecycleCallbackMethodReference> list, List<LifecycleCallbackMethodReference> list2, SelectionParameters selectionParameters);

    @Override // org.mapstruct.ap.internal.model.AbstractMappingMethodBuilder
    protected boolean shouldUsePropertyNamesInHistory() {
        return false;
    }

    ContainerMappingMethodBuilder(Class<B> cls, String str) {
        super(cls);
        this.errorMessagePart = str;
    }

    public B formattingParameters(FormattingParameters formattingParameters) {
        this.formattingParameters = formattingParameters;
        return (B) this.myself;
    }

    public B selectionParameters(SelectionParameters selectionParameters) {
        this.selectionParameters = selectionParameters;
        return (B) this.myself;
    }

    public B callingContextTargetPropertyName(String str) {
        this.callingContextTargetPropertyName = str;
        return (B) this.myself;
    }

    public B positionHint(AnnotationMirror annotationMirror) {
        this.positionHint = annotationMirror;
        return (B) this.myself;
    }

    @Override // org.mapstruct.ap.internal.model.AbstractMappingMethodBuilder
    public final M build() {
        Type type = ((Parameter) Collections.first(this.method.getSourceParameters())).getType();
        Type resultType = this.method.getResultType();
        final Type elementType = getElementType(type);
        final Type elementType2 = getElementType(resultType);
        String safeVariableName = Strings.getSafeVariableName(elementType.getName(), this.method.getParameterNames());
        final SourceRHS sourceRHS = new SourceRHS(safeVariableName, elementType, new HashSet(), this.errorMessagePart);
        Assignment targetAssignment = this.ctx.getMappingResolver().getTargetAssignment(this.method, getDescription(), elementType2, this.formattingParameters, SelectionCriteria.forMappingMethods(this.selectionParameters, this.method.getOptions().getIterableMapping().getMappingControl(this.ctx.getElementUtils()), this.callingContextTargetPropertyName, false), sourceRHS, this.positionHint, new Supplier() { // from class: org.mapstruct.ap.internal.model.ContainerMappingMethodBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.m2366xba7a3b6b(sourceRHS, elementType, elementType2);
            }
        });
        if (targetAssignment == null) {
            if (this.method instanceof ForgedMethod) {
                return null;
            }
            reportCannotCreateMapping(this.method, String.format("%s \"%s\"", sourceRHS.getSourceErrorMessagePart(), sourceRHS.getSourceType().describe()), sourceRHS.getSourceType(), elementType2, "");
        } else {
            this.ctx.getMessager().note(2, Message.ITERABLEMAPPING_SELECT_ELEMENT_NOTE, targetAssignment);
            if (this.method instanceof ForgedMethod) {
                ((ForgedMethod) this.method).addThrownTypes(targetAssignment.getThrownTypes());
            }
        }
        Assignment wrapper = getWrapper(targetAssignment, this.method);
        boolean zIsReturnDefault = this.method.getOptions().getIterableMapping().getNullValueMappingStrategy().isReturnDefault();
        MethodReference factoryMethod = this.method.isUpdateMethod() ? null : ObjectFactoryMethodResolver.getFactoryMethod(this.method, null, this.ctx);
        HashSet hashSet = new HashSet(this.method.getParameterNames());
        hashSet.add(safeVariableName);
        return (M) instantiateMappingMethod(this.method, hashSet, wrapper, factoryMethod, zIsReturnDefault, safeVariableName, LifecycleMethodResolver.beforeMappingMethods(this.method, this.selectionParameters, this.ctx, hashSet), LifecycleMethodResolver.afterMappingMethods(this.method, this.selectionParameters, this.ctx, hashSet), this.selectionParameters);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: forge, reason: merged with bridge method [inline-methods] */
    public Assignment m2366xba7a3b6b(SourceRHS sourceRHS, Type type, Type type2) {
        Assignment assignmentForgeMapping = super.forgeMapping(sourceRHS, type, type2);
        if (assignmentForgeMapping != null) {
            this.ctx.getMessager().note(2, Message.ITERABLEMAPPING_CREATE_ELEMENT_NOTE, assignmentForgeMapping);
        }
        return assignmentForgeMapping;
    }
}
