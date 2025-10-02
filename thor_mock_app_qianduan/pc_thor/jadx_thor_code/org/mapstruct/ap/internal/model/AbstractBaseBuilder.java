package org.mapstruct.ap.internal.model;

import javax.lang.model.element.AnnotationMirror;
import org.mapstruct.ap.internal.model.AbstractBaseBuilder;
import org.mapstruct.ap.internal.model.BeanMappingMethod;
import org.mapstruct.ap.internal.model.ValueMappingMethod;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.MappingMethodUtils;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.util.Message;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes3.dex */
public class AbstractBaseBuilder<B extends AbstractBaseBuilder<B>> {
    protected MappingBuilderContext ctx;
    protected Method method;
    protected B myself;

    AbstractBaseBuilder(Class<B> cls) {
        this.myself = cls.cast(this);
    }

    public B mappingContext(MappingBuilderContext mappingBuilderContext) {
        this.ctx = mappingBuilderContext;
        return this.myself;
    }

    public B method(Method method) {
        this.method = method;
        return this.myself;
    }

    boolean canGenerateAutoSubMappingBetween(Type type, Type type2) {
        return !isDisableSubMappingMethodsGeneration() && this.ctx.canGenerateAutoSubMappingBetween(type, type2);
    }

    private boolean isDisableSubMappingMethodsGeneration() {
        return this.method.getOptions().getMapper().isDisableSubMappingMethodsGeneration().booleanValue();
    }

    Assignment createForgedAssignment(SourceRHS sourceRHS, BuilderType builderType, ForgedMethod forgedMethod) {
        MappingMethod mappingMethodBuild;
        if (this.ctx.getForgedMethodsUnderCreation().containsKey(forgedMethod)) {
            return createAssignment(sourceRHS, this.ctx.getForgedMethodsUnderCreation().get(forgedMethod));
        }
        this.ctx.getForgedMethodsUnderCreation().put(forgedMethod, forgedMethod);
        if (MappingMethodUtils.isEnumMapping(forgedMethod)) {
            mappingMethodBuild = new ValueMappingMethod.Builder().method(forgedMethod).valueMappings(forgedMethod.getOptions().getValueMappings()).enumMapping(forgedMethod.getOptions().getEnumMappingOptions()).mappingContext(this.ctx).build();
        } else {
            mappingMethodBuild = new BeanMappingMethod.Builder().forgedMethod(forgedMethod).returnTypeBuilder(builderType).mappingContext(this.ctx).build();
        }
        Assignment assignmentCreateForgedAssignment = createForgedAssignment(sourceRHS, forgedMethod, mappingMethodBuild);
        this.ctx.getForgedMethodsUnderCreation().remove(forgedMethod);
        return assignmentCreateForgedAssignment;
    }

    Assignment createForgedAssignment(SourceRHS sourceRHS, ForgedMethod forgedMethod, MappingMethod mappingMethod) {
        if (mappingMethod == null) {
            return null;
        }
        if (forgedMethod.getMappingReferences().isRestrictToDefinedMappings() || !this.ctx.getMappingsToGenerate().contains(mappingMethod)) {
            this.ctx.getMappingsToGenerate().add(mappingMethod);
        } else {
            forgedMethod = new ForgedMethod(this.ctx.getExistingMappingMethod(mappingMethod).getName(), forgedMethod);
        }
        return createAssignment(sourceRHS, forgedMethod);
    }

    private Assignment createAssignment(SourceRHS sourceRHS, ForgedMethod forgedMethod) {
        MethodReference methodReferenceForForgedMethod = MethodReference.forForgedMethod(forgedMethod, ParameterBinding.fromParameters(forgedMethod.getParameters()));
        methodReferenceForForgedMethod.setAssignment(sourceRHS);
        return methodReferenceForForgedMethod;
    }

    void reportCannotCreateMapping(Method method, String str, Type type, Type type2, String str2) {
        this.ctx.getMessager().printMessage(method.getExecutable(), Message.PROPERTYMAPPING_MAPPING_NOT_FOUND, str, type2.describe(), str2, type2.describe(), type.describe());
    }

    void reportCannotCreateMapping(Method method, AnnotationMirror annotationMirror, String str, Type type, Type type2, String str2) {
        this.ctx.getMessager().printMessage(method.getExecutable(), annotationMirror, Message.PROPERTYMAPPING_MAPPING_NOT_FOUND, str, type2.describe(), str2, type2.describe(), type.describe());
    }
}
