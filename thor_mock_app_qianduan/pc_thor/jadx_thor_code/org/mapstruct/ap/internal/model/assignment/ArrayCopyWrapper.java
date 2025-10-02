package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.Set;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;

/* loaded from: classes3.dex */
public class ArrayCopyWrapper extends AssignmentWrapper {
    private final Type arraysType;
    private final boolean setExplicitlyToDefault;
    private final boolean setExplicitlyToNull;
    private final Type targetType;

    public boolean isIncludeSourceNullCheck() {
        return true;
    }

    public ArrayCopyWrapper(Assignment assignment, String str, Type type, Type type2, boolean z, boolean z2, boolean z3) {
        super(assignment, z);
        this.arraysType = type;
        this.targetType = type2;
        assignment.setSourceLocalVarName(assignment.createUniqueVarName(str));
        this.setExplicitlyToDefault = z3;
        this.setExplicitlyToNull = z2;
    }

    @Override // org.mapstruct.ap.internal.model.assignment.AssignmentWrapper, org.mapstruct.ap.internal.model.common.ModelElement, org.mapstruct.ap.internal.model.Constructor
    public Set<Type> getImportTypes() {
        HashSet hashSet = new HashSet(getAssignment().getImportTypes());
        hashSet.add(this.arraysType);
        hashSet.add(this.targetType);
        return hashSet;
    }

    public boolean isSetExplicitlyToNull() {
        return this.setExplicitlyToNull;
    }

    public boolean isSetExplicitlyToDefault() {
        return this.setExplicitlyToDefault;
    }
}
