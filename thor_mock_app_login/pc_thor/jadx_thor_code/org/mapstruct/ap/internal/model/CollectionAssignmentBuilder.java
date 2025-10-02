package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.gem.CollectionMappingStrategyGem;
import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.assignment.ExistingInstanceSetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.GetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMaps;
import org.mapstruct.ap.internal.model.assignment.SetterWrapperForCollectionsAndMapsWithNullCheck;
import org.mapstruct.ap.internal.model.assignment.UpdateWrapper;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.AccessorType;

/* loaded from: classes3.dex */
public class CollectionAssignmentBuilder {
    private Assignment assignment;
    private MappingBuilderContext ctx;
    private Method method;
    private NullValueCheckStrategyGem nvcs;
    private NullValuePropertyMappingStrategyGem nvpms;
    private SourceRHS sourceRHS;
    private AccessorType targetAccessorType;
    private String targetPropertyName;
    private Accessor targetReadAccessor;
    private Type targetType;

    public CollectionAssignmentBuilder mappingBuilderContext(MappingBuilderContext mappingBuilderContext) {
        this.ctx = mappingBuilderContext;
        return this;
    }

    public CollectionAssignmentBuilder method(Method method) {
        this.method = method;
        return this;
    }

    public CollectionAssignmentBuilder targetReadAccessor(Accessor accessor) {
        this.targetReadAccessor = accessor;
        return this;
    }

    public CollectionAssignmentBuilder targetType(Type type) {
        this.targetType = type;
        return this;
    }

    public CollectionAssignmentBuilder targetPropertyName(String str) {
        this.targetPropertyName = str;
        return this;
    }

    public CollectionAssignmentBuilder targetAccessorType(AccessorType accessorType) {
        this.targetAccessorType = accessorType;
        return this;
    }

    public CollectionAssignmentBuilder assignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }

    public CollectionAssignmentBuilder rightHandSide(SourceRHS sourceRHS) {
        this.sourceRHS = sourceRHS;
        return this;
    }

    public CollectionAssignmentBuilder nullValueCheckStrategy(NullValueCheckStrategyGem nullValueCheckStrategyGem) {
        this.nvcs = nullValueCheckStrategyGem;
        return this;
    }

    public CollectionAssignmentBuilder nullValuePropertyMappingStrategy(NullValuePropertyMappingStrategyGem nullValuePropertyMappingStrategyGem) {
        this.nvpms = nullValuePropertyMappingStrategyGem;
        return this;
    }

    public Assignment build() {
        Assignment assignment = this.assignment;
        boolean z = this.method.getOptions().getMapper().getCollectionMappingStrategy() == CollectionMappingStrategyGem.TARGET_IMMUTABLE || this.targetReadAccessor == null;
        if (this.targetAccessorType == AccessorType.SETTER || this.targetAccessorType.isFieldAssignment()) {
            if (assignment.isCallingUpdateMethod() && !z) {
                if (this.targetReadAccessor == null) {
                    this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.PROPERTYMAPPING_NO_READ_ACCESSOR_FOR_TARGET_TYPE, this.targetPropertyName);
                }
                return new UpdateWrapper(assignment, this.method.getThrownTypes(), ObjectFactoryMethodResolver.getFactoryMethod(this.method, this.targetType, SelectionParameters.forSourceRHS(this.sourceRHS), this.ctx), this.targetAccessorType.isFieldAssignment(), this.targetType, true, this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_NULL && !this.targetType.isPrimitive(), this.nvpms == NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT);
            }
            if (this.method.isUpdateMethod() && !z) {
                return new ExistingInstanceSetterWrapperForCollectionsAndMaps(assignment, this.method.getThrownTypes(), this.targetType, this.nvcs, this.nvpms, this.ctx.getTypeFactory(), this.targetAccessorType.isFieldAssignment());
            }
            if (assignment.getType() == Assignment.AssignmentType.DIRECT || this.nvcs == NullValueCheckStrategyGem.ALWAYS) {
                return new SetterWrapperForCollectionsAndMapsWithNullCheck(assignment, this.method.getThrownTypes(), this.targetType, this.ctx.getTypeFactory(), this.targetAccessorType.isFieldAssignment());
            }
            return new SetterWrapperForCollectionsAndMaps(assignment, this.method.getThrownTypes(), this.targetType, this.targetAccessorType.isFieldAssignment());
        }
        if (z) {
            this.ctx.getMessager().printMessage(this.method.getExecutable(), Message.PROPERTYMAPPING_NO_WRITE_ACCESSOR_FOR_TARGET_TYPE, this.targetPropertyName);
        }
        return new GetterWrapperForCollectionsAndMaps(assignment, this.method.getThrownTypes(), this.targetType, this.targetAccessorType.isFieldAssignment());
    }
}
