package org.mapstruct.ap.internal.model.assignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class UpdateWrapper extends AssignmentWrapper {
    private final Assignment factoryMethod;
    private final boolean includeSourceNullCheck;
    private final boolean setExplicitlyToDefault;
    private final boolean setExplicitlyToNull;
    private final Type targetImplementationType;
    private final List<Type> thrownTypesToExclude;

    public UpdateWrapper(Assignment assignment, List<Type> list, Assignment assignment2, boolean z, Type type, boolean z2, boolean z3, boolean z4) {
        super(assignment, z);
        this.thrownTypesToExclude = list;
        this.factoryMethod = assignment2;
        this.targetImplementationType = determineImplType(assignment2, type);
        this.includeSourceNullCheck = z2;
        this.setExplicitlyToDefault = z4;
        this.setExplicitlyToNull = z3;
    }

    private static Type determineImplType(Assignment assignment, Type type) {
        if (assignment != null) {
            return null;
        }
        return type.getImplementationType() != null ? type.getImplementationType() : type;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.Assignment
    public List<Type> getThrownTypes() {
        List<Type> thrownTypes = super.getThrownTypes();
        ArrayList arrayList = new ArrayList(thrownTypes);
        for (Type type : this.thrownTypesToExclude) {
            for (Type type2 : thrownTypes) {
                if (type2.isAssignableTo(type)) {
                    arrayList.remove(type2);
                }
            }
        }
        return arrayList;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(super.getImportTypes());
        Assignment assignment = this.factoryMethod;
        if (assignment != null) {
            hashSet.addAll(assignment.getImportTypes());
        }
        Type type = this.targetImplementationType;
        if (type != null) {
            hashSet.add(type);
            hashSet.addAll(this.targetImplementationType.getTypeParameters());
        }
        return hashSet;
    }

    public Assignment getFactoryMethod() {
        return this.factoryMethod;
    }

    public boolean isIncludeSourceNullCheck() {
        return this.includeSourceNullCheck;
    }

    public boolean isSetExplicitlyToNull() {
        return this.setExplicitlyToNull;
    }

    public boolean isSetExplicitlyToDefault() {
        return this.setExplicitlyToDefault;
    }
}
