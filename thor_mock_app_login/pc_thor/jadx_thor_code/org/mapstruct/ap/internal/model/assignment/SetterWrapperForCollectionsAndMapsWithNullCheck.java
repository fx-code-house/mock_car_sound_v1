package org.mapstruct.ap.internal.model.assignment;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/* loaded from: classes3.dex */
public class SetterWrapperForCollectionsAndMapsWithNullCheck extends WrapperForCollectionsAndMaps {
    private final Type targetType;
    private final TypeFactory typeFactory;

    public SetterWrapperForCollectionsAndMapsWithNullCheck(Assignment assignment, List<Type> list, Type type, TypeFactory typeFactory, boolean z) {
        super(assignment, list, type, z);
        this.targetType = type;
        this.typeFactory = typeFactory;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(super.getImportTypes());
        if (isDirectAssignment()) {
            if (this.targetType.getImplementationType() != null) {
                hashSet.addAll(this.targetType.getImplementationType().getImportTypes());
            } else {
                hashSet.addAll(this.targetType.getImportTypes());
            }
            if (isEnumSet()) {
                hashSet.add(this.typeFactory.getType(EnumSet.class));
            }
        }
        if (isDirectAssignment() || getSourcePresenceCheckerReference() == null) {
            hashSet.addAll(getNullCheckLocalVarType().getImportTypes());
        }
        return hashSet;
    }

    public boolean isDirectAssignment() {
        return getType() == Assignment.AssignmentType.DIRECT;
    }

    public boolean isEnumSet() {
        return "java.util.EnumSet".equals(this.targetType.getFullyQualifiedName());
    }
}
